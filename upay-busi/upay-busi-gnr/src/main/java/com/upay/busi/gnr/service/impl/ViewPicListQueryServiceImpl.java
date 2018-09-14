package com.upay.busi.gnr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.page.QueryResult;
import com.pactera.dipper.po.Order;
import com.upay.busi.gnr.service.dto.ViewPicListQueryDto;
import com.upay.busi.gnr.service.dto.ViewPicListQuerySubDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.gnr.GnrCurviewpicInfoPo;

/**
 * 轮播图列表查询
 * 
 * @author wuyuejun
 * 
 */
public class ViewPicListQueryServiceImpl extends
		AbstractDipperHandler<ViewPicListQueryDto> {

	/** 数据库服务接口 */
	@Resource
	private IDaoService daoService;

	public void setDaoService(IDaoService daoService) {
		this.daoService = daoService;
	}

	@Override
	public ViewPicListQueryDto execute(ViewPicListQueryDto viewPicListQueryDto,
			Message message) throws Exception {

		String chnlId = viewPicListQueryDto.getChnlId();// 渠道代码

		// String devType = viewPicListQueryDto.getDevType();// 设备类型

		Map<String, Object> map = new HashMap<String, Object>();

		if (StringUtils.isBlank(chnlId)) {// 判断渠道代码不能为空
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004,
					GnrCurviewpicInfoPo.ALIAS_CHNL_ID);
		}
		map.put("chnlId", chnlId);

		// if (StringUtils.isBlank(devType)) {// 判断设备类型
		// if(chnlId.equals(DataBaseConstants_POT.CHNL_ID_APP)){
		// ExInfo.throwDipperEx(AppCodeDict.BISGNR0004,MrkAppScrInfoPo.ALIAS_DEV_TYPE);
		// }
		//
		// }else{
		// map.put("devType", devType);
		// }

		// 获取分页信息
//		int currentNum = viewPicListQueryDto.getCurrentNum();
//		int pageIndex = viewPicListQueryDto.getPageIndex();

		// QueryResult<GnrCurviewpicInfoPo> gnrOperateListResult
		// =daoService.selectQueryResult(GnrCurviewpicInfoPo.class.getName()+
		// ".selectViewPicListQueryList",map, (pageIndex -
		// 1)*currentNum,currentNum);

		// if (gnrOperateListResult != null) {

		GnrCurviewpicInfoPo picPo = new GnrCurviewpicInfoPo();
		picPo.setChnlId(chnlId);
		picPo.addOrder(Order.asc("priority"));
		// List<GnrCurviewpicInfoPo> mbiList =
		// gnrOperateListResult.getResultlist();
		List<GnrCurviewpicInfoPo> mbiList = daoService.selectList(picPo);
		List<Map<String, Object>> subDtoList = new ArrayList<Map<String, Object>>();

		if (mbiList != null && mbiList.size() > 0) {
			ViewPicListQuerySubDto subDto = null;
			/** 循环取出，放入子DTO */

			/**
			 * 静态资源服务器域名
			 */
			String imgDomainName = (String) DipperParm
					.getParmByKey(CmparmConstants.IMG_DOMAIN_NAME);

			/**
			 * 获取HTML文件域名
			 */
//			String htmlDomainName = (String) DipperParm
//					.getParmByKey(CmparmConstants.HTML_DOMAIN_NAME);

			for (GnrCurviewpicInfoPo po : mbiList) {
				subDto = new ViewPicListQuerySubDto();
				subDto.setPicLink(imgDomainName + po.getPicLink());

				// 暂时没活动，先注掉
				// if(po.getEventId()!=null){//根据活动ID获取活动链接
				// GnrEventInfoPo mrkEventInfoPo=new GnrEventInfoPo();
				// mrkEventInfoPo.setEventId(po.getEventId());po.gete
				// List<GnrEventInfoPo>
				// ListMrkEventInfoPo=daoService.selectList(mrkEventInfoPo);
				// if(ListMrkEventInfoPo!=null&&ListMrkEventInfoPo.size()>0){
				// subDto.setEventLink(htmlDomainName+ListMrkEventInfoPo.get(0).getEventLink());
				// }
				// }

				subDtoList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(subDto));
			}
		}

		// viewPicListQueryDto.setTotalNum((int) gnrOperateListResult
		// .getTotalrecord());
		viewPicListQueryDto.setViewPicList(subDtoList);
		// }

		return viewPicListQueryDto;
	}

}
