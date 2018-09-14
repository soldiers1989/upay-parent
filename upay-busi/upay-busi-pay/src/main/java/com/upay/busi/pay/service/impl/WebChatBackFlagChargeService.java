/**
 * 
 */
package com.upay.busi.pay.service.impl;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.pay.service.dto.WebChatBackFlagChargeDto;

/**
 * @author zhanggr
 *
 */
public class WebChatBackFlagChargeService extends AbstractDipperHandler<WebChatBackFlagChargeDto> {

    @Override
    public WebChatBackFlagChargeDto execute(WebChatBackFlagChargeDto webChatBackFlagChargeDto, Message msg) throws Exception {
         String returnCode = webChatBackFlagChargeDto.getReturnCode();
         String resultCode = webChatBackFlagChargeDto.getResultCode();
         if((returnCode!=null&&returnCode.equals("SUCCESS"))&&(resultCode!=null&&resultCode.equals("SUCCESS"))){
             webChatBackFlagChargeDto.setUpdateFlag("1");//成功的时候为1，不更新状态 
         }else{
             webChatBackFlagChargeDto.setUpdateFlag("0");//0：更新
         }
        return null;
    }

}
