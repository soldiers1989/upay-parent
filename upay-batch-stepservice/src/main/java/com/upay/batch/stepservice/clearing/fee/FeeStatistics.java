package com.upay.batch.stepservice.clearing.fee;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.utils.common.UUIDGenerator;
import com.upay.batch.stepservice.schedule.BatchCommon;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkMerListPo;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import com.upay.dao.po.fee.FeeStatisticsPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liudan
 * 统计 银联 微信 支付 中金 网联  手续费支出 和支出
 */
public class FeeStatistics extends AbstractStepExecutor<Map<String, Object>, Map<String, Object>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeeStatistics.class);

    private BatchCommon batchCommon;

    public BatchCommon getBatchCommon() {
        return batchCommon;
    }

    public void setBatchCommon(BatchCommon batchCommon) {
        this.batchCommon = batchCommon;
    }

    //代收商户号
    private String acpReceiveMerId;
    //代付商户号
    private String acpPayMerId;
    //无跳转商户号
    private String acpMerId;

    public String getAcpReceiveMerId() {
        return acpReceiveMerId;
    }

    public void setAcpReceiveMerId(String acpReceiveMerId) {
        this.acpReceiveMerId = acpReceiveMerId;
    }

    public String getAcpPayMerId() {
        return acpPayMerId;
    }

    public void setAcpPayMerId(String acpPayMerId) {
        this.acpPayMerId = acpPayMerId;
    }

    public String getAcpMerId() {
        return acpMerId;
    }

    public void setAcpMerId(String acpMerId) {
        this.acpMerId = acpMerId;
    }

    //手续费类型
    private final String FEE_TYPE_IN = "0";//手续费收入
    private final String FEE_TYPE_OUT = "1";//手续费支出
    //业务类型
    private final String BIZ_TYPE_ACP = "acp";//银联二维码
    private final String BIZ_TYPE_ACPMER = "acpmer";//无跳转支付
    private final String BIZ_TYPE_ACPPAY = "acppay";//代付
    private final String BIZ_TYPE_ACPRECEIVE = "acpreceive";//代收
    private final String BIZ_TYPE_WECHAT = "wechat";//手续费支出  微信
    private final String BIZ_TYPE_ALIPAY = "alipay";//手续费支出  支付宝


    private final String IS_REG_YES = "1";//已统计手续费
    private final String IS_REG_NO = "0";//未统计手续费


    //机构代码
    private String issCode;

    public String getIssCode() {
        return issCode;
    }

    public void setIssCode(String issCode) {
        this.issCode = issCode;
    }

    //统计手续费  支出
    public void sumOutCome(BatchParams batchParams, Map<String, Object> data) {

        //1.  统计 各个 渠道  昨天的手续费支出总数 并入库
        FeeStatisticsPo feeStatisticsPo = new FeeStatisticsPo();
        feeStatisticsPo.setStatisticsTime(batchParams.getTranDate());
        feeStatisticsPo.setTxnTime(DateUtil.getPreDate(batchParams.getTranDate()));
        String orgCode = data.get("ORG_CODE") + "";
        feeStatisticsPo.setRouteCode(orgCode);
        String merId = data.get("MER_ID") + "";
        if (acpMerId.equals(merId)) {
            feeStatisticsPo.setBizType(this.BIZ_TYPE_ACPMER);
            //代付
        } else if (acpPayMerId.equals(merId)) {
            feeStatisticsPo.setBizType(this.BIZ_TYPE_ACPPAY);
            //代收
        } else if (acpReceiveMerId.equals(merId)) {
            feeStatisticsPo.setBizType(this.BIZ_TYPE_ACPRECEIVE);
            //二维码
        } else {
            //银联二维码
            if (DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(orgCode)) {
                feeStatisticsPo.setBizType(this.BIZ_TYPE_ACP);
            }
            //支付宝
            if (DataBaseConstants_PAY.ROUTE_CODE_ALIPAY.equals(orgCode)) {
                feeStatisticsPo.setBizType(this.BIZ_TYPE_ALIPAY);
            }
            //微信
            if (DataBaseConstants_PAY.ROUTE_CODE_WECHAT.equals(orgCode)) {
                feeStatisticsPo.setBizType(this.BIZ_TYPE_WECHAT);
            }
        }
        FeeStatisticsPo feeStatisticsPo1 = daoService.selectOne(FeeStatisticsPo.class.getName() + ".selectByTime", feeStatisticsPo);
        if (feeStatisticsPo1 == null) {
            //设置 付款方
            List<PayRouteInfoPo> payRouteInfoPos = (List<PayRouteInfoPo>) batchParams.getParameter().get("payRouteInfoPos");
            feeStatisticsPo.setFeeType(this.FEE_TYPE_OUT);
            feeStatisticsPo.setIssCode(this.issCode);
            feeStatisticsPo.setPayerAcctName("手续费支出账户");
            feeStatisticsPo.setPayerAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
            feeStatisticsPo.setPayeeAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
            feeStatisticsPo.setFeeAmt(new BigDecimal(data.get("FEE_AMT") + ""));
            for (PayRouteInfoPo payRouteInfoPo : payRouteInfoPos) {
                if (payRouteInfoPo != null) {
                    if (payRouteInfoPo.getRouteCode().equals(orgCode)) {
                        feeStatisticsPo.setPayerAcctNo(payRouteInfoPo.getFeeExpenseAcctNo());
                        if (acpMerId.equals(merId) ||
                                acpPayMerId.equals(merId) ||
                                acpReceiveMerId.equals(merId)) {
                            //银联代收  代付 无跳转 往来户 账户  收款方
                            feeStatisticsPo.setPayeeAcctName("银联代收代付无跳转往来户账户");
                            feeStatisticsPo.setPayeeAcctNo(payRouteInfoPo.getUnionAcctNo());
                        } else {
                            //银联二维码 往来账户   收款方
                            feeStatisticsPo.setPayeeAcctName("银联二维码支付宝微信中金往来户账户");
                            feeStatisticsPo.setPayeeAcctNo(payRouteInfoPo.getTransAcctNo());
                        }

                    }
                }
            }
            daoService.insert(feeStatisticsPo);
            //3.进行转账 更新订单号 订单状态
         //   transferAndUpdateOrder(feeStatisticsPo);
        }

    }


    //统计手续费  收入
    public void sumInCome(BatchParams batchParams) {


        logger.info("开始记录手续费  支出");
        //1.查询数据  根据时间 统计
        //当天 统计  上一天交易的  手续费 收入和支出
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("txnDate", DateUtil.getPreDate(batchParams.getTranDate()));
        List<ChkMerListPo> ChkMerListPos = daoService.selectList(ChkMerListPo.class.getName() + ".getListByWhere", parm);
        int size = ChkMerListPos.size();
        if (ChkMerListPos != null && size > 0) {
            //查询清算账户
            PayRouteInfoPo payRouteInfoPoHost = new PayRouteInfoPo();
            payRouteInfoPoHost.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
            payRouteInfoPoHost = daoService.selectOne(payRouteInfoPoHost);
            for (int i = 0; i < size; i++) {
                ChkMerListPo data = ChkMerListPos.get(i);
                PayOrderListPo payOrderListPo = new PayOrderListPo();
                payOrderListPo.setOrderNo(data.getOrderNo());
                payOrderListPo = daoService.selectOne(payOrderListPo);
                //查询通道 往来户账户 手续费支出账户
                PayRouteInfoPo payRouteInfo = new PayRouteInfoPo();
                payRouteInfo.setRouteCode(payOrderListPo.getRouteCode());
                payRouteInfo = daoService.selectOne(payRouteInfo);
                //2根据第三方平台流水号 查询 是否已经记录手续费
                FeeStatisticsPo feeStatisticsPo = new FeeStatisticsPo();
                feeStatisticsPo.setStatisticsTime(batchParams.getTranDate());
                feeStatisticsPo.setTxnTime(DateUtil.getPreDate(batchParams.getTranDate()));
                feeStatisticsPo.setRouteCode(payOrderListPo.getRouteCode());
                FeeStatisticsPo feeStatisticsPo1 = daoService.selectOne(feeStatisticsPo);
                //第一次记录手续费
                BigDecimal feeAmt = data.getMerFeeAmt().add(data.getSecMerFeeAmt());
                if (feeStatisticsPo1 == null) {
                    feeStatisticsPo.setFeeAmt(feeAmt);
                    feeStatisticsPo.setFeeType(this.FEE_TYPE_IN);
                    feeStatisticsPo.setIssCode(this.issCode);
                    //TODO:ChkThirdDetail 需要添加 商户字段  以及 商户对账都需要存入商户字段入库  用于区分银联的各个渠道的手续费
                    // feeStatisticsPo.setMerId(data.getMerId());
                    feeStatisticsPo.setPayerAcctName("待清算账户");
                    feeStatisticsPo.setPayerAcctNo(payRouteInfoPoHost.getClrAcctNo());
                    feeStatisticsPo.setPayerAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
                    feeStatisticsPo.setPayeeAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
                    feeStatisticsPo.setPayeeAcctName("二维码支付宝微信中金手续费收入账户");
                    feeStatisticsPo.setPayeeAcctNo(payRouteInfo.getUnionAcctNo());
                    daoService.insert(feeStatisticsPo);
                }
                // 如果存在记录  就  累计手续费
                else {
                    //累计手续费
                    feeStatisticsPo1.setFeeAmt(feeStatisticsPo1.getFeeAmt().add(feeAmt));
                    //更新数据库
                    daoService.update(feeStatisticsPo1, feeStatisticsPo);
                }

                logger.info("当前执行到第" + i + "记录" + "size:" + size + "" + (size == i));
                //如果到最后一条  就 进行记账
                //只有一条数据的情况
                if (size - 1 == i) {
                    logger.info("开始记账");
                    //记账   更新 费率表的订单号 和订单状态
                    transferAndUpdateOrder(feeStatisticsPo1, feeStatisticsPo);
                    logger.info("记账结束");
                }
            }
        }


    }


    //记账   更新 费率表的订单号 和订单状态
    public void transferAndUpdateOrder(FeeStatisticsPo feeStatisticsPo1, FeeStatisticsPo feeStatisticsPo) {
        Map<String, Object> singleColltionMap = new HashMap<String, Object>();
        Message msssage = null;
        //开始记账
        //  从手续费支出账户  转到   往来户账户 平账
        singleColltionMap.put("transCode", DataBaseConstans_ACC.TRANS_CODE_COLLECTION);
        singleColltionMap.put("chnlId", "07");
        singleColltionMap.put("outerOrderNo", UUIDGenerator.randomUUID());
        singleColltionMap.put("collectionType", "1");//代收类型 1-内部转账  2-非内部转账
        singleColltionMap.put("merDate", DateUtil.format(new Date(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1));       // 商户时间
        // 付款账号
        if (feeStatisticsPo1 == null) {
            singleColltionMap.put("acctNo", feeStatisticsPo.getPayerAcctNo());
            singleColltionMap.put("payeeAccountNo", feeStatisticsPo.getPayeeAcctNo());
            singleColltionMap.put("transAmt", feeStatisticsPo.getFeeAmt());// 交易金额  即手续费金额
        } else {
            singleColltionMap.put("acctNo", feeStatisticsPo1.getPayerAcctNo());
            singleColltionMap.put("payeeAccountNo", feeStatisticsPo1.getPayeeAcctNo());
            singleColltionMap.put("transAmt", feeStatisticsPo1.getFeeAmt());// 交易金额  即手续费金额
        }

        singleColltionMap.put("payeeAccountType", DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
        singleColltionMap.put("singleCollectionFlag", "02");//01本平台上起     02其他第三方商户发起
        singleColltionMap.put("acctName", "往来户账户");
        singleColltionMap.put("accountType", DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
        singleColltionMap.put("spbillCreateIp", "127.0.0.1");// ip
        logger.info("singleColltionMap {}", singleColltionMap);
        msssage = batchCommon.postPay0019(singleColltionMap);
        //获取订单状态 和订单号
        if (msssage != null) {
            Map<String, Object> bodys = (Map<String, Object>) msssage.getTarget().getBodys();
            if (bodys != null && bodys.size() > 0) {
                if (bodys.containsKey("orderNo")) {
                    String orderNo = bodys.get("orderNo") + "";
                    //现有订单才有  查询订单状态的必要
                    if (StringUtils.isNotBlank(orderNo)) {
                        if (feeStatisticsPo1 == null) {
                            feeStatisticsPo.setOrderNo(orderNo.toString());
                        } else {
                            feeStatisticsPo1.setOrderNo(orderNo.toString());
                        }

                        //正常流程  返回orderStat
                        String orderStat = bodys.get("orderStat") + "";
                        if (bodys.containsKey("orderStat")) {
                            if (StringUtils.isNotBlank(orderStat)) {
                                if (feeStatisticsPo1 == null) {
                                    feeStatisticsPo.setTransStat(orderStat.toString());
                                } else {
                                    feeStatisticsPo1.setTransStat(orderStat.toString());
                                }
                            }
                        }
                        //没有返回orderStat 订单状态 中途出现异常   或者存在 订单状态但是 为空去查询数据库 确认订单状态                                                                                       //去查询数据库 确认订单状态
                        if (!bodys.containsKey("orderStat") ||
                                StringUtils.isBlank(orderStat)) {
                            PayOrderListPo orderListPo = new PayOrderListPo();
                            orderListPo.setOrderNo(orderNo);
                            orderListPo = daoService.selectOne(orderListPo);
                            if (feeStatisticsPo1 == null) {
                                feeStatisticsPo.setTransStat(orderListPo.getOrderStat());
                            } else {
                                feeStatisticsPo1.setTransStat(orderListPo.getOrderStat());
                            }
                        }
                        //每一条对账记录  对应一条手续费  进行记录
                        //只有一条记录的 时候
                        if (feeStatisticsPo1 == null) {
                            daoService.update(feeStatisticsPo);
                        } else {//多条记录的时候
                            daoService.update(feeStatisticsPo1, feeStatisticsPo);
                        }

                    }
                }

            }


        }
    }


    @Override
    public List<Map<String, Object>> getDataList(BatchParams batchParams, int offset, int pageSize, Map<String, Object> object) throws BatchException {

        //<editor-fold desc="手续费收入">
        //分类 统计手续费
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("thirdDate", DateUtil.getPreDate(batchParams.getTranDate()));
        paramMap.put("isRegisterFeeamt", this.IS_REG_NO); //0和NULL 都未统计过
        List<Map<String, Object>> routeFeeList = daoService.selectList(ChkThirdDetailPo.class.getName() + ".sumByOrgCode", paramMap);

        //获取资金通道
        PayRouteInfoPo payRouteInfo = new PayRouteInfoPo();
        List<PayRouteInfoPo> payRouteInfoPos = daoService.selectList(payRouteInfo);
        batchParams.getParameter().put("payRouteInfoPos", payRouteInfoPos);

        //获取
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("thirdDate", DateUtil.getPreDate(batchParams.getTranDate()));
        parm.put("isRegisterFeeamt", this.IS_REG_NO); //0和NULL 都未统计过
        List<ChkThirdDetailPo> chkThirdDetailPos = daoService.selectList(ChkThirdDetailPo.class.getName() + ".selectByThirdDate", parm);
        batchParams.getParameter().put("chkThirdDetailPos", chkThirdDetailPos);

        //更新已经统计
        for (ChkThirdDetailPo temp : chkThirdDetailPos) {
            temp.setIsRegisterFeeamt(this.IS_REG_YES);
            daoService.update(temp);
        }
        //</editor-fold>
        return routeFeeList;
    }


    @Override
    public void execute(BatchParams batchParams, int index, Map<String, Object> data, Map<String, Object> object) throws BatchException {
        //判断是否是指定日期执行
        logger.debug("指定日期:" + batchParams.getParameter().containsKey("tranDate"));
        if (batchParams.getParameter().containsKey("tranDate")) {
            Date tranDate = (Date) batchParams.getParameter().get("tranDate");
            Date preDate = (Date) batchParams.getParameter().get("preDate");
            batchParams.setTranDate(tranDate);
            batchParams.setPreDate(preDate);
        }
        logger.debug("执行的日期是：" + DateUtil.format(batchParams.getTranDate(), "yyyy-MM-dd"));


        //统计手续费 支出
        sumOutCome(batchParams,data);


        //统计手续费收入



    }


    //记账   更新 费率表的订单号 和订单状态
    public void transferAndUpdateOrder(FeeStatisticsPo feeStatisticsPo) {
        Map<String, Object> singleColltionMap = new HashMap<String, Object>();
        Message msssage = null;
        //开始记账
        //  从手续费支出账户  转到   往来户账户 平账
        singleColltionMap.put("transCode", DataBaseConstans_ACC.TRANS_CODE_COLLECTION);
        singleColltionMap.put("chnlId", "07");
        singleColltionMap.put("outerOrderNo", UUIDGenerator.randomUUID());
        singleColltionMap.put("collectionType", "1");//代收类型 1-内部转账  2-非内部转账
        singleColltionMap.put("merDate", DateUtil.format(new Date(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1));       // 商户时间
        // 付款账号
        if (feeStatisticsPo != null) {
            singleColltionMap.put("acctNo", feeStatisticsPo.getPayerAcctNo());
            singleColltionMap.put("payeeAccountNo", feeStatisticsPo.getPayeeAcctNo());
            singleColltionMap.put("transAmt", feeStatisticsPo.getFeeAmt());// 交易金额  即手续费金额
        }

        singleColltionMap.put("payeeAccountType", DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
        singleColltionMap.put("singleCollectionFlag", "02");//01本平台上起     02其他第三方商户发起
        singleColltionMap.put("acctName", "往来户账户");
        singleColltionMap.put("accountType", DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
        singleColltionMap.put("spbillCreateIp", "127.0.0.1");// ip
        logger.info("singleColltionMap {}", singleColltionMap);
        msssage = batchCommon.postPay0019(singleColltionMap);
        //获取订单状态 和订单号
        if (msssage != null) {
            Map<String, Object> bodys = (Map<String, Object>) msssage.getTarget().getBodys();
            if (bodys != null && bodys.size() > 0) {
                if (bodys.containsKey("orderNo")) {
                    String orderNo = bodys.get("orderNo") + "";
                    //现有订单才有  查询订单状态的必要
                    if (StringUtils.isNotBlank(orderNo)) {
                        if (feeStatisticsPo != null) {
                            feeStatisticsPo.setOrderNo(orderNo.toString());
                        }
                        //正常流程  返回orderStat
                        String orderStat = bodys.get("orderStat") + "";
                        if (bodys.containsKey("orderStat")) {
                            if (StringUtils.isNotBlank(orderStat)) {
                                if (feeStatisticsPo != null) {
                                    feeStatisticsPo.setTransStat(orderStat.toString());
                                }
                            }
                        }
                        //没有返回orderStat 订单状态 中途出现异常   或者存在 订单状态但是 为空去查询数据库 确认订单状态                                                                                       //去查询数据库 确认订单状态
                        if (!bodys.containsKey("orderStat") ||
                                StringUtils.isBlank(orderStat)) {
                            PayOrderListPo orderListPo = new PayOrderListPo();
                            orderListPo.setOrderNo(orderNo);
                            orderListPo = daoService.selectOne(orderListPo);
                            if (feeStatisticsPo != null) {
                                feeStatisticsPo.setTransStat(orderListPo.getOrderStat());
                            }
                        }
                        //每一条对账记录  对应一条手续费  进行记录
                        //只有一条记录的 时候
                        if (feeStatisticsPo != null) {
                            daoService.update(feeStatisticsPo);
                        }

                    }
                }

            }

        }
    }


}