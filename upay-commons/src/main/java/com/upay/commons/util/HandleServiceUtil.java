package com.upay.commons.util;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants.ResponseCode;
import com.pactera.dipper.core.utils.Constants.Transaction;
import com.pactera.dipper.core.utils.PropertyCopyUtil;
import com.pactera.dipper.transaction.TransactionDefinition;
import com.upay.commons.dto.BaseDto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


/**
 * 
 * 调用原子服务\接口工具类
 * 
 * @author: wh_liyw
 * @CreateDate:2015年9月8日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2015年9月8日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public class HandleServiceUtil {

    /**
     * 调用原子服务工具类
     * 
     * @param clz
     * @param dto
     */
    @SuppressWarnings({ "rawtypes" })
    public static void handleService(AbstractDipperHandler clz, BaseDto dto) throws Exception {
        Message message =
                MessageFactory.create(
                    IdGenerateFactory.generateId(),
                    MessageFactory.createSimpleMessage(),
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        PropertyCopyUtil.copyBean2MapNoClass(dto)),
                    FaultFactory.create(ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        clz.handle(message);
    }


    /**
     * 调用原子服务\接口工具类
     * 
     * @param bodys
     * @return
     */
    public static Message getMessage(Map<String, Object> bodys) {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put(Transaction.LAZY, new TransactionDefinition("", "", 0,
            TransactionDefinition.PROPAGATION_NOT_SUPPORTED));
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(),
                    MessageFactory.createSimpleMessage(headers, bodys),
                    FaultFactory.create(ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        return message;
    }

}
