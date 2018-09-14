package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.SingleCollectionOrderChkDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.pay.PayOrderListPo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.math.BigDecimal;

/**
 * 单笔代收交易参数检查及交易参数初始化
 * 由收银台发起单笔代收交易，订单已预生成；
 * 由商户后台发起的单笔代收交易，订单需要生成；
 * 代收内部转账交易，为行内内部户转账交易，无需行号、账户类型、证件类型、证件号码、手机号，但需上送收款方信息
 * @author zhangjianfeng
 * @since 2016/11/24 08:39
 */
public class SingleCollectionOrderChkService extends AbstractDipperHandler<SingleCollectionOrderChkDto> {

    private static final Logger logger = LoggerFactory.getLogger(SingleCollectionOrderChkService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public SingleCollectionOrderChkDto execute(SingleCollectionOrderChkDto dto, Message message) throws Exception {
    	//当商户号为空时，交易则为我行   代收，不涉及商户
    	String payeeAccountNo="";
    	String payeeAccountType="";
    	String payeeAccountName="";
    	String payeeMobile="";
    	if(StringUtils.isBlank(dto.getMerNo())){
    		payeeAccountNo = dto.getPayeeAccountNo();
    		payeeAccountType = dto.getPayeeAccountType();
    		if(StringUtils.isBlank(payeeAccountNo)){
    			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "收款账号");
    		}
    		if(StringUtils.isBlank(payeeAccountType)){
    			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "收款账号类型");
    		}
    		
    		if(DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(payeeAccountType)
    				||DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(payeeAccountType)){
    			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "收款账号类型暂不支持他行卡或他行对公账户");
    		}
    		payeeAccountName = dto.getPayeeAccountName();
    		payeeMobile = dto.getPayeeMobile();
    	}
    	
    	//代扣金额检查
    	BigDecimal transAmt = dto.getTransAmt();
    	if(null==transAmt){
    		ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "代扣金额");
    	}
    	
    	if(transAmt.signum() <= 0) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "金额必须大于0"); //交易金额必须大于0
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

        //代收类型类
        String collectionType = dto.getCollectionType();

        //交易备注非空处理
        if(StringUtils.isBlank(dto.getTransComments())) {
            if(CommonConstants_GNR.TRANS_CODE_SINGLE_COLLECTION.equals(dto.getTransCode())
                    && StringUtils.isNotBlank(collectionType)
                    && DataBaseConstants_PAY.COLLECTION_TYPE_IN_TRANSFERS.equals(collectionType)) {
                dto.setTransComments("内部转账");
            } else if(CommonConstants_GNR.TRANS_CODE_SINGLE_COLLECTION.equals(dto.getTransCode())
                    && StringUtils.isNotBlank(collectionType)
                    && DataBaseConstants_PAY.COLLECTION_TYPE_NON_IN_TRANSFERS.equals(collectionType)) {
                dto.setTransComments("代收");
            } else if(CommonConstants_GNR.TRANS_CODE_SINGLE_COLLECTION.equals(dto.getTransCode())
                    && StringUtils.isBlank(collectionType)) {
                dto.setTransComments("代收");
            } else if(CommonConstants_GNR.TRANS_TYPE_QUICK_PAY.equals(dto.getTransCode())
                    || CommonConstants_GNR.TRANS_TYPE_QUICK_PAY_VERIFY_CODE.equals(dto.getTransCode())) {
                dto.setTransComments("快捷支付");
            }
        }

        //代收内部转账
        if(StringUtils.isNotBlank(collectionType)
                && DataBaseConstants_PAY.COLLECTION_TYPE_IN_TRANSFERS.equals(collectionType)) {
            //代收类型为本行内部转账
            dto.setBankBinFlag(DataBaseConstants_PAY.BANK_FLAG_IN);
            dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
            
        	/*if(DataBaseConstants_PAY.ACCT_TYPE_BUSINESS.equals(dto.getAccountType())){
                dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_ENTITY_SETTLEMENT_ACCT); //单位结算户
        	}else if(DataBaseConstants_PAY.ACCT_TYPE_PERSON.equals(dto.getAccountType())){
        		dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);//个人借记卡
        	}*/
            if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getAccountType())){
                dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_ENTITY_SETTLEMENT_ACCT); //单位结算户
            }else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getAccountType())){
                dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);//个人借记卡
            }
            dto.setCardBinType(dto.getAccountType());







            //收款方信息检查
//            if(StringUtils.isBlank(dto.getReceiveAcctNo())) {
//                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "收款账号");
//            }
//            if(StringUtils.isBlank(dto.getReceiveAcctName())) {
//                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "收款户名");
//            }
            //交易类型 代收
            dto.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_COLLECTION);
        } else { //代收非内部转账
            //代扣账户类型非空检查
            if(StringUtils.isBlank(dto.getAccountType())) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付账户类型");
            }

            //证件类型非空检查
            if(StringUtils.isBlank(dto.getCertType())) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "证件类型");
            }

            //证件号码非空检查
            if(DataBaseConstants_PAY.ACCT_TYPE_PERSON.equals(dto.getAccountType()) && StringUtils.isBlank(dto.getCertNo())) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "证件号码");
            }

            //个人账户贷记卡有效期、CVN非空检查  账户类型
          /*  if(DataBaseConstants_PAY.ACCT_TYPE_PERSON.equals(dto.getAccountType())
                    && DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(dto.getAcctType())) {*/
            if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(dto.getAccountType())){
            	//如果是个人账户  判断是借记卡还是贷记卡
            	if(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(dto.getCardBinType())){
                    if(StringUtils.isBlank(dto.getValiDate())) {
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "信用卡有效期");
                    }
                    if(StringUtils.isBlank(dto.getCvn2())) {
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "信用卡CVN");
                    }
                    dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD);   //贷记卡
            	}else{
            		dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);    //借记卡
            	}
            	
               /* if(StringUtils.isBlank(dto.getValiDate())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "信用卡有效期");
                }
                if(StringUtils.isBlank(dto.getCvn2())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "信用卡CVN");
                }*/
            }/*else if(DataBaseConstants_PAY.ACCT_TYPE_BUSINESS.equals(dto.getAccountType())){
            	if(StringUtils.isBlank(dto.getBankId())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "代扣他行对公账户银行编号");
                }
            	dto.setLogoId(dto.getBankId());
            	dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
            	dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_ENTITY_SETTLEMENT_ACCT); //他行对公
            }*/
           else if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getAccountType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getAccountType())){
                if(StringUtils.isBlank(dto.getBankId())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "代扣他行对公账户银行编号");
                }
                dto.setLogoId(dto.getBankId());
                dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
                dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_ENTITY_SETTLEMENT_ACCT); //他行对公
            }
            dto.setCardBinType(dto.getAccountType());
            dto.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_COLLECTION);
        }

        //渠道标识
        if("01".equals(dto.getSingleCollectionFlag())) { //收银台发起单笔代收交易
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
            //订单是不为待支付状态
            if(!DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N.equals(payOrder.getOrderStat())) {
                ExInfo.throwDipperEx(AppCodeDict.GWSPAY2102); //订单状态不是【待支付】状态
            }
            
            if(transAmt.compareTo(payOrder.getTransAmt())!=0){
            	ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "代付金额与平台订单金额不符，请检查!");
            }
            //用户IP检查
//            String spbillCreateIp = dto.getSpbillCreateIp();
//            if(StringUtils.isBlank(spbillCreateIp)) {
//                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户IP");
//            }
//            if(!spbillCreateIp.equals(payOrder.getSpbillCreateIp())) {
//                ExInfo.throwDipperEx(AppCodeDict.GWSPAY4003);
//            }
            //订单是否过期
            if(dto.getSysTime().compareTo(payOrder.getOuterOrderEndDate()) > 0) {
                ExInfo.throwDipperEx(AppCodeDict.GWSPAY2105);//订单过期
            }
            dto.setOrderType(payOrder.getOrderType());
            dto.setMerNo(payOrder.getMerNo());
        } else {
            
        	//商户发起
        	if(StringUtils.isNotBlank(dto.getMerNo())){
        		//商户后台发起的单笔代收交易
        		//商户号非空检查
//	            if(StringUtils.isBlank(dto.getMerNo())) {
//	                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户号");
//	            }
	
	            //商户订单号非空检查
	            if(StringUtils.isBlank(dto.getOuterOrderNo())) {
	                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户订单号");
	            }
	
	            //商户订单号唯一性检查
	            PayOrderListPo payOrder = new PayOrderListPo();
	            payOrder.setOuterOrderNo(dto.getOuterOrderNo());
	            payOrder = daoService.selectOne(payOrder);
	            if(payOrder != null) {
	            	//如果商户订单号在平台存在，则返回平台订单号和状态
	            	dto.setOrderNo(payOrder.getOrderNo());//平台订单号
	            	dto.setOrderStat(payOrder.getOrderStat());//状态
	                ExInfo.throwDipperEx(AppCodeDict.BISPAY0043, "商户订单号");
	            }
	
	            //商户交易日期非空检查
	            if(StringUtils.isBlank(dto.getMerDate())) {
	                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户交易日期");
	            } else {
	            	try{
	            		DateUtil.parse(dto.getMerDate(), "yyyyMMddHHmmss");
	            	}catch(Exception e){
	            		ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "商户交易日期格式不为'yyyyMMddHHmmss'");
	            	}
	//                dto.setMerDate(DateUtil.format(DateUtil.parse(dto.getMerDate(), "yyyyMMdd HH:mm:ss"), "yyyyMMddHHmmss"));
	            }
	
	            //商户通知URL非空检查
	            if(StringUtils.isBlank(dto.getNotifyUrl())) {
	                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户通知URL");
	            }
	            dto.setOrderType(DataBaseConstants_PAY.T_ORDER_LIST_TYPE_OUT_ONLINE);
	            dto.setOrderName("商户：["+dto.getMerNo()+"]代收");
        	}else{
        		dto.setOrderType(DataBaseConstants_PAY.T_ORDER_LIST_TYPE_UPAY);//平台生成的订单
        		dto.setOrderName("我行代收");
        	}

            //用户IP设置，由商户后台发起的付收交易经nginx转发，可通过X-Real-IP获取用户真实IP地址：proxy_set_header X-Real-IP $remote_addr;
            String spbillCreateIp = dto.getSpbillCreateIp();
            if(StringUtils.isBlank(spbillCreateIp)) {
                Object userRealIp = message.getTarget().getHeaders().get("X-Real-IP");
                dto.setSpbillCreateIp(userRealIp == null ? "127.0.0.1" : userRealIp.toString());
            }
            dto.setSingleCollectionFlag("02"); //02-商户发起单笔代收交易；
        }
        //支付类型
        dto.setPayType(DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_COLLECTION);
        //币种 CNY
        dto.setCurr(DataBaseConstants_PAY.T_CCY_CNY);
        //支付服务类型 0002-即时到账
        dto.setPayServicType(DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_FORTHWITH);
        //来往账标志
        dto.setSrFlag(DataBaseConstants_PAY.SR_FLAG_NOSTRO);

        logger.info("单笔代收交易，交易参数检查及交易参数初始化完成");
        return dto;
    }


}
