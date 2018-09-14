package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CollectionOpenTokenQueryDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.CollectionOpenPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 银联代收授权查询
 *
 * @author hry
 */
public class CollectionOpenQueryService extends AbstractDipperHandler<CollectionOpenTokenQueryDto> {

    private static final Logger LOG = LoggerFactory
            .getLogger(CollectionOpenTokenInsertService.class);

    @Resource
    private IDaoService daoService;


    //删除授权
    private final String APPLY_FLAG_DEL="2";
    //申请授权
    private final String APPLY_FLAG_INS="1";

    @Override
    public CollectionOpenTokenQueryDto execute(CollectionOpenTokenQueryDto collectionOpenTokenQueryDto, Message msg) throws Exception {
        @SuppressWarnings("unused")
        String existRecord = "false";
        String accNO = collectionOpenTokenQueryDto.getAccNo();
        collectionOpenTokenQueryDto.setBindacctno(accNO);
        String phone = collectionOpenTokenQueryDto.getPhoneNo();
        collectionOpenTokenQueryDto.setPhone(phone);
        String customerNm = collectionOpenTokenQueryDto.getCustomerNm();
        String certifTp = collectionOpenTokenQueryDto.getCertifTp();
        String certifId = collectionOpenTokenQueryDto.getCertifId();
        CollectionOpenPo collectionOpenPo = new CollectionOpenPo();

        //账号+姓名 /账号+证件类型+证件号
        if (StringUtils.isNotBlank(accNO)){
            collectionOpenPo.setBindacctno(accNO);
            collectionOpenPo.setStatus("01");
            //申请 代收授权需要判断  删除授权不需要
            if ("1".equals(collectionOpenTokenQueryDto.getApplyFlag())) {
                if(StringUtils.isNotBlank(customerNm)){
                    collectionOpenPo.setCustomerNm(customerNm);
                }else{
                    ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "代收签约，客户姓名");
                }
                if(StringUtils.isNotBlank(certifId)){
                    collectionOpenPo.setCertifId(certifId);
                }else{
                    ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "代收签约，证件号");
                }
                if(StringUtils.isNotBlank(certifTp)){
                    collectionOpenPo.setCertifTp(certifTp);
                }else{
                    ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "代收签约，证件类型");
                }
            }

        }else{
            ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "代收账号");
        }
        CollectionOpenPo tmpcotp = daoService.selectOne(collectionOpenPo);
        if (tmpcotp != null) {
            if (StringUtils.isNotBlank(tmpcotp.getId())) {
                //针对于消费时     授权传递的  姓名+证件号+证件类型  与数据中查询的授权记录 不一致
                if( CommonConstants_GNR.TRANS_CODE_SINGLE_COLLECTION.equals(collectionOpenTokenQueryDto.getTransCode())){
                     if(!certifId.equals(tmpcotp.getCertifId())){
                         ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0103, certifId,tmpcotp.getCertifId());
                     }
                    if(!certifTp.equals(tmpcotp.getCertifTp())){
                        ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0104, certifTp,tmpcotp.getCertifTp());
                    }
                    if(!customerNm.equals(tmpcotp.getCustomerNm())){
                        ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0105, customerNm,tmpcotp.getCustomerNm());
                    }
                }
                existRecord = "true";
            }
        } else {
            Object[]obj=new Object[]{
                    accNO,
                    phone,
                    certifId,
                    customerNm,
                    certifTp
            };
            //针对于 删除 授权情况
            if ("2".equals(collectionOpenTokenQueryDto.getApplyFlag())) {
                ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0101, obj);
            }
            /*针对于消费的情况*/
           if( CommonConstants_GNR.TRANS_CODE_SINGLE_COLLECTION.equals(collectionOpenTokenQueryDto.getTransCode())){
               ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0102, obj);
            }
        }

        //针对于 申请授权  和消费授权 的情况
        if (!"2".equals(collectionOpenTokenQueryDto.getApplyFlag())) {
            if (existRecord.equals("true") && collectionOpenTokenQueryDto.getFlay().equals("1")) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0035, accNO);
            }
            if (existRecord.equals("flase") && collectionOpenTokenQueryDto.getFlay().equals("2")) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0034, accNO);
            }
        }
        collectionOpenTokenQueryDto.setExistRecord(existRecord);
        return collectionOpenTokenQueryDto;
    }

}

