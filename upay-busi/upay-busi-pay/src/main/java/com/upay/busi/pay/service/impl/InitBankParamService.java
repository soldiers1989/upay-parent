package com.upay.busi.pay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.InitCoreBankParamDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_FEE;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.CommonMethodUtils;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 核心接口的参数转换
 * 
 * @author shangqiankun
 * @version 创建时间：2016年8月15日 下午7:33:34
 */
public class InitBankParamService extends AbstractDipperHandler<InitCoreBankParamDto> {

    @Resource
    private IDaoService daoService;
    
    @Override
    public InitCoreBankParamDto execute(InitCoreBankParamDto dto, Message message) throws Exception {
        String routeCode=StringUtils.isBlank(dto.getSuccessRouteCode())?dto.getRouteCode():DataBaseConstants_PAY.ROUTE_CODE_HOST;
        if(StringUtils.isBlank(routeCode)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "通道代码");
        }
        if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)){
            SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
            String time = sim.format(new Date());
            dto.setMachineTime(time.substring(8));
            dto.setMachineDate(time.substring(0, 8));
            dto.setBizDate(sim.format(SysInfoContext.getSysDate()).substring(0, 8));
            dto.setCurrency(DataBaseConstants_PAY.T_CORE_CCY_CNY);
            dto.setAmount(dto.getTotalFee());
            dto.setCharge("");
            if(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(dto.getAccType())){
                if(StringUtils.isBlank(dto.getCvv2())){
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "信用卡cvv2");
                }
                if(StringUtils.isBlank(dto.getBankAccNo())){
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "卡号");
                }
                if(StringUtils.isBlank(dto.getValiddate())){                    
                    if(StringUtils.isBlank(dto.getValidDate())){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "信用卡有效期");
                    }
                    dto.setValiddate(dto.getValidDate());
                }
            }
        }else if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routeCode)){
            if(dto.getTotalFee()==null){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付金额");
            }
            dto.setPaymentNo(CommonMethodUtils.getZJSubSeq());
            dto.setAmount(dto.getTotalFee());
            dto.setSettlementFlag(CommonConstants_GNR.ZJ_SETTLEMENTFLAG);
            if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(dto.getAccType())){                
                if(StringUtils.isBlank(dto.getTxSNBinding())){
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "绑卡的绑定流水号");
                }
            }else if(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(dto.getAccType())){
                if(StringUtils.isBlank(dto.getTxSNBinding())){                    
                    dto.setTxSNBinding(CommonMethodUtils.getZJSubSeq());
                    dto.setTxSNBindingNew(true);
                }
                dto.setAccountNumber(dto.getBankAccNo());
                dto.setIdentificationType(CommonBaseConstans_PAY.ZJ_PAPER_TYPE_0);
                dto.setCardType(CommonBaseConstans_PAY.ZJ_PAY_CARD_TYPE_20);
                if(StringUtils.isBlank(dto.getMobile())){
                    UsrRegInfoPo usr=new UsrRegInfoPo();
                    usr.setUserId(dto.getUserId());
                    usr=daoService.selectOne(usr);
                    if(usr!=null){
                        dto.setPhoneNumber(usr.getMobile());
                    }                    
                }else{
                    dto.setPhoneNumber(dto.getMobile());
                }
                if(StringUtils.isBlank(dto.getValiddate())){
                    dto.setValiddate(dto.getValidDate());
                }
                if(StringUtils.isNotBlank(dto.getValidDate())){
                    dto.setValidDate(dto.getValidDate().substring(2));                    
                }
                //TODO 测试用
                dto.setRemark("10");
            }
        }
        return dto;
    }
}
