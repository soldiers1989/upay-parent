package com.upay.busi.acc.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.acc.service.dto.SmokeTransferAccountDataDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**
 * 为订单流水信息作准备
 * 
 * @author liubing
 * 
 */
public class SmokeTransferAccountDataService extends
		AbstractDipperHandler<SmokeTransferAccountDataDto> {
	@Resource
	private IDaoService daoService;

	@Override
	public SmokeTransferAccountDataDto execute(SmokeTransferAccountDataDto dto,
			Message message) throws Exception {

		// 查询核心资金通道

		PayRouteInfoPo routeCode = new PayRouteInfoPo();
		routeCode.setRouteCode(DataBaseConstans_ACC.ROUTE_CODE_CORE);
		routeCode = daoService.selectOne(routeCode);
		if (null == routeCode) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "核心资金通道未配置");
		}
		PayRouteInfoPo zjRouteCode = new PayRouteInfoPo();
		zjRouteCode.setRouteCode(DataBaseConstans_ACC.ROUTE_CODE_ZJ);
		zjRouteCode = daoService.selectOne(zjRouteCode);
		if (null == zjRouteCode) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "中金资金通道未配置");
		}
		GnrParmPo parmPo=new GnrParmPo();
    	parmPo.setParmId(DataBaseConstans_ACC.ZJ_ACCT_NO);
    	parmPo=daoService.selectOne(parmPo);
    	if(null==parmPo){
    		ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "中金备用金账户");
    	}
    	dto.setBankTxTime(DateUtil.format(dto.getSysTime(),DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1));
    	
		dto.setCoreClrAcctNo(routeCode.getClrAcctNo());//核心待清算账户
		dto.setCoreTransAcctNo(routeCode.getTransAcctNo());//核心资金池账户
		dto.setCoreRouteCode(routeCode.getRouteCode());//核心资金通道
		dto.setCoreBankName("云南红塔银行");//核心银行名称
		dto.setCoreBankNo("");//核心银行行号
		dto.setCoreAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);//核心资金池账户 清算账户类型
		dto.setCoreClrType(routeCode.getClrType());//核心清算方式
		dto.setCoreName("云南红塔银行");//核心名称
		dto.setCoreUserId(routeCode.getDefUserId());//核心userid
		
		dto.setTransType(DataBaseConstants_PAY.ROUTE_FUNC_CODE_TRANSFER);//平台订单交易类型为转账
		
		// String brancode = (String) DipperParm.getParmByKey("RCB_BRANCODE");

		String stlAcctType = dto.getStlAcctType();
		if (DataBaseConstants_PAY.ACCT_TYPE_EPAY.equals(stlAcctType)) {// 结算方式为虚拟账户
			AccVbookPo vbookPo = new AccVbookPo();
			vbookPo.setUserId(dto.getUserId());
			vbookPo = daoService.selectOne(vbookPo);
			if (null == vbookPo) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "特约商户未开通电子账户");
			}
			
			dto.setPayerAccNo(routeCode.getTransAcctNo());//付款账号  商户虚拟账号
        	dto.setPayerName("平台资金池账户");//付款户名
        	dto.setPayerOrgName("云南红塔银行");//付款账户机构名
        	dto.setPayerBankNo("");//付款账户行号
        	dto.setPayerBankName("云南红塔银行");//付款账户行名
        	dto.setPayerUserId(dto.getUserId());//付款用户id
        	dto.setPayerAccType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);//付款账户类型 
        	dto.setRouteCode(routeCode.getRouteCode());// 资金通道为核心
        	dto.setPayeeAccNo(dto.getPayeeAccNo());//收款账号
        	dto.setPayeeAccType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);//收款账户类型
        	dto.setPayeeName(dto.getPayeeName());//收款账户名
        	dto.setPayeeOrgName("云南红塔银行");//收款账户机构名
        	dto.setPayeeBankNo("");//收款账户行号
        	dto.setPayeeBankName("云南红塔银行");//收款账户行号
			
			dto.setvAcctNo(vbookPo.getVacctNo());
			dto.setOrderName("虚拟账户转账到卡");// 订单名称
			dto.setOrderDes("虚拟账户转账到卡");// 订单描述
			dto.setTransComments("虚拟账户转账到卡");// 订单描述
			
			dto.setSetAccount(dto.getPayerAccNo());
			dto.setBankCardNo(dto.getPayeeAccNo());
			dto.setTrantype(DataBaseConstans_ACC.CODE_TRANS_TYPE_WITHDRAW);// 核心交易类型为提现
			
		} else if (DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(stlAcctType)) {// 结算方式为借记卡
			checkInputParm(dto);
			
			dto.setPayerAccNo(dto.getPayerAccNo());//付款账号  商户虚拟账号
        	dto.setPayerName(dto.getPayerName());//付款户名
        	dto.setPayerOrgName("云南红塔银行");//付款账户机构名
        	dto.setPayerBankNo("");//付款账户行号
        	dto.setPayerBankName("云南红塔银行");//付款账户行名
        	dto.setPayerUserId(dto.getUserId());//付款用户id
        	dto.setPayerAccType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);//付款账户类型 
            
        	dto.setPayeeAccNo(dto.getPayeeAccNo());//收款账号
        	dto.setPayeeName(dto.getPayeeName());//收款账户名
        	dto.setPayeeOrgName("云南红塔银行");//收款账户机构名
        	dto.setPayeeBankNo("");//收款账户行号
        	dto.setPayeeBankName("云南红塔银行");//收款账户行号
        	
        	
			if(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getPayeeCardType())){
				dto.setSetAccount(dto.getPayeeAccNo());
				dto.setBankCardNo(dto.getPayerAccNo());
				dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_CARDTOPUBLIC);// 核心交易类型为 卡转对公账户
				
				dto.setOrderName("一级商户卡号转账到二级商户对公账户");// 订单名称
				dto.setOrderDes("一级商户卡号转账到二级商户对公账户");// 订单描述
				dto.setTransComments("一级商户卡号转账到二级商户对公账户");// 订单描述
				dto.setPayeeAccType(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT);//收款账户类型
			}else if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getPayeeCardType())){
				dto.setSetAccount(dto.getPayeeAccNo());
				dto.setBankCardNo(dto.getPayerAccNo());
				dto.setTrantype(DataBaseConstans_ACC.CODE_TRANS_TYPE_CARD_TO_CARD);// 核心交易类型为支付
				
				dto.setZjAccType(DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);
				dto.setOrderName("一级商户卡号转账到二级商户卡号");// 订单名称
				dto.setOrderDes("一级商户卡号转账到二级商户卡号");// 订单描述
				dto.setTransComments("一级商户卡号转账到二级商户卡号");// 订单描述
				dto.setPayeeAccType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);//付款账户类型 
				
			}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getPayeeCardType())){//他行借记卡
				//请求中金代付接口参数
				dto.setPaymentAccountName(parmPo.getParmName());
				dto.setPaymentAccountNumber(parmPo.getParmValue());
				dto.setAccountType("11");
				dto.setVbindAcctNo(dto.getPayeeAccNo());
				dto.setCertName(dto.getPayeeName());
				dto.setOrderName("一级商户卡号转账到二级商户他行借记卡");// 订单名称
				dto.setOrderDes("一级商户卡号转账到二级商户对他行借记卡");// 订单描述
				dto.setTransComments("一级商户卡号转账到二级商户他行借记卡");// 订单描述
				dto.setZjAccType(DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);
				//请求核心接口参数
				dto.setBankCardNo(dto.getPayerAccNo());
				dto.setSetAccount(zjRouteCode.getTransAcctNo());
				dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_PAY);// 核心交易类型为支付
				dto.setZjRouteCode(DataBaseConstans_ACC.ROUTE_CODE_ZJ);// 资金通道为中金
				dto.setCoreRouteCode(routeCode.getRouteCode());
				dto.setSecondCoreAccType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				dto.setSecondCoreAccName("中金支付");
			}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getPayeeCardType())){//他行对公账户
				dto.setPaymentAccountName(parmPo.getParmName());
				dto.setPaymentAccountNumber(parmPo.getParmValue());
				dto.setAccountType("12");
				dto.setVbindAcctNo(dto.getPayeeAccNo());
				dto.setCertName(dto.getPayeeName());
				//请求核心接口参数
				dto.setBankCardNo(dto.getPayerAccNo());
				dto.setSetAccount(zjRouteCode.getTransAcctNo());
				dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI);// 核心交易类型为支付
				dto.setZjRouteCode(DataBaseConstans_ACC.ROUTE_CODE_ZJ);// 资金通道为中金
				dto.setCoreRouteCode(routeCode.getRouteCode());
				dto.setOrderName("一级商户卡号转账到二级商户他行对公账户");// 订单名称
				dto.setOrderDes("一级商户卡号转账到二级商户对他行对公账户");// 订单描述
				dto.setTransComments("一级商户卡号转账到二级商户他行对公账户");// 订单描述
				dto.setZjAccType(DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);
				dto.setSecondCoreAccType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				dto.setSecondCoreAccName("中金支付");
				dto.setSecondCorePayerAcctName(dto.getPayerName());
			}

		} else if (DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(stlAcctType)) {// 结算方式为内部账户
			checkInputParm(dto);
			
			if(!DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getPayeeCardType())&& !DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getPayeeCardType())){
				dto.setPayerAccNo(dto.getPayerAccNo());//付款账号  商户虚拟账号
				dto.setPayerName(dto.getPayerName());//付款户名
				dto.setPayerOrgName("云南红塔银行");//付款账户机构名
				dto.setPayerBankNo("");//付款账户行号
				dto.setPayerBankName("云南红塔银行");//付款账户行名
				dto.setPayerUserId(dto.getUserId());//付款用户id
				dto.setPayerAccType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);//付款账户类型 
				
				dto.setPayeeAccNo(dto.getPayeeAccNo());//收款账号
				
				dto.setPayeeName(dto.getPayeeName());//收款账户名
				dto.setPayeeOrgName("云南红塔银行");//收款账户机构名
				dto.setPayeeBankNo("");//收款账户行号
				dto.setPayeeBankName("云南红塔银行");//收款账户行号
				
			}
			
			if(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getPayeeCardType())){
				dto.setOrderName("一级商户内部账户转账到二级商户对公账户");// 订单名称
				dto.setOrderDes("一级商户内部户转账到二级商户对公账户");// 订单描述
				dto.setTransComments("一级商户内部户转账到二级商户对公账户");// 订单描述
				dto.setPayeeAccType(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT);//收款账户类型
				
				dto.setSetAccount(dto.getPayerAccNo());
				dto.setBankCardNo(dto.getPayeeAccNo());
				dto.setTrantype(DataBaseConstans_ACC.CODE_TRANS_TYPE_INSIDE_TO_PUBLIC);// 核心交易类型为 内转对公账户
			}else if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getPayeeCardType())){
				dto.setPayeeAccType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);//收款账户类型
				dto.setOrderName("一级商户内部账户转账到二级商户卡号");// 订单名称
				dto.setOrderDes("一级商户内部户转账到二级商户卡号");// 订单描述
				dto.setTransComments("一级商户内部户转账到二级商户卡号");// 订单描述
				
				
				dto.setSetAccount(dto.getPayerAccNo());
				dto.setBankCardNo(dto.getPayeeAccNo());
				dto.setTrantype(DataBaseConstans_ACC.CODE_TRANS_TYPE_WITHDRAW);// 核心交易类型为提现
			}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getPayeeCardType())){//他行借记卡
				//请求中金代付接口参数
				dto.setPaymentAccountName(parmPo.getParmName());
				dto.setPaymentAccountNumber(parmPo.getParmValue());
				dto.setAccountType("11");
				dto.setVbindAcctNo(dto.getPayeeAccNo());
				dto.setCertName(dto.getPayeeName());
				//请求核心接口参数
				dto.setBankCardNo(dto.getPayerAccNo());
				dto.setSetAccount(zjRouteCode.getTransAcctNo());
				dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI);// 核心交易类型为支付
				dto.setZjRouteCode(DataBaseConstans_ACC.ROUTE_CODE_ZJ);// 资金通道为中金
				dto.setCoreRouteCode(routeCode.getRouteCode());
				dto.setZjAccType(DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);
				dto.setOrderName("一级商户内部账户转账到二级商户他行借记卡");// 订单名称
				dto.setOrderDes("一级商户内部账户转账到二级商户对他行借记卡");// 订单描述
				dto.setTransComments("一级商户内部账户转账到二级商户他行借记卡");// 订单描述
				dto.setSecondCoreAccType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				dto.setSecondCoreAccName("中金支付");
			}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getPayeeCardType())){//他行对公账户
				//请求中金代付接口参数
				dto.setPaymentAccountName(parmPo.getParmName());
				dto.setPaymentAccountNumber(parmPo.getParmValue());
				dto.setAccountType("12");
				dto.setVbindAcctNo(dto.getPayeeAccNo());
				dto.setCertName(dto.getPayeeName());
				dto.setZjAccType(DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);
				//请求核心接口参数
				dto.setBankCardNo(dto.getPayerAccNo());
				dto.setSetAccount(zjRouteCode.getTransAcctNo());
				dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI);// 核心交易类型为支付
				dto.setZjRouteCode(DataBaseConstans_ACC.ROUTE_CODE_ZJ);// 资金通道为中金
				dto.setCoreRouteCode(routeCode.getRouteCode());
				dto.setOrderName("一级商户内部账户转账到二级商户他行对公账户");// 订单名称
				dto.setOrderDes("一级商户内部账户转账到二级商户对他行对公账户");// 订单描述
				dto.setTransComments("一级商户内部账户转账到二级商户他行对公账户");// 订单描述
				dto.setSecondCoreAccType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				dto.setSecondCoreAccName("中金支付");
			}
		}else if (DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(stlAcctType)) {// 结算账户为对公账户
			checkInputParm(dto);
			
			if(!DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getPayeeCardType())&& !DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getPayeeCardType())){
				dto.setPayerAccNo(dto.getPayerAccNo());//付款账号  商户虚拟账号
				dto.setPayerName(dto.getPayerName());//付款户名
				dto.setPayerOrgName("云南红塔银行");//付款账户机构名
				dto.setPayerBankNo("");//付款账户行号
				dto.setPayerBankName("云南红塔银行");//付款账户行名
				dto.setPayerUserId(dto.getUserId());//付款用户id
				dto.setPayerAccType(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT);//付款账户类型 
				
				dto.setPayeeAccNo(dto.getPayeeAccNo());//收款账号
				
				dto.setPayeeName(dto.getPayeeName());//收款账户名
				dto.setPayeeOrgName("云南红塔银行");//收款账户机构名
				dto.setPayeeBankNo("");//收款账户行号
				dto.setPayeeBankName("云南红塔银行");//收款账户行号
				
			}
			if(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getPayeeCardType())){
				dto.setOrderName("一级商户内部账户转账到二级商户对公账户");// 订单名称
				dto.setOrderDes("一级商户内部户转账到二级商户对公账户");// 订单描述
				dto.setTransComments("一级商户内部户转账到二级商户对公账户");// 订单描述
				dto.setPayeeAccType(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT);//收款账户类型
				
				dto.setBankCardNo(dto.getPayerAccNo());
				dto.setSetAccount(dto.getPayeeAccNo());
				dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);// 核心交易类型为 内转对公账户
				
			}else if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getPayeeCardType())){
				dto.setOrderName("一级商户内部账户转账到二级商户卡号");// 订单名称
				dto.setOrderDes("一级商户内部户转账到二级商户卡号");// 订单描述
				dto.setTransComments("一级商户内部户转账到二级商户卡号");// 订单描述
				dto.setPayeeAccType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);//收款账户类型
				
				
				dto.setBankCardNo(dto.getPayerAccNo());
				dto.setSetAccount(dto.getPayeeAccNo());
				dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);// 核心交易类型为提现
				
			}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getPayeeCardType())){//他行借记卡
				//请求中金代付接口参数
				dto.setPaymentAccountName(parmPo.getParmName());
				dto.setPaymentAccountNumber(parmPo.getParmValue());
				dto.setAccountType("11");
				dto.setVbindAcctNo(dto.getPayeeAccNo());
				dto.setCertName(dto.getPayeeName());
				//请求核心接口参数
				dto.setBankCardNo(dto.getPayerAccNo());
				dto.setSetAccount(zjRouteCode.getTransAcctNo());
				dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_PUBLICTOIN);// 核心交易类型为支付
				dto.setZjRouteCode(DataBaseConstans_ACC.ROUTE_CODE_ZJ);// 资金通道为中金
				dto.setCoreRouteCode(routeCode.getRouteCode());
				dto.setZjAccType(DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);
				dto.setOrderName("一级商户内部账户转账到二级商户他行借记卡");// 订单名称
				dto.setOrderDes("一级商户内部账户转账到二级商户对他行借记卡");// 订单描述
				dto.setTransComments("一级商户内部账户转账到二级商户他行借记卡");// 订单描述
				dto.setSecondCoreAccType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				dto.setSecondCoreAccName("中金支付");
			}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getPayeeCardType())){//他行对公账户
				//请求中金代付接口参数
				dto.setPaymentAccountName(parmPo.getParmName());
				dto.setPaymentAccountNumber(parmPo.getParmValue());
				dto.setAccountType("12");
				dto.setVbindAcctNo(dto.getPayeeAccNo());
				dto.setCertName(dto.getPayeeName());
				dto.setZjAccType(DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);
				//请求核心接口参数
				dto.setBankCardNo(dto.getPayerAccNo());
				dto.setSetAccount(zjRouteCode.getTransAcctNo());
				dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_PUBLICTOIN);// 核心交易类型为支付
				dto.setZjRouteCode(DataBaseConstans_ACC.ROUTE_CODE_ZJ);// 资金通道为中金
				dto.setCoreRouteCode(routeCode.getRouteCode());
				dto.setOrderName("一级商户内部账户转账到二级商户他行对公账户");// 订单名称
				dto.setOrderDes("一级商户内部账户转账到二级商户对他行对公账户");// 订单描述
				dto.setTransComments("一级商户内部账户转账到二级商户他行对公账户");// 订单描述
				dto.setSecondCoreAccType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				dto.setSecondCoreAccName("中金支付");
			}
		}

		dto.setRouteCode(routeCode.getRouteCode());// 资金通道为核心
		
		return dto;
	}
	
	private void checkInputParm(SmokeTransferAccountDataDto dto){
		String payerAccNo = dto.getPayerAccNo();
		String payeeAccNo = dto.getPayeeAccNo();
		if(StringUtils.isBlank(payerAccNo)){
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "烟草批量转账付款方账号");
		}
		if(StringUtils.isBlank(payeeAccNo)){
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "烟草批量转账收款方账号");
		}
	}
}
