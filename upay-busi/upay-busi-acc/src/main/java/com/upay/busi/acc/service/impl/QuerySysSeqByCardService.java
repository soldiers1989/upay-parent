package com.upay.busi.acc.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.QuerySysSeqByCardDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccBindBookPo;


/**
 * 根据账户查询原流水号
 * 
 * @author: liubing
 * @CreateDate:2015年4月10日
 * 
 */
public class QuerySysSeqByCardService extends AbstractDipperHandler<QuerySysSeqByCardDto> {
    @Resource
    private IDaoService daoService;


    @Override
    public QuerySysSeqByCardDto execute(QuerySysSeqByCardDto querySysSeqByCardDto, Message msg)
            throws Exception {
        // 获取绑定账户解绑所需要字段
        /*
         * String eCardNo = querySysSeqByCardDto.geteCardNo();// 电子账户卡号 String
         * bindAcctType = querySysSeqByCardDto.getBindAcctType();// 绑定账户类型
         * String eBindBankCode = querySysSeqByCardDto.geteBindBankCode();//
         * 绑定卡行号
         */String eBindAcctNo = querySysSeqByCardDto.getVbindAcctNo();// 绑定卡账户账号,待解决问题！！！
         String vAcctNo = querySysSeqByCardDto.getvAcctNo();
        // 判断所需字段是否为空

        if (StringUtils.isBlank(eBindAcctNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "绑定卡账户账号");
        }
        if (StringUtils.isBlank(vAcctNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "虚拟账号");
        }

        // 根据用户号及账号判断此用户状态
        AccBindBookPo accBindBookPo = new AccBindBookPo();

        accBindBookPo.setVbindAcctNo(eBindAcctNo);
        accBindBookPo.setVacctNo(vAcctNo);
        accBindBookPo.setBindStat(DataBaseConstans_ACC.BIND_STAT_BINDED_ACTIVATED);
        accBindBookPo = daoService.selectOne(accBindBookPo);
        if (null != accBindBookPo) {

            /*
             * if
             * (DataBaseConstants_ACC.BIND_STAT_UNBINDED.equals(accBindBookPo.
             * getBindStat())) { ExInfo.throwDipperEx(AppCodeDict.BISACC0048); }
             */
            querySysSeqByCardDto.setOldGateWay_zjpay_sysSeq(accBindBookPo.getRemark1());

        } else {
            // 不存在该已经绑定账户
            ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "该绑定账户");
        }

        // 如果电子账户所有的银行卡都没有绑定成功,才更新电子账户信息状态
        // accBindBookPo = new AccBindBookPo();
        // accBindBookPo.setEcardNo(eCardNo);
        // accBindBookPo.setBindStat(DataBaseConstants_ACC.BIND_STAT_BINDED_ACTIVATED);
        // list = daoService.selectList(accBindBookPo);
        // if (list.size() == 0) {
        // AccEbookPo accEbookPo = new AccEbookPo();
        // accEbookPo.setEcardNo(eCardNo);
        // accEbookPo = daoService.selectOne(accEbookPo);
        // // 将电子账户信息表中的账户状态更新为正常
        // accEbookPo.setEacctStat(DataBaseConstants_ACC.ACCT_STAT_NOACTIVE);
        // daoService.update(accEbookPo);
        // }

        return querySysSeqByCardDto;
    }
}
