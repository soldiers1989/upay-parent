package com.upay.batch.stepservice.clearing.mer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.util.DateUtil;
import com.upay.dao.PlatFormHolidayContext;
import com.upay.dao.po.chk.StlBookPo;
import com.upay.dao.po.chk.StlCountPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * Created by dk on 2017/2/21.
 */
public class MerCheckInStlStep extends AbstractStepExecutor<MerBaseInfoPo, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MerCheckInStlStep.class);


    @Override
    public List<MerBaseInfoPo> getObjectList(BatchParams batchParams) throws BatchException {
        LOGGER.info("-------------查询所有状态正常商户开始");
        List<MerBaseInfoPo> merBaseInfoPos = new ArrayList<MerBaseInfoPo>();
        // 节假日不参于清算
        if (!PlatFormHolidayContext.isWorkDay(batchParams.getTranDate(), "0")) {
            LOGGER.info("-------------非工作日，商户不参与清算结束");
            return merBaseInfoPos;
        } else {
            Map<String,Object> map=new HashMap<String,Object>();
//            map.put("stat", DateBaseConstants_MER.MER_STAT_NORMAL);
            merBaseInfoPos = daoService.selectList(MerBaseInfoPo.class.getName().concat(".getFistMer"),map);
            LOGGER.info("-------------查询商户结束merBaseInfo.size[{}]", merBaseInfoPos.size());
            return merBaseInfoPos;
        }

    }


    @Override
    public void execute(BatchParams batchParams, int index, Object data, MerBaseInfoPo object)
            throws BatchException {
        LOGGER.info("-------------商户清算登记清算记录开始"+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
//        char[] stat = DataBaseConstants_BATCH.STL_STAT_INIT_VALUE.toCharArray();
//        stat[DataBaseConstants_BATCH.STL_STAT_REV_AMT_IDX] =
//                DataBaseConstants_BATCH.STL_STAT_CHECK_IN.toCharArray()[0];
        StlBookPo stlBookPo = new StlBookPo();
        stlBookPo.setMerNo(object.getMerNo());
        stlBookPo.setStlDate(batchParams.getTranDate());
        stlBookPo.setMerFlag(DataBaseConstants_BATCH.MER_FLAG_SPECIAL_BUSINESS);
        stlBookPo.setStlMerFlag(DataBaseConstants_BATCH.STL_MER_FLAG_PLATFORM);
        stlBookPo = daoService.selectOne(stlBookPo);
        if (null != stlBookPo) {
            //已经清算过不进行清算
            return;
        }
        stlBookPo = new StlBookPo();
        // 当天未跑过清算记录的商户才进行银联清算
        stlBookPo.setStlBatchNo(batchParams.getBatchNo());
        stlBookPo.setStlDate(batchParams.getTranDate());
        stlBookPo.setStlMerFlag(DataBaseConstants_BATCH.STL_MER_FLAG_PLATFORM);
        stlBookPo.setMerFlag(DataBaseConstants_BATCH.MER_FLAG_SPECIAL_BUSINESS);// 直销特约商户
        stlBookPo.setMerNo(object.getMerNo());
        stlBookPo.setPromoterDeptNo(object.getPromoterDeptNo());
        stlBookPo.setStat(DataBaseConstants_BATCH.STL_STAT_CHECK_IN);// 状态：0：登记
        stlBookPo.setRemark("商户[" + object.getMerName() + "]代收清算");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
        Map<String, Object> sqlStlParams = new HashMap<String, Object>();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
        sqlStlParams.put("sysDate", batchParams.getTranDate());
        sqlStlParams.put("merNo", object.getMerNo());
        sqlStlParams.put("orderStat1", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
        sqlStlParams.put("orderStat2", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP);
        sqlStlParams.put("orderStat3", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
        sqlStlParams.put("orderStat4", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
//        sqlStlParams.put("chkFlag", DataBaseConstants_PAY.T_CHK_STAT_SUC);
        sqlStlParams.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_PAY);
        sqlStlParams.put("oriClearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_READY);
        LOGGER.info("-------------商户清算应收查询开始："+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        StlCountPo stlCountPo =
                daoService.selectOne(PayOrderListPo.class.getName() + ".stlMerRevOrder", sqlStlParams);
        LOGGER.info("-------------商户清算应收查询结束："+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        if (null != stlCountPo) {
            stlBookPo.setRevAmt(stlCountPo.getRevAmt());// 商户应收本金
            LOGGER.info("-------------查询商户应收本金金额[{}]元", stlCountPo.getRevAmt());
            stlBookPo.setPayFee(stlCountPo.getPayFee());// 商户应付手续费
            LOGGER.info("-------------查询商户代收应付手续费金额[{}]元", stlCountPo.getPayFee());
        }
        Map<String, Object> sqlRefParams = new HashMap<String, Object>();
        sqlRefParams.put("payOrderStat1", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
        sqlRefParams.put("payOrderStat2", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
        sqlRefParams.put("refOrderStat", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC);
        sqlRefParams.put("refClearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_READY);
        sqlRefParams.put("refTransType", CommonConstants_GNR.SYS_TRANS_TYPE_REF);
        sqlRefParams.put("payTransType", CommonConstants_GNR.SYS_TRANS_TYPE_PAY);
        sqlRefParams.put("sysDate", batchParams.getTranDate());
        sqlRefParams.put("merNo", object.getMerNo());
        LOGGER.info("-------------商户清算应付查询开始："+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        StlCountPo refCountPo =
                daoService.selectOne(PayOrderListPo.class.getName() + ".stlMerPayOrder", sqlRefParams);
        LOGGER.info("-------------商户清算应付查询结束："+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        
        if(refCountPo!=null){
            stlBookPo.setPayAmt(refCountPo.getPayAmt());// 商户应付本金
            LOGGER.info("-------------查询商户应付本金金额[{}]元", refCountPo.getPayAmt());
        }
        BigDecimal zero=BigDecimal.ZERO;
        BigDecimal payAmt=stlBookPo.getPayAmt()==null?zero:stlBookPo.getPayAmt();
        BigDecimal revAmt=stlBookPo.getRevAmt()==null?zero:stlBookPo.getRevAmt();
        BigDecimal payFee=stlBookPo.getPayFee()==null?zero:stlBookPo.getPayFee();
        stlBookPo.setBarAmt(revAmt.subtract(payAmt).subtract(payFee));
//        stlCountPo = daoService.selectOne(PayOrderListPo.class.getName() + ".stlMerPayOrder", sqlStlParams);
//        if (null != stlCountPo) {
//            stlBookPo.setPayAmt(stlCountPo.getPayAmt());// 商户应付本金
//            LOGGER.info("-------------查询商户应收本金金额[{}]元", stlCountPo.getRevAmt());
//            stlBookPo.setPlatePayFee(stlBookPo.getPayFee().add(stlCountPo.getPayFee()));// 商户应付手续费
//            LOGGER.info("-------------查询商户代付应付手续费金额[{}]元", stlCountPo.getPayFee());
//        }
        daoService.insert(stlBookPo);
        LOGGER.info("-------------商户清算结束--登记商户号[{}]清算清算记录", object.getMerNo());
        sqlStlParams.put("clearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
        sqlRefParams.put("clearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
        LOGGER.info("-------------商户清算完成   更新订单清算状态开始："+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        int i = daoService.update(PayOrderListPo.class.getName() + ".updateStlMerPayOrder", sqlStlParams);
        int j = daoService.update(PayOrderListPo.class.getName() + ".updateStlMerRefOrder", sqlRefParams);
        LOGGER.info("-------------商户清算完成   更新订单清算状态结束："+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        
        LOGGER.info("-------------商户清算结束--登记商户号[{}]清算，更新清算记录[{}]", object.getMerNo(), i+j);
        LOGGER.info("-------------商户清算结束");
    }
}
