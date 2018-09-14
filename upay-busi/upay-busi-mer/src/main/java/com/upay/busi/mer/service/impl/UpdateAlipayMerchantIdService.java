package com.upay.busi.mer.service.impl;

import java.sql.Blob;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.UpdateAlipayMerchantIdDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;
/**
 * 更新支付宝返回的商户编号到商户表
 * @author yhy
 *
 */
public class UpdateAlipayMerchantIdService extends AbstractDipperHandler<UpdateAlipayMerchantIdDto> {
	private static final Logger LOG = LoggerFactory.getLogger(UpdateAlipayMerchantIdService.class);
	
    @Resource
    IDaoService daoService;
    
    @Override
    public UpdateAlipayMerchantIdDto execute(UpdateAlipayMerchantIdDto dto, Message msg)
            throws Exception {
    	String merNo = dto.getMerNo();
       	String subMchId = dto.getSubMerchantId();
       	byte[] alipayUpdateParam = dto.getAlipayUpdateParam();
        Map<String, Object> deserialize =  (Map<String, Object>) SerializationUtils.deserialize(alipayUpdateParam);
        LOG.info("转化后的参数：："+deserialize);
        if(StringUtils.isBlank(merNo)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户号");
        }
        if(StringUtils.isBlank(subMchId)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户微信识别码");
        }
        
        MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
        merBaseInfoPo.setMerNo(merNo);
        MerBaseInfoPo merBaseInfo = new MerBaseInfoPo();
        merBaseInfo.setAlipayMerchantId(subMchId);
        merBaseInfo.setAlipayMerupdateParam(alipayUpdateParam);
        daoService.update(merBaseInfo, merBaseInfoPo);
               	
        return dto;
    }
}
