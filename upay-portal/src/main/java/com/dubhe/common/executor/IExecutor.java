package com.dubhe.common.executor;




/**
 * 交易前后处理的统一接口
 * @author freeplato
 *
 */
public interface IExecutor {
	
	public ExecutorDTO execute(ExecutorDTO executorDTO);

}
