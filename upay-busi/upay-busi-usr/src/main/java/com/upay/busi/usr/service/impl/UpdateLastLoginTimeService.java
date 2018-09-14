package com.upay.busi.usr.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.UpdateLastLoginTimeDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 更新最后登录时间
 * 
 * @author shangqiankun
 * @version 创建时间：2016年7月22日 上午10:14:08
 */
public class UpdateLastLoginTimeService extends AbstractDipperHandler<UpdateLastLoginTimeDto> {

    private static final Logger log = LoggerFactory.getLogger(UpdateLastLoginTimeService.class);

    @Resource
    private IDaoService daoService;


    @Override
    public UpdateLastLoginTimeDto execute(UpdateLastLoginTimeDto dto, Message message) throws Exception {
        Date now = dto.getSysTime();
        // 参数校验
        String userId = dto.getUserId();
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户id");
        }
        UsrRegInfoPo regWhere = new UsrRegInfoPo();
        regWhere.setUserId(dto.getUserId());
        UsrRegInfoPo regSet = new UsrRegInfoPo();
        regSet.setLastLoginTime(now);
        daoService.update(regSet, regWhere);
        return dto;
    }

}
