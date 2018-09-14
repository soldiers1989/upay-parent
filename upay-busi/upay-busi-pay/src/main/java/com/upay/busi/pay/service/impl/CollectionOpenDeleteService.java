package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CollectionOpenTokenQueryDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.CollectionOpenPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 银联授权删除
 * 
 * @author hry
 *
 */
public class CollectionOpenDeleteService extends AbstractDipperHandler<CollectionOpenTokenQueryDto> {

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
        String customerNm = collectionOpenTokenQueryDto.getCustomerNm();
        String certifTp = collectionOpenTokenQueryDto.getCertifTp();
        String certifId = collectionOpenTokenQueryDto.getCertifId();
        CollectionOpenPo collectionOpenPo =new CollectionOpenPo();


        //账号+姓名 /账号+证件类型+证件号
        if (StringUtils.isNotBlank(accNO)){
            collectionOpenPo.setBindacctno(accNO);
            // 开通状态 1已开通 其他未开通
            collectionOpenPo.setStatus("01");


            if(StringUtils.isNotBlank(customerNm)){
                collectionOpenPo.setCustomerNm(customerNm);
            }
            if(StringUtils.isNotBlank(certifTp)){

                collectionOpenPo.setCertifTp(certifTp);
            }
            if(StringUtils.isNotBlank(certifId)) {
                collectionOpenPo.setCertifId(certifId);
            }

            if(StringUtils.isNotBlank(phone)){
                collectionOpenPo.setPhone(phone);
            }

        }else{
            ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "代收账号");
        }
        CollectionOpenPo tmpcotp=daoService.selectOne(collectionOpenPo);
        if(tmpcotp!=null){
            daoService.delete(tmpcotp);
            collectionOpenTokenQueryDto.setActivateStatus("0");
        }else{
            ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "账号:"+accNO+","+"未经过代收授权，不能撤销签约");
        }
        return collectionOpenTokenQueryDto;
    }

}

