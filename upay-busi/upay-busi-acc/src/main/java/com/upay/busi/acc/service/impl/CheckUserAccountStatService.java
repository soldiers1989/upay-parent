package com.upay.busi.acc.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.CheckUserAccountStatDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.CommonMethodUtils;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 用户账户状态检查(有些交易不需要账户状态检查)
 * 变更记录：
 * 注释148行以下代码的原因是：商户改造后，商户不能做支付交易，绑卡，充值 提现。  所以不存在商户有虚拟账号的情况。
 * @author shangqiankun
 * @version 创建时间：2016年8月5日 下午2:25:28
 */
public class CheckUserAccountStatService extends AbstractDipperHandler<CheckUserAccountStatDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public CheckUserAccountStatDto execute(CheckUserAccountStatDto dto, Message message) throws Exception {
        String accNo = dto.getAccNo();
        String dcFlag = dto.getDcFlag();
        String routeCode=dto.getRouteCode();
        BigDecimal payAmt=dto.getTransAmt();
        String transType=dto.getTransType();
        if (StringUtils.isBlank(dcFlag)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "借贷标识");
        }
        if (StringUtils.isBlank(routeCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "资金通道");
        }
        if(payAmt==null){
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "支付金额");
        }
        if(StringUtils.isBlank(transType)){
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "交易类型");
        }
        boolean check=false;
//        String oriTransType=null;
        AccVbookPo accv = null;
        if(CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(transType)){
            if (StringUtils.isBlank(accNo)) {
                String userId = dto.getUserId();
                if (StringUtils.isBlank(userId)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "用户id");
                }
                accv = new AccVbookPo();
                accv.setUserId(userId);
                accv = daoService.selectOne(accv);
            } else {
                accv = new AccVbookPo();
                accv.setVacctNo(accNo);
                accv = daoService.selectOne(accv);
            }
            if (accv == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0002);
            }
            if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)&&DataBaseConstants_PAY.T_PAY_TYPE_PLATFORM_ACC.equals(dto.getPayType())){
                if(StringUtils.isNotBlank(dto.getPayType())&&DataBaseConstants_PAY.T_PAY_TYPE_PLATFORM_ACC.equals(dto.getPayType())){
                    dto.setAccType(DataBaseConstants_PAY.ACCT_TYPE_EPAY);
                }
                this.checkAcc(accv,DataBaseConstants_PAY.T_DC_FLAG_D,payAmt);
            }
        }else if(CommonConstants_GNR.SYS_TRANS_TYPE_REC.equals(transType)){
            if (StringUtils.isBlank(accNo)) {
                String userId = dto.getUserId();
                if (StringUtils.isBlank(userId)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "用户id");
                }
                accv = new AccVbookPo();
                accv.setUserId(userId);
                accv = daoService.selectOne(accv);
            } else {
                accv = new AccVbookPo();
                accv.setVacctNo(accNo);
                accv = daoService.selectOne(accv);
            }
            if (accv == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0002);
            }
            this.checkAcc(accv,DataBaseConstants_PAY.T_DC_FLAG_C,payAmt);
        }else if(CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(transType)){
            if (StringUtils.isBlank(accNo)) {
                String userId = dto.getUserId();
                if (StringUtils.isBlank(userId)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "用户id");
                }
                accv = new AccVbookPo();
                accv.setUserId(userId);
                accv = daoService.selectOne(accv);
            } else {
                accv = new AccVbookPo();
                accv.setVacctNo(accNo);
                accv = daoService.selectOne(accv);
            }
            if (accv == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0002);
            }
            this.checkAcc(accv,DataBaseConstants_PAY.T_DC_FLAG_D,payAmt);
        }else if(CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(transType)){
            if (StringUtils.isBlank(accNo)) {
                String userId = dto.getUserId();
                if (StringUtils.isBlank(userId)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "用户id");
                }
                accv = new AccVbookPo();
                accv.setUserId(userId);
                accv = daoService.selectOne(accv);
            } else {
                accv = new AccVbookPo();
                accv.setVacctNo(accNo);
                accv = daoService.selectOne(accv);
            }
            if (accv == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0002);
            }
        }else if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(transType)){ //退货商户账户状态检查、用户状态检查、商户账户余额检查（可选）
            if(StringUtils.isBlank(dto.getIsNextDayRefund())){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "隔日退款标识");
            }
            if(StringUtils.isBlank(dto.getMerUserId())){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户的用户id");
            }
            if(StringUtils.isBlank(dto.getOriPayType())){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "原订单支付方式");
            }
            
            //注释以下代码的原因是：商户改造后，商户不能做支付交易，绑卡，充值 提现。  所以不存在商户有虚拟账号的情况。
            //商户虚拟账户状态检查
//            if(CommonBaseConstans_PAY.REFUND_NOT_TODAY.equals(dto.getIsNextDayRefund())){                
//                accv = new AccVbookPo();
//                accv.setUserId(dto.getMerUserId());
//                accv = daoService.selectOne(accv);
//                if(accv == null){
//                    ExInfo.throwDipperEx(AppCodeDict.BISACC0002);
//                }
//                if(DataBaseConstants_PAY.T_DC_FLAG_D.equals(dto.getDcFlag())){
//                    this.checkAcc(accv,DataBaseConstants_PAY.T_DC_FLAG_C,payAmt);
//                }else{                    
//                    this.checkAcc(accv,DataBaseConstants_PAY.T_DC_FLAG_D,payAmt);
//                    dto.setMerAccNo(accv.getVacctNo());
//                    
//                    //商户账户余额检查
//                    if(payAmt != null && payAmt.compareTo(BigDecimal.ZERO) > 0) {
//                        if(accv.getAcctBal().compareTo(payAmt) < 0) {
//                            ExInfo.throwDipperEx(AppCodeDict.BISACC0007, accv.getAcctBal()); //账户[{}]余额不足
//                        }
//                    }
//                }
//            }
            if(checkPayType(dto.getOriPayType())){                
                if(StringUtils.isBlank(dto.getOriUserId())){
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "原订单用户id");
                }
                accv = new AccVbookPo();
                accv.setUserId(dto.getOriUserId()); //原支付订单，用户ID
                accv = daoService.selectOne(accv);
                if(accv!=null){                
                    dto.setAccNo(accv.getVacctNo());
                }
                //检查用户账户状态
                if(DataBaseConstants_PAY.T_PAY_TYPE_PLATFORM_ACC.equals(dto.getOriPayType())){                
                    if(DataBaseConstants_PAY.T_PAY_TYPE_PLATFORM_ACC.equals(dto.getOriPayType())) {
                        if(accv == null){
                            ExInfo.throwDipperEx(AppCodeDict.BISACC0002);
                        }
                        this.checkAcc(accv,DataBaseConstants_PAY.T_DC_FLAG_C,payAmt);
                    }
                }
            }
            return dto;
        }
        
        if(accv!=null){            
            dto.setAccNo(accv.getVacctNo());
        }
        return dto;
    }
    /**
     * 检查账户状态
     */
    public void checkAcc(AccVbookPo acc,String dcFlag,BigDecimal payAmt){
        BigDecimal zero=new BigDecimal(0);
        String accStat = acc.getVacctStat();
        if (!DataBaseConstans_ACC.ACC_V_BOOK_FRZSTAT_NORMAL.equals(accStat)) {
            if (DataBaseConstans_ACC.ACC_V_BOOK_VSTAT_UNACTIVEL.equals(accStat)) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0003);
            }
            if (DataBaseConstans_ACC.ACC_V_BOOK_VSTAT_SLEEP.equals(accStat)) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0004);
            }
            if (DataBaseConstans_ACC.ACC_V_BOOK_VSTAT_CANCEL.equals(accStat)) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0005);
            }
        }
        if(DataBaseConstants_PAY.T_DC_FLAG_D.equals(dcFlag)) { //账户做为付款方
            if (!DataBaseConstans_ACC.ACC_V_BOOK_FRZSTAT_NORMAL.equals(acc.getFrzStat())) {
                if (DataBaseConstans_ACC.ACC_V_BOOK_FRZSTAT_FROZEN.equals(acc.getFrzStat())||DataBaseConstans_ACC.ACC_V_BOOK_FRZSTAT_BORROW.equals(acc.getFrzStat())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0008);
                }
                if(DataBaseConstans_ACC.ACC_V_BOOK_FRZSTAT_AMT.equals(acc.getFrzStat())){
                    if(acc.getAcctBal().compareTo((acc.getFrzBal()==null?zero:acc.getFrzBal()).add(payAmt))<0){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0029);
                    }
                }
            }
            if(!DataBaseConstans_ACC.ACC_V_BOOK_STOPSTAT_NORMAL.equals(acc.getStopStat())){
                if(DataBaseConstans_ACC.ACC_V_BOOK_STOPSTAT_NO.equals(acc.getStopStat())||DataBaseConstans_ACC.ACC_V_BOOK_STOPSTAT_ONLYGET.equals(acc.getStopStat())){
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0032,acc.getVacctNo());
                }
            }
            if(acc.getAcctBal().compareTo(payAmt==null?new BigDecimal(0):payAmt)<0){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0029);
            }
        }else if(DataBaseConstants_PAY.T_DC_FLAG_C.equals(dcFlag)) { //账户做为收款方
            if (!DataBaseConstans_ACC.ACC_V_BOOK_FRZSTAT_NORMAL.equals(acc.getFrzStat())) {
                if (DataBaseConstans_ACC.ACC_V_BOOK_FRZSTAT_FROZEN.equals(acc.getFrzStat())||DataBaseConstans_ACC.ACC_V_BOOK_FRZSTAT_LOAN.equals(acc.getFrzStat())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0008);
                }
            }
            if(!DataBaseConstans_ACC.ACC_V_BOOK_STOPSTAT_NORMAL.equals(acc.getStopStat())){
                if(DataBaseConstans_ACC.ACC_V_BOOK_STOPSTAT_NO.equals(acc.getStopStat())){
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0032,acc.getVacctNo());
                }
            }
        }
    }
    
    public boolean checkPayType(String payType){
    	boolean flag = false;
    	if(!(DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_PERSONAL.equals(payType)||
                DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_COMPANY.equals(payType)||
                DataBaseConstants_PAY.T_PAY_TYPE_PUBLIC_NO.equals(payType)||
                DataBaseConstants_PAY.T_PAY_TYPE_CARD_PAY.equals(payType)||
                DataBaseConstants_PAY.T_PAY_TYPE_UNIONPAY_GATEWAY.equals(payType)||
                DataBaseConstants_PAY.T_PAY_TYPE_ALIPAY_QR_CODE.equals(payType)||
                DataBaseConstants_PAY.T_PAY_TYPE_ALIPAY_CARD_PAY.equals(payType)||
                DataBaseConstants_PAY.T_PAY_TYPE_ALIPAY_TOGETHER.equals(payType)||
                DataBaseConstants_PAY.T_PAY_TYPE_ACP_WR_CODE.equals(payType)||
                DataBaseConstants_PAY.T_PAY_TYPE_ACP_XWR_CODE.equals(payType)||
                DataBaseConstants_PAY.T_PAY_TYPE_ACP_QR_CODE.equals(payType))){
    		flag = true;
    	}
    	return flag;
    }
}
