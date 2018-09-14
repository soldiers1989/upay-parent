package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CheckInChkErrDealResultDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.chk.ChkErrDealResultPo;
import com.upay.dao.po.chk.ChkErrListPo;
import com.upay.dao.po.chk.ChkHostDetailPo;
import com.upay.dao.po.chk.ChkHostFilePo;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 登记差错处理结果信息 并且更新差错表信息
 *
 */
public class CheckInChkErrDealResultService extends AbstractDipperHandler<CheckInChkErrDealResultDto>{

	@Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(CheckInChkErrDealResultService.class);

	
	@Override
	public CheckInChkErrDealResultDto execute(CheckInChkErrDealResultDto dto,
			Message message) throws Exception {
		String isDeal = dto.getIsDeal();
		String errFlowSeq = dto.getErrFlowSeq();//差错中的流水
		String errRouteCode = dto.getErrRouteCode();
		String chkStat = dto.getChkStat();
		String errTransAmt = dto.getErrTransAmt();
		String dealType = dto.getDealType();
		//差错处理流水
		String dealSeq = dto.getCoreSubSeq();
		//内管传入
		String dealFlowStat = dto.getDealFlowStat();
		String transStat = dto.getTransStat();
		String feePaySeq = dto.getFeePaySeq();
		String isAddFee = dto.getIsAddFee();
		String stlErr = dto.getStlErr();
		if (StringUtils.isBlank(errFlowSeq)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "平台流水号");
        }
		if (StringUtils.isBlank(errRouteCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "通道代码");
        }
		if (StringUtils.isBlank(chkStat)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "对账状态");
        }
		String flowFlag = null;
		String errStat = null;
		//查询差错流水或差错对应得平台流水
		PayFlowListPo payFlow = new PayFlowListPo();
		payFlow.setTransSubSeq(errFlowSeq);
		payFlow = daoService.selectOne(payFlow);	
		
		PayOrderListPo payOrderList = new PayOrderListPo();
		payOrderList.setOrderNo(payFlow.getOrderNo());
		payOrderList = daoService.selectOne(payOrderList);
		
		if(DataBaseConstants_BATCH.T_CHK_FLAG_MORE.equals(chkStat)){
			flowFlag = DataBaseConstants_PAY.FLOW_FLAG_PLATE;
		}else if(DataBaseConstants_BATCH.T_CHK_FLAG_LESS.equals(chkStat)){
			flowFlag = DataBaseConstants_PAY.FLOW_FLAG_OTHER;
		}else if(DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED.equals(chkStat)){
			
			if(payFlow.getTransAmt().compareTo(new BigDecimal(errTransAmt)) == 0){
				flowFlag = DataBaseConstants_PAY.FLOW_FLAG_PLATE;
			}else{
				flowFlag = DataBaseConstants_PAY.FLOW_FLAG_OTHER;
			}
		}
		ChkErrDealResultPo chkErrDealResultPo = new ChkErrDealResultPo();
		chkErrDealResultPo.setSeqNo(errFlowSeq);
		chkErrDealResultPo.setFlowFlag(flowFlag);
		List<ChkErrDealResultPo> selectList = daoService.selectList(chkErrDealResultPo);
		
		
		
		//isDeal为空的是不需要差错处理的流水，直接记为差错处理成功
		if(StringUtils.isBlank(isDeal) && StringUtils.isBlank(stlErr)){
			ChkErrDealResultPo chkErrDeal = insertChkErrDealResult(payFlow,errFlowSeq,errRouteCode,dto);
			chkErrDeal.setFlowFlag(flowFlag);
			chkErrDeal.setResultInfo(DataBaseConstants_PAY.T_PMT_DEELERR_SUC);
			daoService.insert(chkErrDeal);
			errStat= DataBaseConstants_PAY.T_PMT_DEELERR_SUC;
			logger.info("流水为["+errFlowSeq+"]的差错处理成功!");
		}else{
			
			//修改流水的交易状态，对账状态，清算状态
			
			//补账处理，修改订单为成功状态,修改订单清算状态
			if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_ADD.equals(dealType) || DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_ADD.equals(dealType)){
				if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(transStat)){
					updateStat(payOrderList,payFlow);
				}
			}else if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_RUS.equals(dealType) || DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_RUS.equals(dealType)){
				if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(transStat)){
					if(payOrderList!=null){
						if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(payOrderList.getOrderStat())
								||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP.equals(payOrderList.getOrderStat())
								||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(payOrderList.getOrderStat())
								||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC.equals(payOrderList.getOrderStat())
								||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO.equals(payOrderList.getOrderStat())
								||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL.equals(payOrderList.getOrderStat())){
							payOrderList.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_HC);
						}
					}
					if(payFlow != null){
						if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(payFlow.getTransStat()) ){
							payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_WIPE);
						}
						if(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC.equals(payFlow.getClearFlag())){
							payFlow.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
						}
						if(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS.equals(payFlow.getRouteTransStat())){
							payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
						}
					}
					if(isAddFee != null && "N".equals(isAddFee) && feePaySeq!= null){
						PayFlowListPo payFlowListWhere = new PayFlowListPo();
						payFlowListWhere.setTransSubSeq(feePaySeq);
						PayFlowListPo payFlowListParam = new PayFlowListPo();
						payFlowListParam.setTransStat(DataBaseConstants_PAY.T_PAY_TX_RUSH);
						
						daoService.update(payFlowListParam, payFlowListWhere);
					}
					
					PayFlowListPo payFlowListWhere = new PayFlowListPo();
					payFlowListWhere.setTransSubSeq(dealSeq);
					PayFlowListPo payFlowListParam = new PayFlowListPo();
					payFlowListParam.setTransStat(DataBaseConstants_PAY.T_PAY_TX_RUSH);
					daoService.update(payFlowListParam, payFlowListWhere);
				}
			}else if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_PLAT_ADJUST.equals(dealType)){
				if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(dealFlowStat)){
					updateStat(payOrderList,payFlow);
				}else if(DataBaseConstants_PAY.T_PAY_TX_FAL.equals(dealFlowStat)){
					if(payOrderList!=null){
						if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(payOrderList.getOrderStat())
								||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP.equals(payOrderList.getOrderStat())
								||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(payOrderList.getOrderStat())
								||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC.equals(payOrderList.getOrderStat())){
							payOrderList.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_HC);
						}
					}
					if(payFlow != null){ 
						if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(payFlow.getTransStat()) ){
							payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_WIPE);
						}
						if(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC.equals(payFlow.getClearFlag())){
							payFlow.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
						}
						if(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS.equals(payFlow.getRouteTransStat())){
							payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
						}
					}
				}
			}
			//更新订单交易状态和清算状态
			if(StringUtils.isBlank(stlErr)){
				daoService.update(payOrderList);
			}
			//更新流水交易状态、对账状态、清算状态
			daoService.update(payFlow);
			//更新手续费的流水
			if(isAddFee != null && feePaySeq!= null && DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(transStat)){
				PayFlowListPo payFlowListWhere = new PayFlowListPo();
				payFlowListWhere.setTransSubSeq(feePaySeq);
				PayFlowListPo payFlowListParam = new PayFlowListPo();
				payFlowListParam.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
				
				daoService.update(payFlowListParam, payFlowListWhere);
			}
			
			
			if(selectList == null || selectList.size() == 0){
				ChkErrDealResultPo chkErrDeal = insertChkErrDealResult(payFlow,errFlowSeq,errRouteCode,dto);
				if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(transStat)){
					if(!DataBaseConstants_PAY.T_PMT_DEELERR_WAY_PLAT_ADJUST.equals(dealType)){
						updateClearFlag(dealSeq);
					}
					chkErrDeal.setResultInfo(DataBaseConstants_PAY.T_PMT_DEELERR_SUC);
					errStat= DataBaseConstants_PAY.T_PMT_DEELERR_SUC;
					logger.info("流水为["+errFlowSeq+"]的差错处理成功!");
				}else{
					chkErrDeal.setResultInfo(DataBaseConstants_PAY.T_PMT_DEELERR_FAL);
					errStat= DataBaseConstants_PAY.T_PMT_DEELERR_FAL;
					logger.info("流水为["+errFlowSeq+"]的差错处理失败!");
				}
				chkErrDeal.setFlowFlag(flowFlag);
				chkErrDeal.setDealNo(dealSeq);// 调账流水
				chkErrDeal.setPayAcct(payFlow.getPayerAcctNo());
				chkErrDeal.setPayeeAcct(payFlow.getPayeeAcctNo());
				chkErrDeal.setTransAmt(payFlow.getTransAmt());
				daoService.insert(chkErrDeal);
				
			}else{
				ChkErrDealResultPo chkErrDealUp = selectList.get(0);
				chkErrDealUp.setProcNum(chkErrDealUp.getProcNum()+1);
				if(!DataBaseConstants_PAY.T_PMT_DEELERR_WAY_PLAT_ADJUST.equals(dealType)){
					updateClearFlag(dealSeq);
				}
				if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(transStat)){
					chkErrDealUp.setResultInfo(DataBaseConstants_PAY.T_PMT_DEELERR_SUC);
					errStat= DataBaseConstants_PAY.T_PMT_DEELERR_SUC;
					logger.info("流水为["+errFlowSeq+"]的差错处理成功!");
				}else{
					chkErrDealUp.setResultInfo(DataBaseConstants_PAY.T_PMT_DEELERR_FAL);
					errStat= DataBaseConstants_PAY.T_PMT_DEELERR_FAL;
					logger.info("流水为["+errFlowSeq+"]的差错处理失败!");
				}
				chkErrDealUp.setDealNo(dealSeq);// 调账流水
				daoService.update(chkErrDealUp);
			}
		}
		
		//差错处理成功后，修改原差错流水的对账状态，以及对账明细中的对账状态
		//核心对账明细在表T_CHK_HOST_DETAIL中，中金、微信对账明细在表T_CHK_THIRD_DETAIL,
		//中金的对账明细，如果是中金多（对方多），则chnl_seq字段中放的是中金的流水（平台流水中的通道流水号ROUTE_SEQ）
		/*if(DataBaseConstants_PAY.T_PMT_DEELERR_SUC.equals(errStat)){
			PayFlowListPo payFlowU = new PayFlowListPo();
			payFlowU.setTransSubSeq(errFlowSeq);
			payFlowU = daoService.selectOne(payFlowU);	
			if(DataBaseConstants_BATCH.T_CHK_FLAG_MORE.equals(chkStat)){
				payFlowU.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
				daoService.update(payFlowU);
			}else if(DataBaseConstants_BATCH.T_CHK_FLAG_LESS.equals(chkStat)){
				if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(errRouteCode)){
					ChkHostDetailPo chkHostDetailWhere = new ChkHostDetailPo();
					chkHostDetailWhere.setPlatSeq(payFlowU.getTransSubSeq());
					ChkHostDetailPo chkHostDetailParam = new ChkHostDetailPo();
					chkHostDetailParam.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
					daoService.update(chkHostDetailParam, chkHostDetailWhere);
				}else{
					ChkThirdDetailPo chkThirdDetailParam = new ChkThirdDetailPo();
					chkThirdDetailParam.setThirdStat(DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
					ChkThirdDetailPo chkThirdDetailWhere = new ChkThirdDetailPo();
					if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS.equals(errRouteCode)){
						chkThirdDetailWhere.setChnlSeq(payFlowU.getTransSubSeq());
						
					}else if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(errRouteCode)){
						chkThirdDetailWhere.setChnlSeq(payFlowU.getRouteSeq());
					}
					daoService.update(chkThirdDetailParam, chkThirdDetailWhere);
				}
			}
		}*/
		
		//修改差错表中流水的处理状态
		ChkErrListPo chkErrList = new ChkErrListPo();
		chkErrList.setSysSeq(errFlowSeq);
		chkErrList.setHostErrStat(chkStat);
		chkErrList = daoService.selectOne(chkErrList);
		if(chkErrList == null){
			chkErrList = new ChkErrListPo();
			chkErrList.setSysSeq(errFlowSeq);
			chkErrList.setThridErrStat(chkStat);
			chkErrList = daoService.selectOne(chkErrList);
			if(chkErrList==null){
				chkErrList = new ChkErrListPo();
				chkErrList.setSysSeq(dto.getZjErrRouteSeq());
				chkErrList.setThridErrStat(chkStat);
				chkErrList = daoService.selectOne(chkErrList);
				if(chkErrList == null){
					ExInfo.throwDipperEx(AppCodeDict.BISPAY0108);
				}
			}
		}
		chkErrList.setProcFlag(dealType);
		chkErrList.setErrStat(errStat);
		daoService.update(chkErrList);
		
		//差错处理方式为补核心账时，由于补账是新增一条流水，因此需要将原流水修改为失败状态，新增流水的原因是该补账流水需要下一天对账
		if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_ADD.equals(dealType)){
			PayFlowListPo payFlowListParam = new PayFlowListPo();
			payFlowListParam.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
			
			PayFlowListPo payFlowListWhere = new PayFlowListPo();
			payFlowListWhere.setTransSubSeq(errFlowSeq);
			
			daoService.update(payFlowListParam, payFlowListWhere);
		}
		
		//虚拟账户余额更改，补账处理，与正交易相同，冲账交易相反;
		/*if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(transStat) && (CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(payOrderList.getTransType()) || CommonConstants_GNR.SYS_TRANS_TYPE_REC.equals(payOrderList.getTransType()))){
			AccVbookPo accVbook = new AccVbookPo();
			accVbook.setUserId(payOrderList.getUserId());
			accVbook = daoService.selectOne(accVbook);
			//补账
			if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_ADD.equals(dealType) || DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_ADD.equals(dealType)){
				if(CommonConstants_GNR.SYS_TRANS_TYPE_REC.equals(payOrderList.getTransType())){
					accVbook.setAcctBal(accVbook.getAcctBal().add(payOrderList.getTransAmt()));
					daoService.update(accVbook);
				}else if(CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(payOrderList.getTransType())){
					if(isAddFee != null && feePaySeq!= null){
						accVbook.setAcctBal(accVbook.getAcctBal().subtract(payOrderList.getTransAmt()).subtract(payOrderList.getFeeAmt()));
					}else{
						accVbook.setAcctBal(accVbook.getAcctBal().subtract(payOrderList.getTransAmt()));
					}
					daoService.update(accVbook);
				}
			}else if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_RUS.equals(dealType) || DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_RUS.equals(dealType)){
				
				if(CommonConstants_GNR.SYS_TRANS_TYPE_REC.equals(payOrderList.getTransType())){
					accVbook.setAcctBal(accVbook.getAcctBal().subtract(payOrderList.getTransAmt()));
					daoService.update(accVbook);
				}else if(CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(payOrderList.getTransType())){
					if(isAddFee != null && feePaySeq!= null){
						accVbook.setAcctBal(accVbook.getAcctBal().add(payOrderList.getTransAmt()).add(payOrderList.getFeeAmt()));
					}else{
						accVbook.setAcctBal(accVbook.getAcctBal().add(payOrderList.getTransAmt()));
					}
					daoService.update(accVbook);
				}
			}
		}*/
		
		return dto;
		
	}
	
	private ChkErrDealResultPo insertChkErrDealResult(PayFlowListPo payFlowListPo, String errFlowSeq, String routeCode, CheckInChkErrDealResultDto dto){
		ChkErrDealResultPo chkErrDeal = new ChkErrDealResultPo();
		chkErrDeal.setOrgCode(routeCode);
		//核心对账明细在表T_CHK_HOST_DETAIL中，中金、微信对账明细在表T_CHK_THIRD_DETAIL,
		//中金的对账明细，如果是中金多（对方多），则chnl_seq字段中放的是中金的流水（平台流水中的通道流水号ROUTE_SEQ	）
		if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)){
			ChkHostDetailPo chkHostDetailPo = new ChkHostDetailPo();
			chkHostDetailPo.setPlatSeq(errFlowSeq);
			chkHostDetailPo = daoService.selectOne(chkHostDetailPo);
			if(chkHostDetailPo!=null){
				chkErrDeal.setHostChkBatchNo(chkHostDetailPo.getChkBatchNo());
				chkErrDeal.setHostChkDate(chkHostDetailPo.getChkDate());
				chkErrDeal.setPlatDate(chkHostDetailPo.getTranDate());
			}
			chkErrDeal.setHostSeq(dto.getRouteSeq());
		}else{
			ChkThirdDetailPo chkThirdDetailPo = new ChkThirdDetailPo();
			chkThirdDetailPo.setChnlSeq(errFlowSeq);
			chkThirdDetailPo = daoService.selectOne(chkThirdDetailPo);
			if(chkThirdDetailPo!=null){
				chkErrDeal.setThirdChkBatchNo(chkThirdDetailPo.getChkBatchNo());
				chkErrDeal.setThirdChkDate(chkThirdDetailPo.getChkDate());
			}else{
				chkThirdDetailPo = new ChkThirdDetailPo();
				chkThirdDetailPo.setChnlSeq(payFlowListPo.getRouteSeq());
				chkThirdDetailPo = daoService.selectOne(chkThirdDetailPo);
				if(chkThirdDetailPo!=null){
					chkErrDeal.setThirdChkBatchNo(chkThirdDetailPo.getChkBatchNo());
					chkErrDeal.setThirdChkDate(chkThirdDetailPo.getChkDate());
				}
			}
			chkErrDeal.setChnlSeq(dto.getRouteSeq());
		}
			
		
		chkErrDeal.setSeqNo(errFlowSeq);
		chkErrDeal.setDealDate(new Date());
		chkErrDeal.setProcNum(1);
		chkErrDeal.setProcTime(new Date());
		chkErrDeal.setTellerNo(dto.getTellerNo());
		chkErrDeal.setChkTeller(dto.getTellerNo());
		return chkErrDeal;
	}

	
	private void updateStat(PayOrderListPo payOrderList, PayFlowListPo payFlow){
		if(payOrderList!=null){
			if(!DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(payOrderList.getOrderStat())
					&& !DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(payOrderList.getOrderStat())
					&& !DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP.equals(payOrderList.getOrderStat())
					&& !DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC.equals(payOrderList.getOrderStat())){
				if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(payOrderList.getTransType())){
					payOrderList.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC);
				}else{
					if(DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE.equals(payOrderList.getPayServicType())){
						payOrderList.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP);
					}else{
						payOrderList.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
					}
				}
				
			}
			if(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO.equals(payOrderList.getClearFlag())){
				if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(payOrderList.getTransType())){
					//判断是否是隔日退款
					if(payOrderList.getOriDate().compareTo(payOrderList.getOrderDate()) < 0){
						payOrderList.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
					}else{
						payOrderList.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_READY);
					}
				}else{
					payOrderList.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_READY);
				}
			}
		}
		
		if(payFlow != null){
			if(!DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(payFlow.getTransStat()) && !DataBaseConstants_PAY.T_PAY_TX_PROING.equals(payFlow.getTransStat()) 
					&& !DataBaseConstants_PAY.T_PAY_TX_UNKNOWN.equals(payFlow.getTransStat()) ){
				payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
			}
			if(!DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC.equals(payFlow.getClearFlag())){
				payFlow.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
			}
			/*if(!DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS.equals(payFlow.getChkFlag())){
				payFlow.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
			}*/
			if(!CommonConstants_GNR.OUT_PAY_STAT_SUCCESS.equals(payFlow.getRouteTransStat())){
				payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
			}
		}
	}
	
	private	 void updateClearFlag(String dealSeq){
		PayFlowListPo payFlows = new PayFlowListPo();
		payFlows.setTransSubSeq(dealSeq);
		
		payFlows = daoService.selectOne(payFlows);
		payFlows.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
		daoService.update(payFlows);
	}
	
}
