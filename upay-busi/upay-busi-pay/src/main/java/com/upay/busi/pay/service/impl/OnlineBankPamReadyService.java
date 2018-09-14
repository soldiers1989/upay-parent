package com.upay.busi.pay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.pay.service.dto.OnlineBankPamReadyDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.pay.PayRouteInfoPo;

public class OnlineBankPamReadyService extends
		AbstractDipperHandler<OnlineBankPamReadyDto> {
	@Resource
	IDaoService daoService;
	@Override
	public OnlineBankPamReadyDto execute(OnlineBankPamReadyDto dto,
			Message message) throws Exception {
		dto.setOrderType(DataBaseConstants_PAY.T_ORDER_LIST_TYPE_OUT_ONLINE);
		dto.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_COLLECTION);
		dto.setOrderName("网银他行卡充值");
		dto.setAccountName(dto.getPayeeAcctName());
		
		// 查询核心的信息
				PayRouteInfoPo coreRouteInfo = new PayRouteInfoPo();
				coreRouteInfo.setRouteCode(DataBaseConstans_ACC.ROUTE_CODE_CORE);
				coreRouteInfo = daoService.selectOne(coreRouteInfo);
				// 核心
				dto.setCoreAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);// 核心资金池账户
																					// 清算账户类型
				dto.setCoreBankNo("");// 核心银行行号
				dto.setCoreClrAcctNo(coreRouteInfo.getClrAcctNo());// 核心待清算账户
				dto.setCoreClrType(coreRouteInfo.getClrType());// 核心清算方式
				dto.setCoreName(coreRouteInfo.getRouteName());// 核心名称
				dto.setCoreRouteCode(coreRouteInfo.getRouteCode());// 核心资金通道
				dto.setCoreTransAcctNo(coreRouteInfo.getTransAcctNo());// 核心资金池账户
				dto.setCoreBankName("红塔银行");// 核心银行名称
				dto.setCoreUserId(coreRouteInfo.getDefUserId());

				String routeCode = dto.getRouteCode();
				
				dto.setRouteType(routeCode);
				if (DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)) { // 核心通道单笔代收
					
					SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
					String time = sim.format(new Date());
					dto.setMachineTime(time.substring(8));
					dto.setMachineDate(time.substring(0, 8));
					dto.setBizDate(sim.format(SysInfoContext.getSysDate()).substring(0,8));
					dto.setCurrency(DataBaseConstants_PAY.T_CORE_CCY_CNY);
					dto.setAmount(dto.getTotalFee());
					
					dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);//本行对公账户 到 平台待清账户
					dto.setSetAccount(coreRouteInfo.getClrAcctNo());//贷方 收款方  待清算账户
					dto.setBankCardNo(dto.getPayerAcctNo());//借方 付款方   用户账号
					
					
					if(DataBaseConstants_PAY.ACCT_TYPE_BUSINESS.equals(dto.getAccountType())){
						//当代扣本行对公账户时需要，核心流水付款方需要以下的参数
						dto.setCupBankName("云南红塔银行");
						dto.setCnapsBankNo("05247410");
						dto.setCardBinType(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT);
					}
					
					
					dto.setOrderDes("本行代收");
				} else if (DataBaseConstants_PAY.ROUTE_CODE_CPCN.equals(routeCode)) { // 中金通道单笔代收
					
					//查询第三方资金通道对应信息
					PayRouteInfoPo payRoute = new PayRouteInfoPo();
					payRoute.setRouteCode(dto.getRouteCode());
					payRoute = daoService.selectOne(payRoute);

					dto.setThirdTransAcctNo(payRoute.getTransAcctNo());// 第三方往来户
					dto.setThirdRouteCode(payRoute.getRouteCode());// 第三方资金通道
					dto.setThirdBankName(payRoute.getRouteName());// 第三方银行名称
					dto.setThirdBankNo("");// 第三方银行行号
					dto.setThirdAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);// 第三方心资金池账户
																						// 清算账户类型
					dto.setThirdClrType(payRoute.getClrType());// 第三方清算方式
					dto.setThirdName(payRoute.getRouteName());// 第三方名称
					dto.setThirdUserId(payRoute.getDefUserId());// 第三方用户ID

					//代收时查询红塔E付在中金开的备用金账户
					GnrParmPo parmPo=new GnrParmPo();
					parmPo.setParmId(DataBaseConstans_ACC.ZJ_ACCT_NO);
					parmPo=daoService.selectOne(parmPo);
					if(null==parmPo){
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "中金备用金账户参数未配置");
					}
					
					dto.setPaymentAccountName(parmPo.getParmName());// 中金账号名称
					dto.setPaymentAccountType(DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);//中金备用金类型
					dto.setPaymentAccountNumber(parmPo.getParmValue());// 中金备用账号
					
					if (DataBaseConstans_ACC.TRANS_CODE_WITHDRAW.equals(dto.getTransCode())) {
						dto.setBankTxTime(DateUtil.format(dto.getSysTime(),DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1));
					}
					
					//准备核心记账参数
					SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
					String time = sim.format(new Date());
					dto.setMachineTime(time.substring(8));
					dto.setMachineDate(time.substring(0, 8));
					dto.setBizDate(sim.format(SysInfoContext.getSysDate()).substring(0,8));
					dto.setCurrency(DataBaseConstants_PAY.T_CORE_CCY_CNY);
					dto.setAmount(dto.getTotalFee());
					
					dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);//交易类型为
					dto.setBankCardNo(dto.getThirdTransAcctNo());// 付款方   中金往来户
					dto.setSetAccount(dto.getPayeeAcctNo());// 收款方         红塔二类账户
					
					
					//调用中金代收接口 参数准备
					dto.setAmount(dto.getTotalFee());// 交易金额 分
					dto.setAccountType(DataBaseConstants_PAY.ACCT_TYPE_PERSON);// 账户类型11个人账户12企业账户
					dto.setValidDate("");// 信用卡有效期，格式 YYMM 当代扣信用卡时，该项必填
					dto.setCvn2("");// 信用卡背面的末 3 位数字当代扣信用卡时，该项必填
					dto.setAccountName(dto.getPayerAcctName());// 账户名称
					dto.setAccountNumber(dto.getPayerAcctNo());// 账户号码
					dto.setBranchName(dto.getPayerBranchName());// 分支行
					dto.setProvince(dto.getPayerProvince());// 分支行省份
					dto.setCity(dto.getPayerCity());// 分支行城市
					dto.setEmail(dto.getPayerEmail());// 电子邮箱
					dto.setRouteCertType("0");// 个人证件类型   0 身份证
					dto.setCertNo(dto.getPayerCertNo());// 付款人证件号码
//					dto.setNote("中金代收");// 备注
					dto.setContractUserID("");// 协议用户编号。目前允许为空
					dto.setPhoneNumber(dto.getPayerMobile());// 付款人手机号
					dto.setSettlementFlag("0001");// 结算标识， 默认 SettlementFlag=0001
					if(DataBaseConstants_PAY.ACCT_TYPE_PERSON.equals(dto.getAccountType())){
						dto.setCardMediaType("10");// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
						// 11=个人账户时，不上送默认为借记卡 账户类型为
						// 12=企业账户时，该字段为空
					}else{
						dto.setCardMediaType(null);// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
						// 11=个人账户时，不上送默认为借记卡 账户类型为
						// 12=企业账户时，该字段为空
					}
					
					
					dto.setOrderDes("中金代收");
				}
				
				//网银充值  核心记账时     账户类型为二类户
				dto.setPayeeAccountType(DataBaseConstants_PAY.TWO_TYPES_OF_ACCOUNT);
				return dto;
	}
} 
