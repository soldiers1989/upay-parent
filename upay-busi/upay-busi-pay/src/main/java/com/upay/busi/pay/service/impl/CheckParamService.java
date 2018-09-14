package com.upay.busi.pay.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import org.springframework.beans.factory.annotation.Autowired;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CheckParamDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.LocalHostUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;

/**
 * 判断四要素是否为空和有效性
 * @author wangzhenxing
 * 
 * 2016年11月28日
 */
public class CheckParamService extends AbstractDipperHandler<CheckParamDto> {
	
	
	private final static Logger logger = LoggerFactory.getLogger(CheckParamService.class);
	@Resource
	private IDaoService daoService;
	@Autowired
	private ISequenceService sequenceService;
	
	@Override
	public CheckParamDto execute(CheckParamDto dto, Message msg)
			throws Exception {
		// TODO Auto-generated method stub
		String acctNo = dto.geteBindAcctNo();
		//验证银行卡号是否为空
		if (null == acctNo || "".equals(acctNo)) {
			logger.error("单笔代收 卡bin查询银行信息:卡号为空");
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "卡号");
		}
		dto.setBankCardNo(acctNo);
		//验证银行卡号是否有效
		
		String acctName = dto.getAcctName();
		System.out.println("账户名称：>>>>>>"+acctName);
		if (null == acctName || "".equals(acctName)) {
			logger.error("单笔代收 客户信息验证：账户名称为空");
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001,"单笔代收 客户信息验证：账户名称");
		}
		dto.setAccountName(acctName);
		String certNo = dto.getCertNo();
		//验证证件号码是否为空
		if (null == certNo || "".equals(certNo)) {
			logger.error("单笔代收 客户信息验证：证件号码为空");
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001,"单笔代收 客户信息验证：证件号码");
		}
		//身份证 证件类型
		String certType=dto.getCertType();
		if(StringUtils.isBlank(certType)){
			dto.setCertType("1");
		}else{
			if ("01".equals(certType)) {
				dto.setCertType("1");
			}
		}
		
		String mobile = dto.getMobile();
		//验证手机号是否为空
		if (null == mobile || "".equals(mobile)) {
			logger.error("单笔代收 客户验证信息:手机号为空");
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001,"单笔代收 客户信息验证：手机号");
		}
		if (11 != mobile.length()) {
			logger.error("单笔代收 客户信息验证：手机号长度不合法");
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"单笔代收 客户信息验证：手机号长度不合法");
		}
		dto.setMobilePhone(mobile);
//		dto.setTxnTime(new Date());
//		dto.setOrderId(sequenceService.generateOrderNo("CHECK"));
        Object userRealIp = msg.getTarget().getHeaders().get("X-Real-IP");
        String realIp = "";
        if (null == userRealIp) {
			realIp = LocalHostUtil.LOCAL_IP_ADDR;
		}else {
			realIp = userRealIp.toString();
		}
        logger.info("取得用户IP:[{}],------------------------------>", userRealIp);
        dto.setSpbillCreateIp(realIp);
        
        SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
        String time = sim.format(new Date());
        dto.setMachineTime(time.substring(8));
        dto.setMachineDate(time.substring(0, 8));
        dto.setBizDate(sim.format(SysInfoContext.getSysDate()).substring(0, 8));
        dto.setBizSerialNo(dto.getSysSeq());
		return dto;
	}

}
