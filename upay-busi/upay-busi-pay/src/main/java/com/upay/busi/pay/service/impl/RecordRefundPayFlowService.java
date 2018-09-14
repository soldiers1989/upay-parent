package com.upay.busi.pay.service.impl;

import java.util.ArrayList;
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
import com.upay.busi.pay.service.dto.RefundPayFlowDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.CommonMethodUtils;
import com.upay.commons.util.DateUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.acc.AccBindBookPo;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.pay.PayFlowListPo;

/**
 * 生成通道退款支付流水并保存
 * @author zhangjianfeng
 * @since 2016/08/21 22:08
 * 此原子服务需要放在事物中
 */
public class RecordRefundPayFlowService extends AbstractDipperHandler<RefundPayFlowDto> {

    private static final Logger LOG = LoggerFactory.getLogger(RecordRefundPayFlowService.class);

    @Resource
    private IDaoService daoService;

    @Resource
    private ISequenceService sequenceService;

    @Override
    public RefundPayFlowDto execute(RefundPayFlowDto dto, Message message) throws Exception {
        if(StringUtils.isBlank(dto.getIsUseOriPayer())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "是否自定义付款方标识");
        }
        if(StringUtils.isBlank(dto.getIsUseOriPayee())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "是否自定义收款方标识");
        }
        if(StringUtils.isBlank(dto.getRouteCode())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "资金通道");
        }
        if(dto.getTransAmt()==null){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易金额");
        }
        if(StringUtils.isBlank(dto.getOrderNo())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        if(StringUtils.isBlank(dto.getRefundRepeatFlag())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "重复退款标识");
        }
        if(StringUtils.isBlank(dto.getMerNo())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户号");
        }
        PayFlowListPo oriRoutePayFlow = new PayFlowListPo();
        String routeCode = dto.getRouteCode();
        PayFlowListPo refundPayFlow = new PayFlowListPo();
        //若是微信支付，发生重复退款，使用原流水
        if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS.equals(routeCode)&&CommonBaseConstans_PAY.REFUND_REPEAT_YES.equals(dto.getRefundRepeatFlag())){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("orderNo",dto.getOrderNo() );
            map.put("routeCode", dto.getRouteCode());
            map.put("merNo", dto.getMerNo());
            refundPayFlow=daoService.selectOne(PayFlowListPo.class.getName().concat(".getPayFlowLastOne"),map);
            if(refundPayFlow==null){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "支付流水");
            }
            if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(refundPayFlow.getTransStat())){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0053);
            }
            if(DataBaseConstants_PAY.T_PAY_TX_PROING.equals(refundPayFlow.getTransStat())||DataBaseConstants_PAY.T_PAY_TX_UNKNOWN.equals(refundPayFlow.getTransStat())){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0055);
            }
            if(dto.getTransAmt().compareTo(refundPayFlow.getTransAmt())!=0){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0100,"退款 金额");
            }
            //修改流水状态为初始状态，删除通道方信息
            Map<String,Object> update=new HashMap<String,Object>();
            update.put("transSeq", refundPayFlow.getTransSubSeq());
            update.put("transStat", refundPayFlow.getTransStat());
            int num=daoService.update(PayFlowListPo.class.getName().concat(".resetPayFlow"), update);
            if(num!=1){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "退款重置流水");
            }
        }else{           
            oriRoutePayFlow.setOrderNo(dto.getOriOrderNo()); //原订单号
            oriRoutePayFlow.setMerNo(dto.getMerNo()); //商户号
            oriRoutePayFlow.setRouteCode(routeCode); //原支付流水通道代码
            oriRoutePayFlow.setCcy(dto.getOriCcy()); //原支付流水交易币种
            if(!DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)){                
                oriRoutePayFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS); //原支付流水交易状态 N-成功
                oriRoutePayFlow.setRouteTransStat(DataBaseConstants_PAY.ROUTE_TRANS_TYPE_SUCCESS); //原支付流水通道支付状态 0-成功
            }
            List<PayFlowListPo> flowList = daoService.selectList(oriRoutePayFlow);
            if(flowList!=null && flowList.size()>0) {
                if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)){
                    boolean isFail=true;
                    for(PayFlowListPo pp:flowList){
                        if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(pp.getTransStat())){
                            oriRoutePayFlow=pp;
                            isFail=false;
                        }
                    }
                    if(isFail){
                        oriRoutePayFlow=flowList.get(0);
                    }
                }else{
                    oriRoutePayFlow=flowList.get(0);
                }
            }else{
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0027, new Object[]{dto.getOriOrderNo(), routeCode}); //订单[{}]{}通道支付流水不存在                
            }
            refundPayFlow.setOrderNo(dto.getOrderNo()); //支付平台退款订单号
            refundPayFlow.setOrderDes(dto.getRefundDes()); //退款订单描述
            refundPayFlow.setMerNo(dto.getMerNo()); //商户号
            refundPayFlow.setSecMerNo(dto.getSecMerNo()); //二级商户号
            refundPayFlow.setSrFlag(DataBaseConstants_PAY.SR_FLAG_NOSTRO); //往来标识
            refundPayFlow.setSysDate(SysInfoContext.getSysDate()); //支付平台交易日期
            if(dto.getIsEsbCore().equals("N")){
            	refundPayFlow.setTransSubSeq(sequenceService.generatePayFlowSeq()); //支付流水号
            }else{
            	refundPayFlow.setTransSubSeq(sequenceService.generateEsbNo()); //支付流水号
            }
            refundPayFlow.setRouteCode(routeCode); //通道代码
            //付款账户信息
            if("N".equals(dto.getIsUseOriPayer())){            
                refundPayFlow.setPayerAcctType(oriRoutePayFlow.getPayeeAcctType());
                refundPayFlow.setPayerAcctNo(oriRoutePayFlow.getPayeeAcctNo());
                refundPayFlow.setPayerName(oriRoutePayFlow.getPayeeName());
                refundPayFlow.setPayerOrgName(oriRoutePayFlow.getPayeeOrgName());
                refundPayFlow.setPayerBankName(oriRoutePayFlow.getPayeeBankName());
                refundPayFlow.setPayerBankNo(oriRoutePayFlow.getPayeeBankNo());
            }else{
                refundPayFlow.setPayerAcctType(dto.getPayerAcctType());
                refundPayFlow.setPayerAcctNo(dto.getPayerAccNo());
                refundPayFlow.setPayerName(dto.getPayerName());
                refundPayFlow.setPayerOrgName(dto.getPayerOrgName());
                refundPayFlow.setPayerBankName(dto.getPayerBankName());
                refundPayFlow.setPayerBankNo(dto.getPayerBankNo());
            }
            //设置退款收款方账户信息
            if("N".equals(dto.getIsUseOriPayee())){            
                refundPayFlow.setPayeeAcctType(oriRoutePayFlow.getPayerAcctType());
                refundPayFlow.setPayeeAcctNo(oriRoutePayFlow.getPayerAcctNo());
                refundPayFlow.setPayeeName(oriRoutePayFlow.getPayerName());
                refundPayFlow.setPayeeOrgName(oriRoutePayFlow.getPayerOrgName());
                refundPayFlow.setPayeeBankName(oriRoutePayFlow.getPayerBankName());
                refundPayFlow.setPayeeBankNo(oriRoutePayFlow.getPayerBankNo());
            }else{
                refundPayFlow.setPayeeAcctType(dto.getPayeeAccType());
                refundPayFlow.setPayeeAcctNo(dto.getPayeeAccNo());
                refundPayFlow.setPayeeName(dto.getPayeeName());
                refundPayFlow.setPayeeOrgName(dto.getPayeeOrgName());
                refundPayFlow.setPayeeBankName(dto.getPayeeBankName());
                refundPayFlow.setPayeeBankNo(dto.getPayeeBankNo());
            }
            if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(dto.getRouteCode())){
                refundPayFlow.setRouteSeq(CommonMethodUtils.getZJSubSeq());
            }
            refundPayFlow.setCcy(dto.getOriCcy());
            refundPayFlow.setTransAmt(dto.getTransAmt());
            refundPayFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_BEGIN);
            //refundPayFlow.setRouteTransStat();
            Date currTime = new Date();
            refundPayFlow.setTransTime(currTime);
            refundPayFlow.setLastUpdateTime(currTime);
            refundPayFlow.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
            refundPayFlow.setPayUserId(oriRoutePayFlow.getPayUserId());
            refundPayFlow.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
            HashMap<String, Object> parmMap=new HashMap<>();
            parmMap.put("orderNo", dto.getOrderNo());
            Integer maxSeqNo = (Integer)daoService.selectOne(PayFlowListPo.class.getName()+".findMaxSeqNo",parmMap);
            if(null==maxSeqNo||0==maxSeqNo){
            	maxSeqNo=1;
            }else{
            	maxSeqNo=maxSeqNo+1;
            }
            refundPayFlow.setSeqNo(maxSeqNo);
            refundPayFlow.setPromoterDeptNo(dto.getPromoterDeptNo());
            daoService.insert(refundPayFlow);
            if(oriRoutePayFlow.getPayerAcctType()!=null&&(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(oriRoutePayFlow.getPayerAcctType())
                    ||DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(oriRoutePayFlow.getPayerAcctType()))){
                if(StringUtils.isBlank(dto.getOriUserId())){
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "原订单用户id");
                }
                if(StringUtils.isBlank(oriRoutePayFlow.getPayerAcctNo())){
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "付款账号");
                }
                AccVbookPo book=new AccVbookPo();
                book.setUserId(dto.getOriUserId());
                book.setVacctStat(DataBaseConstans_ACC.ACC_V_BOOK_VSTAT_NOMAL);
                book=daoService.selectOne(book);
                if(book==null){
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "账户");
                }
                AccBindBookPo acc=new AccBindBookPo();
                acc.setVbindAcctNo(oriRoutePayFlow.getPayerAcctNo());
                acc.setVacctNo(book.getVacctNo());
                acc.setBindStat(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_BIND);
                acc=daoService.selectOne(acc);
                if(acc==null){
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "付款账号");
                }
                if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(oriRoutePayFlow.getRouteCode())){
                    if(StringUtils.isBlank(acc.getRemark1())){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "绑定流水号 ");
                    }
                    dto.setTxSNBinding(acc.getRemark1());
                }
                if(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(oriRoutePayFlow.getPayerAcctType())){
                    if(StringUtils.isBlank(acc.getCvn2())){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "贷记 卡安全码");
                    }
                    if(StringUtils.isBlank(acc.getValidDate())){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "贷记卡有效时间");
                    }
                    dto.setCvv2(acc.getCvn2());
                    dto.setValiddate(acc.getValidDate());
                }
            }
        }
        
        //DTO中设置付款方信息
        dto.setPayerAccNo(refundPayFlow.getPayerAcctNo());
//        dto.setPayerAcctType(refundPayFlow.getPayerAcctType());
//        dto.setPayerBankName(refundPayFlow.getPayerBankName());
//        dto.setPayerBankNo(refundPayFlow.getPayerBankNo());
//        dto.setPayerName(refundPayFlow.getPayerName());
//        dto.setPayerOrgName(refundPayFlow.getPayerOrgName());
//        //DTO中设置收款方信息
        dto.setPayeeAccNo(refundPayFlow.getPayeeAcctNo());
        dto.setPayeeAccType(refundPayFlow.getPayeeAcctType());
//        dto.setPayeeBankName(refundPayFlow.getPayeeBankName());
//        dto.setPayeeBankNo(refundPayFlow.getPayeeBankNo());
//        dto.setPayeeName(refundPayFlow.getPayeeName());
//        dto.setPayeeOrgName(refundPayFlow.getPayeeOrgName());
//        //其它信息设置
        if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)){
        	dto.setOriRouteCharDate(oriRoutePayFlow.getSysDate()==null?null:DateUtil.format(oriRoutePayFlow.getSysDate(), "yyyyMMdd"));
        }else{
        	dto.setOriRouteCharDate(oriRoutePayFlow.getRouteDate()==null?null:DateUtil.format(oriRoutePayFlow.getRouteDate(), "yyyyMMdd"));
        }
        dto.setOriRouteSeq(oriRoutePayFlow.getRouteSeq());
        dto.setOriTransSubSeq(oriRoutePayFlow.getTransSubSeq());
        dto.setOriFlowTransCharDate(oriRoutePayFlow.getSysDate()==null?null:DateUtil.format(oriRoutePayFlow.getSysDate(), "yyyyMMdd"));
        dto.setTransSubSeq(refundPayFlow.getTransSubSeq());
        dto.setRouteSeq(refundPayFlow.getRouteSeq());
        dto.setFlowTransCharDate(refundPayFlow.getSysDate()==null?null:DateUtil.format(refundPayFlow.getSysDate(), "yyyyMMdd"));
        return dto;
    }
}
