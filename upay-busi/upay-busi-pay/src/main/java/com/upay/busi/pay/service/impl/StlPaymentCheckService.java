package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
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
 * 结算转账 交易参数检查及交易参数初始化
 * @author zhangjianfeng
 * @since 2016/11/28 00:40
 */
public class StlPaymentCheckService extends AbstractDipperHandler<SinglePaymentOrderChkDto> {

    private static final Logger logger = LoggerFactory.getLogger(StlPaymentCheckService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public SinglePaymentOrderChkDto execute(SinglePaymentOrderChkDto dto, Message message) throws Exception {
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
    		ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "转账金额");
    	}
        if(dto.getTransAmt().signum()<=0){
        	ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "转账金额必须大于0!");
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
                dto.setTransComments("提现");
            } else if(CommonConstants_GNR.TRANS_CODE_SINGLE_PAYMENT.equals(dto.getTransCode())
                    && StringUtils.isBlank(singlePayType)) {
                dto.setTransComments("提现");
            }
        }

        if(StringUtils.isNotBlank(singlePayType) && DataBaseConstants_PAY.SINGLE_PAY_TYPE_IN_TRANSFERS.equals(singlePayType)){ //代付内部转账
        	dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
        	dto.setBankBinFlag(DataBaseConstants_PAY.BANK_FLAG_IN);
            //代付类型为本行内部转账
        	if(DataBaseConstants_PAY.ACCT_TYPE_BUSINESS.equals(dto.getAccountType())){
                dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_ENTITY_SETTLEMENT_ACCT); //单位结算账户
        	}else if(DataBaseConstants_PAY.ACCT_TYPE_PERSON.equals(dto.getAccountType())){
        		dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);//个人卡号
        	}
            //交易类型 代付
            dto.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_PAYMENT);
        } else {//代付非行内内部转账
        	 //如果为企业账户的话，默认设置资金通道为0004(中金)
            if(DataBaseConstants_PAY.ACCT_TYPE_BUSINESS.equals(dto.getAccountType())){
            	dto.setBankBinFlag(DataBaseConstants_PAY.BANK_FLAG_OUT);
            	dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
                dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_ENTITY_SETTLEMENT_ACCT);//账户类型
            }else if(DataBaseConstants_PAY.ACCT_TYPE_PERSON.equals(dto.getAccountType())){  
            	//如果是个人账户  判断是借记卡还是贷记卡
            	if(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(dto.getCardBinType())){
            		dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD);   //贷记卡
            	}else{
            		dto.setAcctType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);    //借记卡
            	}
            }
            //交易类型默认为代付
//            if(StringUtils.isBlank(dto.getTransType())) {
                dto.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_PAYMENT);
//            }
        }

       

        //用户IP设置，由商户后台发起的付收交易经nginx转发，可通过X-Real-IP获取用户真实IP地址：proxy_set_header X-Real-IP $remote_addr;
        String spbillCreateIp = dto.getSpbillCreateIp();
        if(StringUtils.isBlank(spbillCreateIp)) {
            Object userRealIp = message.getTarget().getHeaders().get("X-Real-IP");
            dto.setSpbillCreateIp(userRealIp == null ? null : userRealIp.toString());
        }

        //交易金额，非负检查，非零检查，单位转换（分转换为元）
        BigDecimal transAmt = dto.getTransAmt();
        transAmt = transAmt.setScale(2, BigDecimal.ROUND_HALF_UP);

        dto.setCharTransAmtYuan(transAmt.toString());
        dto.setCharTransAmt(transAmt.multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        dto.setTransAmt(transAmt);
        dto.setOrderType(DataBaseConstants_PAY.T_ORDER_LIST_TYPE_UPAY);
        

        //支付类型  日终结算
        dto.setPayType(DataBaseConstants_PAY.T_PAY_TYPE_STL);
        //币种 CNY
        dto.setCurr(DataBaseConstants_PAY.T_CCY_CNY);
        //支付服务类型 0002-即时到账
        dto.setPayServicType(DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_FORTHWITH);
        //来往账标志
        dto.setSrFlag(DataBaseConstants_PAY.SR_FLAG_NOSTRO);

        logger.info("单笔结算转账交易，交易参数检查及交易参数初始化完成");
        return dto;
    }

}
