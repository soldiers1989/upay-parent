package com.dubhe.common.annotation;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.annotation.JSONField;
import com.dubhe.common.util.MoneyUtil;


/**
 * 金额字段响应处理（元转分）
 * @author leo
 *
 */
public class RspMoneyTranCheck implements ICheck {

    @Override
    public void checkField(Field field, Object object,String sessionId,HttpServletRequest request) throws Exception {
        field.setAccessible(true);
        Object obj = field.get(object);
        RspMoneyTran rspMoneyTran = field.getAnnotation(RspMoneyTran.class);
        int decimal = rspMoneyTran.decimal();
        try{
        	obj = MoneyUtil.transferY2F((BigDecimal) obj,decimal);
        	field.set(object, obj);
        }catch(Exception e){
        	e.printStackTrace();
        	throwException(field);
        }
    }

    private void throwException(Field field) {
        throw new IllegalArgumentException(field.getAnnotation(JSONField.class).name() + "不是金额类型");
    }
    
}
