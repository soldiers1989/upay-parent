package com.upay.gateway.client.sms.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.presys.cp.client.AbstractClientDipperHandler;


public class SmsClientHandler extends AbstractClientDipperHandler {

    @Override
    protected void setInitParam(Map<String, Object> init) {
        // TODO Auto-generated method stub
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
//        init.put("tranCode", "610006");// 内部交易代码，获取模板用
        init.put("termDate", dateFormat.format(date));
        init.put("termTime", timeFormat.format(date));
//        init.put("branchNo", "1010");

    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        target.putAll(source);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        target.putAll(source);
    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
        // TODO Auto-generated method stub

    }

}
