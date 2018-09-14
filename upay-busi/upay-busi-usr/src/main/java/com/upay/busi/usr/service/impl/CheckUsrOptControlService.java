package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.CheckUsrOptControlDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.CommonMethodUtils;
import com.upay.dao.po.usr.UsrOptControlInfoPo;
import com.upay.dao.po.usr.UsrRegInfoPo;

/**
 * 用户交易权限检查
 * @author shangqiankun
 * @version 创建时间：2016年8月13日 上午10:18:59
 */
public class CheckUsrOptControlService extends AbstractDipperHandler<CheckUsrOptControlDto> {

    @Resource
    private IDaoService daoService;
    
    @Override
    public CheckUsrOptControlDto execute(CheckUsrOptControlDto dto, Message message) throws Exception {
        String userId=dto.getUserId();
        String chnlId=dto.getChnlId();
        if(StringUtils.isNotBlank(userId)){
        	UsrRegInfoPo user=new UsrRegInfoPo();
            user.setUserId(userId);
            user=daoService.selectOne(user);
            if(user==null){
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0005,userId);
            }
            UsrOptControlInfoPo opt=new UsrOptControlInfoPo();
            opt.setUserCertLevel(user.getUserCertLevel());
            opt.setTransCode(dto.getTransCode());
            opt.setChnlId(chnlId);
            opt=daoService.selectOne(opt);
            if(opt==null){
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0032,"用户交易权限");
            }
            if(!DataBaseConstants_USR.OP_PERMISSION_YES.equals(opt.getOpPermission())){
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0033);
            }
            dto.setMobile(user.getMobile());
        }
        if(StringUtils.isBlank(chnlId)){
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "渠道id");
        }
        
        
        if(StringUtils.isBlank(dto.getRouteCode())){
            if(StringUtils.isBlank(dto.getPayType())){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付方式");
            }
            //根据支付方式获取资金通道
            String routeCode=CommonMethodUtils.getRouteCodeByPayType(dto.getPayType());
            dto.setRouteCode(routeCode);
        }
        return dto;
    }

}
