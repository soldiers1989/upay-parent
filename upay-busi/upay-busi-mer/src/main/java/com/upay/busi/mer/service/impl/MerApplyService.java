package com.upay.busi.mer.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.MerApplyDto;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.mer.MerApplyBookPo;
import com.upay.dao.po.mer.MerBaseInfoPo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 特约商户申请
 *
 * @author yanzixiong
 * @version 创建时间：2016年8月15日08:56:15
 */
public class MerApplyService extends AbstractDipperHandler<MerApplyDto> {

    @Resource
    private IDaoService daoService;
    @Autowired
    private ISequenceService sequenceService;


    @Override
    public MerApplyDto execute(MerApplyDto dto, Message msg) throws Exception {
        Date now = SysInfoContext.getSysDate();
        // 检查参数
        String merName = dto.getMerName();
        String merWithoutPwdSign = dto.getMerWithoutPwdSign();
        String payopenflag = dto.getPayopenflag();
        String merBusiType = dto.getMerBusiType();
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
        String aliasName = dto.getAliasName();
        String merNo = dto.getMerNo();
        if (StringUtils.isBlank(merName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户名称");
        }
        if (StringUtils.isBlank(merWithoutPwdSign)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户授权免密标志");
        }
        if (StringUtils.isBlank(payopenflag)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "支付功能开通标志");
        }
        if (StringUtils.isBlank(merBusiType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户业务类型");
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
        if (StringUtils.isBlank(aliasName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户简称");
        }


        MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
        MerApplyBookPo merApplyBookPo = new MerApplyBookPo();
        /*x新增商户时需要检查*/
        if (!StringUtils.isNotBlank(merNo)) {
            // 在商户表和商户申请表中查找是否已经成为一级商户
            Map<String, Object> whereMap = new HashMap<String, Object>();
            whereMap.put("userId", userId);
            merBaseInfoPo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap);
            if (null != merBaseInfoPo) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0001);
            }
            // 在商户表和商户申请表中查找是否有相同userId
            merApplyBookPo = daoService.selectOne(MerApplyBookPo.class.getName() + ".selectOneMer", whereMap);
            if (null != merApplyBookPo && !DateBaseConstants_MER.MER_APPLY_STAT_NO.equals(merApplyBookPo.getApplyState())) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0001);
            }
        }

//        AccVbookPo accVbookPo = new AccVbookPo();
//        accVbookPo.setUserId(userId);
//        accVbookPo = daoService.selectOne(accVbookPo);
//        if (null == accVbookPo) {
//            ExInfo.throwDipperEx(AppCodeDict.BISMER0008);
//        } else if (!DataBaseConstans_ACC.ACC_V_BOOK_VSTAT_NOMAL.equals(accVbookPo.getVacctStat())) {
//            ExInfo.throwDipperEx(AppCodeDict.BISGNR0021, "平台虚拟账户状态");
//        }

        MerApplyBookPo mer = new MerApplyBookPo();

        // 获取商户申请编号
        String merApplyNo = sequenceService.generateMerApplyNo();
        dto.setMerApplyNo(merApplyNo);

        /*更新*/
        if (StringUtils.isNotBlank(merNo)) {
            Map<String, Object> whereMaps = new HashMap<String, Object>();
            whereMaps.put("merNo", merNo);
            MerBaseInfoPo merBaseInfoPoTemp = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMaps);
            mer.setState("11");//表示修改
            BeanUtils.copyProperties(merBaseInfoPoTemp, mer);
            mer.setId(null);
        } else {
            mer.setState("00");//表示新增
        }
        mer.setMerApplyNo(merApplyNo);
        mer.setMerName(merName);
        mer.setMerWithoutPwdSign(merWithoutPwdSign);
        mer.setPayOpenFlag(payopenflag);
        mer.setMerBusiType(merBusiType);
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
        mer.setUserId(dto.getUserId());
        mer.setAliasName(aliasName);
        if(StringUtils.isBlank(mer.getQrcodeUsrAlipay())){
        	
            mer.setQrcodeUsrAlipay(DateBaseConstants_MER.QRCODE_USR_WAY_DIRECT);
        }
        if(StringUtils.isBlank(mer.getQrcodeUsrWechat())){
        	mer.setQrcodeUsrWechat(DateBaseConstants_MER.QRCODE_USR_WAY_DIRECT);
        }
        
        
//        mer.setQrcodeUsrWay(DateBaseConstants_MER.QRCODE_USR_WAY_DIRECT);
        
        //新增业务人员信息
        String promoterIphone = dto.getPromoterIphone();
        String promoterDeptNo=null;
        String promoterDeptName=null;
        if(StringUtils.isNotBlank(promoterIphone)){
        	Map<String, Object> parmMap=new HashMap<String, Object>();
	        parmMap.put("mobile", promoterIphone);
			HashMap<String,Object> deptNo = daoService.selectOne(MerBaseInfoPo.class.getName().concat(".getUnitCodeByMobile"),parmMap);
			if(deptNo!=null&&deptNo.size()>0){
				promoterDeptNo=(String)deptNo.get("UNIT_CODE_");
				promoterDeptName=(String)deptNo.get("UNIT_NAME_");
			}
        }
        
        mer.setPromoterIphone(dto.getPromoterIphone());//业务员电话号码
        mer.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
        mer.setPromoterDeptName(promoterDeptName);
		mer.setPromoterName(dto.getPromoterName());//业务员姓名
        
        
        
        if (null != merApplyBookPo && DateBaseConstants_MER.MER_APPLY_STAT_NO.equals(merApplyBookPo.getApplyState())) {
            mer.setWfStatus(DateBaseConstants_MER.MER_WF_BACK);
            daoService.update(mer, merApplyBookPo);
        } else {
            daoService.insert(mer);
        }

        return dto;
    }

}
