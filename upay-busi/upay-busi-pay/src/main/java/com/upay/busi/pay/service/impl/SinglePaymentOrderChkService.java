package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.pay.service.dto.SinglePaymentOrderChkDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.PayOrderListPo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.math.BigDecimal;

/**
 * 单笔代付交易参数检查及交易参数初始化
 * 由收银台发起单笔代付交易，订单已预生成；
 * 由商户后台发起的单笔代付交易，订单需要生成；
 * 代收内部转账为，行内内部转账交易，无需上送银行行号、手机号、账户类型，但需上送付款方信息
 * @author zhangjianfeng
 * @since 2016/11/28 00:40
 */
public class SinglePaymentOrderChkService extends AbstractDipperHandler<SinglePaymentOrderChkDto> {

    private static final Logger logger = LoggerFactory.getLogger(SinglePaymentOrderChkService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public SinglePaymentOrderChkDto execute(SinglePaymentOrderChkDto dto, Message message) throws Exception {
    	//当商户号为空时，交易则为我行   代收，不涉及商户
    	String payerAccountNo="";
    	String payerAccountType="";
    	String payerAccountName="";
    	String payerMobile="";
    	if(StringUtils.isBlank(dto.getMerNo())){
    		payerAccountNo = dto.getPayerAccountNo();
    		payerAccountType = dto.getPayerAccountType();
    		if(StringUtils.isBlank(payerAccountNo)){
    			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "付款账号");
    		}
    		if(StringUtils.isBlank(payerAccountType)){
    			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "付款账号类型");
    		}
    		
    		if(DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(payerAccountType)
    				||DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(payerAccountType)){
    			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "付款账号类型暂不支持他行卡或他行对公账户");
    		}
    		payerAccountName = dto.getPayerAccountName();
    		payerMobile = dto.getPayerMobile();
    	}
    	
        //渠道标识
        if(StringUtils.isBlank(dto.getChnlId())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "渠道标识");
        }

        //代扣银行卡号非空检查
        if(StringUtils.isBlank(dto.getAcctNo())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付账号");
        }

        //代扣银行卡号账户名称非空检查
        if(StringUtils.isBlank(dto.getAcctName())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付账户名");
        }
        
        if(dto.getTransAmt()==null){
    		ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "代付金额");
    	}
        if(dto.getTransAmt().signum()<=0){
        	ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "代付金额必须大于0!");
        }
        

        //代付类型
        String singlePayType = dto.getSinglePayType();
        //交易备注非空检查
        if(StringUtils.isBlank(dto.getTransComments())) {
            if(CommonConstants_GNR.TRANS_CODE_SINGLE_PAYMENT.equals(dto.getTransCode())
                    && StringUtils.isNotBlank(singlePayType)
                    && DataBaseConstants_PAY.SINGLE_PAY_TYPE_IN_TRANSFERS.equals(singlePayType)) {
                dto.setTransComments("内部转账");
            } else if(CommonConstants_GNR.TRANS_CODE_SINGLE_PAYMENT.equals(dto.getTransCode())
                    && StringUtils.isNotBlank(singlePayType)
                    && DataBaseConstants_PAY.SINGLE_PAY_TYPE_NON_IN_TRANSFERS.equals(singlePayType)) {
                dto.setTransComments("代付");
            } else if(CommonConstants_GNR.TRANS_CODE_SINGLE_PAYMENT.equals(dto.getTransCode())
                    && StringUtils.isBlank(singlePayType)) {
                dto.setTransComments("代付");
            }
        }

        if(StringUtils.isNotBlank(singlePayType)
                && DataBaseConstants_PAY.SINGLE_PAY_TYPE_IN_TRANSFERS.equals(singlePayType)) { //代付内部转账
        	dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
        	dto.setBankBinFlag(DataBaseConstants_PAY.BANK_FLAG_IN);
            //代付类型为本行内部转账
        	/*if(DataBaseConstants_PAY.ACCT_TYPE_BUSINESS.equals(dto.getAccountType())){
                dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_ENTITY_SETTLEMENT_ACCT); //单位结算账户
        	}else if(DataBaseConstants_PAY.ACCT_TYPE_PERSON.equals(dto.getAccountType())){
        		dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);//个人卡号
        	}*/

            if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getAccountType())){
                dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_ENTITY_SETTLEMENT_ACCT); //单位结算账户
            }else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getAccountType())){
                dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);//个人卡号
            }
            dto.setCardBinType(dto.getAccountType());

            
            //付款方信息查询
//            if(StringUtils.isBlank(dto.getPayAcctNo())) {
//                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "付款账号");
//            }
//            if(StringUtils.isBlank(dto.getPayAcctName())) {
//                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "付款户名");
//            }
            //交易类型 代付
            dto.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_PAYMENT);
        } else {//代付非行内内部转账
        	 //如果为企业账户的话，默认设置资金通道为0004(中金)
           /* if(DataBaseConstants_PAY.ACCT_TYPE_BUSINESS.equals(dto.getAccountType())){
            	dto.setBankBinFlag(DataBaseConstants_PAY.BANK_FLAG_OUT);
                dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_ENTITY_SETTLEMENT_ACCT);//账户类型
            }else if(DataBaseConstants_PAY.ACCT_TYPE_PERSON.equals(dto.getAccountType())){  
            	//如果是个人账户  判断是借记卡还是贷记卡
            	if(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(dto.getCardBinType())){
            		dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD);   //贷记卡
            	}else{
            		dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);    //借记卡
            	}
            }*/
       //如果为企业账户的话，默认设置资金通道为0004(中金)
            if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getAccountType())){
                dto.setBankBinFlag(DataBaseConstants_PAY.BANK_FLAG_OUT);
                dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_ENTITY_SETTLEMENT_ACCT);//账户类型
            }else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getAccountType())){
                //如果是个人账户  判断是借记卡还是贷记卡
                if(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(dto.getCardBinType())){
                    dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD);   //贷记卡
                }else{
                    dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);    //借记卡
                }
            }
            dto.setCardBinType(dto.getAccountType());
            //代付账户类型非空检查
//            if(StringUtils.isBlank(dto.getAcctType())) {
//                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "收款账户类型");
//            }

            //企业账户暂不支付
//            if(DataBaseConstants_PAY.ACCT_TYPE_BUSINESS.equals(dto.getAccountType())) {
//                ExInfo.throwDipperEx(AppCodeDict.BISPAY0101); //该卡不支持当前业务
//            }

            //手机号非空检查
//            if(StringUtils.isBlank(dto.getMobile())) {
//                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "手机号");
//            }
            //交易类型默认为提现
//            if(StringUtils.isBlank(dto.getTransType())) {
                dto.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_PAYMENT);
//            }
        }

        if("01".equals(dto.getSinglePaymentFlag())) { //收银台发起单笔付收交易
            String orderNo = dto.getOrderNo();
            if(StringUtils.isBlank(orderNo)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付订单号");
            }
            PayOrderListPo payOrder = new PayOrderListPo();
            payOrder.setOrderNo(orderNo);
            payOrder = daoService.selectOne(payOrder);

            //订单是否存在
            if(payOrder == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, orderNo); //订单[{}]不存在
            }
            //订单是否为待支付状态
            if(!DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N.equals(payOrder.getOrderStat())) {
                ExInfo.throwDipperEx(AppCodeDict.GWSPAY2102); //订单状态不是【待支付】状态
            }
            BigDecimal transAmt = payOrder.getTransAmt();
            if(transAmt.compareTo(dto.getTransAmt())!=0){
            	ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "代付金额与平台订单金额不符，请检查!");
            }
            
            //用户IP检查
            String spbillCreateIp = dto.getSpbillCreateIp();
            if(StringUtils.isBlank(spbillCreateIp)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户IP");
            }
            if(!spbillCreateIp.equals(payOrder.getSpbillCreateIp())) {
                ExInfo.throwDipperEx(AppCodeDict.GWSPAY4003);
            }
            //订单是否过期
            if(dto.getSysTime().compareTo(payOrder.getOuterOrderEndDate()) > 0) {
                ExInfo.throwDipperEx(AppCodeDict.GWSPAY2105);//订单过期
            }
            dto.setTransAmt(payOrder.getTransAmt());
            dto.setCharTransAmtYuan(payOrder.getTransAmt().toString());
            dto.setCharTransAmt(payOrder.getTransAmt().multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
            dto.setOrderType(payOrder.getOrderType());
            dto.setMerNo(payOrder.getMerNo());
        } else { 
        	//商户后台发起的单笔代付交易
        	if(StringUtils.isNotBlank(dto.getMerNo())){
	        	//商户号非空检查
	//            if(StringUtils.isBlank(dto.getMerNo())) {
	//                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户号");
	//            }
	        	if(StringUtils.isBlank(dto.getOuterOrderNo())) {
	                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户订单号");
	            }
	        	//商户通知URL非空检查
	            if(StringUtils.isBlank(dto.getNotifyUrl())) {
	                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户通知URL");
	            }
	        	//商户订单号唯一性检查
	            PayOrderListPo payOrder = new PayOrderListPo();
	            payOrder.setOuterOrderNo(dto.getOuterOrderNo());
	            payOrder = daoService.selectOne(payOrder);
	            if(payOrder != null) {
	            	dto.setOrderNo(payOrder.getOrderNo());
	            	dto.setOrderStat(payOrder.getOrderStat());
	                ExInfo.throwDipperEx(AppCodeDict.BISPAY0043, "商户订单号");
	            }
	
	            //商户交易日期非空检查
	            if(StringUtils.isBlank(dto.getMerDate())) {
	                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户交易日期");
	            }else {
	            	try{
	            		DateUtil.parse(dto.getMerDate(), "yyyyMMddHHmmss");
	            	}catch(Exception e){
	            		ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "商户交易日期格式不为'yyyyMMddHHmmss'");
	            	}
	//                    dto.setMerDate(DateUtil.format(DateUtil.parse(dto.getMerDate(), "yyyyMMddHHmmss"), "yyyyMMddHHmmss"));
	            }
	
	            //用户IP设置，由商户后台发起的付收交易经nginx转发，可通过X-Real-IP获取用户真实IP地址：proxy_set_header X-Real-IP $remote_addr;
	            String spbillCreateIp = dto.getSpbillCreateIp();
	            if(StringUtils.isBlank(spbillCreateIp)) {
	                Object userRealIp = message.getTarget().getHeaders().get("X-Real-IP");
	                dto.setSpbillCreateIp(userRealIp == null ? null : userRealIp.toString());
	            }
	            dto.setOrderName("商户：["+dto.getMerNo()+"]代收");
        	}else{
        		dto.setOrderName("我行代收");
        	}

            //交易金额，非负检查，非零检查，单位转换（分转换为元）
            BigDecimal transAmt = dto.getTransAmt();
            if(transAmt == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易金额");
            }
            if(transAmt.signum() <= 0) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0047); //交易金额必须大于0
            }
            transAmt = transAmt.setScale(2, BigDecimal.ROUND_HALF_UP);

            dto.setCharTransAmtYuan(transAmt.toString());
            dto.setCharTransAmt(transAmt.multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
            dto.setTransAmt(transAmt);
            dto.setSinglePaymentFlag("02"); //02-商户发起单笔代付交易；
            if(!"Y".equals(dto.getStlFlag())){
            	dto.setOrderType(DataBaseConstants_PAY.T_ORDER_LIST_TYPE_OUT_ONLINE);
            	
            }else{
            	dto.setOrderType(DataBaseConstants_PAY.T_ORDER_LIST_TYPE_UPAY);
            }
        }

        //支付类型
        dto.setPayType(DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_PAYMENT);
        //币种 CNY
        dto.setCurr(DataBaseConstants_PAY.T_CCY_CNY);
        //支付服务类型 0002-即时到账
        dto.setPayServicType(DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_FORTHWITH);
        //来往账标志
        dto.setSrFlag(DataBaseConstants_PAY.SR_FLAG_NOSTRO);

        logger.info("单笔代付交易，交易参数检查及交易参数初始化完成");
        return dto;
    }
}
