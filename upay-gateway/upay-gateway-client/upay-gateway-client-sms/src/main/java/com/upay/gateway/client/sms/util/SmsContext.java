package com.upay.gateway.client.sms.util;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Sms上下文
 * 
 * @author 车静
 * 
 */
public class SmsContext {
    private static Integer id = 1;
    private static ConcurrentHashMap<Integer, Object> hashCommand = new ConcurrentHashMap();
    private static SmsContext smsContext;


    private SmsContext() {
        // TODO Auto-generated constructor stub
    }


    public synchronized static SmsContext getInstance() {
        if (smsContext == null) {
            smsContext = new SmsContext();
        }
        return smsContext;
    }


    /**
     * 获得下一个上下文ID
     * 
     * @return
     */
    public synchronized static int nextContextId() {
        int n = 0;
        synchronized (id) {
            ++id;
            n = id.intValue();
        }
        return n;
    }


    public static int currContextId() {
        return id.intValue();
    }


    public void addCommand(int nContextId, Object obj) {
        // Integer nId = Integer.valueOf(nContextId);
        hashCommand.put(Integer.valueOf(nContextId), obj);
    }


    public Object getCommand(int nContextId) {
        Integer nKey = Integer.valueOf(nContextId);
        Object obj = hashCommand.get(nKey);
        return obj;
    }


    public Object removeCommand(int nContextId) {
        Integer nKey = Integer.valueOf(nContextId);
        Object obj = hashCommand.remove(nKey);
        return obj;
    }


    public void deleteCommand(int nContextId) {
        Integer nKey = Integer.valueOf(nContextId);
        Object obj = hashCommand.remove(nKey);
        obj = null;
    }

}
