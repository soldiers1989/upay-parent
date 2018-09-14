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
 *冲正
 冲正必须与原始消费在同一天（准确讲是昨日23:00至本日23:00之间）。
 冲正交易，仅用于超时无应答等异常场景，只有发生支付系统超时或者支付结果未知时可调用冲正，
 其他正常支付的订单如果需要实现相通功能，请调用消费撤销或者退货。
 */
public class UnionPayWashedDipperHandlerImplTest extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(UnionPayWashedDipperHandlerImplTest.class);
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
        acpService = (IDipperHandler<Message>) applicationContext.getBean("UnionPayWashedDipperHandler");
        Map<String, Object> contentData = new HashMap<String, Object>();
        contentData.put("merId", "777290058135880");
        contentData.put("qrNo", "6220835931895481436");
        contentData.put("orderNo", "20180116000011332228");
        contentData.put("orderTime", "20180116105245");
        acpService.handle(getMessage(contentData, null));
    }

}
