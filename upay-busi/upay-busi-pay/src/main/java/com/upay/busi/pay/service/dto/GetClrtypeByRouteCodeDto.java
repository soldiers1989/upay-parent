/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2016年11月26日
 */
public class GetClrtypeByRouteCodeDto extends BaseDto {
    private String routeCode;//通道代码
    private String clrType;//通道清算方式
    
    private String clrType001;//核心清算方式
    private String clrType002;//银联清算方式
    private String clrType003;//微信清算方式
    private String clrType004;//中金清算方式
    
    
    
    public String getClrType001() {
        return clrType001;
    }
    public void setClrType001(String clrType001) {
        this.clrType001 = clrType001;
    }
    public String getClrType002() {
        return clrType002;
    }
    public void setClrType002(String clrType002) {
        this.clrType002 = clrType002;
    }
    public String getClrType003() {
        return clrType003;
    }
    public void setClrType003(String clrType003) {
        this.clrType003 = clrType003;
    }
    public String getClrType004() {
        return clrType004;
    }
    public void setClrType004(String clrType004) {
        this.clrType004 = clrType004;
    }
    public String getRouteCode() {
        return routeCode;
    }
    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }
    public String getClrType() {
        return clrType;
    }
    public void setClrType(String clrType) {
        this.clrType = clrType;
    }
    
    
    
}
