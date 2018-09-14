package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.pay.service.dto.SinglePaymentOrderChkDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 支付方式参数读取
 *
 * @liudan
 */
public class UseEsbCoreChkService extends AbstractDipperHandler<SinglePaymentOrderChkDto> {

    private static final Logger logger = LoggerFactory.getLogger(UseEsbCoreChkService.class);

    @Resource
    private IDaoService daoService;

    @Resource
    private ISequenceService seqService;

    @Override
    public SinglePaymentOrderChkDto execute(SinglePaymentOrderChkDto dto, Message message) throws Exception {
    	   logger.info("从配置表口 读取  route  begin--------------");


           GnrParmPo gnrParmPoPo = new GnrParmPo();
           String payRouteMethod = dto.getPayRouteMethod();
           if (StringUtils.isNotBlank(payRouteMethod)) {
               gnrParmPoPo.setParmId(payRouteMethod);
           } else {
               ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "请在pay_param中初始化 id为initPayMethod  的 payRouteMethod字段 值为 'USR_ESB_CORE（是否使用esb核心）'");
           }
           gnrParmPoPo = daoService.selectOne(gnrParmPoPo);
           if(null!=gnrParmPoPo){
        	   dto.setIsEsbCore(gnrParmPoPo.getParmValue());
           }else{
        	   ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"  未配置是否使用AT通道参数");
           }



           //设置ESB规则序列号
        /*if("Y".equals(dto.getIsEsbCore())){
            Map<String,Object> bodysMap = (Map<String, Object>) message.getTarget().getBodys();
            bodysMap.put("destTransSubSeq",seqService.generateEsbNo());
           // message.getTarget().setBody(bodysMap);
        }*/




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
