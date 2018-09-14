package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.DealReqHostResultDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;

/**
 *	商户内部账户余额查询--核心返回结果处理
 * @author yhy
 * @since 20170613
 */
public class DealReqHostResultService extends AbstractDipperHandler<DealReqHostResultDto>{
	
	private static final Logger logger = LoggerFactory.getLogger(MerChkFileDownService.class);

	@Resource
	IDaoService daoService;
	@Override
	public DealReqHostResultDto execute(DealReqHostResultDto dto, Message message)
			throws Exception {
		
		String code=message.getFault().getCode();
		Map<String, Object> headers = (Map<String, Object>) message.getTarget().getBodys();
		String tranCode = (String)headers.get("tranCode");
		if(CommonConstants_GNR.RSP_CODE_SUCCESS.equals(code)){
			if("08007".equals(tranCode)){
				if(StringUtils.isBlank(dto.getBkDate())){
					ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "核心日期");
				}
				if(StringUtils.isBlank(dto.getRespCode())){
					ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "核心状态码");
				}
				if(CommonConstants_GNR.T_PAY_BANK_HT_SUCCESS.equals(dto.getRespCode())){
						dto.setQueryDate(dto.getBkDate());
						dto.setAcctBalance(dto.getDbalance());
				}else{
					ExInfo.throwDipperEx(AppCodeDict.BISPAY0112, "核心查询");
				}
			
			}else if("08008".equals(tranCode)){
				if(StringUtils.isBlank(dto.getRespCode())){
					ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "核心状态码");
				}
				if(CommonConstants_GNR.T_PAY_BANK_HT_SUCCESS.equals(dto.getRespCode())){
					String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
					dto.setQueryDate(date);
					dto.setAcctBalance(dto.getBal());
				}else{
					ExInfo.throwDipperEx(AppCodeDict.BISPAY0112, "核心查询");
				}
			}
        }else{
        	ExInfo.throwDipperEx(AppCodeDict.BISPAY0112, "账户查询");
        }
        
		return dto;
	}

}
