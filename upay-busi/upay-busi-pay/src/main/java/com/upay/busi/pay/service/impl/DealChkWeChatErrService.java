package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.pay.service.dto.DealChkWeChatErrDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.chk.ChkErrListPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;


/**
 * 处理与微信对账、中金对账产生的差错
 * 
 * @author yhy 20170516
 * 
 */
public class DealChkWeChatErrService extends AbstractDipperHandler<DealChkWeChatErrDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(DealChkWeChatErrService.class);
    @Resource
    private ISequenceService seqService;
    
	@Override
	public DealChkWeChatErrDto execute(DealChkWeChatErrDto dto, Message message)
			throws Exception {
		String dealType = dto.getDealType();
		String errFlowSeq = dto.getErrFlowSeq();
		String errRouteCode = dto.getErrRouteCode();
		SimpleDateFormat simp = new SimpleDateFormat("yyyyMMdd");
		if (StringUtils.isBlank(errFlowSeq)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "平台流水号");
        }
		if (StringUtils.isBlank(dealType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "差错处理方式");
        }
		if (StringUtils.isBlank(errRouteCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "通道代码");
        }
		PayFlowListPo payFlowList = new PayFlowListPo();
		payFlowList.setTransSubSeq(errFlowSeq);
		payFlowList = daoService.selectOne(payFlowList);
		//交易码
		dto.setCoreTranCode(CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
		//由于交易都是先记录流水然后再请求第三方接口，因此如果微信、中金多的话，支付平台必定有流水（目前生产和uat环境用同一个微信环境，要先排除uat的微信交易）
		if(payFlowList!=null){ 
			
			//中金或者微信通道信息
			PayRouteInfoPo otherRouteInfo = new PayRouteInfoPo();
			otherRouteInfo.setRouteCode(errRouteCode);
			otherRouteInfo = daoService.selectOne(otherRouteInfo);
			//核心通道信息
			PayRouteInfoPo coreRouteInfo = new PayRouteInfoPo();
			coreRouteInfo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
			coreRouteInfo = daoService.selectOne(coreRouteInfo);
			//该差错流水对应的订单信息
			PayOrderListPo payOrder = new PayOrderListPo();
			payOrder.setOrderNo(payFlowList.getOrderNo());
			payOrder = daoService.selectOne(payOrder);
			//微信多或者中金多
			if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_ADD.equals(dealType)){
				
				//查找核心流水，提现和隔日退款都会有两笔核心流水
				PayFlowListPo coreFlow = new PayFlowListPo();
				coreFlow.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
				coreFlow.setOrderNo(payFlowList.getOrderNo());
				coreFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
				List<PayFlowListPo> coreFlowList = daoService.selectList(coreFlow);
				String paySeq = seqService.generatePayFlowSeq();
				if(coreFlowList.size() == 0){
					if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(errRouteCode)){
						GnrParmPo parmPo=new GnrParmPo();
		            	parmPo.setParmId(DataBaseConstans_ACC.ZJ_ACCT_NO);
		            	parmPo=daoService.selectOne(parmPo);
		            	//如果订单为空的话，则是商户结算时产生的流水
		            	if(payOrder == null){
							if(payFlowList.getPayerAcctNo().equals(parmPo.getParmValue())){
								dto.setPayErAcctNo(coreRouteInfo.getClrAcctNo());
								dto.setPayEeAcctNo(otherRouteInfo.getTransAcctNo());
							}else if(payFlowList.getPayeeAcctNo().equals(parmPo.getParmValue())){
								dto.setPayErAcctNo(otherRouteInfo.getTransAcctNo());
								dto.setPayEeAcctNo(coreRouteInfo.getClrAcctNo());
							}
						}else{
							//退款
							if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(payOrder.getTransType())){
								//当日退款 (隔日退款需手工处理)
								dto.setPayErAcctNo(coreRouteInfo.getClrAcctNo());
								dto.setPayEeAcctNo(otherRouteInfo.getTransAcctNo());
								
								//提现
							}else if(CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(payOrder.getTransType())){
								
								if(payOrder.getFeeAmt() != null && payOrder.getFeeAmt().compareTo(new BigDecimal("0"))>0){
									//查询是否存在手续费的成功流水
									PayFlowListPo feeFlowList = new PayFlowListPo();
									feeFlowList.setOrderNo(payOrder.getOrderNo());
									feeFlowList.setPayerAcctNo(coreRouteInfo.getTransAcctNo());
									feeFlowList.setPayeeAcctNo(coreRouteInfo.getClrAcctNo());
									feeFlowList.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
									feeFlowList = daoService.selectOne(feeFlowList);
									if(feeFlowList == null){
										dto.setIsAddFee("Y");//是否补手续费 Y-补 N-冲
									}
								}
								dto.setPayErAcctNo(coreRouteInfo.getTransAcctNo());
								dto.setPayEeAcctNo(otherRouteInfo.getTransAcctNo());
								//支付
							}else if(CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(payOrder.getTransType())){
								dto.setPayErAcctNo(otherRouteInfo.getTransAcctNo());
								dto.setPayEeAcctNo(coreRouteInfo.getClrAcctNo());
								//转账
							}else if(CommonConstants_GNR.SYS_TRANS_TYPE_TRA.equals(payOrder.getTransType())){
								MerAcctInfoPo merAcctInfoPo = new MerAcctInfoPo();
								merAcctInfoPo.setMerNo(payFlowList.getMerNo());
								merAcctInfoPo = daoService.selectOne(merAcctInfoPo);
								
								dto.setPayErAcctNo(merAcctInfoPo.getStlAcctNo());
								dto.setPayEeAcctNo(otherRouteInfo.getTransAcctNo());
								//充值
							}else if(CommonConstants_GNR.SYS_TRANS_TYPE_REC.equals(payOrder.getTransType())){
								dto.setPayErAcctNo(otherRouteInfo.getTransAcctNo());
								dto.setPayEeAcctNo(coreRouteInfo.getTransAcctNo());
							}
						}
					}else{
						if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(payOrder.getTransType())){
							dto.setPayErAcctNo(coreRouteInfo.getClrAcctNo());
							dto.setPayEeAcctNo(otherRouteInfo.getTransAcctNo());
						}else{
							dto.setPayErAcctNo(otherRouteInfo.getTransAcctNo());
							dto.setPayEeAcctNo(coreRouteInfo.getClrAcctNo());
						}
					}
					
					//登记流水
					insertPayFlowList(dto.getPayErAcctNo(),dto.getPayEeAcctNo(),payFlowList,paySeq);
					dto.setTranType(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);
					dto.setTranAmt(MoneyUtil.transferY2F(payFlowList.getTransAmt().multiply(new BigDecimal(100)), 0).toString());
					dto.setCoreSubSeq(paySeq);
					dto.setTranDate(SysInfoContext.getSysDate());
					dto.setIsReqCore("Y");
				}else{
					dto.setIsReqCore("N"); 
					return dto;
				}
					
				//微信少、中金少
			}else if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_RUS.equals(dealType)){
				
				PayFlowListPo payFlowListPo = new PayFlowListPo();
				payFlowListPo.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
				payFlowListPo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
				payFlowListPo.setOrderNo(payFlowList.getOrderNo());
				List<PayFlowListPo> succList = daoService.selectList(payFlowListPo);
				//如果没有成功的核心流水，直接修改流水状态
				if(succList.size() == 0){
					dto.setIsReqCore("N");
				}else{
					//冲账的话，如果有手续费，需要冲掉手续费
					if(CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(payOrder.getTransType())){
						if(payOrder.getFeeAmt() != null && payOrder.getFeeAmt().compareTo(new BigDecimal("0"))>0){
							PayFlowListPo feeFlowList = new PayFlowListPo();
							feeFlowList.setOrderNo(payOrder.getOrderNo());
							feeFlowList.setPayerAcctNo(coreRouteInfo.getTransAcctNo());
							feeFlowList.setPayeeAcctNo(coreRouteInfo.getClrAcctNo());
							feeFlowList.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
							feeFlowList = daoService.selectOne(feeFlowList);
							if(feeFlowList != null){
								dto.setIsAddFee("N");//是否补手续费  Y-补 N-冲
								String tranSeq = feeFlowList.getTransSubSeq();
								for (PayFlowListPo pay : succList) {
									if(!tranSeq.equals(pay.getTransSubSeq())){
										dto.setBankCardNo(pay.getPayerAcctNo());
										dto.setSetAccount(pay.getPayeeAcctNo());
										dto.setAmount(MoneyUtil.transferY2F(pay.getTransAmt().multiply(new BigDecimal(100)), 0).toString());
										dto.setOrgBizDate(simp.format(pay.getSysDate()));
										dto.setOrgBizSerialNo(pay.getTransSubSeq());
										break;
									}
								}
								
							}else{
								PayFlowListPo flow = succList.get(0);
								dto.setBankCardNo(flow.getPayerAcctNo());
								dto.setSetAccount(flow.getPayeeAcctNo());
								dto.setAmount(MoneyUtil.transferY2F(flow.getTransAmt().multiply(new BigDecimal(100)), 0).toString());
								dto.setOrgBizDate(simp.format(flow.getSysDate()));
								dto.setOrgBizSerialNo(flow.getTransSubSeq());
							}
							
						}else{
							PayFlowListPo flow = succList.get(0);
							dto.setBankCardNo(flow.getPayerAcctNo());
							dto.setSetAccount(flow.getPayeeAcctNo());
							dto.setAmount(MoneyUtil.transferY2F(flow.getTransAmt().multiply(new BigDecimal(100)), 0).toString());
							dto.setOrgBizDate(simp.format(flow.getSysDate()));
							dto.setOrgBizSerialNo(flow.getTransSubSeq());
						}
						
					}else{
						PayFlowListPo flow = succList.get(0);
						dto.setBankCardNo(flow.getPayerAcctNo());
						dto.setSetAccount(flow.getPayeeAcctNo());
						dto.setAmount(MoneyUtil.transferY2F(flow.getTransAmt().multiply(new BigDecimal(100)), 0).toString());
						dto.setOrgBizDate(simp.format(flow.getSysDate()));
						dto.setOrgBizSerialNo(flow.getTransSubSeq());
					}
					dto.setCoreTranCode(CommonConstants_GNR.CORE_BANK_PAY_CODE_08002);
					
					dto.setIsReqCore("Y");
					String paySeq = seqService.generatePayFlowSeq();                  
					insertPayFlowList(dto.getBankCardNo(),dto.getSetAccount(),payFlowList,paySeq);
					dto.setCoreSubSeq(paySeq);
					dto.setTranDate(SysInfoContext.getSysDate());
				}
			}
		}else{
			 ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "交易流水");
		}
		SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
        String time = sim.format(new Date());
        dto.setMachineDate(time.substring(0, 8));
        dto.setMachineTime(time.substring(8));
        dto.setBizDate(sim.format(SysInfoContext.getSysDate()).substring(0, 8));
        dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
		return dto;
	}
	
	private void insertPayFlowList(String payErAcctNo, String payEeAcctNo, PayFlowListPo payFlowList, String paySeq){
		
        Date now = new Date();
        Date date = SysInfoContext.getSysDate();
        PayFlowListPo pay = new PayFlowListPo();
        pay.setOrderNo(payFlowList.getOrderNo());
        pay.setSrFlag(payFlowList.getSrFlag());
        pay.setOrderDes(payFlowList.getOrderDes());
        pay.setSecMerNo(payFlowList.getSecMerNo());
        pay.setSysDate(date);
        pay.setTransSubSeq(paySeq);
        pay.setPayerAcctNo(payErAcctNo);
        pay.setPayeeAcctNo(payEeAcctNo);
        pay.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
        pay.setMerNo(payFlowList.getMerNo());
        pay.setPayerAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
        pay.setPayeeAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
        pay.setCcy(DataBaseConstants_PAY.T_CCY_CNY);
        pay.setTransAmt(payFlowList.getTransAmt());
        pay.setFeeAmt(payFlowList.getFeeAmt());
        pay.setTransTime(now);
        pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_BEGIN);
        pay.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
        pay.setLastUpdateTime(now);
        pay.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
        
        HashMap<String, Object> parmMap=new HashMap<>();
        parmMap.put("orderNo", payFlowList.getOrderNo());
        Integer maxSeqNo = (Integer)daoService.selectOne(PayFlowListPo.class.getName()+".findMaxSeqNo",parmMap);
        if(null==maxSeqNo||0==maxSeqNo){
        	maxSeqNo=1;
        }else{
        	maxSeqNo=maxSeqNo+1;
        }
        pay.setSeqNo(maxSeqNo);
        
        daoService.insert(pay);
	}
}
