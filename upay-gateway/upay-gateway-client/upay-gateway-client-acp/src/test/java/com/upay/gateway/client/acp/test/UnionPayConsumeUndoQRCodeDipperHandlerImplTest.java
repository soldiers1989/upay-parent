package com.upay.gateway.client.acp.test;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *二维码撤销类
 撤销必须与原始消费在同一天（准确讲是前日23:00至本日23:00之间）。
 */
public class UnionPayConsumeUndoQRCodeDipperHandlerImplTest extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(UnionPayConsumeUndoQRCodeDipperHandlerImplTest.class);
    private IDipperHandler<Message> acpService;

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        logger.debug("===================test start!!");


    }

    @After
    public void after() {
        logger.debug("===================test end!!");
    }


    @Test
    public void testQueryByAcp() throws Exception {

    	acpService = (IDipperHandler<Message>) applicationContext.getBean("UnionPayConsumeUndoQRCodeDipperHandler");
        Map<String, Object> contentData = new HashMap<String, Object>();
        contentData.put("qrCode", "https://qr.95516.com/00010000/62211207388589561350978063822872");
        acpService.handle(getMessage(contentData, null));
    }

}
