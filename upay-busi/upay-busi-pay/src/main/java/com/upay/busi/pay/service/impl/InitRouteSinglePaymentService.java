package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.pay.service.dto.InitRouteSinglePaymentDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

/**
 * 初始化各资金通道单笔代付参数 本行卡代付交易商户手续费 借：商户账户 贷：平台手续费收入户 他行卡代付交易商户手续费 借：商户账户 贷：平台待清算账户
 * 
 * @author zhangjianfeng
 * @since 2016/11/27 10:58
 */
public class InitRouteSinglePaymentService extends AbstractDipperHandler<InitRouteSinglePaymentDto> {

	private static final Logger LOGGER = LoggerFactory.getLogger(InitRouteSinglePaymentService.class);
	@Resource
	IDaoService daoService;

	@SuppressWarnings("all")
	@Override
	public InitRouteSinglePaymentDto execute(InitRouteSinglePaymentDto dto,
			Message message) throws Exception {
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
		
		//商户代付是从商户结算账户里扣钱，所以付款方一定是商户的结算账户
		String stlAcctNo="";
		String stlAcctName="";
		String stlAcctType="";
		String merNo = dto.getMerNo();
		if(StringUtils.isNotBlank(merNo)){
			MerAcctInfoPo merAcctInfo=new MerAcctInfoPo();
			merAcctInfo.setMerNo(merNo);
			merAcctInfo=daoService.selectOne(merAcctInfo);
			if(null!=merAcctInfo){
				//流水付款方信息准备
				stlAcctName = merAcctInfo.getStlAcctName();
				stlAcctType = merAcctInfo.getStlAcctType();
				stlAcctNo = merAcctInfo.getStlAcctNo();
				dto.setStlAcctName(stlAcctName);
				dto.setStlAcctNo(stlAcctNo);
				dto.setStlAcctType(stlAcctType);
				if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(stlAcctType)
						||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(stlAcctType)){
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"暂不支持商户结算账户为他行卡的代付业务。");
				}
				
			}else{
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"商户【"+merNo+"】未登记结算账户,请检查。");
			}
		}else{
			//如果是我行代付 比如贷款放款之类的
			dto.setStlAcctName(dto.getPayerAccountName());
			dto.setStlAcctNo(dto.getPayerAccountNo());
			dto.setStlAcctType(dto.getPayerAccountType());
			stlAcctNo=dto.getPayerAccountNo();
		}

		dto.setRouteType(routeCode);
		if (DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)) { // 核心通道单笔代付
			SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
			String time = sim.format(new Date());
			dto.setMachineTime(time.substring(8));
			dto.setMachineDate(time.substring(0, 8));
			dto.setBizDate(sim.format(SysInfoContext.getSysDate()).substring(0,8));
			dto.setCurrency(DataBaseConstants_PAY.T_CORE_CCY_CNY);
			dto.setAmount(dto.getTotalFee());
			
			//本行借记卡代付   个人三种情况    卡到卡      内到卡    公到卡    企业三种情况    卡到公      内到公    公到公
			dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);
			dto.setSetAccount(dto.getAcctNo());// 贷方账户 收款方    
			dto.setBankCardNo(stlAcctNo);// 借方账户  付款方  商户结算户
			
			/*if(DataBaseConstants_PAY.ACCT_TYPE_BUSINESS.equals(dto.getAccountType())){
				//当代付是本行对公账户时需要，核心流水付款方需要以下的参数
				dto.setCupBankName("云南红塔银行");
				dto.setCnapsBankNo("05247410");
				dto.setCardBinType(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT);
			}*/
			if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getAccountType())){
				dto.setCupBankName("云南红塔银行");
				dto.setCnapsBankNo("05247410");
				//dto.setCardBinType(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT);
			}
			//dto.setCardBinType(dto.getAccountType());
			dto.setOrderDes("本行代付");
		} else if (DataBaseConstants_PAY.ROUTE_CODE_CPCN.equals(routeCode)) { // 中金通道单笔代付

			//TODO   他行代付时，付款方二类户时，要经过中金的往来户，调用核心记账08001接口时时需要加上  third flag =1 （联创万凤伟 是这么说的）
			//还未添加相关代码





			// 查询第三方资金通道对应信息
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

			// 代收时查询红塔E付在中金开的备用金账户
			GnrParmPo parmPo = new GnrParmPo();
			parmPo.setParmId(DataBaseConstans_ACC.ZJ_ACCT_NO);
			parmPo = daoService.selectOne(parmPo);
			if (null == parmPo) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "中金备用金账户参数未配置");
			}

			//核心记账接口参数准备
			SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
			String time = sim.format(new Date());
			dto.setMachineTime(time.substring(8));
			dto.setMachineDate(time.substring(0, 8));
			dto.setBizDate(sim.format(SysInfoContext.getSysDate()).substring(0,8));
			dto.setCurrency(DataBaseConstants_PAY.T_CORE_CCY_CNY);
			dto.setAmount(dto.getTotalFee());


			dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);
			dto.setSetAccount(dto.getThirdTransAcctNo());// 收款方  中金往来账户
			dto.setBankCardNo(stlAcctNo);// 付款方   商户结算账户


			//中金代付接口参数准备
			dto.setPaymentAccountName(parmPo.getParmName());// 中金账号名称
			dto.setPaymentAccountType(DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);// 中金备用金类型
			dto.setPaymentAccountNumber(parmPo.getParmValue());// 中金备用账号



			/*if(DataBaseConstants_PAY.ACCT_TYPE_PERSON.equals(dto.getAccountType())){//个人代付
				dto.setBankId(dto.getLogoId());
			}else if (DataBaseConstants_PAY.ACCT_TYPE_BUSINESS.equals(dto.getAccountType())){//企业代付
				dto.setCupBankName("");
				dto.setCnapsBankNo("");
				dto.setCardBinType(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT);
			}*/


			if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getAccountType())){
				dto.setCupBankName("");
				dto.setCnapsBankNo("");

			}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getAccountType())){
				dto.setBankId(dto.getLogoId());
			}


		//	dto.setCardBinType(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT);

//			dto.setAccountType(dto.getAcctType());
			dto.setCertName(dto.getAcctName());
			dto.setVbindAcctNo(dto.getAcctNo());
			dto.setPhoneNumber(dto.getMobile());
			dto.setOrderDes("中金代付");

			//该接口不会返回处理时间，固我们自己获取当前时间
			dto.setBankTxTime(DateUtil.format(dto.getSysTime(),DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1));
		}else if (DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(routeCode)) { // 银联通道单笔代付
			// 查询第三方资金通道对应信息
			PayRouteInfoPo payRoute = new PayRouteInfoPo();
			payRoute.setRouteCode(dto.getRouteCode());
			payRoute = daoService.selectOne(payRoute);

			//dto.setThirdTransAcctNo(payRoute.getTransAcctNo());// 第三方往来户
			dto.setThirdTransAcctNo(payRoute.getUnionAcctNo());// 第三方往来户
			dto.setThirdRouteCode(payRoute.getRouteCode());// 第三方资金通道
			dto.setThirdBankName(payRoute.getRouteName());// 第三方银行名称
			dto.setThirdBankNo("");// 第三方银行行号
			dto.setThirdAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);// 第三方心资金池账户
			// 清算账户类型
			dto.setThirdClrType(payRoute.getClrType());// 第三方清算方式
			dto.setThirdName(payRoute.getRouteName());// 第三方名称
			dto.setThirdUserId(payRoute.getDefUserId());// 第三方用户ID

			// 代收时查询红塔E付在中金开的备用金账户
//			GnrParmPo parmPo = new GnrParmPo();
//			parmPo.setParmId(DataBaseConstans_ACC.ZJ_ACCT_NO);
//			parmPo = daoService.selectOne(parmPo);
//			if (null == parmPo) {
//				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "中金备用金账户参数未配置");
//			}

			//核心记账接口参数准备
			SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
			String time = sim.format(new Date());
			dto.setMachineTime(time.substring(8));
			dto.setMachineDate(time.substring(0, 8));
			dto.setBizDate(sim.format(SysInfoContext.getSysDate()).substring(0,8));
			dto.setCurrency(DataBaseConstants_PAY.T_CORE_CCY_CNY);
			dto.setAmount(dto.getTotalFee());


			dto.setTrantype(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);
			dto.setSetAccount(dto.getThirdTransAcctNo());// 收款方  中金往来账户
			dto.setBankCardNo(stlAcctNo);// 付款方   商户结算账户


			//中金代付接口参数准备
//			dto.setPaymentAccountName(parmPo.getParmName());// 中金账号名称
//			dto.setPaymentAccountType(DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);// 中金备用金类型
//			dto.setPaymentAccountNumber(parmPo.getParmValue());// 中金备用账号

			/*if(DataBaseConstants_PAY.ACCT_TYPE_PERSON.equals(dto.getAccountType())){//个人代付
				dto.setBankId(dto.getLogoId());
			}else if (DataBaseConstants_PAY.ACCT_TYPE_BUSINESS.equals(dto.getAccountType())){//企业代付
				dto.setCupBankName("");
				dto.setCnapsBankNo("");
				dto.setCardBinType(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT);
			}*/




			if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(dto.getAccountType())){
				dto.setCupBankName("");
				dto.setCnapsBankNo("");

			}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(dto.getAccountType())
					||DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getAccountType())){
				dto.setBankId(dto.getLogoId());
			}
			//dto.setCardBinType(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT);
//			dto.setAccountType(dto.getAcctType());
			dto.setCertName(dto.getAcctName());
			dto.setVbindAcctNo(dto.getAcctNo());
			dto.setPhoneNumber(dto.getMobile());
			dto.setOrderDes("银联代付");


			//该接口不会返回处理时间，固我们自己获取当前时间
			dto.setBankTxTime(DateUtil.format(dto.getSysTime(),DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1));
		}
		return dto;
	}

}
