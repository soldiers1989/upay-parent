/**
 * 
 */
package com.upay.busi.pay.service.impl;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.pay.service.dto.AlipayReqParamInitDto;
import com.upay.busi.pay.service.dto.WebChatChangeInitDto;
import com.upay.commons.dict.AppCodeDict;

/**
 *  支付宝初始化字段
 *
 */
public class AlipayReqParamInitService extends AbstractDipperHandler<AlipayReqParamInitDto> {

    @Override
    public AlipayReqParamInitDto execute(AlipayReqParamInitDto dto, Message msg) throws Exception {
        String transSubSeq = dto.getTransSubSeq();
        String aliasName = dto.getAliasName();
        if(StringUtils.isBlank(transSubSeq)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "流水号");
        }
        if(StringUtils.isBlank(aliasName)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户简称");
        }
        dto.setSubject(aliasName+"-"+transSubSeq);
        return dto;
    }
}
