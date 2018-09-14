/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.pay.service.dto.TranAmtChangDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;


/**
 * 金额转换 //0：分到元 1:元到分
 * 
 * @author zhanggr
 * 
 */
public class TranAmtChangService extends AbstractDipperHandler<TranAmtChangDto> {
	private BigDecimal maxAmount=new BigDecimal("999999999999999");
    @Override
    public TranAmtChangDto execute(TranAmtChangDto tranAmtChangDto, Message message) throws Exception {

        String changFlag = tranAmtChangDto.getChangFlag();
        String totalFee = tranAmtChangDto.getTotalFee();
        
        if (StringUtils.isBlank(changFlag)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "金额转换标识");
        }
        if (changFlag.equals(DataBaseConstants_PAY.MONEY_CHANG_FLAG_ZERO)) {
            if (null==totalFee) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "交易金额");
            }
            tranAmtChangDto.setTransAmt(MoneyUtil.transferF2Y(new BigDecimal(totalFee), 2));
        } else if (changFlag.equals(DataBaseConstants_PAY.MONEY_CHANG_FLAG_ONE)) {
        	BigDecimal transAmt = tranAmtChangDto.getTransAmt();
        	if(null!=transAmt){
        		//没有与前端交互的的情况
        		tranAmtChangDto.setTotalFee(String.valueOf(MoneyUtil.transferY2F(transAmt, 2)));
        	}else{
        		//与前端有交互的情况
        		String transAmtStr = tranAmtChangDto.getTransAmtStr();
            	if(StringUtils.isBlank(transAmtStr)){
            		ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "交易金额");
            	}           	
            	BigDecimal transAmount = new BigDecimal(transAmtStr);
        		tranAmtChangDto.setTransAmt(transAmount);
        		tranAmtChangDto.setTotalFee(String.valueOf(MoneyUtil.transferY2F(transAmount, 2)));
        	}
        	if(null!=tranAmtChangDto.getTotalFee()){
        		BigDecimal fee=new BigDecimal(tranAmtChangDto.getTotalFee());
        		if(fee.compareTo(maxAmount)>0){
        			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "传入第三方接口金额超限");
        		}
        	}
            
        }
        
        
        return tranAmtChangDto;
    }

}
