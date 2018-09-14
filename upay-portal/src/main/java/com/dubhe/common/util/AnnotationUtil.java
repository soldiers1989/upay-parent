package com.dubhe.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.annotation.JSONField;
import com.dubhe.common.annotation.ICheck;
import com.dubhe.common.annotation.NotNull;
import com.dubhe.common.annotation.NotNullCheck;
import com.dubhe.common.annotation.ReqMoneyTran;
import com.dubhe.common.annotation.ReqMoneyTranCheck;
import com.dubhe.common.annotation.RspMoneyTran;
import com.dubhe.common.annotation.RspMoneyTranCheck;
import com.dubhe.common.annotation.TranData;
import com.dubhe.common.annotation.TranDataCheck;


/**
 * 注解检查
 * 
 * @author freeplato
 * 
 */
public class AnnotationUtil {
    private static Map<Class<?>, ICheck> map = new HashMap<Class<?>, ICheck>(3);

    private static final String LIST_FIELD_FLAG = "ROWS";
    private static final String LIST_FIELD_CLASS_SUB_FLAG = "Sub";
    private static final String LIST_FIELD_CLASS_RSP_FLAG = "Rsp";
    private static final String LIST_FIELD_CLASS_METHOD_FLAG = "get";

    static {
        map.put(NotNull.class, new NotNullCheck());
        map.put(TranData.class, new TranDataCheck());
        map.put(ReqMoneyTran.class, new ReqMoneyTranCheck());
        map.put(RspMoneyTran.class, new RspMoneyTranCheck());
    }


    public static void checkField(Class<?> clazz, Object object, String sessionId, HttpServletRequest request)
            throws Exception {
        for (Field field : clazz.getDeclaredFields()) {
            for (Annotation anno : field.getAnnotations()) {
                if (anno.annotationType().equals(JSONField.class)) {
                    JSONField jsonField = field.getAnnotation(JSONField.class);
                    if (LIST_FIELD_FLAG.equals(jsonField.name())) {
                        StringBuffer clazzSubName = new StringBuffer();
                        String genericReturnTypeStr = field.getGenericType().toString();
                        if (genericReturnTypeStr.indexOf("<") != -1) {
                            clazzSubName.append(genericReturnTypeStr.substring(
                                genericReturnTypeStr.indexOf("<") + 1, genericReturnTypeStr.indexOf(">")));

                        } else {
                            clazzSubName
                                .append(
                                    clazz.getName().substring(0,
                                        clazz.getName().indexOf(LIST_FIELD_CLASS_RSP_FLAG)))
                                .append(LIST_FIELD_CLASS_SUB_FLAG).append(LIST_FIELD_CLASS_RSP_FLAG);
                        }
                        Method[] method = object.getClass().getMethods();
                        for (Method method2 : method) {
                            if (method2.getName().startsWith(LIST_FIELD_CLASS_METHOD_FLAG)) {
                                Class<?> obj = Class.forName(clazzSubName.toString());
                                Object subObj = method2.invoke(object);
                                if (subObj != null && subObj instanceof List) {
                                    List<Object> subObjArr = (List<Object>) subObj;
                                    for (Object subObject : subObjArr) {
                                        checkField(obj, subObject, sessionId, request);
                                    }
                                }
                            }
                        }
                    }
                }

                if (map.containsKey(anno.annotationType())) {
                    map.get(anno.annotationType()).checkField(field, object, sessionId, request);
                    // break;
                }
            }
        }
    }
}
