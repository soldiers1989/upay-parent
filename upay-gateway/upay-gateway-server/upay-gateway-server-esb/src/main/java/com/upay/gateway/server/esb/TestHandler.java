package com.upay.gateway.server.esb;

import java.util.Map;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;


/**
 * 测试用,代替dubbo服务
 * 
 * @author Administrator
 * 
 */
public class TestHandler implements IDipperHandler<Message> {
    @Override
    public Message handle(Message m) throws Exception {
        // TODO Auto-generated method stub
        ((Map<String, Object>) m.getTarget().getBodys()).put("returnCode", "SUCCESS");
        ((Map<String, Object>) m.getTarget().getBodys()).put("returnMsg", "OK");
        System.err.println("调起服务成功...");
        return m;
    }
}
