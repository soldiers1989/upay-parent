package com.upay.gateway.client.esb;


import com.pactera.dipper.core.Message;
import com.pactera.dipper.presys.cp.http.handler.AbstractHttpClientCommHandler;
import com.pactera.dipper.utils.xml.XMLFormatUtils;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






public class HttpNewClientCommHandler
  extends AbstractHttpClientCommHandler
{
  private static final Logger LOG = LoggerFactory.getLogger(HttpNewClientCommHandler.class);
  
  public HttpNewClientCommHandler() {}
  
  protected void receiveHandle(HttpEntity resEntity, Message m) throws Exception {
    byte[] bytes = EntityUtils.toByteArray(resEntity);
    LOG.info("client[{}] receive:[{}]", m.getChannel(), new String(bytes, m.getCharset()));
    LOG.debug("client[{}] receiveInfo format:[{}]", m.getChannel(), XMLFormatUtils.formatXML(new String(bytes, m.getCharset()), m.getCharset()));
    

    m.getTarget().setBody(bytes);
  }
  
  protected void receiveHandleNoOk(HttpEntity resEntity, Message m, StatusLine statusLine) throws Exception
  {
    byte[] bytes = EntityUtils.toByteArray(resEntity);
    LOG.error("client[{}] [{}] receive:[{}]", new Object[] { m.getChannel(), statusLine, new String(bytes, m.getCharset()) });
    
    LOG.debug("client[{}] [{}] receiveInfo format:[{}]", new Object[] { m.getChannel(), statusLine, XMLFormatUtils.formatXML(new String(bytes, m.getCharset()), m.getCharset()) });
  }
  



  protected byte[] setEntity(HttpPost httppost, Message m)
  {
    byte[] bytes = (byte[])m.getTarget().getBodys();
    ByteArrayEntity reqentity = new ByteArrayEntity(bytes);
    reqentity.setChunked(false);
    httppost.setEntity(reqentity);
    return bytes;
  }
  

  protected void setHeader(HttpPost httppost, Message m)
  {
    httppost.setHeader("Content-Type", "text/xml;charset=".concat(m.getCharset()));
  }
}