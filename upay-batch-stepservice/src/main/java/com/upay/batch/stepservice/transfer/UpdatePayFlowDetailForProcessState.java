package com.upay.batch.stepservice.transfer;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.dao.po.pay.PayFlowDetailPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * 批量转账 - 第五步 更新支付结果到回盘文件中。
 *
 * @author zhangjianfeng
 * @since 2017/02/20 01:59
 */
public class UpdatePayFlowDetailForProcessState extends AbstractStepExecutor<Object, Object> {

    /**
     * 日志记录。
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(UpdatePayFlowDetailForProcessState.class);



     /* 更新处理状态
     *
     * @param batchParams
     * @param index
     * @param data
     * @param object
     * @throws BatchException
     */
    @Override
    public void execute(BatchParams batchParams, int index,
                        Object data, Object object) throws BatchException {
        PayFlowDetailPo wherePayFlowDetailPo=new PayFlowDetailPo();
        wherePayFlowDetailPo.setProcess(DataBaseConstants_PAY.PAY_PROCESS_N);
        wherePayFlowDetailPo.setTransStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        wherePayFlowDetailPo.setMerTransDate(calendar.getTime());
       //todo:
        PayFlowDetailPo setPayFlowDetailPo=new PayFlowDetailPo();
        setPayFlowDetailPo.setProcess(DataBaseConstants_PAY.PAY_PROCESS_Y);
        daoService.update(setPayFlowDetailPo,wherePayFlowDetailPo);
    }
}
