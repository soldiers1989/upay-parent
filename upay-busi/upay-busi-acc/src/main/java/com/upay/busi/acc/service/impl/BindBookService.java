package com.upay.busi.acc.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.BindBookDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.acc.AccBindBookPo;

/**
 * 电子账户绑卡
 * 
 * @author: liubing
 * @CreateDate:2015年4月9日
 * 
 */
public class BindBookService extends AbstractDipperHandler<BindBookDto> {
	// private final static Logger log =
	// LoggerFactory.getLogger(BindBookService.class);
	@Resource
	private IDaoService daoService;

	@Override
	public BindBookDto execute(BindBookDto bindBookDto, Message msg)
			throws Exception {
		// 获取电子账户绑卡需要的字段值
		String vAcctNo = bindBookDto.getvAcctNo();// 虚拟账户账号
		String cardBingType = bindBookDto.getCardBinType();// 绑定账户类型
		String eBindBankFlag = bindBookDto.geteBindBankFlag();//
		String bindChnlId = bindBookDto.getChnlId();// 绑定渠道
		String eBindOpenCode = bindBookDto.geteBindOpenCode();// 绑定账户开户行名
		String eBindBankCode = bindBookDto.geteBindBankCode();// 绑定账户行号
		String eBindBankName = bindBookDto.geteBindBankName();// 绑定账户开户行名
		String eBindAcctNo = bindBookDto.geteBindAcctNo();// 电子账户绑定账户账号
		String transCode = bindBookDto.getTransCode();// 电子账户绑定账户账号
		String cnapsBankNo = bindBookDto.getCnapsBankNo();
		String cardBin = bindBookDto.getCardBin();
		String reserveMobile = bindBookDto.getReserveMobile();// 预留手机号
		// 绑定账户银行类别---------第三方渠道传值
		String eBindFlag = bindBookDto.geteBindFlag();// 绑卡方式---------第三方渠道传值
		String thirdAuthChnl = bindBookDto.getThirdAuthChnl();// 第三方鉴权渠道
		BigDecimal transferVerifyAmt = bindBookDto.getTransferVerifyAmt();// 打款验证金额
		String validDate = bindBookDto.getValidDate();
		String cvn2 = bindBookDto.getCvn2();
		// 判断所需字段是否为空
		if (StringUtils.isBlank(vAcctNo)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "虚拟账户账号");
		}
		if (StringUtils.isBlank(cardBingType)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "绑定账户类型");
		}
		if (StringUtils.isBlank(eBindBankFlag)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "绑定账户银行类别");
		}
		if (StringUtils.isBlank(bindChnlId)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "绑定渠道");
		}
		if (StringUtils.isBlank(eBindBankName)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "绑定账户行名");
		}
		if (StringUtils.isBlank(eBindBankCode)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0012, "绑定账户行号");
		}
		if (StringUtils.isBlank(eBindAcctNo)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0012, "电子账户绑定账户账号");
		}
		if (StringUtils.isBlank(eBindFlag)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0012, "绑卡方式");
		}
		// if (StringUtils.isBlank(cardPwd)) {
		// ExInfo.throwDipperEx(AppCodeDict.BISACC0012, "银行卡密码");
		// }
		if (eBindFlag.equals(DataBaseConstans_ACC.BIND_FLAG_CHNL)) {
			if (StringUtils.isBlank(thirdAuthChnl)) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0012, "第三方鉴权渠道");
			}
		}
		if (DataBaseConstans_ACC.ACCT_TYPE_CREDIT_CARD.equals(cardBingType)) {
			if (StringUtils.isBlank(validDate)) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "信用卡有效期");
			}
			if (StringUtils.isBlank(cvn2)) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "信用卡安全码");
			}
		} else {
			validDate = null;
			cvn2 = null;
		}

		boolean bindAcctFlag = false;
		boolean daoFlag = false;// 数据库操作标志
		/* 第三方鉴权绑定变更卡 */
		if (transCode.equals(DataBaseConstans_ACC.TRANS_CODE_CHANG_CARD)) {
			bindAcctFlag = true;
		}

		// 创建电子账户绑卡登记簿实例 关于绑定卡失败，再次换个卡进行绑定的情况？
		AccBindBookPo accBindBookPo = new AccBindBookPo();
		accBindBookPo.setVacctNo(vAcctNo);
		accBindBookPo.setVbindAcctNo(eBindAcctNo);
		accBindBookPo.setCardBin(cardBin);// 卡BIN是非空字段
		accBindBookPo
				.setBindStat(DataBaseConstans_ACC.BIND_STAT_BINDED_TOACTIVATED);
		accBindBookPo = daoService.selectOne(accBindBookPo);
		// 存在已绑定的待激活账户
		if (null == accBindBookPo) {
			accBindBookPo = new AccBindBookPo();
			accBindBookPo.setVacctNo(vAcctNo);
			daoFlag = true;
		}
		// 根据绑卡方式进行开户：当开户原因为1时，eBindFlag为：1：第三方鉴权2：
		if (eBindFlag.equals(DataBaseConstans_ACC.BIND_FLAG_CHNL)) {
			// 第三方鉴权渠道
			accBindBookPo.setThirdAuthChnl(thirdAuthChnl);
		} else if (eBindFlag.equals(DataBaseConstans_ACC.BIND_FLAG_VERIFYAMT)) {
			// 打款验证金额
			accBindBookPo.setTransferVerifyAmt(transferVerifyAmt);
		}
		// 将绑卡登记簿所需数据传入所需实例中
		accBindBookPo.setVbindAcctNo(eBindAcctNo);// 绑定的银行账号

		// 当为是中金借记卡的时候，需要传入绑定的流水号      如果是他行信用卡需要 第一次支付成功再更新绑定流水号
		if (DataBaseConstans_ACC.BIND_BANK_FLAG_OTHER.equals(eBindBankFlag)
				&& DataBaseConstans_ACC.ACCT_TYPE_DEBIT_CARD
						.equals(cardBingType)) {
			accBindBookPo.setRemark1(bindBookDto.getTxSNBinding());// 中金绑卡需要传入绑定流水号
		}

		accBindBookPo.setReserveMobile(reserveMobile);
		accBindBookPo.setCardBin(cardBin);
		accBindBookPo.setVbindBankCode(eBindBankCode);
		accBindBookPo.setBindTime(bindBookDto.getSysTime());
		accBindBookPo.setBindChnlId(bindChnlId);
		accBindBookPo.setVbindFlag(eBindFlag);// 绑卡方式--------------------第三方渠道传值
		accBindBookPo
				.setDefaultFlag(DataBaseConstans_ACC.BIND_DEFAULT_FLAG_YES);// 默认绑卡标志???

		accBindBookPo.setBindAcctType(cardBingType);
		accBindBookPo.setValidDate(validDate);
		accBindBookPo.setCvn2(cvn2);
		accBindBookPo.setVbindOpenCode(cnapsBankNo);
		accBindBookPo.setVbindBankFlag(eBindBankFlag);
		// 对应的param-value值，打款验证期限
		if (transCode.equals(DataBaseConstans_ACC.TRANS_CODE_AMT_OPEN_CARD)
				|| transCode
						.equals(DataBaseConstans_ACC.TRANS_CODE_AMT_CHANG_CARD)) {
			BigDecimal dateNum = (BigDecimal) DipperParm
					.getParmByKey("TRANSFER_VERIFY_DATE");
			Date transferVerifyDate = DateUtil.add(bindBookDto.getSysDate(),
					Calendar.DATE, Integer.parseInt(dateNum.toString()));
			accBindBookPo.setTransferVerifyDate(transferVerifyDate);
		}
		accBindBookPo.setVbindBankName(eBindBankName);// 绑卡行名
		// accBindBookPo.setVbindOpenCode(eBindOpenCode);// 电子账户绑定账户开户行名
		if (!bindAcctFlag) {
			accBindBookPo
					.setBindStat(DataBaseConstans_ACC.BIND_STAT_BINDED_TOACTIVATED);// 绑定状态,绑定待激活-0
		} else {
			// accBindBookPo.setBindStat(DataBaseConstans_ACC.BIND_STAT_BINDED_ACTIVATED);//
			// 绑定状态,绑定激活-1
			accBindBookPo
					.setBindStat(DataBaseConstans_ACC.BIND_STAT_BINDED_TOACTIVATED);
		}
		accBindBookPo.setUnbindChnlId(bindBookDto.getChnlId());// 解绑渠道即是绑定渠道

		if (daoFlag) {
			// 将用户的电子账户绑卡信息插入到电子账户绑卡登记簿信息表
			accBindBookPo
					.setCardFistPay(DataBaseConstans_ACC.ACC_V_BOOK_BIND_FIRST);
			daoService.insert(accBindBookPo);
		} else {
			// 将用户的电子账户绑卡信息更新到电子账户绑卡登记簿信息表
			accBindBookPo
					.setCardFistPay(DataBaseConstans_ACC.ACC_V_BOOK_BIND_NOFIRST);
			AccBindBookPo whereBindBookPo = new AccBindBookPo();
			whereBindBookPo.setId(accBindBookPo.getId());
			daoService.update(accBindBookPo, whereBindBookPo);
		}

		return bindBookDto;
	}
}
