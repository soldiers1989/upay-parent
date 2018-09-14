/**
 * 
 */
package com.upay.busi.pay.service.impl;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.pay.service.dto.WebChatChangeInitDto;
import com.upay.commons.dict.AppCodeDict;

/**
 *  微信初始化字段
 * @author zhanggr
 *
 */
public class WebChatChangeInitService extends AbstractDipperHandler<WebChatChangeInitDto> {

    @Override
    public WebChatChangeInitDto execute(WebChatChangeInitDto webChatChangeInitDto, Message msg) throws Exception {
        String transSubSeq = webChatChangeInitDto.getTransSubSeq();
        String body = webChatChangeInitDto.getBody();
        if(StringUtils.isBlank(transSubSeq)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "流水号");
        }
        if(StringUtils.isBlank(body)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "body字段值");
        }
        webChatChangeInitDto.setBody(body+"-"+transSubSeq);
        return webChatChangeInitDto;
    }
}
