package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CollectionOpenTokenQueryDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.CollectionOpenTokenPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 银联token授权删除
 * 
 * @author hry
 *
 */
public class CollectionOpenTokenDeleteService extends AbstractDipperHandler<CollectionOpenTokenQueryDto> {

	private static final Logger LOG = LoggerFactory
			.getLogger(CollectionOpenTokenInsertService.class);
	
	@Resource
	private IDaoService daoService;
	
    @Override
    public CollectionOpenTokenQueryDto execute(CollectionOpenTokenQueryDto collectionOpenTokenQueryDto, Message msg) throws Exception {
    	String accNO = collectionOpenTokenQueryDto.getAccNo();
    	collectionOpenTokenQueryDto.setBindacctno(accNO);
        String phone = collectionOpenTokenQueryDto.getPhoneNo();
        collectionOpenTokenQueryDto.setPhone(phone);
        CollectionOpenTokenPo collectionOpenTokenPo =new CollectionOpenTokenPo();
        collectionOpenTokenPo.setBindacctno(accNO);
        collectionOpenTokenPo.setPhone(phone);
        collectionOpenTokenPo.setStatus("01");
        CollectionOpenTokenPo tmpcotp=daoService.selectOne(collectionOpenTokenPo);
        if(tmpcotp!=null){
            collectionOpenTokenQueryDto.setToken(tmpcotp.getToken());
            daoService.delete(tmpcotp);
            collectionOpenTokenQueryDto.setActivateStatus("0");
        }else{
            ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "账号:"+accNO+","+"手机号:"+phone+"未经过无跳转支付授权，不能撤销签约");
        }
        return collectionOpenTokenQueryDto;
    }

}

