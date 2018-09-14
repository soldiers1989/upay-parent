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
 *
 */
public class UnionPayMarketingActivityBalanceInquiryDipperHandlerImplTest extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(UnionPayMarketingActivityBalanceInquiryDipperHandlerImplTest.class);
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
        acpService = (IDipperHandler<Message>) applicationContext.getBean("UnionPayMarketingActivityBalanceInquiryDipperHandler");
        Map<String, Object> contentData = new HashMap<String, Object>();
        // --商户订单号
        String orderId ="";
        // TODO:营销活动余额查询  待测试
        contentData.put("orderId", orderId);
        contentData.put("discountId","xxxxxxxxx");    //营销活动编号,请根据实际上送为准
        acpService.handle(getMessage(contentData, null));
    }

}
