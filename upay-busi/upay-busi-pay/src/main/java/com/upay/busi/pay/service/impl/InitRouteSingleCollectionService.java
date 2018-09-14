package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.pay.service.dto.InitRouteSingleCollectionDto;
import com.upay.commons.constants.*;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.pay.PayRouteInfoPo;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 初始化各资金通首单笔代收参数
 * 
 * 
 * @author zhangjianfeng
 * @since 2016/11/25 21:36
 */
public class InitRouteSingleCollectionService extends
		AbstractDipperHandler<InitRouteSingleCollectionDto> {

	@Resource
	IDaoService daoService;

	@Override
	public InitRouteSingleCollectionDto execute(
			InitRouteSingleCollectionDto dto, Message message) throws Exception {
		// 查询核心的信息
		PayRouteInfoPo coreRouteInfo = new PayRouteInfoPo();
		coreRouteInfo.setRouteCode(DataBaseConstans_ACC.ROUTE_CODE_CORE);
		coreRouteInfo = daoService.selectOne(coreRouteInfo);
		//如果商户号不为空的话，说明是商户代收，商户代收的话，资金会第二天清算给商户，所以收款账户是核心待清户
		if(StringUtils.isNotBlank(dto.getMerNo())){
			// 核心通道信息     
			dto.setCoreAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);// 核心资金池账户  清算账户类型
			dto.setCoreBankNo("");// 核心银行行号
			dto.setCoreClrAcctNo(coreRouteInfo.getClrAcctNo());// 核心待清算账户
			dto.setCoreClrType(coreRouteInfo.getClrType());// 核心清算方式
			dto.setCoreName(coreRouteInfo.getRouteName());// 核心名称
			dto.setCoreRouteCode(coreRouteInfo.getRouteCode());// 核心资金通道
			dto.setCoreTransAcctNo(coreRouteInfo.getTransAcctNo());// 核心资金池账户
			dto.setCoreBankName("红塔银行");// 核心银行名称
			dto.setCoreUserId(coreRouteInfo.getDefUserId());
		}else{
			//商户号为空，说明是我行代扣，不需要商户信息，所以收款账号直接是我行卡号、对公账、二类户或者是内部户
			// 核心通道信息     
			dto.setCoreAcctType(dto.getPayeeAccountType());// 我行收款账户类型
			dto.setCoreBankNo("");// 核心银行行号
			dto.setCoreClrAcctNo(dto.getPayeeAccountNo());// 我行收款账号
			dto.setCoreClrType(coreRouteInfo.getClrType());// 核心清算方式
			dto.setCoreName(dto.getPayeeAccountName());// 我行收款账户名称
			dto.setCoreRouteCode(coreRouteInfo.getRouteCode());// 核心资金通道
			dto.setCoreTransAcctNo(dto.getPayeeAccountNo());// 我行收款账号
			dto.setCoreBankName("红塔银行");// 核心银行名称
			dto.setCoreUserId(coreRouteInfo.getDefUserId());
		}

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
//			dto.setSetAccount(coreRouteInfo.getClrAcctNo());//贷方 收款方  待清算账户
			dto.setSetAccount(dto.getCoreClrAcctNo());//贷方 收款方  商户不为空时为待清算账户   商户为空时收款方账户
			dto.setBankCardNo(dto.getAcctNo());//借方 付款方   用户账号
			
			
			/*if(DataBaseConstants_PAY.ACCT_TYPE_BUSINESS.equals(dto.getAccountType())){
				//当代扣本行对公账户时需要，核心流水付款方需要以下的参数
				dto.setCupBankName("云南红塔银行");
				dto.setCnapsBankNo("05247410");
				dto.setCardBinType(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT);
			}
*/

			if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getAccountType())){
				dto.setCupBankName("云南红塔银行");
				dto.setCnapsBankNo("05247410");
			}
			//dto.setCardBinType(dto.getAccountType());
			dto.setOrderDes("本行代收");
		} else if (DataBaseConstants_PAY.ROUTE_CODE_CPCN.equals(routeCode)) { // 中金通道单笔代收
			//TODO   他行代收时， 收款方二类户时，要经过中金的往来户，调用核心记账08001接口时需要加上  third flag =1  （联创万凤伟 是这么说的）
			//还未添加相关代码
			
			//ggggfdsddsddddddd
			
			
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
			
			dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);//交易类型为内转内
			dto.setSetAccount(dto.getCoreClrAcctNo());// 付款方   核心待清算账户  或者我行 收款账户
			dto.setBankCardNo(dto.getThirdTransAcctNo());// 付款方         中金往来户
			
			//调用中金代收接口 参数准备
			dto.setAmount(dto.getTotalFee());// 交易金额 分
			dto.setAccountType(dto.getAccountType());// 账户类型11个人账户12企业账户
			dto.setValidDate(dto.getValidDate());// 信用卡有效期，格式 YYMM 当代扣信用卡时，该项必填
			dto.setCvn2(dto.getCvn2());// 信用卡背面的末 3 位数字当代扣信用卡时，该项必填
			dto.setAccountName(dto.getAcctName());// 账户名称
			dto.setAccountNumber(dto.getAcctNo());// 账户号码
			dto.setBranchName("");// 分支行
			dto.setProvince("");// 分支行省份
			dto.setCity("");// 分支行城市
			dto.setEmail("");// 电子邮箱
			dto.setRouteCertType("");// 个人证件类型
			dto.setCertNo("");// 证件号码
//			dto.setNote("中金代收");// 备注
			dto.setContractUserID("");// 协议用户编号。目前允许为空
			dto.setPhoneNumber("");// 手机号
			dto.setSettlementFlag("0001");// 结算标识， 默认 SettlementFlag=0001
			/*if(DataBaseConstants_PAY.ACCT_TYPE_PERSON.equals(dto.getAccountType())){
				if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getCardBinType())){
					dto.setCardMediaType("10");// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
				}else if(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(dto.getCardBinType())){
					dto.setCardMediaType("20");// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
				}

				// 11=个人账户时，不上送默认为借记卡 账户类型为
				// 12=企业账户时，该字段为空
			}else{
				dto.setCardMediaType(null);// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
				// 11=个人账户时，不上送默认为借记卡 账户类型为
				// 12=企业账户时，该字段为空
			}*/

			if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getAccountType())){
				dto.setCardMediaType(null);// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
				// 11=个人账户时，不上送默认为借记卡 账户类型为
				// 12=企业账户时，该字段为空
			}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getAccountType())){
				if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getAcctType())){
					dto.setCardMediaType("10");// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
				}else if(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(dto.getAcctType())){
					dto.setCardMediaType("20");// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
				}
				// 11=个人账户时，不上送默认为借记卡 账户类型为
				// 12=企业账户时，该字段为空
			}

			
			dto.setOrderDes("中金代收");
		}else if (DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(routeCode)) { // 银联通道单笔d代收

			//查询第三方资金通道对应信息
			PayRouteInfoPo payRoute = new PayRouteInfoPo();
			payRoute.setRouteCode(dto.getRouteCode());
			payRoute = daoService.selectOne(payRoute);

		  // dto.setThirdTransAcctNo(payRoute.getTransAcctNo());// 第三方往来户
			dto.setThirdTransAcctNo(payRoute.getUnionAcctNo());// 第三方往来户
			dto.setThirdRouteCode(payRoute.getRouteCode());// 第三方资金通道
			dto.setThirdBankName(payRoute.getRouteName());// 第三方银行名称
			dto.setThirdBankNo("");// 第三方银行行号
			dto.setThirdAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);// 第三方心资金池账户
			// 清算账户类型
			dto.setThirdClrType(payRoute.getClrType());// 第三方清算方式
			dto.setThirdName(payRoute.getRouteName());// 第三方名称
			dto.setThirdUserId(payRoute.getDefUserId());// 第三方用户ID
            //todo:银联备用金账户  需要修改
			//代收时查询红塔E付在中金开的备用金账户
			GnrParmPo parmPo=new GnrParmPo();
			parmPo.setParmId(DataBaseConstans_ACC.ZJ_ACCT_NO);
			parmPo=daoService.selectOne(parmPo);
			if(null==parmPo){
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "银联备用金账户参数未配置");
			}

			dto.setPaymentAccountName(parmPo.getParmName());// 银联账号名称
			dto.setPaymentAccountType(DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);//银联备用金类型
			dto.setPaymentAccountNumber(parmPo.getParmValue());// 银联备用账号


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

			dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);//交易类型为内转内
			dto.setSetAccount(dto.getCoreClrAcctNo());// 付款方   核心待清算账户  或者我行 收款账户
			dto.setBankCardNo(dto.getThirdTransAcctNo());// 付款方         中金往来户

			/*if(DataBaseConstants_PAY.ACCT_TYPE_PERSON.equals(dto.getAccountType())){
				if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getCardBinType())){
					dto.setCardMediaType("10");// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
				}else if(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(dto.getCardBinType())){
					dto.setCardMediaType("20");// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
				}
				// 11=个人账户时，不上送默认为借记卡 账户类型为
				// 12=企业账户时，该字段为空
			}else{
				dto.setCardMediaType(null);// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
				// 11=个人账户时，不上送默认为借记卡 账户类型为
				// 12=企业账户时，该字段为空
			}*/
			if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getAccountType())){
				dto.setCardMediaType(null);// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
				// 11=个人账户时，不上送默认为借记卡 账户类型为
				// 12=企业账户时，该字段为空
			}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getAccountType())){
				if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getAcctType())){
					dto.setCardMediaType("10");// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
				}else if(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(dto.getAcctType())){
					dto.setCardMediaType("20");// 中金 卡介质类型:10=借记卡 20=贷记卡 30=存折；账户类型为
				}
				// 11=个人账户时，不上送默认为借记卡 账户类型为
				// 12=企业账户时，该字段为空
			}

            //获取描述
				String payRouteMethod = dto.getPayRouteMethod();
				if(CommonBaseConstans_PAY.UNION_PAY_PRIORITY_COLLECTION.equals(payRouteMethod)){
					dto.setOrderDes("银联代收");
				}else if(CommonBaseConstans_PAY.UNION_PAY_NON_JUMP_PAY.equals(payRouteMethod)){
					dto.setOrderDes("银联无跳转支付");
				}
		}

		return dto;
	}

}
