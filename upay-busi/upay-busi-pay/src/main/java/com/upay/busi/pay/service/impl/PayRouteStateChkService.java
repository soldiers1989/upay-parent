package com.upay.busi.pay.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.PayRouteStateChkDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.PayRoutePermInfoPo;


/**
 * 资金通道状态检查
 * 
 * @author liyulong
 * 
 */
public class PayRouteStateChkService extends AbstractDipperHandler<PayRouteStateChkDto> {

    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(PayRouteStateChkService.class);


    @Override
    public PayRouteStateChkDto execute(PayRouteStateChkDto payRouteStateChkDto, Message msg) throws Exception {
        // 取出输入项
        String routeCode = payRouteStateChkDto.getRouteCode();// 通道代码
        String routeFuncCode = payRouteStateChkDto.getRouteFuncCode();// 功能代码

        if (StringUtils.isBlank(routeCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "通道代码");
        }

        if (StringUtils.isBlank(routeFuncCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "功能代码");
        }

        PayRoutePermInfoPo payRoutePermInfoPo = new PayRoutePermInfoPo();
        payRoutePermInfoPo.setRouteCode(routeCode);
        payRoutePermInfoPo.setRouteFuncCode(routeFuncCode);
        payRoutePermInfoPo = daoService.selectOne(payRoutePermInfoPo);
        if (payRoutePermInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0028);
        }
        String transSyncFlag = null;// 交易同步/异步标识
        String routeStat = null;// 通道状态
        String statTime = null;// 开放开始时间
        String endTime = null;// 开放截止时间
        transSyncFlag = payRoutePermInfoPo.getTransSyncFlag();
        routeStat = payRoutePermInfoPo.getRouteStat();
        statTime = payRoutePermInfoPo.getStatTime();
        endTime = payRoutePermInfoPo.getEndTime();

        if (routeStat != null && routeStat.equals(DataBaseConstants_PAY.ROUTE_STAT_STOP)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0007);
        }
        String time = DateUtil.format(new Date(), DataBaseConstans_ACC.DATE_FORMAT_HHMMSS);
        // logger.debug(time.compareTo(statTime) + "");
        // logger.debug(time.compareTo(endTime) + "");
        if (StringUtils.isNotBlank(endTime) && StringUtils.isNotBlank(statTime)) {
            if (time.compareTo(statTime) <= 0 || endTime.compareTo(time) <= 0) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0008);
            }
        }
        payRouteStateChkDto.setRouteCode(routeCode);
        payRouteStateChkDto.setTransSyncFlag(transSyncFlag);
        logger.debug("------------------------------end");
        return payRouteStateChkDto;
    }

}
