/**
 * 
 */
package com.upay.gateway.client.sms.client;

import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Fault;
import com.upay.gateway.client.sms.AbstractSmsHttpClientDipperHandler;

/**
 * 短信发送
 * 
 * @author zacks
 * 
 */
public class DefaultSmsClientDipperHandler extends
		AbstractSmsHttpClientDipperHandler {

	@Override
	public boolean isErrorThrow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void setInitParam(Map<String, Object> init) {
		// TODO Auto-generated method stub
		setChannelId("smscli");
	}

	@Override
	protected void doSuccessHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doErrorHandle(Map<String, Object> target, Fault fault) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doFailureHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void receiveHandle(HttpEntity arg0, Message arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected byte[] setEntity(HttpPost arg0, Message arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setHeader(HttpPost arg0, Message arg1) {
		// TODO Auto-generated method stub

	}
}
