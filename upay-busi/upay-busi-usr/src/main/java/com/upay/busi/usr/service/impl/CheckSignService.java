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
import com.upay.busi.usr.service.dto.CheckSignDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.Md5Util;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.usr.UsrComRegRecPo;


/**
 * 验证签名，有效期
 * 
 * @author liyulong
 * 
 */
public class CheckSignService extends AbstractDipperHandler<CheckSignDto> {

    @Resource
    private IDaoService daoService;

    private static final Logger log = LoggerFactory.getLogger(UserRegInfoService.class);


    @Override
    public CheckSignDto execute(CheckSignDto checkSignDto, Message msg) throws Exception {
        log.info("-----------------验签开始");
        String sid = checkSignDto.getSid();
        String trans = checkSignDto.getTrans();
        if (StringUtils.isBlank(sid)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0049);
        }
        if (StringUtils.isBlank(trans)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0049);
        }
        if (!trans.equals(Md5Util.toMD5(DataBaseConstants_USR.TRANS_CODE_SENDMAIL))) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0049);
        }
        UsrComRegRecPo usrComRegRecPo = new UsrComRegRecPo();
        usrComRegRecPo.setSid(sid);
        usrComRegRecPo.setTransCode(DataBaseConstants_USR.TRANS_CODE_SENDMAIL);
        usrComRegRecPo = daoService.selectOne(usrComRegRecPo);
        if (usrComRegRecPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0029, "该记录");
        } else {
            // "是否已生产用户0-没有1-已生成"
            String generateFlag = usrComRegRecPo.getGenerateFlag();
            if (StringUtils.isBlank(generateFlag)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "注册标识");
            }

            /*todo:需求更改  一个邮箱可以对应多个商户*/
            if (generateFlag.equals(DataBaseConstants_USR.GENERATE_FLAG_NO)||generateFlag.equals(DataBaseConstants_USR.GENERATE_FLAG_YES)) {
                Date sysDate = SysInfoContext.getSysTime(); // 系统时间
                Date valiedDate = usrComRegRecPo.getValiedDate();
                checkSignDto.setMailTo(usrComRegRecPo.getComEmail());
                if (sysDate.compareTo(valiedDate) > 0) {// 已经过期
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0044);
                }
            }

            /*todo:需求更改  一个邮箱可以对应多个商户*/
            /*else if (generateFlag.equals(DataBaseConstants_USR.GENERATE_FLAG_YES)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0002, "邮箱");
            }*/ else {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "数据库信息");
            }
        }
        return checkSignDto;
    }
}
