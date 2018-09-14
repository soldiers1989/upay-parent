package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.PayOrderCheckDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.pay.PayOrderListPo;
/**
 * 订单预生成前检查
 * @author Guo
 *
 */
public class PayOrderCheckService extends AbstractDipperHandler<PayOrderCheckDto> {
	private static final Logger LOG = LoggerFactory.getLogger(PayOrderCheckService.class);
	
	@Resource
	private IDaoService daoService;

	@Override
	public PayOrderCheckDto execute(PayOrderCheckDto dto, Message message) throws Exception {
	    SimpleDateFormat SIM_YMDHMS=new SimpleDateFormat("yyyyMMddHHmmss");
		LOG.info("channel:[{}],订单预生成前检查开始", message.getChannel());
		Date startDate = DateUtil.parse(dto.getOuterOrderStartDate(), "yyyyMMddHHmmss");
		Date endDate = DateUtil.parse(dto.getOuterOrderEndDate(), "yyyyMMddHHmmss");
		if(startDate.compareTo(endDate) > 0) {
			ExInfo.throwDipperEx(AppCodeDict.GWSPAY2426);
		}
		if(dto.getTransAmt()==null){
		    ExInfo.throwDipperEx(AppCodeDict.GWSPAY4001);
		}
		if(dto.getTransAmt().compareTo(BigDecimal.ZERO)<=0){
		    ExInfo.throwDipperEx(AppCodeDict.GWSPAY4002);
		}
		if(dto.getTransAmt().intValue()>=Integer.MAX_VALUE){
		    ExInfo.throwDipperEx(AppCodeDict.BISPAY0039);
		}
		//根据商户订单号查询
		PayOrderListPo pol = new PayOrderListPo();
		pol.setOuterOrderNo(dto.getOuterOrderNo());
		pol = daoService.selectOne(pol);
		String isCreateOrder = "true";
		if(null != pol) {
			String userIp = pol.getSpbillCreateIp();
			String orderStat = pol.getOrderStat();//订单状态
			if(!DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N.equals(orderStat)) {
				ExInfo.throwDipperEx(AppCodeDict.GWSPAY2102);
			}
			
			if(SysInfoContext.getSysTime().compareTo(pol.getOuterOrderEndDate()) > 0) {
				ExInfo.throwDipperEx(AppCodeDict.GWSPAY2105);//订单过期
			}
			isCreateOrder = "false";
			dto.setOrderNo(pol.getOrderNo());//返回订单号
		}
		//商户上传参数校验
		//没有校验闰年平年
		String reg="^(19|20)\\d{2}"
                + "(((02)((0|2)([0-9])|(1)\\d{1}))|"
                + "((01|03|05|07|08|10|12)((0[1-9])|((1|2)\\d{1})|(30|31)))|"
                + "((04|06|09|11)((0[1-9])|(1|2)\\d{1}|(30))))"
                + "(((0|1)\\d{1})|(2[0-3]))"
                + "(([0-5])\\d{1})"
                + "(([0-5])\\d{1})$";
		Pattern pat=Pattern.compile(reg);
		Matcher mat1= pat.matcher(dto.getOuterOrderStartDate());
		Matcher mat2= pat.matcher(dto.getOuterOrderEndDate());
		if(!mat1.matches()||!mat2.matches()){
		    ExInfo.throwDipperEx(AppCodeDict.BISGNR0027);
		}
		Date now =new Date();
		LOG.debug("当前时间   "+SIM_YMDHMS.format(now));
		LOG.debug("开始时间   "+dto.getOuterOrderStartDate());
		LOG.debug("结束时间   "+dto.getOuterOrderEndDate());
		if(SIM_YMDHMS.parse(dto.getOuterOrderStartDate()).compareTo(SIM_YMDHMS.parse(dto.getOuterOrderEndDate()))>0
		        ||SIM_YMDHMS.parse(dto.getOuterOrderEndDate()).compareTo(now)<0){
		    ExInfo.throwDipperEx(AppCodeDict.BISGNR0030);
		}
		if(!DataBaseConstants_PAY.T_CCY_CNY.equals(dto.getCurr())){
		    ExInfo.throwDipperEx(AppCodeDict.BISGNR0029, "人民币");
		}
		if(!DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE.equals(dto.getPayServicType())
		        &&!DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_FORTHWITH.equals(dto.getPayServicType())){
		    ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "支付服务类型");
		}
		if(!CommonConstants_GNR.CHNL_ID_APP.equals(dto.getChnlId())&&!CommonConstants_GNR.CHNL_ID_WEB.equals(dto.getChnlId())){
		    ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "渠道");
		}
		dto.getChnlId();
		if((dto.getOtherTranAmt()!=null&&dto.getOtherTranAmt().compareTo(BigDecimal.ZERO)>0)
		        ||(dto.getProductAmt()!=null&&dto.getProductAmt().compareTo(BigDecimal.ZERO)>0)){
		    BigDecimal zero=new BigDecimal(0);
		    if(!(dto.getTransAmt().compareTo((dto.getOtherTranAmt()==null?zero:dto.getOtherTranAmt())
                .add(dto.getProductAmt()==null?zero:dto.getProductAmt()))==0)){
		        ExInfo.throwDipperEx(AppCodeDict.BISPAY0059);
		    }
		}
		dto.setIsCreateOrder(isCreateOrder);
		LOG.info("channel:[{}],订单预生成前检查结束", message.getChannel());
		return dto;
	}
}
