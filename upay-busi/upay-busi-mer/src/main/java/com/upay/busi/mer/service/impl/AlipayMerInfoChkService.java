/**
 * 
 */
package com.upay.busi.mer.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.SerializationUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.AlipayMerInfoChkDto;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;


/**
 * 商户基本信息查询和状态检查
 * 
 * @author 
 * 
 */
public class AlipayMerInfoChkService extends AbstractDipperHandler<AlipayMerInfoChkDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public AlipayMerInfoChkDto execute(AlipayMerInfoChkDto dto, Message message)
            throws Exception {
        String merNo = dto.getMerNo();
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
        }
        String merState = "";
        String payopenflag = "";
        MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
        merBaseInfoPo.setMerNo(merNo);
        merBaseInfoPo = daoService.selectOne(merBaseInfoPo);
        if (merBaseInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "商户:" + merNo);
        } else {
            merState = merBaseInfoPo.getMerState();
            if (DateBaseConstants_MER.MER_STAT_NORMAL.equals(merState)) {
                payopenflag = merBaseInfoPo.getPayOpenFlag();
                if (StringUtils.isNotBlank(payopenflag) && DateBaseConstants_MER.MER_PAYOPENFLAG_CLOSE.equals(payopenflag)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISMER0011, merNo);
                }
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0010, merNo);
            }
            String subMerId = merBaseInfoPo.getAlipayMerchantId();
            if(StringUtils.isBlank(subMerId)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0121, merNo);
            }else{
            	Map<String, Object> deserialize = (Map<String, Object>) SerializationUtils.deserialize(merBaseInfoPo.getAlipayMerupdateParam());
            	
            	dto.setAlipayUpdateParam(merBaseInfoPo.getAlipayMerupdateParam());
            	dto.setSubMerchantId(subMerId);  
            }
        }
        
        return dto;
    }
}
