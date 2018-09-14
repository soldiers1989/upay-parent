package com.upay.busi.acc.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.BindCoreCardStatChkDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;

/**
 * 绑定账户解绑
 * 
 * @author: liubing
 * @CreateDate:2015年4月10日
 * 
 */
public class BindCoreCardStatChkService extends
		AbstractDipperHandler<BindCoreCardStatChkDto> {
	@Resource
	private IDaoService daoService;
	private final static Logger log = LoggerFactory
			.getLogger(BindCoreCardStatChkService.class);

	@Override
	public BindCoreCardStatChkDto execute(BindCoreCardStatChkDto dto,
			Message msg) throws Exception {
		String bindAcctType = dto.getBindAcctType();
		String cardStat = dto.getCardStat();
		log.info("核心返回的卡状态信息=================" + cardStat + "=========卡类型=="
				+ bindAcctType);

		if (DataBaseConstans_ACC.ACCT_TYPE_DEBIT_CARD.equals(bindAcctType)) {
			checkCoreStat(cardStat);
		} else {
			switch (cardStat) {
			case "T":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "卡己冻结,不能绑卡!");
				break;
			case "C":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "卡己注销,不能绑卡!");
				break;
			case "R":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "卡己挂失,不能绑卡!");
				break;
			case "F":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "该卡存在欺诈行为,不能绑卡!");
				break;
			case "H":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "潜在高风险账户,不能绑卡!");
				break;
			case "J":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "认定损失的账户,不能绑卡!");
				break;
			case "K":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "该账户己破产或死亡,不能绑卡!");
				break;
			case "O":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "该户主己失去联络,不能绑卡!");
				break;
			case "P":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "账户关闭或删除,不能绑卡!");
				break;
			case "Q":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "该卡己到期不续卡,不能绑卡!");
				break;
			case "S":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "卡被盗,不能绑卡!");
				break;
			case "L":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "持卡人申请支付账户,不能绑卡!");
				break;
			case "N":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "该卡未激活,不能绑卡!");
				break;
			case "W":
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "呆账核销账户,不能绑卡!");
				break;
			// case "Y":
			// ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "催收账户,不能绑卡!");
			// break;
			}

			if (!"0000".equals(cardStat)) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "卡状态异常,不能绑卡!");
			}
		}

		return dto;
	}

	// public static void main(String [] args){
	// BindCoreCardStatChkService a=new BindCoreCardStatChkService();
	// a.checkCoreStat("02000000000000000000");
	// }
	private void checkCoreStat(String cardStat) {
		String coreCardStat = "00000000000000000000";
		if (!coreCardStat.equals(cardStat)) {
			for (int i = 1; i < 15; i++) {
			    log.debug(i+"");
				char stat = cardStat.charAt(i - 1);
				switch (i) {
				case 1:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡己注销,不能绑卡!");
						break;
					case '2':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡己挂失销户,不能绑卡!");
						break;
					case '3':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡己作废,不能绑卡!");
						break;
					case '4':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡己损坏销户,不能绑卡!");
						break;
					case '5':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡己申请销户,不能绑卡!");
						break;
					}
					break;
				case 2:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡己口头挂失,不能绑卡!");
						break;
					case '2':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡己书面挂失,不能绑卡!");
						break;
					}
					break;
				case 3:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡密码己口头挂失,不能绑卡!");
						break;
					case '2':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡密码己书面挂失,不能绑卡!");
						break;
					}
					break;
				case 4:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡己冻结,不能绑卡!");
						break;
					}
					break;
				case 5:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡己没收或吞卡,不能绑卡!");
						break;
					}
					break;
				case 6:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"拣拾卡,不能绑卡!");
						break;
					}
					break;
				case 7:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡未初始密码,不能绑卡!");
						break;
					}
					break;
				case 8:
					switch (stat) {
					case '2':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"挂失补卡中,不能绑卡!");
						break;
					case '3':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"正在换卡中,不能绑卡!");
						break;
					case '4':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"银联换卡中,不能绑卡!");
						break;
					case '5':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"损坏换卡中,不能绑卡!");
						break;
					}
					break;
				case 9:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡己锁定,不能绑卡!");
						break;
					}
					break;
				case 10:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"IC卡不可读,不能绑卡!");
						break;
					}
					break;
				case 11:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡己止付,不能绑卡!");
						break;
					}
					break;
				case 12:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"客户止付(主卡客户对附卡的止付),不能绑卡!");
						break;
					}
					break;
				case 13:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"卡己欠费,不能绑卡!");
						break;
					}
					break;
				case 14:
					switch (stat) {
					case '1':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"正常换卡申请中,不能绑卡!");
						break;
					case '2':
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								"挂失补卡申请中,不能绑卡!");
						break;
					}
					break;
				}
			}
		}
	}
}
