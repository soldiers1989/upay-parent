package com.upay.busi.acc.service.impl;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.acc.service.dto.AccZJUnbindCardDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;


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
public class AccZJUnbindCardService extends AbstractDipperHandler<AccZJUnbindCardDto> {

    @Override
    public AccZJUnbindCardDto execute(AccZJUnbindCardDto accZJPayReceiveDto, Message message)
            throws Exception {
        String zjPayStatus = accZJPayReceiveDto.getZjPayStatus();
        if (StringUtils.isBlank(zjPayStatus)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "中金状态");
        }
        if (DataBaseConstans_ACC.ACCT_NO_BINDED.equals(zjPayStatus)) {
            ExInfo.throwDipperEx("解绑失败，该卡为绑定状态");
        }
        return accZJPayReceiveDto;
    }
}
