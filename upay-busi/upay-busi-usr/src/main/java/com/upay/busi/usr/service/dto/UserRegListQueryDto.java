package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;

import java.util.List;
import java.util.Map;


/**
 * @author liudan
 * @date  <code>2018-4-2</code>
 */
public class UserRegListQueryDto extends BaseDto {
    private String mobile;
    private String comEmail;
    private String userName;


    private List<Map<Object, Object>> usrList;

    public List<Map<Object, Object>> getUsrList() {
        return usrList;
    }

    public void setUsrList(List<Map<Object, Object>> usrList) {
        this.usrList = usrList;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getComEmail() {
        return comEmail;
    }

    public void setComEmail(String comEmail) {
        this.comEmail = comEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
