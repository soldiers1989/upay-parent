/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.UpdateLimitAmtDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.acc.AccLmtCountPo;

/**
 * @author shang
 * 2016年10月14日
 */
public class UpdateLimitAmtService extends AbstractDipperHandler<UpdateLimitAmtDto> {


    @Resource
    IDaoService daoService;
    
    @Resource
    private ISequenceService seqService;
    
    
    @Override
    public UpdateLimitAmtDto execute(UpdateLimitAmtDto dto, Message message) throws Exception {
        if(StringUtils.isBlank(dto.getOrderStat())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单状态");
        }
        if(dto.getAccLmtMap()!=null&&(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(dto.getOrderStat())||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(dto.getOrderStat()))){
            Map<String,Boolean> m=dto.getAccLmtMap();
            BigDecimal pay=dto.getTransAmt();
            Date now=new Date();
            AccLmtCountPo accLmt = new AccLmtCountPo();
            accLmt.setUserId(dto.getUserId());
            accLmt.setLmtAccountFlag(DataBaseConstans_ACC.T_ACC_LMT_COUNT_ACCOUNT_FLAG_ACCSYS);
            accLmt.setSysLmtId(dto.getSysLmtId());
            accLmt = daoService.selectOne("getLastOne", accLmt);
            BigDecimal zero=new BigDecimal(0);
            // 记录或者更新限额
            AccLmtCountPo accLmtNew = new AccLmtCountPo();
            if (accLmt == null) {
                accLmtNew.setTransDate(now);
                accLmtNew.setUserId(dto.getUserId());
                accLmtNew.setLmtAccountFlag(dto.getLmtAccountFlag());
                accLmtNew.setSysLmtId(dto.getSysLmtId());
                accLmtNew.setSysTranslmtId(seqService.generateLmtAmountSeq(dto.getAccType()));
                accLmtNew.setDaySumAmtLimit(!m.get("DA")?zero:pay);
                accLmtNew.setDaySumCountLimit(!m.get("DN")?0:1);
                accLmtNew.setMonSumAmtLimit(!m.get("MA")?zero:pay);
                accLmtNew.setMonSumCountLimit(!m.get("MN")?0:1);
                accLmtNew.setYearSumAmtLimit(!m.get("YA")?zero:pay);
                accLmtNew.setYearSumCountLimit(!m.get("YN")?0:1);
                daoService.insert(accLmtNew);
            } else if(m.get("day")!=null&&m.get("month")!=null&&m.get("year")!=null){
                Date lastDate = accLmt.getTransDate();
//                Map<String, Boolean> map = this.checkDate(lastDate);
                if (m.get("day")) {
                    AccLmtCountPo accParam = new AccLmtCountPo();
                    accParam.setDaySumAmtLimit(!m.get("DA")?zero:accLmt.getDaySumAmtLimit()==null?pay:accLmt.getDaySumAmtLimit().add(pay));
                    accParam.setDaySumCountLimit(!m.get("DN")?0:accLmt.getDaySumCountLimit()==null?0:accLmt.getDaySumCountLimit() + 1);
                    accParam.setMonSumAmtLimit(!m.get("MA")?zero:accLmt.getMonSumAmtLimit()==null?pay:accLmt.getMonSumAmtLimit().add(pay));
                    accParam.setMonSumCountLimit(!m.get("MN")?0:accLmt.getMonSumCountLimit()==null?0:accLmt.getMonSumCountLimit() + 1);
                    accParam.setYearSumAmtLimit(!m.get("YA")?zero:accLmt.getYearSumAmtLimit()==null?pay:accLmt.getYearSumAmtLimit().add(pay));
                    accParam.setYearSumCountLimit(!m.get("YN")?0:accLmt.getYearSumCountLimit()==null?0:accLmt.getYearSumCountLimit()+1);
                    AccLmtCountPo accWhere = new AccLmtCountPo();
                    accWhere.setUserId(dto.getUserId());
                    accWhere.setLmtAccountFlag(DataBaseConstans_ACC.T_ACC_LMT_COUNT_ACCOUNT_FLAG_ACCSYS);
                    accWhere.setSysLmtId(dto.getSysLmtId());
                    accWhere.setTransDate(accLmt.getTransDate());
                    daoService.update(accParam, accWhere);
                } else {
                    accLmtNew.setTransDate(now);
                    accLmtNew.setUserId(dto.getUserId());
                    accLmtNew.setLmtAccountFlag(dto.getLmtAccountFlag());
                    accLmtNew.setSysLmtId(dto.getSysLmtId());
                    accLmtNew.setSysTranslmtId(seqService.generateLmtAmountSeq(dto.getAccType()));
                    accLmtNew.setDaySumAmtLimit(!m.get("DA")?zero:pay);
                    accLmtNew.setDaySumCountLimit(!m.get("DN")?0:1);
                    if (m.get("month")) {
                        accLmtNew.setMonSumAmtLimit(!m.get("MA")?zero:accLmt.getMonSumAmtLimit()==null?pay:accLmt.getMonSumAmtLimit().add(pay));
                        accLmtNew.setMonSumCountLimit(!m.get("MN")?0:accLmt.getMonSumCountLimit()==null?0:accLmt.getMonSumCountLimit() + 1);
                        accLmtNew.setYearSumAmtLimit(!m.get("YA")?zero:accLmt.getYearSumAmtLimit()==null?pay:accLmt.getYearSumAmtLimit().add(pay));
                        accLmtNew.setYearSumCountLimit(!m.get("YN")?0:accLmt.getYearSumCountLimit()==null?0:accLmt.getYearSumCountLimit()+1);
                    } else {
                        accLmtNew.setMonSumAmtLimit(pay);
                        accLmtNew.setMonSumCountLimit(1);
                        if(m.get("year")){
                            accLmtNew.setYearSumAmtLimit(!m.get("YA")?zero:accLmt.getYearSumAmtLimit()==null?pay:accLmt.getYearSumAmtLimit().add(pay));
                            accLmtNew.setYearSumCountLimit(!m.get("YN")?0:accLmt.getYearSumCountLimit()==null?0:accLmt.getYearSumCountLimit()+1);
                        }else{
                            accLmtNew.setYearSumAmtLimit(!m.get("YA")?zero:accLmt.getYearSumAmtLimit()==null?pay:accLmt.getYearSumAmtLimit().add(pay));
                            accLmtNew.setYearSumCountLimit(!m.get("YN")?0:accLmt.getYearSumCountLimit()==null?0:accLmt.getYearSumCountLimit()+1);
                        }
                    }
                    daoService.insert(accLmtNew);
                }
            }            
        }
        return dto;
       

    }
    /**
     * 验证是否为当月当天当年
     */
    public Map<String, Boolean> checkDate(Date date) {
        Date now = new Date();
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(now);
        Calendar lastCal = Calendar.getInstance();
        lastCal.setTime(date);
        boolean day = false;
        boolean month = false;
        boolean year = false;
        if(lastCal.compareTo(nowCal)==0){
            day=true;
            month=true;
            year=true;
        }else if(lastCal.compareTo(nowCal)<0){
            lastCal.add(Calendar.DAY_OF_YEAR, 1);
            if(lastCal.compareTo(nowCal)>=0){
                day=true;
                month=true;
                year=true;
            }else{                
                lastCal.add(Calendar.DAY_OF_YEAR, -1);
                lastCal.add(Calendar.MONTH, 1);
                if(lastCal.compareTo(nowCal)>=0){
                    month=true;
                    year=true;
                }else{
                    lastCal.add(Calendar.MONTH, -1);
                    lastCal.add(Calendar.YEAR, 1);                    
                    if(lastCal.compareTo(nowCal)>=0){
                        year=true;
                    }
                }
            }
        }
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("year", year);
        map.put("month", month);
        map.put("day", day);
        return map;

    }

}
