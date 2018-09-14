/**
 *
 */
package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.PlatformAcctInfoQryDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayRouteInfoPo;


/**
 * 凭条资金通道信息查询
 *
 * @author zhanggr
 */
public class PlatformAcctInfoQryService extends AbstractDipperHandler<PlatformAcctInfoQryDto> {
    private static final Logger LOG = LoggerFactory.getLogger(PlatformAcctInfoQryService.class);
    @Resource
    private IDaoService daoService;


    @Override
    public PlatformAcctInfoQryDto execute(PlatformAcctInfoQryDto dto, Message msg) throws Exception {
        LOG.debug("核心资金通道信息查询");
        PayRouteInfoPo payRouteInfo = new PayRouteInfoPo();
        payRouteInfo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
        payRouteInfo = daoService.selectOne(payRouteInfo);
        if (null == payRouteInfo) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "核心资金通道信息");
        }
        // 平台资金池账户
        dto.setCapitalPoolAcctNo(payRouteInfo.getTransAcctNo());
        // 平台待清算账户
        dto.setClearlyAcctNo(payRouteInfo.getClrAcctNo());
        // 平台手续费支出户
        dto.setFeeExpenseeAcctNo(payRouteInfo.getRemark1());
        // 平台手续费收入户
        dto.setFeeIncomeAcctNo(payRouteInfo.getRemark2());
        // 平台垫资账户    暂时没有用到!!!
//        dto.setMatEndowmentAcct(payRouteInfo.getMatEndowmentAcct());
        //设置清算帐户名称
        dto.setPayerName(payRouteInfo.getRouteName());
        return dto;
    }
}
