package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CollectionOpenTokenQueryDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.CollectionOpenTokenPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 银联token授权查询
 *
 * @author hry
 */
public class CollectionOpenTokenQueryService extends AbstractDipperHandler<CollectionOpenTokenQueryDto> {

    private static final Logger LOG = LoggerFactory
            .getLogger(CollectionOpenTokenInsertService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public CollectionOpenTokenQueryDto execute(CollectionOpenTokenQueryDto collectionOpenTokenQueryDto, Message msg) throws Exception {
        @SuppressWarnings("unused")
        String existRecord = "false";

        String accNO = collectionOpenTokenQueryDto.getAccNo();
        collectionOpenTokenQueryDto.setBindacctno(accNO);
        String phone = collectionOpenTokenQueryDto.getPhoneNo();
        collectionOpenTokenQueryDto.setPhone(phone);
        String customerNm = collectionOpenTokenQueryDto.getCustomerNm();
        String certifId = collectionOpenTokenQueryDto.getCertifId();
        String certifTp = collectionOpenTokenQueryDto.getCertifTp();
        if (StringUtils.isBlank(accNO)) {
            ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "卡号");
        }
        if (StringUtils.isBlank(phone)) {
            ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "手机号");
        }
        if (StringUtils.isBlank(customerNm)) {
            ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "姓名");
        }
        if (StringUtils.isBlank(certifId)) {
            ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "证件号");
        }

        CollectionOpenTokenPo collectionOpenTokenPo = new CollectionOpenTokenPo();
        collectionOpenTokenPo.setBindacctno(accNO);
        collectionOpenTokenPo.setPhone(phone);
        collectionOpenTokenPo.setCustomerNm(customerNm);
        collectionOpenTokenPo.setCertifId(certifId);
        collectionOpenTokenPo.setStatus("01");
        CollectionOpenTokenPo tmpcotp = daoService.selectOne(collectionOpenTokenPo);
        if (tmpcotp != null) {
            if (StringUtils.isNotBlank(tmpcotp.getId())) {
                //针对于消费时     授权传递的  姓名+证件号+证件类型  与数据中查询的授权记录 不一致
                if( CommonConstants_GNR.TRANS_CODE_SINGLE_COLLECTION.equals(collectionOpenTokenQueryDto.getTransCode())){
                    if(!certifId.equals(tmpcotp.getCertifId())){
                        ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0106, certifId,tmpcotp.getCertifId());
                    }
                    if(!certifTp.equals(tmpcotp.getCertifTp())){
                        ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0107, certifTp,tmpcotp.getCertifTp());
                    }
                    if(!customerNm.equals(tmpcotp.getCustomerNm())){
                        ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0108, customerNm,tmpcotp.getCustomerNm());
                    }
                }
                existRecord = "true";
            }
            String nowdatestr = DateUtil.format(new Date(), "yyyyMMddHHmmss");
            Date nowdate = DateUtil.parse(nowdatestr, "yyyyMMddHHmmss");
            String tokenEndsrt = tmpcotp.getTokenend();
            Date tokenEnd = DateUtil.parse(tokenEndsrt, "yyyyMMddHHmmss");

            //TODO:  申请token 过期自动签约  消费token过期 需要手动去重新签约
            if (DateUtil.betweenTimes(tokenEnd, nowdate) < 0) {
               // 申请token 过期自动签约
                if( CommonConstants_GNR.TRANS_TYPE_QUICK_PAY_TOKEN_CODE.equals(collectionOpenTokenQueryDto.getTransCode())){
                    existRecord = "false";
                }else{//消费时token过期 消费token过期 需要手动去重新签约
                    ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0089);
                }
            }else{
                collectionOpenTokenQueryDto.setToken(tmpcotp.getToken());
            }
        } else {

            Object[]obj=new Object[]{
                    accNO,
                    phone,
                    certifId,
                    customerNm,
                    certifTp
            };
            //针对于 删除 token情况
            if ("2".equals(collectionOpenTokenQueryDto.getApplyFlag())) {
                ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0099, obj);
            }
            /*针对于消费的情况*/
            if( CommonConstants_GNR.TRANS_CODE_SINGLE_COLLECTION.equals(collectionOpenTokenQueryDto.getTransCode())){
                ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0100, obj);
            }
        }

        //针对于 申请token  和消费token 的情况
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

