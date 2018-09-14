package com.upay.busi.usr.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.utils.common.UUIDGenerator;
import com.upay.busi.usr.service.dto.FreePwdAuthorizeCheckDto;
import com.upay.commons.constants.*;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.usr.UsrWithoutPwdBookPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by Guo on 16/8/25.
 * 授权免密检查
 */
public class FreePwdAuthorizeCheckService extends AbstractDipperHandler<FreePwdAuthorizeCheckDto> {
    private static final Logger LOG = LoggerFactory.getLogger(FreePwdAuthorizeCheckService.class);

    @Resource
    private IDaoService daoService;

    @Resource
    private IDipperCached cacheClient;

    @Override
    public FreePwdAuthorizeCheckDto execute(FreePwdAuthorizeCheckDto dto, Message message) throws Exception {
        LOG.info("授权免密检查开始");

        String merNo = dto.getMerNo();
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "商户号");
        }
        String unionPlatNo = dto.getPlatformUserNo();
        if (StringUtils.isBlank(unionPlatNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户号");
        }
//        String mobile = dto.getMobile();
//        if (StringUtils.isBlank(mobile)) {
//            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "手机号");
//        }
        String chnlId = dto.getChnlId();
        if (StringUtils.isBlank(chnlId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "支付渠道");
        }

        //检查商户是否授权免密商户
        MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
        merBaseInfoPo.setMerNo(merNo);
        merBaseInfoPo = daoService.selectOne(merBaseInfoPo);

        if (null != merBaseInfoPo) {
            String merWithoutPwdSign = merBaseInfoPo.getMerWithoutPwdSign();

            String merState = merBaseInfoPo.getMerState();//商户状态
            if (!DateBaseConstants_MER.MER_STAT_NORMAL.equals(merState)) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0010, merNo);
            }

            String payOpenFlag = merBaseInfoPo.getPayOpenFlag();//支付功能开通标志
            if (DateBaseConstants_MER.MER_PAYOPENFLAG_CLOSE.equals(payOpenFlag)) {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0011, merNo);
            }

            //如果是授权免密商户,则检查是否存在用户绑定关系
            if (DataBaseConstants_USR.Mer_WITHOUT_PWD_SIGN_YES.equals(merWithoutPwdSign)) {
                //检查是否绑定授权免密关系
                UsrWithoutPwdBookPo usrWithoutPwdBookPo = new UsrWithoutPwdBookPo();
                usrWithoutPwdBookPo.setMerNo(merNo);
                usrWithoutPwdBookPo.setUnionPlatNo(unionPlatNo);
                usrWithoutPwdBookPo = daoService.selectOne(usrWithoutPwdBookPo);

                if (null != usrWithoutPwdBookPo) {
                    //如果绑定标志为已绑定,则更新登陆状态为已登陆,并生成sessionId
                    if (DataBaseConstants_USR.MERCHANT_BIND_STAT_BIND.equals(usrWithoutPwdBookPo.getBindStat())) {
                        String sessionId = UUIDGenerator.randomUUID();//生成sessionId
                        String userId = usrWithoutPwdBookPo.getUserId();
                        if(CommonConstants_GNR.CHNL_ID_APP.equals(chnlId)){
                            int sessionValidMinute = Integer.valueOf(String.valueOf(DipperParm.getParmByKey(CmparmConstants.SESSION_APP_INVALID_MINUTE))) ;
                            cacheClient.set(CacheConstants.SESSION_APP.concat(userId), sessionId, sessionValidMinute * 60);
                        }else if (CommonConstants_GNR.CHNL_ID_WEB.equals(chnlId)){
                            int sessionValidMinute = Integer.valueOf(String.valueOf(DipperParm.getParmByKey(CmparmConstants.SESSION_WEB_INVALID_MINUTE)));
                            cacheClient.set(CacheConstants.SESSION_WEB.concat(userId), sessionId, sessionValidMinute * 60);
                        }
                        dto.setSessionId(sessionId);
                        dto.setUserId(userId);
                    }
                }
            }
        } else {
            ExInfo.throwDipperEx(AppCodeDict.BISMER0018,merNo);
        }

        LOG.info("授权免密检查结束");
        return dto;
    }
}
