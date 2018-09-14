package com.upay.busi.pay.service.impl;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.utils.common.UUIDGenerator;
import com.upay.busi.pay.service.dto.DemoDto;

@Deprecated
public class DemoAtomHandler extends AbstractDipperHandler<DemoDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoAtomHandler.class);

//    @Resource
//    private IDaoService daoService;
//
//    Random r = new Random();
//    @Resource
//    UnionAPI unionAPI;
//
//
    @Override
    public DemoDto execute(DemoDto dto, Message arg1) throws Exception {
//        unionAPI.getShortConnection();
//        String s = unionAPI.encrypt("11111111");
//       // System.out.println(s);
//        //System.out.println(unionAPI.decipher(s));
//        LOGGER.debug("========DemoAtomHandler start====================");
//
//        if (dto.getSysSeq() == null) {
//            dto.setSysDate(new Date());
//            dto.setSysSeq(UUIDGenerator.randomUUID());
//        }
//
//        dto.setUsername(r.nextInt() + "");
//        dto.setPassword("测试密码");
//
//        LOGGER.debug("========DemoAtomHandler end====================");
        return dto;
    }

}
