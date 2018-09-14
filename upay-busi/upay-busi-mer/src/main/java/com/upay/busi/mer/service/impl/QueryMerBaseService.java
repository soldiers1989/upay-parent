package com.upay.busi.mer.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.QueryMerBaseDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.mer.MerReltypePo;
/**
 * 商户基本信息查询
 * @author yanzixiong
 * @version 创建时间：2016年11月11日09:33:42
 */
public class QueryMerBaseService extends AbstractDipperHandler<QueryMerBaseDto> {

	@Resource
    private IDaoService daoService;
	
	@Override
	public QueryMerBaseDto execute(QueryMerBaseDto dto, Message msg)
			throws Exception {
		String merNo = dto.getMerNo();
		String userId = dto.getUserId();
		if (StringUtils.isBlank(userId)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户ID");
		}
		MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
		if (StringUtils.isNotBlank(merNo)){
			merBaseInfoPo.setMerNo(merNo);
			merBaseInfoPo = daoService.selectOne(merBaseInfoPo);
			if (null == merBaseInfoPo){
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "商户信息");
			}
		} else {
			merBaseInfoPo=new MerBaseInfoPo();
			merBaseInfoPo.setUserId(userId);
//			Map<String, Object> whereMap = new HashMap<String, Object>();
//	        whereMap.put("userId", userId);
//	        merBaseInfoPo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap);
			merBaseInfoPo = daoService.selectOne(merBaseInfoPo);
	        if (null == merBaseInfoPo){
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "商户信息");
			}
		}
		
		dto.setMerNo(merBaseInfoPo.getMerNo());
		dto.setParentMerNo(merBaseInfoPo.getParentMerNo());
		dto.setUsrId(merBaseInfoPo.getUserId());
		dto.setMerName(merBaseInfoPo.getMerName());
		dto.setMerWithoutPwdSign(merBaseInfoPo.getMerWithoutPwdSign());
		dto.setPayOpenFlag(merBaseInfoPo.getPayOpenFlag());
		dto.setMerBusiType(merBaseInfoPo.getMerBusiType());
		dto.setContact(merBaseInfoPo.getContact());
		dto.setContactTel(merBaseInfoPo.getContactTel());
		dto.setContactMobile(merBaseInfoPo.getContactMobile());
		dto.setContactEmail(merBaseInfoPo.getContactEmail());
		dto.setMerTel(merBaseInfoPo.getMerTel());
		dto.setMerFax(merBaseInfoPo.getMerFax());
		dto.setMerAddr(merBaseInfoPo.getMerAddr());
		dto.setMerPostalCode(merBaseInfoPo.getMerPostalCode());
		dto.setWebsiteCode(merBaseInfoPo.getWebsiteCode());
		dto.setWebsiteName(merBaseInfoPo.getWebsiteName());
		dto.setWebsiteDomain(merBaseInfoPo.getWebsiteDomain());
		
		String websiteScop = merBaseInfoPo.getWebsiteScop();
		if(StringUtils.isNotBlank(websiteScop)){
			MerReltypePo merReltypePo = new MerReltypePo();
			merReltypePo.setReltypeId(websiteScop);
			merReltypePo = daoService.selectOne(merReltypePo);
			if(merReltypePo != null){
				dto.setWebsiteScop(merReltypePo.getReltypeName());
			}else{
				dto.setWebsiteScop(merBaseInfoPo.getWebsiteScop());
			}
		}else{
			dto.setWebsiteScop(merBaseInfoPo.getWebsiteScop());
		}
		dto.setCompanyName(merBaseInfoPo.getCompanyName());
		dto.setEgalPersonName(merBaseInfoPo.getEgalPersonName());
		dto.setEgalPersonIdType(merBaseInfoPo.getEgalPersonIdType());
		dto.setEgalPersonIdNo(merBaseInfoPo.getEgalPersonIdNo());
		dto.setCompanyIdType(merBaseInfoPo.getCompanyIdType());
		dto.setCompanyIdNo(merBaseInfoPo.getCompanyIdNo());
		dto.setOrgDeptNo(merBaseInfoPo.getOrgDeptNo());
		dto.setBusiLicenseId(merBaseInfoPo.getBusiLicenseId());
		dto.setOpenDate(DateUtil.format(merBaseInfoPo.getOpenDate(), "yyyy-MM-dd"));
		dto.setMerState(merBaseInfoPo.getMerState());
		dto.setSubMchId(merBaseInfoPo.getSubMchId());
		
		return dto;
	}

}
