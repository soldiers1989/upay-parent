package com.upay.busi.mer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.page.QueryResult;
import com.upay.busi.mer.service.dto.QueryFeeAssDto;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.fee.FeeAssPo;

/**
 * 查询手续费分润方法
 * 
 * @author yanzixiong
 * @version 创建时间：2016年11月10日11:19:44
 */
public class QueryFeeAssService extends AbstractDipperHandler<QueryFeeAssDto> {

	@Resource
    private IDaoService daoService;
	
	@Override
	public QueryFeeAssDto execute(QueryFeeAssDto dto, Message msg)
			throws Exception {
		String assCode = dto.getAssCode();
		String assName = dto.getAssName();
		
		Map<String, Object> whereMap = new HashMap<String, Object>();
        QueryResult<FeeAssPo> feeAssPoListResult = null;// 分页查询时
        List<FeeAssPo> feeAssPoList = null;// 普通查询时
        List<Map<String, Object>> feeAssPoDtoList = new ArrayList<Map<String, Object>>();
		
        if (assCode != null) {
            whereMap.put("assCode", assCode);
        }
        if (assName != null) {
            whereMap.put("assName", assName);
        }
        int currentNum=dto.getCurrentNum();
		int pageIndex=dto.getPageIndex();
		
		// 无分页就正常查询
        if ((0 ==currentNum && 0 == pageIndex)) {
        	feeAssPoList =
                    this.daoService.selectList(FeeAssPo.class.getName() + ".selectByFuzzy", whereMap);
        }// 有分页就分页查询
        else {
        	feeAssPoListResult =
                    daoService.selectQueryResult(FeeAssPo.class.getName() + ".selectByFuzzy", whereMap,
                        (pageIndex - 1) * currentNum,currentNum);

            if (null != feeAssPoListResult) {
            	feeAssPoList = feeAssPoListResult.getResultlist();
            }
        }
        if (feeAssPoList.size() != 0) {
            for (int i = 0; i < feeAssPoList.size(); i++) {
                dto.setAssCode(feeAssPoList.get(i).getAssCode());
                dto.setAssName(feeAssPoList.get(i).getAssName());
                dto.setAssId(feeAssPoList.get(i).getAssId());
                dto.setAssKind(feeAssPoList.get(i).getAssKind());
                dto.setAssType(feeAssPoList.get(i).getAssType());
                dto.setAssRate(MoneyUtil.moneyFormat(feeAssPoList.get(i).getAssRate()));
                dto.setFixAmt(MoneyUtil.moneyFormat(feeAssPoList.get(i).getFixAmt()));

                feeAssPoDtoList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(dto));
            }
            dto.setFeeAssList(feeAssPoDtoList);
            if (null != feeAssPoListResult) {
                dto.setTotalNum((int) feeAssPoListResult.getTotalrecord());// 总记录数
            }
        }
		
		return dto;
	}

}
