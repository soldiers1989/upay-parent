package com.dubhe.common.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * 
 * 
 * weizhao.dong
 *
 */
public class BaseDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    protected static final String DATE_FORMAT = "yyyy.MM.dd";
	protected static final String TIME_FORMAT = "HH:mm:ss";

	protected static final String DATE_TIME_FORMAT = "yyyy.MM.dd HH:mm:ss";

	protected static final String TIMESTAMP_FORMAT = "yyyy.MM.dd HH:mm:ss.S";
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
