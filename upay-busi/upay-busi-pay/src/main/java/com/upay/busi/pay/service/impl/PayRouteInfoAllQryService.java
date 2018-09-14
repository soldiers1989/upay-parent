/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.PayRouteInfoAllQryDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.AtConfigPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**
 * @author shang
 * 获取所有资金通道信息
 * 2016年10月26日
 */
public class PayRouteInfoAllQryService extends AbstractDipperHandler<PayRouteInfoAllQryDto> {

    
    @Resource
    IDaoService daoService;
    @Override
    public PayRouteInfoAllQryDto execute(PayRouteInfoAllQryDto dto, Message msg) throws Exception {
        PayRouteInfoPo route=new PayRouteInfoPo();
        List<PayRouteInfoPo> routeList=daoService.selectList(route);
        if(routeList==null||routeList.size()==0){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0028);
        }
        for(PayRouteInfoPo r:routeList){
            if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(r.getRouteCode())){
                dto.setTransAcctNo0001(r.getTransAcctNo());
                dto.setClrAcctNo0001(r.getClrAcctNo());
                dto.setClrType001(r.getClrType());
                dto.setCallBack001(r.getCallbackRul());
            }
            if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(r.getRouteCode())){
                dto.setTransAcctNo0002(r.getTransAcctNo());
                dto.setClrAcctNo0002(r.getClrAcctNo());
                dto.setClrType002(r.getClrType());
                dto.setCallBack002(r.getCallbackRul());
            }
            if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS.equals(r.getRouteCode())){
            	String callbackRul = r.getCallbackRul();
            	String transCode=dto.getTransCode();
            	if(StringUtils.isNotBlank(transCode)){
            		//当如果是微信通道，需要判断是否使用银联AT通道的回调地址
            		AtConfigPo atConfig=new AtConfigPo();
                	atConfig.setTransCode(transCode);
                	atConfig=daoService.selectOne(atConfig);
            		if(atConfig!=null){
            			if("Y".equals(atConfig.getUseAt())){
            				callbackRul=r.getCallbackRul();
            			}
            		}
            	}
            	
            	
            	
                dto.setTransAcctNo0003(r.getTransAcctNo());
                dto.setClrAcctNo0003(r.getClrAcctNo());
                dto.setClrType003(r.getClrType());
                dto.setCallBack003(callbackRul);
            }
            if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(r.getRouteCode())){
                dto.setTransAcctNo0004(r.getTransAcctNo());
                dto.setClrAcctNo0004(r.getClrAcctNo());
                dto.setClrType004(r.getClrType());
                dto.setCallBack004(r.getCallbackRul());
            }
        }
        return dto;
    }

}
