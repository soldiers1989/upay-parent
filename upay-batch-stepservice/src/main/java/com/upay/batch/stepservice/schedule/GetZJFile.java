package com.upay.batch.stepservice.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;

public class GetZJFile extends AbstractStepExecutor<Object, Object>{
	 private final static Logger LOG = LoggerFactory.getLogger(GetCoreFile.class);

	@Override
	public void execute(BatchParams batchParams, int index, Object data,
			Object object) throws BatchException {
		// TODO Auto-generated method stub
		super.execute(batchParams, index, data, object);
	}

	@Override
	public List<Object> getDataList(BatchParams batchParams, int offset,
			int pageSize, Object object) throws BatchException {
		// TODO Auto-generated method stub
		return super.getDataList(batchParams, offset, pageSize, object);
	}

	@Override
	public List<Object> getObjectList(BatchParams batchParams)
			throws BatchException {
		// TODO Auto-generated method stub
		return super.getObjectList(batchParams);
	}

	@Override
	public int getTotalResult(BatchParams batchParams, Object object)
			throws BatchException {
		// TODO Auto-generated method stub
		return super.getTotalResult(batchParams, object);
	}
	 
}