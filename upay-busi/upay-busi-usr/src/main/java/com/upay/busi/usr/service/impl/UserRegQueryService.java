package com.upay.busi.usr.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.UserRegQueryDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrRegInfoPo;
import com.upay.dao.po.usr.UsrWithoutPwdBookPo;


/**
 * 注册信息查询
 *
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 上午11:02:42
 */
public class UserRegQueryService extends AbstractDipperHandler<UserRegQueryDto> {

    private static final Logger log = LoggerFactory.getLogger(UserRegQueryService.class);

    @Resource
    private IDaoService daoService;


    @Override
    public UserRegQueryDto execute(UserRegQueryDto dto, Message message) throws Exception {
        String userId = dto.getUserId();
//        String mobile = dto.getMobile();
        String certNo = dto.getCertNo();
        String certType = dto.getCertType();
        String merNo = dto.getMerNo();
        String unionNo = dto.getUnionPlatNo();
//        String email = dto.getEmail();
        String transCode = dto.getTransCode();// 用户重置登录密码时需要用到
        String userName = dto.getUserName();
        // 通过用户id获取注册信息
        String msg = "";
        UsrRegInfoPo reg = null;
      /*  Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, String>> orderByList = new ArrayList<Map<String, String>>();
        Map<String, String> orderByActiveTime = new HashMap<String, String>();
        orderByActiveTime.put("columnName", "ACTIVE_TIME");
        orderByActiveTime.put("sort", "desc");
        orderByList.add(orderByActiveTime);
        map.put("orderBy", orderByList);*/
        if (transCode.equals("SI_USR0022")) {
            // 手机登录  手机号和用户id  唯一确定
           /* if (StringUtils.isNotBlank(mobile)) {
                dto.setLoginMode(DataBaseConstants_USR.LOGIN_MODE_MOBILE);
                reg = new UsrRegInfoPo();
                reg.setMobile(mobile);
                reg = daoService.selectOne(reg);
                //   邮箱登陆
            } else if (StringUtils.isNotBlank(email)) {
                reg = new UsrRegInfoPo();
                reg.setComEmail(email);
                reg = daoService.selectOne(reg);
            }*/
           /* if (StringUtils.isNotBlank(mobile)) {
               *//* reg = new UsrRegInfoPo();
                dto.setLoginMode(DataBaseConstants_USR.LOGIN_MODE_MOBILE);// 手机登录
                reg.setMobile(mobile);
                reg = daoService.selectOne(reg);*//*
                map.put("mobile", mobile);
                List<UsrRegInfoPo> usrRegInfoPos = daoService.selectList(UsrRegInfoPo.class.getName() + ".selectList", map);
                if (usrRegInfoPos != null && usrRegInfoPos.size() > 0) {
                    reg = usrRegInfoPos.get(0);
                }
            } else if (StringUtils.isNotBlank(email)) {
               *//* reg = new UsrRegInfoPo();
                reg.setComEmail(email);
                reg = daoService.selectOne(reg);*//*
                map.put("comEmail", email);
                List<UsrRegInfoPo> usrRegInfoPos = daoService.selectList(UsrRegInfoPo.class.getName() + ".selectList", map);
                if (usrRegInfoPos != null && usrRegInfoPos.size() > 0) {
                    reg = usrRegInfoPos.get(0);
                }

            }*/

            // 用户名登陆
             if (StringUtils.isNotBlank(userName)) {
                dto.setLoginMode(DataBaseConstants_USR.USER_LOGIN_MODE_NAME);
                reg = new UsrRegInfoPo();
                reg.setUserName(userName);
                reg = daoService.selectOne(reg);
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户查询参数");
            }
        } else {
            if (StringUtils.isNotBlank(userId)) {
                reg = new UsrRegInfoPo();
                reg.setUserId(userId);
                reg = daoService.selectOne(reg);
                // 通过手机号获取注册信息
            }
            else if (StringUtils.isNotBlank(userName)) {
                dto.setLoginMode(DataBaseConstants_USR.USER_LOGIN_MODE_NAME);
                reg = new UsrRegInfoPo();
                reg.setUserName(userName);
                reg = daoService.selectOne(reg);
            }
            /*else if (StringUtils.isNotBlank(mobile)) {*/
               /* reg = new UsrRegInfoPo();
                dto.setLoginMode(DataBaseConstants_USR.LOGIN_MODE_MOBILE);// 手机登录
                reg.setMobile(mobile);
                reg = daoService.selectOne(reg);*/
              /*  map.put("mobile", mobile);
                List<UsrRegInfoPo> usrRegInfoPos = daoService.selectList(UsrRegInfoPo.class.getName() + ".selectList", map);
                if (usrRegInfoPos != null && usrRegInfoPos.size() > 0) {
                    reg = usrRegInfoPos.get(0);
                }
            } else if (StringUtils.isNotBlank(email)) {*/
               /* reg = new UsrRegInfoPo();
                reg.setComEmail(email);
                reg = daoService.selectOne(reg);*/
               /* map.put("comEmail", email);*/
              /*  List<UsrRegInfoPo> usrRegInfoPos = daoService.selectList(UsrRegInfoPo.class.getName() + ".selectList", map);
                if (usrRegInfoPos != null && usrRegInfoPos.size() > 0) {
                    reg = usrRegInfoPos.get(0);
                }

            }*/
            // 通过证件号获取注册信息
            else if (StringUtils.isNotBlank(certNo) && StringUtils.isNotBlank(certType)) {
                dto.setLoginMode(DataBaseConstants_USR.LOGIN_MODE_CERTNO);// 身份证登录
                UsrBaseInfoPo base = new UsrBaseInfoPo();
                // base.setCertType(certType);
                base.setCertNo(certNo);
                base = daoService.selectOne(base);
                if (base != null) {
                    reg = new UsrRegInfoPo();
                    reg.setUserId(base.getUserId());
                    reg = daoService.selectOne(reg);
                }
                // 通过商户账号获取注册信息
            } else if (StringUtils.isNotBlank(merNo) && StringUtils.isNotBlank(unionNo)) {
                UsrWithoutPwdBookPo wit = new UsrWithoutPwdBookPo();
                wit.setMerNo(dto.getMerNo());
                wit.setUnionPlatNo(unionNo);
                wit = daoService.selectOne(wit);
                if (wit != null) {
                    reg = new UsrRegInfoPo();
                    reg.setUserId(wit.getUserId());
                    reg = daoService.selectOne(reg);
                }
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户查询参数");
            }
        }

        if (reg != null) {
            String blacklistFlag = reg.getBlacklistFlag();
            if (DataBaseConstants_USR.BLACK_LIST_FLAG_YES.equals(blacklistFlag)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0014);
            }

            /**
             * 静态资源服务器域名
             */
            // String imgDomainName = (String)
            // DipperParm.getParmByKey(CmparmConstants.IMG_DOMAIN_NAME);

            SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
            String imgDomainName = (String) DipperParm.getParmByKey(CmparmConstants.IMG_DOMAIN_NAME);
            dto.setMobile(reg.getMobile());
            dto.setUserName(reg.getUserName());
            dto.setUserNickName(reg.getUserNickName());
            dto.setHeadPic(imgDomainName + reg.getHeadPic());
            dto.setLastLogpwdModifytime(reg.getLastLogpwdModifytime() == null ? null : sim.format(reg
                    .getLastLogpwdModifytime()));
            dto.setLastTradepwdModifytime(reg.getLastTradepwdModifytime() == null ? null : sim.format(reg
                    .getLastTradepwdModifytime()));
            dto.setGasturePwd(reg.getGasturePwd());
            dto.setLastGasturepwdModifytime(reg.getLastGasturepwdModifytime() == null ? null : sim.format(reg
                    .getLastGasturepwdModifytime()));
            dto.setRegTime(reg.getRegTime() == null ? null : sim.format(reg.getRegTime()));
            dto.setRegChnlId(reg.getRegChnlId());
            dto.setUserCertLevel(reg.getUserCertLevel());
            dto.setUserValueLevel(reg.getUserValueLevel());
            dto.setBranchId(reg.getBranchId());
            dto.setUserStat(reg.getUserStat());
            dto.setUserLockFlag(reg.getUserLockFlag());
            dto.setBlacklistFlag(reg.getBlacklistFlag());
            dto.setBlacklistType(reg.getBlacklistType());
            dto.setSecurityType(reg.getSecurityType());
            dto.setActiveTime(reg.getActiveTime() == null ? null : sim.format(reg.getActiveTime()));
            dto.setActiveChnlId(reg.getActiveChnlId());
            dto.setCloseTime(reg.getCloseTime() == null ? null : sim.format(reg.getCloseTime()));
            dto.setCloseChnlId(reg.getCloseChnlId());
            dto.setLastLoginTime(reg.getLastLoginTime() == null ? null : sim.format(reg.getLastLoginTime()));
            dto.setLastUpdateTime(reg.getLastUpdateTime() == null ? null
                    : sim.format(reg.getLastUpdateTime()));
            dto.setRegType(reg.getRegType());
            dto.setApproveType(reg.getRegType());
            dto.setUserId(reg.getUserId());
            dto.setComEmail(reg.getComEmail());
            dto.setMerLevel(reg.getMerLevel());
            dto.setMerNo(merNo);

            String tradePwd = reg.getTradePwd();
            if (StringUtils.isNotBlank(tradePwd)) {
                // 已经设置了交易密码
                dto.setPayPwdFlag(DataBaseConstants_USR.PAY_PWD_YES);
            } else {
                // 开户但没有设置支付密码
                dto.setPayPwdFlag(DataBaseConstants_USR.PAY_PWD_NO2);
            }
        } else {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0047);
        }
        return dto;
    }

}
