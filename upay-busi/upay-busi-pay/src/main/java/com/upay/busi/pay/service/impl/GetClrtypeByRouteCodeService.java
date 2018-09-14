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
import com.upay.busi.pay.service.dto.GetClrtypeByRouteCodeDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**
 * @author shang
 * 2016年11月26日
 */
public class GetClrtypeByRouteCodeService extends AbstractDipperHandler<GetClrtypeByRouteCodeDto> {

    
    @Resource
    IDaoService daoService;
    
    @Override
    public GetClrtypeByRouteCodeDto execute(GetClrtypeByRouteCodeDto dto, Message message) throws Exception {
        if(StringUtils.isBlank(dto.getRouteCode())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "资金通道 ");
        }
        if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(dto.getRouteCode())){
            if(StringUtils.isNotBlank(dto.getClrType001())){
                dto.setClrType(dto.getClrType001());
            }else{
                dto.setClrType(this.getClrType(dto.getRouteCode()));
            }
        }
        if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(dto.getRouteCode())){
            if(StringUtils.isNotBlank(dto.getClrType002())){
                dto.setClrType(dto.getClrType002());
            }else{
                dto.setClrType(this.getClrType(dto.getRouteCode()));
            }
        }
        if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS.equals(dto.getRouteCode())){
            if(StringUtils.isNotBlank(dto.getClrType003())){
                dto.setClrType(dto.getClrType003());
            }else{
                dto.setClrType(this.getClrType(dto.getRouteCode()));
            }
        }
        if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(dto.getRouteCode())){
            if(StringUtils.isNotBlank(dto.getClrType004())){
                dto.setClrType(dto.getClrType004());
            }else{
                dto.setClrType(this.getClrType(dto.getRouteCode()));
            }
        }
        return dto;
    }
    public String getClrType(String routeCode){
        PayRouteInfoPo route=new PayRouteInfoPo();
        route.setRouteCode(routeCode);
        route=daoService.selectOne(route);
        if(route==null){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "资金 通道");
        }
        return route.getClrType();
    }
}
