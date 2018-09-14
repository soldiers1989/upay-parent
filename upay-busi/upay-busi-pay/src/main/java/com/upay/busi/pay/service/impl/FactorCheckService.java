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
 * 四要素验证检查
 *
 * @author hry
 */
public class FactorCheckService extends AbstractDipperHandler<CollectionOpenTokenQueryDto> {

    private static final Logger LOG = LoggerFactory
            .getLogger(CollectionOpenTokenInsertService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public CollectionOpenTokenQueryDto execute(CollectionOpenTokenQueryDto collectionOpenTokenQueryDto, Message msg) throws Exception {
        @SuppressWarnings("unused")
        String accNO = collectionOpenTokenQueryDto.getAccNo();
        collectionOpenTokenQueryDto.setBindacctno(accNO);
        String phone = collectionOpenTokenQueryDto.getPhoneNo();
        collectionOpenTokenQueryDto.setPhone(phone);
        String customerNm = collectionOpenTokenQueryDto.getCustomerNm();
        String certifTp = collectionOpenTokenQueryDto.getCertifTp();
        String certifId = collectionOpenTokenQueryDto.getCertifId();
        String cvn2 = collectionOpenTokenQueryDto.getCvn2();
        String expired = collectionOpenTokenQueryDto.getExpired();
        String cardBinType = collectionOpenTokenQueryDto.getCardBinType();
        if ("1".equals(collectionOpenTokenQueryDto.getApplyFlag())) {
            //借记卡
            if ("1".equals(cardBinType)) {

                if (StringUtils.isBlank(accNO)) {
                    ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "四要素验证账号");
                }
                if (StringUtils.isBlank(customerNm)) {
                    ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "四要素验证，姓名");
                }
                if (StringUtils.isBlank(certifId)) {
                    ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "四要素验证，证件号");
                }
                if (StringUtils.isBlank(certifTp)) {
                    ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "四要素验证，证件类型");
                }
                if (StringUtils.isBlank(phone)) {
                    ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "四要素验证，手机号");
                }
            }

            //贷记卡
            if ("2".equals(cardBinType)) {
                if (StringUtils.isBlank(cvn2)) {
                    ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "信用卡cvn2");
                }
                if (StringUtils.isBlank(expired)) {
                    ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "信用卡有效期");
                }
            }

        } else {
            ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "申请标志 appFlag 请填写 1");
        }
        collectionOpenTokenQueryDto.setExistRecord(Boolean.FALSE.toString());
        return collectionOpenTokenQueryDto;
    }

}

