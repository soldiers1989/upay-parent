package com.upay.busi.mer.service.impl;


import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.MerQueryListDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.usr.UsrRegInfoPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 根据手机号或邮箱查询个人注册信息 或者查询商户信息
 */
public class MerQueryListService extends AbstractDipperHandler<MerQueryListDto> {
    private static final Logger logger = LoggerFactory.getLogger(MerQueryListService.class);
    @Resource
    private IDaoService daoService;

    @Override
    public MerQueryListDto execute(MerQueryListDto dto, Message arg1) throws Exception {
        String contactMobile = dto.getContactMobile();
        String contactEmail = dto.getContactEmail();
        MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
        UsrRegInfoPo usrRegInfoPo=new UsrRegInfoPo();
        List<MerBaseInfoPo> merInfos = null;
        List<UsrRegInfoPo>usrInfos=null;
        if (StringUtils.isBlank(contactMobile) && StringUtils.isBlank(contactEmail)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "手机号和邮件不能同时为空");
        }
        if (StringUtils.isNotBlank(contactMobile)) {
            merBaseInfoPo.setContactMobile(contactMobile);
            usrRegInfoPo.setMobile(contactMobile);
            merInfos = daoService.selectList(merBaseInfoPo);
            usrInfos=daoService.selectList(usrRegInfoPo);

        } else if (StringUtils.isNotBlank(contactEmail)) {
            merBaseInfoPo.setContactEmail(contactEmail);
            usrRegInfoPo.setComEmail(contactEmail);
            merInfos = daoService.selectList(merBaseInfoPo);
            usrInfos=daoService.selectList(usrRegInfoPo);
        }
        List<Map<String, Object>> merList = new ArrayList<>();
        List<Map<String, Object>> usrList = new ArrayList<>();
        logger.debug("查询完成");
        boolean flag=true;
        if (merInfos != null && merInfos.size() > 0) {
            logger.debug("设置dto值");
            for (int i = 0; i < merInfos.size(); i++) {
                MerBaseInfoPo merInfoPo = merInfos.get(i);
                if (merBaseInfoPo != null) {
                    merList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(merInfoPo));
                }
            }
            dto.setMerList(merList);
        }
        if (usrInfos != null && usrInfos.size() > 0) {
            logger.debug("设置dto值");
            for (int i = 0; i < usrInfos.size(); i++) {
                UsrRegInfoPo usrInfoPo=usrInfos.get(i);
                if (usrInfoPo != null) {
                    usrList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(usrInfoPo));
                }
            }
            dto.setUsrList(usrList);
        }


        return dto;
    }

}
