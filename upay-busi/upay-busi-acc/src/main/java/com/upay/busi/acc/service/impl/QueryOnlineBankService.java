package com.upay.busi.acc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.busi.acc.service.dto.OnlineBankDto;
import com.upay.busi.acc.service.dto.QueryOnlineBankDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.dao.po.pay.ChannelLogoBookPo;

/**
 * 查询网银信息
 * 
 * @author liubing
 * @version 创建时间：2016年7月21日 上午10:09:59
 */
public class QueryOnlineBankService extends
		AbstractDipperHandler<QueryOnlineBankDto> {

	private final static Logger log = LoggerFactory
			.getLogger(BindBookService.class);
	@Resource
	private IDaoService daoService;

	@Override
	public QueryOnlineBankDto execute(QueryOnlineBankDto dto, Message message)
			throws Exception {
		ChannelLogoBookPo logobook = new ChannelLogoBookPo();
		logobook.setRemark(dto.getQueryFlag());
		logobook.setChannelId(dto.getChnlId());
		logobook.addOrder(Order.asc("logoId"));//logo id 排序
		List<ChannelLogoBookPo> logobookList = daoService.selectList(logobook);
		List<Map<String, Object>> dtoList = new ArrayList<Map<String, Object>>();
		if (logobookList.size() > 0) {
			/**
             * 静态资源服务器域名
             */
            String imgDomainName = (String) DipperParm.getParmByKey(CmparmConstants.IMG_DOMAIN_NAME);
			for (ChannelLogoBookPo onlineLogo : logobookList) {
				OnlineBankDto bankDto=new OnlineBankDto();
				bankDto.setLogoClass(imgDomainName+onlineLogo.getLogoClass());
				bankDto.setLogoId(onlineLogo.getLogoId());
				bankDto.setLogoName(onlineLogo.getLogoName());
				Map<String, Object> logoMap = BeanCopyUtil
						.copyBean2MapStrObjNoClass(bankDto);
				dtoList.add(logoMap);
			}
		}

		dto.setLogoList(dtoList);
		return dto;
	}

}
