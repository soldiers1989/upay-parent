/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.exception.SysErrCode;
import com.upay.busi.pay.service.dto.PaySetStateDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.SysInfoContext;

/**
 * 交易状态，通道状态设置
 * 
 * @author zhanggr
 * 
 */
public class PaySetStateServiceImpl extends
		AbstractDipperHandler<PaySetStateDto> {

	@Override
	public PaySetStateDto execute(PaySetStateDto pmtSetStateDto, Message message)
			throws Exception {
		// statFlag 0：支付成功 2:支付中 1：支付失败
		String statFlag = pmtSetStateDto.getStatFlag();
        
		//银联at通道 密码支付错误码： UP0124903、UP0124901 更改状态为支付中
		if (pmtSetStateDto.getErrCodeDes() != null
				&& (pmtSetStateDto.getErrCodeDes().indexOf("UP0124903") > 0 || pmtSetStateDto.getErrCodeDes().indexOf("UP0124901") > 0)) {
			statFlag = "2";
		}

		String payServicType = pmtSetStateDto.getPayServicType();
		if (CommonBaseConstans_PAY.STAT_SET_FLAG_SUCC.equals(statFlag)) {
			// <!-- 成功默认值 订单状态 支付状态-->
			if (StringUtils.isNotBlank(payServicType)
					&& DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE
							.equals(payServicType)) {
				pmtSetStateDto
						.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP);
			} else {
				pmtSetStateDto
						.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
			}
			pmtSetStateDto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
			pmtSetStateDto
					.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
		} else if (CommonBaseConstans_PAY.STAT_SET_FLAG_FAIL.equals(statFlag)) {
			/* 增加通道请求超时的处理 by Guo 20160829 */
			if (SysErrCode.SYS003.equals(message.getFault().getCode())) {
				pmtSetStateDto
						.setTransStat(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN);
				pmtSetStateDto
						.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);// 未知
			} else {
				pmtSetStateDto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
				pmtSetStateDto
						.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
			}
			// <!-- 默认值 支付状态 订单状态 3支付失败-->
			pmtSetStateDto
					.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL);

		} else if (CommonBaseConstans_PAY.STAT_SET_FLAG_PAYMENT
				.equals(statFlag)) {
			pmtSetStateDto
					.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING);
			pmtSetStateDto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_PROING);// 改为8
			pmtSetStateDto
					.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);
		}

		Date sysTime = pmtSetStateDto.getSysTime();
		if (sysTime != null && !sysTime.equals("")) {
			String sysTimeOne = DateUtil.format(sysTime, "M月d日 HH:mm");
			pmtSetStateDto.setSysTimeOne(sysTimeOne);
			pmtSetStateDto.setSysTime(sysTime);
		} else {
			Date sysTime1 = SysInfoContext.getSysTime();
			String sysTimeOne = DateUtil.format(sysTime1, "M月d日 HH:mm");
			pmtSetStateDto.setSysTimeOne(sysTimeOne);
			pmtSetStateDto.setSysTime(sysTime1);
		}
		return pmtSetStateDto;
	}
}
