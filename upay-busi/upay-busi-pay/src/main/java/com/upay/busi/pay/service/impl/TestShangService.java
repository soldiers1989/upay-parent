/**
 * 
 */
package com.upay.busi.pay.service.impl;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.pay.service.dto.TestShangDto;

/**
 * @author shang
 * 2016年11月16日
 */
public class TestShangService extends AbstractDipperHandler<TestShangDto> {

    @Override
    public TestShangDto execute(TestShangDto dto, Message message) throws Exception {
       // System.out.println("----------flow被调用----------------");
       // System.out.println("-----------flow获取的参数"+dto.getOrderId()+"---------------");
        return dto;
    }

}
