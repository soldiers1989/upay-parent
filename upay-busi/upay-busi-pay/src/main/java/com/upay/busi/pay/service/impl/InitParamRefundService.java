package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.pay.service.dto.InitParamRefundDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.pay.PayRouteInfoPo;


/**
 *退款请求中金代收代付接口参数准备
 *
 * 
 */
public class InitParamRefundService extends AbstractDipperHandler<InitParamRefundDto> {

    @Resource
    private IDaoService daoService;

    @Override
    public InitParamRefundDto execute(InitParamRefundDto dto, Message message) throws Exception {
    	String merStlAccType = dto.getMerStlAccType();
    	String merAddOrSub = dto.getMerAddOrSub();
    	String merNo = dto.getMerNo();
    	
    	if(StringUtils.isBlank(merStlAccType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "结算账户类型");
        }
    	if(StringUtils.isBlank(merAddOrSub)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "增减标志");
        }
    	if(StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
        }
    	PayRouteInfoPo payRouteInfoPo = new PayRouteInfoPo();
    	payRouteInfoPo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
    	payRouteInfoPo = daoService.selectOne(payRouteInfoPo);
    	
    	MerAcctInfoPo merAcctInfoPo = new MerAcctInfoPo();
    	merAcctInfoPo.setMerNo(merNo);
    	merAcctInfoPo = daoService.selectOne(merAcctInfoPo);
    	GnrParmPo parmPo=new GnrParmPo();
    	parmPo.setParmId(DataBaseConstans_ACC.ZJ_ACCT_NO);
    	parmPo=daoService.selectOne(parmPo);
    	if(null==parmPo){
    		 ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "中金备用金账户配置");
    	}
    	
		dto.setZjPayErAcctType(DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);
		dto.setZjPayEeAcctType(merAcctInfoPo.getStlAcctType());
    	if("1".equals(merAddOrSub)){//1代表交易金额大于手续费金额，隔日退款，从结算账户退至待清算账户 调用中金代收接口
    		dto.setZjVbindAcctNo(parmPo.getParmValue());
    		dto.setCertName(parmPo.getParmName());

    		dto.setBankId(merAcctInfoPo.getBankId());
    		dto.setPaymentAccountName(merAcctInfoPo.getStlAcctName());
    		dto.setPaymentAccountNumber(merAcctInfoPo.getStlAcctNo());
    		dto.setSettlementFlag("0001");//结算标示  默认0001
    		dto.setReqzjFlag("2011");//中金代收接口2011
    		
    		if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(merStlAccType)){//他行借记卡
    			dto.setAccountType("11");//11 个人账户  12企业账户
    		}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(merStlAccType)){//他行对公账户
    			dto.setAccountType("12");//11 个人账户  12企业账户
    		}
    		
    	}else if("0".equals(merAddOrSub)){//0代表交易金额小于手续费金额，隔日退款，从待清算账户退至结算账户 调用中金代付接口
    		dto.setZjVbindAcctNo(merAcctInfoPo.getStlAcctNo());
    		dto.setCertName(merAcctInfoPo.getStlAcctName());
    		dto.setBankId(merAcctInfoPo.getBankId());
    		dto.setPaymentAccountName(parmPo.getParmName());
    		dto.setPaymentAccountNumber(parmPo.getParmValue());
    		dto.setReqzjFlag("4530");//中金代收接口2011
    		if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(merStlAccType)){//他行借记卡
    			dto.setAccountType("11");//11 个人账户  12企业账户
    		}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(merStlAccType)){//他行对公账户
    			dto.setAccountType("12");//11 个人账户  12企业账户
    		}
    	}
    	dto.setZjRouteCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
    	dto.setZjTransAccount(payRouteInfoPo.getTransAcctNo());
        return dto;
    }

}
