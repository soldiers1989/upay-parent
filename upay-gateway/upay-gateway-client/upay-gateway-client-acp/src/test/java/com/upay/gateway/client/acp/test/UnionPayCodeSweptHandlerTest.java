package com.upay.gateway.client.acp.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.commons.constants.CommunicationConstants;
import com.upay.commons.util.DateUtil;
import com.upay.gateway.client.acp.util.AcpToolUtil;

/**
 * 二维码消费（被扫）
 */
public class UnionPayCodeSweptHandlerTest extends BaseTest {
	private static final Logger LOG = LoggerFactory.getLogger(UnionPayCodeSweptHandlerTest.class);
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
    public void testCoodeByAcp() throws Exception {

    	acpService = (IDipperHandler<Message>) applicationContext.getBean("SA_CODE_UnionPayCodeSwept");
        Map<String, Object> contentData = new HashMap<String, Object>();

////        String merId = "802290049000180";
//        SimpleDateFormat SIM_YMDHMS=new SimpleDateFormat("yyyyMMddHHmmss");
//        String orderId=SIM_YMDHMS.format(new Date());
     //   contentData.put("bizType", "000000");// M
        contentData.put("primaryMerName", "merName");//商户名称
        contentData.put("txnAmt", "3000");// M金额
        contentData.put("qrMerId", "777290058135880");// M        // 被查询交易的订单号
        contentData.put("qrNo", "6223685882046899894");// M
        contentData.put("currencyCode", "156");//交易币种 156代表人民币
        contentData.put("orderNo", AcpToolUtil.getOrderId());
        // contentData.put("customerNM", "全渠道");// M
        acpService.handle(getMessage(contentData, null));
    }

}
