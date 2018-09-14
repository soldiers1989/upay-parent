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
 *退货
 */
public class UnionReFoundDipperHandlerImplTest extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(UnionReFoundDipperHandlerImplTest.class);
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
        acpService = (IDipperHandler<Message>) applicationContext.getBean("UnionReFoundDipperHandler");
        Map<String, Object> contentData = new HashMap<String, Object>();
        contentData.put("txnAmt", "1000");
        contentData.put("oriRouteSeq", "20180122315887520793");
//        contentData.put("reqType", "0340000903");//被扫
        contentData.put("reqType", "0150000903");//被扫
        acpService.handle(getMessage(contentData, null));
    }

}
