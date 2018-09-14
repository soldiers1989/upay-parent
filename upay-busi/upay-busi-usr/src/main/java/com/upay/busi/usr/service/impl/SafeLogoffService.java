package com.upay.busi.usr.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.SafeLogoffDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrLogListPo;


/**
 * 安全退出
 * 
 * @author liyulong
 * 
 */
public class SafeLogoffService extends AbstractDipperHandler<SafeLogoffDto> {

    @Resource
    private IDaoService daoService;
    @Resource
    IDipperCached idipperCached;
    private static final Logger logger = LoggerFactory.getLogger(SafeLogoffService.class);


    @Override
    public SafeLogoffDto execute(SafeLogoffDto dto, Message msg) throws Exception {

        String userId = dto.getUserId();
        String sessionId = dto.getSessionId();
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户id");
        }
        if (StringUtils.isBlank(sessionId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "SessionId");
        }
        UsrLogListPo usrLogListPoWhere = new UsrLogListPo();
        usrLogListPoWhere.setSessionId(sessionId);
        UsrLogListPo usrLogListPo = new UsrLogListPo();
        usrLogListPo.setLogoutTime(new Date());
        daoService.update(usrLogListPo, usrLogListPoWhere);

        String chnlId = dto.getChnlId();
        if (chnlId == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "chnlId");
        }
        if (DataBaseConstants_USR.CHNL_ID_APP.equals(chnlId)) {
            idipperCached.delete(CacheConstants.SESSION_APP.concat(userId));
        } else {
            idipperCached.delete(CacheConstants.SESSION_WEB.concat(userId));
        }
        // logger.debug("---------------" + usrLogListPo.getLogoutTime());
        logger.debug("--------------------------------end");
        return dto;
    }

}
