package com.upay.busi.mer.service.impl;

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
import com.upay.busi.mer.service.dto.SecondQueryFeeDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.fee.FeeGetPo;
import com.upay.dao.po.fee.FeeKindPo;
import com.upay.dao.po.mer.MerBaseInfoPo;


/**
 * 查询一级商户维护的二级商户的手续费收取方法
 * 
 * @author yanzixiong
 * @version 创建时间：2016年8月18日09:56:34
 */
public class SecondQueryFeeService extends AbstractDipperHandler<SecondQueryFeeDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public SecondQueryFeeDto execute(SecondQueryFeeDto dto, Message msg) throws Exception {
    	String feeId = dto.getFeeId();
        String userId = dto.getUserId();
        String secMerNo = dto.getSecMerNo();
        Date sysDate = SysInfoContext.getSysDate();
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户ID");
        }

        QueryResult<FeeGetPo> feeGetPoListResult = null;// 分页查询时
        List<FeeGetPo> feeGetPoList = null;// 普通查询时
        List<Map<String, Object>> feeGetPoDtoList = new ArrayList<Map<String, Object>>();

        // 查询一级商户商户号
        Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("userId", userId);
        MerBaseInfoPo merbaseinfopo = new MerBaseInfoPo();
        merbaseinfopo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap);
        if (null == merbaseinfopo) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "一级商户");
        }

        FeeGetPo feeGetPo = new FeeGetPo();
        feeGetPo.setSecMerNo(secMerNo);
        feeGetPo.setMerNo(merbaseinfopo.getMerNo());
        if (StringUtils.isNotBlank(feeId)) {
        	feeGetPo.setFeeId(feeId);
		}
        // 无分页就正常查询
        int currentNum = dto.getCurrentNum();
        int pageIndex = dto.getPageIndex();
        if (0 == currentNum && 0 == pageIndex) {
            feeGetPoList = daoService.selectList(feeGetPo);
        }// 有分页就分页查询
        else {
            feeGetPoListResult =
                    daoService.selectQueryResult(feeGetPo, (pageIndex - 1) * currentNum, currentNum);

            if (null != feeGetPoListResult) {
                feeGetPoList = feeGetPoListResult.getResultlist();
            }
        }

        if (feeGetPoList.size() != 0) {
            for (int i = 0; i < feeGetPoList.size(); i++) {
                String feeCode = feeGetPoList.get(i).getFeeCode();
                if (StringUtils.isBlank(feeCode)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "feeCode");
                }
                FeeKindPo feeKindPo = new FeeKindPo();
                feeKindPo.setFeeCode(feeCode);
                feeKindPo = daoService.selectOne(feeKindPo);
                if (feeKindPo == null) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0032, "该数据");
                }
                dto.setFeeName(feeKindPo.getFeeName());

                String merNo = feeGetPoList.get(i).getSecMerNo();
                if (StringUtils.isBlank(merNo)) {
                    continue;
                }
                MerBaseInfoPo merBaseInfoPo1 = new MerBaseInfoPo();
                merBaseInfoPo1.setMerNo(merNo);
                merBaseInfoPo1 = daoService.selectOne(merBaseInfoPo1);
                if (merBaseInfoPo1 == null) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0032, "该数据");
                }
                dto.setMerName(merBaseInfoPo1.getMerName());

                dto.setFeeId(feeGetPoList.get(i).getFeeId());
                dto.setFeeMethodName(feeGetPoList.get(i).getFeeName());
                dto.setAcctType(feeGetPoList.get(i).getAcctType());
                dto.setChlId(feeGetPoList.get(i).getChnlId());
                dto.setMerNo(feeGetPoList.get(i).getMerNo());
                dto.setSecMerNo(feeGetPoList.get(i).getSecMerNo());
                dto.setTxnCode(feeGetPoList.get(i).getTransCode());
                dto.setFeeCode(feeGetPoList.get(i).getFeeCode());
                dto.setAssCode(feeGetPoList.get(i).getAssCode());
                dto.setStartDate(DateUtil.format(feeGetPoList.get(i).getStartDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
                dto.setEndDate(DateUtil.format(feeGetPoList.get(i).getEndDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
                dto.setFreeCycle(feeGetPoList.get(i).getFreeCycle());
                dto.setFreeCount(feeGetPoList.get(i).getFreeCount());
                dto.setPerFee(feeGetPoList.get(i).getPerFee());
                dto.setGetType(feeGetPoList.get(i).getGetType());
                dto.setLastUpdUserId(feeGetPoList.get(i).getLastUpdUserId());
				if (null != feeGetPoList.get(i).getLastUpdateTime()) {
					dto.setLastUpdateTime(DateUtil.format(feeGetPoList.get(i)
							.getLastUpdateTime(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS));
				}
                dto.setMemo(feeGetPoList.get(i).getMemo());
                dto.setRouteCode(feeGetPoList.get(i).getRouteCode());
                if (sysDate.compareTo(feeGetPoList.get(i).getStartDate()) < 0) {
                    dto.setDateState(DateBaseConstants_MER.DATE_STATE_1);
                } else if (sysDate.compareTo(feeGetPoList.get(i).getEndDate()) > 0) {
                    dto.setDateState(DateBaseConstants_MER.DATE_STATE_3);
                } else {
                    dto.setDateState(DateBaseConstants_MER.DATE_STATE_2);
                }

                feeGetPoDtoList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(dto));
            }
            dto.setFeeGetList(feeGetPoDtoList);
            if (null != feeGetPoListResult) {
                dto.setTotalNum((int) feeGetPoListResult.getTotalrecord());// 总记录数
            }
        }

        return dto;
    }
}
