package com.dubhe.common.executor.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.dubhe.common.constants.ReqRspConstants;
import com.dubhe.common.executor.ExecutorDTO;
import com.dubhe.common.executor.IExecutor;
import com.dubhe.common.util.G4Utils;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.upay.commons.constants.CommunicationConstants;
import com.upay.commons.constants.CommonConstants_GNR;

/**
 * 访问控制
 * @author freeplato
 *
 */
public class AccessControlExecutor implements IExecutor {

	/**
	 * 白名单手机号
	 */
	private Set<String> whiteListMobiles;
	
	/**
	 * 白名单证件 
	 */
	private Set<String> whiteListCerts;
	
	/**
	 * 访问控制
	 */
	private IDipperHandler<Message> accessControlService;
	
	@Override
	public ExecutorDTO execute(ExecutorDTO commTransationDTO) {

    	String mobile = (String)commTransationDTO.getBody().get(CommunicationConstants.MOBILE);
    	String certNo = (String) commTransationDTO.getBody().get(CommunicationConstants.CERT_NO);
    	String transCode = commTransationDTO.getTransCode();
    	if(CommunicationConstants.TRANS_CODE_REG.equals(transCode) || 
    			CommunicationConstants.TRANS_CODE_WEAK_CERT_AUTH.equals(transCode)){
    		try {
            	Map<String, Object> sysHead = commTransationDTO.getSysHead();
            	String id = G4Utils.getUUID();
            	Message message =
                         MessageFactory.create(id, MessageFactory.createSimpleMessage(sysHead, null), MessageFactory
                             .createSimpleMessage(sysHead, null), FaultFactory.create(
                             CommonConstants_GNR.RSP_CODE_SUCCESS, CommonConstants_GNR.RSP_CODE_SUCCESS_DESC),
                             new LinkedList<Store>());
            	Map<String , Object> map = new HashMap<String, Object>();
            	map.put("whiteListMobiles", whiteListMobiles);
            	map.put("whiteListCerts", whiteListCerts);
            	map.put("mobile", mobile);
            	map.put("certNo", certNo);
            	message.getSource().setBody(map);
                message.getTarget().setBody(map);
            	
                message = accessControlService.handle(message);
                commTransationDTO.setRspCode(message.getFault().getCode());
    			commTransationDTO.setRspMsg(message.getFault().getMsg());
    		}catch (Exception e) {
    			commTransationDTO.setRspCode(ReqRspConstants.RSP_CODE_FAIL);
    			commTransationDTO.setRspMsg(e.getMessage());
    			
    		}
    	}
    	
		return commTransationDTO;
	}

	public void setWhiteListMobiles(Set<String> whiteListMobiles) {
		this.whiteListMobiles = whiteListMobiles;
	}

	public void setWhiteListCerts(Set<String> whiteListCerts) {
		this.whiteListCerts = whiteListCerts;
	}

	public void setAccessControlService(IDipperHandler<Message> accessControlService) {
		this.accessControlService = accessControlService;
	}

}
