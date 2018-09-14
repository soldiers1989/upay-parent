package com.ups.api;

import java.util.Map;

/**
 *
 * Created by Guo on 2016/12/11.
 */
public interface UPayClient {

    /**
     * 后台请求
     * @param request
     * @return
     * @throws Exception
     */
    public Map<String, String> execute(Map<String, String> request) throws Exception;

    /**
     * 前台请求（页面跳转到收银台）
     * @param request
     * @throws Exception
     */
    public String pageExecute(Map<String, String> request) throws Exception;
}
