package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.QueryUserNameIsExistDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrRegInfoPo;

/**
 * 
 * @author Administrator
 *检查用户名是否己存在
 */
public class QueryUserNameIsExistService extends AbstractDipperHandler<QueryUserNameIsExistDto>{
	@Resource
    private IDaoService daoService;
	@Override
	public QueryUserNameIsExistDto execute(QueryUserNameIsExistDto dto,
			Message arg1) throws Exception {
		String userName = dto.getUserName();
		if(StringUtils.isBlank(userName)){
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "参数用户名不能为空!");
		}
		UsrRegInfoPo userReg=new UsrRegInfoPo();
		userReg.setUserName(userName);
		userReg=daoService.selectOne(userReg);
		if(userReg!=null){
			dto.setIsExist("Y");
		}else{
			dto.setIsExist("N");
		}
		return dto;
	}
}
