/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.RedpackRegisterDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.pay.PayRedpackListPo;

/**
 * 红包登记
 * 
 * @author zhanggr
 * 
 */
public class RedpackRegisterService extends
		AbstractDipperHandler<RedpackRegisterDto> {

	@Resource
	private IDaoService daoService;
	@Resource
    private ISequenceService seqService;
	
	@Override
	public RedpackRegisterDto execute(RedpackRegisterDto redpackRegisterDto,
			Message msg) throws Exception {
		String outerOrderNo = redpackRegisterDto.getOuterOrderNo();
		String merNo = redpackRegisterDto.getMerNo();
		String msgappid = redpackRegisterDto.getMsgappid();
		String sendName = redpackRegisterDto.getSendName();
		String reOpenid = redpackRegisterDto.getReOpenid();
		BigDecimal totalAmount = redpackRegisterDto.getTransAmt();
		String totalNo = redpackRegisterDto.getTotalNo();//裂变红包时由外部传入，普通红包为1
		
		String wishing = redpackRegisterDto.getWishing();
		String clientIp = redpackRegisterDto.getClientIp();
		String actName = redpackRegisterDto.getActName();
		String sceneId = redpackRegisterDto.getSceneId();
		String riskInfo = redpackRegisterDto.getRiskInfo();
		String consumeMchId = redpackRegisterDto.getConsumeMchId();
		String redpackType = redpackRegisterDto.getRedpackType();//单个：single 普通红包 多个：group裂变红包
		String remark = redpackRegisterDto.getRemark();
		if (StringUtils.isBlank(outerOrderNo)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户订单号");
		}
		if (StringUtils.isBlank(merNo)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
		}
		if (StringUtils.isBlank(msgappid)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "触达用户appid");
		}
		if (StringUtils.isBlank(sendName)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户名称");
		}
		if (StringUtils.isBlank(reOpenid)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户openid");
		}
		if (StringUtils.isBlank(totalAmount.toString())) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "付款金额");
		}
		if (StringUtils.isBlank(totalNo)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "红包发放总人数");
		}
		
		if (StringUtils.isBlank(wishing)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "红包祝福语");
		}
		if (StringUtils.isBlank(clientIp)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "Ip地址");
		}
		if (StringUtils.isBlank(actName)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "活动名称");
		}
		if (StringUtils.isBlank(redpackType)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "红包类型");
		}
		String amtType = null;
		
		if(DataBaseConstants_PAY.RED_PACKET_GROUP.equals(redpackType)){
			amtType = redpackRegisterDto.getAmtType();//裂变红包时传入
			if (StringUtils.isBlank(amtType)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "红包金额设置方式");
			}
		}
		if(DataBaseConstants_PAY.RED_PACKET_SINGLE.equals(redpackType) && totalAmount.compareTo(new BigDecimal("200")) > 0){
			
			if (StringUtils.isBlank(sceneId)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "场景id");
			}
		}
		String flowSeq = seqService.generatePayFlowSeq();
		
		PayRedpackListPo payRedpackListPo = new PayRedpackListPo();

		payRedpackListPo.setActName(actName);
		payRedpackListPo.setAmtType(amtType);
		payRedpackListPo.setClientIp(clientIp);
		payRedpackListPo.setConsumeMchId(redpackRegisterDto.getOrgNo());
		payRedpackListPo.setMerNo(merNo);
		payRedpackListPo.setMsgappid(msgappid);
		payRedpackListPo.setOrderNo(flowSeq);
		payRedpackListPo.setOrderState(DataBaseConstants_PAY.RED_PACKET_STATE_BEGIN);
		payRedpackListPo.setOuterOrderNo(outerOrderNo);
		payRedpackListPo.setRedpackType(redpackType);
		payRedpackListPo.setRemark(remark);
		payRedpackListPo.setReOpenid(reOpenid);
		payRedpackListPo.setRiskInfo(riskInfo);
		payRedpackListPo.setSceneId(sceneId);
		payRedpackListPo.setSendName(sendName);
		payRedpackListPo.setTotalNum(Integer.valueOf(totalNo));
		payRedpackListPo.setTotalAmount(totalAmount);
		payRedpackListPo.setTransDate(new Date());
		payRedpackListPo.setTransTime(new Date());
		payRedpackListPo.setWishing(wishing);
		daoService.insert(payRedpackListPo);

		redpackRegisterDto.setTransSubSeq(flowSeq);//流水号

		return redpackRegisterDto;
	}

}
