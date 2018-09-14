/**
 * 
 */
package com.upay.commons.util;

import java.util.Map;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.upay.commons.constants.CommonConstants_GNR;

/**
 * @author shang
 * 2016年9月19日
 */
public class MessageUtils {
    public static Message createMessage(Map<String,Object> headers,Map<String,Object> bodys,String channel,String format,String charset){
        Message msg=MessageFactory.create(IdGenerateFactory.generateId(), channel, format, charset, 
            MessageFactory.createSimpleMessage(headers, bodys), FaultFactory.create(CommonConstants_GNR.TRANS_SUCCESS, ""));
        return msg;
    }
}
