package com.upay.busi.mer.service.impl;

import java.util.ArrayList;
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
import com.upay.busi.mer.service.dto.MerQuerySecondDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.mer.MerReltypePo;


/**
 * 一级商户查询二级商户基本信息
 * 
 * @author yanzixiong
 * @version 创建时间：2016年8月16日08:31:15
 */
public class MerQuerySecondService extends AbstractDipperHandler<MerQuerySecondDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public MerQuerySecondDto execute(MerQuerySecondDto dto, Message msg) throws Exception {
        // 用户ID
        String userId = dto.getUserId();
        String secMerNo = dto.getSecMerNo();
        String secMerName = dto.getSecMerName();
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户ID");
        }
        int currentNum = dto.getCurrentNum();
        int pageIndex = dto.getPageIndex();
        List<MerBaseInfoPo> merBaseInfoPoList = null;// 商户基本表
        QueryResult<MerBaseInfoPo> merBaseInfoPoListResult = null;// 分页查询时
        List<Map<String, Object>> merInfoPoDtoList = new ArrayList<Map<String, Object>>();
//        Map<String, Object> whereMap = new HashMap<String, Object>();

//        Map<String, Object> whereMap1 = new HashMap<String, Object>();
//        whereMap1.put("userId", userId);
        MerBaseInfoPo merbaseinfopo = new MerBaseInfoPo();
        merbaseinfopo.setUserId(userId);
//        merbaseinfopo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap1);
        merbaseinfopo = daoService.selectOne(merbaseinfopo);
        if (null == merbaseinfopo) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "一级商户");
        }
        String parentMerNo = merbaseinfopo.getParentMerNo();
        String merNo= merbaseinfopo.getMerNo();
        
        merbaseinfopo=null;
        if(StringUtils.isNotBlank(secMerNo)||StringUtils.isNotBlank(secMerName)){
        	merbaseinfopo=new MerBaseInfoPo();
        	merbaseinfopo.setParentMerNo(merNo);
        	merbaseinfopo.setMerName(secMerName);
        	merbaseinfopo.setMerNo(secMerNo);
        }else{
        	//查询所有子商户信息
        	merbaseinfopo=new MerBaseInfoPo();
        	merbaseinfopo.setParentMerNo(merNo);
        }
        
        if(merbaseinfopo!=null){
            // 无分页就正常查询
            if ((0 == currentNum && 0 == pageIndex)) {
                merBaseInfoPoList =daoService.selectList(merbaseinfopo);;
            }// 有分页就分页查询
            else {
                merBaseInfoPoListResult =
                        daoService.selectQueryResult(merbaseinfopo,
                            (pageIndex - 1) * currentNum, currentNum);

                if (null != merBaseInfoPoListResult) {
                    merBaseInfoPoList = merBaseInfoPoListResult.getResultlist();
                }
            }
        }
        
        if (merBaseInfoPoList!=null&&merBaseInfoPoList.size()>0) {
            String imgDomainName = (String) DipperParm.getParmByKey(CmparmConstants.IMG_DOMAIN_NAME);
            for (int i = 0; i < merBaseInfoPoList.size(); i++) {
                dto.setMerNo(merBaseInfoPoList.get(i).getMerNo());
                dto.setMerName(merBaseInfoPoList.get(i).getMerName());
                dto.setMerWithoutPwdSign(merBaseInfoPoList.get(i).getMerWithoutPwdSign());
                dto.setPayOpenFlag(merBaseInfoPoList.get(i).getPayOpenFlag());
                dto.setMerBusiType(merBaseInfoPoList.get(i).getMerBusiType());
                dto.setContact(merBaseInfoPoList.get(i).getContact());
                dto.setContactTel(merBaseInfoPoList.get(i).getContactTel());
                dto.setContactMobile(merBaseInfoPoList.get(i).getContactMobile());
                dto.setContactEmail(merBaseInfoPoList.get(i).getContactEmail());
                dto.setMerTel(merBaseInfoPoList.get(i).getMerTel());
                dto.setMerFax(merBaseInfoPoList.get(i).getMerFax());
                dto.setMerAddr(merBaseInfoPoList.get(i).getMerAddr());
                dto.setMerPostalCode(merBaseInfoPoList.get(i).getMerPostalCode());
                dto.setWebsiteCode(merBaseInfoPoList.get(i).getWebsiteCode());
                dto.setWebsiteName(merBaseInfoPoList.get(i).getWebsiteName());
                dto.setWebsiteDomain(merBaseInfoPoList.get(i).getWebsiteDomain());
                
                String websiteScop = merBaseInfoPoList.get(i).getWebsiteScop();
                if(StringUtils.isNotBlank(websiteScop)){
                	MerReltypePo merReltypePo = new MerReltypePo();
                	merReltypePo.setReltypeId(websiteScop);
                	merReltypePo = daoService.selectOne(merReltypePo);
                	if(merReltypePo != null){
                		dto.setWebsiteScop(merReltypePo.getReltypeName());
                	}else{
                		dto.setWebsiteScop(merBaseInfoPoList.get(i).getWebsiteScop());
                	}
                }else{
                	dto.setWebsiteScop(merBaseInfoPoList.get(i).getWebsiteScop());
                }

                dto.setCompanyName(merBaseInfoPoList.get(i).getCompanyName());
                dto.setEgalPersonName(merBaseInfoPoList.get(i).getEgalPersonName());
                dto.setEgalPersonIdType(merBaseInfoPoList.get(i).getEgalPersonIdType());
                dto.setEgalPersonIdNo(merBaseInfoPoList.get(i).getEgalPersonIdNo());
                dto.setCompanyIdType(merBaseInfoPoList.get(i).getCompanyIdType());
                dto.setCompanyIdNo(merBaseInfoPoList.get(i).getCompanyIdNo());
                dto.setOrgDeptNo(merBaseInfoPoList.get(i).getOrgDeptNo());
                dto.setBusiLicenseId(imgDomainName + merBaseInfoPoList.get(i).getBusiLicenseId());
                dto.setOpenDate(DateUtil
                    .format(merBaseInfoPoList.get(i).getOpenDate(), "yyyy-MM-dd"));
                dto.setMerState(merBaseInfoPoList.get(i).getMerState());
                dto.setSubMchId(merBaseInfoPoList.get(i).getSubMchId());

                merInfoPoDtoList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(dto));
            }
        }
        dto.setMerBaseInfoList(merInfoPoDtoList);
        if (null != merBaseInfoPoListResult) {
            dto.setTotalNum((int) merBaseInfoPoListResult.getTotalrecord());// 总记录数
        }

        return dto;
    }

}
