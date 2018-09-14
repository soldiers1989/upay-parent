package com.upay.busi.acc.service.impl;

import javax.annotation.Resource;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.VacctOpenFlagQueryDto;
import com.upay.commons.constants.CommonBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccBindBookPo;
import com.upay.dao.po.acc.AccVbookPo;


/**
 * 用户电子账户开户标识查询
 * 
 * @author liubing
 * 
 */
public class VacctOpenFlagQueryServiceImpl extends AbstractDipperHandler<VacctOpenFlagQueryDto> {
    @Resource
    private IDaoService daoService;


    @Override
    public VacctOpenFlagQueryDto execute(VacctOpenFlagQueryDto eacctOpenFlagQueryDto, Message message)
            throws Exception {

        String openAcctFlag = CommonBaseConstans_ACC.OPEN_ACCT_FLAG_NO;

        String userId = eacctOpenFlagQueryDto.getUserId();
        AccVbookPo accEbookPo = new AccVbookPo();
        accEbookPo.setUserId(userId);

        accEbookPo = daoService.selectOne(accEbookPo);
        if (accEbookPo != null) {
            String eAcctStat = accEbookPo.getVacctStat();
            if (DataBaseConstans_ACC.ACCT_STAT_NORMAL.equals(eAcctStat)) {
                openAcctFlag = CommonBaseConstans_ACC.OPEN_ACCT_FLAG_YES;
            } else if (DataBaseConstans_ACC.ACCT_STAT_NOACTIVE.equals(eAcctStat)) {
                AccBindBookPo accBindBookPo = new AccBindBookPo();
                accBindBookPo.setVacctNo(accEbookPo.getVacctNo());
                accBindBookPo.setBindStat(DataBaseConstans_ACC.BIND_STAT_BINDED_TOACTIVATED);
                accBindBookPo = daoService.selectOne(accBindBookPo);
                if (accBindBookPo == null) {
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "待激活账户[" + accEbookPo.getVacctNo()
                            + "]绑定卡信息");
                }
                String eBindFlag = accBindBookPo.getVbindFlag();
                if (DataBaseConstans_ACC.E_BIND_FLAG_THDVERIFY.equals(eBindFlag)) {
                    openAcctFlag = CommonBaseConstans_ACC.OPEN_ACCT_FLAG_NO;
                } else {
                    openAcctFlag = CommonBaseConstans_ACC.OPEN_ACCT_FLAG_NOACTIVE;
                }
            }
        }
        eacctOpenFlagQueryDto.setOpenAcctFlag(openAcctFlag);
        return eacctOpenFlagQueryDto;
    }

}
