package com.dubhe.common.json;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 带查询条件的公共业务数据结构
 * @author freeplato
 *
 */
public class QueryHead{

	/**
	 * 查询范围
	 * 01:今天   02:近一周  03:近一个月   04:近三个月
	 */
    @JSONField(name = "QUERY_SCOPE")
    private String queryScope;
    
    /**
     * 查询起始日期
     */
    @JSONField(name = "QUERY_START",format="yyyy-MM-dd")
    private Date queryStart;
    
    /**
     * 查询终止日期
     */
    @JSONField(name = "QUERY_END",format="yyyy-MM-dd")
    private Date queryEnd;

    public String getQueryScope() {
		return queryScope;
	}

	public void setQueryScope(String queryScope) {
		this.queryScope = queryScope;
	}

	public Date getQueryStart() {
		return queryStart;
	}

	public void setQueryStart(Date queryStart) {

		this.queryStart = queryStart;
	}

	public Date getQueryEnd() {
		return queryEnd;
	}

	public void setQueryEnd(Date queryEnd) {
		this.queryEnd = queryEnd;
	}

}
