package com.upay.busi.usr.service.impl;


import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.UserRegListQueryDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrRegInfoPo;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsrRegQueryListService extends AbstractDipperHandler<UserRegListQueryDto> {
    private static final Logger logger = LoggerFactory.getLogger(UsrRegQueryListService.class);
    @Resource
    private IDaoService daoService;

    @Override
    public UserRegListQueryDto execute(UserRegListQueryDto dto, Message arg1) throws Exception {


        String comEmail = dto.getComEmail();
        String mobile = dto.getMobile();

        List<Map<Object, Object>> mapList=new ArrayList<>();
      /*  UsrRegInfoPo base=new UsrRegInfoPo();
        List<UsrRegInfoPo> usrRegInfoPos = null;*/
        if (StringUtils.isBlank(comEmail) && StringUtils.isBlank(mobile)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "手机号和邮件不能同时为空");
        }
        /*if (StringUtils.isNotBlank(comEmail)) {
            base.setComEmail(comEmail);
            usrRegInfoPos = daoService.selectList(base);
        } else if (StringUtils.isNotBlank(mobile)) {
            base.setMobile(mobile);
            usrRegInfoPos = daoService.selectList(base);
        }*/

        Map<Object,Object>map=new HashMap<>();
        if (StringUtils.isNotBlank(comEmail)) {
            map.put("comEmail",comEmail);
        } else if (StringUtils.isNotBlank(mobile)) {
            map.put("mobile",mobile);
        }
        List<Map<Object, Object>> maps = daoService.selectList(UsrRegInfoPo.class.getName() + ".getMerAngUsr", map);

        logger.debug("查询完成");
        if (maps != null && maps.size() > 0) {
            logger.debug("设置dto值");
            for (int i = 0; i < maps.size(); i++) {
                Map<Object, Object> objectMap = maps.get(i);
                if (objectMap != null&&objectMap.size()>0) {
                    mapList.add(objectMap);
                }
            }
            dto.setUsrList(mapList);
        } else {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "用户注册信息");
        }
        return dto;
    }

}
