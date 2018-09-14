package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.DealWeiXinRespDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayRedpackListPo;

public class DealWeiXinRespService extends AbstractDipperHandler<DealWeiXinRespDto> {
    private static final Logger LOG = LoggerFactory.getLogger(DealWeiXinRespService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public DealWeiXinRespDto execute(DealWeiXinRespDto dto, Message msg)
            throws Exception {
    	String orderNo = dto.getTransSubSeq();
    	if (StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "流水号");
        }
    	PayRedpackListPo payRedpackList = new PayRedpackListPo();
    	payRedpackList.setOrderNo(orderNo);
    	payRedpackList = daoService.selectOne(payRedpackList);
    	
        String returnCode = dto.getReturnCode();
        String resultCode = dto.getResultCode();//支付通知结果,业务响应代码

        if (DataBaseConstants_PAY.PAY_RETURN_SUCCES.equals(returnCode) && DataBaseConstants_PAY.PAY_RESULT_SUCCES.equals(resultCode)) {
        	String sendListid = dto.getSendListid();
        	if (StringUtils.isBlank(sendListid)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "微信单号");
            }
        	payRedpackList.setRemark1(returnCode);
        	payRedpackList.setRemark2(resultCode);
        	payRedpackList.setOrderState(DataBaseConstants_PAY.RED_PACKET_STATE_SUCC);
        	payRedpackList.setSendListid(sendListid);
        	daoService.update(payRedpackList);
        } else {
        	payRedpackList.setRemark1(returnCode);
        	payRedpackList.setRemark2(resultCode);
        	payRedpackList.setOrderState(DataBaseConstants_PAY.RED_PACKET_STATE_FAIL);
        	daoService.update(payRedpackList);
        }
        
        return dto;
    }


}
