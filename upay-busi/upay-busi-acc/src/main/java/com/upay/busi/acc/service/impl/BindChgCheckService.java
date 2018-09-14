package com.upay.busi.acc.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.BindChgCheckDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 虚拟账户变更绑定卡前检查
 * 
 * @author liyulong
 * 
 */
@Deprecated
public class BindChgCheckService extends AbstractDipperHandler<BindChgCheckDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(BindChgCheckService.class);


    @Override
    public BindChgCheckDto execute(BindChgCheckDto bindChgCheckDto, Message message) throws Exception {

        String vAcctNo = bindChgCheckDto.getvAcctNo();
        if (StringUtils.isBlank(vAcctNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "虚拟账户账号");
        }

        AccVbookPo accVbookPo = new AccVbookPo();
        accVbookPo.setVacctNo(vAcctNo);
        accVbookPo = daoService.selectOne(accVbookPo);
        if (accVbookPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0012, vAcctNo);
        }
        String userId = accVbookPo.getUserId();
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0029, "用户");
        }
        UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
        usrRegInfoPo.setUserId(userId);
        usrRegInfoPo.setUserStat(DataBaseConstants_USR.USR_STAT_NORMAL);
        usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
        if (usrRegInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0029, "该用户");
        }
        String userCertLevel = usrRegInfoPo.getUserCertLevel();

        // 如果用户不是强实名认证等级，则检查电子账户余额
        if (!userCertLevel.equals(DataBaseConstants_USR.USER_CERT_LEVEL_STRONG_AUTH)) {
            BigDecimal acctBal = accVbookPo.getAcctBal();
            if (acctBal.compareTo(BigDecimal.ZERO) != 0) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0019);
            }
        }
        logger.debug("-------------------------------end");
        return bindChgCheckDto;
    }
}
