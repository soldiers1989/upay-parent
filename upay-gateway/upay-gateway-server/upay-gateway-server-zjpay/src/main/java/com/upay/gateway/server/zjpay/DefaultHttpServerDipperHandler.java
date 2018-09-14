package com.upay.gateway.server.zjpay;

import java.util.Map;

import com.pactera.dipper.core.utils.Constants.Channel;
import com.pactera.dipper.presys.cp.server.DefaultServerDipperHandler;


public class DefaultHttpServerDipperHandler extends DefaultServerDipperHandler {

    @Override
    protected void setInitParam(Map<String, Object> init) {
        String channelTranCode = (String) init.get(Channel._TRAN_CODE_NAME);
        if (null != channelTranCode) {
            String transCode = getServiceMapping().get(channelTranCode);
            init.put("transCode", transCode);
        }

    }
}
