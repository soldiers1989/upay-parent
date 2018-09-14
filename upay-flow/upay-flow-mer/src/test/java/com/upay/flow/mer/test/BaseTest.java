package com.upay.flow.mer.test;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class BaseTest {
    private Logger logger = LoggerFactory.getLogger(BaseTest.class);


    public Message getMessage(Map<String, Object> bodys, String serviceCode) throws Exception {
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("serviceCode", serviceCode);
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(),
                    MessageFactory.createSimpleMessage(header, bodys),
                    FaultFactory.create(ResponseCode.SUCCESS, "success"), new LinkedList<Store>());

        return message;
    }
}
