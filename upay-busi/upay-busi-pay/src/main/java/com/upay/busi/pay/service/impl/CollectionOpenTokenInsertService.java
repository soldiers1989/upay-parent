package com.upay.busi.pay.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CollectionOpenTokenQueryDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.CollectionOpenTokenPo;

/**
 * 银联token授权插入
 * 
 * @author hry
 * 
 */
public class CollectionOpenTokenInsertService extends
		AbstractDipperHandler<CollectionOpenTokenQueryDto> {

	private static final Logger LOG = LoggerFactory
			.getLogger(CollectionOpenTokenInsertService.class);

	@Resource
	private IDaoService daoService;
	private static final Logger logger = LoggerFactory.getLogger(CollectionOpenTokenInsertService.class);

	@Override
	public CollectionOpenTokenQueryDto execute(
			CollectionOpenTokenQueryDto collectionOpenTokenQueryDto, Message msg)
			throws Exception {

		String resultCode = collectionOpenTokenQueryDto.getRespCode();
		if (DataBaseConstants_PAY.UNION_STAT_SUCC.equals(resultCode)||
			DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(resultCode)) {
			CollectionOpenTokenPo collectionOpenTokenPo = new CollectionOpenTokenPo();
			String cardbin = collectionOpenTokenQueryDto.getCardBinType();
			// 卡类型 1、借记卡 2、贷记卡
			// 贷记卡 必送：卡号、手机号、CVN2、有效期；验证码看业务配置（默认不要短信验证码）。
			// 借记卡 必送：卡号、手机号；选送：证件类型+证件号、姓名；验证码看业务配置（默认不要短信验证码）。
			if (StringUtils.isBlank(cardbin)) {
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "卡类型");
			}
			if (StringUtils.isBlank(collectionOpenTokenQueryDto.getBindacctno())) {
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "卡号");
			}
			collectionOpenTokenPo.setBindacctno(collectionOpenTokenQueryDto
					.getBindacctno());
			if (StringUtils.isBlank(collectionOpenTokenQueryDto.getPhone())) {
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "手机号");
			}
			if (StringUtils.isBlank(collectionOpenTokenQueryDto.getCustomerNm())) {
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "姓名");
			}
			if (StringUtils.isBlank(collectionOpenTokenQueryDto.getCertifId())) {
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "证件号");
			}
			
			String tokendate=collectionOpenTokenQueryDto.getTokenPayDataQuery();
			tokendate=tokendate.replace("{", "").replace("}", "");
	    	String[] strtmp1=tokendate.split("&");
	    	Map<String, String> tokenmap=new HashMap<String, String>();
	    	for(int i=0;i<strtmp1.length;i++ ){
	    		tokenmap.put(strtmp1[i].split("=")[0], strtmp1[i].split("=")[1]);
	    	}

			if (cardbin.equals("2")) {
				if (StringUtils.isBlank(collectionOpenTokenQueryDto.getCvn2())) {
					ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "CVN2");
				}
				collectionOpenTokenPo.setCvn2(collectionOpenTokenQueryDto.getCvn2());
				if (StringUtils.isBlank(collectionOpenTokenQueryDto.getExpired())) {
					ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "有效期");
				}
				collectionOpenTokenPo.setExpired(collectionOpenTokenQueryDto.getExpired());
			}
			if (cardbin.equals("1")) {
				if (StringUtils.isBlank(collectionOpenTokenQueryDto.getCustomerNm())) {
					collectionOpenTokenPo.setCustomerNm(collectionOpenTokenQueryDto.getCustomerNm());
				}
				if (StringUtils.isBlank(collectionOpenTokenQueryDto.getCertifTp())) {
					collectionOpenTokenPo.setCertifTp(collectionOpenTokenQueryDto.getCertifTp());
				}
				if (StringUtils.isBlank(collectionOpenTokenQueryDto.getCertifId())) {
					collectionOpenTokenPo.setCertifId(collectionOpenTokenQueryDto.getCertifId());
				}
			}
			collectionOpenTokenPo.setPhone(collectionOpenTokenQueryDto.getPhone());
			collectionOpenTokenPo.setCustomerNm(collectionOpenTokenQueryDto.getCustomerNm());
			collectionOpenTokenPo.setCertifId(collectionOpenTokenQueryDto.getCertifId());
			collectionOpenTokenPo.setCertifTp(collectionOpenTokenQueryDto.getCertifTp());
			collectionOpenTokenPo.setToken(tokenmap.get("token"));
			collectionOpenTokenPo.setTrid(tokenmap.get("trId"));
			collectionOpenTokenPo.setTokenlevel(tokenmap.get("tokenLevel"));
			collectionOpenTokenPo.setTokenbegin(tokenmap.get("tokenBegin"));
			collectionOpenTokenPo.setTokenend(tokenmap.get("tokenEnd"));
			collectionOpenTokenPo.setTokentype(tokenmap.get("tokenType"));
			// 开通状态 1已开通 其他未开通
			collectionOpenTokenPo.setStatus("01");
			if (StringUtils.isBlank(collectionOpenTokenQueryDto.getPin())) {
	           collectionOpenTokenPo.setPin(collectionOpenTokenQueryDto.getPin());
			}
			String tempstr = DateUtil.format(new Date(), "yyyyMMddHHmmss");
			Date tempdate = DateUtil.parse(tempstr, "yyyyMMddHHmmss");
			collectionOpenTokenPo.setCreateDate(tempdate);
			collectionOpenTokenPo.setCardBinType(cardbin);


			//先查询是否已经存在   如果已经存在 并且银联签约成功 认为   个人信息已经更改发起的签约   删除之前已经签约过的token
			CollectionOpenTokenPo collectionOpenTokenPoTemp = new CollectionOpenTokenPo();
			collectionOpenTokenPoTemp.setBindacctno(collectionOpenTokenQueryDto
					.getBindacctno());
			CollectionOpenTokenPo collectionOpenTokenPo1 = daoService.selectOne(collectionOpenTokenPoTemp);
			if(collectionOpenTokenPo1!=null){
				Map<String, Object> bodys = (Map<String, Object>) msg.getTarget().getBodys();
                String applyType = String.valueOf(bodys.get("applyType"));

                logger.info("applyType:"+("3".equals(applyType)));
                logger.info("applyType>"+applyType);
                if(bodys.containsKey("applyType")){
                    //针对四要素验证   如果存在token 就更新 数据库
				    if("3".equals(applyType)){
				        daoService.update(collectionOpenTokenPo,collectionOpenTokenPo1);
				        //直接结束
                        return collectionOpenTokenQueryDto;
                    }
					//如果是 无跳转授权  认为 信息更改重新 授权 删除授权数据 重新授权
					if("1".equals(applyType)){
						daoService.delete(collectionOpenTokenPo1);
					}
				}

			}
			daoService.insert(collectionOpenTokenPo);
			return collectionOpenTokenQueryDto;
		} else {
				ExInfo.throwDipperEx(AppCodeDict.UNIONPAY0109,msg.getFault().getOutMsg());
		}
		return collectionOpenTokenQueryDto;
	}

}
