package com.dubhe.common.annotation;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * @author Hing <xingguang.ren@pactera.com>
 * 
 */
public interface ICheck {
    
    public void checkField(Field field, Object object, String sessionId, HttpServletRequest request) throws Exception;
}
