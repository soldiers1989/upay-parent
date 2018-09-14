/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

import java.io.Serializable;

/**
 *
 */
public class ExceptionInfoDto extends BaseDto implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String errInfo;
    private String errCode;
    private String errFlag;

    public String getErrFlag() {
        return errFlag;
    }

    public void setErrFlag(String errFlag) {
        this.errFlag = errFlag;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
