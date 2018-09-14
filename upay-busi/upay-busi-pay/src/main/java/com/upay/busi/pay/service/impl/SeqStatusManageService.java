package com.upay.busi.pay.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.SeqStatusManageDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.pay.PayFlowListPo;

/**
 * 流水号管理交易状态
 * 
 * @author liu
 * 
 */
public class SeqStatusManageService extends
		AbstractDipperHandler<SeqStatusManageDto> {

	private static final Logger LOG = LoggerFactory
			.getLogger(SeqStatusManageService.class);

	@Resource
	private IDaoService daoService;

	@Override
	public SeqStatusManageDto execute(SeqStatusManageDto seqStatusManageDto,
			Message msg) throws Exception {
		String routeCode = seqStatusManageDto.getRouteCode();
		String transSubSeq = seqStatusManageDto.getTransSubSeq(); // 明细流水
		String transStat = seqStatusManageDto.getTransStat(); // 内部交易状态
		String clrType = seqStatusManageDto.getClrType();// 通道清算方式
		// 判断流水号和交易状态是否为空
		if (StringUtils.isBlank(transSubSeq)) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "流水号");
		}
		if (StringUtils.isBlank(routeCode)) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "资金通道");
		}
		if (StringUtils.isBlank(transStat)) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易状态");
		}
		if (DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(transStat)) {
			seqStatusManageDto.setSuccessRouteCode(seqStatusManageDto
					.getRouteCode());
		}
		PayFlowListPo payFlowListPo = new PayFlowListPo();
		PayFlowListPo payFlowListPo2 = new PayFlowListPo();

		payFlowListPo.setTransSubSeq(transSubSeq);
		PayFlowListPo pay = daoService.selectOne(payFlowListPo);
		if (pay == null) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, transSubSeq);// 流水号不存在
		}
		if (DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)) {
			if (DataBaseConstants_PAY.ROUTE_CLR_TYPE_NOW.equals(clrType)
					|| DataBaseConstants_PAY.ROUTE_CLR_TYPE_NOWA
							.equals(clrType)) {
				if (DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(transStat)) {
					payFlowListPo2
							.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
				}
			}
		} else {
			if (DataBaseConstants_PAY.ROUTE_CLR_TYPE_NOW.equals(clrType)
					|| DataBaseConstants_PAY.ROUTE_CLR_TYPE_NOWA
							.equals(clrType)) {
				if (DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(transStat)) {
					payFlowListPo2
							.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
				}
			} else if (DataBaseConstants_PAY.ROUTE_CLR_TYPE_DAY.equals(clrType)
					|| DataBaseConstants_PAY.ROUTE_CLR_TYPE_DAYA
							.equals(clrType)) {
				if (DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(transStat)) {
					payFlowListPo2
							.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_ING);
				}
			}
		}
		payFlowListPo2.setTransStat(transStat);
		if (StringUtils.isNotBlank(seqStatusManageDto.getRouteTransStat())) {
			payFlowListPo2.setRouteTransStat(seqStatusManageDto
					.getRouteTransStat());
		}

		if (StringUtils.isNotBlank(seqStatusManageDto.getRouteDate())) {
			payFlowListPo2.setRouteDate(DateUtil.parse(
					seqStatusManageDto.getRouteDate(), "yyyyMMdd"));
		}

		if (StringUtils.isNotBlank(seqStatusManageDto.getRouteSeq())) {
			payFlowListPo2.setRouteSeq(seqStatusManageDto.getRouteSeq());
		}
		payFlowListPo2.setLastUpdateTime(seqStatusManageDto.getSysTime()); // 设置最后变更时间
		daoService.update(payFlowListPo2, payFlowListPo);
		if (DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routeCode)) {
			if (StringUtils.isNotBlank(seqStatusManageDto.getRouteTransStat())
					&& !CommonConstants_GNR.OUT_PAY_STAT_SUCCESS
							.equals(seqStatusManageDto.getRouteTransStat())) {
				if (CommonConstants_GNR.OUT_PAY_STAT_ING
						.equals(seqStatusManageDto.getRouteTransStat())) {
					ExInfo.throwDipperEx(AppCodeDict.BISPAY0122,transSubSeq);
				} else {
					if (StringUtils.isBlank(seqStatusManageDto
							.getResponseCode())) {
						ExInfo.throwDipperEx(AppCodeDict.BISPAY0058);
					} else {
						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
								seqStatusManageDto.getResponseMessage());
					}
				}

			}
		} else {
			if (StringUtils.isNotBlank(seqStatusManageDto.getRouteTransStat())
					&& CommonConstants_GNR.OUT_PAY_STAT_FAIL
							.equals(seqStatusManageDto.getRouteTransStat())) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0028, routeCode);
			}
		}

		return seqStatusManageDto;
	}

}
