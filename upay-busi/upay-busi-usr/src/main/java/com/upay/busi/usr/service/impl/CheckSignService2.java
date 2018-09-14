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
import com.upay.busi.usr.service.dto.CheckSign2Dto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.Md5Util;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.usr.UsrComRegRecPo;


/**
 * 验证签名，有效期（重置登录密码）
 * 
 * @author liyulong
 * 
 */
public class CheckSignService2 extends AbstractDipperHandler<CheckSign2Dto> {

    @Resource
    private IDaoService daoService;

    private static final Logger log = LoggerFactory.getLogger(UserRegInfoService.class);


    @Override
    public CheckSign2Dto execute(CheckSign2Dto checkSign2Dto, Message msg) throws Exception {
        log.info("-----------------验签开始");
        String sid = checkSign2Dto.getSid();
        String trans = checkSign2Dto.getTrans();
        if (StringUtils.isBlank(sid)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0049);
        }
        if (StringUtils.isBlank(trans)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0049);
        }
        if (!trans.equals(Md5Util.toMD5("SI_USR0025"))) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0049);
        }
        UsrComRegRecPo usrComRegRecPo = new UsrComRegRecPo();
        usrComRegRecPo.setSid(sid);
        usrComRegRecPo.setTransCode(DataBaseConstants_USR.TRANS_CODE_SEND_MAIL);
        usrComRegRecPo = daoService.selectOne(usrComRegRecPo);
        if (usrComRegRecPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0029, "该记录");
        } else {
            // "是否已重置密码3-没有4-已生成"
            String generateFlag = usrComRegRecPo.getGenerateFlag();
            if (StringUtils.isBlank(generateFlag)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "generateFlag");
            }
            if (DataBaseConstants_USR.GENERATE_FLAG_NO1.equals(generateFlag)) {
                Date sysDate = SysInfoContext.getSysTime(); // 系统时间
                Date valiedDate = usrComRegRecPo.getValiedDate();
                checkSign2Dto.setMailTo(usrComRegRecPo.getComEmail());
                if (sysDate.compareTo(valiedDate) > 0) {// 已经过期
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0044);
                }
            } else if (DataBaseConstants_USR.GENERATE_FLAG_YES1.equals(generateFlag)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0045);
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "数据库信息");
            }

        }
        return checkSign2Dto;
    }
}
