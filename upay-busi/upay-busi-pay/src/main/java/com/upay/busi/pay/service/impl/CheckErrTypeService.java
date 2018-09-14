package com.upay.busi.pay.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CheckErrTypeServiceDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.chk.ChkErrDealResultPo;
import com.upay.dao.po.chk.ChkErrListPo;
import com.upay.dao.po.chk.ChkHostDetailPo;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import com.upay.dao.po.chk.StlBookPo;
import com.upay.dao.po.pay.PayFlowListHisPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 校验差错类型是否与处理方式匹配
 * 
 * @author yhy 20170516
 * 
 */
public class CheckErrTypeService extends AbstractDipperHandler<CheckErrTypeServiceDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(CheckErrTypeService.class);


	@Override
	public CheckErrTypeServiceDto execute(CheckErrTypeServiceDto dto, Message message)
			throws Exception {
		//差错平台流水号
		String errFlowSeq = dto.getErrFlowSeq();
		String dealType = dto.getDealType();
		String errRouteCode = dto.getErrRouteCode();
		String chkStat = dto.getChkStat();
		if (StringUtils.isBlank(errFlowSeq)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "差错平台流水号");
        }
		if (StringUtils.isBlank(dealType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "差错处理方式");
        }
		if (StringUtils.isBlank(errRouteCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "通道代码");
        }
		if (StringUtils.isBlank(chkStat)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "对账状态");
        }
		
		//获取该差错流水是与哪个资金通道对账产生
		PayFlowListPo payFlowList = new PayFlowListPo();
		payFlowList.setTransSubSeq(errFlowSeq);
		payFlowList = daoService.selectOne(payFlowList);
		if(payFlowList==null){
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0116,errRouteCode);
		}
		
		PayOrderListPo payOrder = new PayOrderListPo();
		payOrder.setOrderNo(payFlowList.getOrderNo());
		payOrder = daoService.selectOne(payOrder);
		//调平台、冲核心账，需要保证核心流水对账成功或者已经处理差错成功
		if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_RUS.equals(dealType)){
			PayFlowListPo hostFlowList = new PayFlowListPo();
			hostFlowList.setOrderNo(payFlowList.getOrderNo());
			hostFlowList.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
			List<PayFlowListPo> list = daoService.selectList(hostFlowList);
			boolean isHaveErr = false;
			
			for (PayFlowListPo payFlowListPo : list) {
				if(!DataBaseConstants_PAY.T_CHK_STAT_SUC.equals(payFlowListPo.getChkFlag())){
					ChkErrListPo errListPo = new ChkErrListPo();
					errListPo.setSysSeq(payFlowListPo.getTransSubSeq());
					List<ChkErrListPo> errList = daoService.selectList(errListPo);
					for (ChkErrListPo chkErrListPo : errList) {
						if(!DataBaseConstants_PAY.T_PMT_DEELERR_SUC.equals(chkErrListPo.getErrStat())){
							isHaveErr = true;
							break;
						}
						
					}
					
				}
			}
			if(isHaveErr){
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"核心流水差错尚未处理！");
			}
			
		}		
		if(payOrder != null){
			if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(payOrder.getTransType())){
				if(payOrder.getOrderDate().compareTo(payOrder.getOriDate()) > 0){
					ExInfo.throwDipperEx(AppCodeDict.BISPAY0118,payFlowList.getOrderNo());
				}
			}
		}
		
		
		
		if(DataBaseConstants_BATCH.T_CHK_FLAG_LESS.equals(chkStat)){
			//流水成功的话，说明该订单不存在差错
			if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(payFlowList.getTransStat())){
				//如果不存差错，则将处理方式设置为10 差错校正
				dto.setDealType(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_PLAT_CRRECTION);
				return dto;
			}
		}else if(DataBaseConstants_BATCH.T_CHK_FLAG_MORE.equals(chkStat)){
			
			if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(errRouteCode)){
				ChkHostDetailPo chkHostDetail = new ChkHostDetailPo();
				chkHostDetail.setPlatSeq(errFlowSeq);
				chkHostDetail = daoService.selectOne(chkHostDetail);
				if(chkHostDetail!=null){
					//如果不存差错，则将处理方式设置为10 差错校正
					dto.setDealType(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_PLAT_CRRECTION);
					//如果在对账表中查到，并且对账状态为对方多，则该订单也不存在差错
					return dto;
				}
			}else{
				ChkThirdDetailPo chkThirdDetail = new ChkThirdDetailPo();
				chkThirdDetail.setChnlSeq(errFlowSeq);
				chkThirdDetail = daoService.selectOne(chkThirdDetail);
				if(chkThirdDetail==null){
					//中金对账时，以中金为主对账时产生的差错chnl_seq中登记的为中金的通道流水号
					if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(errRouteCode)){
						chkThirdDetail = new ChkThirdDetailPo();
						chkThirdDetail.setChnlSeq(payFlowList.getRouteSeq());
						chkThirdDetail = daoService.selectOne(chkThirdDetail);
						if(chkThirdDetail !=null){
							//如果不存差错，则将处理方式设置为10 差错校正
							dto.setDealType(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_PLAT_CRRECTION);
							//如果在对账表中查到，并且对账状态为对方多，则该订单也不存在差错
							return dto;
						}
					}
				}else{
					//如果不存差错，则将处理方式设置为10 差错校正
					dto.setDealType(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_PLAT_CRRECTION);
					//如果在对账表中查到，并且对账状态为对方多，则该订单也不存在差错
					return dto;
				}
			}
		}
		
		//由于初版商户结算时，并未登记流水，并且，流水号记录在t_stl_book表的remark字段中，如果该差错记录在该表中，则不进行差错处理，直接修改为差错处理成功
		StlBookPo stlBookPo = new StlBookPo();
		stlBookPo.setRemark(errFlowSeq);
		List<StlBookPo> stlBook = daoService.selectList(stlBookPo);
		if(stlBook.size() != 0){
			//是否进行差错处理 Y-是 N-否
			//如果不存差错，则将处理方式设置为10 差错校正
			dto.setDealType(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_PLAT_CRRECTION);
			return dto;
		}
		//结算产生的差错
		PayOrderListPo stlOrder = new PayOrderListPo();
		stlOrder.setOrderNo(payFlowList.getOrderNo());
		stlOrder = daoService.selectOne(stlOrder);
		if(stlOrder == null && "商户结算流水".equals(payFlowList.getOrderDes())){
			dto.setOrderNo(payFlowList.getOrderNo());
			dto.setStlErr("Y");
			return dto;
		}
		//该流水只能选择相应资金通道差错处理
		if(StringUtils.isNotBlank(errRouteCode)){
			if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_PLAT_ADJUST.equals(dealType)){
				dto.setIsDeal("N");//是否进行差错处理 Y-是 N-否
				dto.setOrderNo(payFlowList.getOrderNo());
				return dto;
			}
			if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(errRouteCode)){
				if(!(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_ADD.equals(dealType) || DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_RUS.equals(dealType))){
					ExInfo.throwDipperEx(AppCodeDict.BISPAY0107, new Object[]{"核心", "核心"});
				}
			}else if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS.equals(errRouteCode)){
				if(!(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_ADD.equals(dealType) || !DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_RUS.equals(dealType) || !DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WECHAT_STRIKE.equals(dealType))){
					ExInfo.throwDipperEx(AppCodeDict.BISPAY0107, new Object[]{"微信", "微信"});
				}
			}else if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(errRouteCode)){
				if(!(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_ADD.equals(dealType) || !DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_RUS.equals(dealType) || !DataBaseConstants_PAY.T_PMT_DEELERR_WAY_ZJ_STRIKE.equals(dealType))){
					ExInfo.throwDipperEx(AppCodeDict.BISPAY0107, new Object[]{"中金", "中金"});
				}
			}
		}
		dto.setIsDeal("Y");//是否进行差错处理 Y-是 N-否
		dto.setOrderNo(payFlowList.getOrderNo());
		return dto;
	}
}
