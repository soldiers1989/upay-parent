/**
 * 
 */
package com.upay.busi.acc.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.TransferAccountCheckDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.usr.UsrRegInfoPo;

import javax.annotation.Resource;

/**
 * 转账前检查
 * @author lb
 *
 */
public class TransferAccountCheckService extends AbstractDipperHandler<TransferAccountCheckDto> {

    @Resource
    private IDaoService daoService;

	@Override
	public TransferAccountCheckDto execute(TransferAccountCheckDto dto,
			Message msg) throws Exception {
		String userId = dto.getUserId();
		String payeeMobile = dto.getPayeeMobile();
		String payeeUserName = dto.getPayeeUserName();

		AccVbookPo payerBook=new AccVbookPo();
		payerBook.setUserId(userId);
		payerBook=daoService.selectOne(payerBook);
		if(payerBook==null){
			//付款人未开通虚拟账户
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "付款人未开通虚拟账户");
		}
		
		UsrRegInfoPo payeeUser=new UsrRegInfoPo();
//		payeeUser.setMobile(payeeMobile);
		payeeUser.setUserName(payeeUserName);
		payeeUser = daoService.selectOne(payeeUser);
		if(null==payeeUser){
			//收款 人不存在
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "收款用户不存在");
		}
		
		if(userId.equals(payeeUser.getUserId())){
			//收款人不能是本人
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "收款用户不能是本人");
		}
		AccVbookPo payeeBook=new AccVbookPo();
		payeeBook.setUserId(payeeUser.getUserId());
		payeeBook=daoService.selectOne(payeeBook);
		if(payeeBook==null){
			//收款人未开通虚拟账户
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "收款人未开通虚拟账户");
		}
		
		return dto;
	}

}
