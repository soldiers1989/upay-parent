package com.upay.gateway.client.acp.test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.gateway.client.acp.util.AcpToolUtil;

/**
 * 申请二维码（主扫）
 */
public class UnionPayApplyQrCodeDipperHandlerImplTest extends BaseTest {
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

        acpService = (IDipperHandler<Message>) applicationContext.getBean("UnionPayApplyQrCodeDipperHandler");
        Map<String, Object> contentData = new HashMap<String, Object>();
        // --商户订单号
        // M
        contentData.put("merNo", "777290058135880");//商户编号
        contentData.put("limitCount","0");
        contentData.put("txnAmt", "800");
        contentData.put("qrValidTime", BigInteger.valueOf(10*365*24*60*60).toString());
        contentData.put("primaryMerName", "阿斯顿");//商户名称
        contentData.put("orderNo", AcpToolUtil.getOrderId());
        acpService.handle(getMessage(contentData, null));
    }

}
