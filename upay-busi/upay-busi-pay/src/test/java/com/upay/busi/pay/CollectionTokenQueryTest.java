package com.upay.busi.pay;

import com.upay.busi.pay.service.dto.CollectionOpenTokenQueryDto;
import com.upay.busi.pay.service.dto.DemoDto;
import com.upay.busi.pay.service.impl.CollectionOpenTokenQueryService;
import com.upay.busi.pay.service.impl.DemoAtomHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class CollectionTokenQueryTest {
    private Logger logger = LoggerFactory.getLogger(CollectionTokenQueryTest.class);
    @Resource
    private CollectionOpenTokenQueryService collectionOpenTokenQueryService;


    @Test
    public void execute() throws Exception {
        CollectionOpenTokenQueryDto collectionTokenQueryDto = new CollectionOpenTokenQueryDto();
        collectionTokenQueryDto.setBindacctno("6221558812340000");
        collectionTokenQueryDto.setPhone("13552535506");
        collectionTokenQueryDto.setFlay("2");
        // moneyChannelSearchDto.setChannelId("01");
        collectionOpenTokenQueryService.execute(collectionTokenQueryDto, null);
    }

}
