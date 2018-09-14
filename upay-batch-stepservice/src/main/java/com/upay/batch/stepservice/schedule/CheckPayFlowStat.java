/**
 * 
 */
package com.upay.batch.stepservice.schedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.mer.MerTransTemplatePo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**
 * 同步订单和流水状态
 * 
 * @author shang 2016年10月9日
 */
public class CheckPayFlowStat extends
		AbstractStepExecutor<Object, PayFlowListPo> {
	@Resource
	ISequenceService sequenceService;
	private static final Logger log = LoggerFactory
			.getLogger(CheckPayFlowStat.class);
	private static final String String = null;
	private static final String ALIPAYUSEATROUTE = "ALIPAY_USE_AT_ROUTE";
	
	private static final String WECHATUSEATROUTE = "WECHAT_USE_AT_ROUTE";
	
	private static final String USRESBCORE="USR_ESB_CORE";

	private static Map<String, Object> whereMap = new HashMap<String, Object>();
	// private Set<String> orderNoList=new HashSet<String>();
	private SimpleDateFormat SIM_YMD = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat SIM_HMS = new SimpleDateFormat("HHmmss");

    @Resource
    private ISequenceService seqService;
	
	@Resource
	IDaoService daoService;
	// 使用的原子服务

	// 使用的gateway
	 @Resource(name="defaultWeiXinClientHandler")
	 IDipperHandler<Message> weixin;

	// 使用的gateway
	@Resource(name = "unionPayOrderQueryClientDipperHandlr")
	IDipperHandler<Message> atweixinQry;

	// 使用的gateway
	@Resource(name = "unionPayOrderRefundQueryClientDipperHandlr")
	IDipperHandler<Message> atweixinRefundQry;
	

	@Resource(name = "SA_ZJPAY_Pay2512Handler")
	IDipperHandler<Message> zj2512;
	@Resource(name = "SA_ZJPAY_Pay2522Handler")
	IDipperHandler<Message> zj2522;
	@Resource(name = "SA_ZJPAY_Pay1120Handler")
	IDipperHandler<Message> zj1120;
	@Resource(name = "SA_ZJPAY_Pay4532Handler")
	IDipperHandler<Message> zj4532;
	@Resource(name = "SA_ZJPAY_Pay2020Handler")
	IDipperHandler<Message> zj2020;
	@Resource(name = "SA_ZJPAY_Pay1132Handler")
	IDipperHandler<Message> zj1132;

	@Resource(name = "coreCliDipperHandler")
	IDipperHandler<Message> core;

	@Resource(name = "SA_PMT_UnionPayTranStatQuery")
	IDipperHandler<Message> unionQuery;

	@Resource(name = "unionPayTranQueryClientDipperHandler")
	IDipperHandler<Message> unionPayTranQueryClientDipperHandler;
	@Resource(name = "unionPayReceiveTranQueryClientDipperHandler")
	IDipperHandler<Message> unionPayReceiveTranQueryClientDipperHandler;

	@Resource(name = "AlipayQueryHandler")
	IDipperHandler<Message> alipayQuery;

	@Resource(name = "AlipayRefundQueryHandler")
	IDipperHandler<Message> alipayRefundQuery;

	@Resource(name = "AT_AlipayQueryHandler")
	IDipperHandler<Message> atalipayQuery;

	@Resource(name = "AT_AlipayRefundQueryHandler")
	IDipperHandler<Message> atalipayRefundQuery;

	@Resource(name = "esbCliDipperHandler")
	IDipperHandler<Message> esbCliDipperHandler;

	// TODO 如果使用了银联代收付，订单壮态同步需要修改 添加代收付 查询接口， 修改的方式最好还是灵活可控制 读取通道 配置 再来选择走中金还是银联

	public CheckPayFlowStat() {
		whereMap.put("statusA", DataBaseConstants_PAY.T_PAY_TX_UNKNOWN);
		whereMap.put("statusB", DataBaseConstants_PAY.T_PAY_TX_PROING);
	}

	@Override
	public int getTotalResult(BatchParams batchParams, Object object)
			throws BatchException {
		// orderNoList=new HashSet<String>();
		Object obj = daoService.count(
				PayFlowListPo.class.getName().concat(".getAllUnknowStatNum"),
				whereMap);
		int num = 0;
		if (obj instanceof Integer) {
			num = (Integer) obj;
		}
		// 获取需要补单的订单
		// Map<String,Object> mapSe=new HashMap<String,Object>();
		// mapSe.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_HOST);
		// mapSe.put("chkFlag", DataBaseConstants_PAY.T_CHK_STAT_PRE);
		// mapSe.put("transStat", DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
		// List<String>
		// list=daoService.selectList(PayFlowListPo.class.getName().concat(".getOrderNoMend"),
		// mapSe);
		// if(list!=null&&list.size()>0){
		// orderNoList.addAll(list);
		// }
		// batchParams.getParameter().put("orderNo", orderNoList);
		return num;
	}

	@Override
	public List<PayFlowListPo> getDataList(BatchParams batchParams, int offset,
			int pageSize, Object object) throws BatchException {
		Object pay = daoService.selectList(PayFlowListPo.class.getName()
				.concat(".getAllUnknowStat"), whereMap, offset, pageSize);
		List<PayFlowListPo> payList = null;
		if (pay != null && pay instanceof List) {
			payList = (List<PayFlowListPo>) pay;
		}
		return payList;
	}

	@Override
	public void execute(BatchParams batchParams, int index, PayFlowListPo pay,
			Object object) throws BatchException {
		log.debug("同步订单状态------订单号为：" + pay.getOrderNo() + "     平台流水号："
				+ pay.getTransSubSeq() + "    平台状态：" + pay.getTransStat()
				+ "    第三方流水号：" + pay.getRouteSeq() + "   第三方状态："
				+ pay.getRouteTransStat());
		// 查询需要验证查询的资金通道
		Map<String, Object> map = this.getRouteCode(pay);
		log.debug("查询资金通道   -----------------");
		map.put("transSubSeq", pay.getTransSubSeq());
		String routeCode = map.get("routeCode").toString();
		if (StringUtils.isNotBlank(routeCode)) {
			String orderStatus = null;
			String payFlowStatus = null;
			String clrFlag = null;
			// 根据不同的资金通道访问不同的外部接口进行查询，根据查询结果确认流水状态和订单状态
			// 核心
			if (DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)) {
				String flag=getAtParm(USRESBCORE);
				String status="";
				if(flag.equals("Y")){
				  status = this.queryEsbCoreBank(pay, map);
				}else{
				 status = this.queryCoreBank(pay, map);
				}
				log.debug("调用核心查询接口返回   -----------------" + status);
				if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map
						.get("transType"))) {
					if (CommonConstants_GNR.CORE_BANK_PAY_STAT_SUCCESS
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
						clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
					}
					if (CommonConstants_GNR.CORE_BANK_PAY_STAT_NO
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
					}

				} else {
					if (CommonConstants_GNR.CORE_BANK_PAY_STAT_SUCCESS
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
						clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
					}
					if (CommonConstants_GNR.CORE_BANK_PAY_STAT_NO
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
					}
				}
			}
			// 中金
			if (DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routeCode)) {
				if (StringUtils.isBlank(pay.getRouteSeq())) {
					logger.error("流水[{}]的通道流水号不能为空" + pay.getTransSubSeq());
					return;
				}
				map.put("routeSeq", pay.getRouteSeq());
				Map<String, String> result = this.queryZJBank(map);
				if (result != null && result.get("status") != null
						&& !"".equals(result.get("status"))) {
					String status = result.get("status");
					if (CmparmConstants.GATEWAY_ZJPAY_2512.equals(result
							.get("interface"))) {
						if (CommonConstants_GNR.ZJ_Q_PAY_STAT_ING
								.equals(status)) {
							orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING;
							payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_PROING;
						}
						if (CommonConstants_GNR.ZJ_Q_PAY_STAT_SUCCESS
								.equals(status)) {
							orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y;
							payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
							clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
						}
						if (CommonConstants_GNR.ZJ_Q_PAY_STAT_FAIL
								.equals(status)) {
							orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL;
							payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
						}
					}
					if (CmparmConstants.GATEWAY_ZJPAY_2522.equals(result
							.get("interface"))) {
						if (CommonConstants_GNR.ZJ_Q_REFUND_STAT_BIGIN
								.equals(status)
								|| CommonConstants_GNR.ZJ_Q_REFUND_STAT_ING
										.equals(status)) {
							orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING;
							payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_PROING;
						}
						if (CommonConstants_GNR.ZJ_Q_REFUND_STAT_SUCCESS
								.equals(status)) {
							orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC;
							payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
							clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
						}
						if (CommonConstants_GNR.ZJ_Q_REFUND_STAT_FAIL
								.equals(status)) {
							orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL;
							payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
						}
					}
					if (CmparmConstants.GATEWAY_ZJPAY_1132.equals(result
							.get("interface"))) {
						if (CommonConstants_GNR.ZJ_Q_1132_STAT_BIGIN
								.equals(status)
								|| CommonConstants_GNR.ZJ_Q_1132_STAT_ING
										.equals(status)) {
							orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING;
							payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_PROING;
						}
						if (CommonConstants_GNR.ZJ_Q_1132_STAT_SUCCESS
								.equals(status)) {
							orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC;
							payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
							clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
						}
						if (CommonConstants_GNR.ZJ_Q_1132_STAT_FAIL
								.equals(status)) {
							orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL;
							payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
						}
					}
					if (CmparmConstants.GATEWAY_ZJPAY_4532.equals(result
							.get("interface"))
							|| CmparmConstants.GATEWAY_ZJPAY_2020.equals(result
									.get("interface"))) {
						if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map
								.get("transType"))) {
							if (CommonConstants_GNR.ZJ_C_PAY_STAT_ING
									.equals(status)) {
								orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING;
								payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_PROING;
							}
							if (CommonConstants_GNR.ZJ_C_PAY_STAT_SUCCESS
									.equals(status)) {
								orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC;
								payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
								clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
							}
							if (CommonConstants_GNR.ZJ_C_PAY_STAT_FAIL
									.equals(status)) {
								orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL;
								payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
							}
						} else {
							if (CommonConstants_GNR.ZJ_C_PAY_STAT_ING
									.equals(status)) {
								orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING;
								payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_PROING;
							}
							if (CommonConstants_GNR.ZJ_C_PAY_STAT_SUCCESS
									.equals(status)) {
								orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y;
								payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
								clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
							}
							if (CommonConstants_GNR.ZJ_C_PAY_STAT_FAIL
									.equals(status)) {
								orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL;
								payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
							}
						}
					}
				} else {
					orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING;
					payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_UNKNOWN;
				}
			}
			// 微信
			if (DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS.equals(routeCode)) {
				String status = null;
				String isAtFlay=getAtParm(WECHATUSEATROUTE);
				if(isAtFlay.equals(DataBaseConstants_PAY.DIRECT)){//微信直接
					status = this.queryWEIXIN(map);
				}else if(isAtFlay.equals(DataBaseConstants_PAY.UNIONPAY)){//微信走银联AT
					status = this.queryAtWEIXIN(map);
				}else if(isAtFlay.equals(DataBaseConstants_PAY.NETUNION)){//微信走网联
					
				}
				// 退款查询
				if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map
						.get("transType"))) {
					if (StringUtils.isBlank(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_UNKNOWN;
					} else if (CommonBaseConstans_PAY.WEIXIN_REFUND_SUCCESS
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
						clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
					} else if (CommonBaseConstans_PAY.WEIXIN_REFUND_FAIL
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
					} else if (CommonBaseConstans_PAY.WEIXIN_REFUND_PROCESSIONG
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_PROING;
					} else if (CommonBaseConstans_PAY.WEIXIN_REFUND_CHANGE
							.equals(status)) {
						ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "微信退款转入代发");
					}
					// 支付查询
				} else {
					if (StringUtils.isBlank(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_UNKNOWN;
						// 支付成功
					} else if (CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_SUCCESS
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
						clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
						// 已转入退款
					} else if (CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_REFUND
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
						clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
						// 用户支付中
					} else if (CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_USERPAYING
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_PROING;
					} else if (CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_CLOSED
							.equals(status)
							|| CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_REVOKED
									.equals(status)
							|| CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_PAYERROR
									.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
					} else if (CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_NOTPAY
							.equals(status)) {
						// 判断订单时间，如果订单没有支付，验证订单日期，今天不处理，今天之前失败处理
						Date now = new Date();
						Date transDate = null;
						SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
						try {
							now = sim.parse(sim.format(now));
							transDate = sim.parse(sim.format(pay.getSysDate()));
						} catch (ParseException e) {
							logger.error("Exception[{}]" + e);
						}
						if (now.getTime() > transDate.getTime()) {
							orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL;
							payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
						} else {
							// 不处理
							return;
						}
					} else {
						// 当前流水不做处理，直接结束当前流水的execute
						return;
					}
				}
			}
			// 银联
			if (DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(routeCode)) {
				String status = this.queryUNIONPAY(map);
				if (StringUtils.isBlank(status)) {
					return;
					// 支付成功
				} else if (DataBaseConstants_PAY.UNION_STAT_SUCC.equals(status)
						|| DataBaseConstants_PAY.UNION_STAT_LESS_SUCC
								.equals(status)) {
					if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map
							.get("transType"))) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC;
					} else {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y;
					}
					payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
					clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
				} else if (checkUnionStat(status)) {
					if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map
							.get("transType"))) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING;
					} else {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING;
					}
					payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_PROING;
				} else {
					if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map
							.get("transType"))) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL;
					} else {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL;
					}
					payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
				}
			}
			// 支付宝
			if (DataBaseConstants_PAY.ROUTE_CODE_ALIPAY.equals(routeCode)) {
				String status = this.queryAlipay(map);
				if (StringUtils.isBlank(status)) {
					return;
					// 支付成功
				} else if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(map
						.get("transType"))) {

					if (DataBaseConstants_PAY.ALIPAY_STATUS_TRADE_SUCCESS
							.equals(status)
							|| DataBaseConstants_PAY.ALIPAY_STATUS_TRADE_FINISHED
									.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
						clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
					} else if (DataBaseConstants_PAY.ALIPAY_STATUS_TRADE_CLOSED
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
					} else if (DataBaseConstants_PAY.ALIPAY_STATUS_WAIT_BUYER_PAY
							.equals(status)) {
						return;
					}

				} else if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map
						.get("transType"))) {
					if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_SUCCESS;
						clrFlag = DataBaseConstants_BATCH.T_CLEAR_FLAG_READY;
					} else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL
							.equals(status)) {
						orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL;
						payFlowStatus = DataBaseConstants_PAY.T_PAY_TX_FAL;
					} else {
						return;
					}
				} else {
					return;
				}
			}

			// 更改订单状态和流水状态
			if (orderStatus != null && payFlowStatus != null) {
				String routStat = null;
				String clrType = null;
				if (DataBaseConstants_PAY.T_PAY_TX_FAL.equals(payFlowStatus)) {
					routStat = CommonConstants_GNR.OUT_PAY_STAT_FAIL;
				} else if (DataBaseConstants_PAY.T_PAY_TX_SUCCESS
						.equals(payFlowStatus)) {
					// 同步状态成功后，纳入补单列表
					// if(!DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)){
					// orderNoList.add(pay.getOrderNo());
					// }
					routStat = CommonConstants_GNR.OUT_PAY_STAT_SUCCESS;
					clrType = DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC;
				} else if (DataBaseConstants_PAY.T_PAY_TX_UNKNOWN
						.equals(payFlowStatus)
						|| DataBaseConstants_PAY.T_PAY_TX_PROING
								.equals(payFlowStatus)) {
					// 如果当前的第三方流水状态还是处理中或未知时，直接返回不操作
					return;
				}

				PayFlowListPo payParam = new PayFlowListPo();
				payParam.setTransStat(payFlowStatus);
				if (payFlowStatus != null) {
					payParam.setRouteTransStat(routStat);
				}
				if (pay.getRouteDate() == null) {
					payParam.setRouteDate(pay.getSysDate());
				}
				if (!DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY
						.equals(routeCode) && map.containsKey("routeSeq")) {
					payParam.setRouteSeq((String) map.get("routeSeq"));
				}
				if (DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS
						.equals(routeCode) && map.containsKey("settleKey")) {
					payParam.setSettleKey((String) map.get("settleKey"));
				}
				payParam.setClearFlag(clrType);
				PayFlowListPo payWhere = new PayFlowListPo();
				payWhere.setTransSubSeq(pay.getTransSubSeq());
				payWhere.setTransStat(pay.getTransStat());
				int payNum = daoService.update(payParam, payWhere);
				String currentOrderStat = (String) map.get("orderStat");

				PayOrderListPo orderParam = new PayOrderListPo();
				if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING
						.equals(currentOrderStat)
						|| DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING
								.equals(currentOrderStat)) {
					// 当前订单状态为支付中或退款中 需要修改订单状态
					if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y
							.equals(orderStatus)) {
						if (DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE
								.equals(map.get("payServiceType"))) {
							orderStatus = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP;
						}
					}
					orderParam.setOrderStat(orderStatus);
				}

				if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP
						.equals(orderStatus)
						|| DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP
								.equals(orderStatus)
						|| DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y
								.equals(orderStatus)
						|| DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC
								.equals(orderStatus)) {
					if (CommonConstants_GNR.SYS_TRANS_TYPE_REF
							.equals((String) map.get("transType"))) {
						PayOrderListPo payOrderListPo = new PayOrderListPo();
						payOrderListPo.setOrderNo(pay.getOrderNo());
						payOrderListPo = daoService.selectOne(payOrderListPo);
						PayOrderListPo oriOrder = new PayOrderListPo();
						oriOrder.setOrderNo(payOrderListPo.getOriOrderNo());
						oriOrder = daoService.selectOne(oriOrder);
						if (oriOrder.getTransAmt().compareTo(
								payOrderListPo.getTransAmt()) == 0) {
							oriOrder.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
						} else {
							// 如果原订单状态为退款中，则需要更新累计退款金额
							if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING
									.equals(payOrderListPo.getOrderStat())) {
								BigDecimal ejectAmt = oriOrder.getEjectAmt() == null ? new BigDecimal(
										"0") : oriOrder.getEjectAmt();
								oriOrder.setEjectAmt(ejectAmt
										.add(payOrderListPo.getTransAmt()));
							}
							if (oriOrder.getEjectAmt().compareTo(
									oriOrder.getTransAmt()) == 0) {
								oriOrder.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
							} else {
								oriOrder.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
							}
						}
						daoService.update(oriOrder);
					}
				}
				String transType = (String) map.get("transType");
				if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(transType)) {
					// 如果是支付，第一条流水的routeCode就是支付渠道
					PayFlowListPo payFlow = new PayFlowListPo();
					payFlow.setOrderNo(pay.getOrderNo());
					payFlow.setSeqNo(1);
					payFlow = daoService.selectOne(payFlow);
					if (payFlow == null) {
						return;
					}
					orderParam.setRouteCode(payFlow.getRouteCode());
				} else if (CommonConstants_GNR.SYS_TRANS_TYPE_REF
						.equals(transType)) {
					String oriOrderNO = (String) map.get("oriOrderNO");
					PayFlowListPo payFlow = new PayFlowListPo();
					payFlow.setOrderNo(oriOrderNO);
					payFlow.setSeqNo(1);
					payFlow = daoService.selectOne(payFlow);
					if (payFlow == null) {
						return;
					}
					orderParam.setRouteCode(payFlow.getRouteCode());
				}

				orderParam.setClearFlag(clrFlag);
				orderParam.setLastUpdateTime(new Date());

				// 如果订单原来的状态就是己清算，则不修改清算状态
				String clearFlag = (String) map.get("clearFlag");// 订单原始清算状态
				if (StringUtils.isNotBlank(clearFlag)
						&& ((DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC
								.equals(clearFlag) || DataBaseConstants_BATCH.T_CLEAR_FLAG_TWOSUC
								.equals(clearFlag)))) {
					orderParam.setClearFlag(clearFlag);
				}

				// 如果是隔日退款，更改订单清算状态为已清算
				if (map.get("oneDay") != null && !(boolean) map.get("oneDay")) {
					if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP
							.equals(orderStatus)
							|| DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP
									.equals(orderStatus)
							|| DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y
									.equals(orderStatus)
							|| DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC
									.equals(orderStatus)) {
						orderParam
								.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
					}
				}

				// 修改订单的清算状态
				PayOrderListPo orderWhere = new PayOrderListPo();
				orderWhere.setOrderNo(pay.getOrderNo());
				orderWhere.setOrderStat(map.get("orderStat").toString());
				int orderNum = daoService.update(orderParam, orderWhere);

				// 更新日累计限额 隔日退款不修改日累计
				if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP
						.equals(orderStatus)
						|| DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP
								.equals(orderStatus)
						|| DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y
								.equals(orderStatus)
						|| DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC
								.equals(orderStatus)) {
					String merNo = (String) map.get("merNo");

					// 当商户为空时，说明是本行调用，或都是平台的充值提现或转账
					if (StringUtils.isNotBlank(merNo)) {
						MerTransTemplatePo merTransTemplate = new MerTransTemplatePo();
						merTransTemplate.setMerNo(merNo);
						merTransTemplate = daoService
								.selectOne(merTransTemplate);
						if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY
								.equals(transType)) {
							merTransTemplate
									.setDailyAcmlativeAmt(merTransTemplate
											.getDailyAcmlativeAmt().add(
													pay.getTransAmt()));
							daoService.update(merTransTemplate);
						} else if (CommonConstants_GNR.SYS_TRANS_TYPE_REF
								.equals(transType)) {
							if (map.get("oneDay") != null
									&& (boolean) map.get("oneDay")) {
								merTransTemplate
										.setDailyAcmlativeAmt(merTransTemplate
												.getDailyAcmlativeAmt()
												.subtract(pay.getTransAmt()));
								daoService.update(merTransTemplate);
							}

						}
					}

				}

				// 如果同步订单状态后 结果为支付成功，则需要通知调用方系统的状态 代付冲正 代扣通知对方系统更新状态
				String chnlId = (String) map.get("chnlId");
				// orderStatus="3";
				if (CommonConstants_GNR.CHNL_ID_TELLER.equals(chnlId)) {
					if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL
							.equals(orderStatus)
							&& CommonConstants_GNR.TRANS_CODE_SINGLE_PAYMENT
									.equals(map.get("transCode"))) {
						// 代付失败 冲正 1:冲正
						coreReverseOrNotify(map, "1", orderStatus);
					} else {
						// 代收付成功 通知 2:通知
						coreReverseOrNotify(map, "2", orderStatus);
					}
				}

				// 更新余额
				if (payNum > 0 && orderNum > 0) {

					map.put("newOrderStat", orderStatus);
					map.put("orderNo", pay.getOrderNo());
					BigDecimal transAmt = pay.getTransAmt();
					String transCode = map.get("transCode") == null ? null
							: (String) map.get("transCode");
					// 提现时不成功需要退手续费。
					if (StringUtils.isNotBlank(transCode)
							&& DataBaseConstans_ACC.TRANS_CODE_WITHDRAW
									.equals(transCode)) {
						BigDecimal feeAmt = (BigDecimal) map.get("feeAmt");
						transAmt = MoneyUtil.add(transAmt, feeAmt,
								RoundingMode.HALF_UP);
					}
					map.put("transAmt", transAmt);
					this.getUpdateTransAmt(map);
				}
			}
		}

	}

	/**
	 * 根据交易类型进行余额的扣减
	 */
	private Map<String, Object> getUpdateTransAmt(Map<String, Object> map) {
		String addOrSub = "";
		// 支付
		if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(map.get("transType"))) {
			// 验证为虚拟账户支付
			if (DataBaseConstants_PAY.T_PAY_TYPE_PLATFORM_ACC.equals(map
					.get("payType"))) {
				// 订单状态需要是处理中状态
				if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING
						.equals(map.get("orderStat"))) {
					// 第一笔流水成功修改余额---交易失败，回退之前扣减的余额
					if (!(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y
							.equals(map.get("newOrderStat")) || DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP
							.equals(map.get("newOrderStat")))
							&& (int) map.get("paySize") == 0) {
						addOrSub = CommonBaseConstans_PAY.ACC_AMT_ADD;
					}
				}
			}
		}
		// 充值
		if (CommonConstants_GNR.SYS_TRANS_TYPE_REC.equals(map.get("transType"))) {
			// 订单状态需要是处理中状态
			if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING
					.equals(map.get("orderStat"))) {
				// 第一笔流水成功修改余额---交易 成功，增加账户余额
				if ((DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y
						.equals(map.get("newOrderStat")))
						&& (int) map.get("paySize") == 0) {
					addOrSub = CommonBaseConstans_PAY.ACC_AMT_ADD;
				}
			}
		}
		// 提现
		if (CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(map.get("transType"))) {
			// 订单状态需要是处理中状态
			if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING
					.equals(map.get("orderStat"))) {
				// 第一笔流水成功修改余额---交易失败，回退之前扣减的余额
				if (!(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y
						.equals(map.get("newOrderStat")))
						&& (int) map.get("paySize") == 0) {
					addOrSub = CommonBaseConstans_PAY.ACC_AMT_ADD;
				}
			}
		}
		// 转账（现只有，余额-余额，余额-本行卡）所以不考虑转出方成功转入方超时情况 trans code 为ACC8002接口为平台转账
		if (CommonConstants_GNR.SYS_TRANS_TYPE_TRA.equals(map.get("transType"))
				&& DataBaseConstans_ACC.TRANS_CODE_TRANSFER.equals(map
						.get("transCode"))) {
			// 订单状态需要是处理中状态
			if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING
					.equals(map.get("orderStat"))) {
				// 第一笔流水成功修改余额---交易失败，回退之前扣减的余额
				if (!(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y
						.equals(map.get("newOrderStat")))
						&& (int) map.get("paySize") == 0) {
					addOrSub = CommonBaseConstans_PAY.ACC_AMT_ADD;
				}
			}
		}
		// 退款
		if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map.get("transType"))) {
			// 订单状态需要是退款中状态
			if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING
					.equals(map.get("orderStat"))) {
				// 第一笔流 成功修改余额---退款成功，增加账户余额
				if (map.get("oriPayType") != null
						&& DataBaseConstants_PAY.T_PAY_TYPE_PLATFORM_ACC
								.equals(map.get("oriPayType"))) {
					if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC
							.equals(map.get("newOrderStat"))
							&& (int) map.get("paySize") == 0) {
						addOrSub = CommonBaseConstans_PAY.ACC_AMT_ADD;
					}
				}
				// 隔日退款----（根据现有业务，只考虑支付退款）-----
				if (!(boolean) map.get("oneDay")) {
					if (!DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC
							.equals(map.get("newOrderStat"))
							&& (int) map.get("paySize") == 0) {
						if (map.get("merNo") != null) {
							MerAcctInfoPo merAcc = new MerAcctInfoPo();
							merAcc.setMerNo(map.get("merNo").toString());
							merAcc = daoService.selectOne(merAcc);
							if (merAcc == null) {
								ExInfo.throwDipperEx("商户[{}]的结算账户不存在",
										map.get("merNo"));
							}
							// 判断商户结算账户类型是否为虚拟账户
							if (DataBaseConstants_PAY.ACCT_TYPE_EPAY
									.equals(merAcc.getStlAcctType())) {
								AccVbookPo acc = new AccVbookPo();
								acc.setVacctNo(merAcc.getStlAcctNo());
								acc = daoService.selectOne(acc);
								if (acc == null) {
									ExInfo.throwDipperEx(
											AppCodeDict.BISACC0012,
											merAcc.getStlAcctNo());
								}
								Map<String, Object> up = new HashMap<String, Object>();
								up.put("updateAmt", map.get("transAmt"));
								up.put("accNo", acc.getVacctNo());
								up.put("addOrSub",
										CommonBaseConstans_PAY.ACC_AMT_ADD);
								daoService.update(AccVbookPo.class.getName()
										.concat(".updateAccountAmtSub"), up);
							}
						}
					}
				}
			}
		}
		String userId = map.get("userId") == null ? null : map.get("userId")
				.toString();
		if (StringUtils.isNotBlank(addOrSub) && StringUtils.isNotBlank(userId)) {
			// 查询 需要退款的账户
			AccVbookPo acc = new AccVbookPo();
			acc.setUserId(userId);
			acc = daoService.selectOne(acc);
			if (acc == null) {
				ExInfo.throwDipperEx(AppCodeDict.BISMER0008);
			}
			Map<String, Object> up = new HashMap<String, Object>();
			up.put("updateAmt", map.get("transAmt"));
			up.put("accNo", acc.getVacctNo());
			up.put("addOrSub", addOrSub);
			daoService.update(
					AccVbookPo.class.getName().concat(".updateAccountAmtSub"),
					up);
		}

		return null;
	}

	/**
	 * 获取需要查证的资金通道和是否需要修改余额
	 * 
	 * @return
	 * @throws ParseException
	 */
	private Map<String, Object> getRouteCode(PayFlowListPo pay) {
		Map<String, Object> map = new HashMap<String, Object>();
		PayOrderListPo order = new PayOrderListPo();
		order.setOrderNo(pay.getOrderNo());
		order = daoService.selectOne(order);
		if (order == null) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, pay.getOrderNo());
		}
		// 修改当如果订单状态是己清算的情况下，订单的清算 状态不改变 这里设值，后面使用
		map.put("clearFlag", order.getClearFlag());

		if (order.getMerNo() != null) {
			map.put("merNo", order.getMerNo());
		}
		PayFlowListPo payFlow = new PayFlowListPo();
		payFlow.setOrderNo(pay.getOrderNo());
		payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
		List<PayFlowListPo> payList = daoService.selectList(payFlow);
		map.put("orderStat", order.getOrderStat());

		map.put("chnlId", order.getChnlId());
		map.put("orderNo", order.getOrderNo());
		map.put("outOrderNo", order.getOuterOrderNo());
		if (null != order.getOrderDate()) {
			map.put("orderDate", DateUtil.format(order.getOrderDate(),
					DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD));
		}
		map.put("transCode", order.getTransCode());

		map.put("paySize", payList == null ? 0 : payList.size());
		map.put("routeCode", pay.getRouteCode());
		map.put("transType", order.getTransType());
		map.put("hasFee", order.getMerFeeAmt() == null ? false : (order
				.getMerFeeAmt().compareTo(BigDecimal.ZERO) > 0 ? true : false));

		// 查找订单商户的微信识别码
		MerBaseInfoPo mer = new MerBaseInfoPo();
		if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(order.getTransType())) {
			if (StringUtils.isBlank(order.getOriOrderNo())) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "原支付订单号");
			}
			PayOrderListPo oriOrder = new PayOrderListPo();
			oriOrder.setOrderNo(order.getOriOrderNo());
			oriOrder = daoService.selectOne(oriOrder);
			if (oriOrder == null) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0020,
						order.getOriOrderNo());
			}
			map.put("oriOrderNO", order.getOriOrderNo());
			map.put("oriPayType", oriOrder.getPayType());
			try {
				map.put("oneDay",
						SIM_YMD.parse(SIM_YMD.format(order.getOrderDate()))
								.getTime() == SIM_YMD.parse(
								SIM_YMD.format(oriOrder.getOrderDate()))
								.getTime());
			} catch (ParseException e) {
				logger.error("Exception[{}]" + e);
			}
			map.put("userId", oriOrder.getUserId());
			if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(oriOrder
					.getTransType())) {
				mer.setMerNo(oriOrder.getMerNo());
			}
			if (DataBaseConstants_PAY.T_PAY_TYPE_WEIXIN_QR_CODE.equals(oriOrder
					.getPayType())
					|| DataBaseConstants_PAY.T_PAY_TYPE_PUBLIC_NO
							.equals(oriOrder.getPayType())
					|| DataBaseConstants_PAY.T_PAY_TYPE_CARD_PAY
							.equals(oriOrder.getPayType())) {
				if (StringUtils.isBlank(mer.getMerNo())) {
					ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单中商户号");
				}
				mer = daoService.selectOne(mer);
				if (mer == null) {
					ExInfo.throwDipperEx(AppCodeDict.BISMER0018,
							order.getMerNo());
				}
				if (StringUtils.isNotBlank(mer.getSubMchId())) {
					map.put("subMchId", mer.getSubMchId());
				}
			}
		} else {
			if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(order
					.getTransType())) {
				mer.setMerNo(order.getMerNo());
			}
			map.put("userId", order.getUserId());
		}
		// 查询子商户的公众账号ID
		if (DataBaseConstants_PAY.T_PAY_TYPE_WEIXIN_QR_CODE.equals(order
				.getPayType())
				|| DataBaseConstants_PAY.T_PAY_TYPE_PUBLIC_NO.equals(order
						.getPayType())
				|| DataBaseConstants_PAY.T_PAY_TYPE_CARD_PAY.equals(order
						.getPayType())) {
			if (StringUtils.isBlank(mer.getMerNo())) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单中商户号");
			}
			mer = daoService.selectOne(mer);
			if (mer == null) {
				ExInfo.throwDipperEx(AppCodeDict.BISMER0018, order.getMerNo());
			}
			if (StringUtils.isNotBlank(mer.getSubMchId())) {
				map.put("subMchId", mer.getSubMchId());
			}
		}
		if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(order.getTransType())) {
			/*
			 * if(!(DataBaseConstants_PAY.T_PAY_TYPE_PUBLIC_NO.equals(order.
			 * getPayType
			 * ())||DataBaseConstants_PAY.T_PAY_TYPE_CARD_PAY.equals(order
			 * .getPayType()))
			 * &&(map.get("userId")==null||"".equals(map.get("userId")))){
			 * ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单的用户ID"); }
			 */
			if (StringUtils.isBlank(order.getPayServicType())) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付服务类型");
			}
			map.put("payServiceType", order.getPayServicType());
		}
		map.put("payType", order.getPayType());
		map.put("transCode", order.getTransCode());
		map.put("feeAmt", order.getFeeAmt());
		map.put("transTime", pay.getTransTime());
		return map;
	}

	/**
	 * 访问核心单笔查询接口
	 * 
	 * @param pay
	 * @param map
	 * @throws Exception
	 */
	public String queryCoreBank(PayFlowListPo pay, Map<String, Object> map) {
		Date now = new Date();
		Date date = SysInfoContext.getSysDate() == null ? now : SysInfoContext
				.getSysDate();
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		//核心时间 使用routeDate 如果为空则使用transDate
		String routeDate=SIM_YMD.format(pay.getRouteDate());
		if(StringUtils.isEmpty(routeDate)){
			routeDate=SIM_YMD.format(pay.getTransTime());
		}
		bodyMap.put("tranCode", CommonConstants_GNR.CORE_BANK_PAY_CODE_08003);// 交易代码
		bodyMap.put("channelId", "74");
		bodyMap.put("machineDate", SIM_YMD.format(date));// 交易日期
		bodyMap.put("machineTime", SIM_HMS.format(now));// 交易时间
		bodyMap.put("bizDate", SIM_YMD.format(date));// 业务日期
		bodyMap.put("bizSerialNo", pay.getTransSubSeq());// 业务流水号
		bodyMap.put("orgBizDate", routeDate);// 原正交易业务日期
		bodyMap.put("orgBizSerialNo", pay.getTransSubSeq());// 原正交易流水号
		Message message = this.getMessage(bodyMap);
		try {
			message = core.handle(message);
		} catch (Exception e) {
			logger.error("Exception[{}]" + e);
		}
		Map<String, Object> result = null;
		if (message.getTarget().getBodys() != null
				&& message.getTarget().getBodys() instanceof Map) {
			result = (Map<String, Object>) message.getTarget().getBodys();
		}
		if (result != null && result.get("status") != null) {
			map.put("routeSeq", result.get("bizSerialNo"));
			return result.get("status").toString();
		}
		return null;
	}
	
	
	/**
	 * 访问核心单笔查询接口
	 * 
	 * @param pay
	 * @param map
	 * @throws Exception
	 */
	public String queryEsbCoreBank(PayFlowListPo pay, Map<String, Object> map) {
		Date now = new Date();
		Date date = SysInfoContext.getSysDate() == null ? now : SysInfoContext
				.getSysDate();
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		//核心时间 使用routeDate 如果为空则使用transDate
		String routeDate=SIM_YMD.format(pay.getRouteDate());
		if(StringUtils.isEmpty(routeDate)){
			routeDate=SIM_YMD.format(pay.getTransTime());
		}
		bodyMap.put("transCode", CommonConstants_GNR.CORE_BANK_PAY_CODE_801006);// 交易代码
		bodyMap.put("svcCd", "30150004");
		bodyMap.put("svcScn", "02");
		bodyMap.put("channelId", "74");
		bodyMap.put("machineDate", SIM_YMD.format(date));// 交易日期
		bodyMap.put("machineTime", SIM_HMS.format(now));// 交易时间
		bodyMap.put("bizDate", SIM_YMD.format(date));// 业务日期
		bodyMap.put("bizSerialNo", seqService.generateEsbNo());// 业务流水号
		bodyMap.put("fileFlg", "0");
		
		bodyMap.put("qryTp", "3");
		bodyMap.put("origTxnDt", routeDate);// 原正交易业务日期
		bodyMap.put("origTxnSeqNo", pay.getTransSubSeq());// 原正交易流水号
		Message message = this.getMessage(bodyMap);
		try {
			message = esbCliDipperHandler.handle(message);
		} catch (Exception e) {
			logger.error("Exception[{}]" + e);
		}
		Map<String, Object> result = null;
		if (message.getTarget().getBodys() != null
				&& message.getTarget().getBodys() instanceof Map) {
			result = (Map<String, Object>) message.getTarget().getBodys();
		}
		 if(message.getFault().getCode().equals("0000000000")){
			map.put("routeSeq", result.get("SerSeqNo"));
			return "0";
		 }else{
			return "1";
		}
	}

	/**
	 * 访问中金单笔查询接口(暂时没有他行卡转账)
	 * 
	 * @param pay
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryZJBank(Map<String, Object> map) {
		if (map.get("routeSeq") == null || "".equals(map.get("routeSeq"))) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "中金通道流水号");
		}
		Map<String, Object> body = new HashMap<String, Object>();
		Message message = this.getMessage(body);
		body.put("institutionID", CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID); // 机构编号

		try {
			if ((CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(map
					.get("transType")) && DataBaseConstants_PAY.T_PAY_TYPE_OTHER_QUICK
					.equals(map.get("payType")))
					|| CommonConstants_GNR.SYS_TRANS_TYPE_REC.equals(map
							.get("transType"))) {
				body.put("_TRAN_CODE", CmparmConstants.GATEWAY_ZJPAY_2512);
				body.put("paymentNo", map.get("routeSeq")); // 支付交易流水号
				message = zj2512.handle(message);
			} else if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map
					.get("transType"))) {

				if (map.get("oneDay") != null && !(boolean) map.get("oneDay")) {
					PayFlowListPo payFlowList = new PayFlowListPo();
					payFlowList.setTransSubSeq((String) map.get("transSubSeq"));
					payFlowList = daoService.selectOne(payFlowList);
					if (DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT
							.equals(payFlowList.getPayeeAcctType())) {
						body.put(
								"institutionID",
								CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ENTERPRISE_FUND_COLLECTION); // 机构编号
						body.put("_TRAN_CODE",
								CmparmConstants.GATEWAY_ZJPAY_2020);
						body.put("txSN", map.get("routeSeq"));
						message = zj2020.handle(message);
					} else if (DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT
							.equals(payFlowList.getPayerAcctType())) {
						body.put(
								"institutionID",
								CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ONLINE_BANK); // 机构编号
						body.put("_TRAN_CODE",
								CmparmConstants.GATEWAY_ZJPAY_4532);
						body.put("txSN", map.get("routeSeq"));
						message = zj4532.handle(message);
					} else {
						if ((DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_PERSONAL
								.equals(map.get("oriPayType")) || DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_COMPANY
								.equals(map.get("oriPayType")))) {
							body.put(
									"institutionID",
									CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ONLINE_BANK); // 机构编号
							body.put("_TRAN_CODE",
									CmparmConstants.GATEWAY_ZJPAY_1132);
							body.put("serialNumber", map.get("routeSeq"));
							message = zj1132.handle(message);
						} else {
							body.put("_TRAN_CODE",
									CmparmConstants.GATEWAY_ZJPAY_2522);
							body.put("txSN", map.get("routeSeq"));
							message = zj2522.handle(message);
						}
					}
				} else {
					if ((DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_PERSONAL
							.equals(map.get("oriPayType")) || DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_COMPANY
							.equals(map.get("oriPayType")))) {
						body.put(
								"institutionID",
								CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ONLINE_BANK); // 机构编号
						body.put("_TRAN_CODE",
								CmparmConstants.GATEWAY_ZJPAY_1132);
						body.put("serialNumber", map.get("routeSeq"));
						message = zj1132.handle(message);
					} else {
						body.put("_TRAN_CODE",
								CmparmConstants.GATEWAY_ZJPAY_2522);
						body.put("txSN", map.get("routeSeq"));
						message = zj2522.handle(message);
					}
				}
			} else if (CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(map
					.get("transType"))
					|| DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_PAYMENT
							.equals(map.get("payType"))
					|| (DataBaseConstans_ACC.TRANS_CODE_SMOKE_TRANSFER
							.equals(map.get("transCode")) && CommonConstants_GNR.SYS_TRANS_TYPE_TRA
							.equals(map.get("transType")))) {
				body.put(
						"institutionID",
						CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ONLINE_BANK); // 机构编号
				body.put("_TRAN_CODE", CmparmConstants.GATEWAY_ZJPAY_4532);
				body.put("txSN", map.get("routeSeq"));
				message = zj4532.handle(message);
			} else if (DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_COLLECTION
					.equals(map.get("payType"))) {
				body.put(
						"institutionID",
						CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ENTERPRISE_FUND_COLLECTION); // 机构编号
				body.put("_TRAN_CODE", CmparmConstants.GATEWAY_ZJPAY_2020);
				body.put("txSN", map.get("routeSeq")); // 支付交易流水号
				message = zj2020.handle(message);
			}
		} catch (Exception e) {
			logger.error("Exception[{}]" + e);
		}

		Map<String, Object> result = null;
		if (message.getTarget().getBodys() != null
				&& message.getTarget().getBodys() instanceof Map) {
			result = (Map<String, Object>) message.getTarget().getBodys();
		}
		if (result != null && result.get("status") != null) {
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("interface", body.get("_TRAN_CODE").toString());
			resultMap.put("status", result.get("status").toString());

			return resultMap;
		}
		return null;
	}

	/**
	 * 访问微信单笔查询接口
	 * 
	 * @throws Exception
	 */
	public String queryAtWEIXIN(Map<String, Object> map) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("sysDate", SysInfoContext.getSysDate());
		body.put("sysTime", new Date());
		// 订单查询
		if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map.get("transType"))) {
			// body.put("tranCode", "REFUNDQUERY");
			// body.put("outRefundNo",map.get("transSubSeq") );
			body.put("tranCode", "ORDERQRYSINGLE");
			body.put("transSubSeq", map.get("transSubSeq"));

		} else {
			// body.put("outTradeNo",map.get("transSubSeq") );
			// body.put("tranCode", "ORDERQUERY");
			body.put("tranCode", "ORDERQUERY");
			body.put("transSubSeq", map.get("transSubSeq"));

		}
		PayRouteInfoPo route = new PayRouteInfoPo();
		route.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS);
		route = daoService.selectOne(route);
		if (route == null) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0028);
		}
		if (StringUtils.isBlank(route.getAppId())) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "微信 公众号ID");
		}
		if (StringUtils.isBlank(route.getOrgNo())) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "微信商户号");
		}
		body.put("appId", route.getAppId());
		body.put("mchId", route.getOrgNo());
		if (map.get("subMchId") != null && !"".equals(map.get("subMchId"))) {
			body.put("subMchId", map.get("subMchId"));
		}
		body.put("subAppid", "");
		body.put("deviceInfo", "");// 可以为空
		body.put("nonceStr", "");
		Message message = this.getMessage(body);
		try {
			if (body.get("tranCode").equals("ORDERQUERY")) {
				message = atweixinQry.handle(message);
			} else {
				message = atweixinRefundQry.handle(message);
			}

		} catch (Exception e) {
			logger.error("Exception[{}]" + e);
		}

		Map<String, Object> result = null;
		if (message.getTarget().getBodys() != null
				&& message.getTarget().getBodys() instanceof Map) {
			result = (Map<String, Object>) message.getTarget().getBodys();
		}

		if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map.get("transType"))) {
			if (DataBaseConstants_PAY.WEIXIN_RESULT_STATUS_FAIL.equals(result
					.get("resultCode"))) {
				return CommonBaseConstans_PAY.WEIXIN_REFUND_FAIL;
			}
			// if(result!=null&&result.get("refundInfoList")!=null){
			// List<Map<String,Object>> reList=(List<Map<String, Object>>)
			// result.get("refundInfoList");
			// if(reList!=null&&reList.size()>0){
			// for(Map<String,Object> reMap:reList){
			// if(reMap.containsKey("refundStatus")){
			// map.put("routeSeq", result.get("refundId"));
			// return reMap.get("refundStatus").toString();
			// }
			// }
			// }
			// }
			if (result != null && result.get("refundId") != null) {
				map.put("routeSeq", result.get("refundId"));
				return result.get("refundStatus").toString();
			}

		} else {
			if (DataBaseConstants_PAY.WEIXIN_RESULT_STATUS_FAIL.equals(result
					.get("resultCode"))) {
				return CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_PAYERROR;
			}
			if (result != null && result.get("tradeState") != null) {
				map.put("routeSeq", result.get("transactionId"));
				return result.get("tradeState").toString();
			}
		}
		return null;
	}

	/**
	 * 访问银联单笔查询接口
	 * 
	 * @throws Exception
	 */
	public String queryUNIONPAY(Map<String, Object> map) {
		Map<String, Object> body = new HashMap<String, Object>();
		String reqType = null;
		if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals((String) map
				.get("transType"))) {
			String oriPayType = (String) map.get("oriPayType");
			if (DataBaseConstants_PAY.T_PAY_TYPE_ACP_QR_CODE.equals(oriPayType)) {
				reqType = "0350000903";
			} else if (DataBaseConstants_PAY.T_PAY_TYPE_ACP_WR_CODE
					.equals(oriPayType)
					|| DataBaseConstants_PAY.T_PAY_TYPE_ACP_XWR_CODE
							.equals(oriPayType)) {
				reqType = "0540000903";
			} else if (DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_COLLECTION
					.equals(oriPayType)) {
				reqType = "000501";
			} else if (DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_PAYMENT
					.equals(oriPayType)) {
				reqType = "000401";
			} else if (DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_WTPAY
					.equals(oriPayType)) {
				reqType = "000902";
			}
		} else {
			String payType = (String) map.get("payType");
			if (DataBaseConstants_PAY.T_PAY_TYPE_ACP_QR_CODE.equals(payType)) {
				reqType = "0350000903";
			} else if (DataBaseConstants_PAY.T_PAY_TYPE_ACP_WR_CODE
					.equals(payType)
					|| DataBaseConstants_PAY.T_PAY_TYPE_ACP_XWR_CODE
							.equals(payType)) {
				reqType = "0540000903";
			} else if (DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_COLLECTION
					.equals(payType)) {
				reqType = "000501";
			} else if (DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_PAYMENT
					.equals(payType)) {
				reqType = "000401";
			} else if (DataBaseConstants_PAY.T_PAY_TYPE_SINGLE_WTPAY
					.equals(payType)) {
				reqType = "000902";
			}

		}
		Message message = null;
		if (reqType.equals("000501") || reqType.equals("000401")
				|| reqType.equals("000902")) {
			body.put("orderId", map.get("transSubSeq"));
			//Date transTime = (Date) map.get("transTime");
			//String transTimeStr = DateUtil.format(transTime, "yyyyMMddHHmmss");
			//body.put("txnTime", transTimeStr);
			//body.put("bizType", reqType);
			message = this.getMessage(body);
			try {
               if(reqType.equals("000902")){//无跳转支付
				   message = unionPayTranQueryClientDipperHandler.handle(message);
			   }
			   if(reqType.equals("000401")){//代付

			   }
			   if(reqType.equals("000501") ){//代收
				   message = unionPayReceiveTranQueryClientDipperHandler.handle(message);
			   }
			} catch (Exception e) {
				logger.error("Exception[{}]" + e);
			}
		} else {
			body.put("transSubSeq", map.get("transSubSeq"));
			Date transTime = (Date) map.get("transTime");
			String transTimeStr = DateUtil.format(transTime, "yyyyMMddHHmmss");
			body.put("orderTime", transTimeStr);
			body.put("reqType", reqType);
			message = this.getMessage(body);
			try {
				message = unionQuery.handle(message);
			} catch (Exception e) {
				logger.error("Exception[{}]" + e);
			}
		}
		Map<String, Object> result = null;
		if (message.getTarget().getBodys() != null
				&& message.getTarget().getBodys() instanceof Map) {
			result = (Map<String, Object>) message.getTarget().getBodys();
		}
		String respCode = null;
		String origRespCode = null;
		if (result.get("respCode") != null
				&& result.get("origRespCode") != null) {
			respCode = (String) result.get("respCode");
			origRespCode = (String) result.get("origRespCode");
			if (DataBaseConstants_PAY.UNION_STAT_SUCC.equals(respCode)
					&& DataBaseConstants_PAY.UNION_STAT_SUCC
							.equals(origRespCode)) {
				map.put("routeSeq", result.get("voucherNum"));
				map.put("settleKey", result.get("settleKey"));
				return origRespCode;
			}
		}
		if (result.get("respCode") != null) {
			return (String) result.get("respCode");
		}

		return null;
	}

	/**
	 * 调用支付宝查询接口
	 * 
	 * @throws Exception
	 */
	public String queryAlipay(Map<String, Object> map) {
		Map<String, Object> body = new HashMap<String, Object>();
		String transType = (String) map.get("transType");
		if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(transType)) {
			body.put("outTradeNo", map.get("transSubSeq"));
		} else {
			String oriOrderNO = (String) map.get("oriOrderNO");
			PayFlowListPo payFlowList = new PayFlowListPo();
			payFlowList.setOrderNo(oriOrderNO);
			payFlowList.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY);
			payFlowList.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
			payFlowList = daoService.selectOne(payFlowList);
			if (payFlowList == null) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0027, oriOrderNO,
						"支付宝0005");
			}
			body.put("outTradeNo", payFlowList.getTransSubSeq());
			body.put("outRequestNo", map.get("transSubSeq"));

		}
		Message message = this.getMessage(body);

		String isAtFlag = getAtParm(ALIPAYUSEATROUTE);
		try {
			if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(transType)) {
				if (isAtFlag.equals(DataBaseConstants_PAY.UNIONPAY)) {//支付宝走银联AT通道
					message = atalipayQuery.handle(message);
				} else if (isAtFlag.equals(DataBaseConstants_PAY.DIRECT)){//支付宝走直联
					message = alipayQuery.handle(message);
				}else if(isAtFlag.equals(DataBaseConstants_PAY.NETUNION)){//支付宝走网联
					
				}
			} else {
				if (isAtFlag.equals(DataBaseConstants_PAY.UNIONPAY)) {//支付宝走银联AT通道
					message = atalipayRefundQuery.handle(message);
				} else if (isAtFlag.equals(DataBaseConstants_PAY.DIRECT)){//支付宝走直联
					message = alipayQuery.handle(message);
				}else if(isAtFlag.equals(DataBaseConstants_PAY.NETUNION)){//支付宝走网联
					
				}
				
			}
		} catch (Exception e) {
			logger.error("Exception[{}]" + e);

		}
		Map<String, Object> result = null;
		if (message.getTarget().getBodys() != null
				&& message.getTarget().getBodys() instanceof Map) {
			result = (Map<String, Object>) message.getTarget().getBodys();
		}
		if (result != null && result.get("outCode") != null
				&& "ACQ.TRADE_NOT_EXIST".equals(result.get("outCode"))) {
			if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(transType)) {
				return DataBaseConstants_PAY.ALIPAY_STATUS_TRADE_CLOSED;
			} else {
				// 退款失败
				return DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL;
			}
		}
		if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(transType)) {
			if (result != null && result.get("tradeStatus") != null) {
				map.put("routeSeq", result.get("tradeNo"));
				return result.get("tradeStatus").toString();
			}
		} else {
			if (DataBaseConstants_PAY.PAY_ALIPAY_SUCC.equals((String) result
					.get("returnCode"))) {
				map.put("routeSeq", result.get("tradeNo"));
				if (result != null && result.get("totalAmount") != null
						&& result.get("refundAmount") != null) {
					// 退款成功
					return DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC;
				} else {
					// 退款失败
					return DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL;
				}
			}
		}
		return null;
	}

	public Message getMessage(Map<String, Object> body) {
		Message message = MessageFactory.create(IdGenerateFactory.generateId(),
				MessageFactory.createSimpleMessage(
						new HashMap<String, Object>(), body),
				com.pactera.dipper.core.factory.MessageFactory
						.createSimpleMessage(new HashMap<String, Object>(),
								body), FaultFactory.create(
						Constants.ResponseCode.SUCCESS, "success"),
				new LinkedList<Store>());
		return message;
	}

	public boolean checkUnionStat(String stat) {
		if (DataBaseConstants_PAY.UNION_STAT_CLOSE.equals(stat)
				|| DataBaseConstants_PAY.UNION_STAT_TIMEOUT.equals(stat)
				|| DataBaseConstants_PAY.UNION_STAT_UNKNOWN.equals(stat)
				|| DataBaseConstants_PAY.UNION_STAT_HANDLING.equals(stat)
				|| DataBaseConstants_PAY.UNION_STAT_BUSY.equals(stat)
				|| DataBaseConstants_PAY.UNION_STAT_REPEAT.equals(stat)
				|| DataBaseConstants_PAY.UNION_STAT_BANK_HANDLING.equals(stat)) {
			return true;
		}
		return false;

	}

	// 核心 代付支付失败冲正 代收代付接口成功 通知消息
	// flag 1:冲正 2:通知
	private String coreReverseOrNotify(Map<String, Object> map, String flag,
			String orderStatus) {
		Map<String, Object> body = new HashMap<String, Object>();
		Message message = this.getMessage(body);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD);
		SimpleDateFormat timeFormat = new SimpleDateFormat(
				DataBaseConstans_ACC.DATE_FORMAT_HHMMSS);
		Date date = new Date();
		String flagStr = "";
		String seq = sequenceService.generateEsbNo();
		body.put("bizSerialNo", seq);
		body.put("AuthFlg", "0");
		// 冲正
		if ("1".equals(flag)) {
			body.put("transCode", "818899");
			body.put("svcCd", "30140001");
			body.put("svcScn", "03");
			body.put("machineDate", timeFormat.format(date));
			body.put("machineTime", timeFormat.format(date));
			body.put("channelId", map.get("chnlId"));
			body.put("fileFlg", "0");

			body.put("OrigDate", map.get("orderDate")); // 原交易日期 yyyy-mm-dd
														// 订单交易日期
			body.put("OrigDevStan", map.get("outOrderNo")); // 本系统外部订单号
			flagStr = "(30140001)外围系统冲正";
		} else {
			// 核心代收付接口 支付成功 通知
			body.put("transCode", "471011");
			body.put("svcCd", "30220010");
			body.put("svcScn", "01");
			body.put("machineDate", dateFormat.format(date));
			body.put("machineTime", timeFormat.format(date));
			body.put("channelId", map.get("chnlId"));

			body.put("OrigTxnDt", map.get("orderDate"));// 本系统订单交易日期 yyyy-mm-dd
			body.put("SndSeqNo1", map.get("outOrderNo")); // 本系统外部订单号
			body.put("OrdrNo", map.get("orderNo")); // 本系统订单号
			body.put("ProcStatus", orderStatus);// 订单状态
			flagStr = "(30220010)贷款状态信息维护  他行贷款还款状态更新";
		}

		try {
			message = esbCliDipperHandler.handle(message);
		} catch (Exception e) {
			logger.error("Exception[{}]" + e);
		}

		Map<String, Object> result = null;
		if (message.getTarget().getBodys() != null
				&& message.getTarget().getBodys() instanceof Map) {
			result = (Map<String, Object>) message.getTarget().getBodys();
		}

		if (result != null && result.size() > 0) {
			String returnCode = (String) result.get("retCd");
			String returnMsg = (String) result.get("retMsg");
			// 判断错误码 是否返回调用成功
			if ("000000".equals(returnCode)) {
				log.info("订单号：" + map.get("orderNo") + " 调用  核心" + flagStr
						+ "接口成功!!!!");
			} else {
				log.error("订单号：" + map.get("orderNo") + " 调用  核心" + flagStr
						+ "接口失败!!!!");
			}
		} else {
			log.error("订单号：" + map.get("orderNo") + " 调用" + flagStr
					+ "接口失败!!!!");
		}
		return null;
	}

	private String getAtParm(String payRouteMethod) {
		GnrParmPo gnrParmPoPo = new GnrParmPo();
		if (StringUtils.isNotBlank(payRouteMethod)) {
			gnrParmPoPo.setParmId(payRouteMethod);
		} else {
			ExInfo.throwDipperEx(
					AppCodeDict.BISPAY0001,
					"请在pay_param中初始化 id为initPayMethod  的 payRouteMethod字段 值为 'WECHAT_USE_AT_ROUTE（微信是否使用银联AT通道）',  或者 'ALIPAY_USE_AT_ROUTE（支付宝是否使用银联AT通道）',    ");
		}
		gnrParmPoPo = daoService.selectOne(gnrParmPoPo);
		return gnrParmPoPo.getParmValue();
	}
	
	
	 /**
     * 访问微信单笔查询接口
     * @throws Exception 
     */
    public String queryWEIXIN(Map<String,Object> map) {
        Map<String,Object> body=new HashMap<String,Object>();
        body.put("sysDate", SysInfoContext.getSysDate());
        body.put("sysTime", new Date());
        // 订单查询
        if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map.get("transType"))){
            body.put("tranCode", "REFUNDQUERY");
            body.put("outRefundNo",map.get("transSubSeq") );
        }else{            
            body.put("outTradeNo",map.get("transSubSeq") );
            body.put("tranCode", "ORDERQUERY");
        }
        PayRouteInfoPo route=new PayRouteInfoPo();
        route.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS);
        route=daoService.selectOne(route);
        if(route==null){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0028);
        }
        if(StringUtils.isBlank(route.getAppId())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001,"微信 公众号ID");
        }
        if(StringUtils.isBlank(route.getOrgNo())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001,"微信商户号");
        }
        body.put("appId", route.getAppId());
        body.put("mchId", route.getOrgNo());
        if(map.get("subMchId")!=null&&!"".equals(map.get("subMchId"))){            
            body.put("subMchId", map.get("subMchId"));
        }
        body.put("subAppid", "");
        body.put("deviceInfo", "");// 可以为空
        body.put("nonceStr", "");      
        Message message = this.getMessage(body);
        try {
			message = weixin.handle(message);
		} catch (Exception e) {
			logger.error("Exception[{}]"+e);
		}
        
        Map<String,Object> result=null;
        if(message.getTarget().getBodys()!=null&&message.getTarget().getBodys() instanceof Map){            
            result=(Map<String, Object>) message.getTarget().getBodys();
        }
        
        if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(map.get("transType"))){
            if(DataBaseConstants_PAY.WEIXIN_RESULT_STATUS_FAIL.equals(result.get("resultCode"))){
                return CommonBaseConstans_PAY.WEIXIN_REFUND_FAIL;
            }
            if(result!=null&&result.get("refundInfoList")!=null){
                List<Map<String,Object>> reList=(List<Map<String, Object>>) result.get("refundInfoList");
                if(reList!=null&&reList.size()>0){
                    for(Map<String,Object> reMap:reList){
                        if(reMap.containsKey("refundStatus")){
                        	map.put("routeSeq", result.get("refundId"));
                            return reMap.get("refundStatus").toString();
                        }
                    }
                }
            }
        }else{            
            if(DataBaseConstants_PAY.WEIXIN_RESULT_STATUS_FAIL.equals(result.get("resultCode"))){
                return CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_PAYERROR;
            }
            if(result!=null&&result.get("tradeState")!=null){
            	map.put("routeSeq", result.get("transactionId"));
                return result.get("tradeState").toString();
            }
        }
        return null;
    }

}
