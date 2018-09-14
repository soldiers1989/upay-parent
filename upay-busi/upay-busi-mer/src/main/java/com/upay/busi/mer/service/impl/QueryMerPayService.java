package com.upay.busi.mer.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.page.QueryResult;
import com.upay.busi.mer.service.dto.QueryMerPayDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;


/**
 * 查询二级商户支付流水表里所有的支付交易信息
 * 
 * @author yanzixiong
 * @version 创建时间：2016年8月20日10:49:40
 */
public class QueryMerPayService extends AbstractDipperHandler<QueryMerPayDto> {

    static final SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private IDaoService daoService;


    @Override
    public QueryMerPayDto execute(QueryMerPayDto dto, Message msg) throws Exception {
        Date now = SysInfoContext.getSysDate();

        String userId = dto.getUserId();
        String secMerNo = dto.getSecMerNo();
        Date startDate = dto.getStartDate();
        Date endDate = dto.getEndDate();
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户ID");
        }
        if (StringUtils.isBlank(secMerNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "二级商户号");
        }

        if (startDate != null && endDate == null) {
            if (now.compareTo(startDate) < 0) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0006, "查询起始日期", "当前日期");
            }
            endDate = now;
        } else if (startDate != null && endDate != null) {
            if (startDate.compareTo(endDate) > 0) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0006, "查询起始日期", "查询结止日期");
            }
            if (now.compareTo(startDate) < 0) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0006, "查询起始日期", "当前日期");
            }
        }

        Map<String, Object> whereMap = new HashMap<String, Object>();
        QueryResult<PayFlowListPo> payFlowListPoListResult = null;// 分页查询时
        List<PayFlowListPo> payFlowListPoList = null;// 普通查询时
        List<Map<String, Object>> payFlowListPoDtoList = new ArrayList<Map<String, Object>>();

        Map<String, Object> whereMap1 = new HashMap<String, Object>();
        whereMap1.put("userId", userId);
        MerBaseInfoPo merbaseinfopo = new MerBaseInfoPo();
        merbaseinfopo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap1);
        if (null == merbaseinfopo) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "一级商户");
        }

        whereMap.put("merNo", merbaseinfopo.getMerNo());
        whereMap.put("secMerNo", secMerNo);

        int currentNum=dto.getCurrentNum();
		int pageIndex=dto.getPageIndex();
		
        if (startDate == null && endDate == null) {
            // 无分页就正常查询
            if (0 == currentNum && 0 == pageIndex) {
                payFlowListPoList =
                        this.daoService.selectList(PayFlowListPo.class.getName() + ".selectListByDate",
                            whereMap);
            } // 有分页就分页查询
            else {
                payFlowListPoListResult =
                        daoService.selectQueryResult(PayFlowListPo.class.getName() + ".selectListByDate",
                            whereMap, (pageIndex - 1) * currentNum,currentNum);
            }
        } else if (startDate == null && endDate != null) {
            whereMap.put("endDate", endDate);
            if ((0 == currentNum && 0 == pageIndex)) {
                payFlowListPoList =
                        this.daoService.selectList(PayFlowListPo.class.getName() + ".selectListByDate",
                            whereMap);
            }// 有分页就分页查询
            else {
                payFlowListPoListResult =
                        daoService.selectQueryResult(PayFlowListPo.class.getName() + ".selectListByDate",
                            whereMap, (pageIndex - 1) * currentNum,currentNum);
            }
        } else {
            whereMap.put("startDate", startDate);
            whereMap.put("endDate", endDate);
            if ((0 == currentNum && 0 == pageIndex)) {
                payFlowListPoList =
                        this.daoService.selectList(PayFlowListPo.class.getName() + ".selectListByDate",
                            whereMap);
            }// 有分页就分页查询
            else {
                payFlowListPoListResult =
                        daoService.selectQueryResult(PayFlowListPo.class.getName() + ".selectListByDate",
                            whereMap, (pageIndex - 1) * currentNum,currentNum);
            }
        }

        if (null != payFlowListPoListResult) {
            payFlowListPoList = payFlowListPoListResult.getResultlist();
        }

        if (payFlowListPoList.size() != 0) {
            for (int i = 0; i < payFlowListPoList.size(); i++) {
                dto.setOrderNo(payFlowListPoList.get(i).getOrderNo());
                dto.setOrderDes(payFlowListPoList.get(i).getOrderDes());
                dto.setMerNo(payFlowListPoList.get(i).getMerNo());
                dto.setSecMerNo(payFlowListPoList.get(i).getSecMerNo());
                dto.setSrFlag(payFlowListPoList.get(i).getSrFlag());
                dto.setSysDate(payFlowListPoList.get(i).getSysDate());
                dto.setTransSubSeq(payFlowListPoList.get(i).getTransSubSeq());
                dto.setRouteCode(payFlowListPoList.get(i).getRouteCode());
                dto.setPayUserId(payFlowListPoList.get(i).getPayUserId());
                dto.setPayerAcctType(payFlowListPoList.get(i).getPayerAcctType());
                dto.setPayerAcctNo(payFlowListPoList.get(i).getPayerAcctNo());
                dto.setPayerName(payFlowListPoList.get(i).getPayerName());
                dto.setPayerOrgName(payFlowListPoList.get(i).getPayerOrgName());
                dto.setPayerBankNo(payFlowListPoList.get(i).getPayerBankNo());
                dto.setPayerBankName(payFlowListPoList.get(i).getPayerBankName());
                dto.setPayeeAcctType(payFlowListPoList.get(i).getPayeeAcctType());
                dto.setPayeeAcctNo(payFlowListPoList.get(i).getPayeeAcctNo());
                dto.setPayeeOrgName(payFlowListPoList.get(i).getPayeeOrgName());
                dto.setPayeeBankNo(payFlowListPoList.get(i).getPayeeBankNo());
                dto.setPayeeBankName(payFlowListPoList.get(i).getPayeeBankName());
                dto.setCcy(payFlowListPoList.get(i).getCcy());
                dto.setTransAmt(payFlowListPoList.get(i).getTransAmt());
                dto.setFeeAmt(payFlowListPoList.get(i).getFeeAmt());
                dto.setTransStat(payFlowListPoList.get(i).getTransStat());
                dto.setRouteTransStat(payFlowListPoList.get(i).getRouteTransStat());
                dto.setTransTime(payFlowListPoList.get(i).getTransTime());
                dto.setRouteDate(payFlowListPoList.get(i).getRouteDate());
                dto.setRouteSeq(payFlowListPoList.get(i).getRouteSeq());
                dto.setLastUpdateTime(payFlowListPoList.get(i).getLastUpdateTime());
                dto.setChkFlag(payFlowListPoList.get(i).getChkFlag());
                dto.setChkBatchNo(payFlowListPoList.get(i).getChkBatchNo());
                dto.setChkDate(payFlowListPoList.get(i).getChkDate());
                dto.setOpenId(payFlowListPoList.get(i).getOpenId());

                payFlowListPoDtoList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(dto));
            }
            dto.setPayFlowList(payFlowListPoDtoList);
            if (null != payFlowListPoListResult) {
                dto.setTotalNum((int) payFlowListPoListResult.getTotalrecord());// 总记录数
            }
        }
        return dto;
    }

}
