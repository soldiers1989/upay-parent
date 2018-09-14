package com.upay.busi.mer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.MerApplyDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.mer.MerApplyBookPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 二级商户维护
 * 
 * @author yanzixiong
 * @version 创建时间：2016年8月15日14:25:09
 */
public class SecondMerApplyService extends AbstractDipperHandler<MerApplyDto> {

    @Resource
    private IDaoService daoService;
    @Autowired
    private ISequenceService sequenceService;


    @Override
    public MerApplyDto execute(MerApplyDto dto, Message msg) throws Exception {
        Date now = SysInfoContext.getSysDate();
        String merName = dto.getMerName();
        String contact = dto.getContact();
        String contactTel = dto.getContactTel();
        String contactMobile = dto.getContactMobile();
        String contactEmail = dto.getContactEmail();
        String merTel = dto.getMerTel();
        String websiteScop = dto.getWebsiteScop();
        String companyName = dto.getCompanyName();
        String egalPersonName = dto.getEgalPersonName();
        String egalPersonIdType = dto.getEgalPersonIdType();
        String egalPersonIdNo = dto.getEgalPersonIdNo();
        String companyIdType = dto.getCompanyIdType();
        String companyIdNo = dto.getCompanyIdNo();
        String orgDeptNo = dto.getOrgDeptNo();
        String busiLicenseId = dto.getBusiLicenseId();
        String userId = dto.getUserId();
        String email = dto.getEmail();
        String aliasName = dto.getAliasName();
        String userName = dto.getUserName();

        if (StringUtils.isBlank(userName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户名");
        }
        if (StringUtils.isBlank(merName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户名称");
        }
        if (StringUtils.isBlank(contact)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "联系人姓名");
        }
        if (StringUtils.isBlank(contactTel)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "联系人电话");
        }
        if (StringUtils.isBlank(contactMobile)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "联系人手机");
        }
        if (StringUtils.isBlank(contactEmail)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "联系人邮件");
        }
        if (StringUtils.isBlank(merTel)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户电话");
        }
        if (StringUtils.isBlank(websiteScop)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "经营范围");
        }
        if (StringUtils.isBlank(companyName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "公司名称");
        }
        if (StringUtils.isBlank(egalPersonName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "法人代表姓名");
        }
        if (StringUtils.isBlank(egalPersonIdType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "法人代表证件类型");
        }
        if (StringUtils.isBlank(egalPersonIdNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "法人代表身份证");
        }
        if (StringUtils.isBlank(companyIdType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "企业证件类型");
        }
        if (StringUtils.isBlank(companyIdNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "企业证件号");
        }
        if (StringUtils.isBlank(orgDeptNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "组织机构代码证号");
        }
        if (StringUtils.isBlank(busiLicenseId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "营业执照");
        }
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户ID");
        }
        if (StringUtils.isBlank(email)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "邮箱");
        }
        if (StringUtils.isBlank(aliasName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户简称");
        }


        // 检查上级商户信息
        Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("userId", userId);
        MerBaseInfoPo merbaseinfopo = new MerBaseInfoPo();
        merbaseinfopo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap);
        if (null == merbaseinfopo){
			ExInfo.throwDipperEx(AppCodeDict.BISMER0003);
        }

        // 检查用户注册信息是否存在
        UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
//     usrRegInfoPo.setComEmail(email);
        usrRegInfoPo.setUserName(userName);
        usrRegInfoPo.setRegType(DataBaseConstants_USR.USER_REG_TYPE_MEMBER);
        usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
         if (usrRegInfoPo == null) {
        	ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, "信息");
        }else{
        	if (DataBaseConstants_USR.MER_LEVER_1.equals(usrRegInfoPo.getMerLevel())) {
            	ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "一级商户不能成为二级商户");
            }else{
            	if (DataBaseConstants_USR.MER_LEVER_2.equals(usrRegInfoPo.getMerLevel())) {
                	ExInfo.throwDipperEx(AppCodeDict.BISACC0000, userName+"己经是二级商户不能重复申请");
                }
            }
        }


       /* if (usrRegInfoPo == null) {
        	ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, "信息");
        }else{
        	if (DataBaseConstants_USR.MER_LEVER_1.equals(usrRegInfoPo.getMerLevel())) {
            	ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "一级商户不能成为二级商户");
            }else{
            	if (DataBaseConstants_USR.MER_LEVER_2.equals(usrRegInfoPo.getMerLevel())) {
                	ExInfo.throwDipperEx(AppCodeDict.BISACC0000, email+"己经是二级商户不能重复申请");
                }
            }
        }*/
      /*  else{
            if (DataBaseConstants_USR.MER_LEVER_1.equals(usrRegInfoPo.getMerLevel())) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "一级商户不能成为二级商户");
            }else{
                if (DataBaseConstants_USR.MER_LEVER_2.equals(usrRegInfoPo.getMerLevel())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0000, email+"己经是二级商户不能重复申请");
                }
            }
        }*/
        
        // 检查虚拟账户号是否存在
//        AccVbookPo accVbookPo = new AccVbookPo();
//        accVbookPo.setUserId(usrRegInfoPo.getUserId());
//
//        accVbookPo = daoService.selectOne(accVbookPo);
//        if (accVbookPo == null) {
//            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "支付平台账户");
//        }
//        String vacctStat = accVbookPo.getVacctStat();
//        if (!DataBaseConstans_ACC.ACC_V_BOOK_VSTAT_NOMAL.equals(vacctStat)) {
//            ExInfo.throwDipperEx(AppCodeDict.BISGNR0021, "账户状态");
//        }
        if ( userId.equals(usrRegInfoPo.getUserId())){
        	ExInfo.throwDipperEx(AppCodeDict.BISMER0027);
        }

        // 在商户表和商户申请表中查找是否有相同userId
        Map<String, Object> whereMap1 = new HashMap<String, Object>();
        whereMap1.put("userId", usrRegInfoPo.getUserId());
        whereMap1.put("parentMerNo", merbaseinfopo.getMerNo());
        MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
        merBaseInfoPo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap1);
        if (null != merBaseInfoPo) {
            ExInfo.throwDipperEx(AppCodeDict.BISMER0002);
        }

        MerApplyBookPo merApplyBookPo = new MerApplyBookPo();
        merApplyBookPo = daoService.selectOne(MerApplyBookPo.class.getName() + ".selectOneMer", whereMap1);
        if (null != merApplyBookPo && !DateBaseConstants_MER.MER_APPLY_STAT_NO.equals(merApplyBookPo.getApplyState())) {
            ExInfo.throwDipperEx(AppCodeDict.BISMER0002);
        }

        MerApplyBookPo mer = new MerApplyBookPo();

        // 获取商户申请编号
        String merApplyNo = sequenceService.generateMerApplyNo();
        dto.setMerApplyNo(merApplyNo);
        mer.setMerApplyNo(merApplyNo);
        mer.setMerName(merName);
        mer.setMerWithoutPwdSign(merbaseinfopo.getMerWithoutPwdSign());
        mer.setPayOpenFlag(merbaseinfopo.getPayOpenFlag());
        mer.setMerBusiType(merbaseinfopo.getMerBusiType());
        mer.setContact(contact);
        mer.setContactTel(contactTel);
        mer.setContactMobile(contactMobile);
        mer.setContactEmail(contactEmail);
        mer.setMerTel(merTel);
        mer.setMerFax(dto.getMerFax());
        mer.setMerAddr(dto.getMerAddr());
        mer.setMerPostalCode(dto.getMerPostalCode());
        mer.setWebsiteCode(dto.getWebsiteCode());
        mer.setWebsiteName(dto.getWebsiteName());
        mer.setWebsiteDomain(dto.getWebsiteDomain());
        mer.setWebsiteScop(websiteScop);
        mer.setCompanyName(companyName);
        mer.setEgalPersonName(egalPersonName);
        mer.setEgalPersonIdType(egalPersonIdType);
        mer.setEgalPersonIdNo(egalPersonIdNo);
        mer.setCompanyIdType(companyIdType);
        mer.setCompanyIdNo(companyIdNo);
        mer.setOrgDeptNo(orgDeptNo);
        mer.setBusiLicenseId(busiLicenseId);
        mer.setWfStatus(DateBaseConstants_MER.MER_WF_STAT);
        mer.setApplyDate(now);
        mer.setApplyState(DateBaseConstants_MER.MER_APPLY_STAT_WAIT);
        mer.setParentMerNo(merbaseinfopo.getMerNo());
        mer.setUserId(usrRegInfoPo.getUserId());
        mer.setAliasName(aliasName);
        if (null != merApplyBookPo && DateBaseConstants_MER.MER_APPLY_STAT_NO.equals(merApplyBookPo.getApplyState())) {
        	mer.setWfStatus(DateBaseConstants_MER.MER_WF_BACK);
        	daoService.update(mer,merApplyBookPo);
        } else {
        	daoService.insert(mer);
        }
        

        return dto;
    }

}
