package com.upay.busi.usr.service.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.ComRegDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.Md5Util;
import com.upay.dao.ParmsContext;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.usr.UsrComRegRecPo;


/**
 * 企业注册
 * 
 * @author liyulong
 * 
 */
public class ComRegService extends AbstractDipperHandler<ComRegDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public ComRegDto execute(ComRegDto comRegDto, Message msg) throws Exception {
        String mailTo = comRegDto.getMailTo();
        String transCode = comRegDto.getTransCode();
        if (StringUtils.isBlank(mailTo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "企业邮箱");
        }
       /* if (StringUtils.isBlank(transCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "transCode");
        }*/
        Map<String, Object> whereMap = new HashMap<>(10);
        whereMap.put("comEmail", mailTo);
        whereMap.put("transCode",DataBaseConstants_USR.TRANS_CODE_SENDMAIL);
        List<Map<String, String>> orderByList = new ArrayList<>(10);
        Map<String, String> orderByMerApplyNo= new HashMap<>(10);
        orderByMerApplyNo.put("columnName", "LAST_SEND_TIME");
        orderByMerApplyNo.put("sort", "desc");
        orderByList.add(orderByMerApplyNo);
        whereMap.put("orderBy", orderByList);
        List<UsrComRegRecPo> usrComRegRecPos = daoService.selectList(UsrComRegRecPo.class.getName() + ".selectList", whereMap);
        UsrComRegRecPo usrComRegRecPo = null;
        if (usrComRegRecPos != null&&usrComRegRecPos.size()>0) {
            usrComRegRecPo=usrComRegRecPos.get(0);
        }
        if (usrComRegRecPo == null) {
            UsrComRegRecPo usrComRegRecPo2 = new UsrComRegRecPo();
            usrComRegRecPo2.setComEmail(mailTo);
            usrComRegRecPo2.setSid(Md5Util.toMD5(mailTo + "1"));
            usrComRegRecPo2.setGenerateFlag(DataBaseConstants_USR.GENERATE_FLAG_NO);
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
            usrComRegRecPo.setSendNum(usrComRegRecPo.getSendNum() + 1);
            int valiedTerm =
                    new BigDecimal(ParmsContext.getParmByKey(CmparmConstants.VALIED_TERM).toString())
                            .intValue();
            Date valiedDate = SysInfoContext.getSysTime();
            valiedDate = DateUtil.add(valiedDate, Calendar.HOUR, valiedTerm);
            usrComRegRecPo.setValiedDate(valiedDate);
            usrComRegRecPo.setLastSendTime(SysInfoContext.getSysTime());
            UsrComRegRecPo usrComRegRecPo2 = new UsrComRegRecPo();
            usrComRegRecPo2.setComEmail(mailTo);
            usrComRegRecPo2.setTransCode(transCode);
            daoService.update(usrComRegRecPo, usrComRegRecPo2);
        }



     /*
        UsrComRegRecPo usrComRegRecPo = new UsrComRegRecPo();
        usrComRegRecPo.setComEmail(mailTo);
        usrComRegRecPo.setTransCode(transCode);
        usrComRegRecPo = daoService.selectOne(usrComRegRecPo);
        if (usrComRegRecPo == null) {
            UsrComRegRecPo usrComRegRecPo2 = new UsrComRegRecPo();
            usrComRegRecPo2.setComEmail(mailTo);
            usrComRegRecPo2.setSid(Md5Util.toMD5(mailTo + "1"));
            usrComRegRecPo2.setGenerateFlag(DataBaseConstants_USR.GENERATE_FLAG_NO);
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
            usrComRegRecPo.setSendNum(usrComRegRecPo.getSendNum() + 1);
            int valiedTerm =
                    new BigDecimal(ParmsContext.getParmByKey(CmparmConstants.VALIED_TERM).toString())
                        .intValue();
            Date valiedDate = SysInfoContext.getSysTime();
            valiedDate = DateUtil.add(valiedDate, Calendar.HOUR, valiedTerm);
            usrComRegRecPo.setValiedDate(valiedDate);
            usrComRegRecPo.setLastSendTime(SysInfoContext.getSysTime());
            UsrComRegRecPo usrComRegRecPo2 = new UsrComRegRecPo();
            usrComRegRecPo2.setComEmail(mailTo);
            usrComRegRecPo2.setTransCode(transCode);
            daoService.update(usrComRegRecPo, usrComRegRecPo2);
        }*/
        return comRegDto;
    }
}
