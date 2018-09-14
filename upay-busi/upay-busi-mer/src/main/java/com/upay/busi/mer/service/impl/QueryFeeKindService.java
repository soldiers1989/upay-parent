package com.upay.busi.mer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.page.QueryResult;
import com.upay.busi.mer.service.dto.QueryFeeKindDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.fee.FeeKindPo;
import com.upay.dao.po.mer.MerBaseInfoPo;


/**
 * 查询一级商户维护的费率规则信息
 * 
 * @author yanzixiong
 * @version 创建时间：2016年8月22日16:33:05
 */
public class QueryFeeKindService extends AbstractDipperHandler<QueryFeeKindDto> {
    @Resource
    private IDaoService daoService;

    @Override
    public QueryFeeKindDto execute(QueryFeeKindDto dto, Message msg) throws Exception {
    	String userId = dto.getUserId();
        String feeCode = dto.getFeeCode();
        String feeName = dto.getFeeName();

        Map<String, Object> whereMap = new HashMap<String, Object>();
        QueryResult<FeeKindPo> feeKindPoListResult = null;// 分页查询时
        List<FeeKindPo> feeKindPoList = null;// 普通查询时
        List<Map<String, Object>> feeKindPoDtoList = new ArrayList<Map<String, Object>>();
        //查询一级商户
        Map<String, Object> whereMap1 = new HashMap<String, Object>();
        whereMap1.put("userId", userId);
        MerBaseInfoPo merbaseinfopo = new MerBaseInfoPo();
        merbaseinfopo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap1);
        if (null == merbaseinfopo) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "一级商户");
        }
        String merNo = merbaseinfopo.getMerNo();

        if (feeCode != null) {
            whereMap.put("feeCode", feeCode);
        }
        if (feeName != null) {
            whereMap.put("feeName", feeName);
        }
        if (merNo != null) {
        	whereMap.put("merNo", merNo);
        }
        int currentNum=dto.getCurrentNum();
		int pageIndex=dto.getPageIndex();
		
        // 无分页就正常查询
        if ((0 ==currentNum && 0 == pageIndex)) {
            feeKindPoList =
                    this.daoService.selectList(FeeKindPo.class.getName() + ".selectByFuzzy", whereMap);
        }// 有分页就分页查询
        else {
            feeKindPoListResult =
                    daoService.selectQueryResult(FeeKindPo.class.getName() + ".selectByFuzzy", whereMap,
                        (pageIndex - 1) * currentNum,currentNum);

            if (null != feeKindPoListResult) {
                feeKindPoList = feeKindPoListResult.getResultlist();
            }
        }
        if (feeKindPoList.size() != 0) {
            for (int i = 0; i < feeKindPoList.size(); i++) {
                dto.setFeeCode(feeKindPoList.get(i).getFeeCode());
                dto.setFeeName(feeKindPoList.get(i).getFeeName());
                dto.setFeeMode(feeKindPoList.get(i).getFeeMode());
                dto.setFixFee(MoneyUtil.moneyFormat(feeKindPoList.get(i).getFixFee()));
                dto.setRationFee(MoneyUtil.moneyFormat(feeKindPoList.get(i).getRationFee()));
                dto.setHighFee(MoneyUtil.moneyFormat(feeKindPoList.get(i).getHighFee()));
                dto.setLowFee(MoneyUtil.moneyFormat(feeKindPoList.get(i).getLowFee()));
                dto.setMemo(feeKindPoList.get(i).getMemo());

                feeKindPoDtoList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(dto));
            }
            dto.setFeeKindList(feeKindPoDtoList);
            if (null != feeKindPoListResult) {
                dto.setTotalNum((int) feeKindPoListResult.getTotalrecord());// 总记录数
            }
        }
        return dto;
    }

}
