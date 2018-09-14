package com.upay.busi.fee.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.fee.service.dto.FeeGetBaseDto;
import com.upay.commons.constants.CommonBaseConstans_FEE;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.fee.FeeAssDetailPo;
import com.upay.dao.po.fee.FeeAssPo;
import com.upay.dao.po.fee.FeeDetailPo;
import com.upay.dao.po.fee.FeeGetPo;
import com.upay.dao.po.fee.FeeKindPo;
import com.upay.dao.po.fee.FeeStandAreaPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * 手续费和分润计算并记录
 * 
 * @author shangqiankun
 * @version 创建时间：2016年8月3日 下午4:44:51
 */
public class FeeGetBaseService extends AbstractDipperHandler<FeeGetBaseDto> {
	private static final Logger LOG = LoggerFactory.getLogger(FeeGetBaseService.class);
	@Resource
	private IDaoService daoService;

	@Override
    public FeeGetBaseDto execute(FeeGetBaseDto dto, Message message) throws Exception {
		//如果商户上传了手续费，则无需统计手续费(杰安调用支付接口时的信息，暂时没有登记分明细)
		//TODO
		BigDecimal merFeeAmt = dto.getMerFeeAmt();
		if(merFeeAmt != null){
			return dto;
		}
				
        SimpleDateFormat SIM_YMD=new SimpleDateFormat("yyyyMMdd");
        String transCode = dto.getTransCode();
        String merNo = dto.getMerNo();
        String orderNo = dto.getOrderNo();
        String chnlId = dto.getChnlId();
        String routeCode = dto.getRouteCode();
        String accNo = dto.getAccNo();
        String transType=dto.getTransType();
        String isMer=dto.getIsMer();
        BigDecimal transAmt=dto.getTransAmt();
//        if (StringUtils.isBlank(isMer)) {
//            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户手续费计算标识");
//        }
        if (transAmt==null) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易金额");
        }else if(transAmt.compareTo(BigDecimal.ZERO)<=0){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0047);
        }
//        if(CommonBaseConstans_FEE.MER_FEE_GET_Y.equals(isMer)){
//        if (StringUtils.isBlank(merNo)) {
//            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户");
//        }
//        }else if(CommonBaseConstans_FEE.MER_FEE_GET_N.equals(isMer)){
        if (StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        if (StringUtils.isBlank(transType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易类型");
        }
        if (StringUtils.isBlank(routeCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "资金通道代码");
        }
//        }else{
//            ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "商户手续费计算标识");
//        }
        
        /**为了防止重复登记流水和分润明细 在计算手续费时先删除之前的手续费分润明细和流水**/
        //删除己存在的手续费明细
        FeeDetailPo feeDetail = new FeeDetailPo();
        feeDetail.setOrderNo(dto.getOrderNo());
    	daoService.delete(feeDetail);
    	
        //删除己存在的分润明细
        FeeAssDetailPo assd = new FeeAssDetailPo();
        assd.setOrderNo(dto.getOrderNo());
    	daoService.delete(assd);
    	
    	//删除己存在的订单流水
    	PayFlowListPo flowPo = new PayFlowListPo();
    	flowPo.setOrderNo(dto.getOrderNo());
    	daoService.delete(flowPo);
    	/**为了防止重复登记流水和分润明细 在计算手续费时先删除之前的手续费分润明细和流水**/
    	
        boolean getFromMer =false;//为false时是个人手续费     为true时是商户手续费
        Map<String, Object> map = new HashMap<String, Object>();
        BigDecimal proportion = new BigDecimal(100);
        Date now=new Date();
        String today = DateUtil.format(now, DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD);
        Date date = SIM_YMD.parse(SIM_YMD.format(SysInfoContext.getSysDate()==null?now:SysInfoContext.getSysDate()));
        map.put("transCode", transCode);
        map.put("now", today);
        if(StringUtils.isNotBlank(merNo)){
            MerBaseInfoPo mm=new MerBaseInfoPo();
            mm.setMerNo(merNo);
            mm=daoService.selectOne(mm);
            if(null==mm){
            	ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "计算手续费：商户信息不存在");
            }else if(!DateBaseConstants_MER.MER_STAT_NORMAL.equals(mm.getMerState())){
            	ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "计算手续费：商户状态不正常");
            }
            map.put("merNo", merNo);
            map.put("userType",DataBaseConstants_PAY.USER_TYPE_MER);
            map.put("userTypeAll",DataBaseConstants_PAY.USER_TYPE_ALL);
            getFromMer=true;//商户手续费
        }else{
            map.put("userType",DataBaseConstants_PAY.USER_TYPE_PLAIN);
            map.put("userTypeAll",DataBaseConstants_PAY.USER_TYPE_ALL);
        }
        if(StringUtils.isNotBlank(dto.getAccType())){            
            map.put("accType", dto.getAccType());
        }
        map.put("chnlId", chnlId);
        map.put("routeCode", routeCode);
        List<Map<String,Object>> feeList = daoService.selectList(FeeGetPo.class.getName().concat(".getOneActive"), map);
        if(feeList!=null&&feeList.size()>1){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0043,"手续费配置 ");
        }
        if (feeList!=null&&feeList.size()==1) {
            FeeGetPo fee = new FeeGetPo();
            BeanUtils.copyProperties(fee, feeList.get(0));
            dto.setGetType(fee.getGetType());
            // 判断是否免收
            boolean checkFree = checkExempt(fee, now);
            if(!checkFree){
                // 获取一级商户手续费
                BigDecimal feeAmt = this.calculationFee(fee.getFeeCode(), transAmt, proportion,null);
                BigDecimal zero=new BigDecimal(0);
                //手续费金额校验
                feeAmt=feeAmt==null?zero:(feeAmt.compareTo(zero)>=0?feeAmt:zero);
                if(feeAmt.compareTo(zero)>0){                    
                    //手续费优惠处理
                    BigDecimal perFee=fee.getPerFee()==null?proportion:new BigDecimal(fee.getPerFee());
                    feeAmt=feeAmt.multiply(perFee).divide(proportion);
                    
                    //如果手续费有多位小数，四舍五入为2位小数
                    feeAmt=feeAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
                    dto.setFeeAmt(feeAmt);//手续费元
                    dto.setMerFeeAmt(feeAmt);//一级商户手续费
                    if(feeAmt.signum()!=0){
                    	dto.setFeeAmtPoints(String.valueOf(MoneyUtil.transferY2F(feeAmt, 2)));//手续费分
                    }
                }
                
                if(getFromMer&&!checkFree){//商户手续费需要记录分润明细和手续费明细
	                // 记录手续费明细
	                this.recordFee(dto, fee, transAmt, feeAmt, DataBaseConstants_PAY.T_FEE_DETAIL_FREE_FLAG_NO, date,
	                    now);
	                if (StringUtils.isNotBlank(fee.getAssCode())) {
	                    // 记录分润明细
	                    this.recordAss(dto, fee.getAssCode(), feeAmt, proportion, date, now);
	                }else{
	                    ExInfo.throwDipperEx(AppCodeDict.BISFEE0004, "分润配置");
	                }      
                }
                
                //计算二级商户手续费
                //二级商户手续费
                BigDecimal secMerFeeAmt=BigDecimal.ZERO;
                if (StringUtils.isNotBlank(dto.getSecMerNo())) {
                	 map.put("secMerNo", dto.getSecMerNo());
                	 map.put("transCode", null);
                	 map.put("chnlId", null);
                	 map.put("accType", null);
                	 map.put("routeCode", null);
                	 map.put("userType", null);
                	 
                	 List<Map<String,Object>> secMerFeeList = daoService.selectList(FeeGetPo.class.getName().concat(".getOneActive"), map);
                     if(secMerFeeList!=null&&secMerFeeList.size()>1){
                         ExInfo.throwDipperEx(AppCodeDict.BISPAY0043,"二级商户【"+dto.getSecMerNo()+"】手续费配置 ");
                     }
                     if (secMerFeeList!=null&&secMerFeeList.size()==1) {
                         FeeGetPo secMerfee = new FeeGetPo();
                         BeanUtils.copyProperties(secMerfee, secMerFeeList.get(0));
                         // 判断是否免收
                         boolean checkSecMerFree = checkExempt(secMerfee, now);
                         if(!checkSecMerFree){
                             // 获取二级手续费
                             secMerFeeAmt = this.calculationFee(secMerfee.getFeeCode(), transAmt, proportion,merNo);
                             
                             //手续费金额校验
                             secMerFeeAmt=secMerFeeAmt==null?zero:(secMerFeeAmt.compareTo(zero)>=0?secMerFeeAmt:zero);
                             if(secMerFeeAmt.compareTo(zero)>0){                    
                                 //手续费优惠处理
                                 BigDecimal perFee=secMerfee.getPerFee()==null?proportion:new BigDecimal(secMerfee.getPerFee());
                                 secMerFeeAmt=secMerFeeAmt.multiply(perFee).divide(proportion);
                                 
                                 //如果手续费有多位小数，四舍五入为2位小数
                                 secMerFeeAmt=secMerFeeAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
                             }
                         }
                     }
                }
                
                LOG.debug("订单号：【"+orderNo+"】    一级商户手续费：【"+feeAmt+"】   二级商户手续费：【"+secMerFeeAmt+"】");
                // 记录订单中手续费金额
                this.updateOrderFee(orderNo, feeAmt,secMerFeeAmt, getFromMer);
            }
        }
        //当手续费为空的情况下，设置为0.00
        if(null==dto.getFeeAmt()){
        	dto.setFeeAmt(MoneyUtil.moneyFormat(BigDecimal.ZERO));
        }
        
        return dto;
    }

	/**
	 * 更新订单中手续费金额
	 * 
	 * @param orderNo
	 * @param feeAmt
	 * @param isMer
	 */
	public void updateOrderFee(String orderNo, BigDecimal feeAmt,
			BigDecimal secMerFeeAmt, boolean getFromMer) {
		PayOrderListPo param = new PayOrderListPo();
		if (getFromMer) {
			param.setMerFeeAmt(feeAmt);
			param.setSecMerFeeAmt(secMerFeeAmt);
		} else {
			param.setFeeAmt(feeAmt);
		}
		PayOrderListPo where = new PayOrderListPo();
		where.setOrderNo(orderNo);
		daoService.update(param, where);
	}

	/**
	 * 记录分润明细
	 * 
	 * @param dto
	 * @param assCode
	 * @param feeAmt
	 * @param proportion
	 * @param date
	 * @param now
	 */
	public void recordAss(FeeGetBaseDto dto, String assCode, BigDecimal feeAmt,
			BigDecimal proportion, Date date, Date now) {
		FeeAssPo ass = new FeeAssPo();
		ass.setAssCode(assCode);
		List<FeeAssPo> assList = daoService.selectList(ass);
		// List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if (assList != null && assList.size() > 0) {
			Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
			BigDecimal allRate = new BigDecimal(0);
			// 计算各个分润方的所占比例的总和 和每个分润方的分润金额
			for (FeeAssPo as : assList) {
				BigDecimal assAmt = this.getAssAmt(as, feeAmt, proportion);
				assAmt = assAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
				map.put(as.getAssId(), assAmt);
				allRate = allRate.add(as.getAssRate());
			}
			// 判断分润比例是否是100%，如果不是100%，则分润配置是有问题的
			if (allRate.compareTo(proportion) != 0) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "分润配置");
			}
			// 处理分润溢出的问题
			map = this.getAssFeeAmt(map, feeAmt);

			// 登记分润明细
			for (FeeAssPo as : assList) {
				// BigDecimal assAmt = this.getAssAmt(as, feeAmt, proportion);
				// FeeAssDetailPo assd = new FeeAssDetailPo();
				// assd.setOrderNo(dto.getOrderNo());
				// assd.setAssCode(assCode);
				// assd.setAssId(as.getAssId());
				// assd = daoService.selectOne(assd);
				// if (assd != null) {
				//
				// FeeAssDetailPo param = new FeeAssDetailPo();
				// param.setAssAmt(map.get(as.getAssId()));
				// param.setRouteCode(dto.getRouteCode());
				// FeeAssDetailPo where = new FeeAssDetailPo();
				// where.setOrderNo(dto.getOrderNo());
				// where.setAssCode(assCode);
				// where.setAssId(as.getAssId());
				// daoService.update(param, where);
				// } else {
				FeeAssDetailPo detail = new FeeAssDetailPo();
				detail.setTxnDate(date);
				detail.setTxnTime(now);
				detail.setOrderNo(dto.getOrderNo());
				detail.setRouteCode(dto.getRouteCode());
				detail.setAcctNo(dto.getAccNo());
				detail.setAcctType(dto.getAccType());
				detail.setChnlId(dto.getChnlId());
				detail.setTransCode(dto.getTransCode());
				detail.setAssCode(assCode);
				detail.setAssId(as.getAssId());
				if (DataBaseConstants_PAY.T_FEE_ASS_ASS_TYPE_RATIO.equals(as
						.getAssType())) {
					detail.setAssRate(as.getAssRate());
				}
				detail.setAssAmt(map.get(as.getAssId()));
				detail.setDcFlag(DataBaseConstants_PAY.T_FEE_ASS_DC_FLAG_BORROW);
				daoService.insert(detail);
				// }
			}
		} else {
			ExInfo.throwDipperEx(AppCodeDict.BISFEE0004, "分润配置");
		}

	}

	/**
	 * 处理分润金额溢出问题，优先级为：统一支付平台>银行机构>资金通道>合作商户
	 * 
	 * @param assAmtMap
	 * @return
	 */
	public Map<String, BigDecimal> getAssFeeAmt(
			Map<String, BigDecimal> assAmtMap, BigDecimal feeAmt) {
		BigDecimal all = new BigDecimal(0);
		for (String key : assAmtMap.keySet()) {
			all = all.add(assAmtMap.get(key));
		}
		if (all.compareTo(feeAmt) > 0) {
			if (assAmtMap.size() == 1) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "分润计算:分润金额");
			}
			ArrayList<String> keyList = new ArrayList<String>();
			ArrayList<BigDecimal> array = new ArrayList<BigDecimal>();
			if (assAmtMap
					.containsKey(DataBaseConstants_PAY.T_FEE_ASS_ASS_ID_PALTFORM)) {
				array.add(array.size(), assAmtMap
						.get(DataBaseConstants_PAY.T_FEE_ASS_ASS_ID_PALTFORM));
				keyList.add(keyList.size(),
						DataBaseConstants_PAY.T_FEE_ASS_ASS_ID_PALTFORM);
			}
			if (assAmtMap
					.containsKey(DataBaseConstants_PAY.T_FEE_ASS_ASS_ID_BANK)) {
				array.add(array.size(), assAmtMap
						.get(DataBaseConstants_PAY.T_FEE_ASS_ASS_ID_BANK));
				keyList.add(keyList.size(),
						DataBaseConstants_PAY.T_FEE_ASS_ASS_ID_BANK);
			}
			if (assAmtMap
					.containsKey(DataBaseConstants_PAY.T_FEE_ASS_ASS_ID_ROUTE)) {
				array.add(array.size(), assAmtMap
						.get(DataBaseConstants_PAY.T_FEE_ASS_ASS_ID_ROUTE));
				keyList.add(keyList.size(),
						DataBaseConstants_PAY.T_FEE_ASS_ASS_ID_ROUTE);
			}
			if (assAmtMap
					.containsKey(DataBaseConstants_PAY.T_FEE_ASS_ASS_ID_MERCHANT)) {
				array.add(array.size(), assAmtMap
						.get(DataBaseConstants_PAY.T_FEE_ASS_ASS_ID_MERCHANT));
				keyList.add(keyList.size(),
						DataBaseConstants_PAY.T_FEE_ASS_ASS_ID_MERCHANT);
			}
			if (array.size() == 0) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "分润计算：分润方");
			}
			BigDecimal copy = new BigDecimal(feeAmt.doubleValue());
			copy = copy.setScale(2, BigDecimal.ROUND_HALF_UP);
			for (int i = 0; i < array.size(); i++) {
				if (copy.compareTo(array.get(i)) < 0) {
					array.set(i, copy);
				}
				copy = copy.subtract(array.get(i));
			}
			for (int j = 0; j < array.size(); j++) {
				assAmtMap.put(keyList.get(j), array.get(j));
			}
		}
		return assAmtMap;
	}

	/**
	 * 获取分润金额
	 * 
	 * @param assCode
	 * @param feeAmt
	 * @param proportion
	 * @return
	 */
	public BigDecimal getAssAmt(FeeAssPo ass, BigDecimal feeAmt,
			BigDecimal proportion) {
		BigDecimal assAmt = null;
		if (DataBaseConstants_PAY.T_FEE_ASS_ASS_TYPE_FIXED.equals(ass
				.getAssType())) {
			assAmt = ass.getFixAmt();
		}
		if (DataBaseConstants_PAY.T_FEE_ASS_ASS_TYPE_RATIO.equals(ass
				.getAssType())) {
			assAmt = feeAmt.multiply(ass.getAssRate().divide(proportion));
		}
		return assAmt;
	}

	/**
	 * 获取手续费
	 * 
	 * @param feeCode
	 * @param payAmount
	 * @return
	 */
	public BigDecimal calculationFee(String feeCode, BigDecimal transAmt,
			BigDecimal proportion,String merNo) {
		BigDecimal fee = new BigDecimal(0);
		BigDecimal zero = new BigDecimal(0);
		FeeKindPo kind = new FeeKindPo();
		kind.setFeeCode(feeCode);
		//如何计算二级商户手续费时，需要把一级商户号一起查询
		if(StringUtils.isNotBlank(merNo)){
			kind.setMemo(merNo);
		}
		kind = daoService.selectOne(
				FeeKindPo.class.getName().concat(".getOneByFeeCode"), kind);
		if (kind != null) {
			if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_SUBSECTION
					.equals(kind.getFeeMode())) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("feeCode", feeCode);
				map.put("status", DataBaseConstants_PAY.FEE_STAND_STAT_YES);
				map.put("now", new Date());
				List<FeeStandAreaPo> standList = daoService.selectList(
						FeeStandAreaPo.class.getName().concat(
								".getFeeStandList"), map);
				FeeStandAreaPo stan = new FeeStandAreaPo();
				for (int i = 0; i < standList.size(); i++) {
					stan = standList.get(i);
					fee = fee.add(this.getStandFeeAmt(stan, transAmt,
							proportion));
				}
			} else {
				if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_FIXED.equals(kind
						.getFeeMode())) {
					fee = kind.getFixFee() == null ? zero : kind.getFixFee();
				}
				if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_ALL.equals(kind
						.getFeeMode())) {
					fee = (kind.getFixFee() == null ? zero : kind.getFixFee())
							.add(transAmt.multiply((kind.getRationFee() == null ? zero
									: kind.getRationFee()).divide(proportion)));
				}
				if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_PROPERTION
						.equals(kind.getFeeMode())) {
					fee = transAmt.multiply((kind.getRationFee() == null ? zero
							: kind.getRationFee()).divide(proportion));
				}
				if (kind.getLowFee() != null && kind.getHighFee() != null) {
					fee = fee.compareTo(kind.getHighFee()) > 0 ? kind
							.getHighFee()
							: fee.compareTo(kind.getLowFee()) < 0 ? kind
									.getLowFee() : fee;
				} else if (kind.getLowFee() != null) {
					fee = fee.compareTo(kind.getLowFee()) < 0 ? kind
							.getLowFee() : fee;
				} else if (kind.getHighFee() != null) {
					fee = fee.compareTo(kind.getHighFee()) > 0 ? kind
							.getHighFee() : fee;
				}
			}
		} else {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0016);
		}
		return fee;

	}

	/**
	 * 计算分段手续费
	 * 
	 * @param stand
	 * @param payAmount
	 * @return
	 */
	public BigDecimal getStandFeeAmt(FeeStandAreaPo stand, BigDecimal transAmt,
			BigDecimal proportion) {

		BigDecimal feeAmt = new BigDecimal(0);
		String standType = stand.getFeeModelCode();
		if (stand.getAreaUp().compareTo(transAmt) <= 0) {
			if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_FIXED
					.equals(standType)) {
				feeAmt = stand.getFixFee();
			} else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_ALL
					.equals(standType)) {
				feeAmt = stand.getFixFee().add(
						stand.getAreaValue().multiply(stand.getRationFee())
								.divide(proportion));
			} else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_PROPERTION
					.equals(standType)) {
				feeAmt = stand.getAreaValue().multiply(stand.getRationFee())
						.divide(proportion);
			}
		} else if (transAmt.compareTo(stand.getAreaLow()) >= 0
				&& transAmt.compareTo(stand.getAreaUp()) < 0) {
			if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_FIXED
					.equals(standType)) {
				feeAmt = stand.getFixFee();
			} else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_ALL
					.equals(standType)) {
				feeAmt = stand.getFixFee().add(
						(transAmt.subtract(stand.getAreaLow())).multiply(
								stand.getRationFee()).divide(proportion));
			} else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_PROPERTION
					.equals(standType)) {
				feeAmt = (transAmt.subtract(stand.getAreaLow())).multiply(
						stand.getRationFee()).divide(proportion);
			}
		}
		return feeAmt;
	}

	/**
	 * 登记手续费明细表
	 * 
	 * @param dto
	 * @param fee
	 * @param payAmount
	 * @param feeAmt
	 * @param freeFlag
	 */
	public void recordFee(FeeGetBaseDto dto, FeeGetPo fee, BigDecimal transAmt,
			BigDecimal feeAmt, String freeFlag, Date date, Date now) {
		// FeeDetailPo feed = new FeeDetailPo();
		// feed.setOrderNo(dto.getOrderNo());
		// feed = daoService.selectOne(feed);
		// if (feed != null) {
		// FeeDetailPo param = new FeeDetailPo();
		// param.setTxnAmt(transAmt);
		// param.setFeeAmt(feeAmt);
		// FeeDetailPo where = new FeeDetailPo();
		// where.setOrderNo(dto.getOrderNo());
		// daoService.update(param, where);
		// } else {
		FeeDetailPo detail = new FeeDetailPo();
		detail.setTxnDate(date);
		detail.setTxnTime(now);
		detail.setOrderNo(dto.getOrderNo());
		detail.setAcctType(dto.getAccType());
		detail.setChnlId(dto.getChnlId());
		detail.setTransCode(dto.getTransCode());
		detail.setFeeCode(fee.getFeeCode());
		detail.setAssCode(fee.getAssCode());
		detail.setTxnAmt(transAmt);
		detail.setFeeAmt(feeAmt);
		detail.setGetType(fee.getGetType());
		detail.setFreeFlag(freeFlag);
		detail.setUserId(dto.getUserId());
		detail.setFeeId(fee.getFeeId());
		if (CommonConstants_GNR.TRANS_TYPE_BIND_PAY.equals(dto.getTransCode())
				|| CommonConstants_GNR.TRANS_TYPE_ONLINE_PAY.equals(dto
						.getTransCode())) {
			if (StringUtils.isNotBlank(dto.getBankAccNo())) {
				detail.setAcctNo(dto.getBankAccNo());
			} else {
				detail.setAcctNo(dto.getAccNo());
			}
		}
		if (CommonConstants_GNR.TRANS_TYPE_RECHARGE.equals(dto.getTransCode())) {
			if (StringUtils.isBlank(dto.getBankAccNo())) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "卡号");
			}
			detail.setAcctNo(dto.getBankAccNo());
		}
		if (CommonConstants_GNR.TRANS_TYPE_CASH.equals(dto.getTransCode())) {
			detail.setAcctNo(dto.getAccNo());
		}
		daoService.insert(detail);
		// }
	}

	/**
	 * 判断是否免收
	 * 
	 * @param fee
	 * @param userId
	 * @return
	 */
	public boolean checkExempt(FeeGetPo fee, Date now) {
		String cydle = fee.getFreeCycle();
		if (DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_NO.equals(cydle)) {
			return false;
		} else if (DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_ALL.equals(cydle)) {
			return true;
		}
		Date feeStart = fee.getStartDate();
		Calendar calFee = Calendar.getInstance();
		calFee.setTime(feeStart);
		Calendar calNow = Calendar.getInstance();
		calNow.setTime(now);
		boolean day = false;
		boolean month = false;
		boolean year = false;
		if (calFee.compareTo(calNow) == 0) {
			day = true;
			month = true;
			year = true;
		} else if (calFee.compareTo(calNow) < 0) {
			calFee.add(Calendar.DAY_OF_YEAR, 1);
			if (calFee.compareTo(calNow) >= 0) {
				day = true;
				month = true;
				year = true;
			} else {
				calFee.add(Calendar.DAY_OF_YEAR, -1);
				calFee.add(Calendar.MONTH, 1);
				if (calFee.compareTo(calNow) >= 0) {
					month = true;
					year = true;
				} else {
					calFee.add(Calendar.MONTH, -1);
					calFee.add(Calendar.YEAR, 1);
					if (calFee.compareTo(calNow) >= 0) {
						year = true;
					} else {
						return false;
					}
				}
			}
		}
		boolean checkNum = true;// this.checkCount(accNo,fee );
		if (DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_DAY.equals(fee
				.getFreeCycle())) {
			if (day && checkNum) {
				return true;
			} else {
				return false;
			}
		} else if (DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_MONTH.equals(fee
				.getFreeCycle())) {
			if (month && checkNum) {
				return true;
			} else {
				return false;
			}
		} else if (DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_YEAR.equals(fee
				.getFreeCycle())) {
			if (year && checkNum) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 检查免收次数
	 * 
	 * @param accNo
	 * @param transCode
	 * @param count
	 * @return
	 */
	public boolean checkCount(String accNo, FeeGetPo fee) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("feeId", fee.getFeeId());
		int use = daoService.count(
				FeeDetailPo.class.getName().concat(".getFeeUseCount"), map);
		if (use >= (fee.getFreeCount() == null ? 0 : fee.getFreeCount())) {
			return false;
		} else {
			return true;
		}
	}
}
