/**
 * 
 */
package com.upay.busi.pay.service.impl;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.pay.service.dto.ChckPayResultAndHandleDto;

/**检查订单状态做相应处理
 * @author zhanggr
 *
 */
@Deprecated
public class ChckPayResultAndHandleService extends AbstractDipperHandler<ChckPayResultAndHandleDto> {

    @Override
    public ChckPayResultAndHandleDto execute(ChckPayResultAndHandleDto chckPayResultAndHandleDto, Message msg) throws Exception {
        
        
        
        return chckPayResultAndHandleDto;
    }
}
