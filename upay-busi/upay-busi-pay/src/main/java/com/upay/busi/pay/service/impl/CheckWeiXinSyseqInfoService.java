/**
 * 
 */
package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CheckWeiXinSyseqInfoDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayFlowListPo;

/**根据订单号和资金通道判断微信流水是否已经登记
 * @author zhanggr
 *
 */
public class CheckWeiXinSyseqInfoService extends AbstractDipperHandler<CheckWeiXinSyseqInfoDto> {
    
    @Resource
    private IDaoService daoService;
    
    @Override
    public CheckWeiXinSyseqInfoDto execute(CheckWeiXinSyseqInfoDto checkWeiXinSyseqInfoDto, Message msg) throws Exception {
        
        String orderNo = checkWeiXinSyseqInfoDto.getOrderNo();
        String routeCode = checkWeiXinSyseqInfoDto.getRouteCode();
        if(StringUtils.isBlank(orderNo)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        if(StringUtils.isBlank(routeCode)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "资金通道代码");
        }
        PayFlowListPo flowListPo = new PayFlowListPo();
        flowListPo.setOrderNo(orderNo);
        flowListPo.setRouteCode(routeCode);
        flowListPo = daoService.selectOne(flowListPo);
        String registFlag = CommonBaseConstans_PAY.REGIST_NOT;
        if(flowListPo!=null){
            checkWeiXinSyseqInfoDto.setMerNo(flowListPo.getMerNo());
            checkWeiXinSyseqInfoDto.setOrderDes(flowListPo.getOrderDes());
            checkWeiXinSyseqInfoDto.setTransAmt(flowListPo.getTransAmt());
            checkWeiXinSyseqInfoDto.setTransSubSeq(flowListPo.getTransSubSeq());
            checkWeiXinSyseqInfoDto.setPayUserId(flowListPo.getPayUserId());
            registFlag = CommonBaseConstans_PAY.REGIST_YES;
        }
        checkWeiXinSyseqInfoDto.setRegistFlag(registFlag);
        return checkWeiXinSyseqInfoDto;
    }

}
