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
import com.upay.busi.pay.service.dto.PayQryOrderNoFormSeqDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;

/**根据流水查询订单信息
 * @author zhanggr
 *
 */
public class PayQryOrderNoFormSeqService extends AbstractDipperHandler<PayQryOrderNoFormSeqDto> {

    @Resource
    private IDaoService daoService;
    
    @Override
    public PayQryOrderNoFormSeqDto execute(PayQryOrderNoFormSeqDto payQryOrderNoFormSeqDto, Message msg) throws Exception {
        
        String transSubSeq = payQryOrderNoFormSeqDto.getTransSubSeq();
        if(StringUtils.isBlank(transSubSeq)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易流水");
        }
        
        PayFlowListPo payFlowListPo = new PayFlowListPo();
        payFlowListPo.setTransSubSeq(transSubSeq);
        payFlowListPo = daoService.selectOne(payFlowListPo);
        if(payFlowListPo!=null){
        	MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
            String merNo=payFlowListPo.getMerNo();
			merBaseInfoPo.setMerNo(merNo);
            merBaseInfoPo = daoService.selectOne(merBaseInfoPo);
            if(null!=merBaseInfoPo){
            	payQryOrderNoFormSeqDto.setMerName(merBaseInfoPo.getMerName()+"("+merBaseInfoPo.getMerAddr()+")");
            	payQryOrderNoFormSeqDto.setMerNameResult(merBaseInfoPo.getMerName());
                String subMerId = merBaseInfoPo.getSubMchId();
//                if(StringUtils.isBlank(subMerId)){
//                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0103, merNo);
//                }else{
                	payQryOrderNoFormSeqDto.setSubMchId(merBaseInfoPo.getSubMchId());  
//                }
            }
        	
          payQryOrderNoFormSeqDto.setOutTradeNo(transSubSeq);
          payQryOrderNoFormSeqDto.setTransAmt(MoneyUtil.moneyFormat(payFlowListPo.getTransAmt()));
          payQryOrderNoFormSeqDto.setTimeEnd(DateUtil.format(payFlowListPo.getTransTime(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS));
        }else{
          ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "此流水："+transSubSeq); 
        }
        return payQryOrderNoFormSeqDto;
    }

}
