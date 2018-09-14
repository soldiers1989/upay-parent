package com.upay.busi.pay.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.upay.busi.pay.service.dto.LimitAmtCheckDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_FEE;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.acc.AccLmtCountPo;
import com.upay.dao.po.acc.AccSysLmtBookPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.mer.MerTransCtrlPo;
import com.upay.dao.po.mer.MerTransLimitPo;
import com.upay.dao.po.mer.MerTransTemplatePo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.trans.TransTemplateCtrlPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 交易限额检查并更改限额
 * 
 * @author shangqiankun
 * @version 创建时间：2016年8月2日 下午3:05:12
 */
public class LimitAmtCheckService extends AbstractDipperHandler<LimitAmtCheckDto> {
	private static final Logger logger = LoggerFactory.getLogger(LimitAmtCheckService.class);
    @Resource
    private IDaoService daoService;
    @Resource
    private ISequenceService seqService;


    @Override
    public LimitAmtCheckDto execute(LimitAmtCheckDto dto, Message message) throws Exception {
        BigDecimal transAmt = dto.getTransAmt();
        String merNo = dto.getMerNo();
        String accType = dto.getAccType();
        String userId = dto.getUserId();
        String payType = dto.getPayType();
        String transCode = dto.getTransCode();
        String chnlId = dto.getChnlId();
        String sysLmtType = dto.getSysLmtType();// 需要在flow中初始化
//        String lmtAccountFlag = dto.getLmtAccountFlag();// 需要在flow中初始化
        String checkusr=dto.getCheckUserLmt();
        String merTransCtrlCode = null;
        logger.info("checkusr   值为:"+checkusr);
        String dcFlag = dto.getDcFlag();
        if (transAmt == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付金额");
        }
        if (StringUtils.isBlank(dto.getTransType())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易类型");
        }
        Date now = new Date();
        if (StringUtils.isNotBlank(merNo)) {
        	//商户日累计限额检查
        	MerTransTemplatePo templatePo = new MerTransTemplatePo();
        	templatePo.setMerNo(merNo);
        	templatePo = daoService.selectOne(templatePo);
        	if(templatePo != null){
        		BigDecimal newAcm = templatePo.getDailyAcmlativeAmt().add(transAmt);
        		//日累计金额大于日累计限额
        		if(newAcm.compareTo(templatePo.getDailyAcmlativeLimit()) > 0){
        			ExInfo.throwDipperEx(AppCodeDict.BISPAY0123,templatePo.getDailyAcmlativeAmt().toString(),templatePo.getDailyAcmlativeLimit().toString());
        		}
        	}
            if (StringUtils.isBlank(payType)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付方式");
            }
            MerBaseInfoPo merBase=new  MerBaseInfoPo();
            merBase.setMerNo(merNo);
            merBase=daoService.selectOne(merBase);
            if(merBase==null){
                ExInfo.throwDipperEx(AppCodeDict.BISMER0018, merNo);
            }
            if(StringUtils.isNotBlank(merBase.getParentMerNo())){
                merNo=merBase.getParentMerNo();
            }
            MerTransCtrlPo mer = new MerTransCtrlPo();
            mer.setMerNo(merNo);
            mer.setTransCode(transCode);
            mer.setPayCardType(accType);
            mer.setPayType(payType);
            if(CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(dto.getTransType())){
                if (StringUtils.isBlank(dto.getPayServicType())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付服务类型");
                }
                mer.setPayServic(dto.getPayServicType());
            }
            mer.setChnlId(chnlId);
            mer.setStatus(DataBaseConstants_PAY.T_MER_TRANS_CTRL_STAT_OPEN);
            mer = daoService.selectOne(mer);
            if (mer == null) {
            	if(templatePo == null){
            		ExInfo.throwDipperEx(AppCodeDict.BISMER0037, merNo);
            	}
            	TransTemplateCtrlPo ctrlPo = new TransTemplateCtrlPo();
            	ctrlPo.setTemplateId(templatePo.getTemplateId());
            	ctrlPo.setTransCode(transCode);
            	ctrlPo.setPayCardType(accType);
            	ctrlPo.setPayType(payType);
            	if(CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(dto.getTransType())){
                    if (StringUtils.isBlank(dto.getPayServicType())) {
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付服务类型");
                    }
                    ctrlPo.setPayServic(dto.getPayServicType());
                }
            	ctrlPo.setChnlId(chnlId);
            	ctrlPo.setStatus(DataBaseConstants_PAY.T_MER_TRANS_CTRL_STAT_OPEN);
            	List<TransTemplateCtrlPo> list = daoService.selectList(ctrlPo);
            	if(list.size() == 0 ){
            		ExInfo.throwDipperEx(AppCodeDict.BISMER0009, merNo);
            	}
            	if(StringUtils.isNotBlank(templatePo.getMerTransCtrlCode())){
            		merTransCtrlCode = templatePo.getMerTransCtrlCode();
            	}
            }else{
            	merTransCtrlCode = mer.getMerTransCtrlCode();
            }
            
            if(StringUtils.isNotBlank(merTransCtrlCode)){                
                // 商户限额检查
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("merTransCtrlCode", merTransCtrlCode);
                //map.put("now", now); 目前暂时删除对时间的控制
                map.put("status", DateBaseConstants_MER.MER_TRANS_LIMIT_STAT_OPEN);
                if(DataBaseConstants_FEE.CHECK_USER_LMT_Y.equals(checkusr)){
                    if (StringUtils.isBlank(userId)) {
                    	//当第三方
                    	map.put("userType",DataBaseConstants_PAY.USER_TYPE_PLAIN);
                    }else{
                    	MerBaseInfoPo mm=new MerBaseInfoPo();
                        mm.setUserId(dto.getUserId());
                        mm.setMerState(DateBaseConstants_MER.MER_STAT_NORMAL);
                        List<MerBaseInfoPo> mmList=daoService.selectList(mm);
                        if(mmList==null||mmList.size()==0){
                            map.put("userType",DataBaseConstants_PAY.USER_TYPE_PLAIN);
                        }else{
                            map.put("userType",DataBaseConstants_PAY.USER_TYPE_MER);
                        }
                    }
                    
                    map.put("userTypeAll",DataBaseConstants_PAY.USER_TYPE_ALL);
                }else{                    
                    if(StringUtils.isBlank(dto.getUserId())){
                        if (StringUtils.isBlank(payType)) {
                            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付方式");
                        }
                        if(DataBaseConstants_PAY.T_PAY_TYPE_WEIXIN_QR_CODE.equals(payType)
                                ||DataBaseConstants_PAY.T_PAY_TYPE_PUBLIC_NO.equals(payType)
                                ||DataBaseConstants_PAY.T_PAY_TYPE_CARD_PAY.equals(payType)
                                ||DataBaseConstants_PAY.T_PAY_TYPE_ALIPAY_QR_CODE.equals(payType)
                                ||DataBaseConstants_PAY.T_PAY_TYPE_ALIPAY_CARD_PAY.equals(payType)
                                ||DataBaseConstants_PAY.T_PAY_TYPE_ACP_WR_CODE.equals(payType)
                                ||DataBaseConstants_PAY.T_PAY_TYPE_ACP_QR_CODE.equals(payType)
                                ||DataBaseConstants_PAY.T_PAY_TYPE_ALIPAY_TOGETHER.equals(payType)
                                ||DataBaseConstants_PAY.T_PAY_TYPE_UNIONPAY_GATEWAY.equals(payType)
                                ||DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_PERSONAL.equals(payType)
                                ||DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_COMPANY.equals(payType)){
                            map.put("userType",DataBaseConstants_PAY.USER_TYPE_PLAIN);
                            map.put("userTypeAll",DataBaseConstants_PAY.USER_TYPE_ALL);
                        }else{
                        	logger.info("用ID 不能为空 1");
                            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户id");
                        }
                    }else{
                        MerBaseInfoPo mm=new MerBaseInfoPo();
                        mm.setUserId(dto.getUserId());
                        mm.setMerState(DateBaseConstants_MER.MER_STAT_NORMAL);
                        List<MerBaseInfoPo> mmList=daoService.selectList(mm);
                        if(mmList==null||mmList.size()==0){
                            map.put("userType",DataBaseConstants_PAY.USER_TYPE_PLAIN);
                        }else{
                            map.put("userType",DataBaseConstants_PAY.USER_TYPE_MER);
                        }
                        map.put("userTypeAll",DataBaseConstants_PAY.USER_TYPE_ALL);
                    }
                }
                List<MerTransLimitPo> merLimitList = daoService.selectList(MerTransLimitPo.class.getName().concat(".getMerTransLimitByCode"),map);
                if (merLimitList != null&&merLimitList.size()>0) {
                    if(merLimitList.size()>1){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0043,"商户限额配置");
                    }
                    if(merLimitList.get(0).getTransLimit()==null){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户单笔限额金额");
                    }
                    if (merLimitList.get(0).getTransLimit().subtract(transAmt).compareTo(BigDecimal.ZERO) < 0) {
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0013, "商户单笔限额");
                    }
                }
            }
        }
        //判断是否使用用户限额
        if(DataBaseConstants_FEE.CHECK_USER_LMT_Y.equals(checkusr) && StringUtils.isNotBlank(userId)){            
            if (StringUtils.isBlank(dcFlag)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "借贷标识");
            }
            if (StringUtils.isBlank(checkusr)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "账户限额标识");
            }
            if (StringUtils.isBlank(accType)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "账户类型");
            }
            if (StringUtils.isBlank(userId)) {
            	logger.info("用ID 不能为空 2");
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户id");
            }
            UsrRegInfoPo user = new UsrRegInfoPo();
            user.setUserId(userId);
            user = daoService.selectOne(user);
            if (user == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "用户：" + userId);
            }
            String userLevel = user.getUserCertLevel();
            if (StringUtils.isBlank(userLevel)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户认证等级");
            }
            // 用户限额
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("accType", accType);
            map.put("userLevel", userLevel);
            map.put("dcFlag", dcFlag);
            map.put("transCode", transCode);
            map.put("chnlId", chnlId);
            map.put("lmtType", sysLmtType);
            map.put("lmtStat", DataBaseConstans_ACC.T_ACC_SYS_LMT_BOOK_LMT_STAT_OPEN);
            map.put("now", now);
            MerBaseInfoPo mm=new MerBaseInfoPo();
            mm.setUserId(dto.getUserId());
            mm.setMerState(DateBaseConstants_MER.MER_STAT_NORMAL);
            List<MerBaseInfoPo> mmList=daoService.selectList(mm);
            if(mmList==null||mmList.size()==0){
                map.put("userType",DataBaseConstants_PAY.USER_TYPE_PLAIN);
            }else{
                map.put("userType",DataBaseConstants_PAY.USER_TYPE_MER);
            }
            map.put("userTypeAll",DataBaseConstants_PAY.USER_TYPE_ALL);
            List<AccSysLmtBookPo> accLmtList = daoService.selectList(AccSysLmtBookPo.class.getName().concat(".getAccSysLmt"),map);
            if (accLmtList != null &&accLmtList.size()>0) {
                if(accLmtList.size()>1){
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0043,"账户限额配置");
                }
                updateAccLmtCount(dto, accLmtList.get(0), transAmt);
            }
        }
        return dto;
    }


    /**
     * 限额检查
     * 
     * @param dto
     * @param acc
     * @param now
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public void updateAccLmtCount(LimitAmtCheckDto dto, AccSysLmtBookPo acc, BigDecimal pay) throws IllegalAccessException, InvocationTargetException {
        String errorCode=null;
        if(DataBaseConstants_PAY.SYS_LMT_TYPE_GENERAL.equals(acc.getLmtType())){
            errorCode=AppCodeDict.BISPAY0013;
        }else if(DataBaseConstants_PAY.SYS_LMT_TYPE_MOBILE.equals(acc.getLmtType())){
            errorCode=AppCodeDict.BISPAY0038;
        }else{
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0003, "限额类型");
        }
        if (acc.getSingleAmtLimit() !=null&&(acc.getSingleAmtLimit().subtract(pay).compareTo(BigDecimal.ZERO)<0)) {
            ExInfo.throwDipperEx(errorCode, "用户单笔限额");
        }
        AccLmtCountPo accLmt = new AccLmtCountPo();
        accLmt.setUserId(dto.getUserId());
        accLmt.setLmtAccountFlag(DataBaseConstans_ACC.T_ACC_LMT_COUNT_ACCOUNT_FLAG_ACCSYS);
        accLmt.setSysLmtId(acc.getSysLmtId());
        accLmt = daoService.selectOne("getLastOne", accLmt);
        Map<String, Boolean> m = this.getMinLimtAmt(accLmt, acc, pay);
        if(!m.get("DAO")){            
            ExInfo.throwDipperEx(errorCode, "日累计金额");
        }
        if(!m.get("DNO")){            
            ExInfo.throwDipperEx(errorCode, "日累计交易笔数");
        }
        if(!m.get("MAO")){            
            ExInfo.throwDipperEx(errorCode, "月累计金额");
        }
        if(!m.get("MNO")){            
            ExInfo.throwDipperEx(errorCode, "月累计交易笔数");
        }
        if(!m.get("YAO")){            
            ExInfo.throwDipperEx(errorCode, "年累计金额");
        }
        if(!m.get("YNO")){            
            ExInfo.throwDipperEx(errorCode, "年累计交易笔数");
        }

        dto.setAccLmtMap(m);
        dto.setSysLmtId(acc.getSysLmtId());
//        BigDecimal zero=new BigDecimal(0);
//        // 记录或者更新限额
//        AccLmtCountPo accLmtNew = new AccLmtCountPo();
//        AccLmtCountDto accLmtCount=new AccLmtCountDto();
//        Map<String,Object> mapAccLmt=new HashMap<String,Object>();
//        if (accLmt == null) {
//            accLmtNew.setTransDate(now);
//            accLmtNew.setUserId(dto.getUserId());
//            accLmtNew.setLmtAccountFlag(dto.getLmtAccountFlag());
//            accLmtNew.setSysLmtId(acc.getSysLmtId());
//            accLmtNew.setSysTranslmtId(seqService.generateLmtAmountSeq(dto.getAccType()));
//            accLmtNew.setDaySumAmtLimit(m.get("DA")?zero:pay);
//            accLmtNew.setDaySumCountLimit(m.get("DN")?0:1);
//            accLmtNew.setMonSumAmtLimit(m.get("MA")?zero:pay);
//            accLmtNew.setMonSumCountLimit(m.get("MN")?0:1);
//            accLmtNew.setYearSumAmtLimit(m.get("YA")?zero:pay);
//            accLmtNew.setYearSumCountLimit(m.get("YN")?0:1);
//            BeanUtils.copyProperties(mapAccLmt,accLmtNew);
//            dto.setFeeInOrUp(CommonConstants_GNR.FEE_DETAIL_INSERT);
//            dto.setNewAccLmtCountDto(accLmtCount);
////            daoService.insert(accLmtNew);
//        } else {
//            Date lastDate = accLmt.getTransDate();
//            Map<String, Boolean> map = this.checkDate(lastDate);
//            if (map.get("day")) {
//                AccLmtCountPo accParam = new AccLmtCountPo();
//                accParam.setDaySumAmtLimit(m.get("DA")?zero:accLmt.getDaySumAmtLimit()==null?pay:accLmt.getDaySumAmtLimit().add(pay));
//                accParam.setDaySumCountLimit(m.get("DN")?0:accLmt.getDaySumCountLimit()==null?0:accLmt.getDaySumCountLimit() + 1);
//                accParam.setMonSumAmtLimit(m.get("MA")?zero:accLmt.getMonSumAmtLimit()==null?pay:accLmt.getMonSumAmtLimit().add(pay));
//                accParam.setMonSumCountLimit(m.get("MN")?0:accLmt.getMonSumCountLimit()==null?0:accLmt.getMonSumCountLimit() + 1);
//                accParam.setYearSumAmtLimit(m.get("YA")?zero:accLmt.getYearSumAmtLimit()==null?pay:accLmt.getYearSumAmtLimit().add(pay));
//                accParam.setYearSumCountLimit(m.get("YN")?0:accLmt.getYearSumCountLimit()==null?0:accLmt.getYearSumCountLimit()+1);
//                AccLmtCountPo accWhere = new AccLmtCountPo();
//                accWhere.setUserId(dto.getUserId());
//                accWhere.setLmtAccountFlag(DataBaseConstans_ACC.T_ACC_LMT_COUNT_ACCOUNT_FLAG_ACCSYS);
//                accWhere.setSysLmtId(acc.getSysLmtId());
//                accWhere.setTransDate(accLmt.getTransDate());
//                BeanUtils.copyProperties(accLmtCount,accParam);
//                dto.setParmAccLmtCountDto(accLmtCount);
//                accLmtCount=new AccLmtCountDto();
//                BeanUtils.copyProperties(accLmtCount,accWhere);
//                dto.setWhereAccLmtCountDto(accLmtCount);
//                dto.setFeeInOrUp(CommonConstants_GNR.FEE_DETAIL_UPDATE);
////                daoService.update(accParam, accWhere);
//            } else {
//                accLmtNew.setTransDate(now);
//                accLmtNew.setUserId(dto.getUserId());
//                accLmtNew.setLmtAccountFlag(dto.getLmtAccountFlag());
//                accLmtNew.setSysLmtId(acc.getSysLmtId());
//                accLmtNew.setSysTranslmtId(seqService.generateLmtAmountSeq(dto.getAccType()));
//                accLmtNew.setDaySumAmtLimit(m.get("DA")?zero:pay);
//                accLmtNew.setDaySumCountLimit(m.get("DN")?0:1);
//                if (map.get("month")) {
//                    accLmtNew.setMonSumAmtLimit(m.get("MA")?zero:accLmt.getMonSumAmtLimit()==null?pay:accLmt.getMonSumAmtLimit().add(pay));
//                    accLmtNew.setMonSumCountLimit(m.get("MN")?0:accLmt.getMonSumCountLimit()==null?0:accLmt.getMonSumCountLimit() + 1);
//                    accLmtNew.setYearSumAmtLimit(m.get("YA")?zero:accLmt.getYearSumAmtLimit()==null?pay:accLmt.getYearSumAmtLimit().add(pay));
//                    accLmtNew.setYearSumCountLimit(m.get("YN")?0:accLmt.getYearSumCountLimit()==null?0:accLmt.getYearSumCountLimit()+1);
//                } else {
//                    accLmtNew.setMonSumAmtLimit(pay);
//                    accLmtNew.setMonSumCountLimit(1);
//                    if(map.get("year")){
//                        accLmtNew.setYearSumAmtLimit(m.get("YA")?zero:accLmt.getYearSumAmtLimit()==null?pay:accLmt.getYearSumAmtLimit().add(pay));
//                        accLmtNew.setYearSumCountLimit(m.get("YN")?0:accLmt.getYearSumCountLimit()==null?0:accLmt.getYearSumCountLimit()+1);
//                    }else{
//                        accLmtNew.setYearSumAmtLimit(m.get("YA")?zero:accLmt.getYearSumAmtLimit()==null?pay:accLmt.getYearSumAmtLimit().add(pay));
//                        accLmtNew.setYearSumCountLimit(m.get("YN")?0:accLmt.getYearSumCountLimit()==null?0:accLmt.getYearSumCountLimit()+1);
//                    }
//                }
//                BeanUtils.copyProperties(accLmtCount,accLmtNew);
//                dto.setFeeInOrUp(CommonConstants_GNR.FEE_DETAIL_INSERT);
//                dto.setNewAccLmtCountDto(accLmtCount);
////                daoService.insert(accLmtNew);
//            }
//        }
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


    /**
     * 验证用户限额和支付次数是否超限
     * 
     * @param usr
     * @param sys
     * @param payAmt
     * @return
     */
    public Map<String, Boolean> getMinLimtAmt(AccLmtCountPo usr, AccSysLmtBookPo sys, BigDecimal payAmt) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        if (usr != null) {
            BigDecimal zero = new BigDecimal(0);
            Map<String, Boolean> mapDate = this.checkDate(usr.getTransDate());
            if (!mapDate.get("day")) {
                usr.setDaySumAmtLimit(zero);
                usr.setDaySumCountLimit(0);
                usr.setMonSumAmtLimit(zero);
                usr.setMonSumCountLimit(0);
                usr.setYearSumAmtLimit(zero);
                usr.setYearSumCountLimit(0);
            }else if (!mapDate.get("month")) {
                usr.setMonSumAmtLimit(zero);
                usr.setMonSumCountLimit(0);
                usr.setYearSumAmtLimit(zero);
                usr.setYearSumCountLimit(0);
            }else if (!mapDate.get("year")) {
                usr.setYearSumAmtLimit(zero);
                usr.setYearSumCountLimit(0);
            }
            map.putAll(mapDate);
        }
        BigDecimal max=new BigDecimal(Integer.MAX_VALUE);
        BigDecimal sysDayAmt = null;
        BigDecimal sysMonAmt = null;
        BigDecimal sysYearAmt = null;
        int sysDayNum = Integer.MAX_VALUE;
        int sysMonNum = Integer.MAX_VALUE;
        int sysYearNum = Integer.MAX_VALUE;
        if(sys.getDaySumAmtLimit()!=null){
            sysDayAmt=sys.getDaySumAmtLimit();
            map.put("DA", true);
        }else{
            sysDayAmt=max;
            map.put("DA", false);
        }
        if(sys.getMonSumAmtLimit()!=null){
            sysMonAmt=sys.getMonSumAmtLimit();
            map.put("MA", true);
        }else{
            sysMonAmt=max;
            map.put("MA", false);
        }
        if(sys.getYearSumAmtLimit()!=null){
            sysYearAmt=sys.getYearSumAmtLimit();
            map.put("YA", true);
        }else{
            map.put("YA", false);
            sysYearAmt=max;
        }
        if(sys.getDaySumTimesLimit()!=null){
            sysDayNum = sys.getDaySumTimesLimit().intValue();
            map.put("DN", true);
        }else{
            map.put("DN", false);
        }
        if(sys.getMonSumTimesLimit()!=null){            
            sysMonNum = sys.getMonSumTimesLimit().intValue();
            map.put("MN", true);
        }else{
            map.put("MN", false);
        }
        if(sys.getYearSumTimesLimit()!=null){
            sysYearNum = sys.getYearSumTimesLimit().intValue();
            map.put("YN", true);
        }else{
            map.put("YN", false);
            
        }
        BigDecimal defau = new BigDecimal(0);
        BigDecimal usrDayAmt =defau;
        BigDecimal usrMonAmt =defau;
        BigDecimal usrYearAmt =defau;
        int usrDayNum=0;
        int usrMonNum=0;
        int usrYearNum=0;
        if(usr!=null){
            usrDayAmt=usr.getDaySumAmtLimit()==null ? defau : usr.getDaySumAmtLimit();
            usrMonAmt=usr.getMonSumAmtLimit()==null ? defau : usr.getMonSumAmtLimit();
            usrYearAmt=usr.getYearSumAmtLimit()==null ? defau : usr.getYearSumAmtLimit();
            usrDayNum=usr.getDaySumCountLimit()==null?0:usr.getDaySumCountLimit();
            usrMonNum=usr.getMonSumCountLimit()==null?0:usr.getMonSumCountLimit();
            usrYearNum=usr.getYearSumCountLimit()==null?0:usr.getYearSumCountLimit();
                    
        }
        BigDecimal minAmt = sysDayAmt.subtract(usrDayAmt).compareTo(sysMonAmt.subtract(usrMonAmt))>=0 
                ? (sysMonAmt.subtract(usrMonAmt).compareTo(sysYearAmt.subtract(usrYearAmt))>=0
                ?sysYearAmt.subtract(usrYearAmt):sysMonAmt.subtract(usrMonAmt)) : sysDayAmt.subtract(usrDayAmt).compareTo(sysYearAmt.subtract(usrYearAmt))>=0
                ?sysYearAmt.subtract(usrYearAmt):sysDayAmt.subtract(usrDayAmt);
        int minNum = (sysDayNum - usrDayNum) >= (sysMonNum - usrMonNum) 
                ? ((sysMonNum - usrMonNum)>=(sysYearNum - usrYearNum)
                ?(sysYearNum - usrYearNum): (sysMonNum - usrMonNum)) : (sysDayNum - usrDayNum)>=(sysYearNum - usrYearNum)
                ?(sysYearNum - usrYearNum):(sysDayNum - usrDayNum);
//        map.put("minAmt", minAmt.compareTo(payAmt)>=0 ? true : false);
//        map.put("minNum", minNum >= 1 ? true : false);
        map.put("DAO", true);
        map.put("DNO", true);
        map.put("MAO", true);
        map.put("MNO", true);
        map.put("YAO", true);
        map.put("YNO", true);
        if(minAmt.compareTo(sysDayAmt.subtract(usrDayAmt))==0&&minAmt.compareTo(payAmt)<0){
            map.put("DAO", false);
        }else if(minAmt.compareTo(sysMonAmt.subtract(usrMonAmt))==0&&minAmt.compareTo(payAmt)<0){
            map.put("MAO", false);
        }else if(minAmt.compareTo(sysYearAmt.subtract(usrYearAmt))==0&&minAmt.compareTo(payAmt)<0){
            map.put("YAO", false);
        }
        if(minNum==(sysDayNum - usrDayNum)&&minNum<1){
            map.put("DNO", false);
        }else if(minNum==(sysMonNum - usrMonNum)&&minNum<1){
            map.put("MNO", false);
        }else if(minNum==(sysYearNum - usrYearNum)&&minNum<1){
            map.put("YNO", false);
        }
        return map;
    }

}
