/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.QueryOrderDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * @author shang
 * 2016年11月7日
 */
public class QueryOrderService extends AbstractDipperHandler<QueryOrderDto> {

    
    @Resource
    IDaoService daoService;

    @Override
    public QueryOrderDto execute(QueryOrderDto dto, Message msg) throws Exception {
        SimpleDateFormat SIM_YMD=new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat SIM_YMDHMS=new SimpleDateFormat("yyyyMMddHHmmss");
        if(StringUtils.isBlank(dto.getOrderNo())&&StringUtils.isBlank(dto.getOuterOrderNo())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单查询参数");
        }
        PayOrderListPo order=new PayOrderListPo();
        if(StringUtils.isNotBlank(dto.getMerNo())){            
            order.setMerNo(dto.getMerNo());
        }
        if(StringUtils.isNotBlank(dto.getSecMerNo())){
            order.setSecMerNo(dto.getSecMerNo());
        }
        if(StringUtils.isNotBlank(dto.getOrderNo())){            
            order.setOrderNo(dto.getOrderNo());
        }else if(StringUtils.isNotBlank(dto.getOuterOrderNo())){
            order.setOuterOrderNo(dto.getOuterOrderNo());
        }
        order=daoService.selectOne(order);
        if(order==null){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, dto.getOrderNo());
        }
        dto.setChnlId(order.getChnlId());
        dto.setPayServicType(order.getPayServicType());
        dto.setMerDate(order.getMerDate()==null?null:SIM_YMD.format(order.getMerDate()));
        dto.setMerSeq(order.getMerSeq());
        dto.setMerNo(order.getMerNo());
        dto.setSecMerNo(order.getSecMerNo());
        dto.setOuterOrderNo(order.getOuterOrderNo());
        dto.setOuterOrderStartDate(order.getOuterOrderStartDate()==null?null:SIM_YMD.format(order.getOuterOrderStartDate()));
        dto.setOuterOrderEndDate(order.getOuterOrderEndDate()==null?null:SIM_YMD.format(order.getOuterOrderEndDate()));
        dto.setOrderType(order.getOrderType());
        dto.setPayType(order.getPayType());
        dto.setOrderNo(order.getOrderNo());
        dto.setOrderName(order.getOrderName());
        dto.setTransCode(order.getTransCode());
        dto.setUserId(order.getUserId());
        dto.setOrderDate(order.getOrderDate()==null?null:SIM_YMD.format(order.getOrderDate()));
        dto.setOrderTime(order.getOrderTime()==null?null:SIM_YMDHMS.format(order.getOrderTime()));
        dto.setOrderLmtTime(order.getOrderLmtTime());
        dto.setCurr(order.getCurr());
        dto.setTransAmt(order.getTransAmt()==null?"":order.getTransAmt().toString());
        dto.setOtherTranAmt(order.getOtherTranAmt()==null?"":order.getOtherTranAmt().toString());
        dto.setProductAmt(order.getProductAmt()==null?"":order.getProductAmt().toString());
        dto.setMerFeeAmt(order.getMerFeeAmt()==null?"":order.getMerFeeAmt().toString());
        dto.setFeeAmt(order.getFeeAmt()==null?"":order.getFeeAmt().toString());
        dto.setOriDate(order.getOriDate()==null?null:SIM_YMD.format(order.getOriDate()));
        dto.setOriOrderNo(order.getOriOrderNo());
        dto.setEjectAmt(order.getEjectAmt()==null?"":order.getEjectAmt().toString());
        dto.setOrderStat(order.getOrderStat());
        if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(order.getTransType())){
            dto.setRefundStat(order.getOrderStat());
        }
        dto.setSpbillCreateIp(order.getSpbillCreateIp());
        dto.setNotifyUrl(order.getNotifyUrl());
        dto.setReturnUrl(order.getReturnUrl());
        dto.setTransComments(order.getTransComments());
        dto.setLastUpdateTime(order.getLastUpdateTime()==null?null:SIM_YMDHMS.format(order.getLastUpdateTime()));
        dto.setRemark1(order.getRemark1());
        dto.setRemark2(order.getRemark2());
        dto.setRemark3(order.getRemark3());
        dto.setChkFlag(order.getChkFlag());
        dto.setChkBatchNo(order.getChkBatchNo());
        dto.setStlFlag(order.getStlFlag());
        dto.setChkDate(order.getChkDate()==null?null:SIM_YMD.format(order.getChkDate()));
        dto.setStlBatchNo(order.getStlBatchNo());
        dto.setStlDate(order.getStlDate()==null?null:SIM_YMD.format(order.getStlDate()));
        dto.setOrderDes(order.getOrderDes());
        dto.setOpenId(order.getOpenId());
        dto.setPayTime(order.getPayTime()==null?null:SIM_YMDHMS.format(order.getPayTime()));
        dto.setPayAcctNo(order.getPayAcctNo());
        dto.setTransType(order.getTransType());
        return dto;
    }
//    /**
//     * 根据订单状态获取状态说明
//     * @return
//     */
//    public static String getStrByOrderStat(String orderStat){
//        String str=null;
//        switch (orderStat){
//        case DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y:
//            str="支付成功";
//            break;
//        case DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N:
//            str="未支付";
//            break;
//        case DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING:
//            str="支付中";
//            break;
//        case DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL:
//            str="支付失败";
//            break;
//        case DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_TOC:
//            str="超时关闭";
//            break;
//        case DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_HC:
//            str="手工关闭";
//            break;
//        case DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP:
//            str="确认收货";
//            break;
//        case DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_AP:
//            str="预约支付";
//            break;
//        case DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP:
//            str="待确认收货";
//            break;
//        }
//        if(str==null){
//            //订单状态错误
//        }
//        return str;
//    }
//    /**
//     * 根据渠道代码获取渠道
//     * @return
//     */
//    public static String getStrByChnlId(String chnl){
//        String str=null;
//        switch (chnl) {
//        case CommonConstants_GNR.CHNL_ID_APP:
//            str="APP";
//            break;
//        case CommonConstants_GNR.CHNL_ID_WEB:
//            str="WEB";
//            break;
//        }
//        if(str==null){
//            
//        }
//        return str;
//    }
//    /**
//     * 根据服支付服务类型获取其说明
//     * @param payService
//     * @return
//     */
//    public static String getStrByPayServiceCode(String payService){
//        String str=null;
//        switch (payService) {
//        case DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE:
//            str="担保交易";
//            break;
//        case DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_FORTHWITH:
//            str="及时到账";
//            break;
//        }
//        if(str==null){
//            
//        }
//        return str;
//    }
//    /**
//     * 根据订单类型获取其说明
//     * @param orderType
//     * @return
//     */
//    public static String getStrByOrderType(String orderType){
//        String str=null;
//        switch (orderType) {
//        case DataBaseConstants_PAY.T_ORDER_LIST_TYPE_NO:
//            str="非订单管理";
//            break;
//        case DataBaseConstants_PAY.T_ORDER_LIST_TYPE_UPAY:
//            str="平台订单";
//            break;
//        case DataBaseConstants_PAY.T_ORDER_LIST_TYPE_OUT_PRE:
//            str="外部预生成订单";
//            break;
//        case DataBaseConstants_PAY.T_ORDER_LIST_TYPE_OUT_ONLINE:
//            str="外部联机生成订单";
//            break;
//        }
//        if(str==null){
//            
//        }
//        return str;
//    }
//    /**
//     * 根据支付方式获取其说明
//     * @param payType
//     * @return
//     */
//    public static String getStrByPayType(String payType){
//        String str=null;
//        switch (payType) {
//        case DataBaseConstants_PAY.T_PAY_TYPE_BANK_QUICK:
//            str="本行卡快捷";
//            break;
//        case DataBaseConstants_PAY.T_PAY_TYPE_OTHER_QUICK:
//            str="他行卡快捷";
//            break;
//        case DataBaseConstants_PAY.T_PAY_TYPE_PLATFORM_ACC:
//            str="平台账户";
//            break;
//        case DataBaseConstants_PAY.T_PAY_TYPE_WEIXIN_QR_CODE:
//            str="微信扫码";
//            break;
//        case DataBaseConstants_PAY.T_PAY_TYPE_PUBLIC_NO:
//            str="微信公众号";
//            break;
//        case DataBaseConstants_PAY.T_PAY_TYPE_UNIONPAY_GATEWAY:
//            str="银联网关";
//            break;
//        case DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_PERSONAL:
//            str="个人网银";
//            break;
//        case DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_COMPANY:
//            str="企业网银";
//            break;
//        }
//        if(str==null){
//            
//        }
//        return str;
//    }

}
