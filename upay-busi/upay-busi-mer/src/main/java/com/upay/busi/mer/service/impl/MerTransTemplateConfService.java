package com.upay.busi.mer.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.mer.service.dto.MerTransTemplateConfDto;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.mer.MerChannelMenuPo;
import com.upay.dao.po.mer.MerTransLimitPo;
import com.upay.dao.po.mer.MerTransTemplatePo;
import com.upay.dao.po.pay.ChannelMenuBookPo;
import com.upay.dao.po.trans.TransTemplatePo;

/**
 * 商户交易权限模板批量配置
 *
 */
public class MerTransTemplateConfService extends AbstractDipperHandler<MerTransTemplateConfDto> {

	@Resource
	private IDaoService daoService;

	@Override
	public MerTransTemplateConfDto execute(MerTransTemplateConfDto dto, Message msg)
			throws Exception {
		List<MerBaseInfoPo> merList = daoService.selectList(MerBaseInfoPo.class.getName() + ".getNotInMer",new HashMap());
		//查询默认配置的日累计限额
		GnrParmPo gnrParm = new GnrParmPo();
		gnrParm.setParmId("MER_DAY_TOTALLIMIT_DEFALUT");
		gnrParm = daoService.selectOne(gnrParm);
		//查询默认的交易权限模板
		TransTemplatePo transTemplate = new TransTemplatePo();
		transTemplate.setIsDefault(DateBaseConstants_MER.MER_TRANS_CTRL_TEMPLATE_DEFAULT);
		transTemplate = daoService.selectOne(transTemplate);
		if(null==transTemplate){
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "未配置默认的交易权限模版，请检查。");
		}
		
		//查询默认的单笔限额配置
		MerTransLimitPo merTransLimit = new MerTransLimitPo();
		merTransLimit.setIsDefault(DateBaseConstants_MER.MER_TRANS_CTRL_TEMPLATE_DEFAULT);
		List<MerTransLimitPo> limitList = daoService.selectList(merTransLimit);
		if(null==limitList||limitList.size()<=0){
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "未配置默认的商户交易限额，请检查。");
		}
		
		List<MerTransTemplatePo> list = new ArrayList<MerTransTemplatePo>();
		for (MerBaseInfoPo merBaseInfo : merList) {
			//为每个商户新增默认交易权限模板配置
			MerTransTemplatePo merTransTemplate = new MerTransTemplatePo();
			merTransTemplate.setDailyAcmlativeLimit(new BigDecimal(gnrParm.getParmValue()));
			merTransTemplate.setDailyAcmlativeAmt(BigDecimal.ZERO);
			merTransTemplate.setMerNo(merBaseInfo.getMerNo());
			merTransTemplate.setStatus(DateBaseConstants_MER.MER_TRANS_TEMPLATE_OPEN);
			merTransTemplate.setMerTransCtrlCode(limitList.get(0).getMerTransCtrlCode());
			merTransTemplate.setTemplateId(transTemplate.getTemplateId());
			list.add(merTransTemplate);
		}
		int size = daoService.insert(list, null);
		if(list.size() != size){
			 ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "商户模板未全部新增成功，请重试！");
		}
		//新增商户支付渠道
		//查询出未配置的商户
		List<MerBaseInfoPo> channelList = daoService.selectList(MerBaseInfoPo.class.getName() + ".getNotInChannel",new HashMap());
		//查询出所有的支付渠道
		List<ChannelMenuBookPo> channelMenuBookList=daoService.selectList(new ChannelMenuBookPo());
		if(channelMenuBookList!=null&&channelMenuBookList.size()>0){
			for (MerBaseInfoPo merBaseInfoPo : channelList) {
				for (ChannelMenuBookPo channelMenuBookPo : channelMenuBookList) {
					MerChannelMenuPo merChannelMenuPo=new MerChannelMenuPo();
					merChannelMenuPo.setChannelId(channelMenuBookPo.getChannelId());
					merChannelMenuPo.setChannelMenuBookId(channelMenuBookPo.getId());
					merChannelMenuPo.setMerNo(merBaseInfoPo.getMerNo());
					merChannelMenuPo.setMerName(merBaseInfoPo.getMerName());
					merChannelMenuPo.setChannelName(channelMenuBookPo.getChannelName());
					daoService.insert(merChannelMenuPo);
				}
			}
		}
		dto.setRspCode("success");
		return dto;
	}
}

