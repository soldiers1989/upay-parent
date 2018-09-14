package com.upay.busi.pay.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.ChannelLeafSearchDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.dao.po.pay.ChannelMenuBookPo;


/**
 * 获取渠道节点 当前台页面传空时，获取节点 当前台页面传渠道ID时，获取节点
 * 
 * @author liyulong
 * 
 */
public class ChannelLeafSearchService extends AbstractDipperHandler<ChannelLeafSearchDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(ChannelLeafSearchService.class);


    @Override
    public ChannelLeafSearchDto execute(ChannelLeafSearchDto channelLeafSearchDto, Message msg)
            throws Exception {
//        ChannelMenuBookPo channelMenuBookPo = new ChannelMenuBookPo();
        List<ChannelMenuBookPo> channelMenuBookPos = new ArrayList<>();
        List<Map<String, Object>> channelLeafSearchDtoList = new ArrayList<Map<String, Object>>();

        // 取出渠道ID
        String channelid = channelLeafSearchDto.getChannelId();
        // web.app渠道
        String chnId = channelLeafSearchDto.getChnlId();
//        channelMenuBookPo.setChannelId(chnId);
//        if (StringUtils.isBlank(channelid)) {
//            channelMenuBookPos =
//                    daoService.selectList(ChannelMenuBookPo.class.getName() + ".topflag1", channelMenuBookPo);
//        } else {
//            channelMenuBookPo.setChannelTopleaf(channelid);
//            channelMenuBookPos =
//                    daoService.selectList(ChannelMenuBookPo.class.getName() + ".topflag", channelMenuBookPo);
//        }
        HashMap<String,Object> whereMap=new HashMap<String,Object>();
        whereMap.put("merNo", channelLeafSearchDto.getMerNo());
        whereMap.put("channelId", chnId);
        channelMenuBookPos =
              daoService.selectList(ChannelMenuBookPo.class.getName() + ".getMerChannelMenu", whereMap);
        if (null != channelMenuBookPos && channelMenuBookPos.size() != 0) {
            for (int i = 0; i < channelMenuBookPos.size(); i++) {
            	String channelName=channelMenuBookPos.get(i).getChannelName();
            	String userId=channelLeafSearchDto.getUserId();
            	if(StringUtils.isBlank(userId)&&DataBaseConstants_PAY.T_PAY_TYPE_BANK_QUICK_DESC.equals(channelName)){
            		//当用户ID为空时，不显示快捷支付
            	}else{
            		channelLeafSearchDto.setChannelId(channelMenuBookPos.get(i).getChannelId());
                    channelLeafSearchDto.setChannelName(channelMenuBookPos.get(i).getChannelName());
                    channelLeafSearchDto.setChannelLeaf(channelMenuBookPos.get(i).getChannelLeaf());
                    channelLeafSearchDto.setChannelUrl(channelMenuBookPos.get(i).getChannelUrl());
                    channelLeafSearchDto.setChannelFlag(channelMenuBookPos.get(i).getChannelFlag());
//                    if (chnId.equals(DataBaseConstants_USR.CHNL_ID_APP)) {
                    	
                        String htmlDomainName =
                                (String) DipperParm.getParmByKey(CmparmConstants.HTML_DOMAIN_NAME);
//                        channelLeafSearchDto.setAppChannelUrl(channelMenuBookPos.get(i).getAppChannelUrl());
                        channelLeafSearchDto.setAppChannelUrl(channelMenuBookPos.get(i).getChannelUrl());
                        if(null!=channelMenuBookPos.get(i).getChannelLogo()){
                    		channelLeafSearchDto.setChannelLogo(htmlDomainName
                                    + channelMenuBookPos.get(i).getChannelLogo());
                    	}
//                    }
                    channelLeafSearchDtoList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(channelLeafSearchDto));
            	}
            }

        }

        channelLeafSearchDto.setChannelLeafSearchDtoList(channelLeafSearchDtoList);
        logger.debug("------------------------------------end");

        return channelLeafSearchDto;
    }
}
