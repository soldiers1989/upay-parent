package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.busi.pay.service.dto.PayQueryCardBinOfAAADto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayCardbinInfoPo;
import com.upay.dao.po.pay.PayRouteCtlInfoPo;
import com.upay.dao.po.pay.PayRoutePermInfoPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 类或接口的功能说明 类或接口的使用说明 :根据卡bin识别鉴权方式 根据卡号查询支持的鉴权通道
 * 
 * @author: liyulong
 * 
 */
public class PayQueryCardBinOfService extends AbstractDipperHandler<PayQueryCardBinOfAAADto> {

    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(PayQueryCardBinOfService.class);


    @Override
    public PayQueryCardBinOfAAADto execute(PayQueryCardBinOfAAADto PayQueryCardBinOfAAADto, Message message)
            throws Exception {
    	String bindAcctNo = PayQueryCardBinOfAAADto.geteBindAcctNo();
        if (StringUtils.isBlank(bindAcctNo)) {
            // logger.error("卡bin识别鉴权方式:卡号为空");
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "卡号");
        }
        String sqlId = PayCardbinInfoPo.class.getName() + ".selectCardBinLenList";
        HashMap<String, Object> cardNoLenMap=new HashMap<String, Object>();
        cardNoLenMap.put("cardNoLen", bindAcctNo.length());
        
        List<Integer> list = this.daoService.selectList(sqlId, cardNoLenMap);// 查询卡BIN长度
        if(null==list||list.size()==0){
        	ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "卡号长度");
        }
        for (int cardBinLen : list) {
            // 按长度截取卡BIN（从长到短），获得数据跳出循环
            if (PayQueryCardBinOfAAADto.geteBindAcctNo().length() <= cardBinLen) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "卡号长度");
            }
        }
        PayQueryCardBinOfAAADto.seteBindFlag(DataBaseConstans_ACC.BIND_FLAG_CHNL);// 第三方鉴权
        logger.debug("-----------------------------end");
        return PayQueryCardBinOfAAADto;
    }
}
