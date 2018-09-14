package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.pay.service.dto.StrikeChkErrServiceDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.CommonMethodUtils;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;
import com.upay.dao.po.pay.SmokeStlDetailPo;


/**
 * 冲微信或中金账
 * 
 * @author yhy 20170516
 * 
 */
public class StrikeChkErrService extends AbstractDipperHandler<StrikeChkErrServiceDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(StrikeChkErrService.class);
    @Resource
    private ISequenceService seqService;
    

	@Override
	public StrikeChkErrServiceDto execute(StrikeChkErrServiceDto dto, Message message)
			throws Exception {
		
		String dealType = dto.getDealType();
		//平台流水号
		String errFlowSeq = dto.getErrFlowSeq();
		//第三方流水号
		if (StringUtils.isBlank(errFlowSeq)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "平台流水号");
        }
		if (StringUtils.isBlank(dealType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "差错处理方式");
        }
		//通道方对应的本平台流水
		PayFlowListPo payFlowList = new PayFlowListPo();
		payFlowList.setTransSubSeq(errFlowSeq);
		payFlowList = daoService.selectOne(payFlowList);
		//查询第三方对账信息表
		ChkThirdDetailPo chkThirdDetail = new ChkThirdDetailPo();
		chkThirdDetail.setThirdSeq(payFlowList.getRouteSeq());
		chkThirdDetail = daoService.selectOne(chkThirdDetail);
		//流水对应的订单信息
		PayOrderListPo payOrderList = new PayOrderListPo();
		payOrderList.setOrderNo(payFlowList.getOrderNo());
		payOrderList = daoService.selectOne(payOrderList);
		
		if(payOrderList != null){
			if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(payOrderList.getTransType())){
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0115);
			}
		}else{
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0108);
		}
		
		//冲微信账
		if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WECHAT_STRIKE.equals(dealType)){
			
			MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
			merBaseInfoPo.setMerNo(payOrderList.getMerNo());
			merBaseInfoPo = daoService.selectOne(merBaseInfoPo);
			String paySeq = seqService.generatePayFlowSeq();
			insertPayFlowList(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS,payOrderList,paySeq,null,null);
			dto.setCoreSubSeq(paySeq);
			dto.setThiredSeq(chkThirdDetail.getChnlSeq());
			dto.setSubMchId(merBaseInfoPo.getSubMchId());
			dto.setTotalFee(String.valueOf(chkThirdDetail.getTransAmt().multiply(new BigDecimal("100")).intValue()));
			
			dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS);
		}else if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_ZJ_STRIKE.equals(dealType)){//冲中金账
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("flag", true);
			//退款走快捷支付退款接口-2521 网银支付走 1133接口
			String paySeq = seqService.generatePayFlowSeq();
			String seq = CommonMethodUtils.getZJSubSeq();
			if(CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(payOrderList.getTransType()) || CommonConstants_GNR.SYS_TRANS_TYPE_REC.equals(payOrderList.getTransType())){
				dto.setThiredSeq(chkThirdDetail.getThirdSeq());
				if(DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_PERSONAL.equals(payOrderList.getPayType()) || DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_COMPANY.equals(payOrderList.getPayType())){
					dto.setZjReqInfa(CmparmConstants.GATEWAY_ZJPAY_1133);
				}else{
					dto.setZjReqInfa(CmparmConstants.GATEWAY_ZJPAY_2521);
				}
				map.put("payEeAcctNo", payFlowList.getPayerAcctNo());
				map.put("payEeAcctName", payFlowList.getPayerName());
				map.put("payEeAcctType", payFlowList.getPayerAcctType());
				map.put("payerBankName", payFlowList.getPayerBankName());
				map.put("flag", false);
			}else{
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0115);
			}
			dto.setZjRouteSeq(seq);
			dto.setCoreSubSeq(paySeq);
			dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
			insertPayFlowList(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY,payOrderList,paySeq,seq,map);
			
		}else if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_UNION_STRIKE.equals(dealType)){
			
			String paySeq = seqService.generatePayFlowSeq();
			insertPayFlowList(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS,payOrderList,paySeq,null,null);
			dto.setCoreSubSeq(paySeq);
			dto.setThiredSeq(chkThirdDetail.getChnlSeq());
			dto.setTotalFee(String.valueOf(chkThirdDetail.getTransAmt().multiply(new BigDecimal("100")).intValue()));
		}
		
		dto.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_REF);
		dto.setTranAmt(MoneyUtil.transferY2F(chkThirdDetail.getTransAmt().multiply(new BigDecimal(100)), 0).toString());
		return dto;
	}
	
	
	private void insertPayFlowList(String routeCode,PayOrderListPo payOrderList, String paySeq,String seq, Map<String, Object> map){
		
        Date now = new Date();
        Date date = SysInfoContext.getSysDate();
       
        PayFlowListPo pay = new PayFlowListPo();
        pay.setOrderNo(payOrderList.getOrderNo());
        pay.setSrFlag("1");
        pay.setOrderDes(payOrderList.getOrderDes());
        pay.setSecMerNo(payOrderList.getSecMerNo());
        pay.setSysDate(date);
        pay.setTransSubSeq(paySeq);
        pay.setRouteCode(routeCode);
        pay.setMerNo(payOrderList.getMerNo());
        if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routeCode)){
        	if((boolean)map.get("flag")){
        		pay.setPayerAcctNo((String)map.get("payErAcctNo"));
        		pay.setPayerName((String)map.get("payErAcctName"));
        		pay.setPayerAcctType((String)map.get("payErAcctType"));
        		pay.setPayeeAcctNo((String)map.get("payEeAcctNo"));
        		pay.setPayeeName((String)map.get("payEeAcctName"));
        		pay.setPayeeAcctType((String)map.get("payEeAcctType"));
        	}else{
        		pay.setPayeeAcctNo((String)map.get("payEeAcctNo"));
        		pay.setPayeeName((String)map.get("payEeAcctName"));
        		pay.setPayeeAcctType((String)map.get("payEeAcctType"));
        		pay.setPayeeBankName((String)map.get("payerBankName"));
        	}
        }
        pay.setCcy(DataBaseConstants_PAY.T_CCY_CNY);
        pay.setTransAmt(payOrderList.getTransAmt());
        pay.setFeeAmt(payOrderList.getFeeAmt());
        pay.setTransTime(now);
        if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routeCode)){
        	pay.setRouteSeq(seq);
        }
        pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_BEGIN);
        pay.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
        pay.setLastUpdateTime(now);
        pay.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
        
        HashMap<String, Object> parmMap=new HashMap<>();
        parmMap.put("orderNo", payOrderList.getOrderNo());
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
