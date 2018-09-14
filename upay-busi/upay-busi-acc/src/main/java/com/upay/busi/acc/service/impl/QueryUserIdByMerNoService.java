package com.upay.busi.acc.service.impl;



import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.QueryUserIdByMerNoDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;

/**
 * 批量  根据商户号查询user id
 * 
 * @author liu bing
 * 
 */
public class QueryUserIdByMerNoService extends AbstractDipperHandler<QueryUserIdByMerNoDto> {
	@Resource
    private IDaoService daoService;
	
    @Override
    public QueryUserIdByMerNoDto execute(QueryUserIdByMerNoDto dto, Message message) throws Exception {
    	String merNo = dto.getMerNo();
    	if(StringUtils.isBlank(merNo)){
    		ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "付款商户号不能为空");
    	}
    	//根据商户号查询用户信息
    	MerBaseInfoPo merBaseInfo=new MerBaseInfoPo();
    	merBaseInfo.setMerNo(merNo);
    	merBaseInfo=daoService.selectOne(merBaseInfo);
    	if(null==merBaseInfo){
    		ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "付款商户未开通");
    	}
    	
    	dto.setUserId(merBaseInfo.getUserId());
        return dto;
    }
}
