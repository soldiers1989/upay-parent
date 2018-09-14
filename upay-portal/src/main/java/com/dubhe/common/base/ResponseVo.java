package com.dubhe.common.base;

import java.util.ArrayList;
import java.util.List;


/**
 *后台响应对象
 * 
 * @author weizhao.dong
 *
 */
public class ResponseVo {
	private boolean success;
	private String msg;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ResponseVo(){
	}
	
	public ResponseVo(boolean success,String msg){
		this.success=success;
		this.msg=msg;
	}
	
}
