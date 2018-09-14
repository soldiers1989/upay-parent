package com.upay.busi.acc.service.impl;

import javax.annotation.Resource;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.CardNoCreateDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.CardUtils;
import com.upay.dao.po.acc.AccCardConfPo;


/**
 * 电子账户开户生成电子账户卡号
 * 
 * @author: liubing
 * @CreateDate:2015年6月16日
 * 
 */
public class CardNoCreateService extends AbstractDipperHandler<CardNoCreateDto> {
    @Resource
    private IDaoService daoService;


    @Override
    public CardNoCreateDto execute(CardNoCreateDto cardNoCreateDto, Message msg) throws Exception {
        String eCardNo = null;

        // 根据卡Bin和卡号状态从电子账户卡号生成规则配置表查处相关数据，拼出电子现在账户eCardNo
        AccCardConfPo accCardConfPo = new AccCardConfPo();
        accCardConfPo.setCardStat(DataBaseConstans_ACC.CARD_STAT_USED);
        accCardConfPo = daoService.selectForUpdate(accCardConfPo);
        if (accCardConfPo != null) {
            String cardBin = accCardConfPo.getCardBin();
            Integer cardSeq = accCardConfPo.getCardSeq();
            String sectionOne = accCardConfPo.getSectionOne();
            String sectionTwo = accCardConfPo.getSectionTwo();
            String sectionThree = accCardConfPo.getSectionThree();
            eCardNo = CardUtils.getCardNo(cardBin, sectionOne, sectionTwo, sectionThree, cardSeq.longValue());
            accCardConfPo.setCardSeq(cardSeq + 1);

            AccCardConfPo set = new AccCardConfPo();
            set.setCardSeq(accCardConfPo.getCardSeq());
            AccCardConfPo where = new AccCardConfPo();
            where.setId(accCardConfPo.getId());
            daoService.update(set, where);
        } else {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0016);
        }
        cardNoCreateDto.seteCardNo(eCardNo);
        return cardNoCreateDto;
    }
}
