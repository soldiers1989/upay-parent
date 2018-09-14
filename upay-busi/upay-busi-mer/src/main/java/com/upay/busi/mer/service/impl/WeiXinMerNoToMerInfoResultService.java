/**
 * 
 */
package com.upay.busi.mer.service.impl;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.mer.service.dto.WeiXinMerNoToMerInfoResultDto;

/**
 * @author zhanggr
 *
 */
public class WeiXinMerNoToMerInfoResultService extends AbstractDipperHandler<WeiXinMerNoToMerInfoResultDto> {

    @Override
    public WeiXinMerNoToMerInfoResultDto execute(WeiXinMerNoToMerInfoResultDto weiXinMerNoToMerInfoResultDto, Message msg)
            throws Exception {
         String resultCode = weiXinMerNoToMerInfoResultDto.getResultCode();
         String resultMsg = weiXinMerNoToMerInfoResultDto.getResultMsg();
         if(StringUtils.isNotBlank(resultCode)&&resultCode.equals("FAIL")){
             ExInfo.throwDipperEx(resultMsg);
         }
        
        return weiXinMerNoToMerInfoResultDto;
    }

}
