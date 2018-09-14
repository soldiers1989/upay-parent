/**
 * 
 */
package com.upay.busi.pay.service.impl;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.pay.service.dto.CheckWiXinOpenIdDto;
import com.upay.commons.dict.AppCodeDict;


/**
 * 校验微信下单的openid 即授权
 * 
 * @author zhanggr
 *
 */
public class CheckWiXinOpenIdService extends AbstractDipperHandler<CheckWiXinOpenIdDto> {

    @Override
    public CheckWiXinOpenIdDto execute(CheckWiXinOpenIdDto checkWiXinOpenIdDto, Message msg) throws Exception {
        String openId = checkWiXinOpenIdDto.getOpenId();
        String subOpenid = checkWiXinOpenIdDto.getSubOpenid();
        if (StringUtils.isBlank(openId) && StringUtils.isBlank(subOpenid)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0104);
        }

        return checkWiXinOpenIdDto;
    }

}
