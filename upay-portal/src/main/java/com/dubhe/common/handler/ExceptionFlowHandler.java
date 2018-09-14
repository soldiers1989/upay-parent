package com.dubhe.common.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.dubhe.common.constants.ReqRspConstants;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
/**
 *
 * @author dongweizhao
 *
 */
public class ExceptionFlowHandler implements IDipperHandler<Message> {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionFlowHandler.class);

	@Override
	public Message handle(Message m) throws Exception {
		 if(!ReqRspConstants.RSP_CODE_SUCCESS.equals(m.getFault().getOutCode())){
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put(ReqRspConstants.RSP_KEY,m.getFault().getOutCode());
			 map.put(ReqRspConstants.RSP_MSG,m.getFault().getOutMsg());
			 m.getTarget().setBody(JSON.toJSONString(map, true).getBytes());
		 }
		 LOG.debug(">>FinallyHandler,[{}]",m);
		return m;
	}

}
