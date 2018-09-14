package com.upay.busi.mer.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.ChkMerNameDto;
import com.upay.busi.mer.service.dto.QueryMerInfoByQrCodeDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerApplyBookPo;
import com.upay.dao.po.mer.MerBaseInfoPo;

/**
 * 根据qrCode查询商户信息
 * 
 */
public class QueryMerInfoByQrCodeService extends AbstractDipperHandler<QueryMerInfoByQrCodeDto> {

	@Resource
    private IDaoService daoService;
	
	@Override
	public QueryMerInfoByQrCodeDto execute(QueryMerInfoByQrCodeDto dto, Message msg)
			throws Exception {
		String qrCode = dto.getQrCode();
		if (StringUtils.isBlank(qrCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "银联二维码链接");
        }
		MerBaseInfoPo merBaseInfo = new MerBaseInfoPo();
		merBaseInfo.setQrCode(qrCode);
		merBaseInfo = daoService.selectOne(merBaseInfo);
		if(merBaseInfo==null){
			 ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "商户信息");
		}
		dto.setMerNo(merBaseInfo.getMerNo());
		return dto;
	}

}
