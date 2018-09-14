package com.upay.dao;

import java.util.HashMap;
import java.util.Map;

import com.pactera.dipper.dao.service.DipperDaoService;
import com.pactera.dipper.po.BasePo;

public class DaoServiceImpl extends DipperDaoService implements IDaoService{

	@Override
	public <T extends BasePo> int insertSelect(T entity) {
		return getSqlSession().insert(entity.getClass().getName() + ".insertSelect",entity);
	}

	@Override
	public int insertSelect(String sqlNme,HashMap<Object, String> parmMap) {
		return getSqlSession().insert(sqlNme,parmMap);
	}
}
