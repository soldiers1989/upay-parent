package com.upay.batch.dispatch;

import com.pactera.dipper.dubbo.startup.StartupUtil;


public class Startup {

    public static void main(String[] args) throws Exception {
        StartupUtil.startApplication("batch-dispatch", args);
    }

}
