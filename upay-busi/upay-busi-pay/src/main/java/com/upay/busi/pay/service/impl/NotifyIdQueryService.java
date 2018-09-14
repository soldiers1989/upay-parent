package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.NotifyIdQueryDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerNotifiyPo;


/**
 * 商户通知ID检查
 * 
 * @author liu
 * 
 */
public class NotifyIdQueryService extends AbstractDipperHandler<NotifyIdQueryDto> {

    private static final Logger LOG = LoggerFactory.getLogger(NotifyIdQueryService.class);

    @Resource
    private IDaoService daoService;


    @Override
    public NotifyIdQueryDto execute(NotifyIdQueryDto notifyIdDto, Message msg) throws Exception {

        String merNo = notifyIdDto.getMerNo();
        String notifyId = notifyIdDto.getNotifyId();

        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISMER0013, "商户号");// 商户号为空异常
        }
        if (StringUtils.isBlank(notifyId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISMER0015); // 通知ID为空异常
        }

        // 如果商户号和通知ID不为空，则去查询商户通知表，查询通知状态，根据通知状态返回resCode,resMsg
        MerNotifiyPo merNotifyPo = new MerNotifiyPo();
        merNotifyPo.setMerNo(merNo);
        merNotifyPo.setNotifyId(notifyId);

        merNotifyPo = daoService.selectOne(merNotifyPo);
        if (merNotifyPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISMER0016); // 输入信息有误，请确认信息是否正确
        }

        String notifyStatus = merNotifyPo.getNotifyStatus();
        if (DataBaseConstants_PAY.MER_NOTIFY_STATUS_TRUE.equals(notifyStatus)) {
            notifyIdDto.setResCode(DataBaseConstants_PAY.MER_NOTIFY_RESCODE_TRUE); // 表示成功的返回码
            notifyIdDto.setResMsg(DataBaseConstants_PAY.MER_NOTIFY_RESMSG_TRUE);
        } else if (DataBaseConstants_PAY.MER_NOTIFY_STATUS_FALSE.equals(notifyStatus)) {
            notifyIdDto.setResCode(DataBaseConstants_PAY.MER_NOTIFY_RESCODE_FALSE); // 表示失败的返回码
            notifyIdDto.setResMsg(DataBaseConstants_PAY.MER_NOTIFY_RESMSG_FALSE);
        }

        return notifyIdDto;
    }

}
