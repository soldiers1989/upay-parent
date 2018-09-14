package com.upay.busi.pay.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.PayQueryCardBinOfBankDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayCardbinInfoPo;


/**
 * 类或接口的功能说明 类或接口的使用说明 :根据卡bin查询银行信息根据卡号查询卡信息、银行信息
 *
 * @author: liubing
 * @CreateDate:2015年4月13日
 *
 *
 * @UpdateUser:修改人名称
 * @CreateDate:2015年4月13日
 * @UpdateRemark:修改具体的内容；
 *
 */
public class PayQueryCardBinOfBankService extends AbstractDipperHandler<PayQueryCardBinOfBankDto> {

    private final static Logger logger = LoggerFactory.getLogger(PayQueryCardBinOfBankService.class);
    @Resource
    private IDaoService daoService;


    @Override
    public PayQueryCardBinOfBankDto execute(PayQueryCardBinOfBankDto payQueryCardBinOfBankDto, Message message)
            throws Exception {
    	String bindAcctNo = payQueryCardBinOfBankDto.geteBindAcctNo();
        if (StringUtils.isBlank(bindAcctNo)) {
            logger.error("卡bin查询银行信息:卡号为空");
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "卡号");
        }

        HashMap<String, Object> cardNoLenMap = new HashMap<String, Object>();
        cardNoLenMap.put("cardNoLen", bindAcctNo.length());
        String sqlId = PayCardbinInfoPo.class.getName() + ".selectCardBinLenList";
        List<Integer> list = daoService.selectList(sqlId, cardNoLenMap); // 查询卡BIN长度

        for (Integer cardBinLen : list) {
            // 按长度截取卡BIN（从长到短），获得数据跳出循环
            if (bindAcctNo.length() <= cardBinLen) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "卡号长度");
            }
            String cardBin = bindAcctNo.substring(0, cardBinLen);
            PayCardbinInfoPo payCardbinInfoPo = new PayCardbinInfoPo();
            payCardbinInfoPo.setCardBin(cardBin);
            payCardbinInfoPo.setCardNoLen(bindAcctNo.length());
            payCardbinInfoPo = daoService.selectOne(payCardbinInfoPo);

            if (payCardbinInfoPo != null) {
            	payQueryCardBinOfBankDto.setLogoId(payCardbinInfoPo.getLogoId());
                payQueryCardBinOfBankDto.setCardBin(cardBin);
                payQueryCardBinOfBankDto.setCardBinName(payCardbinInfoPo.getCardBinName());
                payQueryCardBinOfBankDto.setBankBinFlag(payCardbinInfoPo.getBankBinFlag());
                payQueryCardBinOfBankDto.setCupBankNo(payCardbinInfoPo.getCupBankNo());
                payQueryCardBinOfBankDto.setCupBankName(payCardbinInfoPo.getCupBankName());
                payQueryCardBinOfBankDto.setCnapsBankNo(payCardbinInfoPo.getCupBankNo());
                payQueryCardBinOfBankDto.setCardBinType(payCardbinInfoPo.getCardBinType());
                break;
            }
        }

        if (StringUtils.isBlank(payQueryCardBinOfBankDto.getCupBankNo())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0101);
        }

        return payQueryCardBinOfBankDto;
    }
}
