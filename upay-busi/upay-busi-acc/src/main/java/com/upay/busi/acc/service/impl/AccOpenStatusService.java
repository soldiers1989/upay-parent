package com.upay.busi.acc.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccOpenStatusDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.dao.po.acc.AccVbookPo;


/**
 * 查询开户状态
 * 
 * @author shangqiankun
 * @version 创建时间：2016年7月21日 上午9:40:59
 */
public class AccOpenStatusService extends AbstractDipperHandler<AccOpenStatusDto> {

    private static final Logger log = LoggerFactory.getLogger(AccOpenStatusService.class);

    @Resource
    private IDaoService daoService;


    @Override
    public AccOpenStatusDto execute(AccOpenStatusDto dto, Message message) throws Exception {
        if (StringUtils.isNotBlank(dto.getUserId())) {
            AccVbookPo acc = new AccVbookPo();
            acc.setUserId(dto.getUserId());
            acc = daoService.selectOne(acc);
            if (acc != null) {
                if (DataBaseConstans_ACC.ACC_V_BOOK_VSTAT_NOMAL.equals(acc.getVacctStat())) {
                    dto.setOpenAccFlag(DataBaseConstans_ACC.OPEN_ACCT_FLAG_YES);
                }
                if (DataBaseConstans_ACC.ACC_V_BOOK_VSTAT_UNACTIVEL.equals(acc.getVacctStat())) {
                    dto.setOpenAccFlag(DataBaseConstans_ACC.OPEN_ACCT_FLAG_NOACTIVE);
                }
                if (DataBaseConstans_ACC.ACC_V_BOOK_VSTAT_SLEEP.equals(acc.getVacctStat())) {
                    dto.setOpenAccFlag(DataBaseConstans_ACC.OPEN_ACCT_FLAG_NOACTIVE);
                }
                if (DataBaseConstans_ACC.ACC_V_BOOK_VSTAT_CANCEL.equals(acc.getVacctStat())) {
                    dto.setOpenAccFlag(DataBaseConstans_ACC.OPEN_ACCT_FLAG_CANCEL);
                }
            } else {
                dto.setOpenAccFlag(DataBaseConstans_ACC.OPEN_ACCT_FLAG_NO);
            }
        } else {
            dto.setOpenAccFlag(DataBaseConstans_ACC.OPEN_ACCT_FLAG_NO);
        }
        return dto;
    }

}
