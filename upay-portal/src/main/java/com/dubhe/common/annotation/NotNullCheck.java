/**
 * 
 */
package com.dubhe.common.annotation;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 不为空验证
 * @author Hing <xingguang.ren@pactera.com>
 * 
 */
public class NotNullCheck implements ICheck {

    @Override
    public void checkField(Field field, Object object,String sessionId,HttpServletRequest request) throws Exception {
        field.setAccessible(true);
        Object obj = field.get(object);
        if (null == obj) {
            throwException(field, "null");
        }
        if (obj instanceof String && "".equals((String) obj)) {
            throwException(field, "空");
        }
    }


    private void throwException(Field field, String temp) {
        throw new IllegalArgumentException(field.getAnnotation(JSONField.class).name() + "对应的值不能为" + temp
                + " !!");
    }
}
