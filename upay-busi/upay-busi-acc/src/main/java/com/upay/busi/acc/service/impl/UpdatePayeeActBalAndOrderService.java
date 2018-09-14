/**
 * 
 */
package com.upay.busi.acc.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.UpdateAccBindingDto;
import com.upay.busi.acc.service.dto.UpdatePayeeActBalAndOrderDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.acc.AccBindBookPo;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * @author liu
 * 2017年7月15日
 * 平台转账失败时，但付款方余额己回退，需要扣减收款方的账户余额
 */
public class UpdatePayeeActBalAndOrderService extends AbstractDipperHandler<UpdatePayeeActBalAndOrderDto> {

    @Resource
    private IDaoService daoService;
    
    @Override
    public UpdatePayeeActBalAndOrderDto execute(UpdatePayeeActBalAndOrderDto dto, Message message) throws Exception {
        String payeeVacctNo = dto.getPayeeVacctNo();
        String payeeOrderNo = dto.getPayeeOrderNo();
        BigDecimal transAmt = dto.getTransAmt();
    	
        AccVbookPo vBook=new AccVbookPo();
        vBook.setVacctNo(payeeVacctNo);
        vBook = daoService.selectOne(vBook);
        if(vBook!=null){
        	//减收款账户余额
        	AccVbookPo whereVBook=new AccVbookPo();
        	whereVBook.setVacctNo(payeeVacctNo);
            
        	AccVbookPo setVBook=new AccVbookPo();
        	setVBook.setAcctBal(MoneyUtil.subtract(vBook.getAcctBal(), transAmt, RoundingMode.HALF_UP));
			
			daoService.update(setVBook,whereVBook);
        }
        
        PayOrderListPo payeeOrder=new PayOrderListPo();
        payeeOrder.setOrderNo(payeeOrderNo);
        payeeOrder=daoService.selectOne(payeeOrder);
        if(null!=payeeOrder){
        	//更新收款方账户为支付失败
        	PayOrderListPo wherePayeeOrder=new PayOrderListPo();
        	wherePayeeOrder.setOrderNo(payeeOrderNo);
            
            PayOrderListPo setPayeeOrder=new PayOrderListPo();
            setPayeeOrder.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING);
            
            daoService.update(setPayeeOrder,wherePayeeOrder);
            
            PayFlowListPo payeeFlowList=new PayFlowListPo();
            payeeFlowList.setOrderNo(payeeOrderNo);
            payeeFlowList=daoService.selectOne(payeeFlowList);//收款方只有一条流水，固用select One
            if(null!=payeeFlowList){
            	//更新平台转账收款方流水为失败
            	PayFlowListPo wherePayeeFlowList=new PayFlowListPo();
            	wherePayeeFlowList.setOrderNo(payeeOrderNo);
            	
            	PayFlowListPo setPayeeFlowList=new PayFlowListPo();
            	setPayeeFlowList.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
            	
            	daoService.update(setPayeeFlowList,wherePayeeFlowList);
            }
        }
        return dto;
    }

}
