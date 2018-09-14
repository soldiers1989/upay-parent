package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 
 * 中金解卡状态判断
 * 
 * @author: liubing
 * @CreateDate:2016年3月23日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2016年3月23日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public class AccZJUnbindCardDto extends BaseDto {

    private String zjPayStatus;


    public String getZjPayStatus() {
        return zjPayStatus;
    }


    public void setZjPayStatus(String zjPayStatus) {
        this.zjPayStatus = zjPayStatus;
    }

}
