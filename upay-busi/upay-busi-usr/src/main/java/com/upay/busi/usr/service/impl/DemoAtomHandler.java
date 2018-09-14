package com.upay.busi.usr.service.impl;

import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.DemoDto;
import com.upay.commons.encryptor.UnionAPI;

public class DemoAtomHandler extends AbstractDipperHandler<DemoDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoAtomHandler.class);
    @Resource
   private IDaoService daoService;
    @Resource
    private UnionAPI unionAPI;


	Random r = new Random();


    @Override
    public DemoDto execute(DemoDto dto, Message arg1) throws Exception {
//        LOGGER.DEBUG("========DEMOATOMHANDLER START====================");
//        LOGGER.DEBUG("用户名是：{},密码是：{}",DTO.GETUSERNAME(),DTO.GETPASSWORD());
//        /*IF (DTO.GETSYSSEQ() == NULL) {
//            DTO.SETSYSDATE(NEW DATE());
//            DTO.SETSYSSEQ(UUIDGENERATOR.RANDOMUUID());
//        }*/
//
//        DTO.SETUSERNAME(R.NEXTINT() + "");
//        DTO.SETPASSWORD("测试密码");
//        
//        UNIONAPI.GETSHORTCONNECTION();
//        STRING S = UNIONAPI.ENCRYPT("12333323232323323232");
//        LOGGER.DEBUG("======:"+S);
//
//        LOGGER.DEBUG("========DEMOATOMHANDLER END====================");
        return dto;
    }


	

}
