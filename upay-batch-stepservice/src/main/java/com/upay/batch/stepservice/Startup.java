package com.upay.batch.stepservice;

import com.pactera.dipper.dubbo.startup.StartupUtil;


public class Startup {

    public static void main(String[] args) throws Exception {
        StartupUtil.startApplication("upay-batch-stepservice", args);
    }

}
