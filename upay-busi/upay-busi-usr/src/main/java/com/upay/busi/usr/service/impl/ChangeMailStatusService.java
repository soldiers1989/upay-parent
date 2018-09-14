package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.ChangeMailStatusDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrComRegRecPo;


/**
 * 企业重置密码修改邮件状态
 * 
 * @author liyulong
 * 
 */
public class ChangeMailStatusService extends AbstractDipperHandler<ChangeMailStatusDto> {

    @Resource
    private IDaoService daoService;

    private static final Logger log = LoggerFactory.getLogger(UserRegInfoService.class);


    @Override
    public ChangeMailStatusDto execute(ChangeMailStatusDto changeMailStatusDto, Message msg) throws Exception {
        log.info("-----------------验签开始");
        String email = changeMailStatusDto.getEmail();// 企业邮箱
        if (StringUtils.isBlank(email)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "企业邮箱");
        }
        UsrComRegRecPo usrComRegRecPo = new UsrComRegRecPo();
        usrComRegRecPo.setGenerateFlag(DataBaseConstants_USR.GENERATE_FLAG_YES1);
        UsrComRegRecPo usrComRegRecPo2 = new UsrComRegRecPo();
        usrComRegRecPo2.setComEmail(email);
        usrComRegRecPo2.setTransCode("SI_USR0025");
        daoService.update(usrComRegRecPo, usrComRegRecPo2);
        return changeMailStatusDto;
    }
}
