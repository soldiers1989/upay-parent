package com.upay.batch.stepservice.schedule;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.exception.SysErrCode;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.CommonMethodUtils;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.acc.AccBindBookPo;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**
 * 处理隔日退款异常
 * 
 * @author yhy
 */
public class RefundException extends
		AbstractStepExecutor<Object, PayOrderListPo> {

	@Resource(name = "defaultWeiXinHttpsClientHandler")
	IDipperHandler<Message> weixin;
	@Resource
	private IDaoService daoService;
	@Resource
	private ISequenceService sequenceService;
	@Resource(name = "SA_ZJPAY_Pay2521Handler")
	private IDipperHandler<Message> zj2521;
	@Resource(name = "SA_ZJPAY_Pay1133Handler")
	private IDipperHandler<Message> zj1133;
	@Resource(name = "coreCliDipperHandler")
	private IDipperHandler<Message> coreCliDipperHandler;
	@Resource(name = "UnionReFoundDipperHandler")
	private IDipperHandler<Message> unionRufund;
	
	@Resource(name = "AlipayRefundHandler")
	private IDipperHandler<Message> alipayRufund;

	@Override
	public int getTotalResult(BatchParams batchParams, Object object)
			throws BatchException {

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("orderStat",
				DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC);// 订单状态为11-退款成功
		sqlParam.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_REF);// 交易类型为退款
		sqlParam.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_HOST);// 通道代码0001核心
		sqlParam.put("transStat", DataBaseConstants_PAY.T_PAY_TX_SUCCESS);// 交易状态N-成功
		List<PayOrderListPo> orderList = daoService.selectList(
				PayOrderListPo.class.getName() + ".selectRefundExce", sqlParam);
		List<PayOrderListPo> list = checkFlowList(orderList);
		return list.size();
	}

	@Override
	public List<PayOrderListPo> getDataList(BatchParams batchParams,
			int offset, int pageSize, Object object) throws BatchException {

		// 隔日退款的大体流程为：先从结算账户退钱到带清算账户，然后调用中金快捷支付退款交易，然后去核心记账
		// 隔日退款的流程为：如果结算账户是他行卡或他行对公账户，流程为中金代付>核心记账>中金快捷支付退款>核心记账
		// 如果结算账户为本行卡、本行对公账户、内部账户，流程为核心记账>中金快捷支付退款>核心记账
		// 查询隔日退款异常的订单，并且该订单经过订单状态同步后，订单状态改为11退款成功；
		// 如果核心成功流水成功的条数为0，则说明该退款在做中金代付时，返回处理中；如果条数为1，则说明，该退款在做快捷支付退款时，返回处理中。
		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("orderStat",
				DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC);// 订单状态为11-退款成功
		sqlParam.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_REF);// 交易类型为退款
		sqlParam.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_HOST);// 通道代码0001核心
		sqlParam.put("transStat", DataBaseConstants_PAY.T_PAY_TX_SUCCESS);// 交易状态N-成功
		List<PayOrderListPo> orderList = daoService.selectList(
				PayOrderListPo.class.getName() + ".selectRefundExce", sqlParam);
		List<PayOrderListPo> list = checkFlowList(orderList);
		return list;

	}

	@Override
	public void execute(BatchParams batchParams, int index,
			PayOrderListPo payOrderListPo, Object object) throws BatchException {
		
		MerAcctInfoPo merAcctInfoPo = new MerAcctInfoPo();
		merAcctInfoPo.setMerNo(payOrderListPo.getMerNo());
		merAcctInfoPo = daoService.selectOne(merAcctInfoPo);
		if (merAcctInfoPo == null) {
			logger.error("商户结算账户不存在！");
			return;
		}
		String stlAcctType = merAcctInfoPo.getStlAcctType();// 商户结算账户类型
		GnrParmPo parmPo = new GnrParmPo();
		parmPo.setParmId(DataBaseConstans_ACC.ZJ_ACCT_NO);
		parmPo = daoService.selectOne(parmPo);
		if (null == parmPo) {
			logger.error("-------------中金备用金账户参数未配置!!!");
			return;
		}
		//查询中金、核心通道通道信息
		Map<String, PayRouteInfoPo> routeMap = new HashMap<String, PayRouteInfoPo>();
		PayRouteInfoPo payRouteInfoPo = new PayRouteInfoPo();
		List<PayRouteInfoPo> payRouteList = daoService
				.selectList(payRouteInfoPo);
		for (PayRouteInfoPo payRouteInfoPo2 : payRouteList) {
			routeMap.put(payRouteInfoPo2.getRouteCode(), payRouteInfoPo2);
		}
		//查询核心成功流水条数
		String orderNo = payOrderListPo.getOrderNo();
		List<PayFlowListPo> HostFlowList = successFlowNum(DataBaseConstants_PAY.ROUTE_CODE_HOST,orderNo);
		//查询中金成功流水条数
		List<PayFlowListPo> zjFlowList = successFlowNum(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY,orderNo);
		//查询微信成功流水条数
		List<PayFlowListPo> weChatRefundFlowList = successFlowNum(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS,orderNo);
		//查询银联成功流水条数
		List<PayFlowListPo> unionRefundFlowList = successFlowNum(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS,orderNo);
		//查询银联成功流水条数
		List<PayFlowListPo> alipayRefundFlowList = successFlowNum(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY,orderNo);
		
		//查询原订单是否是微信支付
		PayFlowListPo weChatFlow = new PayFlowListPo();
		weChatFlow.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS);
		weChatFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
		weChatFlow.setOrderNo(payOrderListPo.getOriOrderNo());
		weChatFlow = daoService.selectOne(weChatFlow);
		//查询原订单是否是银联支付
		PayFlowListPo unionFlow = new PayFlowListPo();
		unionFlow.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);
		unionFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
		unionFlow.setOrderNo(payOrderListPo.getOriOrderNo());
		unionFlow = daoService.selectOne(unionFlow);
		//查询原订单是否是支付宝支付
		PayFlowListPo alipayFlow = new PayFlowListPo();
		alipayFlow.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY);
		alipayFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
		alipayFlow.setOrderNo(payOrderListPo.getOriOrderNo());
		alipayFlow = daoService.selectOne(alipayFlow);
		
		//获取原订单信息
		PayOrderListPo oriPayOrder = new PayOrderListPo();
		oriPayOrder.setOrderNo(payOrderListPo.getOriOrderNo());
		oriPayOrder = daoService.selectOne(oriPayOrder);
		//如果原支付方式为平台账户支付，则需要为虚拟账户入账,首先查询虚拟账户是否存在，如果不存在，则不处理该退款
		if(DataBaseConstants_PAY.T_PAY_TYPE_PLATFORM_ACC.equals(oriPayOrder.getPayType())){
			//查询 需要退款的账户
			AccVbookPo acc=new AccVbookPo();
		    acc.setUserId(oriPayOrder.getUserId());
		    acc=daoService.selectOne(acc);
		    if(acc == null){
		    	logger.error("用户的平台账户（虚拟账户）不存在，无法退款！");
				return;
		    }
		}
		PayRouteInfoPo zjRouteInfo = routeMap.get(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
		PayRouteInfoPo hostRouteInfo = routeMap.get(DataBaseConstants_PAY.ROUTE_CODE_HOST);
		//结算账户为他行借记卡和他行对公账户
		if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(stlAcctType) || DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(stlAcctType)){
			// 核心成功流水为0条
			if (HostFlowList.size() == 0) {
				if (zjFlowList.size() == 0) {
					logger.error("该订单异常，请检查！");
					return;
				} else if (zjFlowList.size() == 1) {
					if (stlAcctType.equals(zjFlowList.get(0).getPayerAcctType())&& DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT.equals(zjFlowList.get(0).getPayeeAcctType())) {
						// 中金走代付交易成功,并且核心成功流水为0条，则需要去核心记账>中金快捷支付退款>核心记账
						// 登记核心交易流水
						Map<String, Object> parm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_HOST, "2011",routeMap,null,null);
						Map<String, Object> seqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, parm);
						// 调用核心记账接口
						boolean resp = reqHost(seqMap, parm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
						if(resp){
							if(DataBaseConstants_PAY.T_PAY_TYPE_BANK_QUICK.equals(oriPayOrder.getPayType()) || DataBaseConstants_PAY.T_PAY_TYPE_PLATFORM_ACC.equals(oriPayOrder.getPayType()) ){
								//处理原交易为本行快捷支付、平台账户支付的交易
								Map<String, Object> zjHostParm = dealCard(routeMap,oriPayOrder);
								String transCode = (String) zjHostParm.get("transCode");
								
								// 登记核心交易流水
								Map<String, Object> zjHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, zjHostParm);
								// 调用核心记账接口
								boolean respHost = reqHost(zjHostseqMap, zjHostParm,transCode);
								if(!respHost){
									logger.error("核心记账失败！");
									return;
								}
								
							}else if(weChatFlow == null && unionFlow == null && alipayFlow == null){
								
								// 登记中金流水
								Map<String, Object> zjParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY, null,routeMap,payOrderListPo,null);
								Map<String, Object> zjseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY, zjParm);
								//如果是网银退款，则调用1133接口
								if(DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_PERSONAL.equals(oriPayOrder.getPayType()) || DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_COMPANY.equals(oriPayOrder.getPayType())){
									zjParm.put("onlineBank", CmparmConstants.GATEWAY_ZJPAY_1133);
								}
								// 调用中金快捷支付退款接口
								boolean respZJ = reqZJ(zjseqMap,zjParm);
								if(respZJ){
									// 登记核心交易流水
									Map<String, Object> zjHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_HOST, "2521",routeMap,null,null);
									Map<String, Object> zjHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, zjHostParm);
									// 调用核心记账接口
									boolean respHost = reqHost(zjHostseqMap, zjHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
									if(!respHost){
										logger.error("核心记账失败！");
										return;
									}
								}else{
									logger.error("中金快捷支付退款失败！");
									return;
								}
							}else if(weChatFlow != null){
								//微信退款处理
								//登记微信退款流水
								Map<String, Object> weChatParm = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS,null);
								//调用微信退款接口
								boolean respWeChat = reqWeChat(payOrderListPo,weChatParm);
								if(respWeChat){
									// 登记核心交易流水
									Map<String, Object> weChatHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS, null,routeMap,null,null);
									Map<String, Object> weChatHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, weChatHostParm);
									// 调用核心记账接口
									boolean respHost = reqHost(weChatHostseqMap, weChatHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
									if(!respHost){
										logger.error("核心记账失败！");
										return;
									}
								}else{
									logger.error("微信退款失败！");
									return;
								}
							}else if(unionFlow != null){
								//银联网关支付退款处理
								//登记银联退款流水
								Map<String, Object> unionParm = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS,null);
								//调用银联退款接口
								boolean respUnion = reqUnion(payOrderListPo,unionParm,oriPayOrder.getPayType());
								if(respUnion){
									// 登记核心交易流水
									Map<String, Object> unionHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS, null,routeMap,null,null);
									Map<String, Object> unionHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, unionHostParm);
									// 调用核心记账接口
									boolean respHost = reqHost(unionHostseqMap, unionHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
									if(!respHost){
										logger.error("核心记账失败！");
										return;
									}
								}else{
									logger.error("银联退款失败！");
									return;
								}
							}else if(alipayFlow != null){
								//登记支付宝退款流水
								Map<String, Object> alipayParm = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_ALIPAY,null);
								alipayParm.put("outTradeNo", alipayFlow.getTransSubSeq());
								//调用支付宝退款接口
								boolean respAlipay = reqAlipay(payOrderListPo,alipayParm);
								if(respAlipay){
									// 登记核心交易流水
									Map<String, Object> alipayHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY, null,routeMap,null,null);
									Map<String, Object> alipayHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, alipayHostParm);
									// 调用核心记账接口
									boolean respHost = reqHost(alipayHostseqMap, alipayHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
									if(!respHost){
										logger.error("核心记账失败！");
										return;
									}
								}else{
									logger.error("支付宝退款失败！");
									return;
								}
							}
						}else{
							logger.error("核心记账失败！");
							return;
						}
						
					}
				}
			} else if (HostFlowList.size() == 1) {
				//中金代收成功，核心记账成功，中金快捷支付退款失败
				if(zjFlowList.size() == 1){
					if (stlAcctType.equals(zjFlowList.get(0).getPayerAcctType())&& DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT.equals(zjFlowList.get(0).getPayeeAcctType())) {
						if(HostFlowList.get(0).getPayerAcctNo().equals(zjRouteInfo.getTransAcctNo())&&HostFlowList.get(0).getPayeeAcctNo().equals(hostRouteInfo.getClrAcctNo())){
							if(DataBaseConstants_PAY.T_PAY_TYPE_BANK_QUICK.equals(oriPayOrder.getPayType()) || DataBaseConstants_PAY.T_PAY_TYPE_PLATFORM_ACC.equals(oriPayOrder.getPayType()) ){
								//处理原交易为本行快捷支付、平台账户支付的交易
								Map<String, Object> zjHostParm = dealCard(routeMap,oriPayOrder);
								String transCode = (String) zjHostParm.get("transCode");
								// 登记核心交易流水
								Map<String, Object> zjHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, zjHostParm);
								// 调用核心记账接口
								boolean respHost = reqHost(zjHostseqMap, zjHostParm,transCode);
								if(!respHost){
									logger.error("核心记账失败！");
									return;
								}
								
							}else if(weChatFlow == null && unionFlow == null && alipayFlow == null){
								// 登记中金流水
								Map<String, Object> zjParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY, null,routeMap,payOrderListPo,null);
								Map<String, Object> zjseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY, zjParm);
								//如果是网银退款，则调用1133接口
								if(DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_PERSONAL.equals(oriPayOrder.getPayType()) || DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_COMPANY.equals(oriPayOrder.getPayType())){
									zjParm.put("onlineBank", CmparmConstants.GATEWAY_ZJPAY_1133);
								}
								// 调用中金快捷支付退款接口
								boolean respZJ = reqZJ(zjseqMap,zjParm);
								if(respZJ){
									// 登记核心交易流水
									Map<String, Object> zjHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_HOST, "2521",routeMap,null,null);
									Map<String, Object> zjHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, zjHostParm);
									// 调用核心记账接口
									boolean respHost = reqHost(zjHostseqMap, zjHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
									if(!respHost){
										logger.error("核心记账失败！");
										return;
									}
								}else{
									logger.error("中金快捷支付退款失败！");
									return;
								}
							}else if(weChatFlow != null){
								if(weChatRefundFlowList.size() == 0){
									//微信退款处理
									//登记微信退款流水
									Map<String, Object> weChatParm = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS,null);
									//调用微信退款接口
									boolean respWeChat = reqWeChat(payOrderListPo,weChatParm);
									if(respWeChat){
										// 登记核心交易流水(微信)
										Map<String, Object> weChatHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS, null,routeMap,null,null);
										Map<String, Object> weChatHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, weChatHostParm);
										// 调用核心记账接口
										boolean respHost = reqHost(weChatHostseqMap, weChatHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
										if(!respHost){
											logger.error("核心记账失败！");
											return;
										}
									}else{
										logger.error("微信退款失败！");
										return;
									}
								}else if(weChatRefundFlowList.size() == 1){
									// 登记核心交易流水(微信)
									Map<String, Object> weChatHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS, null,routeMap,null,null);
									Map<String, Object> weChatHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, weChatHostParm);
									// 调用核心记账接口
									boolean respHost = reqHost(weChatHostseqMap, weChatHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
									if(!respHost){
										logger.error("核心记账失败！");
										return;
									}
								}
								
							}else if(unionFlow != null){
								if(unionRefundFlowList.size() == 0){
									//银联网关支付退款处理
									//登记银联退款流水
									Map<String, Object> unionParm = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS,null);
									//调用银联退款接口
									boolean respUnion = reqUnion(payOrderListPo,unionParm,oriPayOrder.getPayType());
									if(respUnion){
										// 登记核心交易流水
										Map<String, Object> unionHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS, null,routeMap,null,null);
										Map<String, Object> unionHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, unionHostParm);
										// 调用核心记账接口
										boolean respHost = reqHost(unionHostseqMap, unionHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
										if(!respHost){
											logger.error("核心记账失败！");
											return;
										}
									}else{
										logger.error("银联退款失败！");
										return;
									}
								}else if(unionRefundFlowList.size() == 1){
									// 登记核心交易流水
									Map<String, Object> unionHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS, null,routeMap,null,null);
									Map<String, Object> unionHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, unionHostParm);
									// 调用核心记账接口
									boolean respHost = reqHost(unionHostseqMap, unionHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
									if(!respHost){
										logger.error("核心记账失败！");
										return;
									}
								}
							}else if(alipayFlow != null){
								if(alipayRefundFlowList.size() == 0){
									//登记支付宝退款流水
									Map<String, Object> alipayParm = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_ALIPAY,null);
									alipayParm.put("outTradeNo", alipayFlow.getTransSubSeq());
									//调用支付宝退款接口
									boolean respAlipay = reqAlipay(payOrderListPo,alipayParm);
									if(respAlipay){
										// 登记核心交易流水
										Map<String, Object> alipayHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY, null,routeMap,null,null);
										Map<String, Object> alipayHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, alipayHostParm);
										// 调用核心记账接口
										boolean respHost = reqHost(alipayHostseqMap, alipayHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
										if(!respHost){
											logger.error("核心记账失败！");
											return;
										}
									}else{
										logger.error("支付宝退款失败！");
										return;
									}
								}else if(alipayRefundFlowList.size() == 1){
									// 登记核心交易流水
									Map<String, Object> alipayHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY, null,routeMap,null,null);
									Map<String, Object> alipayHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, alipayHostParm);
									// 调用核心记账接口
									boolean respHost = reqHost(alipayHostseqMap, alipayHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
									if(!respHost){
										logger.error("核心记账失败！");
										return;
									}
								}
							}
						}
					}
				}else if(zjFlowList.size() == 2){//中金代收成功，核心记账成功，中金快捷支付退款成功，核心记账失败
					if(HostFlowList.get(0).getPayerAcctNo().equals(zjRouteInfo.getTransAcctNo())&&HostFlowList.get(0).getPayeeAcctNo().equals(hostRouteInfo.getClrAcctNo())){
						// 登记核心交易流水
						Map<String, Object> zjHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_HOST, "2521",routeMap,null,null);
						Map<String, Object> zjHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, zjHostParm);
						// 调用核心记账接口
						boolean respHost = reqHost(zjHostseqMap, zjHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
						if(!respHost){
							logger.error("核心记账失败！");
							return;
						}
					}
				}else{
					logger.error("该订单异常，请检查！");
					return;
				}
			} else {
				logger.error("SQL语句异常，请检查！");
				return;
			}
			
		}else{
			//结算账户为内部账户、本行借记卡、本行对公账户
			if(HostFlowList.size()==1){
				if(zjFlowList.size() == 0){
					if(DataBaseConstants_PAY.T_PAY_TYPE_BANK_QUICK.equals(oriPayOrder.getPayType()) || DataBaseConstants_PAY.T_PAY_TYPE_PLATFORM_ACC.equals(oriPayOrder.getPayType()) ){
						//处理原交易为本行快捷支付、平台账户支付的交易
						Map<String, Object> zjHostParm = dealCard(routeMap,oriPayOrder);
						String transCode = (String) zjHostParm.get("transCode");
						// 登记核心交易流水
						Map<String, Object> zjHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, zjHostParm);
						// 调用核心记账接口
						boolean respHost = reqHost(zjHostseqMap, zjHostParm,transCode);
						if(!respHost){
							logger.error("核心记账失败！");
							return;
						}
						
					}else if(weChatFlow == null && unionFlow == null && alipayFlow == null){
						// 登记中金流水
						Map<String, Object> zjParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY, null,routeMap,payOrderListPo,null);
						Map<String, Object> zjseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY, zjParm);
						//如果是网银退款，则调用1133接口
						if(DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_PERSONAL.equals(oriPayOrder.getPayType()) || DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_COMPANY.equals(oriPayOrder.getPayType())){
							zjParm.put("onlineBank", CmparmConstants.GATEWAY_ZJPAY_1133);
						}
						// 调用中金快捷支付退款接口
						boolean respZJ = reqZJ(zjseqMap,zjParm);
						if(respZJ){
							// 登记核心交易流水
							Map<String, Object> zjHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_HOST, "2521",routeMap,null,null);
							Map<String, Object> zjHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, zjHostParm);
							// 调用核心记账接口
							boolean respHost = reqHost(zjHostseqMap, zjHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
							if(!respHost){
								logger.error("核心记账失败！");
								return;
							}
						}else{
							logger.error("中金快捷支付退款失败！");
							return;
						}
					}else if(weChatFlow != null){
						if(weChatRefundFlowList.size() == 0){
							//微信退款处理
							//登记微信退款流水
							Map<String, Object> weChatParm = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS,null);
							//调用微信退款接口
							boolean respWeChat = reqWeChat(payOrderListPo,weChatParm);
							if(respWeChat){
								// 登记核心交易流水(微信)
								Map<String, Object> weChatHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS, null,routeMap,null,null);
								Map<String, Object> weChatHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, weChatHostParm);
								// 调用核心记账接口
								boolean respHost = reqHost(weChatHostseqMap, weChatHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
								if(!respHost){
									logger.error("核心记账失败！");
									return;
								}
							}else{
								logger.error("微信退款失败！");
								return;
							}
						}else if(weChatRefundFlowList.size() == 1){
							// 登记核心交易流水(微信)
							Map<String, Object> weChatHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS, null,routeMap,null,null);
							Map<String, Object> weChatHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, weChatHostParm);
							// 调用核心记账接口
							boolean respHost = reqHost(weChatHostseqMap, weChatHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
							if(!respHost){
								logger.error("核心记账失败！");
								return;
							}
						}
					}else if(unionFlow != null){
						if(unionRefundFlowList.size() == 0){
							//银联网关支付退款处理
							//登记银联退款流水
							Map<String, Object> unionParm = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS,null);
							//调用银联退款接口
							boolean respUnion = reqUnion(payOrderListPo,unionParm,oriPayOrder.getPayType());
							if(respUnion){
								// 登记核心交易流水
								Map<String, Object> unionHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS, null,routeMap,null,null);
								Map<String, Object> unionHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, unionHostParm);
								// 调用核心记账接口
								boolean respHost = reqHost(unionHostseqMap, unionHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
								if(!respHost){
									logger.error("核心记账失败！");
									return;
								}
							}else{
								logger.error("银联退款失败！");
								return;
							}
						}else if(unionRefundFlowList.size() == 1){
							// 登记核心交易流水
							Map<String, Object> unionHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS, null,routeMap,null,null);
							Map<String, Object> unionHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, unionHostParm);
							// 调用核心记账接口
							boolean respHost = reqHost(unionHostseqMap, unionHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
							if(!respHost){
								logger.error("核心记账失败！");
								return;
							}
						}
					}else if(alipayFlow != null){
						if(alipayRefundFlowList.size() == 0){
							//登记支付宝退款流水
							Map<String, Object> alipayParm = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_ALIPAY,null);
							alipayParm.put("outTradeNo", alipayFlow.getTransSubSeq());
							//调用支付宝退款接口
							boolean respAlipay = reqAlipay(payOrderListPo,alipayParm);
							if(respAlipay){
								// 登记核心交易流水
								Map<String, Object> alipayHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY, null,routeMap,null,null);
								Map<String, Object> alipayHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, alipayHostParm);
								// 调用核心记账接口
								boolean respHost = reqHost(alipayHostseqMap, alipayHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
								if(!respHost){
									logger.error("核心记账失败！");
									return;
								}
							}else{
								logger.error("支付宝退款失败！");
								return;
							}
						}else if(alipayRefundFlowList.size() == 1){
							// 登记核心交易流水
							Map<String, Object> alipayHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY, null,routeMap,null,null);
							Map<String, Object> alipayHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, alipayHostParm);
							// 调用核心记账接口
							boolean respHost = reqHost(alipayHostseqMap, alipayHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
							if(!respHost){
								logger.error("核心记账失败！");
								return;
							}
						}
					}
				}else if(zjFlowList.size() == 1){
					// 登记核心交易流水
					Map<String, Object> zjHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_HOST, "2521",routeMap,null,null);
					Map<String, Object> zjHostseqMap = insertFlowList(payOrderListPo,DataBaseConstants_PAY.ROUTE_CODE_HOST, zjHostParm);
					// 调用核心记账接口
					boolean respHost = reqHost(zjHostseqMap, zjHostParm,CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
					if(!respHost){
						logger.error("核心记账失败！");
						return;
					}
				}
			}else{
				logger.error("该退款订单异常！订单号[{}]"+orderNo);
				return;
			}
			
		}
		
		//由于是隔日退款，如果退款成功，修改退款订单的清算状态为清算成功，
		payOrderListPo.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
		daoService.update(payOrderListPo);
		
	}

	private boolean reqAlipay(PayOrderListPo payOrderListPo,
			Map<String, Object> alipayParm) {
		PayFlowListPo payFlow = new PayFlowListPo();
		payFlow.setTransSubSeq((String)alipayParm.get("seq"));
		payFlow = daoService.selectOne(payFlow);
		Date sysDate = SysInfoContext.getSysDate();
		Message message = MessageFactory.create(IdGenerateFactory.generateId(),
				MessageFactory.createSimpleMessage(
						new HashMap<String, Object>(),
						new HashMap<String, Object>()), FaultFactory.create(
						Constants.ResponseCode.SUCCESS, ""));
		Map<String, Object> body = (Map<String, Object>) message.getTarget().getBodys();
		body.put("outTradeNo", alipayParm.get("outTradeNo"));
		body.put("refundAmount", payOrderListPo.getTransAmt());
		body.put("outRequestNo", alipayParm.get("seq"));
		Message handle = null;
		try {
			handle = alipayRufund.handle(message);
		} catch (Exception e) {
			logger.error("-------------调用支付宝退款接口服务返回异常!!!，错误码[{}],错误信息[{}]",
					handle.getFault().getCode(), handle.getFault().getMsg());
		}
		if (SysErrCode.SYS003.equals(message.getFault().getCode())) {
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN);
			payFlow.setRouteDate(sysDate);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);
			daoService.update(payFlow);
			logger.error("-------------调用支付宝退款接口服务返回异常!!!，错误码[{}],错误信息[{}]",
					handle.getFault().getCode(), handle.getFault().getMsg());
			return false;
		}
		Map<String, Object> result = null;
		if (message.getTarget().getBodys() != null
				&& handle.getTarget().getBodys() instanceof Map) {
			result = (Map<String, Object>) handle.getTarget().getBodys();
		}
		if(result!=null&&result.get("returnCode")!=null&&DataBaseConstants_PAY.PAY_ALIPAY_SUCC.equals(result.get("returnCode"))){
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
			payFlow.setRouteDate(sysDate);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
			payFlow.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
			daoService.update(payFlow);
			return true;
		}else{
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
			payFlow.setRouteDate(sysDate);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
			daoService.update(payFlow);
		}
		
		return false;
	}

	private boolean reqUnion(PayOrderListPo payOrderListPo,
			Map<String, Object> unionParm, String payType) {
		// 查询原订单银联流水信息
		PayFlowListPo payFlowListPo = new PayFlowListPo();
		payFlowListPo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);
		payFlowListPo.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
		payFlowListPo.setOrderNo(payOrderListPo.getOriOrderNo());
		payFlowListPo = daoService.selectOne(payFlowListPo);
		Message message = MessageFactory.create(IdGenerateFactory.generateId(),
				MessageFactory.createSimpleMessage(
						new HashMap<String, Object>(),
						new HashMap<String, Object>()), FaultFactory.create(
						Constants.ResponseCode.SUCCESS, ""));
		Map<String, Object> body = (Map<String, Object>) message.getTarget()
				.getBodys();
		Message handle = null;
		Map<String, Object> result = null;
		try {
			if(DataBaseConstants_PAY.T_PAY_TYPE_UNIONPAY_GATEWAY.equals(payType)){
				//尚未接入
				//handle = unionRufund.handle(message);
				return false;
			}else{
				body.put("transSubSeq", (String) unionParm.get("seq"));
				body.put("txnAmt",String.valueOf(payOrderListPo.getTransAmt().multiply(new BigDecimal(100)).intValue()));
				body.put("origOrderNo", payFlowListPo.getTransSubSeq());
				body.put("origOrderTime", DateUtil.format(payFlowListPo.getTransTime(), "yyyyMMddHHmmss"));
				if(DataBaseConstants_PAY.T_PAY_TYPE_ACP_QR_CODE.equals(payType)){
					body.put("reqType", "0340000903");
	        	}
	           	if(DataBaseConstants_PAY.T_PAY_TYPE_ACP_WR_CODE.equals(payType)||DataBaseConstants_PAY.T_PAY_TYPE_ACP_XWR_CODE.equals(payType)){
	           		body.put("reqType", "0150000903");
	        	}
				handle = unionRufund.handle(message);
			}
		} catch (Exception e) {
			logger.error("-------------调用银联退款接口服务返回异常!!!，错误码[{}],错误信息[{}]",
					handle.getFault().getCode(), handle.getFault().getMsg());
		}
		if (SysErrCode.SYS003.equals(message.getFault().getCode())) {
			PayFlowListPo payFlowWhere = new PayFlowListPo();
			payFlowWhere.setTransSubSeq((String) unionParm.get("seq"));
			
			PayFlowListPo payFlowParam = new PayFlowListPo();
			payFlowParam.setTransStat(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN);
			payFlowParam.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);
			
			daoService.update(payFlowParam, payFlowWhere);
			return false;
		}

		if (message.getTarget().getBodys() != null
				&& handle.getTarget().getBodys() instanceof Map) {
			result = (Map<String, Object>) handle.getTarget().getBodys();
		}
		if(result.get("respCode")!=null && DataBaseConstants_PAY.UNION_STAT_SUCC.equals(result.get("respCode"))){
			String voucherNum = (String) result.get("voucherNum");
			String settleKey = (String) result.get("settleKey");
			
			PayFlowListPo payFlowWhere = new PayFlowListPo();
			payFlowWhere.setTransSubSeq((String) unionParm.get("seq"));

			PayFlowListPo payFlowParam = new PayFlowListPo();
			payFlowParam.setRouteSeq(voucherNum);
			payFlowParam.setSettleKey(settleKey);
			payFlowParam.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
			payFlowParam.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
			payFlowParam.setRouteDate(DateUtil.parse(DateUtil.format(new Date(), "yyyyMMdd"), "yyyyMMdd"));
			payFlowParam.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
			daoService.update(payFlowParam, payFlowWhere);
			return true;
    	}else{
    		PayFlowListPo payFlowWhere = new PayFlowListPo();
			payFlowWhere.setTransSubSeq((String) unionParm.get("seq"));
			
			PayFlowListPo payFlowParam = new PayFlowListPo();
			payFlowParam.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
			payFlowParam.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
			
			daoService.update(payFlowParam, payFlowWhere);
    	}

		return false;

	}

	private Map<String, Object> dealCard(Map<String, PayRouteInfoPo> routeMap,
			PayOrderListPo oriPayOrder) {
		SimpleDateFormat simp = new SimpleDateFormat("yyyyMMdd");
		// 原支付流水
		PayFlowListPo payFlowListPo = new PayFlowListPo();
		payFlowListPo.setOrderNo(oriPayOrder.getOrderNo());
		payFlowListPo = daoService.selectOne(payFlowListPo);
		Map<String, Object> zjHostParm = null;
		String transCode = null;
		if (DataBaseConstants_PAY.T_PAY_TYPE_BANK_QUICK.equals(oriPayOrder
				.getPayType())) {
			if (DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD
					.equals(payFlowListPo.getPayerAcctType())) {
				zjHostParm = initFlowParm(
						DataBaseConstants_PAY.ROUTE_CODE_HOST, "CREDIT",
						routeMap, null, payFlowListPo);
				transCode = CommonConstants_GNR.CORE_BANK_PAY_CODE_08011;
				AccVbookPo accVbookPo = new AccVbookPo();
				accVbookPo.setUserId(payFlowListPo.getPayUserId());
				accVbookPo = daoService.selectOne(accVbookPo);
				AccBindBookPo accBindBook = new AccBindBookPo();
				accBindBook.setVacctNo(accVbookPo.getVacctNo());
				accBindBook.setVbindAcctNo(payFlowListPo.getPayerAcctNo());
				List<AccBindBookPo> list = daoService.selectList(accBindBook);
				zjHostParm.put("cvv2", list.get(0).getCvn2());
				zjHostParm.put("validdate",
						simp.format(list.get(0).getValidDate()));
				zjHostParm.put("orgBizDate",
						simp.format(payFlowListPo.getSysDate()));
				zjHostParm
						.put("orgBizSerialNo", payFlowListPo.getTransSubSeq());
			} else if (DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD
					.equals(payFlowListPo.getPayerAcctType())) {
				transCode = CommonConstants_GNR.CORE_BANK_PAY_CODE_08001;
				zjHostParm = initFlowParm(
						DataBaseConstants_PAY.ROUTE_CODE_HOST, "DEBIT",
						routeMap, null, payFlowListPo);
				zjHostParm.put("isDebit", true);
				zjHostParm.put("orgBizDate",
						simp.format(payFlowListPo.getSysDate()));
				zjHostParm
						.put("orgBizSerialNo", payFlowListPo.getTransSubSeq());
			}
		} else {
			transCode = CommonConstants_GNR.CORE_BANK_PAY_CODE_08001;
			zjHostParm = initFlowParm(DataBaseConstants_PAY.ROUTE_CODE_HOST,
					"PLATE", routeMap, null, payFlowListPo);
		}
		zjHostParm.put("transCode", transCode);
		return zjHostParm;
	}

	// oriPayOrder 原订单信息，payOrderListPo 退款订单信息
	private void updateOrderStat(PayOrderListPo oriPayOrder,
			PayOrderListPo payOrderListPo) {
		BigDecimal ejectAmt = oriPayOrder.getEjectAmt() == null ? new BigDecimal(
				"0") : oriPayOrder.getEjectAmt();
		if (ejectAmt.compareTo(oriPayOrder.getTransAmt()) == 0) {
			oriPayOrder
					.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
		} else {
			oriPayOrder
					.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
		}
		daoService.update(oriPayOrder);
	}

	private List<PayFlowListPo> successFlowNum(String routeCode, String orderNo) {
		PayFlowListPo paySuccess = new PayFlowListPo();
		paySuccess.setRouteCode(routeCode);
		paySuccess.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
		paySuccess.setOrderNo(orderNo);
		List<PayFlowListPo> succList = daoService.selectList(paySuccess);
		return succList;
	}

	private boolean reqWeChat(PayOrderListPo payOrderListPo,
			Map<String, Object> weChatParm) {
		// 登记的微信流水
		PayFlowListPo payFlow = new PayFlowListPo();
		payFlow.setTransSubSeq((String) weChatParm.get("seq"));
		payFlow = daoService.selectOne(payFlow);
		// 商户信息查询
		MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
		merBaseInfoPo.setMerNo(payOrderListPo.getMerNo());
		merBaseInfoPo = daoService.selectOne(merBaseInfoPo);

		// 查询原订单微信流水信息
		PayFlowListPo payFlowListPo = new PayFlowListPo();
		payFlowListPo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS);
		payFlowListPo.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
		payFlowListPo.setOrderNo(payOrderListPo.getOriOrderNo());
		payFlowListPo = daoService.selectOne(payFlowListPo);
		Date sysDate = SysInfoContext.getSysDate();

		Message message = MessageFactory.create(IdGenerateFactory.generateId(),
				"weixinhttpscli", "XML", "UTF-8", MessageFactory
						.createSimpleMessage(new HashMap<String, Object>(),
								new HashMap<String, Object>()), FaultFactory
						.create(Constants.ResponseCode.SUCCESS, ""));
		Map<String, Object> body = (Map<String, Object>) message.getTarget()
				.getBodys();

		body.put("tranCode", "REFUNDPAY");
		body.put("appId", "wx288339422065bc01");
		body.put("mchId", "1378612302");
		body.put("subMchId", merBaseInfoPo.getSubMchId());
		// body.put("nonceStr", "");

		body.put("transactionId", "");
		body.put("outTradeNo", payFlowListPo.getTransSubSeq());
		body.put("outRefundNo", weChatParm.get("seq"));
		body.put("totalFee",
				payFlowListPo.getTransAmt().multiply(new BigDecimal("100"))
						.toString());
		body.put("refundFee", String.valueOf(((BigDecimal) weChatParm
				.get("transAmt")).multiply(new BigDecimal("100")).intValue()));
		body.put("opUserId", merBaseInfoPo.getSubMchId());
		Message handle = null;
		Map<String, Object> result = null;
		try {
			handle = weixin.handle(message);
		} catch (Exception e) {
			logger.error("-------------调用微信退款接口服务返回异常!!!，错误码[{}],错误信息[{}]",
					handle.getFault().getCode(), handle.getFault().getMsg());
		}
		if (SysErrCode.SYS003.equals(message.getFault().getCode())) {
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN);
			payFlow.setRouteDate(sysDate);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);
			daoService.update(payFlow);
			return false;
		}
		if (message.getTarget().getBodys() != null
				&& handle.getTarget().getBodys() instanceof Map) {
			result = (Map<String, Object>) handle.getTarget().getBodys();
		}

		String wechatSeq = (String) result.get("transactionId");
		if (CommonBaseConstans_PAY.WEIXIN_REFUND_SUCCESS.equals(result
				.get("resultCode"))) {
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
			payFlow.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
			payFlow.setRouteDate(sysDate);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
			payFlow.setRouteSeq(wechatSeq);
			daoService.update(payFlow);
			return true;
		} else {
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
			payFlow.setRouteDate(sysDate);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
			payFlow.setRouteSeq(wechatSeq);
			daoService.update(payFlow);
			return false;
		}

	}

	private boolean reqZJ(Map<String, Object> seqMap, Map<String, Object> parm) {
		PayFlowListPo payFlow = new PayFlowListPo();
		payFlow.setTransSubSeq((String) seqMap.get("seq"));
		payFlow = daoService.selectOne(payFlow);
		Date sysDate = SysInfoContext.getSysDate();
		Message message = MessageFactory.create(IdGenerateFactory.generateId(),
				"zjpaycli", "XML", "GBK", MessageFactory.createSimpleMessage(
						new HashMap<String, Object>(),
						new HashMap<String, Object>()), FaultFactory.create(
						Constants.ResponseCode.SUCCESS, ""));
		Map<String, Object> bodyMap = (Map<String, Object>) message.getTarget()
				.getBodys();

		bodyMap.put("txSN", seqMap.get("zjSeq"));
		bodyMap.put("paymentNo", parm.get("oriRouteSeq"));
		String amount = MoneyUtil
				.transferY2F(
						new BigDecimal(seqMap.get("transAmt").toString()).multiply(new BigDecimal(
								100)), 0).toString();
		bodyMap.put("amount", amount);
		Message handle = null;
		try {
			if (CmparmConstants.GATEWAY_ZJPAY_1133.equals((String) parm
					.get("onlineBank"))) {
				handle = zj1133.handle(message);
			} else {
				handle = zj2521.handle(message);
			}
		} catch (Exception e) {
			logger.error("-------------调用中金代付接口服务返回异常!!!，错误码[{}],错误信息[{}]",
					handle.getFault().getCode(), handle.getFault().getMsg());
		}
		if (SysErrCode.SYS003.equals(message.getFault().getCode())) {
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN);
			payFlow.setRouteDate(sysDate);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);
			daoService.update(payFlow);
			logger.error("-------------调用中金代付接口服务返回异常!!!，错误码[{}],错误信息[{}]",
					handle.getFault().getCode(), handle.getFault().getMsg());
			return false;
		}

		Map<String, Object> zjBody = (Map<String, Object>) handle.getTarget()
				.getBodys();
		String zjSeq = (String) zjBody.get("txSN");
		String respCode = (String) zjBody.get("respCode");

		if (CommonBaseConstans_PAY.ZJ_REFUND_SUCCESS.equals(respCode)) {
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
			payFlow.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
			payFlow.setRouteDate(sysDate);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
			payFlow.setRouteSeq(zjSeq);
			daoService.update(payFlow);
			return true;
		} else {
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
			payFlow.setRouteSeq(zjSeq);
			payFlow.setRouteDate(sysDate);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
			daoService.update(payFlow);
			logger.error("中金代付接口返回[支付失败]");
			return false;
		}

	}

	private boolean reqHost(Map<String, Object> seqMap,
			Map<String, Object> parm, String transCode) {

		SimpleDateFormat SIM_YMD = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat SIM_HMS = new SimpleDateFormat("HHmmss");
		PayFlowListPo payFlow = new PayFlowListPo();
		payFlow.setTransSubSeq((String) seqMap.get("seq"));
		payFlow = daoService.selectOne(payFlow);

		Message message = MessageFactory.create(IdGenerateFactory.generateId(),
				"corecli", "XML", "UTF-8", MessageFactory.createSimpleMessage(
						new HashMap<String, Object>(),
						new HashMap<String, Object>()), FaultFactory.create(
						Constants.ResponseCode.SUCCESS, ""));
		Date now = new Date();
		Date date = SysInfoContext.getSysDate() == null ? now : SysInfoContext
				.getSysDate();
		Map<String, Object> bodyMap = (Map<String, Object>) message.getTarget()
				.getBodys();
		bodyMap.put("tranCode", transCode);// 交易代码
		bodyMap.put("channelId", "74");
		bodyMap.put("machineDate", SIM_YMD.format(date));// 交易日期
		bodyMap.put("machineTime", SIM_HMS.format(now));// 交易时间
		bodyMap.put("bizDate", SIM_YMD.format(date));// 业务日期
		bodyMap.put("bizSerialNo", seqMap.get("seq"));
		String amount = MoneyUtil
				.transferY2F(
						new BigDecimal(seqMap.get("transAmt").toString()).multiply(new BigDecimal(
								100)), 0).toString();
		bodyMap.put("amount", amount);
		if (CommonConstants_GNR.CORE_BANK_PAY_CODE_08001.equals(transCode)) {
			bodyMap.put("bankCardNo", parm.get("payErAccNo"));
			bodyMap.put("setAccount", parm.get("payEeAccNo"));
			bodyMap.put("trantype",
					CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);
			if (parm.get("isDebit") != null && (boolean) parm.get("isDebit")) {
				bodyMap.put("bankCardNo", parm.get("payEeAccNo"));
				bodyMap.put("setAccount", parm.get("payErAccNo"));
				bodyMap.put("orgBizDate", parm.get("orgBizDate"));
				bodyMap.put("orgBizSerialNo", parm.get("orgBizSerialNo"));
				bodyMap.put("trantype",
						CommonConstants_GNR.CORE_BANK_PAY_TYPE_REFUND);
			}
		} else if (CommonConstants_GNR.CORE_BANK_PAY_CODE_08011
				.equals(transCode)) {

			bodyMap.put("bankCardNo", parm.get("payEeAccNo"));
			bodyMap.put("setAccount", parm.get("payErAccNo"));
			bodyMap.put("trantype",
					CommonConstants_GNR.CORE_BANK_PAY_TYPE_REFUND);
			bodyMap.put("cvv2", parm.get("cvv2"));
			bodyMap.put("validdate", parm.get("validdate"));
			bodyMap.put("orgBizDate", parm.get("orgBizDate"));
			bodyMap.put("orgBizSerialNo", parm.get("orgBizSerialNo"));
		}
		try {
			message = coreCliDipperHandler.handle(message);
		} catch (Exception e) {
			logger.error("-------------调用核心记账接口服务返回异常!!!，错误码[{}],错误信息[{}]",
					message.getFault().getCode(), message.getFault().getMsg());
		}
		if (SysErrCode.SYS003.equals(message.getFault().getCode())) {
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);
			daoService.update(payFlow);
			return false;
		}

		if (!message.getFault().getCode().equals(Constants.ResponseCode.SUCCESS)) {
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
			daoService.update(payFlow);
			logger.error("-------------调用核心记账接口服务返回异常!!!，错误码[{}],错误信息[{}]",
					message.getFault().getCode(), message.getFault().getMsg());
			return false;
		}
		Map<String, Object> resp = (Map<String, Object>) message.getTarget()
				.getBodys();
		String bkDateStr = (String) resp.get("bkDate");
		Date bkDate = null;
		try {
			bkDate = new SimpleDateFormat("yyyyMMdd").parse(bkDateStr);
		} catch (ParseException e) {
			logger.error("-------------字符串解析日期失败");
		}
		String bkSeq = (String) resp.get("bkSerialNo");
		if (CommonConstants_GNR.T_PAY_BANK_HT_SUCCESS.equals(resp
				.get("respCode"))) {
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
			payFlow.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
			payFlow.setRouteDate(bkDate);
			payFlow.setRouteSeq(bkSeq);
			daoService.update(payFlow);
			return true;
		} else {
			payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
			payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
			payFlow.setRouteDate(bkDate);
			payFlow.setRouteSeq(bkSeq);
			daoService.update(payFlow);
			return false;
		}

	}

	private Map<String, Object> initFlowParm(String routeCode, String tranCode,
			Map<String, PayRouteInfoPo> routeMap,
			PayOrderListPo payOrderListPo, PayFlowListPo oriPayFlow) {
		Map<String, Object> map = new HashMap<String, Object>();
		PayRouteInfoPo hostRoute = routeMap
				.get(DataBaseConstants_PAY.ROUTE_CODE_HOST);
		PayRouteInfoPo zjRoute = routeMap
				.get(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
		if (DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)) {
			if ("2011".equals(tranCode)) {
				map.put("payErAccType",
						DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				map.put("payErAccNo", zjRoute.getTransAcctNo());
				map.put("payErAccName", zjRoute.getRouteName());
				map.put("payEeAccType",
						DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				map.put("payEeAccNo", hostRoute.getClrAcctNo());
				map.put("payEeAccName", hostRoute.getRouteName());
			} else if ("2521".equals(tranCode)) {
				map.put("payErAccType",
						DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				map.put("payErAccNo", hostRoute.getClrAcctNo());
				map.put("payErAccName", hostRoute.getRouteName());
				map.put("payEeAccType",
						DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				map.put("payEeAccNo", zjRoute.getTransAcctNo());
				map.put("payEeAccName", zjRoute.getRouteName());
			} else if ("CREDIT".equals(tranCode)) {
				map.put("payErAccType",
						DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				map.put("payErAccNo", hostRoute.getClrAcctNo());
				map.put("payErAccName", hostRoute.getRouteName());
				map.put("payEeAccType",
						DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD);
				map.put("payEeAccNo", oriPayFlow.getPayerAcctNo());
				map.put("payEeAccName", oriPayFlow.getPayerName());
			} else if ("DEBIT".equals(tranCode)) {
				map.put("payErAccType",
						DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				map.put("payErAccNo", hostRoute.getClrAcctNo());
				map.put("payErAccName", hostRoute.getRouteName());
				map.put("payEeAccType",
						DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD);
				map.put("payEeAccNo", oriPayFlow.getPayerAcctNo());
				map.put("payEeAccName", oriPayFlow.getPayerName());
			} else if ("PLATE".equals(tranCode)) {
				map.put("payErAccType",
						DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				map.put("payErAccNo", hostRoute.getClrAcctNo());
				map.put("payErAccName", hostRoute.getRouteName());
				map.put("payEeAccType",
						DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
				map.put("payEeAccNo", hostRoute.getTransAcctNo());
				map.put("payEeAccName", oriPayFlow.getPayerName());
			}
		} else if (DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routeCode)) {
			// 查询原交易的流水
			PayFlowListPo payFlowListPo = new PayFlowListPo();
			payFlowListPo.setOrderNo(payOrderListPo.getOriOrderNo());
			payFlowListPo
					.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
			payFlowListPo.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
			payFlowListPo = daoService.selectOne(payFlowListPo);

			// 中金快捷支付退款 不登记付款方
			map.put("payEeAccType", payFlowListPo.getPayerAcctType());
			map.put("payEeAccNo", payFlowListPo.getPayerAcctNo());
			map.put("payEeAccName", payFlowListPo.getPayerName());
			map.put("payEeBankName", payFlowListPo.getPayerBankName());
			map.put("payEeBankNo", payFlowListPo.getPayerBankNo());
			map.put("payUserId", payFlowListPo.getPayUserId());
			map.put("oriRouteSeq", payFlowListPo.getRouteSeq());
		} else{
			PayRouteInfoPo payRouteInfoPo = routeMap.get(routeCode);
			map.put("payErAccType",
					DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
			map.put("payErAccNo", hostRoute.getClrAcctNo());
			map.put("payErAccName", hostRoute.getRouteName());
			map.put("payEeAccType",
					DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
			map.put("payEeAccNo", payRouteInfoPo.getTransAcctNo());
			map.put("payEeAccName", payRouteInfoPo.getRouteName());
		}
		return map;
	}

	private Map<String, Object> insertFlowList(PayOrderListPo payOrderListPo,
			String routeCode, Map<String, Object> parm) {
		Map<String, Object> map = new HashMap<String, Object>();

		Date now = new Date();
		PayFlowListPo pay = new PayFlowListPo();
		pay.setOrderNo(payOrderListPo.getOrderNo());
		pay.setMerNo(payOrderListPo.getMerNo());
		pay.setSrFlag(DataBaseConstants_PAY.SR_FLAG_NOSTRO);
		pay.setSysDate(SysInfoContext.getSysDate());
		String seq = sequenceService.generatePayFlowSeq();
		pay.setTransSubSeq(seq);
		pay.setRouteCode(routeCode);
		if (!DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS.equals(routeCode)
				&& !DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(routeCode) 
				&& !DataBaseConstants_PAY.ROUTE_CODE_ALIPAY.equals(routeCode)) {
			pay.setPayerAcctNo((String) parm.get("payErAccNo"));
			pay.setPayerAcctType((String) parm.get("payErAccType"));
			pay.setPayerName((String) parm.get("payErAccName"));
			pay.setPayeeAcctNo((String) parm.get("payEeAccNo"));
			pay.setPayeeAcctType((String) parm.get("payEeAccType"));
			pay.setPayeeName((String) parm.get("payEeAccName"));
		}

		pay.setCcy(DataBaseConstants_PAY.T_CCY_CNY);
		// 目前不退手续费
		pay.setTransAmt(payOrderListPo.getTransAmt());
		pay.setTransTime(now);
		pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_BEGIN);
		pay.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
		pay.setLastUpdateTime(now);
		pay.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);

		if (DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routeCode)) {
			pay.setRouteSeq(CommonMethodUtils.getZJSubSeq());
			pay.setPayeeBankName((String) parm.get("payEeBankName"));
			pay.setPayeeBankNo((String) parm.get("payEeBankNo"));
			pay.setPayUserId((String) parm.get("payUserId"));
			map.put("zjSeq", pay.getRouteSeq());
		}

		HashMap<String, Object> parmMap = new HashMap<>();
		parmMap.put("orderNo", payOrderListPo.getOrderNo());
		Integer maxSeqNo = (Integer) daoService.selectOne(
				PayFlowListPo.class.getName() + ".findMaxSeqNo", parmMap);
		if (null == maxSeqNo || 0 == maxSeqNo) {
			maxSeqNo = 1;
		} else {
			maxSeqNo = maxSeqNo + 1;
		}

		pay.setSeqNo(maxSeqNo);
		daoService.insert(pay);
		map.put("transAmt", payOrderListPo.getTransAmt());
		map.put("seq", seq);// 平台流水号
		return map;
	}

	private List<PayOrderListPo> checkFlowList(List<PayOrderListPo> orderList) {
		List<PayOrderListPo> list = new ArrayList<PayOrderListPo>();
		for (PayOrderListPo payOrderListPo : orderList) {
			PayFlowListPo payFlow = new PayFlowListPo();
			payFlow.setOrderNo(payOrderListPo.getOrderNo());
			List<PayFlowListPo> payFlowList = daoService.selectList(payFlow);
			boolean flag = true;
			for (PayFlowListPo payFlowListPo : payFlowList) {
				if (DataBaseConstants_PAY.T_PAY_TX_UNKNOWN.equals(payFlowListPo
						.getTransStat())
						|| DataBaseConstants_PAY.T_PAY_TX_PROING
								.equals(payFlowListPo.getTransStat())) {
					flag = false;
					break;
				}
			}
			if (flag) {
				list.add(payOrderListPo);
			}
		}
		return list;
	}

}
