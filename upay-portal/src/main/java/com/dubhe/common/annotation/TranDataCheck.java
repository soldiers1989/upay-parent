/**
 * 
 */
package com.dubhe.common.annotation;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * @author leo
 * 
 */
public class TranDataCheck implements ICheck {
    
    @Override
    public void checkField(Field field, Object object,String sessionId,HttpServletRequest request) throws Exception {
        
        ApplicationContext applicationContext = 
                WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        field.setAccessible(true);
        String pwd = (String) field.get(object);
//        if(StringUtils.isNotBlank(pwd)){
//            IMsgDealService msgDealService = (IMsgDealService) applicationContext.getBean("msgDealService");
//            pwd = msgDealService.transEncData(sessionId, pwd, wapFlag);
//            field.set(object, pwd);
//        }
    }

}
