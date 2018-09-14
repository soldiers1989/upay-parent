package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.net.URLDecoder;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.PayOrderRegisterOfAcpDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 订单详情登记银联使用
 * 
 * @author hry
 * 
 */

public class PayOrderRegisterOfAcpService extends AbstractDipperHandler<PayOrderRegisterOfAcpDto> {

    private static final Logger LOG = LoggerFactory.getLogger(PayStateQryService.class);

    @Resource
    private IDaoService daoService;
    @Resource
    private ISequenceService sequenceService;


    @Override
    public PayOrderRegisterOfAcpDto execute(PayOrderRegisterOfAcpDto payOrderRegisterDto, Message arg1)
            throws Exception {
        String transCode = payOrderRegisterDto.getTransCode();

        String outerOrderNo = payOrderRegisterDto.getOuterOrderNo();
        if (StringUtils.isNotBlank(outerOrderNo)) {
            PayOrderListPo pol = new PayOrderListPo();
            pol.setOuterOrderNo(outerOrderNo);
            pol = daoService.selectOne(pol);
            if (pol != null) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0106);
            }
        }

        if (StringUtils.isBlank(payOrderRegisterDto.getTransType())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易类型");
        }
        // 渠道代码非空判定
        if (StringUtils.isBlank(payOrderRegisterDto.getChnlId())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "渠道代码");
        }
        // 交易代码非空判定
        if (StringUtils.isBlank(transCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易代码");

        }

        // 用户IP非空判定
        if (StringUtils.isBlank(payOrderRegisterDto.getSpbillCreateIp())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户IP");
        }
        // 币种非空判定
        if (StringUtils.isBlank(payOrderRegisterDto.getCurr())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "币种");
        }
        // 交易金额非空判定
        if (null == payOrderRegisterDto.getTransAmt()) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易金额");
        }

        // 交易金额非负判定
        if (payOrderRegisterDto.getTransAmt().signum() == -1) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0002, "交易金额");
        }
        BigDecimal transAmt = new BigDecimal(payOrderRegisterDto.getTransAmt().doubleValue() + "");
        if (transAmt.scale() > 2) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0041);
        }
        // 金额保留2位小数转换
        payOrderRegisterDto.setTransAmt(payOrderRegisterDto.getTransAmt().setScale(2,
            BigDecimal.ROUND_HALF_UP));
        // 判断支付服务类型
        if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(payOrderRegisterDto.getTransType())) {
            if (StringUtils.isNotBlank(payOrderRegisterDto.getPayServicType())) {
                if (!DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE.equals(payOrderRegisterDto
                    .getPayServicType())
                        && !DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_FORTHWITH.equals(payOrderRegisterDto
                            .getPayServicType())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "支付服务类型");
                }
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付服务类型");
            }
        }

        // Date date = new Date();// 获取当前时间
        // 订单号规则：模块（4位）_日期（8位）_序号（10位）
        String orderNo = sequenceService.generateOrderNo("UPAY");
//        String orderNo = sequenceService.generateOrderNo("UPAY");
        // 登记订单信息
        PayOrderListPo payOrderListPo = new PayOrderListPo();
        // 根据商户号查询商户名称
        if (StringUtils.isNotBlank(payOrderRegisterDto.getMerNo())) {
            MerBaseInfoPo mer = new MerBaseInfoPo();
            mer.setMerNo(payOrderRegisterDto.getMerNo());
            mer = daoService.selectOne(mer);
            if (mer == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0018, payOrderRegisterDto.getMerNo());
            }
            payOrderListPo.setOrderName(mer.getMerName());
        }

        payOrderListPo.setChnlId(payOrderRegisterDto.getChnlId());// 登记渠道代码
        payOrderListPo.setOrderNo(orderNo);// 支付订单号
        payOrderListPo.setOrderDes(payOrderRegisterDto.getTransComments());
        payOrderListPo.setPayServicType(payOrderRegisterDto.getPayServicType());// 支付服务类型
        payOrderListPo.setMerSeq(payOrderRegisterDto.getMerSeq());// 商户流水号
        payOrderListPo.setMerNo(payOrderRegisterDto.getMerNo());// 商户代码
        payOrderListPo.setSecMerNo(payOrderRegisterDto.getSecMerNo());// 二级商户代码
        payOrderListPo.setOuterOrderNo(payOrderRegisterDto.getOuterOrderNo());// 商户订单号
        payOrderListPo.setOrderType(payOrderRegisterDto.getOrderType());// 订单类型
        payOrderListPo.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
        payOrderListPo.setStlFlag(DataBaseConstants_BATCH.T_STL_FLAG_NO);
        payOrderListPo.setTransType(payOrderRegisterDto.getTransType());
        boolean isHavStartDate = false;
        boolean isHavEndDate = false;
        if (StringUtils.isNotBlank(payOrderRegisterDto.getOuterOrderStartDate())) {
            isHavStartDate = true;
            payOrderListPo.setMerDate(DateUtil.parse(payOrderRegisterDto.getOuterOrderStartDate(),
                "yyyyMMddHHmmss"));// 商户日期
            payOrderListPo.setOuterOrderStartDate(DateUtil.parse(
                payOrderRegisterDto.getOuterOrderStartDate(), "yyyyMMddHHmmss"));// 商户订单生成日期
        }
        if (StringUtils.isNotBlank(payOrderRegisterDto.getOuterOrderEndDate())) {
            isHavEndDate = true;
            payOrderListPo.setOuterOrderEndDate(DateUtil.parse(payOrderRegisterDto.getOuterOrderEndDate(),
                "yyyyMMddHHmmss"));// 商户订单失效日期
        }
        payOrderListPo.setPayType(payOrderRegisterDto.getPayType());// 支付方式
        if (StringUtils.isBlank(payOrderListPo.getOrderName())) {
            payOrderListPo.setOrderName(payOrderRegisterDto.getOrderName());// 支付订单名称
        }
        payOrderListPo.setTransCode(transCode);// 交易代码
        payOrderListPo.setUserId(payOrderRegisterDto.getUserId());// 用户ID
        payOrderListPo.setOrderDate(SysInfoContext.getSysDate());// 订单创建日期
        payOrderListPo.setOrderTime(SysInfoContext.getSysTime());// 订单创建时间
        Integer orderLmtTime = 0;
        if (isHavStartDate && isHavEndDate) {
            Long orderLmtTimeL =
                    (payOrderListPo.getOuterOrderEndDate().getTime() - payOrderListPo
                        .getOuterOrderStartDate().getTime()) / 1000L / 60L;
            orderLmtTime = orderLmtTimeL.intValue();
        } else {
            orderLmtTime =
                    Integer.valueOf((String) DipperParm.getParmByKey(CommonConstants_GNR.ORDER_LMT_TIME));
        }
        payOrderListPo.setOrderLmtTime(orderLmtTime);
        payOrderListPo.setCurr(payOrderRegisterDto.getCurr());// 币种
        payOrderListPo.setTransAmt(payOrderRegisterDto.getTransAmt());// 交易金额
        payOrderListPo.setOtherTranAmt(payOrderRegisterDto.getOtherTranAmt());// 其他费用
        payOrderListPo.setProductAmt(payOrderRegisterDto.getProductAmt());// 商品费用
        payOrderListPo.setMerFeeAmt(payOrderRegisterDto.getMerFeeAmt());// 商户手续费
        payOrderListPo.setFeeAmt(payOrderRegisterDto.getFeeAmt());// 客户手续费
        if (payOrderRegisterDto.getOriDate() != null) {
            payOrderListPo.setOriDate(DateUtil.parse(payOrderRegisterDto.getOriDate(), "yyyyMMddHHmmss"));// 原交易日期
        }
        payOrderListPo.setOriOrderNo(payOrderRegisterDto.getOriOrderNo());// 原支付订单号
        // payOrderListPo.setEjectAmt(payOrderRegisterDto.getEjectAmt());//
        payOrderListPo.setEjectAmt(payOrderRegisterDto.getEjectAmt()); // 累计退货额

        if (DataBaseConstans_ACC.TRANS_CODE_RECHARGE.equals(transCode)
                || DataBaseConstans_ACC.TRANS_CODE_TRANSFER.equals(transCode)
                || DataBaseConstans_ACC.TRANS_CODE_WITHDRAW.equals(transCode)) {
            payOrderListPo.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING);// 订单状态
                                                                                               // 2-支付中
        } else {
            payOrderListPo.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N);// 订单状态
                                                                                             // //
                                                                                             // 1-未支付
        }

        payOrderListPo.setSpbillCreateIp(payOrderRegisterDto.getSpbillCreateIp());// 用户IP
        payOrderListPo.setNotifyUrl(URLDecoder.decode(payOrderRegisterDto.getReturnNotifyUrl() == null ? ""
                : payOrderRegisterDto.getReturnNotifyUrl(), "UTF-8"));// 异步通知路径
        payOrderListPo.setReturnUrl(URLDecoder.decode(payOrderRegisterDto.getReturnUrl() == null ? ""
                : payOrderRegisterDto.getReturnUrl(), "UTF-8"));// 同步通知路径
        payOrderListPo.setLastUpdateTime(payOrderRegisterDto.getSysTime());// 最后变更时间
        payOrderListPo.setTransComments(payOrderRegisterDto.getTransComments());
        payOrderListPo.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
        payOrderListPo.setPromoterDeptNo(payOrderRegisterDto.getPromoterDeptNo());
        daoService.insert(payOrderListPo);
        LOG.info("订单预生成执行完毕");
        payOrderRegisterDto.setOrderNo(orderNo);
        payOrderRegisterDto.setTransAmt(payOrderListPo.getTransAmt());

        return payOrderRegisterDto;
    }
}
