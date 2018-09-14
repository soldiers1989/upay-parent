package com.upay.busi.acc.service.impl;

import javax.annotation.Resource;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.acc.service.dto.ZjpaySysSeqDto;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;


/**
 * gateway 生成流水号
 * 
 * @author: liubing
 * @CreateDate:2015年4月8日
 * 
 */
public class ZjpaySysSeqService extends AbstractDipperHandler<ZjpaySysSeqDto> {

    @Resource
    private ISequenceService sequenceService;


    @Override
    public ZjpaySysSeqDto execute(ZjpaySysSeqDto zjpaySysSeqDto, Message msg) throws Exception {

        String gateWay_zjpay_sysSeq = sequenceService.generateZjpaySysSeq(SysInfoContext.getSysTime());
        zjpaySysSeqDto.setTxSNBinding(gateWay_zjpay_sysSeq);
        
        //如果 是信用卡需要截取后四位
//        String validDate = zjpaySysSeqDto.getValidDate();
//        if(!StringUtils.isBlank(validDate)){
//        	zjpaySysSeqDto.setValidDate(validDate.substring(2, 6));
//        }

        return zjpaySysSeqDto;
    }

    /**
     * @param sequenceService
     *            the sequenceService to set
     */
    public void setSequenceService(ISequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }

}
