package com.upay.busi.pay;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadTest {
    private Logger logger = LoggerFactory.getLogger(ThreadTest.class);


    @Test
    public void execute() throws Exception {
    	for (int i = 0; i < 5; i++) {
			Thread test = new ThreadRun();
			test.setName("NO"+i);
			test.start();
		}
		
    }

}
