package com.upay.busi.acc.service.impl;

import java.math.RoundingMode;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccInfoQueryDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.acc.AccVopenBookPo;


/**
 * 虚拟账户信息查询
 * 
 * @author liyulong
 * 
 */
public class AccInfoQueryService extends AbstractDipperHandler<AccInfoQueryDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(AccInfoQueryService.class);


    @Override
    public AccInfoQueryDto execute(AccInfoQueryDto AccInfoQueryDto, Message message) throws Exception {
        String userId = AccInfoQueryDto.getUserId();
        String vAcctNo = AccInfoQueryDto.getvAcctNo();
        if (StringUtils.isBlank(vAcctNo) && StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "用户ID和账号同时");
        }

        AccVbookPo accVbookPo = new AccVbookPo();
        if (StringUtils.isNotBlank(vAcctNo)) {
            accVbookPo.setVacctNo(vAcctNo);
        } else {
            accVbookPo.setUserId(userId);
        }
        accVbookPo = daoService.selectOne(accVbookPo);
        if (accVbookPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0002);
        }
        AccInfoQueryDto.setvAcctNo(accVbookPo.getVacctNo());
        AccInfoQueryDto.setvAcctStat(accVbookPo.getVacctStat());
        AccInfoQueryDto.setStopStat(accVbookPo.getStopStat());
        AccInfoQueryDto.setFrzStat(accVbookPo.getFrzStat());
        AccInfoQueryDto.setSetFlag(accVbookPo.getSetFlag());
        AccInfoQueryDto.setAcctName(accVbookPo.getAcctName());
        AccInfoQueryDto.setAcctOtherName(accVbookPo.getAcctOtherName());
        AccInfoQueryDto.setCCY(accVbookPo.getCcy());
        AccInfoQueryDto.setFrzBal(MoneyUtil.moneyFormat(accVbookPo.getFrzBal()));
        AccInfoQueryDto.setAcctBal(MoneyUtil.moneyFormat(accVbookPo.getAcctBal()));
        AccInfoQueryDto.setLastBal(MoneyUtil.moneyFormat(accVbookPo.getLastBal()));
        AccInfoQueryDto.setCutBal(MoneyUtil.moneyFormat(accVbookPo.getCutBal()));
        AccInfoQueryDto.setExtTime(accVbookPo.getExtTime());
        AccInfoQueryDto.setLastChgTime(accVbookPo.getLastChgTime());
        AccInfoQueryDto.setLastTime(accVbookPo.getLastTime());
        AccInfoQueryDto.setDAC(accVbookPo.getDac());
//        AccInfoQueryDto.setUserId(accVbookPo.getUserId());
        AccInfoQueryDto.setUseBal(MoneyUtil.subtract(accVbookPo.getAcctBal(), accVbookPo.getFrzBal(),
            RoundingMode.HALF_DOWN));

        AccVopenBookPo accVopenBookPo = new AccVopenBookPo();
        accVopenBookPo.setVacctNo(accVbookPo.getVacctNo());
        accVopenBookPo = daoService.selectOne(accVopenBookPo);
        if (accVopenBookPo != null) {
            Date openTime = accVopenBookPo.getOpenTime();
            AccInfoQueryDto.setOpenTime(openTime);
        }
        // logger.debug("++++++++++++++++" + AccInfoQueryDto.getUserId());
        logger.debug("-----------------------------------end");
        return AccInfoQueryDto;
    }
}
