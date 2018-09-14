/**
 *
 */
package com.dubhe.common.handler.mp;

import static com.upay.commons.dict.AppCodeDict.GWSPAY2403;
import static com.upay.commons.dict.AppCodeDict.GWSPAY2404;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.dubhe.common.constants.PayGateConstants;
import com.dubhe.common.util.Dom4jUtil;
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
import com.pactera.dipper.dict.utils.CodeUtils;
import com.pactera.dipper.presys.mp.agilexml.configuration.ConfigurationRegistry;
import com.pactera.dipper.presys.mp.agilexml.model.ConfigDef;
import com.pactera.dipper.presys.mp.agilexml.model.HeadFieldDef;
import com.pactera.dipper.presys.mp.agilexml.model.MappingDef;
import com.pactera.dipper.presys.mp.agilexml.model.PreviewDef;
import com.pactera.dipper.presys.mp.agilexml.utils.Constants;
import com.pactera.dipper.presys.mp.agilexml.utils.TemplateUtils;
import com.pactera.dipper.utils.common.UUIDGenerator;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;


/**
 * @author Hing
 * @since 2014年11月4日
 * @modify Guo 支持多商户签名验签
 * @date 2016-11-01
 */
@SuppressWarnings("unchecked")
public class XmlUnpackHandler implements IDipperHandler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(XmlUnpackHandler.class);

    private static final String KEY_NAME = "key";
    private static final String SIGN_NAME = "sign";

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

    @Override
    public Message handle(Message m) throws Exception {
        Message message = m;
        Object payload = message.getTarget().getBodys();
        final String channelid = message.getChannel();
        final String charset = message.getCharset();
        logger.debug("{} [channel={}] unpack start!!", Constants.MP_BUNDLE, channelid);

        byte[] payloadByte = null;
        if (payload instanceof byte[]) {
            payloadByte = (byte[]) payload;
        } else {
            throw new IllegalArgumentException(Constants.MP_BUNDLE.concat(" [channel=" + channelid
                    + "] unpack payload type error!!"));
        }

        String trancode = "";
        if (null != message.getTarget().getHeaders().get(Channel._IS_CLIENT)) {
            trancode = PacketUtil.getTranCodeFromTargetHeaders(message, Channel._TRAN_CODE_NAME);
        }
        XMLConfiguration xmlcfg = null;
        boolean bool = false;
        TemplateUtils xmlUtils = new TemplateUtils();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String errCodeName = null;
        String errMsgName = null;
        String errCodeSuc = "";
        boolean isDataSign = false;
        boolean svrflag = false;
        boolean caBool = false;
        boolean bodyheadSpecial = false;
        String sysName = "";
        String concreteSysId = "";
        String dataSignName = "sign";

        try {
            Map<String, ConfigDef> configDefMap =
                    ConfigurationRegistry.getInstance().getConfigDefMap().get(channelid);
            for (PreviewDef previewDef : configDefMap.get(Constants.PREVIEW).getPreviewsDef()
                .getPreviewDefs()) {
                if (channelid.equals(previewDef.getId())) {
                    // dataSignName = previewDef.getDataSignName();
                    // if (StringUtils.isBlank(dataSignName)) {
                    dataSignName = SIGN_NAME;
                    // }
                    isDataSign = previewDef.isDataSign();

                    svrflag = previewDef.isSvrflag();
                    sysName = previewDef.getSysName();
                    concreteSysId = previewDef.getConcreteSysId();

                    errCodeSuc = previewDef.getErrCodeSuc();
                    errCodeName = previewDef.getErrCodeName();
                    errMsgName = previewDef.getErrMsgName();
                    int start = 0;
                    int end = 0;
                    if (previewDef.getUnpackDef() != null) {
                        for (HeadFieldDef headFieldDef : previewDef.getUnpackDef().getHeadFieldDefs()) {
                            if (null == headFieldDef.getLengthid()) {
                                end = start + headFieldDef.getLength();
                            } else {
                                end =
                                        start
                                                + Integer.parseInt((String) resultMap.get(headFieldDef
                                                    .getLengthid()));
                            }
                            resultMap.put(headFieldDef.getId(),
                                new String(ArrayUtils.subarray(payloadByte, start, end)).trim());
                            start = end;
                        }
                    }

                    logger.debug("fix head map[{}]", resultMap);

                    payloadByte = ArrayUtils.subarray(payloadByte, end, payloadByte.length);
                    logger.debug("{}", new String(payloadByte, charset));
                    xmlcfg = xmlUtils.readxml(payloadByte);
                    
                    
                    //根据渠道重组报文
                    String rebuildXml=rebuildXml(xmlcfg);
                    logger.debug("ESB重组报文{}",rebuildXml);
                    if(StringUtils.isNotBlank(rebuildXml)){
                    	InputStream is = new ByteArrayInputStream(rebuildXml.toString().getBytes("utf-8"));
                		xmlcfg = new XMLConfiguration();
                		xmlcfg.load(is);
                    }
//                    xmlUtils.getText(xmlcfg, "outerOrderDate");
                    NodeList document = xmlcfg.getDocument().getElementsByTagName("epay");
                    for(int i=0;i<document.getLength();i++){
                    	Element node = (Element)document.item(i);
                    	System.out.println();
                    	
                    }
                    
                    
                    
                    if (null == message.getTarget().getHeaders().get(Channel._IS_CLIENT)) {
                        trancode = xmlUtils.getText(xmlcfg, previewDef.getXpath());
                        PacketUtil.setValueOfTargetHeaders(message, Channel._TRAN_CODE_NAME, trancode);// 交易码
                    }
                    
                    String chnlId = xmlUtils.getText(xmlcfg, "chnlId");

                    // 获取商户密钥,根据商户号从redis里取
                    String merNo = "";// 商户号
                    String targetSign = "";
                    if (isDataSign&&!CommonConstants_GNR.CHNL_ID_ONLINE_BANK.equals(chnlId)) {  //渠道为03时是本行调用不需要验签
                    	//如果交易码为新增一级商户接口  trancode
                    	if(PayGateConstants.ADDORUPDATE_MER_SERVICE_NAME.equals(trancode)&&"A".equals(xmlcfg.getString("operateFlag"))){
                    		merNo = xmlcfg.getString(getMerNoName());
                    	}else{
                    		// 取出商户号
                            merNo = xmlUtils.getText(xmlcfg, getMerNoName());
                            if (StringUtils.isBlank(merNo)) {
                                ExInfo.throwDipperEx(GWSPAY2404, merNoName);
                            }
                    	}
                        
                        targetSign = xmlUtils.getText(xmlcfg, dataSignName);// 报文中的签名串
                        // 获取签名原始串
                        StringBuilder plainText =
                                Dom4jUtil.getPlainText(new String(payloadByte, charset), dataSignName);
                        

                        if (StringUtils.isBlank(targetSign)) {
                            ExInfo.throwDipperEx(GWSPAY2404, dataSignName);
                        }
                        
                        String merKey="";
                        // 从redis取出商户的密钥
                        if(StringUtils.isNotBlank(merNo)){
                        	merKey = cacheClient.get(merNo);
                        }
                        
                        Map<String, Object> bodys = new HashMap<String, Object>();
                        Map<String, Object> headers = new HashMap<String, Object>();
                        if (StringUtils.isBlank(merKey)) {
                            Message msg = null;
                            Map<String, Object> map=null;
                            String operateFlag = xmlcfg.getString("operateFlag");
                            if(StringUtils.isNotBlank(merNo)||(PayGateConstants.ADDORUPDATE_MER_SERVICE_NAME.equals(trancode)&&"U".equals(operateFlag))){
                            	//根据商户号获取加解密串
                            	bodys.put("transCode", "SI_MER0018");
                                bodys.put("merPlatNo", merNo);
                                msg =
                                        MessageFactory.create(UUIDGenerator.randomUUID(),
                                            MessageFactory.createSimpleMessageInstance(),
                                            MessageFactory.createSimpleMessage(headers, bodys),
                                            FaultFactory.create(ResponseCode.SUCCESS, "SUCCESS"));
                                msg = SI_MER0018.handle(msg);
                                map = (Map<String, Object>) msg.getTarget().getBodys();
                                if (!ResponseCode.SUCCESS.equals(msg.getFault().getCode())) {
                                    ExInfo.throwDipperEx(AppCodeDict.GWSPAY1013, msg.getFault().getMsg());
                                }
                                merKey = (String) map.get("merKey");
                            }else{
                            	//获取默认的加密串
                            	bodys.put("transCode", "SI_MER0025");
                                msg =
                                        MessageFactory.create(UUIDGenerator.randomUUID(),
                                            MessageFactory.createSimpleMessageInstance(),
                                            MessageFactory.createSimpleMessage(headers, bodys),
                                            FaultFactory.create(ResponseCode.SUCCESS, "SUCCESS"));
                                msg = SI_MER0025.handle(msg);
                                map = (Map<String, Object>) msg.getTarget().getBodys();
                                if (!ResponseCode.SUCCESS.equals(msg.getFault().getCode())) {
                                    ExInfo.throwDipperEx(AppCodeDict.GWSPAY1013, msg.getFault().getMsg());
                                }
                                merKey = (String) map.get("defaultKey");
                            }
                        }
                        
                        plainText.append(KEY_NAME).append("=").append(merKey);
                        String sign = MD5Utils.md5Hex(plainText.toString(), charset);
                        if (!targetSign.equals(sign)) {
                            ExInfo.throwDipperEx(GWSPAY2403);
                        }
                    }

                    logger.debug("channel[{}], trancode[{}]", channelid, trancode);
                    bool = true;
                    break;
                }
            }

            if (!bool) {
                throw new IllegalArgumentException("[channel=" + channelid + "] preview config error!!");
            }

            if (StringUtils.isBlank(trancode)) {
                throw new IllegalArgumentException("trancode不能为空!!");
            }

            ConfigDef headConfigDef = configDefMap.get(Constants.HEAD);
            if (headConfigDef == null) {
                throw new IllegalArgumentException("请检查" + Constants.HEAD + "对应的配置文件!!");
            }

            ConfigDef configDef = configDefMap.get(trancode);
            if (configDef == null) {
                throw new IllegalArgumentException("请检查" + trancode + "对应的配置文件!!");
            }

            List<MappingDef> mappingDefs = new ArrayList<MappingDef>();
            String head = configDef.getMappingsDef().getHead();
            if (StringUtils.isNotBlank(head)) {
                logger.debug("use head is {}.xml", head);
                ConfigDef headDef = configDefMap.get(head);
                mappingDefs.addAll(headDef.getMappingsDef().getMappingDefs());
            } else {
                logger.debug("use head is head.xml");
                mappingDefs.addAll(headConfigDef.getMappingsDef().getMappingDefs());
            }

            String bodyhead = configDef.getMappingsDef().getBodyhead();
            ConfigDef bodyheadDef = null;
            boolean bodyheadSpecialBool = false;
            if (StringUtils.isNotBlank(bodyhead)) {
                bodyheadDef = configDefMap.get(bodyhead);
                if (null == bodyheadDef) {
                    throw new IllegalArgumentException("请检查" + bodyhead + ".xml配置文件是否存在!!");
                }
                bodyheadSpecialBool = true;
                if (bodyheadSpecial) {
                    mappingDefs.addAll(bodyheadDef.getMappingsDef().getMappingDefs());
                    bodyheadSpecialBool = false;
                }

            }

            setData(xmlcfg, xmlUtils, resultMap, mappingDefs);
            mappingDefs.clear();

            if (bodyheadSpecialBool) {
                mappingDefs.addAll(bodyheadDef.getMappingsDef().getMappingDefs());
            }

            if (configDef.getMappingsDef().getMappingDefs() != null) {
                mappingDefs.addAll(configDef.getMappingsDef().getMappingDefs());
            }

            setData(xmlcfg, xmlUtils, resultMap, mappingDefs);

            if (!svrflag) {
                if (StringUtils.isNotBlank(errCodeSuc) && StringUtils.isNotBlank(errCodeName)) {
                    if (!caBool && !errCodeSuc.equals(resultMap.get(errCodeName))) {
                        message.getFault().setMsgAll(String.valueOf(resultMap.get(errMsgName)));
                        String code = String.valueOf(resultMap.get(errCodeName));
                        message.getFault().setOutCode(code);
                        setErrorMessage(message, resultMap, sysName, concreteSysId, code);
                        print(resultMap);
                        return message;
                    }
                    message.getFault().setCode(com.pactera.dipper.core.utils.Constants.ResponseCode.SUCCESS);
                    message.getFault().setOutCode(errCodeSuc);
                }
            }

        } catch (Exception e) {
            logger.error("", e);
            throw e;
        } finally {
            payloadByte = null;
            xmlUtils.close(xmlcfg);
            xmlUtils = null;
        }

        print(resultMap);

        message.getTarget().setBody(resultMap);

        return message;
    }

//ESB调用重组xml
    private String rebuildXml(XMLConfiguration xmlcfg) {
    	StringBuffer sbf=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF8\"?>");
    	sbf.append("<epay>");
    	sbf.append("<serviceVersion>1.0</serviceVersion>");
    	  sbf.append("<charset>UTF-8</charset>");
    	  sbf.append("<signType>MD5</signType>");
    	//得到文档名称为Student的元素的节点列表
        NodeList sysHead = xmlcfg.getDocument().getElementsByTagName("SYS_HEAD");
        if(sysHead.getLength()==0){
        	return null;
        }
        //遍历该集合，显示结合中的元素及其子元素的名字
        for(int i = 0; i< sysHead.getLength() ; i ++){
            Element node = (Element)sysHead.item(i);
            String chnl=node.getElementsByTagName("ChnlTp").item(0).getFirstChild().getNodeValue();
            if(!CommonConstants_GNR.CHNL_ID_ONLINE_BANK.equals(chnl)){
            	return null;
            }
            sbf.append("<transCode>onlineBankDS</transCode>");
            sbf.append("<chnlId>"+ chnl+"</chnlId>");
            sbf.append("<SvcCd>"+node.getElementsByTagName("SvcCd").item(0).getFirstChild().getNodeValue()+"</SvcCd>");  
	  		sbf.append("<SvcScn>"+node.getElementsByTagName("SvcScn").item(0).getFirstChild().getNodeValue()+"</SvcScn>");
	  		sbf.append("<CnsmSysId>"+node.getElementsByTagName("CnsmSysId").item(0).getFirstChild().getNodeValue()+"</CnsmSysId>");
//	  		sbf.append("<PrvdSysId>"+node.getElementsByTagName("PrvdSysId").item(0).getFirstChild().getNodeValue()+"</PrvdSysId>");
	  		sbf.append("<CnsmSysSeqNo>"+node.getElementsByTagName("CnsmSysSeqNo").item(0).getFirstChild().getNodeValue()+"</CnsmSysSeqNo>");
//	  		sbf.append("<PrvdSysSeqNo>"+node.getElementsByTagName("PrvdSysSeqNo").item(0).getFirstChild().getNodeValue()+"</PrvdSysSeqNo>");
	  		sbf.append("<SrcSysSeqNo>"+node.getElementsByTagName("SrcSysSeqNo").item(0).getFirstChild().getNodeValue()+"</SrcSysSeqNo>");
	  		sbf.append("<TranDt>"+node.getElementsByTagName("TranDt").item(0).getFirstChild().getNodeValue()+"</TranDt>");
	  		sbf.append("<TranTm>"+node.getElementsByTagName("TranTm").item(0).getFirstChild().getNodeValue()+"</TranTm>");
        }
        
        NodeList body = xmlcfg.getDocument().getElementsByTagName("BODY");
        //遍历该集合，显示结合中的元素及其子元素的名字
      for(int i = 0; i< body.getLength() ; i ++){
          Element node = (Element)body.item(i);
	  		 
  		
          sbf.append("<payerAcctNo>"+node.getElementsByTagName("PyrAcctNo").item(0).getFirstChild().getNodeValue()+"</payerAcctNo>");
          sbf.append("<payerAcctName>"+node.getElementsByTagName("PyrAcctNm").item(0).getFirstChild().getNodeValue()+"</payerAcctName>");
          sbf.append("<payerCertNo>"+node.getElementsByTagName("PyrIdentNo").item(0).getFirstChild().getNodeValue()+"</payerCertNo>");
          sbf.append("<payerMobile>"+node.getElementsByTagName("PyMblNo").item(0).getFirstChild().getNodeValue()+"</payerMobile>");
          sbf.append("<payeeAcctNo>"+node.getElementsByTagName("PyeAcctNo").item(0).getFirstChild().getNodeValue()+"</payeeAcctNo>");
          sbf.append("<payeeAcctName>"+node.getElementsByTagName("PyeAcctNm").item(0).getFirstChild().getNodeValue()+"</payeeAcctName>");
          sbf.append("<payeeCertNo>"+node.getElementsByTagName("PyeIdentNo").item(0).getFirstChild().getNodeValue()+"</payeeCertNo>");
          sbf.append("<payeeMobile>"+node.getElementsByTagName("PyeAcctTelNo").item(0).getFirstChild().getNodeValue()+"</payeeMobile>");
          sbf.append("<outerOrderNo>"+node.getElementsByTagName("OrdrNo").item(0).getFirstChild().getNodeValue()+"</outerOrderNo>");
          sbf.append("<outerOrderDate>"+node.getElementsByTagName("OrdrDt").item(0).getFirstChild().getNodeValue()+"</outerOrderDate>");
          sbf.append("<transAmt>"+node.getElementsByTagName("TxnAmt").item(0).getFirstChild().getNodeValue()+"</transAmt>");
          sbf.append("<CURR>"+node.getElementsByTagName("Ccy").item(0).getFirstChild().getNodeValue()+"</CURR>");
          
          sbf.append("<transComments>"+node.getElementsByTagName("Rmk").item(0).getFirstChild().getNodeValue()+"</transComments>");
          sbf.append("<spbillCreateIp>"+node.getElementsByTagName("IpAddr").item(0).getFirstChild().getNodeValue()+"</spbillCreateIp>");
      }
      sbf.append("</epay>");
		return sbf.toString();
	}


	private void print(Map<String, Object> resultMap) {
        logger.debug("{} unpack after:{}", Constants.MP_BUNDLE, resultMap.toString());
        logger.debug("{} unpack end!!", Constants.MP_BUNDLE);
    }


    private void setErrorMessage(Message message, Map<String, Object> resultMap, String sysName,
            String relationSysId, String code) {
        message.setFault(CodeUtils.thirdSysFaultOut2In(message.getFault(), sysName, relationSysId, code));
        message.getTarget().setBody(resultMap);
    }


    @SuppressWarnings("all")
    private void setData(XMLConfiguration xmlcfg, TemplateUtils xmlUtils, Map<String, Object> resultMap,
            List<MappingDef> mappingDefs) {
        for (MappingDef mappingDef : mappingDefs) {
            logger.debug("xpath={}, field={}, type={}, attr={}", new Object[] { mappingDef.getXpath(),
                                                                               mappingDef.getField(),
                                                                               mappingDef.getType(),
                                                                               mappingDef.getAttr() });
            if(mappingDef.getXpath().equals("sign")&&CommonConstants_GNR.CHNL_ID_ONLINE_BANK.equals(resultMap.get("chnlId"))){//本行网银不需要设置sign字段
            	return;
            }
            if (StringUtils.isNotBlank(mappingDef.getType())
                    && Constants.TYPE_LIST.equals(mappingDef.getType())) {
                xmlUtils.setList(xmlcfg, resultMap, mappingDef);
            } else {
                xmlUtils.setText(xmlcfg, resultMap, mappingDef);
            }
        }
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
    
//    public static void main(String [] args){
//    	StringBuffer sbf=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF8\"?>");
//    	sbf.append("<epay>");
//    	sbf.append("<serviceVersion>1.0</serviceVersion>");
//    	sbf.append("<charset>UTF-8</charset>");
//    	sbf.append("<signType>MD5</signType>");
//    	sbf.append("<sign>121</sign>");
//        sbf.append("<payerAcctNo>12313</payerAcctNo>");
//	     sbf.append("<payerAcctName>123</payerAcctName>");
//	     sbf.append("<payerCertNo>345345</payerCertNo>");
//	     sbf.append("<payerMobile>fd</payerMobile>");
//	     sbf.append("<payeeAcctNo>3145324543</payeeAcctNo>");
//	     sbf.append("<payeeAcctName>name</payeeAcctName>");
//	     sbf.append("<payeeCertNo>certNo</payeeCertNo>");
//	     sbf.append("<payeeMobile>13552653485</payeeMobile>");
//	     sbf.append("<outerOrderNo>146487845</outerOrderNo>");
//	     sbf.append("<outerOrderDate>20172653</outerOrderDate>");
//	     sbf.append("<transAmt>100</transAmt>");
//	     sbf.append("<CURR>CNY</CURR>");
//	     sbf.append("</epay>");
//	     
//	     StringBuilder sXML = new StringBuilder();
//			sXML.append(sbf);
//			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			Document doc = null;
//			try {
//				InputStream is = new ByteArrayInputStream(sXML.toString().getBytes(
//						"utf-8"));
//				XMLConfiguration xmlcfg = new XMLConfiguration();
//				xmlcfg.load(is);
////				doc = dbf.newDocumentBuilder().parse(is);
//				doc=xmlcfg.getDocument();
//				is.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			NodeList nodeList = doc.getElementsByTagName("epay");
//			for(int i = 0; i< nodeList.getLength() ; i ++){
//		          Element node = (Element)nodeList.item(i);
//		          System.out.println(node.getElementsByTagName("serviceVersion").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("charset").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("signType").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("sign").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("payerAcctNo").item(0).getFirstChild().getNodeValue());
//		          
//		          System.out.println(node.getElementsByTagName("payerAcctName").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("payerCertNo").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("payerMobile").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("payeeAcctNo").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("payeeAcctName").item(0).getFirstChild().getNodeValue());
//		          
//		          System.out.println(node.getElementsByTagName("payeeCertNo").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("payeeMobile").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("outerOrderNo").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("outerOrderDate").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("transAmt").item(0).getFirstChild().getNodeValue());
//		          System.out.println(node.getElementsByTagName("CURR").item(0).getFirstChild().getNodeValue());
//			}
//			 
//    }
}
