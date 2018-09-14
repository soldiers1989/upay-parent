package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.PayOrderRegisterDto;
import com.upay.commons.constants.*;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 订单详情登记
 * 
 * @author lihe
 * 
 */

public class PayOrderRegisterService extends AbstractDipperHandler<PayOrderRegisterDto> {

	private static final Logger LOG = LoggerFactory.getLogger(PayStateQryService.class);

	@Resource
	private IDaoService daoService;
	@Resource
	private ISequenceService sequenceService;

	@Override
	public PayOrderRegisterDto execute(PayOrderRegisterDto payOrderRegisterDto,
			Message arg1) throws Exception {
		String transCode = payOrderRegisterDto.getTransCode();
		String returnUrl=payOrderRegisterDto.getReturnUrl();
		String notifyUrl=payOrderRegisterDto.getNotifyUrl();
		if (StringUtils.isBlank(payOrderRegisterDto.getOrderType())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单类型类型");
        }
		if(DataBaseConstants_PAY.T_ORDER_LIST_TYPE_OUT_PRE.equals(payOrderRegisterDto.getOrderType())){
		    if (StringUtils.isBlank(returnUrl)) {
		        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "同步通知路径");
		    }
		    if (StringUtils.isBlank(notifyUrl)) {
		        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "异步通知路径");
		    }
		    if(!returnUrl.contains("http://")&&!returnUrl.contains("https://")){
		        ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "同步通知路径格式");  
		    }
		    if(!notifyUrl.contains("http://")&&!notifyUrl.contains("https://")){
		        ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "异步通知路径格式");
            }
		}
	    if (StringUtils.isBlank(payOrderRegisterDto.getTransType())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易类型");
        }
		// 渠道代码非空判定
		if (StringUtils.isBlank(payOrderRegisterDto.getChnlId())) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "渠道代码");
		}
		// 交易代码非空判定
		if (StringUtils.isBlank(transCode)) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易代码");

		}

		// 用户IP非空判定
		if (StringUtils.isBlank(payOrderRegisterDto.getSpbillCreateIp())) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户IP");
		}
		// 币种非空判定
		if (StringUtils.isBlank(payOrderRegisterDto.getCurr())) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "币种");
		}
		// 交易金额非空判定
		if (null == payOrderRegisterDto.getTransAmt()) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易金额");
		}
		
		// 交易金额非负判定
		if (payOrderRegisterDto.getTransAmt().signum() == -1) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0002, "交易金额");
		}
		BigDecimal transAmt=new BigDecimal(payOrderRegisterDto.getTransAmt().doubleValue()+"");
		if(transAmt.scale()>2){
		    ExInfo.throwDipperEx(AppCodeDict.BISPAY0041);
		}
		//金额保留2位小数转换
		payOrderRegisterDto.setTransAmt(payOrderRegisterDto.getTransAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
		//判断支付服务类型
		String extensionParty = null;
		if(CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(payOrderRegisterDto.getTransType())){
		    if(StringUtils.isNotBlank(payOrderRegisterDto.getPayServicType())){
		        if(!DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE.equals(payOrderRegisterDto.getPayServicType())&&!DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_FORTHWITH.equals(payOrderRegisterDto.getPayServicType())){
		            ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "支付服务类型");
		        }
		    }else{
		        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付服务类型");
		    }
		    if(StringUtils.isNotBlank(payOrderRegisterDto.getMerNo())){	
		    	MerBaseInfoPo merBaseInfo = new MerBaseInfoPo();
		    	merBaseInfo.setMerNo(payOrderRegisterDto.getMerNo());
		    	merBaseInfo = daoService.selectOne(merBaseInfo);
		    	if(merBaseInfo!=null){
		    		extensionParty = merBaseInfo.getExtensionParty();
		    	}
		        //根据商户号查询商户名称
		        if(StringUtils.isBlank(payOrderRegisterDto.getPrimaryMerName())){
		            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户名称");
		        }
		    }
		}

//		Date date = new Date();// 获取当前时间
		// 订单号规则：模块（4位）_日期（8位）_序号（10位）
		String orderNo = sequenceService.generateOrderNo("UPAY");
		//转账时收款方需要记录付款方的订单号
		if(DataBaseConstans_ACC.TRANS_CODE_TRANSFER.equals(payOrderRegisterDto.getTransCode())&&null==payOrderRegisterDto.getTransferPayerOrderNo()){
			payOrderRegisterDto.setTransferPayerOrderNo(orderNo);
		}
		// 登记订单信息
		PayOrderListPo payOrderListPo = new PayOrderListPo();
		if(StringUtils.isNotBlank(payOrderRegisterDto.getPrimaryMerName())){		    
		    payOrderListPo.setOrderName(payOrderRegisterDto.getPrimaryMerName());
		}else{
		    if(StringUtils.isBlank(payOrderRegisterDto.getOrderName())){
		        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单名称");
		    }
		    payOrderListPo.setOrderName(payOrderRegisterDto.getOrderName());
		}
		payOrderListPo.setOrderDes(payOrderRegisterDto.getOrderDes());
		payOrderListPo.setChnlId(payOrderRegisterDto.getChnlId());// 登记渠道代码
		payOrderListPo.setOrderNo(orderNo);// 支付订单号
		payOrderListPo.setPayServicType(payOrderRegisterDto.getPayServicType());// 支付服务类型
		payOrderListPo.setMerSeq(payOrderRegisterDto.getMerSeq());// 商户流水号
		payOrderListPo.setMerNo(payOrderRegisterDto.getMerNo());// 商户代码
		payOrderListPo.setSecMerNo(payOrderRegisterDto.getSecMerNo());// 二级商户代码
		payOrderListPo.setOuterOrderNo(payOrderRegisterDto.getOuterOrderNo());// 商户订单号
		payOrderListPo.setOrderType(payOrderRegisterDto.getOrderType());// 订单类型
		payOrderListPo.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
		payOrderListPo.setStlFlag(DataBaseConstants_BATCH.T_STL_FLAG_NO);
		payOrderListPo.setTransType(payOrderRegisterDto.getTransType());




/**
 * 以下几个字段  主要
 * 用于  补单时
 * 第三方流水成功      核心流水失败 。（根本核心流水没有入库 导致无法补单的情况）
 *
 */
		payOrderListPo.setPayeeAcctNo(payOrderRegisterDto.getPayeeAcctNo());//收款方卡号
		payOrderListPo.setPayeeAcctType(payOrderRegisterDto.getPayeeAcctType());//收款方卡类型
		payOrderListPo.setPayerAcctNo(payOrderRegisterDto.getPayerAcctNo());//付款方卡号
		payOrderListPo.setPayerAcctType(payOrderRegisterDto.getPayerAcctType());//付款方卡类型

		boolean isHavStartDate = false;
		boolean isHavEndDate = false;
		if (StringUtils.isNotBlank(payOrderRegisterDto.getOuterOrderStartDate())) {
			isHavStartDate = true;
			payOrderListPo.setMerDate(DateUtil.parse(payOrderRegisterDto.getOuterOrderStartDate(),
					"yyyyMMddHHmmss"));// 商户日期
			payOrderListPo.setOuterOrderStartDate(DateUtil.parse(payOrderRegisterDto.getOuterOrderStartDate(),
					"yyyyMMddHHmmss"));// 商户订单生成日期
		}
		if (StringUtils.isNotBlank(payOrderRegisterDto.getOuterOrderEndDate())) {
			isHavEndDate = true;
			payOrderListPo.setOuterOrderEndDate(DateUtil.parse(payOrderRegisterDto.getOuterOrderEndDate(),
					"yyyyMMddHHmmss"));// 商户订单失效日期
		}
		payOrderListPo.setPayType(payOrderRegisterDto.getPayType());// 支付方式

		//设置代收和无跳转支付  代付的  payType  用于订单状态同步
		Map<String,Object>objectMap= (Map<String, Object>) arg1.getTarget().getBodys();
		//根据支付方式  设置payType
		if(objectMap.containsKey("payRouteMethod")){
			String payRouteMethod =(String) objectMap.get("payRouteMethod");
				//代收 03
				if(CommonBaseConstans_PAY.UNION_PAY_PRIORITY_COLLECTION.equals(payRouteMethod)){
					payOrderListPo.setPayType(DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_COLLECTION);
				}
				//无跳转支付  05
				if(CommonBaseConstans_PAY.UNION_PAY_NON_JUMP_PAY.equals(payRouteMethod)){
					payOrderListPo.setPayType(DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_WTPAY);
				}
				//代付  04
				if(CommonBaseConstans_PAY.ZJ_PAY_PRIORITY_COLLECTION.equals(payRouteMethod)){
					payOrderListPo.setPayType(DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_PAYMENT);
				}
				payOrderRegisterDto.setPayType(payOrderListPo.getPayType());
		}



		if(StringUtils.isBlank(payOrderListPo.getOrderName())){		    
		    payOrderListPo.setOrderName(payOrderRegisterDto.getOrderName());// 支付订单名称
		}
		payOrderListPo.setTransCode(transCode);// 交易代码
		payOrderListPo.setUserId(payOrderRegisterDto.getUserId());// 用户ID
		//如果是渠道是12表示柜面   日期以核心为准
		String chnlId = payOrderRegisterDto.getChnlId();
		if(CommonConstants_GNR.CHNL_ID_TELLER.equals(chnlId)){
			SimpleDateFormat dateFormat=new SimpleDateFormat(DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD);
			Date date = dateFormat.parse(payOrderRegisterDto.getMerDate());
			payOrderListPo.setOrderDate(date);
		}else {

			payOrderListPo.setOrderDate(SysInfoContext.getSysDate());// 订单创建日期
		}

		payOrderListPo.setOrderTime(SysInfoContext.getSysTime());// 订单创建时间
		Integer orderLmtTime = 0;
		if (isHavStartDate && isHavEndDate) {
		    Long orderLmtTimeL = (payOrderListPo.getOuterOrderEndDate().getTime() - payOrderListPo.getOuterOrderStartDate().getTime())/1000L/60L;
		    orderLmtTime = orderLmtTimeL.intValue();
		} else {
		    orderLmtTime = Integer.valueOf((String) DipperParm.getParmByKey(CommonConstants_GNR.ORDER_LMT_TIME));
		}
		payOrderListPo.setOrderLmtTime(orderLmtTime);
		payOrderListPo.setCurr(payOrderRegisterDto.getCurr());// 币种
		payOrderListPo.setTransAmt(payOrderRegisterDto.getTransAmt());// 交易金额
		payOrderListPo.setOtherTranAmt(payOrderRegisterDto.getOtherTranAmt());// 其他费用
		payOrderListPo.setProductAmt(payOrderRegisterDto.getProductAmt());// 商品费用
		payOrderListPo.setMerFeeAmt(payOrderRegisterDto.getMerFeeAmt());// 商户手续费
		payOrderListPo.setFeeAmt(payOrderRegisterDto.getFeeAmt());// 客户手续费
		if(payOrderRegisterDto.getOriDate() != null) {
			payOrderListPo.setOriDate(DateUtil.parse(
					payOrderRegisterDto.getOriDate(), "yyyyMMddHHmmss"));// 原交易日期
		}
		payOrderListPo.setOriOrderNo(payOrderRegisterDto.getOriOrderNo());// 原支付订单号
		// payOrderListPo.setEjectAmt(payOrderRegisterDto.getEjectAmt());//
		payOrderListPo.setEjectAmt(payOrderRegisterDto.getEjectAmt()); // 累计退货额
	    
		if(DataBaseConstans_ACC.TRANS_CODE_RECHARGE.equals(transCode)||
				DataBaseConstans_ACC.TRANS_CODE_TRANSFER.equals(transCode)||
				DataBaseConstans_ACC.TRANS_CODE_WITHDRAW.equals(transCode)||
				DataBaseConstans_ACC.TRANS_CODE_COLLECTION.equals(transCode)||
				DataBaseConstans_ACC.TRANS_CODE_LOCAL_COLLECTION.equals(transCode)||
				DataBaseConstans_ACC.TRANS_CODE_PAYMENT.equals(transCode)||
				DataBaseConstans_ACC.TRANS_CODE_WXPAY.equals(transCode)){  //后台代收接口，快速注册下单返回微信二维码接口，没有待支付状态，初始化为支付中
			payOrderListPo.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING);// 订单状态  2-支付中
		}else{
			payOrderListPo.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N);// 订单状态 // 1-未支付
		}
		
		payOrderListPo.setSpbillCreateIp(payOrderRegisterDto.getSpbillCreateIp());// 用户IP
		payOrderListPo.setNotifyUrl(URLDecoder.decode(payOrderRegisterDto.getNotifyUrl()==null?"":payOrderRegisterDto.getNotifyUrl(),"UTF-8"));// 异步通知路径
		payOrderListPo.setReturnUrl(URLDecoder.decode(payOrderRegisterDto.getReturnUrl()==null?"":payOrderRegisterDto.getReturnUrl(), "UTF-8"));// 同步通知路径
		payOrderListPo.setLastUpdateTime(new Date());// 最后变更时间
		payOrderListPo.setTransComments(payOrderRegisterDto.getTransComments());
		payOrderListPo.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
				
		//转账时收款方需要记录付款方的订单号
  		if("1".equals(payOrderRegisterDto.getIsRegPayerOrderNo())){
  			if(DataBaseConstants_FEE.FEE_GET_TYPE_INNER.equals(payOrderRegisterDto.getGetType())){
  				payOrderListPo.setTransAmt(MoneyUtil.subtract(payOrderRegisterDto.getTransAmt(), payOrderRegisterDto.getFeeAmt(), RoundingMode.HALF_UP));// 交易金额
  			}
  			payOrderListPo.setRemark1(payOrderRegisterDto.getTransferPayerOrderNo());
  			payOrderListPo.setFeeAmt(null);// 收款方订单为无客户手续费
  		}
  		payOrderListPo.setExtensionParty(extensionParty);
  		payOrderRegisterDto.setPromoterDeptNo(payOrderRegisterDto.getPromoterDeptNo());
		daoService.insert(payOrderListPo);
		LOG.info("订单预生成执行完毕");
		payOrderRegisterDto.setOrderNo(orderNo);
		payOrderRegisterDto.setTransAmt(payOrderListPo.getTransAmt());
		
		return payOrderRegisterDto;
	}
}
