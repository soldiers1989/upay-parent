package com.upay.busi.usr.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import javax.annotation.Resource;

import com.upay.dao.po.mer.MerApplyBookPo;
import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.MailSignDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.Md5Util;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.usr.UsrComRegRecPo;


/**
 * 邮箱加签，内容拼接
 * 
 * @author liyulong
 * 
 */
public class MailSignService extends AbstractDipperHandler<MailSignDto> {
    @Resource
    private IDaoService daoService;

    private String mailTemplet;


    @Override
    public MailSignDto execute(MailSignDto mailSignDto, Message msg) throws Exception {
        String mailTo = mailSignDto.getMailTo();
        StringBuilder mailContent = new StringBuilder();
        String funcType = mailSignDto.getFuncType();// 01：注册 02：重置密码
        String mailFrom = (String) DipperParm.getParmByKey(CmparmConstants.MAIL_FROM);
        String mailSubject = null;
        String userName = mailSignDto.getUserName();
        if (StringUtils.isBlank(mailTo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "企业邮箱");
        }
        if (StringUtils.isBlank(funcType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "功能类型 ");
        }
        // ClassLoader classLoader = getClass().getClassLoader();
        if (funcType.equals(DataBaseConstants_USR.FUNC_TYPE_REG)) {
            mailSubject = "商户注册";
          /*  UsrComRegRecPo usrComRegRecPo = new UsrComRegRecPo();
            usrComRegRecPo.setComEmail(mailTo);
            usrComRegRecPo.setTransCode(DataBaseConstants_USR.TRANS_CODE_SENDMAIL);
            usrComRegRecPo = daoService.selectOne(usrComRegRecPo);*/

            Map<String, Object> whereMap = new HashMap<>(10);
            whereMap.put("comEmail", mailTo);
            whereMap.put("transCode",DataBaseConstants_USR.TRANS_CODE_SENDMAIL);
            List<Map<String, String>> orderByList = new ArrayList<>(10);
            Map<String, String> orderByMerApplyNo= new HashMap<>(10);
            orderByMerApplyNo.put("columnName", "LAST_SEND_TIME");
            orderByMerApplyNo.put("sort", "desc");
            orderByList.add(orderByMerApplyNo);
            whereMap.put("orderBy", orderByList);
            List<UsrComRegRecPo> usrComRegRecPos = daoService.selectList(UsrComRegRecPo.class.getName() + ".selectList", whereMap);
//            if (usrComRegRecPo != null) {
            if (usrComRegRecPos != null&&usrComRegRecPos.size()>0) {
                // 如果该企业用户已经生成
                // TODO:不做唯一性校验（需求修改）
               /* if (DataBaseConstants_USR.GENERATE_FLAG_YES.equals(usrComRegRecPo.getGenerateFlag())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0002, "企业邮箱");
                }*/
                UsrComRegRecPo usrComRegRecPo = usrComRegRecPos.get(0);
                if (usrComRegRecPo != null) {
                    Date lastTime = usrComRegRecPo.getLastSendTime();
                    Date sysTime = SysInfoContext.getSysTime();// 获取系统时间
                    lastTime = DateUtil.add(lastTime, Calendar.MINUTE, 5);
                    if (sysTime.compareTo(lastTime) < 0) {
                        ExInfo.throwDipperEx(AppCodeDict.BISUSR0050);
                    }
                }
            }

            // URL resource = classLoader.getResource("templet/regist.html");
            // File file = new File(resource);
            File file = new File(mailTemplet + "regist.html");
            BufferedReader reader = null;
            try {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
                reader = new BufferedReader(isr);
                String tempString = null;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    mailContent.append(tempString);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
        } else if (funcType.equals(DataBaseConstants_USR.FUNC_TYPE_RES)) {
            if (StringUtils.isBlank(userName)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户名不能为空");
            }
            Date sysTime = SysInfoContext.getSysTime();// 获取系统时间
            mailSubject = "重置登录密码";

            /*UsrComRegRecPo usrComRegRecPo = new UsrComRegRecPo();
            usrComRegRecPo.setComEmail(mailTo);
            usrComRegRecPo.setGenerateFlag(DataBaseConstants_USR.GENERATE_FLAG_YES);
            usrComRegRecPo.setTransCode(DataBaseConstants_USR.TRANS_CODE_SENDMAIL);
            usrComRegRecPo = daoService.selectOne(usrComRegRecPo);
            if (usrComRegRecPo == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0029, "该企业用户");
            }*/

            Map<String, Object> whereMap = new HashMap<>(10);
            whereMap.put("comEmail", mailTo);
            whereMap.put("transCode",DataBaseConstants_USR.TRANS_CODE_SENDMAIL);
            whereMap.put("generateFlag",DataBaseConstants_USR.GENERATE_FLAG_YES);
            List<Map<String, String>> orderByList = new ArrayList<>(10);
            Map<String, String> orderByMerApplyNo= new HashMap<>(10);
            orderByMerApplyNo.put("columnName", "LAST_SEND_TIME");
            orderByMerApplyNo.put("sort", "desc");
            orderByList.add(orderByMerApplyNo);
            whereMap.put("orderBy", orderByList);
            List<UsrComRegRecPo> usrComRegRecPos = daoService.selectList(UsrComRegRecPo.class.getName() + ".selectList", whereMap);
            if (usrComRegRecPos == null||usrComRegRecPos.size()==0) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0029, "该企业用户");
            }

           /* UsrComRegRecPo usrComRegRecPo2 = new UsrComRegRecPo();
            usrComRegRecPo2.setComEmail(mailTo);
            usrComRegRecPo2.setTransCode(DataBaseConstants_USR.TRANS_CODE_SEND_MAIL);
            usrComRegRecPo2 = daoService.selectOne(usrComRegRecPo2);*/
            whereMap.remove("generateFlag");
            usrComRegRecPos = daoService.selectList(UsrComRegRecPo.class.getName() + ".selectList", whereMap);
            if (usrComRegRecPos == null||usrComRegRecPos.size()==0) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0029, "该企业用户");
            }
            Date lastTime = SysInfoContext.getSysTime();
            if (usrComRegRecPos != null&&usrComRegRecPos.size()>0) {
                UsrComRegRecPo usrComRegRecPo = usrComRegRecPos.get(0);
                if(usrComRegRecPo!=null){
                    lastTime = usrComRegRecPo.getLastSendTime();
                    lastTime = DateUtil.add(lastTime, Calendar.MINUTE, 5);
                    if (sysTime.compareTo(lastTime) < 0) {
                        ExInfo.throwDipperEx(AppCodeDict.BISUSR0050);
                    }
                }
            }

            // URL resource = classLoader.getResource("templet/resetmail.html");
            // log.info(resource.getPath());
            // log.info(resource.getHost());
            // File file = new File(resource.getFile());
            // File file = new
            // File("/home/appsvr/global-resources/config/flow/usr/resetmail.html");
            File file = new File(mailTemplet + "resetmail.html");
            BufferedReader reader = null;
            try {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
                reader = new BufferedReader(isr);
                String tempString = null;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    mailContent.append(tempString);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
        }

        String md5Mail = Md5Util.toMD5(mailTo + "1");
        String[] parms = new String[5];
        String imgDomainName = (String) DipperParm.getParmByKey(CmparmConstants.IMG_DOMAIN_NAME);// 图片服务器域名
        String mailImg = (String) DipperParm.getParmByKey(CmparmConstants.MAIL_IMG);// 邮件图标
        parms[0] = imgDomainName + mailImg;
        parms[1] = mailTo;
        String localIp = (String) DipperParm.getParmByKey(CmparmConstants.LOCAL_IP);
        if (funcType.equals(DataBaseConstants_USR.FUNC_TYPE_REG)) {
            parms[2] =
                    localIp + "app/register/register_business.html?email=" + md5Mail + "&transCode="
                            + Md5Util.toMD5(DataBaseConstants_USR.TRANS_CODE_SENDMAIL);
        } else if (funcType.equals(DataBaseConstants_USR.FUNC_TYPE_RES)) {
            parms[2] =
                    localIp + "app/resetPwd/resetPwd_business.html?email=" + md5Mail + "&transCode="
                            + Md5Util.toMD5(DataBaseConstants_USR.TRANS_CODE_SEND_MAIL)+"&userName="+userName;
        }
        parms[3] = parms[2];
        parms[4] = parms[2];
        String content = mailContent.toString();
        for (int i = 0; i < parms.length; i++) {
            content = content.replaceFirst("\\$", parms[i]);
        }
        mailSignDto.setMailTo(mailTo);
        mailSignDto.setMailContent(content);
        mailSignDto.setMailFrom(mailFrom);
        mailSignDto.setMailSubject(mailSubject);
        return mailSignDto;
    }


    public String getMailTemplet() {
        return mailTemplet;
    }


    public void setMailTemplet(String mailTemplet) {
        this.mailTemplet = mailTemplet;
    }

}
