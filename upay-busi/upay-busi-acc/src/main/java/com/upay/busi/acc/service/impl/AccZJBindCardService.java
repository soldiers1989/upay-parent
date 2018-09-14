package com.upay.busi.acc.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.acc.service.dto.AccZJBindCardDto;
import com.upay.commons.dict.AppCodeDict;

/**
 * 
 * 中金绑卡、绑卡（解卡）查询状态判断 当status=10时当成是失败状态 需重新绑定
 * 
 * @author: David
 * @CreateDate:2016年3月23日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2016年3月23日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public class AccZJBindCardService extends
		AbstractDipperHandler<AccZJBindCardDto> {
	//绑卡失败
	private static final String BIND_FAIL="20";
	
	//绑卡处理中
	private static final String BIND_PROCING="10";
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public AccZJBindCardDto execute(AccZJBindCardDto accZJPayPayDto,
			Message message) throws Exception {
		String zjPayStatus = accZJPayPayDto.getZjPayStatus();
		logger.info("---->中金状态[{}]", zjPayStatus);
		if (null == zjPayStatus) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "中金状态");
		}
		if (BIND_FAIL.equals(zjPayStatus) || BIND_PROCING.equals(zjPayStatus)) {
			String responseMessage = accZJPayPayDto.getResponseMessage();
			if(StringUtils.isBlank(responseMessage)){
				responseMessage="绑定信息验证失败，请重新绑定卡";
			}
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000,responseMessage);
		}
		return accZJPayPayDto;
	}
}
