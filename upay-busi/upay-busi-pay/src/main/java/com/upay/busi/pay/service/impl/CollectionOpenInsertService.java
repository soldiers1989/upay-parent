package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CollectionOpenTokenQueryDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.CollectionOpenPo;
import com.upay.dao.po.pay.CollectionOpenTokenPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 银联代收授权插入
 * 
 * @author hry
 * 
 */
public class CollectionOpenInsertService extends
		AbstractDipperHandler<CollectionOpenTokenQueryDto> {

	private static final Logger LOG = LoggerFactory
			.getLogger(CollectionOpenInsertService.class);

	@Resource
	private IDaoService daoService;

	@Override
	public CollectionOpenTokenQueryDto execute(
			CollectionOpenTokenQueryDto collectionOpenTokenQueryDto, Message msg)
			throws Exception {
		String resultCode = collectionOpenTokenQueryDto.getRespCode();
		String cardbin = collectionOpenTokenQueryDto.getCardBinType();
		String customerNm = collectionOpenTokenQueryDto.getCustomerNm();
		String certifTp = collectionOpenTokenQueryDto.getCertifTp();
		String certifId = collectionOpenTokenQueryDto.getCertifId();
		String bindacctno = collectionOpenTokenQueryDto.getBindacctno();
		String phone = collectionOpenTokenQueryDto.getPhone();
		if (DataBaseConstants_PAY.UNION_STAT_SUCC.equals(resultCode)||
			DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(resultCode)) {
			CollectionOpenPo collectionOpenPo = new CollectionOpenPo();
			// 卡类型 1、借记卡 2、贷记卡
			// 贷记卡 必送：卡号、手机号、CVN2、有效期；验证码看业务配置（默认不要短信验证码）。
			// 借记卡 必送：卡号、手机号；选送：证件类型+证件号、姓名；验证码看业务配置（默认不要短信验证码）。
			if (StringUtils.isBlank(cardbin)) {
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "卡类型");
			}

			//账号+姓名 /账号+证件类型+证件号
			if (StringUtils.isNotBlank(bindacctno)){
				collectionOpenPo.setBindacctno(bindacctno);
				// 开通状态 1已开通 其他未开通
				collectionOpenPo.setStatus("01");
				if(StringUtils.isNotBlank(customerNm)){
					collectionOpenPo.setCustomerNm(customerNm);
				}else{
					ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "代收签约，客户姓名");
				}
				if(StringUtils.isNotBlank(certifId)){
					collectionOpenPo.setCertifId(certifId);
				}else{
					ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "代收签约，证件号");
				}
				if(StringUtils.isNotBlank(certifTp)){
					collectionOpenPo.setCertifTp(certifTp);
				}else{
					ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "代收签约，证件类型");
				}

				if(StringUtils.isNotBlank(phone)){
					collectionOpenPo.setPhone(phone);
				}
			}else{
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "代收签约，卡号");
			}

			if (cardbin.equals("2")) {
				if (StringUtils.isBlank(collectionOpenTokenQueryDto.getCvn2())) {
					ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "CVN2");
				}
				collectionOpenPo.setCvn2(collectionOpenTokenQueryDto.getCvn2());
				if (StringUtils.isBlank(collectionOpenTokenQueryDto.getExpired())) {
					ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "有效期");
				}
				collectionOpenPo.setExpired(collectionOpenTokenQueryDto.getExpired());
			}
			String tempstr = DateUtil.format(new Date(), "yyyyMMddHHmmss");
			Date tempdate = DateUtil.parse(tempstr, "yyyyMMddHHmmss");
			collectionOpenPo.setCreateDate(tempdate);
			collectionOpenPo.setCardBinType(cardbin);
			//先查询是否已经存在   如果已经存在 并且银联签约成功 认为   个人信息已经更改发起的签约   删除之前已经签约过的token
			CollectionOpenTokenPo collectionOpenTokenPoTemp = new CollectionOpenTokenPo();
			collectionOpenTokenPoTemp.setBindacctno(collectionOpenTokenQueryDto
					.getBindacctno());
			CollectionOpenTokenPo collectionOpenTokenPo1 = daoService.selectOne(collectionOpenTokenPoTemp);
			if(collectionOpenTokenPo1!=null){
				daoService.delete(collectionOpenTokenPo1);
			}
			daoService.insert(collectionOpenPo);
			collectionOpenTokenQueryDto.setActivateStatus("1");
		} else {
				ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0109,msg.getFault().getOutMsg());
		}
		return collectionOpenTokenQueryDto;
	}

}
