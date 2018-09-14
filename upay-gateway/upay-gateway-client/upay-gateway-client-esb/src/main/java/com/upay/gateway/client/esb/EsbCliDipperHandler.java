package com.upay.gateway.client.esb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.presys.cp.client.AbstractClientDipperHandler;
import com.upay.commons.constants.CommonConstants_GNR;
import org.apache.commons.lang.StringUtils;

//import com.upay.dao.ISequenceService;

public class EsbCliDipperHandler extends AbstractClientDipperHandler {
	private String routedate = "";
	private String acctNm;
	private String pswd;
	private String sndTp;
	private String msgTp;

	@Override
    protected void setInitParam(Map<String, Object> init) {
    	init.put("bizSerialNo", init.get("transSubSeq"));
    	routedate=(String) init.get("machineDate");
    	if(StringUtils.isNotBlank((String)init.get("isEsbSms"))){
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
	        Date date = new Date();
	        init.put("transCode", "610006");// 内部交易代码，获取模板用
	        init.put("svcCd", "60120001");
	        init.put("svcScn", "01");
	        init.put("machineDate", dateFormat.format(date));
	        init.put("machineTime", timeFormat.format(date));
	        init.put("fileFlg", "0");
	     	init.put("acctNm", acctNm);
	     	init.put("pswd", pswd);
	     	init.put("sndTp", sndTp);
	     	init.put("msgTp", msgTp);
	     	init.put("sndNo", init.get("phoneNo"));
	     	init.put("sndCntnt", init.get("sendMsg")); 
    	}
    }

	@Override
	protected void doSuccessHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		source.put("routeDate", routedate);
		target.putAll(source);
	}

	@Override
	protected void doFailureHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		// System.out.println(source.toString());
		// source.get("source")
		// respCode
		target.putAll(source);

		doStr(source, target, fault);
	}

	@Override
	protected void doErrorHandle(Map<String, Object> target, Fault fault) {
		target.put(CommonConstants_GNR.ROUTE_TIME_OUT_KEY,
				CommonConstants_GNR.ROUTE_TIME_OUT_VALUE);
	}

	@Override
	public boolean isErrorThrow() {
		return true;
	}

	@Override
	public boolean isFailureThrow() {
		return true;
	}

	public void doStr(Map<String, Object> source, Map<String, Object> target,
			Fault fault) {

		String respCode = (String) target.get("retCd");
		String respMsg = (String) target.get("retMsg");
		fault.setCodeAll(respCode);
		fault.setMsgAll(respMsg);
	}

	public void setAcctNm(String acctNm) {
		this.acctNm = acctNm;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public void setSndTp(String sndTp) {
		this.sndTp = sndTp;
	}

	public void setMsgTp(String msgTp) {
		this.msgTp = msgTp;
	}
}
