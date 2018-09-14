package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.impl.SimpleMessage;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CollectionOpenTokenQueryDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.UnionUtils;
import com.upay.dao.po.pay.CollectionOpenTokenPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 银联token授权删除
 *
 * @author hry
 */
public class CheckTokenService extends AbstractDipperHandler<CollectionOpenTokenQueryDto> {

    private static final Logger LOG = LoggerFactory
            .getLogger(CollectionOpenTokenInsertService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public CollectionOpenTokenQueryDto execute(CollectionOpenTokenQueryDto collectionOpenTokenQueryDto, Message msg) throws Exception {

        Map<String, Object> bodyMap = (Map<String, Object>) msg.getTarget().getBodys();
        String origRespCode = null;
        String origRespMsg = null;
        String respCode = null;
        String respMsg = null;
        if (bodyMap.containsKey("respCode")) {
            respCode = (String) bodyMap.get("respCode");
        }
        if (bodyMap.containsKey("origRespCode")) {
            origRespCode = (String) bodyMap.get("origRespCode");
        }
        if (bodyMap.containsKey("origRespMsg")) {
            origRespMsg = (String) bodyMap.get("origRespMsg");
        }

        boolean isDete = false;

        //1.查询交易受理成功  由 origRespCode 判断流水和订单状态  错误信息由 origRespCode返回
        if (CommonBaseConstans_PAY.UNION_PAYMENT_SUCCESS.equals(respCode) ||
                DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(respCode)) {
            if (CommonBaseConstans_PAY.UNION_PAYMENT_SUCCESS.equals(origRespCode) ||
                    DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(origRespCode)) {
            } else {
                //token  失效 或者  已经过期
                if ("80".equals(origRespCode) || "89".equals(origRespCode)) {
                    isDete = true;
                }
            }
        }
        //2.未受理成功   由 respCode 决定 响应码  判断  错误信息由 respCode返回
        else {
            if (CommonBaseConstans_PAY.UNION_PAYMENT_SUCCESS.equals(respCode) ||
                    DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(respCode)) {
            } else {
                //token  失效 或者  已经过期
                if ("80".equals(respCode) || "89".equals(respCode)) {
                    isDete = true;
                }
            }

        }

        if (isDete) {
            String accNO = collectionOpenTokenQueryDto.getAccNo();
            String phone = collectionOpenTokenQueryDto.getPhoneNo();
            String customerNm = collectionOpenTokenQueryDto.getCustomerNm();
            String certifId = collectionOpenTokenQueryDto.getCertifId();
            String certifTp = collectionOpenTokenQueryDto.getCertifTp();
            CollectionOpenTokenPo collectionOpenTokenPo = new CollectionOpenTokenPo();
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
            if (StringUtils.isBlank(certifTp)) {
                ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "证件类型");
            }
            collectionOpenTokenPo.setStatus("01");
            collectionOpenTokenPo.setBindacctno(accNO);
            collectionOpenTokenPo.setPhone(phone);
            collectionOpenTokenPo.setCustomerNm(customerNm);
            collectionOpenTokenPo.setCertifId(certifId);
            collectionOpenTokenPo.setCertifTp(certifTp);
            CollectionOpenTokenPo tmpcotp = daoService.selectOne(collectionOpenTokenPo);
            if (tmpcotp != null) {
                daoService.delete(tmpcotp);
            }
        }
        return collectionOpenTokenQueryDto;
    }

}

