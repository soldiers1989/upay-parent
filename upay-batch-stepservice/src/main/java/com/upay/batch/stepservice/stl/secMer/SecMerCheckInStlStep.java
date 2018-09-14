package com.upay.batch.stepservice.stl.secMer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.util.DateUtil;
import com.upay.dao.PlatFormHolidayContext;
import com.upay.dao.po.chk.StlBookPo;
import com.upay.dao.po.chk.StlCountPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 二级商户清算
 * Created by dk on 2017/8/1.
 */
public class SecMerCheckInStlStep extends AbstractStepExecutor<MerAcctInfoPo, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecMerCheckInStlStep.class);


    @Override
    public List<MerAcctInfoPo> getObjectList(BatchParams batchParams) throws BatchException {
        LOGGER.info("-------------查询所有状态正常二级商户开始");
        List<MerAcctInfoPo> merBaseInfoPos = new ArrayList<MerAcctInfoPo>();
        // 节假日不参于清算
        if (!PlatFormHolidayContext.isWorkDay(batchParams.getTranDate(), "0")) {
            LOGGER.info("-------------非工作日，商户不参与清算结束");
            return merBaseInfoPos;
        } else {
        	//检查当日是否进行一清
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("stat", DateBaseConstants_MER.MER_STAT_NORMAL);
            merBaseInfoPos = daoService.selectList(MerAcctInfoPo.class.getName().concat(".getSecondMer"),map);
            LOGGER.info("-------------查询商户结束merBaseInfo.size[{}]", merBaseInfoPos.size());
            return merBaseInfoPos;
        }

    }


    @Override
    public void execute(BatchParams batchParams, int index, Object data, MerAcctInfoPo object)
            throws BatchException {
        LOGGER.info("-------------商户二级清算登记清算记录开始"+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        Date isEntrustDate = object.getIsEntrustDate();
        if(isEntrustDate==null){
        	throw new BatchException("二级商户【"+object.getMerNo()+"】委托二清之后，委托清算开始时间不能为空!请检查。");
        }
        int betweenDays = DateUtil.betweenDays(isEntrustDate, batchParams.getTranDate());
        if(betweenDays==0){
        	//清算日期如果是今天开始，那么则查询订单的日期是昨天，
        	isEntrustDate=DateUtil.add(isEntrustDate, Calendar.DAY_OF_MONTH, -1);
        }else if(betweenDays>0){
        	//清算日期如果是今天之后，那么则查询订单的开始日期为空，
        	LOGGER.info("二级商户【"+object.getMerNo()+"】委托二清从"+DateUtil.format(isEntrustDate, "yyyy-MM-dd")+"开始");
        	return;
        }
        
        StlBookPo stlBookPo = new StlBookPo();
        stlBookPo.setMerNo(object.getParentMerNo());
        stlBookPo.setSecMerNo(object.getMerNo());
        stlBookPo.setStlDate(batchParams.getTranDate());
        stlBookPo.setMerFlag(DataBaseConstants_BATCH.MER_FLAG_SEC_BUSINESS);
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
        stlBookPo.setMerFlag(DataBaseConstants_BATCH.MER_FLAG_SEC_BUSINESS);// 二级商户
        stlBookPo.setMerNo(object.getParentMerNo());
        stlBookPo.setSecMerNo(object.getMerNo());
        stlBookPo.setStat(DataBaseConstants_BATCH.STL_STAT_CHECK_IN);// 状态：0：登记
        stlBookPo.setRemark("二级商户[" + object.getMerName() + "]代收清算");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
        Map<String, Object> sqlStlParams = new HashMap<String, Object>();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
        sqlStlParams.put("sysDate", batchParams.getTranDate());
        sqlStlParams.put("isEntrustDate", DateUtil.format(isEntrustDate, DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));//委托二清开始的时间
        sqlStlParams.put("merNo", object.getParentMerNo());
        sqlStlParams.put("secMerNo", object.getMerNo());
        sqlStlParams.put("orderStat1", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
        sqlStlParams.put("orderStat2", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP);
        sqlStlParams.put("orderStat3", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
        sqlStlParams.put("orderStat4", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
//        sqlStlParams.put("chkFlag", DataBaseConstants_PAY.T_CHK_STAT_SUC);
        sqlStlParams.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_PAY);
        sqlStlParams.put("oriClearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
        LOGGER.info("-------------二级商户清算应收查询开始："+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        StlCountPo stlCountPo =
                daoService.selectOne(PayOrderListPo.class.getName() + ".stlSecMerRevOrder", sqlStlParams);
        LOGGER.info("-------------二级商户清算应收查询结束："+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        if (null != stlCountPo) {
            stlBookPo.setRevAmt(stlCountPo.getRevAmt());// 商户应收本金
            LOGGER.info("-------------查询二级商户应收本金金额[{}]元", stlCountPo.getRevAmt());
            stlBookPo.setPayFee(stlCountPo.getPayFee());// 商户应付手续费
            LOGGER.info("-------------查询二级商户代收应付手续费金额[{}]元", stlCountPo.getPayFee());
        }
        Map<String, Object> sqlRefParams = new HashMap<String, Object>();
        sqlRefParams.put("payOrderStat1", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
        sqlRefParams.put("payOrderStat2", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
        sqlRefParams.put("refOrderStat", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC);
        sqlRefParams.put("refClearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
        sqlRefParams.put("refTransType", CommonConstants_GNR.SYS_TRANS_TYPE_REF);
        sqlRefParams.put("payTransType", CommonConstants_GNR.SYS_TRANS_TYPE_PAY);
        sqlRefParams.put("sysDate", batchParams.getTranDate());
        sqlRefParams.put("isEntrustDate", DateUtil.format(isEntrustDate, DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));//委托二清开始的时间
        sqlRefParams.put("merNo", object.getParentMerNo());
        sqlRefParams.put("secMerNo", object.getMerNo());
        
        LOGGER.info("-------------二级商户清算应付查询开始："+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        StlCountPo refCountPo =
                daoService.selectOne(PayOrderListPo.class.getName() + ".stlSecMerPayOrder", sqlRefParams);
        LOGGER.info("-------------二级商户清算应付查询结束："+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        
        if(refCountPo!=null){
            stlBookPo.setPayAmt(refCountPo.getPayAmt());// 商户应付本金
            LOGGER.info("-------------查询二级商户应付本金金额[{}]元", refCountPo.getPayAmt());
        }
        
        
        
        
        BigDecimal zero=BigDecimal.ZERO;
        BigDecimal secRevAmt=stlBookPo.getRevAmt()==null?zero:stlBookPo.getRevAmt();//二级商户应收本金
        BigDecimal secPayAmt=stlBookPo.getPayAmt()==null?zero:stlBookPo.getPayAmt();//二级商户退款应付金额
        //TODO 计算一级商户收取二级商户的手续费
        BigDecimal secMerFeeAmt=stlBookPo.getPayFee()==null?zero:stlBookPo.getPayFee();//一级商户收取二级商户的手续费
        
        stlBookPo.setBarAmt(secRevAmt.subtract(secPayAmt).subtract(secMerFeeAmt));
        daoService.insert(stlBookPo);
        LOGGER.info("-------------二级商户清算结束--登记商户号[{}]清算清算记录", object.getMerNo());
        sqlStlParams.put("clearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_TWOSUC);//二级清算完成
        sqlRefParams.put("clearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_TWOSUC);//二级清算完成
        LOGGER.info("-------------二级商户清算完成   更新订单清算状态开始："+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        int i = daoService.update(PayOrderListPo.class.getName() + ".updateStlSecMerPayOrder", sqlStlParams);//更新支付订单
        int j = daoService.update(PayOrderListPo.class.getName() + ".updateStlSecMerRefOrder", sqlRefParams);//更新退款订单
        LOGGER.info("-------------二级商户清算完成   更新订单清算状态结束："+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
        
        LOGGER.info("-------------二级商户清算结束--登记商户号[{}]清算，更新清算记录[{}]", object.getMerNo(), i+j);
        LOGGER.info("-------------二级商户清算结束");
    }
    
//    public static void main(String [] args){
//    	Date isEntrustDate=DateUtil.parse("20170904", "yyyyMMdd");
//    	Date today=DateUtil.parse("20170905", "yyyyMMdd");
//    	int betweenDays = DateUtil.betweenDays(isEntrustDate, today);
//        if(betweenDays==0){
//        	//清算日期如果是今天开始，那么则查询订单的日期是昨天，
//        	isEntrustDate=DateUtil.add(isEntrustDate, Calendar.DAY_OF_MONTH, -1);
//        }else if(betweenDays>0){
//        	//清算日期如果是今天之后，那么则查询订单的开始日期为空，
//        	isEntrustDate=null;
//        }
//    	System.out.println(isEntrustDate!=null?DateUtil.format(isEntrustDate, "yyyyMMdd"):null);
//    }
}
