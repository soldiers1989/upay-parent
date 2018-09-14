package com.upay.flow.pay;

import com.pactera.dipper.dubbo.startup.StartupUtil;


public class Startup {

    public static void main(String[] args) throws Exception {
        StartupUtil.startApplication("flow-pay", args);
    }

}
