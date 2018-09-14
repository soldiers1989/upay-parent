package com.upay.commons.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.upay.commons.constants.CommunicationConstants;
import com.upay.commons.util.DateUtil;


/**
 * 带查询条件的公共业务数据结构
 * 
 * @author freeplato
 * 
 */
public class BaseQueryDto extends BaseDto {

    /**
     * 查询范围 01:今天 02:近一周 03:近一个月 04:近三个月 05近一年
     */
    private String queryScope;

    /**
     * 查询起始日期
     */
    private Date queryStart;

    /**
     * 查询终止日期
     */
    private Date queryEnd;


    public String getQueryScope() {
        return queryScope;
    }


    public void setQueryScope(String queryScope) {
        setQueryDate(queryScope, this.getSysDate());
        this.queryScope = queryScope;
    }


    @Override
    public void setSysDate(Date sysDate) {
        super.setSysDate(sysDate);
        setQueryDate(getQueryScope(), sysDate);
    }


    public void setQueryDate(String queryScope, Date date) {
        if (StringUtils.isNotBlank(queryScope) && date != null) {
            setQueryStart(DateUtil.getScopeDate(DateUtil.getRealTime(date, CommunicationConstants.PRE_TIME),
                queryScope));
            setQueryEnd(DateUtil.getRealTime(date, CommunicationConstants.POST_TIME));
        }
    }


    public Date getQueryStart() {
        return queryStart;
    }


    public void setQueryStart(Date queryStart) {
        if (this.queryStart == null) {
            if (queryStart != null) {
                this.queryStart = DateUtil.getRealTime(queryStart, CommunicationConstants.PRE_TIME);
            }
        }
    }


    public void setQueryEnd(Date queryEnd) {
        if (this.queryEnd == null) {
            if (queryEnd != null) {
                this.queryEnd = DateUtil.getRealTime(queryEnd, CommunicationConstants.POST_TIME);
            }
        }
    }


    public Date getQueryEnd() {
        return queryEnd;

    }

}
