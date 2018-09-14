package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.SinglePaymentOrderChkDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.DsConfPo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 支付方式参数读取
 *
 * @liudan
 */
public class SinglePaymentParamChkService extends AbstractDipperHandler<SinglePaymentOrderChkDto> {

    private static final Logger logger = LoggerFactory.getLogger(SinglePaymentParamChkService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public SinglePaymentOrderChkDto execute(SinglePaymentOrderChkDto dto, Message message) throws Exception {
        logger.info("从系统参数表（T_GNR_PARM） 读取  route  begin--------------");
        //判断 代付走  银联代付    还是  中金代付      判断代收走  银联代收   还是银联无跳转支付    还是中金代收
        DsConfPo dsConfPo=new DsConfPo();
        dsConfPo.setCallerChnlId(dto.getChnlId());
        dsConfPo.setTransCode(dto.getTransCode());
        dsConfPo=daoService.selectOne(dsConfPo);
        if(null==dsConfPo){
        	ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"调用方的代收付渠道未配置！");
        }
        
//        String payRouteMethod = dto.getPayRouteMethod();
        String payRouteMethod =dsConfPo.getPayRoute();
        dto.setPayRouteMethod(payRouteMethod);
        logger.info(" pay_route method   --------------{}", dto.getPayRouteMethod());
        if (StringUtils.isNotBlank(payRouteMethod)) {
            if (CommonBaseConstans_PAY.UNION_PAY_PRIORITY_PAY.equals(payRouteMethod)
                    || CommonBaseConstans_PAY.UNION_PAY_PRIORITY_COLLECTION.equals(payRouteMethod)
                    || CommonBaseConstans_PAY.UNION_PAY_NON_JUMP_PAY.equals(payRouteMethod)
                    || CommonBaseConstans_PAY.ZJ_PAY_PRIORITY_PAY.equals(payRouteMethod)
                    || CommonBaseConstans_PAY.ZJ_PAY_PRIORITY_COLLECTION.equals(payRouteMethod)) {
                if (CommonBaseConstans_PAY.UNION_PAY_PRIORITY_PAY.equals(payRouteMethod)
                        || CommonBaseConstans_PAY.UNION_PAY_PRIORITY_COLLECTION.equals(payRouteMethod)
                        || CommonBaseConstans_PAY.UNION_PAY_NON_JUMP_PAY.equals(payRouteMethod)) {
                    dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);
                } else if (CommonBaseConstans_PAY.ZJ_PAY_PRIORITY_PAY.equals(payRouteMethod)
                        || CommonBaseConstans_PAY.ZJ_PAY_PRIORITY_COLLECTION.equals(payRouteMethod)) {
                    dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
                }
            } else {
                logger.info("未获取支付渠道payRouteMethod参数  值分别为 银联代付（0001）中金代付（0002） 银联代收（0003）无跳转支付（0004）中金代收（0005）");
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "未获取支付渠道payRouteMethod参数  值分别为 银联代付（0001）中金代付（0002） 银联代收（0003）无跳转支付（0004）中金代收（0005）");
            }
        } else {
            logger.info("未获取支付渠道payRouteMethod参数  值分别为 银联代付（0001）中金代付（0002） 银联代收（0003）无跳转支付（0004）中金代收（0005）");
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "未获取支付渠道payRouteMethod参数  值分别为 银联代付（0001）中金代付（0002） 银联代收（0003）无跳转支付（0004）中金代收（0005）");
        }
        logger.info("从系统参数表（T_GNR_PARM） 读取  route  end   route值为[{}]--------------", dto.getRouteCode());
        return dto;
    }
}
