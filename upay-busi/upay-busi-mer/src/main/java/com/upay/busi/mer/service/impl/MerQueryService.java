package com.upay.busi.mer.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.MerQueryDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.mer.MerReltypePo;

public class MerQueryService extends AbstractDipperHandler<MerQueryDto>{
	 private static final Logger logger = LoggerFactory.getLogger(MerQueryService.class);
	@Resource
	private IDaoService daoService;
	@Override
	public MerQueryDto execute(MerQueryDto dto, Message arg1) throws Exception {
		String merNo = dto.getMerNo();
		String userId = dto.getUserId();
		MerBaseInfoPo base=new MerBaseInfoPo();
		MerBaseInfoPo merInfo=null;
		if(StringUtils.isBlank(merNo)&&StringUtils.isBlank(userId)){
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "商户号和用户ID不能同时为空");
		}
		
		if(StringUtils.isNotBlank(merNo)){
			base.setMerNo(merNo);
			merInfo=daoService.selectOne(base);
		}else if(StringUtils.isNotBlank(userId)){
			base.setMerNo(userId);
			merInfo=daoService.selectOne(base);
		}
		
		logger.debug("查询完成");
		if(merInfo!=null){
			logger.debug("设置dto值");
			dto.setMerNo(merInfo.getMerNo());
			dto.setMerName(merInfo.getMerName());
			dto.setMerWithoutPwdSign(merInfo.getMerWithoutPwdSign());
			dto.setPayOpenFlag(merInfo.getPayOpenFlag());
			dto.setMerBusiType(merInfo.getMerBusiType());
			dto.setContact(merInfo.getContact());
			dto.setContactTel(merInfo.getContactTel());
			dto.setContactMobile(merInfo.getContactMobile());
			dto.setContactEmail(merInfo.getContactEmail());
			dto.setMerTel(merInfo.getMerTel());
			dto.setMerFax(merInfo.getMerFax());
			dto.setMerAddr(merInfo.getMerAddr());
			dto.setMerPostalCode(merInfo.getMerPostalCode());
			dto.setWebsiteCode(merInfo.getWebsiteCode());
			dto.setWebsiteName(merInfo.getWebsiteName());
			dto.setWebsiteDomain(merInfo.getWebsiteDomain());
			
			String websiteScop = merInfo.getWebsiteScop();
			String websiteScopDesc = getgetWebsiteScopDescByRelTypeId(websiteScop);
			if(StringUtils.isBlank(websiteScopDesc)){
				websiteScopDesc = getgetWebsiteScopByRelTypeName(websiteScop);
				dto.setWebsiteScop(websiteScopDesc);
				dto.setWebsiteScopDesc(websiteScop);
			}else{
				dto.setWebsiteScop(websiteScop);
				dto.setWebsiteScopDesc(websiteScopDesc);
			}
			
			dto.setCompanyName(merInfo.getCompanyName());
			dto.setEgalPersonName(merInfo.getEgalPersonName());
			dto.setEgalPersonIdType(merInfo.getEgalPersonIdType());
			dto.setEgalPersonIdNo(merInfo.getEgalPersonIdNo());
			dto.setCompanyIdType(merInfo.getCompanyIdType());
			dto.setCompanyIdNo(merInfo.getCompanyIdNo());
			dto.setOrgDeptNo(merInfo.getOrgDeptNo());
			/**
             * 静态资源服务器域名
             */
            String imgDomainName = (String) DipperParm.getParmByKey(CmparmConstants.IMG_DOMAIN_NAME);
			dto.setBusiLicenseId(imgDomainName +merInfo.getBusiLicenseId());
			
			dto.setOpenDate(merInfo.getOpenDate());
			dto.setMerState(merInfo.getMerState());
			dto.setParentMerNo(merInfo.getParentMerNo());
			dto.setUserId(merInfo.getUserId());
			dto.setSubMchId(merInfo.getSubMchId());
			dto.setIsEntrust(merInfo.getIsEntrust());
			dto.setExtensionParty(merInfo.getExtensionParty());
			dto.setQrCode(merInfo.getQrCode());
			dto.setQrLimitCount(merInfo.getQrLimitCount());
			dto.setQrValidTime(merInfo.getQrValidTime());
			dto.setQrOrderNo(merInfo.getQrOrderNo());
			dto.setAlipayMerchantId(merInfo.getAlipayMerchantId());
//			dto.setAlipayMerupdateParam(byte[] alipayMerupdateParam);
			dto.setAliasName(merInfo.getAliasName()); 
			if(StringUtils.isNotBlank(merInfo.getParentMerNo())){
				String parentMerNo=merInfo.getParentMerNo();
				
				logger.debug("查询一级商户值");
				base=new MerBaseInfoPo();
				base.setMerNo(parentMerNo);
				merInfo=daoService.selectOne(base);
				if(merInfo!=null){
					dto.setParentMerName(base.getMerName());
				}
			}
			
			dto.setPromoterIphone(merInfo.getPromoterIphone());//业务员手机号
			dto.setPromoterName(merInfo.getPromoterName());//业务员姓名
			dto.setPromoterDeptName(merInfo.getPromoterDeptName());
			dto.setPromoterDeptNo(merInfo.getPromoterDeptNo());
		}else{
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "商户信息");
		}
		return dto;
	}
	
	private String getgetWebsiteScopDescByRelTypeId(String websiteScope){
		MerReltypePo merRelType=new MerReltypePo();
		String websiteScopeDesc=null;
		merRelType.setReltypeId(websiteScope);
		merRelType = daoService.selectOne(merRelType);
		if(null!=merRelType){
			websiteScopeDesc= merRelType.getReltypeName();
		}
		return websiteScopeDesc;
	}
	private String getgetWebsiteScopByRelTypeName(String relTypeName){
		MerReltypePo merRelType=new MerReltypePo();
		String websiteScopeDesc=null;
		merRelType.setReltypeName(relTypeName);
		List<MerReltypePo> merRelTypeList = daoService.selectList(merRelType);
		if(null!=merRelTypeList&&merRelTypeList.size()>0){
			merRelType=merRelTypeList.get(0);
			websiteScopeDesc= merRelType.getReltypeName();
		}
		return websiteScopeDesc;
	}
}
