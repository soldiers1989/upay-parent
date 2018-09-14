package com.upay.busi.mer.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.MerWecahtConfigDto;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;

public class MerWecahtConfigService extends
		AbstractDipperHandler<MerWecahtConfigDto> {
	@Resource
	private IDaoService daoService;

	@Override
	public MerWecahtConfigDto execute(MerWecahtConfigDto dto, Message arg1)
			throws Exception {
		String operateFlag = dto.getOperateFlag();// 操作标志
													// PAYCATALOGCONF:添加支付授权目录
													// BINDAPPIDCONF: 绑定APPID
													// ATTENTIONCONF:推荐关注APPID
		String merNo = dto.getMerNo();// 商户的商户号
		String jsapiPath = dto.getJsapiPath();// 支付授权目录
		String subAppid = dto.getSubAppid();// 绑定特约商户或渠道公众号、小程序、APP支付等对应的APPID
		String subscribeAppid = dto.getSubscribeAppid();// 推荐关注APPID
														// 特约商户或渠道的公众号APPID

		if (StringUtils.isBlank(merNo)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
		}
		if ("PAYCATALOGCONF".equals(operateFlag)) {
			dto.setUpdateFlag("2");
			// 1 添加支付授权目录
			if (StringUtils.isBlank(jsapiPath)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "支付授权目录");
			}
		} else if ("BINDAPPIDCONF".equals(operateFlag)) {
			dto.setUpdateFlag("3");
			// 2 绑定APPID
			if (StringUtils.isBlank(subAppid)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "微信APPID");
			}
		} else if ("ATTENTIONCONF".equals(operateFlag)) {
			dto.setUpdateFlag("4");
			if (StringUtils.isBlank(subAppid)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "微信APPID");
			}

			if (StringUtils.isBlank(subscribeAppid)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "公众号APPID");
			}
		}

		MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
		merBaseInfoPo.setMerNo(merNo);
		merBaseInfoPo = daoService.selectOne(merBaseInfoPo);
		if (merBaseInfoPo == null) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "商户:" + merNo);
		} else {
			String merState = merBaseInfoPo.getMerState();
			if (DateBaseConstants_MER.MER_STAT_NORMAL.equals(merState)) {
				String payopenflag = merBaseInfoPo.getPayOpenFlag();
				if (StringUtils.isNotBlank(payopenflag)
						&& DateBaseConstants_MER.MER_PAYOPENFLAG_CLOSE
								.equals(payopenflag)) {
					ExInfo.throwDipperEx(AppCodeDict.BISMER0011, merNo);
				}
			} else {
				ExInfo.throwDipperEx(AppCodeDict.BISMER0010, merNo);
			}
			String subMerId = merBaseInfoPo.getSubMchId();
			if (StringUtils.isBlank(subMerId)) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0103, merNo);
			} else {
				dto.setSubMchId(subMerId);
			}
		}
		dto.setTranCode(operateFlag);
		return dto;
	}

}
