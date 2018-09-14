package com.upay.busi.acc.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.BindCheckDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccBindBookPo;
import com.upay.dao.po.acc.AccVbookPo;


/**
 * 根据卡号查询预留手机号
 * 
 * @author liyulong
 * 
 */
public class BindCheckService extends AbstractDipperHandler<BindCheckDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(BindCheckService.class);


    @Override
    public BindCheckDto execute(BindCheckDto bindCheckDto, Message message) throws Exception {
        String vBindAcctNo = bindCheckDto.getvBindAcctNo();
        String mobile = bindCheckDto.getMobile();
        String userId = bindCheckDto.getUserId();
        if (StringUtils.isBlank(vBindAcctNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "卡号");
        }
        if (StringUtils.isBlank(mobile)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "手机号");
        }
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "用户ID");
        }
        AccVbookPo accVbookPo = new AccVbookPo();
        accVbookPo.setUserId(userId);
        accVbookPo = daoService.selectOne(accVbookPo);
        if (accVbookPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "该虚拟账户");
        }

        AccBindBookPo accBindBookPo = new AccBindBookPo();
        accBindBookPo.setVacctNo(accVbookPo.getVacctNo());
        accBindBookPo.setVbindAcctNo(vBindAcctNo);
        accBindBookPo.setBindStat(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_BIND);
        accBindBookPo = daoService.selectOne(accBindBookPo);
        if (accBindBookPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "该绑卡信息");
        }
        String reserveMobile = accBindBookPo.getReserveMobile();
        if (!mobile.equals(reserveMobile)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0021, "预留手机号");
        }

        logger.debug("-----------------------------------end");
        return bindCheckDto;
    }
}
