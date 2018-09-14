package com.upay.busi.gnr.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.gnr.service.dto.MenuQueryDto;
import com.upay.busi.gnr.service.dto.MenuQueryDto.MenuQuerySubDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.menu.MenuInfoBookPo;
import com.upay.dao.po.menu.UserMenuBookPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 菜单查询
 * 
 * @author liyulong
 * 
 */
public class MenuQueryServiceImpl extends AbstractDipperHandler<MenuQueryDto> {
    private static final Logger log = LoggerFactory.getLogger(MenuQueryServiceImpl.class);
    @Resource
    private IDaoService daoService;


    @Override
    public MenuQueryDto execute(MenuQueryDto menuQueryDto, Message message) throws Exception {
        String userCertLevel = menuQueryDto.getUserCertLevel();// 用户等级
        String userId = menuQueryDto.getUserId();
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户ID");
        }
        if (StringUtils.isBlank(userCertLevel)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户认证等级");
        }
        UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
        usrRegInfoPo.setUserId(userId);
        usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
        if (usrRegInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, userId);
        }
        String regType = null;
        regType = usrRegInfoPo.getRegType();
        UserMenuBookPo userMenuBookPo = new UserMenuBookPo();
        userMenuBookPo.setRegType(regType);
        String merLevel = usrRegInfoPo.getMerLevel();
        if (StringUtils.isBlank(merLevel)) {
            userMenuBookPo.setUserCertLevel(userCertLevel);
        } else {
            userMenuBookPo.setMerLevel(merLevel);
        }
        List<UserMenuBookPo> uArrayList = new ArrayList<UserMenuBookPo>();
        if (StringUtils.isBlank(merLevel)) {
            uArrayList =
                    daoService.selectList(UserMenuBookPo.class.getName() + ".userCertLevel", userMenuBookPo);
        } else {
            uArrayList = daoService.selectList(UserMenuBookPo.class.getName() + ".merLevel", userMenuBookPo);
        }
        if (uArrayList.size() != 0) {
            List<Map<String, Object>> newRows = new ArrayList<Map<String, Object>>();
            for (UserMenuBookPo uMenuBookPo : uArrayList) {
                String menueId = null;
                menueId = uMenuBookPo.getMenuId();
                MenuInfoBookPo menuInfoBookPo = new MenuInfoBookPo();
                menuInfoBookPo.setMenuId(menueId);
                menuInfoBookPo = daoService.selectOne(menuInfoBookPo);
                if (menuInfoBookPo != null) {
                    MenuQuerySubDto menuQuerySubDto = menuQueryDto.new MenuQuerySubDto();
                    BeanUtils.copyProperties(menuQuerySubDto, menuInfoBookPo);
                    Map<String, Object> subDtoMap = BeanCopyUtil.copyBean2MapStrObjNoClass(menuQuerySubDto);
                    newRows.add(subDtoMap);
                }
            }
            menuQueryDto.setRows(newRows);
        }
        // log.debug(menuQueryDto.getRows().size() + "*************");
        log.debug("-----------------------------end");
        return menuQueryDto;
    }
}
