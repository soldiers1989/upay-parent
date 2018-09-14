/**
 * 
 */
package com.upay.busi.pay.service.impl;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.pay.service.dto.MicropayStateChangeDto;


/**
 * 微信支付状态转换为订单状态
 * 
 * @author zhanggr
 *
 */
public class MicropayStateChangeService extends AbstractDipperHandler<MicropayStateChangeDto> {

    @Override
    public MicropayStateChangeDto execute(MicropayStateChangeDto micropayStateChangeDto, Message msg)
            throws Exception {

        return micropayStateChangeDto;
    }

}
