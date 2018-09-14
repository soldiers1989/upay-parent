package com.upay.gateway.client.acp.test;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件传输接口（对账文件下载）
 */
public class UnionPayFileTransferDipperHandlerImplTest extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(GnrServiceImplTest.class);
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

        acpService = (IDipperHandler<Message>) applicationContext.getBean("UnionPayFileTransferDipperHandler");
       Map<String, Object> contentData = new HashMap<String, Object>();
       acpService.handle(getMessage(contentData, null));
    }

}
