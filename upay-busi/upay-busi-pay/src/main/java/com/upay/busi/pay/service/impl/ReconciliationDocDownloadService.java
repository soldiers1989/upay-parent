package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.ReconciliationDocDownloadDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.chk.ChkMerListPo;

/**
 * Created by Guo on 16/8/25.
 * 对账单下载
 */
public class ReconciliationDocDownloadService extends AbstractDipperHandler<ReconciliationDocDownloadDto> {
    private static final Logger LOG = LoggerFactory.getLogger(ReconciliationDocDownloadService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public ReconciliationDocDownloadDto execute(ReconciliationDocDownloadDto dto, Message message) throws Exception {
        LOG.info("对账单下载开始");
        SimpleDateFormat SIM_YMD=new SimpleDateFormat("yyyyMMdd");
        if(StringUtils.isBlank(dto.getOrderDate())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单日期");
        }
        if(StringUtils.isBlank(dto.getOrderType())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "对账单下载的订单类型");
        }
        String reg="^(19|20)\\d{2}"
                + "(((02)((0|2)([0-9])|(1)\\d{1}))|"
                + "((01|03|05|07|08|10|12)((0[1-9])|((1|2)\\d{1})|(30|31)))|"
                + "((04|06|09|11)((0[1-9])|(1|2)\\d{1}|(30))))$";
        Pattern pat=Pattern.compile(reg);
        Matcher mat=pat.matcher(dto.getOrderDate());
        if(!mat.matches()){
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0027);
        }
        String merNo = dto.getMerNo();
        String extensionParty = dto.getExtensionParty();
//        String chkDate = dto.getChkDate();//对账日期
        String orderType = dto.getOrderType();//订单类型
//        Integer pageSize = dto.getPageSize()==null?10:dto.getPageSize();//每页记录数
//        Integer offset = dto.getOffset()==null?0:dto.getOffset();//起始记录
        //查询商户对账明细表
//        ChkMerListPo chkMerListPo = new ChkMerListPo();
        Map<String,Object> map=new HashMap<String,Object>();
        if(StringUtils.isNotBlank(extensionParty)){
        	map.put("extensionParty", extensionParty);
        }else{
        	map.put("merNo",merNo );
        	
        }
//        chkMerListPo.setMerNo(merNo);
        if(StringUtils.isBlank(dto.getSecMerNo())){
            map.put("secMerNo", dto.getSecMerNo());
//            chkMerListPo.setSecMerNo(dto.getSecMerNo());
        }
        try {
            map.put("txnDate", SIM_YMD.parse(dto.getOrderDate()));
//            chkMerListPo.setTxnDate(SIM_YMD.parse(dto.getOrderDate()));
        } catch (ParseException e) {
            LOG.error("日期 转换错误 ");
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0027);
        }
        Date now =new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(SIM_YMD.parse(dto.getOrderDate()));
        Calendar calNow=Calendar.getInstance();
        calNow.setTime(SIM_YMD.parse(SIM_YMD.format(now)));
        if(calNow.compareTo(cal)<=0){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "日期选择");
        }
//        chkMerListPo.setTxnDate(SIM_YMD.parse(dto.getOrderDate()));
//        chkMerListPo.setChkDate(DateUtil.parse(chkDate, "yyyyMMdd"));
        map.put("chkFlag",DateBaseConstants_MER.CHK_FLAG_SUCCESS );
//        chkMerListPo.setChkFlag(DateBaseConstants_MER.CHK_FLAG_SUCCESS);//对账标志为未对账
        List<String> orderStatList=new ArrayList<String>();
        if (DataBaseConstants_PAY.MER_RECON_DOWNLOAD_SUC.equals(orderType)) {
            orderStatList.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
            orderStatList.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP);
            orderStatList.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
            orderStatList.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
            map.put("orderStatList", orderStatList);
//            chkMerListPo.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);//订单状态为成功
        } else if (DataBaseConstants_PAY.MER_RECON_DOWNLOAD_REF.equals(orderType)) {
            orderStatList.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC);
            map.put("orderStatList", orderStatList);
//            chkMerListPo.setTransType(CommonConstants_GNR.TRANS_TYPE_REFUND);//交易类型为退款
        }else if(!DataBaseConstants_PAY.MER_RECON_DOWNLOAD_ALL.equals(orderType)){            
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "对账单下载的订单类型上传");            
        }
        List<ChkMerListPo> chkMerListPoList = daoService.selectList(ChkMerListPo.class.getName().concat(".getListByWhere"),map);

        List<Map<String, Object>> reconciliationDocDownloadDtoList = new ArrayList<>();
        BigDecimal totTransAmt = BigDecimal.ZERO;
        if (null != chkMerListPoList && chkMerListPoList.size() > 0) {
            for (ChkMerListPo merListPo : chkMerListPoList) {
                if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(merListPo.getTransType())){
                    totTransAmt = totTransAmt.subtract(merListPo.getTxnAmt());//累计本次查询汇总金额
                }else{                    
                    totTransAmt = totTransAmt.add(merListPo.getTxnAmt());//累计本次查询汇总金额
                }
                ReconciliationDocDownloadDto rddDto = new ReconciliationDocDownloadDto();
                rddDto.setMerNo(merListPo.getMerNo());
                rddDto.setTxnDate(merListPo.getTxnDate());
                rddDto.setSecMerNo(merListPo.getSecMerNo());
                rddDto.setOrderNo(merListPo.getOrderNo());
                rddDto.setOriOrderNo(merListPo.getOriOrderNo());
                rddDto.setTxnAmt(merListPo.getTxnAmt());
                rddDto.setMerOrder(merListPo.getMerOrder());
                rddDto.setCurr(DataBaseConstants_PAY.T_CCY_CNY);
                rddDto.setOrderStat(merListPo.getOrderStat());
                rddDto.setTransType(merListPo.getTransType());
                rddDto.setMerFeeAmt(merListPo.getMerFeeAmt());
                rddDto.setSecMerFeeAmt(merListPo.getSecMerFeeAmt());
                reconciliationDocDownloadDtoList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(rddDto));
            }
        }
        dto.setCount(chkMerListPoList.size());
        dto.setTotTransAmt(totTransAmt);//本次查询的汇总金额
        dto.setReconciliationDocDownloadDtoList(reconciliationDocDownloadDtoList);

        LOG.info("对账单下载结束");
        return dto;
    }
}
