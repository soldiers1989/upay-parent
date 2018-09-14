package com.upay.busi.mer.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.MerStatChkDto;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;


/**
 * 商户状态检查
 *
 * @author yanzixiong
 * @version 创建时间：2016年8月18日08:48:59
 */
public class MerStatChkService extends AbstractDipperHandler<MerStatChkDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public MerStatChkDto execute(MerStatChkDto dto, Message msg) throws Exception {
        String userId = dto.getUserId();
        String merNo = dto.getMerNo();
        if (StringUtils.isBlank(userId) && StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户ID或者商户号");
        }

        // 检查商户状态
        MerBaseInfoPo merbaseinfopo = new MerBaseInfoPo();
        if (merNo != null && !StringUtils.isBlank(merNo)) {
            merbaseinfopo.setMerNo(merNo);
            merbaseinfopo = daoService.selectOne(merbaseinfopo);
            if (null == merbaseinfopo) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "一级商户");
            }
        } else if (userId != null && !StringUtils.isBlank(userId)) {
//            Map<String, Object> whereMap = new HashMap<String, Object>();
//            whereMap.put("userId", userId);
//            merbaseinfopo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap);
            merbaseinfopo=new MerBaseInfoPo(); 
            merbaseinfopo.setUserId(userId);
            merbaseinfopo = daoService.selectOne(merbaseinfopo);
            if (null == merbaseinfopo) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "商户");
            }
        }
        if(StringUtils.isNotBlank(merbaseinfopo.getQrMer())){
        	dto.setQrMerId(merbaseinfopo.getQrMer());
        }
        dto.setPrimaryMerName(merbaseinfopo.getMerName());
        /*
         * if (merbaseinfopo.getParentMerNo() != null){
         * ExInfo.throwDipperEx(AppCodeDict.BISMER0003); }
         */
        if (!DateBaseConstants_MER.MER_STAT_NORMAL.equals(merbaseinfopo.getMerState())) {
            ExInfo.throwDipperEx(AppCodeDict.BISMER0010, merbaseinfopo.getMerNo());
        }
        dto.setMerNo(merbaseinfopo.getMerNo());
        dto.setPromoterDeptNo(merbaseinfopo.getPromoterDeptNo());
        dto.setMerUserId(merbaseinfopo.getUserId());
        if (StringUtils.isNotBlank(dto.getSecMerNo())) {
            merbaseinfopo = new MerBaseInfoPo();
            merbaseinfopo.setMerNo(dto.getSecMerNo());
            merbaseinfopo = daoService.selectOne(merbaseinfopo);
            if (merbaseinfopo == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "二级商户");
            }
            if (StringUtils.isBlank(merbaseinfopo.getParentMerNo())) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0024, dto.getSecMerNo(), "上级商户号");
            }
            if (!merbaseinfopo.getParentMerNo().equals(dto.getMerNo())) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0025, dto.getMerNo(), dto.getSecMerNo());
            }
            if (!DateBaseConstants_MER.MER_STAT_NORMAL.equals(merbaseinfopo.getMerState())) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0010, merbaseinfopo.getMerNo());
            }
        }else if(StringUtils.isNotBlank(merbaseinfopo.getParentMerNo())){
        	 merNo = merbaseinfopo.getParentMerNo();
        	 merbaseinfopo = new MerBaseInfoPo();
             merbaseinfopo.setMerNo(merNo);
             merbaseinfopo = daoService.selectOne(merbaseinfopo);
             if (merbaseinfopo == null) {
                 ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "一级商户");
             }
             if (!merNo.equals(merbaseinfopo.getMerNo())) {
                 ExInfo.throwDipperEx(AppCodeDict.BISMER0025, dto.getMerNo(), merbaseinfopo.getMerNo());
             }
             if (!DateBaseConstants_MER.MER_STAT_NORMAL.equals(merbaseinfopo.getMerState())) {
                 ExInfo.throwDipperEx(AppCodeDict.BISMER0010, merbaseinfopo.getMerNo());
             }
        }
        return dto;
    }

}
