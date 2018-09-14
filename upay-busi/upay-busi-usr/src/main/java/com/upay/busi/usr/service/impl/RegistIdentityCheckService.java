package com.upay.busi.usr.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.RegistIdentityCheckDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 用户注册标识检查
 * 
 * @author liubing
 * 
 */
public class RegistIdentityCheckService extends AbstractDipperHandler<RegistIdentityCheckDto> {

    /** 数据库服务接口 */
    @Resource
    private IDaoService daoService;


    @Override
    public RegistIdentityCheckDto execute(RegistIdentityCheckDto dto, Message msg) throws Exception {
    	
        String userName=dto.getUserName();
        String mobile=dto.getMobile();
        String email=dto.getEmail();
        if(StringUtils.isBlank(userName)&&StringUtils.isBlank(mobile)&&StringUtils.isBlank(email)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "查询用户信息参数");
        }
        String str=null;
        UsrRegInfoPo user=null;
        UsrRegInfoPo us=new UsrRegInfoPo();
//        if(StringUtils.isNotBlank(userName)){            
//            us.setUserName(userName);
//            user=daoService.selectOne(us);
//            us.setUserName(null);
//            if(user!=null){
//                str="登陆名";                
//            }
//        }else 
        if(StringUtils.isNotBlank(mobile)){
            us.setMobile(mobile);
            user=daoService.selectOne(us);
            us.setMobile(null);
            if(user!=null){                
                str="手机号";
            }
        }else if(StringUtils.isNotBlank(email)){
            us.setComEmail(email);
            user=daoService.selectOne(us);
            if(user!=null){
                str="邮箱";
            }
        }
        if(str!=null){
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0002,str);
        }
        return dto;
    }
}
