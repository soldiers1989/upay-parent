package com.upay.gateway.server.esb;

import java.util.HashMap;
import java.util.Map;

import com.pactera.dipper.core.utils.Constants.Channel;
import com.pactera.dipper.presys.cp.server.DefaultServerDipperHandler;


public class DefaultHttpServerDipperHandler extends DefaultServerDipperHandler {
	private static HashMap<String, String> transCodeMap=new HashMap<>();
    @Override
    protected void setInitParam(Map<String, Object> init) {
    	if(transCodeMap.size()==0){
    		transCodeMap.put("3041001006", "SI_PAY0024");
    		transCodeMap.put("2013000307", "SI_ACC1008");
    		transCodeMap.put("3063001801", "SI_PAY1010");
    		transCodeMap.put("3063001802", "SI_PAY1011");
    		
    		transCodeMap.put("3063001805", "SI_PAY0013");
    		transCodeMap.put("3061000504", "SI_PAY0019");
    		transCodeMap.put("3061000901", "SI_PAY0020");
    		transCodeMap.put("3042003101", "SI_PAY3011");
    		transCodeMap.put("3015000308", "SI_PAY7001");
    	}
        String channelTranCode = (String) init.get("transCode");
        init.put("tradeNo", channelTranCode);
        init.put("chnlId", init.get("chnlTp"));
        if (null != channelTranCode) {
//            String transCode = getServiceMapping().get(channelTranCode);
            init.put("transCode", transCodeMap.get(channelTranCode));

        }

    }
}
