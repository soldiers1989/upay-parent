package com.upay.busi.pay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.NotifyAcpStatusCheckDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * Created by hry . 银联支付结果通知前检查
 */
public class NotifyAcpStatusCheckService extends
		AbstractDipperHandler<NotifyAcpStatusCheckDto> {
	private static final Logger LOG = LoggerFactory
			.getLogger(NotifyAcpStatusCheckService.class);

	@Resource
	private IDaoService daoService;

	public NotifyAcpStatusCheckDto execute(NotifyAcpStatusCheckDto dto,
			Message message) throws Exception {
		LOG.info("支付结果通知前检查开始");
		String transSubSeq = dto.getOrderNo();
	    
		if (StringUtils.isBlank(transSubSeq)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "支付流水号");
		}
		
		dto.setOutRouteSeq(dto.getOrderNo());
		
		// 根据支付流水号查询支付流水表
		PayFlowListPo flowListPo = new PayFlowListPo();
		flowListPo.setTransSubSeq(transSubSeq);
		flowListPo = daoService.selectOne(flowListPo);
		if (null != flowListPo) {
			dto.setOrderNo(flowListPo.getOrderNo());
			dto.setSrFlag(flowListPo.getSrFlag());
			dto.setPayeeAccNo(flowListPo.getPayeeAcctNo());
			dto.setPayerAccNo(flowListPo.getPayerAcctNo());
			dto.setMerNo(flowListPo.getMerNo());
			dto.setTransAmt(flowListPo.getTransAmt());
			dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
		} else {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0021, transSubSeq);// 支付流水不存在
		}
		PayOrderListPo orderListPo = new PayOrderListPo();
		orderListPo.setOrderNo(dto.getOrderNo());
		orderListPo = daoService.selectOne(orderListPo);

		if (null == orderListPo) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, dto.getOrderNo());// 订单不存在
		}

		String isNotifyCoreSys = "true";
		// String refundFlag = "false";
		String orderStat = orderListPo.getOrderStat();
		// 如果订单状态为终态:支付成功,则直接返回成功给第三方支付
		if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y
				.equals(orderStat)) {
			isNotifyCoreSys = "false";
			// String payType = orderListPo.getPayType();
			// if (StringUtils.isNotBlank(payType)
			// && !DataBaseConstants_PAY.T_PAY_TYPE_ACP_WR_CODE.equals(payType)
			// || !DataBaseConstants_PAY.T_PAY_TYPE_ACP_QR_CODE.equals(payType))
			// {
			// refundFlag = "true";
			// }
		}
		dto.setPayServicType(orderListPo.getPayServicType());
		dto.setIsNotifyCoreSys(isNotifyCoreSys);
		// dto.setRefundFlag(refundFlag);
		String orderTime=dto.getOrderTime();
		if(StringUtils.isBlank(orderTime)){
			dto.setTimeEnd(transSubSeq.substring(0,transSubSeq.length()-3));
		}else{
			dto.setTimeEnd(dto.getOrderTime());
		}
		String routeDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		dto.setRouteDate(routeDate);
		dto.setRouteSeq(dto.getVoucherNum());
		LOG.info("支付结果通知前检查结束");
		return dto;
	}
}
