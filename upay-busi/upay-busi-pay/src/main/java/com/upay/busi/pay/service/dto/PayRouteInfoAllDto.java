/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

import java.util.List;
import java.util.Map;


/**
 * 资金通道信息查询所有
 * 
 * @author zhanggr
 * 
 */
public class PayRouteInfoAllDto extends BaseDto {
    private String routeCode;// 资金通道代码
    private String routeName;// 通道名称

    private List<Map<String, Object>> payRouteInfos;// 渠道集合

    public List<Map<String, Object>> getPayRouteInfos() {
        return payRouteInfos;
    }

    public void setPayRouteInfos(List<Map<String, Object>> payRouteInfos) {
        this.payRouteInfos = payRouteInfos;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}
