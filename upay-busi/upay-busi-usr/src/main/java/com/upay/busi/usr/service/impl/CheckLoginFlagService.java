/**
 *
 */
package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import com.upay.commons.constants.DataBaseConstants_USR;
import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.CheckLoginFlagDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrRegInfoPo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shang
 * 2016年11月7日
 */
public class CheckLoginFlagService extends AbstractDipperHandler<CheckLoginFlagDto> {


    @Resource
    IDaoService daoService;

    @Override
    public CheckLoginFlagDto execute(CheckLoginFlagDto dto, Message msg) throws Exception {
        if (StringUtils.isBlank(dto.getLoginFlag())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "登陆标识");
        }
        boolean check = false;
        UsrRegInfoPo us = null;
        UsrRegInfoPo user = new UsrRegInfoPo();
        user.setMobile(dto.getLoginFlag());
        //todo:手机号和邮箱 不能作为唯一标识
//        us=daoService.selectOne(user);
        Map<String, Object> whereMap = new HashMap<>(10);
        whereMap.put("mobile", dto.getLoginFlag());
        List<Map<String, String>> orderByList = new ArrayList<>(10);
        Map<String, String> orderByActiveTime = new HashMap<>(10);
        orderByActiveTime.put("columnName", "ACTIVE_TIME");
        orderByActiveTime.put("sort", "desc");
        orderByList.add(orderByActiveTime);
        whereMap.put("orderBy", orderByList);
        List<UsrRegInfoPo> UsrRegInfoPos = daoService.selectList(user.getClass().getName() + ".selectList", whereMap);
        if (UsrRegInfoPos != null && UsrRegInfoPos.size() > 0) {
            check = true;
        } else {
//            user.setMobile(null);
//            user.setComEmail(dto.getLoginFlag());
            whereMap.remove("mobile");
            whereMap.put("comEmail", dto.getLoginFlag());
            UsrRegInfoPos = daoService.selectList(user.getClass().getName() + ".selectList", whereMap);
//            us = daoService.selectOne(user);
            if (UsrRegInfoPos != null && UsrRegInfoPos.size() > 0) {
                check = true;
            }
        }
        if (check) {
            dto.setIfExist(CommonConstants_GNR.IF_EXIST_Y);
        } else {
            dto.setIfExist(CommonConstants_GNR.IF_EXIST_N);
        }
        return dto;
    }

}
