package com.upay.busi.usr.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.UserPreloginDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrLogListPo;
import com.upay.dao.po.usr.UsrRegInfoPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

/**
 * 预计登陆信息
 *
 * @author shangqiankun
 * @version 创建时间：2016年7月20日 下午2:31:21
 */
public class UserPreloginService extends AbstractDipperHandler<UserPreloginDto> {

    private static final Logger log = LoggerFactory
            .getLogger(UserPreloginService.class);

    @Resource
    private IDaoService daoService;
    @Resource
    private IDipperCached cached;

    @Override
    public UserPreloginDto execute(UserPreloginDto dto, Message message)
            throws Exception {
        Date now = new Date();
        // 校验参数
        String userId = dto.getUserId();
       /* String mobile = dto.getMobile();
        String email = dto.getEmail();*/
      /*  if (StringUtils.isBlank(userId) && StringUtils.isBlank(mobile)
                && StringUtils.isBlank(email)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户id或手机号或email");
        }*/
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户id");
        }
        // 验证该用户是否存在
        UsrRegInfoPo reg = null;
        if (StringUtils.isNotBlank(userId)) {
            reg = new UsrRegInfoPo();
            reg.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
            reg.setUserId(userId);
            reg = daoService.selectOne(reg);
            if (reg == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, userId);
            }
        } /*else if (StringUtils.isNotBlank(mobile)) {
           *//*
            reg = new UsrRegInfoPo();
			reg.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
			reg.setMobile(mobile);
			reg = daoService.selectOne(reg);*//*
            Map<String, Object> map = new HashMap<String, Object>();
            List<Map<String, String>> orderByList = new ArrayList<Map<String, String>>();
            Map<String, String> orderByActiveTime = new HashMap<String, String>();
            orderByActiveTime.put("columnName", "ACTIVE_TIME");
            orderByActiveTime.put("sort", "desc");
            orderByList.add(orderByActiveTime);
            map.put("orderBy", orderByList);
            map.put("userStat", DataBaseConstants_USR.USER_STAT_NORMAL);
            map.put("mobile", mobile);
            List<UsrRegInfoPo> usrRegInfoPos = daoService.selectList(UsrRegInfoPo.class.getName() + ".selectList", map);
            if (usrRegInfoPos != null && usrRegInfoPos.size() > 0) {
                reg = usrRegInfoPos.get(0);
            }
            if (reg == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, mobile);
            }
        } else if (StringUtils.isNotBlank(email)) {
			*//*UsrBaseInfoPo base = new UsrBaseInfoPo();
			base.setEmail(email);
			base = new UsrBaseInfoPo();
			if (base != null) {
				reg = new UsrRegInfoPo();
				reg.setUserId(base.getUserId());
				reg = daoService.selectOne(reg);
			}
			if (reg == null) {
				ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, email);
			}*//*
            Map<String, Object> map = new HashMap<String, Object>();
            List<Map<String, String>> orderByList = new ArrayList<Map<String, String>>();
            Map<String, String> orderByActiveTime = new HashMap<String, String>();
            orderByActiveTime.put("columnName", "ACTIVE_TIME");
            orderByActiveTime.put("sort", "desc");
            orderByList.add(orderByActiveTime);
            map.put("orderBy", orderByList);
            map.put("userStat", DataBaseConstants_USR.USER_STAT_NORMAL);
            map.put("comEmail", email);
            List<UsrRegInfoPo> usrRegInfoPos = daoService.selectList(UsrRegInfoPo.class.getName() + ".selectList", map);
            if (usrRegInfoPos != null && usrRegInfoPos.size() > 0) {
                reg = usrRegInfoPos.get(0);
            }
            if (reg == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, email);
            }
        }*/
        String sessionId = UUID.randomUUID().toString().replace("-", "");
        // 单点登录判断
        String isMutiplue = isMutiplueLogin(userId, dto.getChnlId(), sessionId);
        // String isMutiplue=DataBaseConstants_USR.IS_MUTIPLUE_LOGIN_YES;
//        mobile = reg.getMobile();
        userId = reg.getUserId();
        // 登记登录信息
        UsrLogListPo log = new UsrLogListPo();
        log.setUserId(userId);
        log.setChnlId(dto.getChnlId());
        log.setLoginMode(DataBaseConstants_USR.USER_LOGIN_MODE_EXEMPT);
        log.setIsMutiplueLogin(isMutiplue);
        log.setSessionId(sessionId);
        log.setPlatform(dto.getPlatform());
        log.setLoginTime(now);
        log.setLoginAddr(dto.getLoginAddr());
        log.setLoginIp(dto.getLoginIp());
        log.setAddrGetFlag(dto.getAddrGetFlag() == null ? DataBaseConstants_USR.ADDR_GET_FLAG_NO
                : dto.getAddrGetFlag());
        log.setAddrProv(dto.getAddrProv());
        log.setAddrCity(dto.getAddrCity());
        log.setAddrCounty(dto.getAddrCounty());
        log.setAddrDetail(dto.getAddrDetail());
        if (DataBaseConstants_USR.USR_LOGIN_STAT_FAIL
                .equals(dto.getLoginStat())) {
            log.setLoginStat(dto.getLoginStat());
            log.setFailReason(message.getFault().getMsg());
        } else {
            log.setLoginStat(DataBaseConstants_USR.USR_LOGIN_STAT_SUCCUESS);
        }
        daoService.insert(log);
        dto.setSessionId(sessionId);
        return dto;
    }

    /**
     * 获取单点登录状态
     *
     * @param userId
     * @param chnlId
     * @param sessionId
     * @return
     */
    private String isMutiplueLogin(String userId, String chnlId,
                                   String sessionId) {

        String isMutiplueLogin = DataBaseConstants_USR.IS_MUTIPLUE_LOGIN_NO;

        // 判断多渠道登录的协同登录状态
        if (CommonConstants_GNR.CHNL_ID_APP.equals(chnlId)) {
            String sessionWeb = cached.get(CacheConstants.SESSION_WEB
                    .concat(userId));
            if (StringUtils.isNotBlank(sessionWeb)) {
                isMutiplueLogin = DataBaseConstants_USR.IS_MUTIPLUE_LOGIN_YES;
            }
        } else if (CommonConstants_GNR.CHNL_ID_WEB.equals(chnlId)) {
            String sessionApp = cached.get(CacheConstants.SESSION_APP
                    .concat(userId));
            if (StringUtils.isNotBlank(sessionApp)) {
                isMutiplueLogin = DataBaseConstants_USR.IS_MUTIPLUE_LOGIN_YES;
            }
        }

        return isMutiplueLogin;

    }

}
