package com.dubhe.common.handler;

import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.presys.cp.server.DefaultServerDipperHandler;

import java.util.Map;

/**
 * Created by Guo on 16/9/7.
 */
public class PayGateDefaultServerDipperHandler extends DefaultServerDipperHandler {
    @Override
    protected void setInitParam(Map<String, Object> init) {
        String channelTranCode = (String) init.get(Constants.Channel._TRAN_CODE_NAME);
        if (null != channelTranCode) {
            String transCode = getServiceMapping().get(channelTranCode);
            init.put("transCode", transCode);
        }
    }
}
