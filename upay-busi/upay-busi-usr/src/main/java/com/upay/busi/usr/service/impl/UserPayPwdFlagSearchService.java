package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.UserPayPwdFlagSearchDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrRegInfoPo;
import com.upay.dao.po.usr.UsrWithoutPwdBookPo;


/**
 * 查询用户是否设置支付密码
 * 
 * @author liyulong
 * 
 */
public class UserPayPwdFlagSearchService extends AbstractDipperHandler<UserPayPwdFlagSearchDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(UserPayPwdFlagSearchService.class);

    @Resource
    IDipperCached iCached;


    @Override
    public UserPayPwdFlagSearchDto execute(UserPayPwdFlagSearchDto userPayPwdFlagSearchDto, Message msg)
            throws Exception {
        String userId = userPayPwdFlagSearchDto.getUserId();
        String orderNo = userPayPwdFlagSearchDto.getOrderNo();// 订单号
        String chnlId = userPayPwdFlagSearchDto.getChnlId();// 渠道ID
        String tradePwd = null;// 用户支付密码
        String transCode = userPayPwdFlagSearchDto.getTransCode();
        String sess = userPayPwdFlagSearchDto.getSessionId();// 本浏览器session
        UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();

        if (StringUtils.isBlank(chnlId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "渠道ID");
        }
        if (StringUtils.isBlank(transCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "transCode");
        }
        PayOrderListPo payOrderListPo = new PayOrderListPo();
        if (transCode.equals("SI_USR0002")) {// 判断订单状态
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
            if (!orderStat.equals(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N)) {
                if (orderStat.equals(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y)
                        || orderStat.equals(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP)
                        || orderStat.equals(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0045);
                } else {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0044);
                }
            }
        }
        String userId1 = payOrderListPo.getUserId();// 如果userId1存在为免密，否则为非免密
        boolean flag = false;
        if (StringUtils.isNotBlank(userId1)) {
            UsrWithoutPwdBookPo usrWithoutPwdBookPo = new UsrWithoutPwdBookPo();
            usrWithoutPwdBookPo.setUserId(userId1);
            usrWithoutPwdBookPo.setMerNo(payOrderListPo.getMerNo());
            usrWithoutPwdBookPo.setBindStat("1");// 已经绑定
            usrWithoutPwdBookPo = daoService.selectOne(usrWithoutPwdBookPo);
            if (usrWithoutPwdBookPo != null) {
                flag = true;// 免密
            }
        }
        if (flag) {// 免密
            logger.debug("免密用户----------------");
            if (StringUtils.isNotBlank(userId)) {
                if (!userId.equals(userId1)) {// 如果有其他用户登录，删除另外一个用户的session
                    if (DataBaseConstants_USR.CHNL_ID_APP.equals(chnlId)) {
                        iCached.delete(CacheConstants.SESSION_APP.concat(userId));
                        sess = null;
                    } else {
                        iCached.delete(CacheConstants.SESSION_WEB.concat(userId));
                        sess = null;
                    }
                }
            }
            usrRegInfoPo.setUserId(userId1);
        } else {// 非免密
            logger.debug("非免密用户-----------------");
            if (StringUtils.isBlank(userId)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户ID");
            } else {
                usrRegInfoPo.setUserId(userId);
            }
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
        if (flag) {// 免密
            if (StringUtils.isNotBlank(sessionId)) {
                if (DataBaseConstants_USR.CHNL_ID_APP.equals(chnlId)) {
                    int sessionValidMinute =
                            Integer.valueOf(DipperParm.getParmByKey(
                                CmparmConstants.SESSION_APP_INVALID_MINUTE).toString());
                    iCached.set(CacheConstants.SESSION_APP.concat(usrRegInfoPo.getUserId()), sessionId,
                        sessionValidMinute * 60);
                } else if (DataBaseConstants_USR.CHNL_ID_WEB.equals(chnlId)) {
                    int sessionValidMinute =
                            Integer.valueOf(DipperParm.getParmByKey(
                                CmparmConstants.SESSION_WEB_INVALID_MINUTE).toString());
                    iCached.set(CacheConstants.SESSION_WEB.concat(usrRegInfoPo.getUserId()), sessionId,
                        sessionValidMinute * 60);
                }
                // String session = UUID.randomUUID().toString().replace("-",
                // "");
                // if (chnlId.equals(DataBaseConstants_USR.CHNL_ID_APP)) {
                // int sessionValidMinute =
                // Integer.valueOf(DipperParm.getParmByKey(CmparmConstants.SESSION_APP_INVALID_MINUTE)
                // .toString());
                // iCached.set(CacheConstants.SESSION_APP.concat(usrRegInfoPo.getUserId()),
                // session,
                // sessionValidMinute * 60);
                // } else {
                // int sessionValidMinute =
                // Integer.valueOf(DipperParm.getParmByKey(CmparmConstants.SESSION_WEB_INVALID_MINUTE)
                // .toString());
                // iCached.set(CacheConstants.SESSION_WEB.concat(usrRegInfoPo.getUserId()),
                // session,
                // sessionValidMinute * 60);
                // }
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0054);
            }
        } else {// 非免密
            if (StringUtils.isNotBlank(sessionId)) {
                if (DataBaseConstants_USR.CHNL_ID_APP.equals(chnlId)) {
                    int sessionValidMinute =
                            Integer.valueOf(DipperParm.getParmByKey(
                                CmparmConstants.SESSION_APP_INVALID_MINUTE).toString());
                    iCached.set(CacheConstants.SESSION_APP.concat(usrRegInfoPo.getUserId()), sess,
                        sessionValidMinute * 60);
                } else if (DataBaseConstants_USR.CHNL_ID_WEB.equals(chnlId)) {
                    int sessionValidMinute =
                            Integer.valueOf(DipperParm.getParmByKey(
                                CmparmConstants.SESSION_WEB_INVALID_MINUTE).toString());
                    iCached.set(CacheConstants.SESSION_WEB.concat(usrRegInfoPo.getUserId()), sess,
                        sessionValidMinute * 60);
                }
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0037);
            }
        }

        if (chnlId.equals(DataBaseConstants_USR.CHNL_ID_WEB)) {// WEB渠道
            userPayPwdFlagSearchDto.setSessionId(iCached.get(
                CacheConstants.SESSION_WEB + usrRegInfoPo.getUserId()).toString());
        } else if (chnlId.equals(DataBaseConstants_USR.CHNL_ID_APP)) {
            userPayPwdFlagSearchDto.setSessionId(iCached.get(
                CacheConstants.SESSION_APP + usrRegInfoPo.getUserId()).toString());
        }
        userPayPwdFlagSearchDto.setUserName(usrRegInfoPo.getUserName());
        userPayPwdFlagSearchDto.setUserId(usrRegInfoPo.getUserId());
        userPayPwdFlagSearchDto.setMobile(usrRegInfoPo.getMobile());
        userPayPwdFlagSearchDto.setUserCertLevel(usrRegInfoPo.getUserCertLevel());
        userPayPwdFlagSearchDto.setRegType(usrRegInfoPo.getRegType());

        UsrBaseInfoPo usrBaseInfoPo = new UsrBaseInfoPo();
        usrBaseInfoPo.setUserId(usrRegInfoPo.getUserId());
        usrBaseInfoPo = daoService.selectOne(usrBaseInfoPo);
        if (usrBaseInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "该用户");
        }
        userPayPwdFlagSearchDto.setCertNo(usrBaseInfoPo.getCertNo());
        userPayPwdFlagSearchDto.setCertName(usrBaseInfoPo.getCertName());
        tradePwd = usrRegInfoPo.getTradePwd();
        AccVbookPo accVbookPo = new AccVbookPo();
        accVbookPo.setUserId(usrRegInfoPo.getUserId());
        accVbookPo = daoService.selectOne(accVbookPo);
        if (accVbookPo == null) {
            if (StringUtils.isBlank(tradePwd)) {
                userPayPwdFlagSearchDto.setPayPwdFlag(DataBaseConstants_USR.PAY_PWD_NO1);
            } else {
                userPayPwdFlagSearchDto.setPayPwdFlag(DataBaseConstants_USR.PAY_PWD_YES);
            }
        } else {
            if (StringUtils.isBlank(tradePwd)) {
                userPayPwdFlagSearchDto.setPayPwdFlag(DataBaseConstants_USR.PAY_PWD_NO2);
            } else {
                userPayPwdFlagSearchDto.setPayPwdFlag(DataBaseConstants_USR.PAY_PWD_YES);
            }
        }
        logger.debug("-------------------------------------end");
        return userPayPwdFlagSearchDto;
    }
}
