package com.dubhe.common.executor;

import java.util.LinkedList;
import java.util.List;


/**
 * 交易前、后处理的容器
 * 
 * @author WUDUFENG
 * @since 2014年4月29日
 */
public class ExecutorContext {

    private List<IExecutor> executorContext = new LinkedList<IExecutor>();

    public ExecutorDTO execute(ExecutorDTO executorDTO) throws Exception {
        for (IExecutor executor : executorContext) {
        	executorDTO = executor.execute(executorDTO);
        }
		return executorDTO;
    }


    public void setExecutorContext(List<IExecutor> executorContext) {
        this.executorContext = executorContext;
    }
}
