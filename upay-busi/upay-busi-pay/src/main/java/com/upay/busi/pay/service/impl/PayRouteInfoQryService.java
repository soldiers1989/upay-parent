/**
 * 
 */
package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.PayRouteInfoQryDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.AtConfigPo;
import com.upay.dao.po.pay.PayRouteInfoPo;


/**
 * 资金通道信息查询
 * 
 * @author zhanggr
 * 
 */
public class PayRouteInfoQryService extends AbstractDipperHandler<PayRouteInfoQryDto> {
    private static final Logger LOG = LoggerFactory.getLogger(PayStateQryService.class);
    @Resource
    private IDaoService daoService;


    @Override
    public PayRouteInfoQryDto execute(PayRouteInfoQryDto payRouteInfoQryDto, Message msg) throws Exception {
        String routeCode = payRouteInfoQryDto.getRouteCode();
        String isAt=payRouteInfoQryDto.getIsAt();
        if (StringUtils.isBlank(routeCode)) {
            LOG.debug("资金通道代码不能为空");
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "资金通道代码");
        }
        PayRouteInfoPo payRouteInfo = new PayRouteInfoPo();
        payRouteInfo.setRouteCode(routeCode);
        payRouteInfo = daoService.selectOne(payRouteInfo);
        if (payRouteInfo == null) {
            LOG.debug("资金通道:" + routeCode + "不存在");
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "资金通道" + routeCode);
        } else {
        	
        	String callbackRul = payRouteInfo.getCallbackRul();
        	String transCode=payRouteInfoQryDto.getTransCode();
        	if(StringUtils.isNotBlank(transCode)){
        		//当如果是微信通道，需要判断是否使用银联AT通道的回调地址
        		if(StringUtils.isNotBlank(isAt)){
        			if(DataBaseConstants_PAY.UNIONPAY.equals(isAt)){
        				callbackRul=payRouteInfo.getUnionAtCallbackRul();
        			}
        		}
        	}
        	
            payRouteInfoQryDto.setAppId(payRouteInfo.getAppId());
            payRouteInfoQryDto.setOrgNo(payRouteInfo.getOrgNo());
            payRouteInfoQryDto.setTransAcctNo(payRouteInfo.getTransAcctNo());
            payRouteInfoQryDto.setClrAcctNo(payRouteInfo.getClrAcctNo());
            payRouteInfoQryDto.setTerminalId(payRouteInfo.getTerminalId());
            payRouteInfoQryDto.setPublicKeyPath(payRouteInfo.getPublicKeyPath());
            payRouteInfoQryDto.setCertFilePath(payRouteInfo.getCertFilePath());
            payRouteInfoQryDto.setCallbackRul(callbackRul);
            payRouteInfoQryDto.setServiceVersion(payRouteInfo.getServiceVersion());
            payRouteInfoQryDto.setAppSecret(payRouteInfo.getAppSecret());
            payRouteInfoQryDto.setRemark1(payRouteInfo.getRemark1());
            payRouteInfoQryDto.setClrType(payRouteInfo.getClrType());
        }
        return payRouteInfoQryDto;
    }
}
