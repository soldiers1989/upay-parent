package com.upay.pay.test;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.union.bean.body;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/test-*.xml")
public class Pay3001Test  extends BaseTest{
	private Logger logger = LoggerFactory.getLogger(Pay3001Test.class);
	
	@Resource(name = "SI_PAY3001")
	private IDipperHandler<Message> SI_PAY3001;

	@org.junit.Test
   public void test() throws Exception{
       Map<String,Object> headMap=new HashMap<String,Object>();
       
       Map<String,Object> bodyMap=new HashMap<String,Object>();
//       bodyMap.put("transCode", CommonConstants_GNR.TRANS_TYPE_UNIONPAY_RETURN);
       bodyMap.put("routeCode", "0002");
       bodyMap.put("orderId", "20170823000011387958");
       bodyMap.put("queryId", "2017122721001004600200273430");
       bodyMap.put("voucherNum", "2017122721001004600200273430");
        bodyMap.put("respCode", "00");
       bodyMap.put("txnTime", "20170823144100");
       bodyMap.put("txnAmt", "1000");
       Message message =
               MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                   headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                   new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                   Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
       message = SI_PAY3001.handle(message);
       System.out.println(message);
   }
}
