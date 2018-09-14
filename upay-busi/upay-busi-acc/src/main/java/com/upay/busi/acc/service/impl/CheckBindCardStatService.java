package com.upay.busi.acc.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.CheckBindCardStatDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccBindBookPo;
import com.upay.dao.po.pay.PayCardbinInfoPo;

/**
 * 绑定卡状态检查(当支付方式为本行快捷和他行快捷的时候才做此检查)
 * @author shangqiankun
 * @version 创建时间：2016年8月5日 下午4:21:53
 */
public class CheckBindCardStatService extends AbstractDipperHandler<CheckBindCardStatDto> {

    @Resource
    private IDaoService daoService;
    @Override
    public CheckBindCardStatDto execute(CheckBindCardStatDto dto, Message message) throws Exception {
        String accNo=dto.getAccNo();
        String bankAccNo=dto.getBankAccNo();
        if(StringUtils.isBlank(accNo)){
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "用户账号");
        }
        if(StringUtils.isBlank(bankAccNo)){
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "银行卡号");
        }
        AccBindBookPo bind=new AccBindBookPo();
        bind.setVacctNo(accNo);
        bind.setVbindAcctNo(bankAccNo);
        bind=daoService.selectOne(AccBindBookPo.class.getName().concat(".getLastOneByTime"),bind);
        if(bind==null){
            ExInfo.throwDipperEx(AppCodeDict.BISACC0006,bankAccNo);
        }
        String bindStat=bind.getBindStat();
        if(!DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_BIND.equals(bindStat)){
            if(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_LOSE.equals(bindStat)){
                ExInfo.throwDipperEx(AppCodeDict.BISACC0010,bankAccNo);
            }
            if(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_UNACTIVE.equals(bindStat)){
                ExInfo.throwDipperEx(AppCodeDict.BISACC0009,bankAccNo);
            }
            if(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_UNBIND.equals(bindStat)){
                ExInfo.throwDipperEx(AppCodeDict.BISACC0011,bankAccNo);
            }
        }
        dto.setBindAccType(bind.getBindAcctType());//绑定账户类型
        dto.setBindBankFlag(bind.getVbindBankFlag());//绑定账户银行类别
        dto.setBindBankCode(bind.getVbindBankCode());//绑定账户行号
        dto.setBindBankName(bind.getVbindBankName());//绑定账户行名
        dto.setBindAccNo(bind.getVbindAcctNo());//绑定账户账号
        dto.setBindOpenCode(bind.getVbindOpenCode());//绑定账户开户机构号
        dto.setBindOpenName(bind.getVbindOpenName());//绑定账户开户行名
        dto.setCardFistPay(bind.getCardFistPay());//是否为首次支付
        dto.setCvv2(bind.getCvn2());
        dto.setValidDate(bind.getValidDate());
        if(StringUtils.isNotBlank(bind.getRemark1())){
            dto.setTxSNBinding(bind.getRemark1());
        }
        PayCardbinInfoPo card=new PayCardbinInfoPo();
        card.setCardBin(bind.getCardBin());
        card=daoService.selectOne(card);
        if(card==null){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "卡bin："+bind.getCardBin());
        }
        if(DataBaseConstants_PAY.BANK_FLAG_IN.equals(card.getBankBinFlag())){
            dto.setPayType(DataBaseConstants_PAY.T_PAY_TYPE_BANK_QUICK);
        }else if(DataBaseConstants_PAY.BANK_FLAG_OUT.equals(card.getBankBinFlag())){
            dto.setPayType(DataBaseConstants_PAY.T_PAY_TYPE_OTHER_QUICK);
        }else{
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "卡bin行内外标识");
        }
        if(DataBaseConstans_ACC.BANK_CARD_TYPE_BORROW.equals(card.getCardBinType())){
            dto.setAccType(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);
        }else if(DataBaseConstans_ACC.BANK_CARD_TYPE_LEND.equals(card.getCardBinType())){
            dto.setAccType(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD);
        }else{
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0100,"卡类型");
        }
        return dto;
    }

}
