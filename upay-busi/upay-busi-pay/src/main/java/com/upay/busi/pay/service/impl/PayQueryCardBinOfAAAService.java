package com.upay.busi.pay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


/**
 * 类或接口的功能说明 类或接口的使用说明 :根据卡bin识别鉴权方式 根据卡号查询支持的鉴权通道
 * 
 * @author: liyulong
 * 
 */
public class PayQueryCardBinOfAAAService extends AbstractDipperHandler<PayQueryCardBinOfAAADto> {

    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(PayQueryCardBinOfAAAService.class);


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
            String cardBin = PayQueryCardBinOfAAADto.geteBindAcctNo().substring(0, cardBinLen);
            PayRouteCtlInfoPo PayRouteCtlInfoPo = new PayRouteCtlInfoPo();
            PayRouteCtlInfoPo.setCardBin(cardBin);
            PayRouteCtlInfoPo.setRouteFuncCode(DataBaseConstants_PAY.ROUTE_FUNC_CODE_AAA);
            // PayRouteCtlInfoPo.setRouteStat(DataBaseConstants_PAY.ROUTE_STAT_NORMAL);
            PayRouteCtlInfoPo.addOrder(Order.asc("payPrity"));
            List<PayRouteCtlInfoPo> routelist = daoService.selectList(PayRouteCtlInfoPo);
            int brk = 0;
            if (routelist.size() > 0) {
                for (PayRouteCtlInfoPo routeCtlInfoPo : routelist) {
                    String routeCode = routeCtlInfoPo.getRouteCode();
                    PayRoutePermInfoPo PayRoutePermInfoPo = new PayRoutePermInfoPo();
                    PayRoutePermInfoPo.setRouteCode(routeCode);
                    PayRoutePermInfoPo.setRouteFuncCode(DataBaseConstants_PAY.ROUTE_FUNC_CODE_AAA);
                    PayRoutePermInfoPo.setRouteStat(DataBaseConstants_PAY.ROUTE_STAT_NORMAL);
                    PayRoutePermInfoPo = daoService.selectOne(PayRoutePermInfoPo);
                    if (PayRoutePermInfoPo != null) {
                        SimpleDateFormat simple = new SimpleDateFormat("HHmmss");
                        String moeTime = simple.format(new Date());
                        if (moeTime.compareTo(PayRoutePermInfoPo.getStatTime()) >= 0
                                && PayRoutePermInfoPo.getEndTime().compareTo(moeTime) >= 0) {
                            PayQueryCardBinOfAAADto.setRouteCode(PayRoutePermInfoPo.getRouteCode());
                            PayQueryCardBinOfAAADto.seteBindFlag(DataBaseConstans_ACC.BIND_FLAG_CHNL);// 第三方鉴权
                            brk = 1;
                            break;
                        }
                    }
                }
                if (brk == 1) {
                    break;
                }
            }
        }
        // if (StringUtils.isBlank(PayQueryCardBinOfAAADto.getRouteCode())) {
        // PayQueryCardBinOfAAADto.setvBindFlag(DataBaseConstans_ACC.BIND_FLAG_VERIFYAMT);//
        // 打款验证
        // }
        logger.debug("-----------------------------end");
        return PayQueryCardBinOfAAADto;
    }
}
