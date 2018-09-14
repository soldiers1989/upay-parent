package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.CheckUsrDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 邮箱重置登录密码，校验是否本人
 *
 * @author liyulong
 */
public class CheckUsrService extends AbstractDipperHandler<CheckUsrDto> {

    @Resource
    private IDaoService daoService;

    private static final Logger log = LoggerFactory.getLogger(UserRegInfoService.class);


    @Override
    public CheckUsrDto execute(CheckUsrDto checkUsrDto, Message msg) throws Exception {
        log.info("-----------------校验开始");
       /* String mobile = checkUsrDto.getMobile();
        String comEmail = checkUsrDto.getMailTo();*/

        String userName = checkUsrDto.getUserName();


       /* if (StringUtils.isBlank(mobile)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "手机号");
        }
        if (StringUtils.isBlank(comEmail)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "邮箱");
        }*/

        if (StringUtils.isBlank(userName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户名");
        }
        UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
        usrRegInfoPo.setUserName(userName);
        usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
        if (usrRegInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0053);
        }
        //设置 接收人的邮件
        checkUsrDto.setMailTo(usrRegInfoPo.getComEmail());
        return checkUsrDto;
    }
}
