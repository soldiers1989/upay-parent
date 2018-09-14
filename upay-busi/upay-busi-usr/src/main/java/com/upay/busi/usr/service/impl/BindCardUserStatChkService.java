package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.BindCardUserStatChkDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrRegInfoPo;

/**
 * 用户绑卡开户，用户状态检查处理
 * @author zhangjianfeng
 * @since 2016/8/9
 */
public class BindCardUserStatChkService extends AbstractDipperHandler<BindCardUserStatChkDto> {
	
	@Resource
	IDaoService daoService;

	@Override
	public BindCardUserStatChkDto execute(BindCardUserStatChkDto dto, Message message) throws Exception {
		String userId = dto.getUserId(); //用户ID
		if(StringUtils.isBlank(userId)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户id");
		}
		String certId = dto.getCertId(); //证件号码
		if(StringUtils.isBlank(certId)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "证件号码");
		}
		String certName = dto.getCertName(); //证件姓名
		if(StringUtils.isBlank(certName)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "姓名");
		}
		String certType = dto.getCertType(); //证件类型现只支持0-身份证
		if(StringUtils.isBlank(certType)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "证件类型");
		}
		if(StringUtils.isNotBlank(certType) && !DataBaseConstants_USR.CERT_TYPE_ID_CARD.equals(certType)) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0023);
		}

		String bindAcctNo = dto.geteBindAcctNo(); //绑定账户账号
		if(StringUtils.isBlank(bindAcctNo)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "银行卡号");
		}
		String bindMobile = dto.geteBindMobile(); //银行预留手机号
		if(StringUtils.isBlank(bindMobile)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "银行预留手机号");
		}
		//查询用户注册信息
		UsrRegInfoPo usrRegInfo = new UsrRegInfoPo();
		usrRegInfo.setUserId(userId);
		usrRegInfo = daoService.selectOne(usrRegInfo);
		//如果查询用户注册信息为空
		if(null == usrRegInfo) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0005,userId); //该用户不存在
		}
		//0-正常；1－待激活；9-注销；
		String userStatus = usrRegInfo.getUserStat();
		if(DataBaseConstants_USR.USER_STAT_UNACTIVATE.equals(userStatus)) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0018); //该用户待激活
		}
		if(DataBaseConstants_USR.USER_STAT_LOGOFF.equals(userStatus)) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0017); //该用户已经注销
		}
		if(DataBaseConstants_USR.CERT_TYPE_ID_CARD.equals(certType)) {
			dto.setCoreCertType(DataBaseConstants_USR.CERT_TYPE_CERT); //核心证件类型 01－身份证
		}
		dto.setUserCertLevel(usrRegInfo.getUserCertLevel());
		dto.setIsCheckmobilePhone(DataBaseConstants_USR.CHECK_MOBILE_PHONE_YES);
		dto.setIsCheckAccountName(DataBaseConstants_USR.CHECK_ACCOUNT_NAME_YES);
		dto.setIsCheckCertificateNo(DataBaseConstants_USR.CHECK_CERTIFICATENO_YES);
		return dto;
	}

}
