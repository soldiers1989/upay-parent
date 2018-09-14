package com.upay.busi.mer.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.MerTransLimitCheckDto;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.mer.MerTransCtrlPo;
import com.upay.dao.po.mer.MerTransLimitPo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.math.BigDecimal;

/**
 * 商户交易限额检查
 * @author zhangjianfeng
 * @since 2016/11/24 17:09
 */
public class MerTransLimitCheckService extends AbstractDipperHandler<MerTransLimitCheckDto> {

    private static final Logger logger = LoggerFactory.getLogger(MerTransLimitCheckService.class);

    @Resource
    IDaoService daoService;

    @Override
    public MerTransLimitCheckDto execute(MerTransLimitCheckDto dto, Message message) throws Exception {
        //一级商户号
        String merNo = dto.getMerNo();
        //二级商户号
        String secMerNo = dto.getSecMerNo();

        //支付类型非空检查
        if(StringUtils.isBlank(dto.getPayType())) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "支付方式");
        }

        //支付账户类型非空检查
        if(StringUtils.isBlank(dto.getPayCardType())) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "支付账户类型");
        }

        //交易码非空检查
        if(StringUtils.isBlank(dto.getTransCode())) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "交易码");
        }

        //渠道代码非空检查
        if(StringUtils.isBlank(dto.getChnlId())) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "渠道代码");
        }

        //交易金额非空检查
        BigDecimal transAmt = dto.getTransAmt();
        if(transAmt == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "交易金额");
        }
        if(transAmt.signum() == -1) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0002, "交易金额");
        }

        //商户号检查
        if(StringUtils.isBlank(merNo) && StringUtils.isBlank(secMerNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "商户号");
        } else if(StringUtils.isBlank(merNo) && StringUtils.isNotBlank(secMerNo)) {
            MerBaseInfoPo merBaseInfo = new MerBaseInfoPo();
            merBaseInfo.setMerNo(secMerNo);
            merBaseInfo = daoService.selectOne(merBaseInfo);
            if(merBaseInfo == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0018, secMerNo);
            }
            dto.setMerNo(merBaseInfo.getParentMerNo());
            merNo = merBaseInfo.getMerNo();
        }

        //查询商户交易权限控制
        MerTransCtrlPo merTransCtrl = new MerTransCtrlPo();
        merTransCtrl.setMerNo(merNo);
        merTransCtrl.setTransCode(dto.getTransCode());
        merTransCtrl.setPayType(dto.getPayType());
        merTransCtrl.setPayCardType(dto.getPayCardType());
        merTransCtrl.setChnlId(dto.getChnlId());
        merTransCtrl.setStatus(DateBaseConstants_MER.MER_STAT_NORMAL);

        merTransCtrl = daoService.selectOne(merTransCtrl);
        if(merTransCtrl == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISMER0009, merNo); //商户[{}]没有交易权限
        }

        //商户限额检查
        MerTransLimitPo merTransLimit = new MerTransLimitPo();
        merTransLimit.setMerTransCtrlCode(merTransCtrl.getMerTransCtrlCode());
        merTransLimit.setStatus(DateBaseConstants_MER.MER_STAT_NORMAL);
        merTransLimit = daoService.selectOne(merTransLimit);

        if(merTransLimit != null) {
            //超过交易限额
            if(merTransLimit.getTransLimit().compareTo(transAmt) < 0) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0009, merNo);
            }
        }

        return dto;
    }
}
