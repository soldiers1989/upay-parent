package com.upay.busi.gnr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.gnr.service.dto.SrvPostProcessingDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.dao.po.gnr.GnrOperateListPo;


/**
 * 接口服务后处理 步骤 1.根据交易成功状态更新用户操作流水状态 2.交易成功的话，保持session会话时间重置为默认时间
 * 2.交易成功的话，判断是否有事件（短信/消息）需要发送
 * 
 * @author freeplato
 * 
 */
public class SrvPostProcessingServiceImpl extends AbstractDipperHandler<SrvPostProcessingDto> {

    @Resource
    private IDaoService daoService;
    @Resource
    private IDipperCached cacheClient;


    @Override
    public SrvPostProcessingDto execute(SrvPostProcessingDto srvPostProcessingDTO, Message message)
            throws Exception {
        String userId = srvPostProcessingDTO.getUserId();
        String sysSeq = srvPostProcessingDTO.getSysSeq();
        if (StringUtils.isNotBlank(sysSeq)) {

            String rspCode = message.getFault().getCode();
            String rspMsg = CommonConstants_GNR.RSP_CODE_SUCCESS_DESC;
            String operateStat = CommonConstants_GNR.OPERATE_STAT_SUCCESS;
            if (!CommonConstants_GNR.RSP_CODE_SUCCESS.equals(rspCode)) {
                rspMsg = message.getFault().getMsg();
                operateStat = CommonConstants_GNR.OPERATE_STAT_FAIL;
            } else {
                String chnlId = srvPostProcessingDTO.getChnlId();
                String sessionId = srvPostProcessingDTO.getSessionId();
                if (StringUtils.isNotBlank(sessionId)) {
                    sessionReset(chnlId, userId, sessionId);
                }
            }

            GnrOperateListPo gnrOperateListWhere = new GnrOperateListPo();
            gnrOperateListWhere.setOperSeq(sysSeq);
            GnrOperateListPo gnrOperateListUpd = new GnrOperateListPo();
            gnrOperateListUpd.setOperStat(operateStat);
            gnrOperateListUpd.setRspCode(rspCode);
            gnrOperateListUpd.setRspMsg(rspMsg);
            daoService.update(gnrOperateListUpd, gnrOperateListWhere);

        }

        return srvPostProcessingDTO;
    }


    /**
     * 当交易成功后，重置session会话为默认会话时间
     * 
     * @param chnlId
     * @param userId
     * @param sessionId
     */
    private void sessionReset(String chnlId, String userId, String sessionId) {
        if (CommonConstants_GNR.CHNL_ID_APP.equals(chnlId)) {
            if (cacheClient.keyExists(CacheConstants.SESSION_APP.concat(userId))) {
                int sessionValidMinute =
                        Integer.valueOf(DipperParm.getParmByKey(CmparmConstants.SESSION_APP_INVALID_MINUTE)
                            .toString());
                cacheClient
                    .set(CacheConstants.SESSION_APP.concat(userId), sessionId, sessionValidMinute * 60);
            }
        } else if (CommonConstants_GNR.CHNL_ID_WEB.equals(chnlId)) {
            if (cacheClient.keyExists(CacheConstants.SESSION_WEB.concat(userId))) {
                int sessionValidMinute =
                        Integer.valueOf(DipperParm.getParmByKey(CmparmConstants.SESSION_WEB_INVALID_MINUTE)
                            .toString());
                cacheClient
                    .set(CacheConstants.SESSION_WEB.concat(userId), sessionId, sessionValidMinute * 60);
            }
        }
    }

}
