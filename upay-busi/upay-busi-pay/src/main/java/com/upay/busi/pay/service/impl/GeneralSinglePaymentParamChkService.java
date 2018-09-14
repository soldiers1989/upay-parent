package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.pay.service.dto.SinglePaymentOrderChkDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.gnr.GnrTransConfPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.AtConfigPo;
import com.upay.dao.po.pay.DsConfPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 支付方式参数读取
 *
 * @liudan
 */
public class GeneralSinglePaymentParamChkService extends AbstractDipperHandler<SinglePaymentOrderChkDto> {

    private static final Logger logger = LoggerFactory.getLogger(GeneralSinglePaymentParamChkService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public SinglePaymentOrderChkDto execute(SinglePaymentOrderChkDto dto, Message message) throws Exception {
    	   logger.info("从配置表口 读取  route  begin--------------");

           GnrParmPo gnrParmPoPo = new GnrParmPo();
           String payRouteMethod = dto.getPayRouteMethod();
           if (StringUtils.isNotBlank(payRouteMethod)) {
               gnrParmPoPo.setParmId(payRouteMethod);
           } else {
               ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "请在pay_param中初始化 id为initPayMethod  的 payRouteMethod字段 值为 'WECHAT_USE_AT_ROUTE（微信是否使用银联AT通道）',  或者 'ALIPAY_USE_AT_ROUTE（支付宝是否使用银联AT通道）',    ");
           }
           gnrParmPoPo = daoService.selectOne(gnrParmPoPo);
           if(null!=gnrParmPoPo){
        	   dto.setIsAt(gnrParmPoPo.getParmValue());
           }else{
        	   ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"  未配置是否使用AT通道参数");
           }
           String isAt=dto.getIsAt();
           
           //为实现商户分批切银联AT 或网联AT  添加以下逻辑    当总开关走的是直接时，则检查商户的配置是否走银联 或网联，或者直联
           if(DataBaseConstants_PAY.DIRECT.equals(isAt)){
        	   String merNo = dto.getMerNo();
        	   if(StringUtils.isBlank(merNo)){
        		    // 因为这个原子服务是公用参数  支付和  三方回调公用，固回调时没有商户号，必须通过流水号去查询对应订单获取商户号
        		    String transSubSeq=dto.getTransSubSeq();
        		    if(StringUtils.isNotBlank(transSubSeq)){
        		    	PayFlowListPo flowListPo = new PayFlowListPo();
            	        flowListPo.setTransSubSeq(transSubSeq);
            	        flowListPo = daoService.selectOne(flowListPo);
            	        if(flowListPo!=null){
            	        	String orderNo = flowListPo.getOrderNo();
            	        	if(StringUtils.isNotBlank(orderNo)){
            	        		PayOrderListPo order=new PayOrderListPo();
            	        		order.setOrderNo(orderNo);
            	        		order=daoService.selectOne(order);
            	        		if(order!=null){
            	        			dto.setPromoterDeptNo(order.getPromoterDeptNo());//回调时，登记第二笔流水的所属机构，实现流水分机构
            	        			merNo=order.getMerNo();
            	        		}
            	        	}
            	        }
        		    }
        	   }
        	   
               if(StringUtils.isNotBlank(merNo)){
            	   MerBaseInfoPo merBaseInfo=new MerBaseInfoPo();
            	   merBaseInfo.setMerNo(merNo);
            	   merBaseInfo=daoService.selectOne(merBaseInfo);
            	   if(null==merBaseInfo){
            		   ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"商户【"+merNo+"】信息不存在");
            	   }else{
            		   String qrcodeUsrWay=null;
            		   if("WECHAT_USE_AT_ROUTE".equals(payRouteMethod)){//微信支付
            			   qrcodeUsrWay= merBaseInfo.getQrcodeUsrWechat();
            			   logger.debug("微信支付 使用  "+qrcodeUsrWay+"   通道");
            		   }else if("ALIPAY_USE_AT_ROUTE".equals(payRouteMethod)){//支付宝支付
            			   qrcodeUsrWay= merBaseInfo.getQrcodeUsrAlipay();
            			   logger.debug("支付宝支付 使用  "+qrcodeUsrWay+"   通道");
            		   }
            		    
            	dto.setIsAt(qrcodeUsrWay);
            	   }
               }
           }
           
           
           
    	   
//    	   String transCode = dto.getTransCode();
//    	   if (StringUtils.isBlank(transCode)) {
//    		   ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"查询微信或支付宝是否使用AT时,交易代码不能为空!!");
//    	   }
//    	   AtConfigPo atConfigPo=new AtConfigPo();
//    	   atConfigPo.setTransCode(transCode);
//    	   atConfigPo = daoService.selectOne(atConfigPo);
//    	   if(null!=atConfigPo){
//    		   dto.setIsAt(atConfigPo.getUseAt());
//    	   }else{
//    		   GnrTransConfPo transConf=new GnrTransConfPo();
//    		   transConf = daoService.selectOne(transConf);
//    		   String transName=null;
//    		   if(transConf!=null){
//    			   transName=transConf.getTransName();
//    		   }
//    		   ExInfo.throwDipperEx(AppCodeDict.BISACC0000,transName==null?transCode:transName+"  未配置是否使用AT通道参数");
//    	   }
           
           return dto;
    }
}
