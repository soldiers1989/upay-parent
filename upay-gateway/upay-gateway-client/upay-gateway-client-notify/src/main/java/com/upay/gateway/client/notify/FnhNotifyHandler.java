package com.upay.gateway.client.notify;

import static com.upay.commons.dict.AppCodeDict.GWSPAY2403;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.bean.Fault;
import com.upay.gateway.client.notify.util.DataSignUtil;

/**
 * 富农汇支付完成后台通知客户端
 * Created by Guo on 2016/10/21.
 */
public class FnhNotifyHandler extends DefaultNotifyClientHandler {
    private static final Logger LOG = LoggerFactory.getLogger(FnhNotifyHandler.class);

    //验签秘钥
    private String key;
    private static String KEY_NAME = "key";

    //报文中商户号字段名称
    private String merNoName;
    
//    private Map<String, Map<String, Object>> columnMap;
    
   /* @Resource(name = "DIPPER_REDIS_CLIENT")
    private IDipperCached cacheClient;*/

    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
        super.doErrorHandle(target, fault);
    }

    @Override
    protected void setInitParam(Map<String, Object> init) {
    	init.put("tranCode", "notify");
    	init.put("channelId", "notifycli");
    	init.put("transCode", "resultNotify");
    	init.put("serviceVersion", "1.0");
    	init.put("charset", "utf-8");
    	init.put("signType", "MD5");
//        boolean isDataSign = (boolean) init.get("dataSign");

//        if (isDataSign) { //加签
//            String dataSign = "";
//            String merNo = (String) init.get("merNo");
//            String merNoName = cacheClient.get(merNo);
//            Map<String, Object> paramMap = columnMap.get("UNIFIEDORDER");
//            try {
//                dataSign = DataSignUtil.signData(paramMap, KEY_NAME, merNoName, "utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            String dataSignName = (String) init.get("dataSignName");
//            init.put(dataSignName, dataSign);
//        }
        super.setInitParam(init);
    }

    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
    	LOG.debug("通知成功了。。。。。。。。。。。。。。。。。。。。。。。");
    	String sign = (String) source.get("sign");
    	boolean isRight = false;
    	
    	LOG.info("merKey的值2==:" + (String) target.get("key"));
    	LOG.info("source的值1==:" + source.toString());
    	LOG.info("target的值1==:" + target.toString());
    	LOG.info("fault的值1==:" + fault.toString()+"=="+fault.getCode());
    	
    	if (null != sign  && !"".equals(sign)) {
			try {
				//String merKey = (String) source.get(getMerNoName());
				String merKey = (String) target.get("key");				
				if (merKey == null || merKey.equals("")) {
					merKey = this.key;
				}
				isRight = DataSignUtil.unSignData(source, KEY_NAME, merKey, sign);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!isRight) {
				ExInfo.throwDipperEx(GWSPAY2403);
			}
		}
        super.doSuccessHandle(source, target, fault);
    }

    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
    	LOG.debug("通知失败");
    	LOG.info("source的值2==:" + source.toString());
    	LOG.info("target的值2==:" + target.toString());
    	LOG.info("fault的值2==:" + fault.toString()+"=="+fault.getCode());
        super.doFailureHandle(source, target, fault);
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

	/*public IDipperCached getCacheClient() {
		return cacheClient;
	}

	public void setCacheClient(IDipperCached cacheClient) {
		this.cacheClient = cacheClient;
	}
*/
//	public Map<String, Map<String, Object>> getColumnMap() {
//		return columnMap;
//	}
//
//	public void setColumnMap(Map<String, Map<String, Object>> columnMap) {
//		this.columnMap = columnMap;
//	}
    
	@Override
	public boolean isFailureThrow() {
		return true;
	}
    
    
}
