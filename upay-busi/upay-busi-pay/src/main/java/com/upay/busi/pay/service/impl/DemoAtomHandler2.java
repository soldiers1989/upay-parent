package com.upay.busi.pay.service.impl;

import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.DemoDto;

@Deprecated
public class DemoAtomHandler2 extends AbstractDipperHandler<DemoDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoAtomHandler2.class);

    @Resource
    private IDaoService daoService;

    Random r = new Random();


    @Override
    public DemoDto execute(DemoDto dto, Message arg1) throws Exception {
        LOGGER.debug("========DemoAtomHandler 2 start====================");

        String username = dto.getUsername();
        String password = dto.getPassword();

        LOGGER.debug("user [{}] , password [{}] ", username, password);

        // UserBookPo po = new UserBookPo();
        // po.setPassword(password);
        // po.setUsername(username);

        // daoService.insert(po);

        // if (!false)
        // throw new RuntimeException("test error");

        LOGGER.debug("========DemoAtomHandler 2 end====================");
        return dto;
    }

}
