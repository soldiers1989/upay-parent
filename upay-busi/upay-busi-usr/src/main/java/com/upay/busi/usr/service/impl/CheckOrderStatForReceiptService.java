/**
 * 
 */
package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.CheckOrderStatForReceiptDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * @author shang 2016年12月12日
 */
public class CheckOrderStatForReceiptService extends AbstractDipperHandler<CheckOrderStatForReceiptDto> {

    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(UserPayPwdFlagSearchService.class);

    @Resource
    IDipperCached iCached;


    @Override
    public CheckOrderStatForReceiptDto execute(CheckOrderStatForReceiptDto dto, Message message)
            throws Exception {
        String userId = dto.getUserId();
        String orderNo = dto.getOrderNo();// 订单号
        String chnlId = dto.getChnlId();// 渠道ID
        String tradePwd = null;// 用户支付密码
        String transCode = dto.getTransCode();
        UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();

        if (StringUtils.isBlank(chnlId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "渠道ID");
        }
        if (StringUtils.isBlank(transCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "transCode");
        }
        PayOrderListPo payOrderListPo = new PayOrderListPo();
        if (transCode.equals("SI_USR0028")) {// 判断订单状态
            if (StringUtils.isBlank(orderNo)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
            }
            payOrderListPo.setOrderNo(orderNo);
            payOrderListPo = daoService.selectOne(payOrderListPo);
            if (payOrderListPo == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0042);
            }
            String orderStat = payOrderListPo.getOrderStat();
            if (StringUtils.isBlank(orderStat)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单状态");
            }
            if (!orderStat.equals(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0044);
            }
        }
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户id");
        }
        String userId1 = payOrderListPo.getUserId();
        if (!userId1.equals(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0056);
        }
        if (StringUtils.isBlank(userId1)) {
            if (StringUtils.isBlank(userId)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户ID");
            } else {
                usrRegInfoPo.setUserId(userId);
            }
        } else {// 免密
            if (StringUtils.isNotBlank(userId)) {
                if (!userId1.equals(userId)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0056);
                }
            }
            usrRegInfoPo.setUserId(userId1);
        }
        usrRegInfoPo.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
        usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
        if (usrRegInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "该用户");
        }
        String sessionId = null;
        if (chnlId.equals(DataBaseConstants_USR.CHNL_ID_WEB)) {// WEB渠道
            sessionId = iCached.get(CacheConstants.SESSION_WEB + usrRegInfoPo.getUserId());
        } else if (chnlId.equals(DataBaseConstants_USR.CHNL_ID_APP)) {
            sessionId = iCached.get(CacheConstants.SESSION_APP + usrRegInfoPo.getUserId());
        }
        if (StringUtils.isBlank(sessionId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0037);
        }
        if (chnlId.equals(DataBaseConstants_USR.CHNL_ID_WEB)) {// WEB渠道
            dto.setSessionId(iCached.get(CacheConstants.SESSION_WEB + usrRegInfoPo.getUserId()).toString());
        } else if (chnlId.equals(DataBaseConstants_USR.CHNL_ID_APP)) {
            dto.setSessionId(iCached.get(CacheConstants.SESSION_APP + usrRegInfoPo.getUserId()).toString());
        }
        dto.setUserName(usrRegInfoPo.getUserName());
        dto.setUserId(usrRegInfoPo.getUserId());
        dto.setMobile(usrRegInfoPo.getMobile());
        dto.setUserCertLevel(usrRegInfoPo.getUserCertLevel());
        dto.setRegType(usrRegInfoPo.getRegType());

        UsrBaseInfoPo usrBaseInfoPo = new UsrBaseInfoPo();
        usrBaseInfoPo.setUserId(usrRegInfoPo.getUserId());
        usrBaseInfoPo = daoService.selectOne(usrBaseInfoPo);
        if (usrBaseInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "该用户");
        }
        dto.setCertNo(usrBaseInfoPo.getCertNo());
        dto.setCertName(usrBaseInfoPo.getCertName());
        tradePwd = usrRegInfoPo.getTradePwd();
        AccVbookPo accVbookPo = new AccVbookPo();
        accVbookPo.setUserId(usrRegInfoPo.getUserId());
        accVbookPo = daoService.selectOne(accVbookPo);
        if (accVbookPo == null) {
            if (StringUtils.isBlank(tradePwd)) {
                dto.setPayPwdFlag(DataBaseConstants_USR.PAY_PWD_NO1);
            } else {
                dto.setPayPwdFlag(DataBaseConstants_USR.PAY_PWD_YES);
            }
        } else {
            if (StringUtils.isBlank(tradePwd)) {
                dto.setPayPwdFlag(DataBaseConstants_USR.PAY_PWD_NO2);
            } else {
                dto.setPayPwdFlag(DataBaseConstants_USR.PAY_PWD_YES);
            }
        }
        logger.debug("-------------------------------------end");
        return dto;
    }

}
