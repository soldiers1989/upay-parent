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
 *消费撤销类访问次数:264
 撤销必须与原始消费在同一天（准确讲是前日23:00至本日23:00之间），且撤销必须是全金额撤销。
 */
public class UnionPayConsumeUndoDipperHandlerImplTest extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(UnionPayConsumeUndoDipperHandlerImplTest.class);
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

    	acpService = (IDipperHandler<Message>) applicationContext.getBean("UnionPayConsumeUndoDipperHandler");
        Map<String, Object> contentData = new HashMap<String, Object>();
//        contentData.put("origOrderNo", "0350000903");
//        contentData.put("origOrderTime", "20180103000011332029");
        contentData.put("voucherNum", "20180122503927979115");
//        contentData.put("reqType", "0330000903");//被扫
        contentData.put("reqType", "0170000903");//主扫
        acpService.handle(getMessage(contentData, null));
    }

}
