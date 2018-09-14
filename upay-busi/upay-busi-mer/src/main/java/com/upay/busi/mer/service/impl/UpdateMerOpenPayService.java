package com.upay.busi.mer.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.UpdateMerOpenPayDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerOpenPayPo;

public class UpdateMerOpenPayService extends AbstractDipperHandler<UpdateMerOpenPayDto>{
	@Resource
    private IDaoService daoService;
	@Override
	public UpdateMerOpenPayDto execute(UpdateMerOpenPayDto dto, Message arg1)
			throws Exception {
		String updateFlag = dto.getUpdateFlag();
		String merNo=dto.getMerNo();//商户号
		//1:微信商户  2：微信回调地址配置  3：绑定APPID 4:配置appid   5:支付宝    6：银联
		if(StringUtils.isBlank(merNo)){
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
		}
		if(StringUtils.isNotBlank(updateFlag)){
			MerOpenPayPo openPay=new MerOpenPayPo();
			if("1".equals(updateFlag)){
				openPay.setWechatOpen("1");//更新微信支付开通状态
			}else if("2".equals(updateFlag)){
				openPay.setWechatAuthDir("1");//更新微信授权地址开通状态
			}else if("3".equals(updateFlag)){
				openPay.setWechatBindAppid("1");//更新微信绑定appid状态
			}else if("4".equals(updateFlag)){
				openPay.setWechatAttention("1");//更新微信推荐关注定appid状态
			}else if("5".equals(updateFlag)){
				openPay.setAlipayOpen("1");//更新支付宝支付开通状态
			}else if("6".equals(updateFlag)){
				openPay.setUnionOpen("1");//更新银联支付开通状态
			}
			
			MerOpenPayPo whereOpenPay=new MerOpenPayPo();
			whereOpenPay.setMerNo(merNo);
			
			
			daoService.update(openPay,whereOpenPay);
		}
		
		return dto;
	}

}
