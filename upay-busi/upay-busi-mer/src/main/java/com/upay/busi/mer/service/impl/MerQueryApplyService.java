package com.upay.busi.mer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.page.QueryResult;
import com.upay.busi.mer.service.dto.MerQueryApplyDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.mer.MerApplyBookPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.mer.MerReltypePo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 一级商户维护二级商户申请信息
 * 
 * @author yanzixiong
 * @version 创建时间：2016年10月26日16:13:48
 */
public class MerQueryApplyService extends AbstractDipperHandler<MerQueryApplyDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public MerQueryApplyDto execute(MerQueryApplyDto dto, Message msg) throws Exception {
        // 用户ID
        String userId = dto.getUserId();
        String merApplyNo = dto.getMerApplyNo();
        String secMerName = dto.getSecMerName();
        String operation = dto.getOperation();
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户ID");
        }
        if (StringUtils.isBlank(operation)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "操作方式");
        }

        Map<String, Object> whereMap1 = new HashMap<String, Object>();
        whereMap1.put("userId", userId);
        MerBaseInfoPo merbaseinfopo = new MerBaseInfoPo();
        merbaseinfopo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap1);
        if (null == merbaseinfopo) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "一级商户");
        }

        if (DateBaseConstants_MER.MER_OPERATION_QUERY.equals(operation)) {
            List<MerApplyBookPo> merApplyBookPoList = null;// 商户申请表
            QueryResult<MerApplyBookPo> merApplyBookPoListResult = null;// 分页查询时
            List<Map<String, Object>> merInfoPoDtoList = new ArrayList<Map<String, Object>>();
            Map<String, Object> whereMap = new HashMap<String, Object>();

            whereMap.put("parentMerNo", merbaseinfopo.getMerNo());
            if (merApplyNo != null) {
                whereMap.put("merApplyNo", merApplyNo);
            }
            if (secMerName != null) {
                whereMap.put("secMerName", secMerName);
            }
            int currentNum = dto.getCurrentNum();
            int pageIndex = dto.getPageIndex();
            // 无分页就正常查询
            if ((0 == currentNum && 0 == pageIndex)) {
                merApplyBookPoList =
                        this.daoService.selectList(MerApplyBookPo.class.getName() + ".selectByFuzzy",
                            whereMap);
            }// 有分页就分页查询
            else {
                merApplyBookPoListResult =
                        daoService.selectQueryResult(MerApplyBookPo.class.getName() + ".selectByFuzzy",
                            whereMap, (pageIndex - 1) * currentNum, currentNum);

                if (null != merApplyBookPoListResult) {
                    merApplyBookPoList = merApplyBookPoListResult.getResultlist();
                }
            }
            if (merApplyBookPoList.size() != 0) {
                String imgDomainName = (String) DipperParm.getParmByKey(CmparmConstants.IMG_DOMAIN_NAME);
                for (int i = 0; i < merApplyBookPoList.size(); i++) {
                    dto.setMerApplyNo(merApplyBookPoList.get(i).getMerApplyNo());
                    dto.setMerName(merApplyBookPoList.get(i).getMerName());
                    dto.setMerWithoutPwdSign(merApplyBookPoList.get(i).getMerWithoutPwdSign());
                    dto.setPayOpenFlag(merApplyBookPoList.get(i).getPayOpenFlag());
                    dto.setMerBusiType(merApplyBookPoList.get(i).getMerBusiType());
                    dto.setContact(merApplyBookPoList.get(i).getContact());
                    dto.setContactTel(merApplyBookPoList.get(i).getContactTel());
                    dto.setContactMobile(merApplyBookPoList.get(i).getContactMobile());
                    dto.setContactEmail(merApplyBookPoList.get(i).getContactEmail());
                    dto.setMerTel(merApplyBookPoList.get(i).getMerTel());
                    dto.setMerFax(merApplyBookPoList.get(i).getMerFax());
                    dto.setMerAddr(merApplyBookPoList.get(i).getMerAddr());
                    dto.setMerPostalCode(merApplyBookPoList.get(i).getMerPostalCode());
                    dto.setWebsiteCode(merApplyBookPoList.get(i).getWebsiteCode());
                    dto.setWebsiteName(merApplyBookPoList.get(i).getWebsiteName());
                    dto.setWebsiteDomain(merApplyBookPoList.get(i).getWebsiteDomain());
                    
                    String websiteScop = merApplyBookPoList.get(i).getWebsiteScop();
                    if(StringUtils.isNotBlank(websiteScop)){
                    	MerReltypePo merReltypePo = new MerReltypePo();
                    	merReltypePo.setReltypeId(websiteScop);
                    	merReltypePo = daoService.selectOne(merReltypePo);
                    	if(merReltypePo != null){
                    		dto.setWebsiteScop(merReltypePo.getReltypeName());
                    	}else{
                    		dto.setWebsiteScop(merApplyBookPoList.get(i).getWebsiteScop());
                    	}
                    }else{
                    	dto.setWebsiteScop(merApplyBookPoList.get(i).getWebsiteScop());
                    }
                    
                    dto.setCompanyName(merApplyBookPoList.get(i).getCompanyName());
                    dto.setEgalPersonName(merApplyBookPoList.get(i).getEgalPersonName());
                    dto.setEgalPersonIdType(merApplyBookPoList.get(i).getEgalPersonIdType());
                    dto.setEgalPersonIdNo(merApplyBookPoList.get(i).getEgalPersonIdNo());
                    dto.setCompanyIdType(merApplyBookPoList.get(i).getCompanyIdType());
                    dto.setCompanyIdNo(merApplyBookPoList.get(i).getCompanyIdNo());
                    dto.setOrgDeptNo(merApplyBookPoList.get(i).getOrgDeptNo());
                    dto.setBusiLicenseId(imgDomainName + merApplyBookPoList.get(i).getBusiLicenseId());
                    dto.setApplyDate(DateUtil.format(merApplyBookPoList.get(i).getApplyDate(),
                        DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
                    dto.setApplyState(merApplyBookPoList.get(i).getApplyState());
                    dto.setAnswerApply(merApplyBookPoList.get(i).getAnswerApply());
                    
                    UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
                    usrRegInfoPo.setUserId(merApplyBookPoList.get(i).getUserId());
                    usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
                    if (usrRegInfoPo == null) {
                        ExInfo.throwDipperEx(AppCodeDict.BISMER0018, "注册邮箱");
                    }
                    dto.setEmail(usrRegInfoPo.getComEmail());

                    merInfoPoDtoList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(dto));
                }
            }
            dto.setMerApplyBookList(merInfoPoDtoList);
            if (null != merApplyBookPoListResult) {
                dto.setTotalNum((int) merApplyBookPoListResult.getTotalrecord());// 总记录数
            }
        } else if (DateBaseConstants_MER.MER_OPERATION_DELETE.equals(operation)) {
            if (null == merApplyNo) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户申请编号");
            }
            MerApplyBookPo merApplyBookPo = new MerApplyBookPo();
            merApplyBookPo.setMerApplyNo(merApplyNo);
            merApplyBookPo = daoService.selectOne(merApplyBookPo);
            if (null == merApplyBookPo) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0018, "申请信息");
            }
            if (!DateBaseConstants_MER.MER_APPLY_STAT_NO.equals(merApplyBookPo.getApplyState())) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0022);
            }
            daoService.delete(merApplyBookPo);
        } else {
            Date now = dto.getSysDate();
            if (null == merApplyNo) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户申请编号");
            }
            MerApplyBookPo merApplyBookPo1 = new MerApplyBookPo();
            merApplyBookPo1.setMerApplyNo(merApplyNo);
            merApplyBookPo1 = daoService.selectOne(merApplyBookPo1);
            if (null == merApplyBookPo1) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0018, "申请信息");
            }
            if (!DateBaseConstants_MER.MER_APPLY_STAT_NO.equals(merApplyBookPo1.getApplyState())) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0020);
            }
            MerApplyBookPo merApplyBookPo = new MerApplyBookPo();
            merApplyBookPo.setMerName(dto.getMerName());
            merApplyBookPo.setContact(dto.getContact());
            merApplyBookPo.setContactTel(dto.getContactTel());
            merApplyBookPo.setContactMobile(dto.getContactMobile());
            merApplyBookPo.setContactEmail(dto.getContactEmail());
            merApplyBookPo.setMerTel(dto.getMerTel());
            merApplyBookPo.setMerFax(dto.getMerFax());
            merApplyBookPo.setMerAddr(dto.getMerAddr());
            merApplyBookPo.setMerPostalCode(dto.getMerPostalCode());
            merApplyBookPo.setWebsiteCode(dto.getWebsiteCode());
            merApplyBookPo.setWebsiteName(dto.getWebsiteName());
            merApplyBookPo.setWebsiteDomain(dto.getWebsiteDomain());
            merApplyBookPo.setWebsiteScop(dto.getWebsiteScop());
            merApplyBookPo.setCompanyName(dto.getCompanyName());
            merApplyBookPo.setEgalPersonName(dto.getEgalPersonName());
            merApplyBookPo.setEgalPersonIdType(dto.getEgalPersonIdType());
            merApplyBookPo.setEgalPersonIdNo(dto.getEgalPersonIdNo());
            merApplyBookPo.setCompanyIdType(dto.getCompanyIdType());
            merApplyBookPo.setCompanyIdNo(dto.getCompanyIdNo());
            merApplyBookPo.setOrgDeptNo(dto.getOrgDeptNo());
            merApplyBookPo.setBusiLicenseId(dto.getBusiLicenseId());
            merApplyBookPo.setWfStatus(DateBaseConstants_MER.MER_WF_BACK);
            merApplyBookPo.setApplyDate(now);
            merApplyBookPo.setApplyState(DateBaseConstants_MER.MER_APPLY_STAT_WAIT);

            daoService.update(merApplyBookPo, merApplyBookPo1);
        }
        return dto;
    }
}
