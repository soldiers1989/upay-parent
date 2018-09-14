package com.upay.batch.stepservice.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.ParmsContext;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 自动确认收货
 * 
 * @author liyulong
 * 
 */
public class ConfirmReceipt extends AbstractStepExecutor<Object, PayOrderListPo> {
    @Resource
    private IDaoService daoService;


    // @Override
    // public List<Object> getObjectList(BatchParams batchParams) throws
    // BatchException {
    // // TODO Auto-generated method stub
    // return super.getObjectList(batchParams);
    // }
    @Override
    public int getTotalResult(BatchParams batchParams, Object object) throws BatchException {
        PayOrderListPo payOrderListPo = new PayOrderListPo();
        // 查询记录条数
        payOrderListPo.setPayServicType(DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE);
        payOrderListPo.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP);
        return daoService.selectList(payOrderListPo).size();
    }


    @Override
    public List<PayOrderListPo> getDataList(BatchParams batchParams, int offset, int pageSize, Object object)
            throws BatchException {
        PayOrderListPo payOrderListPo = new PayOrderListPo();
        // 查询记录
        payOrderListPo.setPayServicType(DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE);
        payOrderListPo.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP);
        List<PayOrderListPo> selectList = daoService.selectList(payOrderListPo, offset, pageSize);
        return selectList;
    }


    @Override
    public void execute(BatchParams batchParams, int index, PayOrderListPo payOrderListPo, Object object)
            throws BatchException {
        // 如果支付完成时间加上确认支付时效大于、等于系统时间，将状态更改为确认支付
        Date sysTime = SysInfoContext.getSysTime(); // 系统时间
        // Date sysTime = new Date(); // 测试用
        if (null != payOrderListPo && StringUtils.isNotBlank(payOrderListPo.getOrderNo())) {
            String orderNo = payOrderListPo.getOrderNo();
            Date payTime = payOrderListPo.getPayTime();
            if (payTime == null) {
                throw new BatchException("订单信息有误");
            }
            int confirmTime =
                    Integer.parseInt(ParmsContext.getParmByKey(CmparmConstants.CONFIRM_TIME).toString()); // 确认收货时效
            Date confirmReceiptTime = DateUtil.add(payTime, Calendar.HOUR, confirmTime);// 自动确认收货时间
            if (sysTime.compareTo(confirmReceiptTime) >= 0) {
                payOrderListPo.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP);
                PayOrderListPo payOrderListPo2 = new PayOrderListPo();
                payOrderListPo2.setOrderNo(orderNo);
                daoService.update(payOrderListPo, payOrderListPo2);
            } else {
                logger.debug("该订单还没到自动确认收货时间");
            }
            // logger.debug("--------------------------sysTime+" + sysTime);
            // logger.debug("--------------------------confirmReceiptTime+" +
            // confirmReceiptTime);
            // logger.debug("--------------------------confirmTime+" +
            // confirmTime);
        }
    }
}
