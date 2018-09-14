package com.upay.dao;

import java.util.HashMap;
import java.util.Map;

import com.pactera.dipper.po.BasePo;

public interface IDaoService extends com.pactera.dipper.dao.service.IDaoService {

	// 自定义的业务dao方法

	<T extends BasePo> int insertSelect(T entity);

	int insertSelect(String sqlNme, HashMap<Object, String> parmMap);
	
	
}
