package com.upay.busi.acc.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.acc.service.dto.QueryRouteByBindCardNoDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.acc.AccBindBookPo;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.pay.PayCardbinInfoPo;
import com.upay.dao.po.pay.PayRouteCtlInfoPo;
import com.upay.dao.po.pay.PayRouteInfoPo;
import com.upay.dao.po.pay.PayRoutePermInfoPo;
import com.upay.dao.po.usr.UsrRegInfoPo;

/*
 * 充值提现时根据卡号查询对应卡BIN和资金通道
 * liu bing
 */

public class QueryRouteByBindCardNoService extends
		AbstractDipperHandler<QueryRouteByBindCardNoDto> {
	private static final Logger logger = LoggerFactory
			.getLogger(QueryRouteByBindCardNoService.class);
	@Resource
	IDaoService daoService;

	@Override
	public QueryRouteByBindCardNoDto execute(QueryRouteByBindCardNoDto dto, Message msg) throws Exception {
		Date sysTime = dto.getSysTime();
		String vbindAcctNo = dto.getVbindAcctNo();// 前端页面传入的绑定卡卡号
		String routeFuncCode = null;// 功能代码
		String transCode = dto.getTransCode();
		String payeeUserName = dto.getPayeeUserName();

		dto.setMachineDate(DateUtil.format(sysTime,
				DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD));
		dto.setMachineTime(DateUtil.format(sysTime,
				DataBaseConstans_ACC.DATE_FORMAT_HHMMSS));
		dto.setBizDate(DateUtil.format(dto.getSysDate(),
				DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD));
		dto.setPayAmount(dto.getTransAmt());// 记交易流水时的交易金额
		dto.setTransDate(DateUtil.format(dto.getSysTime(),
				DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS));
		
		switch (transCode) {
		case DataBaseConstans_ACC.TRANS_CODE_RECHARGE://充值
			routeFuncCode = DataBaseConstants_PAY.ROUTE_FUNC_CODE_RECHARGE;
			dto.setRouteFuncCode(routeFuncCode);// 功能代码 充值
			dto.setOrderLmtTime(0);// 实时 >0表示等待几分
			dto.setTransComments(DataBaseConstans_ACC.TRANS_TYPE_RECHARGE_TEXT);
			dto.setOrderName(DataBaseConstans_ACC.TRANS_TYPE_RECHARGE_TEXT);
			dto.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_REC);
			dto.setOrderDes(DataBaseConstans_ACC.TRANS_TYPE_RECHARGE_TEXT);
			break;

		case DataBaseConstans_ACC.TRANS_CODE_WITHDRAW:// 提现
			routeFuncCode = DataBaseConstants_PAY.ROUTE_FUNC_CODE_WITHDRAW;
			dto.setOrderName(DataBaseConstans_ACC.TRANS_TYPE_WITHDRAW_TEXT);
			dto.setTransComments(DataBaseConstans_ACC.TRANS_TYPE_WITHDRAW_TEXT);
			dto.setOrderLmtTime(0);// 实时 >0表示等待几分
			dto.setRouteFuncCode(routeFuncCode);// 功能代码 // 提现
			dto.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_CAS);
			dto.setOrderDes(DataBaseConstans_ACC.TRANS_TYPE_WITHDRAW_TEXT);
			break;
		case DataBaseConstans_ACC.TRANS_CODE_TRANSFER://转账
			routeFuncCode = DataBaseConstants_PAY.ROUTE_FUNC_CODE_TRANSFER;
			dto.setOrderName(DataBaseConstans_ACC.TRANS_TYPE_TRANSFER_TEXT_OUT);
			dto.setTransComments(DataBaseConstans_ACC.TRANS_TYPE_TRANSFER_TEXT_OUT);
			dto.setOrderLmtTime(0);// 实时 >0表示等待几分
			dto.setRouteFuncCode(routeFuncCode);// 功能代码 // 转账
			dto.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_TRA);
			dto.setOrderDes(DataBaseConstans_ACC.TRANS_TYPE_TRANSFER_TEXT_OUT);
			dto.setPayerUser(dto.getUserId());
			break;
		}
		

		AccVbookPo vbook = new AccVbookPo();
		vbook.setUserId(dto.getUserId());
		vbook = daoService.selectOne(vbook);
		if (null == vbook) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "虚拟账户信息");
		}
		dto.setvAcctNo(vbook.getVacctNo());
		dto.setTransferPayerAcctNo(vbook.getVacctNo());
		//查询核心的信息
		PayRouteInfoPo coreRouteInfo = new PayRouteInfoPo();
		coreRouteInfo.setRouteCode(DataBaseConstans_ACC.ROUTE_CODE_CORE);
		coreRouteInfo = daoService.selectOne(coreRouteInfo);
		//核心
		dto.setCoreAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);//核心资金池账户 清算账户类型
		dto.setCoreBankNo("");//核心银行行号
		dto.setCoreClrAcctNo(coreRouteInfo.getClrAcctNo());//核心待清算账户
		dto.setCoreClrType(coreRouteInfo.getClrType());//核心清算方式
		dto.setCoreName(coreRouteInfo.getRouteName());//核心名称
		dto.setCoreRouteCode(coreRouteInfo.getRouteCode());//核心资金通道
		dto.setCoreTransAcctNo(coreRouteInfo.getTransAcctNo());//核心资金池账户
		dto.setCoreBankName("红塔银行");//核心银行名称
		dto.setCoreUserId(coreRouteInfo.getDefUserId());
		

		if (DataBaseConstans_ACC.TRANS_CODE_TRANSFER.equals(transCode)) {
			String transferComMail = dto.getPayeeComMail();
			String transferMobile = dto.getPayeeMobile();
			if(StringUtils.isBlank(transferMobile)&&StringUtils.isBlank(transferComMail)){
				ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "收款用户信息");
			}
			
			UsrRegInfoPo user=null;
			if(StringUtils.isNotBlank(payeeUserName)){
				user=new UsrRegInfoPo();
				user.setUserName(payeeUserName);
				user=daoService.selectOne(user);
			}
		/*	if(StringUtils.isNotBlank(transferMobile)){
				user=new UsrRegInfoPo();
				user.setMobile(transferMobile);
				user=daoService.selectOne(user);
			}*//*else if(StringUtils.isNotBlank(transferComMail)){
				user=new UsrRegInfoPo();
				user.setComEmail(transferComMail);
				user=daoService.selectOne(user);
			}*/
			
			if(null==user){
				ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "收款用户信息");
			}
			
			if(dto.getUserId().equals(user.getUserId())){
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "不能给本人转账");
			}
			String userId = dto.getUserId();
			dto.setPayerTransferUserId(userId);
			
			AccVbookPo payeeAcct=new AccVbookPo();
			payeeAcct.setUserId(user.getUserId());
			payeeAcct = daoService.selectOne(payeeAcct);
			if(null==payeeAcct){
				//未开通虚拟账户
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"收款方未开通虚拟账户");
			}
			String vacctStat = payeeAcct.getVacctStat();
			if(!DataBaseConstans_ACC.ACC_V_BOOK_VSTAT_NOMAL.equals(vacctStat)){
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"收款方虚拟账户状态不正常");
			}
			String frzStat = payeeAcct.getFrzStat();
			if(DataBaseConstans_ACC.ACC_V_BOOK_FRZSTAT_FROZEN.equals(frzStat)){
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"收款方虚拟账户冻结状态为双向冻结（不收不付）");
			}
			if(DataBaseConstans_ACC.ACC_V_BOOK_FRZSTAT_LOAN.equals(frzStat)){
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"收款方虚拟账户冻结状态为贷方冻结（只付不收）");
			}
			String stopStat = payeeAcct.getStopStat();
			if(DataBaseConstans_ACC.ACC_V_BOOK_STOPSTAT_NO.equals(stopStat)){
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"收款方虚拟账户止付状态为不收不付 ");
			}
			
			//虚拟账户
			dto.setPayeeVacctNo(payeeAcct.getVacctNo());//转账时收款方的虚拟账号
			dto.setPayeeName(payeeAcct.getAcctName());//转账时收款方的姓名
			dto.setPayeeUserId(user.getUserId());//收款人用户ID
			dto.setThisRouteCode(coreRouteInfo.getRouteCode());//平台资金通道
			dto.setThisBankName("红塔E付");//平台银行名称
			dto.setThisBankNo("");//平台银行行号
			dto.setThisAcctType(DataBaseConstans_ACC.ACCT_TYPE_ELECT_ACCT);//账户类型
			dto.setThisClrType(coreRouteInfo.getClrType());//平台清算方式
			
			
			dto.setAccType(DataBaseConstans_ACC.ACCT_TYPE_ELECT_ACCT);// 手续费需要的账户类型 设置为虚拟账户
			dto.setRouteCode(DataBaseConstans_ACC.ROUTE_CODE_CORE);//资金通道为核心
			
			return dto;
		} else {
			if (StringUtils.isBlank(vbindAcctNo)) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "充值卡号");
			}
			// 充值提现时参数准备
			AccBindBookPo bindBook = new AccBindBookPo();
			bindBook.setVbindAcctNo(vbindAcctNo);
			bindBook.setVacctNo(vbook.getVacctNo());
			bindBook.setBindStat(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_BIND);// 己激活的卡号
			bindBook = daoService.selectOne(bindBook);
			if (bindBook != null) {
				// 借记卡不能做充值提现
				if (DataBaseConstans_ACC.ACCT_TYPE_CREDIT_CARD.equals(bindBook
						.getBindAcctType())) {
					ExInfo.throwDipperEx(AppCodeDict.BISACC0031);
				}

				dto.setReserveMobile(bindBook.getReserveMobile());// 他们提现充值可能需要预留手机号
				
				String bindStat = bindBook.getBindStat();
				/** 账户绑卡状态:1已绑定 */
				if (bindStat.equals(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_BIND)) {
					String cardBin = bindBook.getCardBin();
					// 获取资金通道
					getRouteCode(dto, cardBin, routeFuncCode);
					
					PayRouteInfoPo payRoute = new PayRouteInfoPo();
					payRoute.setRouteCode(dto.getRouteCode());
					payRoute = daoService.selectOne(payRoute);

					PayCardbinInfoPo cardbinInfo = new PayCardbinInfoPo();
					cardbinInfo.setCardBin(cardBin);
					cardbinInfo = daoService.selectOne(cardbinInfo);

					if (payRoute != null && null != cardbinInfo) {

						dto.setCardBin(cardBin);
						dto.setAccType(DataBaseConstans_ACC.ACCT_TYPE_ELECT_ACCT);// 手续费需要的账户类型    设置为虚拟账户
						String routeCode = payRoute.getRouteCode();
						
						if (DataBaseConstans_ACC.ROUTE_CODE_CORE.equals(routeCode)) {
							dto.setRouteType(DataBaseConstans_ACC.ROUTE_CODE_CORE);
						}else if(DataBaseConstans_ACC.ROUTE_CODE_ZJ.equals(routeCode)){
							// 生成中金流水 获取绑定卡时中金的流水号
							dto.setTxSNBinding(bindBook.getRemark1());
							
							dto.setRouteType(DataBaseConstans_ACC.ROUTE_CODE_ZJ);
							
							//提现时代付接口查询红塔E付在中金开的备用金账户
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
								dto.setBankId(cardbinInfo.getLogoId());// 中金提现需要银行ID号
							}
						}
						
						dto.setThirdTransAcctNo(payRoute.getTransAcctNo());//第三方往来户
						dto.setThirdRouteCode(payRoute.getRouteCode());//第三方资金通道
						dto.setThirdBankName(payRoute.getRouteName());//第三方银行名称
						dto.setThirdBankNo("");//第三方银行行号
						dto.setThirdAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);//第三方心资金池账户 清算账户类型
						dto.setThirdClrType(payRoute.getClrType());//第三方清算方式
						dto.setThirdName(payRoute.getRouteName());//第三方名称
						dto.setThirdUserId(payRoute.getDefUserId());//第三方用户ID
						
						
						dto.setBindAcctBankName(cardbinInfo.getCupBankName());//绑定卡银行名称
						dto.setBindAcctBankNo(cardbinInfo.getCupBankNo());//绑定卡银行号
						dto.setBindRouteCode(payRoute.getRouteCode());//绑定卡的资金通道
						dto.setBindOrgName(cardbinInfo.getCupBankName());//绑定卡银行机构名称
						dto.setBindAcctType(bindBook.getBindAcctType());//绑定卡的类型  11借记卡  12贷记卡
						dto.setBindUserId(dto.getUserId());//绑定卡的userID
						dto.setBindName(dto.getCertName());//绑卡人姓名
						
					} else {
						ExInfo.throwDipperEx(AppCodeDict.BISACC0001,
								"卡号对应的资金通道");
					}
				} else {
					/** 账户绑卡状态:0待激活 */
					if (bindStat
							.equals(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_UNACTIVE)) {
						ExInfo.throwDipperEx(AppCodeDict.BISACC0009);
					}
					/** 账户绑卡状态:2过期失效 */
					if (bindStat
							.equals(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_UNACTIVE)) {
						ExInfo.throwDipperEx(AppCodeDict.BISACC0010);
					}
					/** 账户绑卡状态:3解除绑定 */
					if (bindStat
							.equals(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_UNACTIVE)) {
						ExInfo.throwDipperEx(AppCodeDict.BISACC0011);
					}
				}
			} else {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "银行卡绑定信息");
			}
			dto.setTransAmt(MoneyUtil.moneyFormat(dto.getTransAmt()));
			return dto;
		}

	}

	/**
	 * 
	 * 根据卡bin 和 功能代码按照优级查找查找资金通道
	 * 
	 * @param dto
	 * @param cardBin
	 * @param funcCode
	 * @return
	 */
	private void getRouteCode(QueryRouteByBindCardNoDto dto, String cardBin,
			String funcCode) {
		PayRouteCtlInfoPo routeCtlInfo = new PayRouteCtlInfoPo();
		routeCtlInfo.setCardBin(cardBin);
		routeCtlInfo.setRouteFuncCode(funcCode);
		routeCtlInfo.addOrder(Order.asc("payPrity"));
		List<PayRouteCtlInfoPo> routeCtlInfoList = daoService
				.selectList(routeCtlInfo);
		if (routeCtlInfoList.size() > 0) {
			for (PayRouteCtlInfoPo routeCtlInfoPo : routeCtlInfoList) {
				String routeCode = routeCtlInfoPo.getRouteCode();
				PayRoutePermInfoPo pmtRoutePermInfoPo = new PayRoutePermInfoPo();
				pmtRoutePermInfoPo.setRouteCode(routeCode);
				pmtRoutePermInfoPo.setRouteFuncCode(funcCode);
				pmtRoutePermInfoPo
						.setRouteStat(DataBaseConstants_PAY.ROUTE_STAT_NORMAL);
				pmtRoutePermInfoPo = daoService.selectOne(pmtRoutePermInfoPo);
				logger.info("资金渠道===" + routeCode + "  " + funcCode
						+ "         1正常");
				if (pmtRoutePermInfoPo != null) {
					SimpleDateFormat simple = new SimpleDateFormat("HHmmss");
					String moeTime = simple.format(new Date());
					if (moeTime.compareTo(pmtRoutePermInfoPo.getStatTime()) >= 0
							&& pmtRoutePermInfoPo.getEndTime().compareTo(
									moeTime) >= 0) {
						dto.setRouteCode(pmtRoutePermInfoPo.getRouteCode());
						break;
					}
				}
			}
		}
		// 资金通道没有 的情况下需要抛错
		if (StringUtils.isBlank(dto.getRouteCode())) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0030, dto.getTransComments());
		}
	}
}
