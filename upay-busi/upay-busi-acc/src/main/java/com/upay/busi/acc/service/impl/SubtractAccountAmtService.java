package com.upay.busi.acc.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.SubtractAccountAmtDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_FEE;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.acc.AccVbookPo;


/**
 * 更改账户金额
 * 
 * @author shangqiankun
 * @version 创建时间：2016年8月25日 下午6:30:10
 * 此原子服务需要放入事物中
 */
public class SubtractAccountAmtService extends AbstractDipperHandler<SubtractAccountAmtDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public SubtractAccountAmtDto execute(SubtractAccountAmtDto dto, Message message) throws Exception {
        BigDecimal updateAmt = dto.getUpdateAmt();
        String accNo = dto.getAccNo();
        String addOrSub=dto.getAddOrSub();
        if (updateAmt == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易金额");
        } else if (updateAmt.compareTo(BigDecimal.ZERO) <= 0) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0002);
        }
        if (StringUtils.isBlank(accNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "账户号");
        }
        if (StringUtils.isBlank(addOrSub)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "余额修改的增减标识");
        }
        AccVbookPo acc = new AccVbookPo();
        acc.setVacctNo(accNo);
        acc = daoService.selectOne(acc);
        if (acc == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "账户:" + accNo);
        }
        
      //如果是提现、转账，需要检查是否有手续费，如果有则需要在账户余额中扣减相应手续费
        if(DataBaseConstans_ACC.TRANS_CODE_WITHDRAW.equals(dto.getTransCode())||DataBaseConstans_ACC.TRANS_CODE_TRANSFER.equals(dto.getTransCode())){
        	String getType = dto.getGetType();//手续费收起方式：0内扣，1外扣
        	BigDecimal feeAmt = dto.getFeeAmt();
        	if(feeAmt!=null&&feeAmt.signum()>0){
        		switch (getType) {
        		//如果是外扣需要加上手续费
				case DataBaseConstants_FEE.FEE_GET_TYPE_OUTTER:
					//如果不是转账的收款人时，手续费会外扣时需要加上手续费
					String isPayeeAcct = dto.getIsPayeeAcct();
					String isAddFeeAmt=dto.getIsAddFeeAmt();
					if(!DataBaseConstans_ACC.IS_PAYEE_ACCT.equals(isPayeeAcct)||
							(StringUtils.isNotBlank(isAddFeeAmt)&&DataBaseConstans_ACC.ADD_FEE_AMT_YES.equals(isAddFeeAmt))){
						updateAmt=updateAmt.add(feeAmt);
					}
					break;
				//如果是内扣,在调第三方接口的时候需要将金额扣去手续费
				case DataBaseConstants_FEE.FEE_GET_TYPE_INNER:
					BigDecimal thirdAmt=updateAmt.subtract(feeAmt);
					if(thirdAmt.signum()<=0){//当手续费类型为内扣时， 交易金额-手续费>=小于0
						 ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "交易金额："+updateAmt+"  手续费："+feeAmt+"  交易金额小于或等于手续费不能交易，请重新输入.");
					}
					dto.setTotalFee(String.valueOf(MoneyUtil.transferY2F(thirdAmt, 2)));
					break;
				}
        	}
        }
        
        if(CommonBaseConstans_PAY.ACC_AMT_SUB.equals(addOrSub)){
        	BigDecimal frzBal = acc.getFrzBal()==null?BigDecimal.ZERO:acc.getFrzBal();
        	BigDecimal useBal = MoneyUtil.subtract(acc.getAcctBal(), updateAmt.add(frzBal), RoundingMode.HALF_UP);
            if(useBal.compareTo(BigDecimal.ZERO)<0){
                ExInfo.throwDipperEx(AppCodeDict.BISACC0007,acc.getVacctNo());
            }
        }
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("updateAmt", updateAmt);
        map.put("accNo", acc.getVacctNo());
        map.put("addOrSub", addOrSub);
        int num=daoService.update(AccVbookPo.class.getName().concat(".updateAccountAmtSub"), map);
        if(num==1){
            if(CommonBaseConstans_PAY.ACC_AMT_SUB.equals(addOrSub)){                
                dto.setUpdateUserAccountAmt(true);
            }
        }else{
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "余额扣减");
        }
        return dto;
    }

}
