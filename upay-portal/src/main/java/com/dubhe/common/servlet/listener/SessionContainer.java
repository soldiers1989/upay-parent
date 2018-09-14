package com.dubhe.common.servlet.listener;

import java.io.Serializable;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.dubhe.common.datastructure.Dto;
import com.dubhe.common.datastructure.impl.BaseDto;


/**
 * Session容器
 * 
 * @see javax.servlet.http.HttpSessionBindingListener
 */
public class SessionContainer implements HttpSessionBindingListener, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    /**
     * 报表对象集
     */
    private Dto reportDto;


    public SessionContainer() {
        super();
        reportDto = new BaseDto();
    }


    /**
     * 清除会话容器缓存对象
     */
    public void cleanUp() {
        // userInfo不能在此批量重置,只能使用setUserInfo(null)方法对其进行独立操作
        // userInfo = null;
        reportDto.clear();
    }

    public void valueBound(HttpSessionBindingEvent event) {
        // System.out.println("会话创建了");
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        // System.out.println("会话销毁了");
    }


    public Dto getReportDto() {
        return reportDto;
    }


    public void setReportDto(Dto reportDto) {
        this.reportDto = reportDto;
    }

}
