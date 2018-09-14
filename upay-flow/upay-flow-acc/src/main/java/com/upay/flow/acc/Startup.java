package com.upay.flow.acc;

import com.pactera.dipper.dubbo.startup.StartupUtil;


public class Startup {

    public static void main(String[] args) throws Exception {
        StartupUtil.startApplication("flow-acc", args);
    }

}
