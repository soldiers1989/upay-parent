package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.pay.service.dto.RefundOrderDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.CommonMethodUtils;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 退款订单检查原子服务
 * 交易参数检查、原支付订单状态检查、是否重复提交退款、退款金额检查、退款期限检查
 * @author zhangjianfeng
 * @since 2016/8/16
 */
public class RefundOrderCheckService extends AbstractDipperHandler<RefundOrderDto> {

    private static final Logger LOG = LoggerFactory.getLogger(RefundOrderCheckService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public RefundOrderDto execute(RefundOrderDto dto, Message message) throws Exception {

        //交易非空检查
        String merNo = dto.getMerNo(); //商户号
        String merUserId=dto.getMerUserId();
        String outerOrderNo = dto.getOuterOrderNo(); //商户原订单号
        String outerRefundNo=dto.getOuterRefundNo();//商户订单号
        String platOrderNo=dto.getPlatOrderNo();
        if(StringUtils.isBlank(merUserId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户的用户号");
        }
        if(StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
        }
        if(StringUtils.isBlank(outerOrderNo)&&StringUtils.isBlank(platOrderNo)){
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "退款订单号");
        }
        if(StringUtils.isBlank(outerRefundNo)){
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号订单号");
        }
        //退款金额
        if(StringUtils.isBlank(dto.getRefundAmt())) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "退款金额");
        }
        
        BigDecimal transAmt = new BigDecimal(dto.getRefundAmt());
        if (transAmt.signum() <= 0) { //退款金额为负数或退款金额为零
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0046); //退款金额无效
        }

        //商户IP非空判定
//        if (StringUtils.isBlank(dto.getSpbillCreateIp())) {
//            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户IP");
//        }
        //查询原订单信息
        PayOrderListPo oriOrder = new PayOrderListPo();
        oriOrder.setMerNo(merNo);
        if(StringUtils.isNotBlank(platOrderNo)){
            oriOrder.setOrderNo(platOrderNo);
        }else if(StringUtils.isNotBlank(outerOrderNo)){
            oriOrder.setOuterOrderNo(outerOrderNo);
        }
        oriOrder.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_PAY); //支付交易
        oriOrder = daoService.selectOne(oriOrder);

        //原支付订单是否存在
        if(null == oriOrder) {
            if(StringUtils.isNotBlank(outerOrderNo)){                
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, outerOrderNo); //订单[{}]不存在
            }else{
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, platOrderNo); //订单[{}]不存在
            }
        }
        
        //原支付订单状态检查，只对支付成功或确认收贷的订单进行退款处理
        String oriOrderStatus = oriOrder.getOrderStat();
        String payServerType=oriOrder.getPayServicType();
        boolean checkStat=StringUtils.isBlank(payServerType)?false
                :(DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE.equals(payServerType)&&DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP.equals(oriOrderStatus))
                ||(DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_FORTHWITH.equals(payServerType)&&DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(oriOrderStatus)
                ||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO.equals(oriOrderStatus));
        LOG.debug("原支付订单[{}]订单状态为[{}]", new Object[]{oriOrder.getOrderNo(), oriOrderStatus});
        LOG.debug("原支付订单[{}]订单支付类型为[{}]", new Object[]{oriOrder.getOrderNo(), payServerType});
        if(!checkStat) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0057);
        }

        //判断是否在退款日期内
        int outDays=Integer.parseInt(DipperParm.getParmByKey(CommonConstants_GNR.MER_REFUND_OUT_DAYS).toString());
        int days = DateUtil.betweenDays(dto.getSysDate(), oriOrder.getOrderDate());
        if(days > outDays) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0050, outDays); //只允许[{}]天内退货
        }

//        //查询订单之前退款记录
        BigDecimal hadRefundAmt = new BigDecimal("0"); //已退款金额
//        Map<String, Object> refundQueryMap = new HashMap<String, Object>();
//        refundQueryMap.put("transCode", dto.getTransCode()); //退款交易
//        refundQueryMap.put("oriOrderNo", oriOrder.getOrderNo()); //退款交易原订单号
//        refundQueryMap.put("merNo", merNo); //退款商户号
//        refundQueryMap.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_REF);
//        refundQueryMap.put("orderStats", new String[]{DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC});
//
//
//        List<PayOrderListPo> refundOrderList =
//                daoService.selectList(PayOrderListPo.class.getName() + ".selectListExtra", refundQueryMap);
        hadRefundAmt=oriOrder.getEjectAmt()==null?hadRefundAmt:oriOrder.getEjectAmt();
//        if(refundOrderList!=null&&refundOrderList.size()>0){            
//            for(PayOrderListPo refundOrder : refundOrderList) {
//                hadRefundAmt = hadRefundAmt.add(refundOrder.getTransAmt());
//            }
//        }
        /*
         * TODO 需要判断手续费是否分润
         * 分润前：可退金额=交易总金额-已退金额
         * 分润后：可退金额=交易总金额-已退金额-手续费
         */
        boolean checkFee=false;

        //退款金额判断，退款总金额不能大于订单支付金额
        BigDecimal oriOrderTransAmt = oriOrder.getTransAmt(); //原订单交易金额单位：元
        BigDecimal totalRefundAmt = transAmt.add(hadRefundAmt);
        if(oriOrderTransAmt.compareTo(totalRefundAmt) < 0) { //退款累计金额[{}]超过原订单总金额[{}]
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0025, new Object[]{totalRefundAmt, oriOrderTransAmt});
        }
        //判断是部分退款还是全额退款
        if(oriOrderTransAmt.compareTo(transAmt)==0||oriOrderTransAmt.compareTo(totalRefundAmt)==0){
            dto.setRefundAmtFlag(CommonBaseConstans_PAY.REFUND_AMT_ALL);
        }else{
            dto.setRefundAmtFlag(CommonBaseConstans_PAY.REFUND_AMT_POR);
        }
        if(DataBaseConstants_PAY.T_PAY_TYPE_WEIXIN_QR_CODE.equals(oriOrder.getPayType())||
                DataBaseConstants_PAY.T_PAY_TYPE_PUBLIC_NO.equals(oriOrder.getPayType())||
                DataBaseConstants_PAY.T_PAY_TYPE_CARD_PAY.equals(oriOrder.getPayType())){
            if(StringUtils.isBlank(oriOrder.getMerNo())){
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "原订单商户号");
            }
            MerBaseInfoPo mer=new MerBaseInfoPo();
            mer.setMerNo(oriOrder.getMerNo());
            mer=daoService.selectOne(mer);
            if(mer==null){
                ExInfo.throwDipperEx(AppCodeDict.BISMER0018, oriOrder.getMerNo());
            }
            if(StringUtils.isBlank(mer.getSubMchId())){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "微信商户识别码");
            }
            dto.setSubMchId(mer.getSubMchId());
        }
        
        //对于退款失败的订单重新发起退款，采用原来的订单号，和流水号
        PayOrderListPo refundOrder=new PayOrderListPo();
        refundOrder.setOuterOrderNo(outerRefundNo);
        refundOrder.setMerNo(merNo);
        refundOrder=daoService.selectOne(refundOrder);
        dto.setRefundRepeatFlag(CommonBaseConstans_PAY.REFUND_REPEAT_NO);
        if(refundOrder!=null){
            if(StringUtils.isBlank(refundOrder.getOriOrderNo())){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0043,"退款订单号");
            }
            if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC.equals(refundOrder.getOrderStat())){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0054);
            }
            if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING.equals(refundOrder.getOrderStat())){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0055);
            }
            dto.setOrderNo(refundOrder.getOrderNo());
            dto.setRefundRepeatFlag(CommonBaseConstans_PAY.REFUND_REPEAT_YES);
        }
        
        dto.setOriOrderName(oriOrder.getOrderName());
        dto.setEjectAmt(totalRefundAmt);
        BigDecimal big=new BigDecimal(100);
        dto.setOriOrderTransAmt(oriOrder.getTransAmt()); //原订单交易金额
        dto.setHadRefundAmt(hadRefundAmt); //已成功或申请中的退款金额
        String oriPayType = oriOrder.getPayType(); //原订单支付方式
        dto.setOriPayType(oriPayType);
        dto.setOriStlFlag(oriOrder.getStlFlag()); //原订单清算标志
        dto.setOriCcy(oriOrder.getCurr()); //原交易币种
        dto.setOriUserId(oriOrder.getUserId()); //原订单用户ID
        dto.setOriTransDate(oriOrder.getOrderDate());
        dto.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_REF);
        dto.setOriOrderNo(oriOrder.getOrderNo());
        dto.setUserTransAmt(transAmt);
        dto.setTransAmt(transAmt);
        dto.setOriTotalFee(oriOrder.getTransAmt().setScale(2, BigDecimal.ROUND_HALF_UP).multiply(big).intValue()+"");
        dto.setOriFeeAmt(oriOrder.getMerFeeAmt());
        dto.setRefundAmt(transAmt.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(big).intValue()+"");
        String routeCode=CommonMethodUtils.getRouteCodeByPayType(oriOrder.getPayType());
        if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(routeCode)){
        	dto.setUnionOrderTime(new SimpleDateFormat("yyyyMMddHHmmss").format(oriOrder.getPayTime()));
        	if(oriPayType.equals(DataBaseConstants_PAY.T_PAY_TYPE_ACP_QR_CODE)){
        		dto.setReqType("0340000903");
        	}
           	if(oriPayType.equals(DataBaseConstants_PAY.T_PAY_TYPE_ACP_WR_CODE)||oriPayType.equals(DataBaseConstants_PAY.T_PAY_TYPE_ACP_XWR_CODE)){
        		dto.setReqType("0150000903");
        	}
        	
        }
        dto.setOrgRouteCode(routeCode);
        dto.setRouteCode(routeCode);
        dto.setOriRouteCode(routeCode);
        dto.setOriTransAmt(oriOrder.getTransAmt());
        dto.setTotalFeeRefund(transAmt.multiply(new BigDecimal(100)).intValue()+"");
        //是否隔日退货设置： N-否； Y-是；
        //是否退回手续费:Y-退，N-不退
        //目前不退还手续费
        dto.setIsRefundFee("N");
        if(dto.getSysDate().equals(oriOrder.getOrderDate())) {
            dto.setIsNextDayRefund("N");
        } else {
            if(hadRefundAmt.compareTo(BigDecimal.ZERO)==0&&!checkFee&&(oriOrder.getMerFeeAmt()!=null&&oriOrder.getMerFeeAmt().compareTo(BigDecimal.ZERO)>0)){
               // dto.setIsRefundFee("Y");目前不退还手续费
            }
            dto.setIsNextDayRefund("Y");
        }
        
        //如果是隔日退款，检查商户的结算账户
        dto.setRefundMerStlAmtFlag("N");
        if("Y".equals(dto.getIsNextDayRefund())&&oriOrder !=null){
            if(CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(oriOrder.getTransType())){
                MerAcctInfoPo merAcc=new MerAcctInfoPo();
                merAcc.setMerPlatNo(oriOrder.getMerNo());
                merAcc=daoService.selectOne(merAcc);
                if(merAcc==null){
                    ExInfo.throwDipperEx("商户[{}]没有结算账户",oriOrder.getMerNo());
                }
                if(DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(merAcc.getStlAcctType())){
                	//目前中金代收不支持他行对公账户
                	ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "目前不支持他行对公账户的隔日退款");
                }
                dto.setMerStlAccNo(merAcc.getStlAcctNo());
                dto.setMerStlAccType(merAcc.getStlAcctType());
                //如果商户结算账户不为虚拟账户，查询核心清算账户账号
                PayRouteInfoPo route=new PayRouteInfoPo();
                route.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
                route=daoService.selectOne(route);
                if(route==null){
                    ExInfo.throwDipperEx("[{}]无通道信息",DataBaseConstants_PAY.ROUTE_CODE_HOST);
                }
                dto.setCoreTransAccNo(route.getTransAcctNo());
                dto.setCoreStlAccNo(route.getClrAcctNo());
                dto.setRefundMerStlAmtFlag("Y");
                if("Y".equals(dto.getIsRefundFee())){
                    BigDecimal merFeeAmt=oriOrder.getMerFeeAmt()==null?BigDecimal.ZERO:oriOrder.getMerFeeAmt();
                    if(transAmt.compareTo(merFeeAmt)>0){
                        dto.setRefundMerStlAmt(transAmt.subtract(merFeeAmt));
                        dto.setRefundMerStlAmtF(transAmt.subtract(merFeeAmt).multiply(big).toString());
                        dto.setMerAddOrSub("1");
                        dto.setMerDcFlag(DataBaseConstants_PAY.T_DC_FLAG_D);
                    }else if(transAmt.compareTo(merFeeAmt)<0){
                        dto.setRefundMerStlAmt(merFeeAmt.subtract(transAmt));
                        dto.setRefundMerStlAmtF(merFeeAmt.subtract(transAmt).multiply(big).toString());
                        dto.setMerAddOrSub("0");
                        dto.setMerDcFlag(DataBaseConstants_PAY.T_DC_FLAG_C);
                    }else{
                        dto.setRefundMerStlAmtFlag("N");
                    }
                }else{
                    dto.setMerAddOrSub("1");
                    dto.setRefundMerStlAmt(transAmt);
                    dto.setRefundMerStlAmtF(transAmt.multiply(big).toString());
                }
                
            }
        }
        
    	
    	List<PayRouteInfoPo> list = daoService.selectList(new PayRouteInfoPo());
    	for (PayRouteInfoPo payRouteInfoPo : list) {
    		if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(payRouteInfoPo.getRouteCode())){
    			dto.setClrTypeZJ(payRouteInfoPo.getClrType());
    		}else if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(payRouteInfoPo.getRouteCode())){
    			dto.setClrTypeHost(payRouteInfoPo.getClrType());
    		}else if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(payRouteInfoPo.getRouteCode())){
    			dto.setClrTypeUnion(payRouteInfoPo.getClrType());
    		}else if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS.equals(payRouteInfoPo.getRouteCode())){
    			dto.setClrTypeWechat(payRouteInfoPo.getClrType());
    		}else if(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY.equals(payRouteInfoPo.getRouteCode())){
    			dto.setClrTypeAlipay(payRouteInfoPo.getClrType());
    		}
		}
    	
    	
    	
        dto.setOuterOrderNo(dto.getOuterRefundNo());
        return dto;
    }

}
