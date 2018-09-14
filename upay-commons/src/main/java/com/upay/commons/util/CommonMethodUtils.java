/**
 * 
 */
package com.upay.commons.util;

import payment.api.util.GUIDGenerator;

import com.pactera.dipper.commons.exception.ExInfo;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
/**
 * @author shang
 * 2016年9月26日
 */
public class CommonMethodUtils {
    /**
     * 根据支付方式获取资金通道
     * @param payType
     * @return
     */
    public static String getRouteCodeByPayType(String payType){
        String routeCode=null;
        switch (payType){
        case DataBaseConstants_PAY.T_PAY_TYPE_BANK_QUICK:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_HOST;
            break;
        case DataBaseConstants_PAY.T_PAY_TYPE_OTHER_QUICK:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY;
            break;
        case DataBaseConstants_PAY.T_PAY_TYPE_PLATFORM_ACC:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_HOST;
            break;
        case DataBaseConstants_PAY.T_PAY_TYPE_WEIXIN_QR_CODE:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS;
            break;
        case DataBaseConstants_PAY.T_PAY_TYPE_PUBLIC_NO:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS;
            break;
        case DataBaseConstants_PAY.T_PAY_TYPE_CARD_PAY:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS;
            break;
        case DataBaseConstants_PAY.T_PAY_TYPE_UNIONPAY_GATEWAY:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS;
            break;
        case DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_PERSONAL:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY;
            break;
        case DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_COMPANY:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY;
            break;
        case DataBaseConstants_PAY.T_PAY_TYPE_ACP_WR_CODE:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS;
            break;    
        case DataBaseConstants_PAY.T_PAY_TYPE_ACP_QR_CODE:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS;
            break;      
        case DataBaseConstants_PAY.T_PAY_TYPE_ALIPAY_QR_CODE:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_ALIPAY;
            break;
        case DataBaseConstants_PAY.T_PAY_TYPE_ALIPAY_CARD_PAY:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_ALIPAY;
            break;
        case DataBaseConstants_PAY.T_PAY_TYPE_ALIPAY_TOGETHER:
            routeCode=DataBaseConstants_PAY.ROUTE_CODE_ALIPAY;
            break;
        }
        if(routeCode==null){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0033);
        }
        return routeCode;
    }
    public static String getZJSubSeq(){
    	String seqNo = null;
		try {
			seqNo = GUIDGenerator.genGUID();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return seqNo;
    }
}
