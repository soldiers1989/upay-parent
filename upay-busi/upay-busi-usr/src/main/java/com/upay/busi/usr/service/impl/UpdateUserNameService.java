package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.UpdateUserNameDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrRegInfoPo;
/**
 * 更新用户名
 * @author Administrator
 *
 */
public class UpdateUserNameService extends AbstractDipperHandler<UpdateUserNameDto>{
	@Resource
    private IDaoService daoService;
	@Override
	public UpdateUserNameDto execute(UpdateUserNameDto dto, Message arg1)
			throws Exception {
		String userName = dto.getUserName();
		String userId = dto.getUserId();
		if(StringUtils.isBlank(userName)){
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "参数用户名不能为空!");
		}
		if(StringUtils.isBlank(userId)){
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "参数用户ID不能为空!");
		}
		UsrRegInfoPo userReg=new UsrRegInfoPo();
		userReg.setUserName(userName);
		userReg=daoService.selectOne(userReg);
		if(userReg!=null){
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "用户名己存在!");
		}else{
			UsrRegInfoPo usrRegSet=new UsrRegInfoPo();
			usrRegSet.setUserName(userName);
			
			UsrRegInfoPo usrReghwere=new UsrRegInfoPo();
			usrReghwere.setUserId(userId);
			
			daoService.update(usrRegSet, usrReghwere);
		}
		
		return dto;
	}

}
