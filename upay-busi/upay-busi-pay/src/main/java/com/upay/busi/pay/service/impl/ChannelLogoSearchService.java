package com.upay.busi.pay.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.ChannelLogoSearchDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.pay.ChannelLogoBookPo;


/**
 * 获取渠道logo
 * 
 * @author liyulong
 * 
 */
public class ChannelLogoSearchService extends AbstractDipperHandler<ChannelLogoSearchDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(ChannelLogoSearchService.class);


    @Override
    public ChannelLogoSearchDto execute(ChannelLogoSearchDto channelLogoSearchDto, Message msg)
            throws Exception {
        // 取出渠道ID
        String channelid = channelLogoSearchDto.getChannelId();
        ChannelLogoBookPo channelLogoBookPo = new ChannelLogoBookPo();
        List<ChannelLogoBookPo> channelLogoBookPos = new ArrayList<ChannelLogoBookPo>();
        List<Map<String, Object>> channelLogoSearchDtoList = new ArrayList<Map<String, Object>>();
        if (StringUtils.isBlank(channelid)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "渠道ID");
        } else {
            channelLogoBookPo.setChannelId(channelid);
            channelLogoBookPos = daoService.selectList(channelLogoBookPo);
            if (null == channelLogoBookPos || channelLogoBookPos.size() == 0) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "logo");
            } else {
                for (int i = 0; i < channelLogoBookPos.size(); i++) {
                    channelLogoSearchDto.setLogoId(channelLogoBookPos.get(i).getLogoId());
                    channelLogoSearchDto.setLogoName(channelLogoBookPos.get(i).getLogoName());
                    channelLogoSearchDto.setLogoClass(channelLogoBookPos.get(i).getLogoClass());
                    channelLogoSearchDto.setChannelTranslmt(MoneyUtil.moneyFormat(channelLogoBookPos.get(i)
                        .getChannelTranslmt()));
                    channelLogoSearchDtoList
                        .add(BeanCopyUtil.copyBean2MapStrObjNoClass(channelLogoSearchDto));
                }
                channelLogoSearchDto.setChannelLogoSearchDtoList(channelLogoSearchDtoList);
                logger.debug("-------------------------------------end");
            }
        }
        // logger.debug(dto.getChannelLogoSearchDtoList().size() + "");
        return channelLogoSearchDto;
    }
}
