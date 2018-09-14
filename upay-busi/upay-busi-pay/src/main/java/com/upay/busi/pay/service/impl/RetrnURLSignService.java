/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.RetrnURLSignDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.MD5Utils;
import com.upay.dao.po.mer.MerNotifiyPo;
import com.upay.dao.po.mer.MerPlatSettingPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * @author Administrator
 * 
 */
public class RetrnURLSignService extends AbstractDipperHandler<RetrnURLSignDto> {
	private static final Logger LOG = LoggerFactory
			.getLogger(RetrnURLSignService.class);
	@Resource
	private IDaoService daoService;

	public RetrnURLSignDto execute(RetrnURLSignDto dto, Message message)
			throws Exception {
		PayOrderListPo order = new PayOrderListPo();
		order.setOrderNo(dto.getOrderNo());
		order = daoService.selectOne(order);
		
		MerPlatSettingPo merPlatSetting = new MerPlatSettingPo();
		merPlatSetting.setMerPlatNo(order.getMerNo());
		merPlatSetting = daoService.selectOne(merPlatSetting);
		
		MerNotifiyPo notifiyPo=new MerNotifiyPo();
		notifiyPo.setMerNo(order.getMerNo());
		notifiyPo.setOuterOrderNo(order.getOuterOrderNo());
		List<MerNotifiyPo> notifiyList = daoService.selectList(notifiyPo);
		if(notifiyList!=null&notifiyList.size()>0){
			notifiyPo=notifiyList.get(0);
		}
		if (null != order) {
			// 拼接实时返回交易状态链接
			StringBuffer returnUrl = new StringBuffer(order.getReturnUrl());
			if (StringUtils.isNotBlank(returnUrl.toString())) {
				String orderStat = order.getOrderStat();
				String chnlId = order.getChnlId();
				String merNo = order.getMerNo();
				String outerOrderNo = order.getOuterOrderNo();
				String transAmt = order.getTransAmt() == null ? "0.00" : order.getTransAmt().toString();
				String timeEnd = order.getPayTime() == null ? "null": DateUtil.format(order.getPayTime(),DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1);
				String notifyId=notifiyPo!=null?notifiyPo.getNotifyId()+"":"null";
				String otherTranAmt=order.getOtherTranAmt()==null?"0.00":order.getOtherTranAmt().toString();
				String productAmt=order.getProductAmt()==null?"0.00":order.getProductAmt().toString();
				String comments=String.valueOf(order.getTransComments());
				String secMerNo = String.valueOf(order.getSecMerNo());
				String orderNo = order.getOrderNo();
				String payServicType = notifiyPo!=null?notifiyPo.getNotifyType()+"":"null";
				String curr = order.getCurr();
				
				returnUrl.append("&transAmt=" + transAmt);
				returnUrl.append("&serviceVersion=1.0");
				returnUrl.append("&notifyId="+notifyId);
				returnUrl.append("&secMerNo="+secMerNo);
				returnUrl.append("&charset=utf-8");
				returnUrl.append("&otherTranAmt="+otherTranAmt);
				returnUrl.append("&timeEnd=" + String.valueOf(timeEnd));
				returnUrl.append("&orderNo="+orderNo);
				returnUrl.append("&CURR=" + curr);
				
				returnUrl.append("&payServicType=" + payServicType);
				returnUrl.append("&outerOrderNo=" + outerOrderNo);
				returnUrl.append("&transCode=resultNotify");
				returnUrl.append("&productAmt=" + productAmt);
				returnUrl.append("&merNo="+merNo);
				returnUrl.append("&transComments="+comments);
				returnUrl.append("&transStat=" + orderStat);
				
				HashMap<String, Object> needSign = new HashMap<String, Object>();
				needSign.put("transAmt" , transAmt);
				needSign.put("serviceVersion","1.0");
				needSign.put("notifyId",notifyId);
				needSign.put("secMerNo",secMerNo);
				needSign.put("charset","utf-8");
				needSign.put("otherTranAmt",otherTranAmt);
				needSign.put("timeEnd" , timeEnd);
				needSign.put("orderNo",orderNo);
				needSign.put("CURR" , curr);

				needSign.put("payServicType" , payServicType);
				needSign.put("outerOrderNo" , outerOrderNo);
				needSign.put("transCode","resultNotify");
				needSign.put("productAmt" , productAmt);
				needSign.put("merNo",merNo);
				needSign.put("transComments",comments);
				needSign.put("transStat" , orderStat);
				needSign.put("chnlId" , chnlId);
				
				String sign = "";
				if (null != merPlatSetting) {
					String signSource = MD5Utils.getSignSource(needSign, "key",
							merPlatSetting.getKey3des());
					sign = MD5Utils.md5Hex(signSource, "utf-8");
				}

				returnUrl.append("&sign=" + sign);
				returnUrl.append("&chnlId="+chnlId);
//				returnUrl.append("&signType=MD5");
				LOG.debug("return url 签名：{}", sign);
				LOG.debug("return url：{}", returnUrl.toString());
			}
			dto.setReturnUrl(returnUrl.toString());
		}
		return dto;
	}
}