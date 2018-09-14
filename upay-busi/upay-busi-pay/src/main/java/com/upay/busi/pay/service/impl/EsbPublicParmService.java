/**
 * 
 */
package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Fault;
import com.upay.busi.pay.service.dto.EsbPublicParmDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;

/**
 * @author Administrator
 *
 */
public class EsbPublicParmService extends AbstractDipperHandler<EsbPublicParmDto>{
	@Resource
    ISequenceService sequenceService;
	@Override
	public EsbPublicParmDto execute(EsbPublicParmDto dto,
			Message m) throws Exception {
		String bankId = dto.getBankId();
		String cardType = dto.getCardType();
		Fault fault = m.getFault();
		String code = fault.getCode();
		dto.setPrvdSysId("UPP000");
		String seq=dto.getSysSeq();
		
		
		
		dto.setRetMsg(fault.getMsg());
		if(!CommonConstants_GNR.RSP_CODE_SUCCESS.equals(code)){
			dto.setTranRetSt("F");
			//dto.setRetCd(transErroCode("00"+fault.getCode()));
			dto.setRetCd(fault.getCode());
//			dto.setRetMsg(fault.getMsg());
//			dto.setRetMsg("处理失败,请重试!");
			if(CommonConstants_GNR.CHNL_ID_TELLER.equals(dto.getChnlId())){
				//dto.setRetMsg("处理失败,请重试!");
				dto.setRetMsg(fault.getMsg());
			}else if(CommonConstants_GNR.CHNL_ID_ONLINE_BANK.equals(dto.getChnlId())){
				dto.setRetMsg(fault.getMsg());
			}else{
				dto.setRetMsg(fault.getMsg());
			}
//			dto.setRetMsg("处理失败,请重试!");
		}else{
			dto.setRetCd("000000");
			dto.setTranRetSt("S");
			if("SI_ACC1008".equals(dto.getTransCode())&&(StringUtils.isBlank(bankId)||StringUtils.isBlank(cardType))){
				dto.setRetCd("000001");
				dto.setTranRetSt("F");
				if(StringUtils.isBlank(dto.getRetMsg())){
					dto.setRetMsg("您输入的信息与绑定银行卡开户行预留信息不一致，请重新输入");
				}
			}
		}
		if(StringUtils.isNotBlank(seq)){
			dto.setPrvdSysSeqNo(seq);
		}else{
			seq = sequenceService.generateEsbNo();
			dto.setPrvdSysSeqNo(seq);
		}
		return dto;
	}
	
	private String transErroCode(String erroCode){
		return "UPP000"+erroCode.substring(2, erroCode.length());
	}
}
