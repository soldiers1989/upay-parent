/**
 * 
 */
package com.dubhe.common.handler.mp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dubhe.common.constants.PayGateConstants;
import com.dubhe.common.util.MD5Utils;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants.Channel;
import com.pactera.dipper.core.utils.Constants.ResponseCode;
import com.pactera.dipper.core.utils.PacketUtil;
import com.pactera.dipper.presys.mp.agilexml.configuration.ConfigurationRegistry;
import com.pactera.dipper.presys.mp.agilexml.configuration.TemplateRegistry;
import com.pactera.dipper.presys.mp.agilexml.model.ConfigDef;
import com.pactera.dipper.presys.mp.agilexml.model.PreviewDef;
import com.pactera.dipper.presys.mp.agilexml.utils.Constants;
import com.pactera.dipper.presys.mp.agilexml.utils.TemplateUtils;
import com.pactera.dipper.utils.common.UUIDGenerator;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;


/**
 * 
 * @author Guo
 * @since 2016-08-09
 */
public class XmlPackHandler implements IDipperHandler<Message> {
    private static final Logger LOG = LoggerFactory.getLogger(XmlPackHandler.class);

    private static final String SIGN_NAME = "sign";
    private static final String SIGN_TYPE = "signType";
    private static final String RSP_CODE = "rspCode";
    private static final String SUCCESS = "000000";

    // 验签密钥
    private String key;
    // 报文中商户号字段名称
    private String merNoName;

    @Resource(name = "DIPPER_REDIS_CLIENT")
    private IDipperCached cacheClient;
    @Resource(name = "SI_MER0018")
    private IDipperHandler<Message> SI_MER0018;
    @Resource(name = "SI_MER0025")
    private IDipperHandler<Message> SI_MER0025;

    @SuppressWarnings("unchecked")
    public Message handle(Message m) throws Exception {
        // 是否时错误返回
        boolean isDataSign = true;
        Message message = m;
        Object payload = message.getTarget().getBodys();
        String outCode = message.getFault().getOutCode();
        final String channelid = message.getChannel();
        final String charset = message.getCharset();

        String trancode = PacketUtil.getTranCodeFromTargetHeaders(message, Channel._TRAN_CODE_NAME);

        LOG.debug("{} [channel={}] pack start!!", Constants.MP_BUNDLE, channelid);

        Map<String, Object> payloadMap = null;
        if (payload instanceof Map) {
            payloadMap = (Map<String, Object>) payload;
        } else {
            throw new IllegalArgumentException(Constants.MP_BUNDLE.concat(" [channel=" + channelid
                    + "] pack payload type error!!"));
        }

        byte[] result = new byte[0];
        boolean bool = false;
        Map<String, ConfigDef> configDefMap =
                ConfigurationRegistry.getInstance().getConfigDefMap().get(channelid);
        for (PreviewDef previewDef : configDefMap.get(Constants.PREVIEW).getPreviewsDef().getPreviewDefs()) {
            if (channelid.equals(previewDef.getId())) {
                payloadMap.put(previewDef.getTrancodename(), trancode);
                LOG.debug("channel[{}], trancode[{}]", channelid, trancode);

                Map<String, String> templates =
                        TemplateRegistry.getInstance().getTemplatesMap().get(channelid);
                if (!templates.containsKey(trancode)) {
                    throw new IllegalArgumentException("请配置[" + trancode + "]对应的模板!!");
                }

                result =
                        new TemplateUtils()
                            .createTemplate(payloadMap, Constants.MAIN_FTL, charset, channelid);
                // 加签
                String dataSign = "";
                String chnlId = (String)payloadMap.get("chnlId");
                boolean isOnlineBank=StringUtils.isNotBlank(chnlId)&&!CommonConstants_GNR.CHNL_ID_ONLINE_BANK.equals(chnlId);
                if (previewDef.isDataSign()) {
                    List<String> list = new ArrayList<>();
                    String body = new String(result, m.getCharset());
                    Document document = DocumentHelper.parseText(body);
                    Element el = document.getRootElement();
                    Iterator<Element> it = el.elementIterator();
                    while (it.hasNext()) {
                        Element next = it.next();
                        if (StringUtils.isNotBlank(next.getText()) && !SIGN_NAME.equals(next.getName())&&!SIGN_TYPE.equals(next.getName())) {
                            StringBuilder keyVal = new StringBuilder();
                            keyVal.append(next.getName()).append("=").append(next.getText());// 拼接key=value
                            list.add(keyVal.toString());
                            LOG.debug(keyVal.toString());
                            //add by yx 增加错误返回时，不需要签名
                            if (StringUtils.isNotBlank(next.getText()) && RSP_CODE.equals(next.getName())){
                                if (!SUCCESS.equals(next.getText())){
                                    isDataSign = false;
                                }
                            }
                        }
                    }
                    if (isDataSign&&isOnlineBank){//本行内网调用不需要加签  06是本行网银
                        // 排序
                        String[] keyVals = new String[list.size()];
                        list.toArray(keyVals);
                        Arrays.sort(keyVals);

                        StringBuilder signBuf = new StringBuilder();
                        for (String keyVal : keyVals) {
                            signBuf.append(keyVal).append("&");
                        }
                        
                        // 取key
                        String merNo = (String) payloadMap.get(getMerNoName());
                        String transCode=(String) payloadMap.get("transCode");
                        String operateFlag=(String) payloadMap.get("operateFlag");
                        String merKey="";
                        //不为商户新增时，要从缓存里存商户密钥
                        if(!(PayGateConstants.ADDORUPDATE_MER_SERVICE_NAME.equals(transCode)&&"A".equals(operateFlag))){
                        	if (StringUtils.isBlank(merNo)) {
                                ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户号");
                            }
                        	merKey = cacheClient.get(merNo);
                        }
                        
                        Map<String, Object> bodys = new HashMap<String, Object>();
                        Map<String, Object> headers = new HashMap<String, Object>();
                        if (StringUtils.isBlank(merKey)) {
                            Message msg = null;
                            Map<String, Object> map=null;
                            //如果交易代码为新增一级商户加上，加解密串使用默认的
                            if(PayGateConstants.ADDORUPDATE_MER_SERVICE_NAME.equals(transCode)&&"A".equals(operateFlag)){
                            	bodys.put("transCode", "SI_MER0025");
                                msg = MessageFactory.create(UUIDGenerator.randomUUID(),
                                                MessageFactory.createSimpleMessageInstance(),
                                                MessageFactory.createSimpleMessage(headers, bodys),
                                                FaultFactory.create(ResponseCode.SUCCESS, "SUCCESS"));
                                msg = SI_MER0025.handle(msg);
                                map = (Map<String, Object>) msg.getTarget().getBodys();
                                if (!ResponseCode.SUCCESS.equals(msg.getFault().getCode())) {
                                    ExInfo.throwDipperEx(AppCodeDict.GWSPAY1013, msg.getFault().getMsg());
                                }
                                merKey = (String) map.get("defaultKey");
                            }else{
                            	bodys.put("transCode", "SI_MER0018");
                                bodys.put("merPlatNo", merNo);
                                msg = MessageFactory.create(UUIDGenerator.randomUUID(),
                                                MessageFactory.createSimpleMessageInstance(),
                                                MessageFactory.createSimpleMessage(headers, bodys),
                                                FaultFactory.create(ResponseCode.SUCCESS, "SUCCESS"));
                                msg = SI_MER0018.handle(msg);
                                map = (Map<String, Object>) msg.getTarget().getBodys();
                                if (!ResponseCode.SUCCESS.equals(msg.getFault().getCode())) {
                                    ExInfo.throwDipperEx(AppCodeDict.GWSPAY1013, msg.getFault().getMsg());
                                }
                                merKey = (String) map.get("merKey");
                            }
                        }

                        LOG.debug(previewDef.getDataSignName()+"merKey==============="+merKey);
                        signBuf.append(previewDef.getDataSignName()).append("=").append(merKey);
                        LOG.debug("返回时需要加签的信息==============="+signBuf.toString());
                        dataSign = MD5Utils.md5Hex(signBuf.toString(), charset);
                        LOG.debug("返回时加签后的字符为==============="+dataSign);
                        payloadMap.put(SIGN_NAME, dataSign);
                        payloadMap.put(SIGN_TYPE, "MD5");
                    }else{
                    	payloadMap.remove(SIGN_NAME);
                    	payloadMap.remove(SIGN_TYPE);
                    }

                }
                
                if(CommonConstants_GNR.CHNL_ID_ONLINE_BANK.equals(chnlId)){//如果渠道是06（本行网银），需要为ESB平台重组报文
                	result=rebuildReturnInfo(payloadMap,outCode);
                }else{
                	result =
                            new TemplateUtils()
                                .createTemplate(payloadMap, Constants.MAIN_FTL, charset, channelid);
                }

                bool = true;
                break;
            }
        }

        if (!bool) {
            throw new IllegalArgumentException("preview中没有配置相应的渠道[" + channelid + "] !!");
        }

        LOG.debug("{} pack after:[{}]", Constants.MP_BUNDLE, new String(result, charset));
        LOG.debug("{} pack end!!", Constants.MP_BUNDLE);
        

        message.getTarget().setBody(result);

        return message;
    }
    
    public byte[] rebuildReturnInfo(Map<String, Object> payloadMap,String outCode){
    	String retStat="S";
    	String retCode=CommonConstants_GNR.TRANS_FAULT_SUCCESS;
    	Date now=new Date();
    	StringBuffer sb=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<service>");
		sb.append("<SYS_HEAD>");
		sb.append("<SvcCd>"+payloadMap.get("SvcCd")+"</SvcCd>");
		sb.append("<SvcScn>"+payloadMap.get("SvcScn")+"</SvcScn>");
		sb.append("<CnsmSysId>EBS</CnsmSysId>");
		sb.append("<PrvdSysId>UPP</PrvdSysId>");
		sb.append("<CnsmSysSeqNo>"+payloadMap.get("CnsmSysSeqNo")+"</CnsmSysSeqNo>");
		sb.append("<PrvdSysSeqNo>UPAY"+DateUtil.format(now, "yyyyMMddhhmmss")+"</PrvdSysSeqNo>");
		sb.append("<SrcSysSeqNo>"+payloadMap.get("SrcSysSeqNo")+"</SrcSysSeqNo>");
		
		sb.append("<TranDt>"+DateUtil.format(now, "yyyyMMdd")+"</TranDt>");
		sb.append("<TranTm>"+DateUtil.format(now, "hhmmss")+"</TranTm>");
		if(!CommonConstants_GNR.TRANS_FAULT_SUCCESS.equals(payloadMap.get("outCode"))&&
				!CommonConstants_GNR.SERVICE_STAT.equals(outCode)){
			retStat="F";
			retCode="UPP000"+outCode;
		}
		sb.append("<TranRetSt>"+retStat+"</TranRetSt>");
		sb.append("<RetMsgArryList>");
		sb.append("<RetMsgArry>");
		sb.append(" <RetCd>"+retCode+"</RetCd>");
		sb.append(" <RetMsg>"+payloadMap.get("outMsg")+"</RetMsg>");
		sb.append("</RetMsgArry>");
		sb.append("</RetMsgArryList>");
		sb.append(" <FileFlg>0</FileFlg>");
		sb.append("</SYS_HEAD>");
		sb.append("<APP_HEAD>");
//		sb.append("<TlrNo>00012545</TlrNo>");
//		sb.append("<BrId>0099</BrId>");
//		sb.append("<TlrPwsd>800800</TlrPwsd>");
//		sb.append("<TlrLvl>1</TlrLvl>");
//		sb.append("<AthrzInd>0</AthrzInd>");
		sb.append("</APP_HEAD>");
		sb.append("</service>");
		LOG.debug("ESB 接口返回{}",sb.toString());
		return sb.toString().getBytes();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

    }


    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public String getMerNoName() {
        return merNoName;
    }


    public void setMerNoName(String merNoName) {
        this.merNoName = merNoName;
    }

}
