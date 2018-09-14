package com.upay.busi.pay.service.impl;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.pay.service.dto.GenerateATWeiXinOrderNoDto;

public class GenerateATWeiXinOrderNoService extends AbstractDipperHandler<GenerateATWeiXinOrderNoDto>{

	@Override
	public GenerateATWeiXinOrderNoDto execute(GenerateATWeiXinOrderNoDto dto,
			Message arg1) throws Exception {
		String out_trade_no="121775250120140703" + System.currentTimeMillis();
		dto.setTransSubSeq(out_trade_no);
		return dto;
	}

}
