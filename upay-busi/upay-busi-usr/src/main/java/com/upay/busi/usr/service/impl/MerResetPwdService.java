package com.upay.busi.usr.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.MerResetPwdDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.Md5Util;
import com.upay.dao.ParmsContext;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.usr.UsrComRegRecPo;


/**
 * 商户重置登录密码邮件登记
 * 
 * @author liyulong
 * 
 */
public class MerResetPwdService extends AbstractDipperHandler<MerResetPwdDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public MerResetPwdDto execute(MerResetPwdDto merResetPwdDto, Message msg) throws Exception {
        String mailTo = merResetPwdDto.getMailTo();
        String transCode = merResetPwdDto.getTransCode();
        if (StringUtils.isBlank(mailTo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "企业邮箱");
        }
        if (StringUtils.isBlank(transCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "transCode");
        }
        UsrComRegRecPo usrComRegRecPo = new UsrComRegRecPo();
        usrComRegRecPo.setComEmail(mailTo);
        usrComRegRecPo.setGenerateFlag(DataBaseConstants_USR.GENERATE_FLAG_YES);
        usrComRegRecPo.setTransCode(DataBaseConstants_USR.TRANS_CODE_SENDMAIL);
        usrComRegRecPo = daoService.selectOne(usrComRegRecPo);
        if (usrComRegRecPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0029, "该企业邮箱注册信息");
        } else {
            UsrComRegRecPo usrComRegRecPo3 = new UsrComRegRecPo();
            usrComRegRecPo3.setComEmail(mailTo);
            // usrComRegRecPo.setGenerateFlag(DataBaseConstants_USR.GENERATE_FLAG_NO1);
            usrComRegRecPo3.setTransCode(transCode);
            usrComRegRecPo3 = daoService.selectOne(usrComRegRecPo3);
            if (usrComRegRecPo3 == null) {
                UsrComRegRecPo usrComRegRecPo2 = new UsrComRegRecPo();
                usrComRegRecPo2.setComEmail(mailTo);
                usrComRegRecPo2.setSid(Md5Util.toMD5(mailTo + "1"));
                usrComRegRecPo2.setGenerateFlag(DataBaseConstants_USR.GENERATE_FLAG_NO1);
                usrComRegRecPo2.setSendNum(1);
                // 邮箱失效时效
                int valiedTerm =
                        new BigDecimal(ParmsContext.getParmByKey(CmparmConstants.VALIED_TERM).toString())
                            .intValue();
                Date valiedDate = SysInfoContext.getSysTime();
                valiedDate = DateUtil.add(valiedDate, Calendar.HOUR, valiedTerm);
                usrComRegRecPo2.setValiedDate(valiedDate);
                usrComRegRecPo2.setLastSendTime(SysInfoContext.getSysTime());
                usrComRegRecPo2.setTransCode(transCode);
                daoService.insert(usrComRegRecPo2);
            } else {
                String generateFlag = usrComRegRecPo3.getGenerateFlag();
                if (StringUtils.isBlank(generateFlag)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "generateFlag");
                }
                if (generateFlag.equals(DataBaseConstants_USR.GENERATE_FLAG_YES1)) {
                    usrComRegRecPo3.setGenerateFlag(DataBaseConstants_USR.GENERATE_FLAG_NO1);
                }
                usrComRegRecPo3.setSendNum(usrComRegRecPo3.getSendNum() + 1);
                int valiedTerm =
                        new BigDecimal(ParmsContext.getParmByKey(CmparmConstants.VALIED_TERM).toString())
                            .intValue();
                Date valiedDate = SysInfoContext.getSysTime();
                valiedDate = DateUtil.add(valiedDate, Calendar.HOUR, valiedTerm);
                usrComRegRecPo3.setValiedDate(valiedDate);
                usrComRegRecPo3.setLastSendTime(SysInfoContext.getSysTime());
                UsrComRegRecPo usrComRegRecPo2 = new UsrComRegRecPo();
                usrComRegRecPo2.setComEmail(mailTo);
                usrComRegRecPo2.setTransCode(transCode);
                daoService.update(usrComRegRecPo3, usrComRegRecPo2);
            }
        }
        return merResetPwdDto;
    }
}
