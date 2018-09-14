package com.upay.busi.mer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.MerQueryReltypeDto;
import com.upay.dao.po.mer.MerReltypePo;

/**
 * 商户类别查询
 * 
 * @author yanzixiong
 * @version 创建时间：2016年11月8日09:04:05
 */
public class MerQueryReltypeService extends AbstractDipperHandler<MerQueryReltypeDto> {

	@Resource
    private IDaoService daoService;
	
	@Override
	public MerQueryReltypeDto execute(MerQueryReltypeDto dto, Message msg)
			throws Exception {
		String reltypeId = dto.getReltypeId();
		if (StringUtils.isBlank(reltypeId)) {
            reltypeId = "root";
        }
		
        List<MerReltypePo> merReltypePoList = null;// 普通查询
        List<Map<String, Object>> merReltypeDtoList = new ArrayList<Map<String, Object>>();

        MerReltypePo merReltypePo = new MerReltypePo();
        merReltypePo.setOriReltypeType(reltypeId);
        
        merReltypePoList = daoService.selectList(merReltypePo);
        if (merReltypePoList.size() != 0){
        	for (int i = 0; i < merReltypePoList.size(); i++){
        		dto.setReltypeName(merReltypePoList.get(i).getReltypeName());
        		dto.setReltypeId(merReltypePoList.get(i).getReltypeId());
        		
        		merReltypeDtoList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(dto));
        	}
        	dto.setMerReltypeList(merReltypeDtoList);
        }
        
		return dto;
	}

}
