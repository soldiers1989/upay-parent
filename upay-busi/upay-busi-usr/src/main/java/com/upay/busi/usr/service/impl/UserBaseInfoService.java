package com.upay.busi.usr.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.UserBaseInfoDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrCertBlackListPo;
import com.upay.dao.po.usr.UsrRegInfoPo;
import com.upay.dao.po.usr.UsrWithoutPwdBookPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 用户基本信息查询
 * 
 * @author shangqiankun
 * @version 创建时间：2016年7月20日 下午4:59:57
 */
public class UserBaseInfoService extends AbstractDipperHandler<UserBaseInfoDto> {

	private final static Logger log = LoggerFactory
			.getLogger(BaseInfoChgService.class);

	@Resource
	private IDaoService daoService;

	@Override
	public UserBaseInfoDto execute(UserBaseInfoDto dto, Message message)
			throws Exception {
		log.info("<----------User Base Info------------->");
		UsrBaseInfoPo base = null;
		// 根据用户id获取用户基本信息
		if (StringUtils.isNotBlank(dto.getUserId())) {
			base = new UsrBaseInfoPo();
			base.setUserId(dto.getUserId());
			base = daoService.selectOne(base);
			// 根据用户手机号获取用户基本信息
		}/* else if (StringUtils.isNotBlank(dto.getMobile())) {
			UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
			usrRegInfoPo.setMobile(dto.getMobile());
			usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
			if (usrRegInfoPo != null) {
				base = new UsrBaseInfoPo();
				base.setUserId(usrRegInfoPo.getUserId());
				base = daoService.selectOne(base);
			}

		}*/
		else if (StringUtils.isNotBlank(dto.getUserName())) {
			UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
			usrRegInfoPo.setUserName(dto.getUserName());
			usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
			if (usrRegInfoPo != null) {
				base = new UsrBaseInfoPo();
				base.setUserId(usrRegInfoPo.getUserId());
				base = daoService.selectOne(base);
			}
			// 根据合作平台账号获取用户基本信息
		}

		else if (StringUtils.isNotBlank(dto.getPlatformType())
				&& StringUtils.isNotBlank(dto.getPlatformUserNo())) {
			UsrWithoutPwdBookPo wi = new UsrWithoutPwdBookPo();
			wi.setUnionPlatType(dto.getPlatformType());
			wi.setUnionPlatNo(dto.getPlatformUserNo());
			wi = daoService.selectOne(wi);
			if (wi != null) {
				base = new UsrBaseInfoPo();
				base.setUserId(wi.getUserId());
				base = daoService.selectOne(base);
			}
			// 根据证件号获取用户基本信息
		} else if (StringUtils.isNotBlank(dto.getCertType())
				&& StringUtils.isNotBlank(dto.getCertNo())) {
			base = new UsrBaseInfoPo();
			base.setCertType(dto.getCertType());
			base.setCertNo(dto.getCertNo());
			base = daoService.selectOne(base);
		} /*else if (StringUtils.isNotBlank(dto.getEmail())) {
			base = new UsrBaseInfoPo();
			base.setEmail(dto.getEmail());
			base = daoService.selectOne(base);
		} */else {
			ExInfo.throwDipperEx("用户个人信息查询参数", AppCodeDict.BISUSR0006);
		}
		if (base != null) {
			String certNo = base.getCertNo();
			if(StringUtils.isNotBlank(certNo)){
				//检查用户信息是否被加入黑名单
				UsrCertBlackListPo blackList = new UsrCertBlackListPo();
				blackList.setCertNo(certNo);
				blackList.setCertType(DataBaseConstants_USR.CERT_TYPE_CERT);
				blackList = daoService.selectOne(blackList);
				if (blackList != null) {
					//log.debug("自然人黑名单标志==============   "+blackList.getBlacklistFlag());
					if(DataBaseConstants_USR.BLACK_LIST_FLAG_YES.equals(blackList.getBlacklistFlag())){
						ExInfo.throwDipperEx(AppCodeDict.BISUSR0051);
					}
				}
			}

			// SimpleDateFormat sim = new
			// SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMD);
			// SimpleDateFormat sim2 = new
			// SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_Y_M_D_H_M_S);
			dto.setUserId(base.getUserId());
			String certType = base.getCertType();
			if (StringUtils.isNotBlank(certType)) {
				// 统一修改为身份证认证
				dto.setCertType(DataBaseConstants_USR.CERT_TYPE_CERT);
			}

			dto.setCertNo(certNo);
			dto.setCertName(base.getCertName());
			dto.setCertFlag(base.getCertExpFlag());
			dto.setCertBegin(base.getCertExpBegin() == null ? null : DateUtil
					.format(base.getCertExpBegin(),
							CommonBaseConstants_USR.DATE_EXPRESSION_YMD));// sim.format(base.getCertExpBegin()));
			dto.setCertEnd(base.getCertExpEnd() == null ? null : DateUtil
					.format(base.getCertExpEnd(),
							CommonBaseConstants_USR.DATE_EXPRESSION_YMD));// sim.format(base.getCertExpEnd()));
			dto.setSex(base.getSex());
			dto.setBirthday(base.getBirthday() == null ? null : DateUtil
					.format(base.getBirthday(),
							CommonBaseConstants_USR.DATE_EXPRESSION_YMD));// sim.format(base.getBirthday()));
			dto.setCountry(base.getCountry());
			dto.setNation(base.getNation());
			dto.setPolitically(base.getBackground());
			dto.setReserveInfo(base.getReligion());
			dto.setMarriage(base.getMarriage());
			dto.setEducation(base.getEduBg());
			dto.setPosition(base.getJob());
			dto.setAddrCode(base.getAddressCode());
			dto.setAddrReal(base.getAddressReal());
			dto.setEmail(base.getEmail());
			dto.setQq(base.getQq());
			dto.setWeixin(base.getWeixin());
			dto.setSign(base.getSign());
			dto.setReserveInfo(base.getPreInfo());
			dto.setECIF(base.getEcifCustNo());
			// dto.setLastUpdate(sim2.format(base.getLastUpdateTime()));
			dto.setLastUpdate(DateUtil.format(base.getLastUpdateTime(),
					CommonBaseConstants_USR.DATE_EXPRESSION_Y_M_D_H_M_S));
		}
		return dto;
	}

}
