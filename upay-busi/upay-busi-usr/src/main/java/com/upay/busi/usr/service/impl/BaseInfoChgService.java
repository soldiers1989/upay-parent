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
import com.upay.busi.usr.service.dto.BaseInfoChgDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 个人信息修改
 * 
 * @author liyulong
 * 
 */
public class BaseInfoChgService extends AbstractDipperHandler<BaseInfoChgDto> {

    @Resource
    private IDaoService daoService;
    private final static Logger log = LoggerFactory.getLogger(BaseInfoChgService.class);


    @Override
    public BaseInfoChgDto execute(BaseInfoChgDto dto, Message msg) throws Exception {
        log.info("<------------base info change--------------->");
        String userId = dto.getUserId();
        String headPic = dto.getHeadPic();
        UsrRegInfoPo usr = new UsrRegInfoPo();
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户id");
        }
        usr.setUserId(userId);
        usr = daoService.selectOne(usr);
        if (usr == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0040, userId);
        }

        if (StringUtils.isNotBlank(headPic)) {
            UsrRegInfoPo usrRegInfoPoWhere = new UsrRegInfoPo();
            usrRegInfoPoWhere.setUserId(userId);
            UsrRegInfoPo usrRegInfoUpd = new UsrRegInfoPo();
            usrRegInfoUpd.setHeadPic(headPic);
            usrRegInfoUpd.setLastUpdateTime(dto.getSysTime());
            daoService.update(usrRegInfoUpd, usrRegInfoPoWhere);
        } else {
            String email = StringUtils.isBlank(dto.getEmail()) ? "" : dto.getEmail();
            String qq = StringUtils.isBlank(dto.getQq()) ? "" : dto.getQq();
            String weixin = StringUtils.isBlank(dto.getWeixin()) ? "" : dto.getWeixin();
            String country = StringUtils.isBlank(dto.getCountry()) ? "" : dto.getCountry();
            String nation = StringUtils.isBlank(dto.getNation()) ? "" : dto.getNation();
            String backGround = StringUtils.isBlank(dto.getBackGround()) ? "" : dto.getBackGround();
            String religion = StringUtils.isBlank(dto.getReligion()) ? "" : dto.getReligion();
            String marriage = StringUtils.isBlank(dto.getMarriage()) ? "" : dto.getMarriage();
            String eduBg = StringUtils.isBlank(dto.getEduBg()) ? "" : dto.getEduBg();
            String job = StringUtils.isBlank(dto.getJob()) ? "" : dto.getJob();
            String addressCode = StringUtils.isBlank(dto.getAddressCode()) ? "" : dto.getAddressCode();
            String addressReal = StringUtils.isBlank(dto.getAddressReal()) ? "" : cleanXSS(dto.getAddressReal());
            
            UsrBaseInfoPo usrBaseInfoUpdPo = new UsrBaseInfoPo();
            usrBaseInfoUpdPo.setEmail(email);
            usrBaseInfoUpdPo.setQq(qq);
            usrBaseInfoUpdPo.setWeixin(weixin);
            usrBaseInfoUpdPo.setCountry(country);
            usrBaseInfoUpdPo.setNation(nation);
            usrBaseInfoUpdPo.setBackground(backGround);
            usrBaseInfoUpdPo.setReligion(religion);
            usrBaseInfoUpdPo.setMarriage(marriage);
            usrBaseInfoUpdPo.setEduBg(eduBg);
            usrBaseInfoUpdPo.setJob(job);
            usrBaseInfoUpdPo.setAddressCode(addressCode);
            usrBaseInfoUpdPo.setAddressReal(addressReal);
            usrBaseInfoUpdPo.setLastUpdateTime(new Date());
            UsrBaseInfoPo usrBaseInfoWherePo = new UsrBaseInfoPo();
            usrBaseInfoWherePo.setUserId(userId);
            daoService.update(usrBaseInfoUpdPo, usrBaseInfoWherePo);
        }
        return dto;
    }
    
    private String cleanXSS(String value){
    	value = value.replaceAll("<","&lt;").replaceAll(">","&gt;");
		value = value.replaceAll("\\(","&#40;").replaceAll("\\)","&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)","");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']","\"\"");
		value = value.replaceAll("script","");
		return value;
    }
}
