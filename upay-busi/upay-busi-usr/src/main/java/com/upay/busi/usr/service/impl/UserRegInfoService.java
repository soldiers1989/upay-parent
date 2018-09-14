package com.upay.busi.usr.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import com.pactera.dipper.utils.common.UUIDGenerator;
import org.apache.commons.lang.StringUtils;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.usr.service.dto.UserRegDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.AppPwdUtil;
import com.upay.commons.util.ValidateUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrComRegRecPo;
import com.upay.dao.po.usr.UsrRegInfoPo;
import com.upay.dao.po.usr.UsrWithoutPwdBookPo;
import com.upay.commons.encryptor.UnionAPI;

/**
 * 用户注册
 *
 * @author shangqiankun
 * @version 创建时间：2016年7月19日 上午9:23:43
 */
public class UserRegInfoService extends AbstractDipperHandler<UserRegDto> {

    private static final Logger log = LoggerFactory
            .getLogger(UserRegInfoService.class);

    @Resource
    private UnionAPI unionAPI;
    @Resource
    private IDaoService daoService;

    @Autowired
    private ISequenceService sequenceService;
    @Resource
    IDipperCached idipperCached;

    @Override
    public UserRegDto execute(UserRegDto dto, Message message) throws Exception {


        Date now = new Date();
        // 检查参数
        // String userName = dto.getUserName();
        
        String aesKey = dto.getAesKey();
        String mobile = dto.getMobile();
        // String email = dto.getEmail();
        String merNo = dto.getMerNo();
        String regType = dto.getRegType();
        String pwd = dto.getLoginPwd();
        if (StringUtils.isBlank(mobile) && StringUtils.isBlank(dto.getEmail())) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "注册手机号或邮箱");
        }
        // 判断用户注册类型
        // if (StringUtils.isBlank(regType)) {
        // ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "注册会员类型");
        // } else
        if (DataBaseConstants_USR.USER_REG_TYPE_COMMON.equals(regType)) {
            if (StringUtils.isBlank(mobile)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "手机号");
            }

            // 验证手机号格式
            if (!ValidateUtil.checkMobile(mobile)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0009, mobile);
            }
//            userName = mobile;
            dto.setEmail(null);
        } else if (DataBaseConstants_USR.USER_REG_TYPE_MEMBER.equals(regType)) {
            if (StringUtils.isBlank(dto.getEmail())) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "邮箱");
            }
            if (StringUtils.isNotBlank(dto.getEmail())) {
                if (!ValidateUtil.checkEmail(dto.getEmail())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0041, dto.getEmail());
                }
            }
//            userName = dto.getEmail();
        } else {
            if (StringUtils.isBlank(dto.getMobile())) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "手机号");
            }
        }

        boolean isCheck = false;
        // 检查注册唯一性
        Map<String, String> map = checkUserReg(dto);
        // 判断注册类型
        UsrRegInfoPo reg = new UsrRegInfoPo();
        String userId = sequenceService.generateUserId();
        if (DataBaseConstants_USR.USER_REG_TYPE_COMMON.equals(regType)
                || DataBaseConstants_USR.USER_REG_TYPE_MEMBER.equals(regType)) {
            if (StringUtils.isBlank(pwd)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "密码");
            }
            if (StringUtils.isNotBlank(map.get("result"))) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0002, map.get("result"));
            }
        } else {
            dto.setChnlId(DataBaseConstants_USR.CHNL_ID_WEB);// 富农汇注册渠道认为是web
            dto.setRegType(DataBaseConstants_USR.USER_REG_TYPE_COMMON);
            if (StringUtils.isBlank(merNo)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "商户号");
            }
            // 检查商户是否是授权免密商户
            MerBaseInfoPo mer = new MerBaseInfoPo();
            mer.setMerNo(merNo);
            mer = daoService.selectOne(mer);
            if (mer != null) {
                if (DateBaseConstants_MER.MER_STAT_NORMAL.equals(mer
                        .getMerState())) {
                    String merWi = mer.getMerWithoutPwdSign();
                    if (DataBaseConstants_USR.Mer_WITHOUT_PWD_SIGN_NO
                            .equals(merWi)) {
                        isCheck = false;
                    } else if (DataBaseConstants_USR.Mer_WITHOUT_PWD_SIGN_YES
                            .equals(merWi)) {
                        isCheck = true;
                    } else {
                        ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "授权免密绑定标识");
                    }
                } else {
                    ExInfo.throwDipperEx(AppCodeDict.BISMER0010, merNo);
                }
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0018, merNo);
            }
            if (isCheck) {
                if (StringUtils.isNotBlank(map.get("result"))) {
                    // 判断是否绑定当前商户
                    reg.setUserId(map.get("userId"));
                    reg = daoService.selectOne(reg);
                /*	boolean check = StringUtils.isBlank(dto.getMobile()) ? (StringUtils
                            .isBlank(dto.getEmail()) ? false : dto.getEmail()
							.equals(reg.getComEmail())) : dto.getMobile()
							.equals(reg.getMobile());*/
                    boolean check = StringUtils.isBlank(dto.getUserName()) ? false : dto.getUserName()
                            .equals(reg.getUserName());
                    if (check) {
                        UsrWithoutPwdBookPo withou = new UsrWithoutPwdBookPo();
                        withou.setUnionPlatNo(dto.getPlatformUserNo());
                        withou.setMerNo(dto.getMerNo());
                        withou = daoService.selectOne(withou);
                        if (withou == null) {
                            // 绑定授权免密关系
                            bindMer(dto, reg.getUserId(), now);
                            dto.setUserId(reg.getUserId());
                        } else if (withou != null
                                && dto.getMerNo().equals(withou.getMerNo())) {
                            ExInfo.throwDipperEx(AppCodeDict.BISUSR0002,
                                    map.get("result"));
                        }
                    } else {
                        ExInfo.throwDipperEx(AppCodeDict.BISUSR0002,
                                map.get("result"));
                    }
                } else {
                    UsrWithoutPwdBookPo withou = new UsrWithoutPwdBookPo();
                    withou.setUnionPlatNo(dto.getPlatformUserNo());
                    withou.setMerNo(dto.getMerNo());
                    withou = daoService.selectOne(withou);
                    if (withou == null) {
                        // 绑定授权免密关系
                        bindMer(dto, userId, now);
                    } else if (withou != null
                            && dto.getMerNo().equals(withou.getMerNo())) {
                        ExInfo.throwDipperEx(AppCodeDict.BISUSR0002,
                                dto.getPlatformUserNo());
                    }
                }
            } else {
                // 非授权免密商户：注册失败
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0001, merNo);
            }
        }
        dto.setUserId(userId);
        // 添加注册信息
        // 将输入密码加密 以便与数据库密码 比较
        boolean encryptorFlag = false;
        GnrParmPo gnrParm = new GnrParmPo();
        gnrParm.setParmId(DataBaseConstants_USR.ENCRYPTOR_FLAG);
        gnrParm = daoService.selectOne(gnrParm);
        if (null != gnrParm) {
            encryptorFlag = Boolean.valueOf(gnrParm.getParmValue());
        }
        if (StringUtils.isNotBlank(pwd) && encryptorFlag) {
            unionAPI.getShortConnection(idipperCached, getEncryptorLocalBank());// 加密工具
            // 第一次加密码
            log.debug("用户注册 前端传入：" + pwd);
            try {
                if (CommonConstants_GNR.CHNL_ID_WEB.equals(dto.getChnlId())) {
                    // 微通PC端解密
                    pwd = AppPwdUtil.decryptToSM2(aesKey, pwd, dto.getMiType());
                } else {
                    // 微通App端解密
                    pwd = AppPwdUtil.decrypt(idipperCached, pwd, aesKey);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "登录密码威通解密失败,请检查!");
            }

            log.debug("微通解密：" + pwd);

            // pwd=Md5Util.toMD5(pwd);
            try {
                // 第二次加密
                pwd = unionAPI.encryptWT(pwd);
                pwd = unionAPI.encrypt(pwd);
            } catch (Exception e) {
                e.printStackTrace();
                ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "登录密码核心解密失败,请检查!");
            }
            log.debug("用户注册 核心加密后：" + pwd);
        }
        if (map == null || map.size() == 0) {
            reg.setLoginPwd(pwd);
            reg.setUserNickName(dto.getUserNickName());
            reg.setUserId(userId);
            String userName = getUserName();
            reg.setUserName(userName);
            reg.setRegTime(now);
            reg.setRegChnlId(dto.getChnlId());
            reg.setUserCertLevel(DataBaseConstants_USR.USER_CERT_LEVEL_MOBILE);
            reg.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
            reg.setUserLockFlag(DataBaseConstants_USR.USER_LOCK_FLAG_INIT);

            GnrParmPo parmPo = new GnrParmPo();
            parmPo.setParmId(CommonConstants_GNR.DEFAULT_HEAD_PIC);
            parmPo = daoService.selectOne(parmPo);
            if (null != parmPo) {
                reg.setHeadPic(parmPo.getParmValue());// 设置默认头像
            }

            reg.setBlacklistFlag(DataBaseConstants_USR.BLACK_LIST_FLAG_NO);
            reg.setActiveTime(now);
            reg.setActiveChnlId(dto.getChnlId());
            reg.setRegType(dto.getRegType());
            reg.setMobile(dto.getMobile());
            reg.setComEmail(dto.getEmail());
            if (DataBaseConstants_USR.USER_REG_TYPE_MEMBER.equals(regType)) {
                reg.setMerLevel(DataBaseConstants_USR.MER_LEVER_0);
            }
            daoService.insert(reg);
            if (DataBaseConstants_USR.USER_REG_TYPE_MEMBER.equals(regType)) {// 更新企业注册信息表：已生成用户
                UsrComRegRecPo usrComRegRecPo = new UsrComRegRecPo();
                usrComRegRecPo.setGenerateFlag(DataBaseConstants_USR.GENERATE_FLAG_YES);
                UsrComRegRecPo usrComRegInfoWhere = new UsrComRegRecPo();
                usrComRegInfoWhere.setComEmail(dto.getEmail());
                daoService.update(usrComRegRecPo, usrComRegInfoWhere);

            }
            // 添加个人基本信息
            UsrBaseInfoPo base = new UsrBaseInfoPo();
            base.setUserId(userId);
            base.setCertName(dto.getName());
            base.setSex(dto.getSex());
            base.setLastUpdateTime(now);
            base.setAddressReal(dto.getAddress());
            base.setCertType(dto.getCertType());
            base.setCertNo(dto.getCertNo());
            daoService.insert(base);
        }
        dto.setUserId(reg.getUserId());
        return dto;
    }

    /**
     * 绑定授权免密关系
     *
     * @param dto
     * @param userId
     */
    public void bindMer(UserRegDto dto, String userId, Date now) {
        UsrWithoutPwdBookPo w = new UsrWithoutPwdBookPo();
        w.setBindTime(now);
        w.setBindChnlId(dto.getChnlId());
        w.setUserId(userId);
        w.setMerNo(dto.getMerNo());
        w.setBindStat(DataBaseConstants_USR.MERCHANT_BIND_STAT_BIND);
        w.setUnionPlatType(dto.getPlatformType());
        w.setUnionPlatNo(dto.getPlatformUserNo());
        w.setActiveTime(now);
        w.setMobile(dto.getMobile());
        daoService.insert(w);
    }

    /**
     * 判断用户注册唯一性
     *
     * @param dto
     * @return
     */
    public Map<String, String> checkUserReg(UserRegDto dto) {
        Map<String, String> map = new HashMap<String, String>();
        UsrRegInfoPo usr = null;
    /*	if (dto.getMobile() != null) {
            usr = new UsrRegInfoPo();
			usr.setMobile(dto.getMobile());
			usr.setUserStat(DataBaseConstants_USR.USR_STAT_LOGOFF);
			usr = daoService.selectOne(
					UsrRegInfoPo.class.getName().concat(".getUserNotCancel"),
					usr);
			if (usr != null) {
				map.put("result", usr.getMobile());
				map.put("userId", usr.getUserId());
				return map;
			}
		}*/
        if (usr == null && dto.getUserName() != null) {
            usr = new UsrRegInfoPo();
            usr.setUserName(dto.getUserName());
            usr.setUserStat(DataBaseConstants_USR.USR_STAT_LOGOFF);
            usr = daoService.selectOne(UsrRegInfoPo.class.getName().concat(".getUserNotCancel"), usr);
            if (usr != null) {
                map.put("result", usr.getUserName());
                map.put("userId", usr.getUserId());
                return map;
            }
        }
		/*if (usr == null && dto.getEmail() != null) {
			usr = new UsrRegInfoPo();
			usr.setComEmail(dto.getEmail());
			usr.setUserStat(DataBaseConstants_USR.USR_STAT_LOGOFF);
			usr = daoService.selectOne(
					UsrRegInfoPo.class.getName().concat(".getUserNotCancel"),
					usr);
			if (usr != null) {
				map.put("result", usr.getComEmail());
				map.put("userId", usr.getUserId());
				return map;
			}
		}*/
        return map;
    }

    private String getEncryptorLocalBank() {
        String encryptorLocalBank = idipperCached.get("ENCRYPTOR_LOCAL_BANK");
        if (StringUtils.isNotBlank(encryptorLocalBank)) {
            return encryptorLocalBank;
        }
        GnrParmPo parm = new GnrParmPo();
        parm.setParmId("ENCRYPTOR_LOCAL_BANK");
        parm = daoService.selectOne(parm);
        if (parm != null) {
            encryptorLocalBank = parm.getParmValue();
            idipperCached.add("ENCRYPTOR_LOCAL_BANK", encryptorLocalBank);
        }
        return encryptorLocalBank;
    }

    public static void main(String[] args) {
		/*Object parse = JSONObject.parseObject("{'encryptIp':'197.3.11.54','encryptPort':'12002','timeout':'7','appID':'TE','sysID':'TE','connNum':'5','keyName':'upay.05247410-des.zek','encryptkeyName':'upay.05247410-01.sm2','cipherDataLen':'2','vKindex':'01','algorthmID':'SM2','dataFillMode':'08','format':'0'}");
		Object parse = JSONObject.parseObject("{'unionOtpIP':'197.3.11.109','unionOtpPort':'8555','timeout':'7','appID':'upay','sysID':'upay','connNum':'5'}");
		Map<String,Object> a=(Map<String, Object>) parse;
		System.out.println(a);*/
    	UserRegInfoService userService=new UserRegInfoService();
        System.out.println(userService.getUserName());
    }
    public String getUserName(){
    	boolean bool=true;
    	String userName="";
    	while(bool){
    		userName=generateUsername();
    		UsrRegInfoPo reg=new UsrRegInfoPo();
    		reg.setUserName(userName);
    		reg=daoService.selectOne(reg);
    		if(reg==null){
    			bool=false;
    			break;
    		}else{
    			log.error("用户名  "+userName+  "己存在,重新生成");
    		}
    	}
    	log.error("用户名："+userName);
    	return userName;
    }

    public static  String generateUsername() {
//        String han = "\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31\u53bb\u5b50\u5f97\u4e5f\u548c\u90a3\u8981\u4e0b\u770b\u5929\u65f6\u8fc7\u51fa\u5c0f\u4e48\u8d77\u4f60\u90fd\u628a\u597d\u8fd8\u591a\u6ca1\u4e3a\u53c8\u53ef\u5bb6\u5b66\u53ea\u4ee5\u4e3b\u4f1a\u6837\u5e74\u60f3\u751f\u540c\u8001\u4e2d\u5341\u4ece\u81ea\u9762\u524d\u5934\u9053\u5b83\u540e\u7136\u8d70\u5f88\u50cf\u89c1\u4e24\u7528\u5979\u56fd\u52a8\u8fdb\u6210\u56de\u4ec0\u8fb9\u4f5c\u5bf9\u5f00\u800c\u5df1\u4e9b\u73b0\u5c71\u6c11\u5019\u7ecf\u53d1\u5de5\u5411\u4e8b\u547d\u7ed9\u957f\u6c34\u51e0\u4e49\u4e09\u58f0\u4e8e\u9ad8\u624b\u77e5\u7406\u773c\u5fd7\u70b9\u5fc3\u6218\u4e8c\u95ee\u4f46\u8eab\u65b9\u5b9e\u5403\u505a\u53eb\u5f53\u4f4f\u542c\u9769\u6253\u5462\u771f\u5168\u624d\u56db\u5df2\u6240\u654c\u4e4b\u6700\u5149\u4ea7\u60c5\u8def\u5206\u603b\u6761\u767d\u8bdd\u4e1c\u5e2d\u6b21\u4eb2\u5982\u88ab\u82b1\u53e3\u653e\u513f\u5e38\u6c14\u4e94\u7b2c\u4f7f\u5199\u519b\u5427\u6587\u8fd0\u518d\u679c\u600e\u5b9a\u8bb8\u5feb\u660e\u884c\u56e0\u522b\u98de\u5916\u6811\u7269\u6d3b\u90e8\u95e8\u65e0\u5f80\u8239\u671b\u65b0\u5e26\u961f\u5148\u529b\u5b8c\u5374\u7ad9\u4ee3\u5458\u673a\u66f4\u4e5d\u60a8\u6bcf\u98ce\u7ea7\u8ddf\u7b11\u554a\u5b69\u4e07\u5c11\u76f4\u610f\u591c\u6bd4\u9636\u8fde\u8f66\u91cd\u4fbf\u6597\u9a6c\u54ea\u5316\u592a\u6307\u53d8\u793e\u4f3c\u58eb\u8005\u5e72\u77f3\u6ee1\u65e5\u51b3\u767e\u539f\u62ff\u7fa4\u7a76\u5404\u516d\u672c\u601d\u89e3\u7acb\u6cb3\u6751\u516b\u96be\u65e9\u8bba\u5417\u6839\u5171\u8ba9\u76f8\u7814\u4eca\u5176\u4e66\u5750\u63a5\u5e94\u5173\u4fe1\u89c9\u6b65\u53cd\u5904\u8bb0\u5c06\u5343\u627e\u4e89\u9886\u6216\u5e08\u7ed3\u5757\u8dd1\u8c01\u8349\u8d8a\u5b57\u52a0\u811a\u7d27\u7231\u7b49\u4e60\u9635\u6015\u6708\u9752\u534a\u706b\u6cd5\u9898\u5efa\u8d76\u4f4d\u5531\u6d77\u4e03\u5973\u4efb\u4ef6\u611f\u51c6\u5f20\u56e2\u5c4b\u79bb\u8272\u8138\u7247\u79d1\u5012\u775b\u5229\u4e16\u521a\u4e14\u7531\u9001\u5207\u661f\u5bfc\u665a\u8868\u591f\u6574\u8ba4\u54cd\u96ea\u6d41\u672a\u573a\u8be5\u5e76\u5e95\u6df1\u523b\u5e73\u4f1f\u5fd9\u63d0\u786e\u8fd1\u4eae\u8f7b\u8bb2\u519c\u53e4\u9ed1\u544a\u754c\u62c9\u540d\u5440\u571f\u6e05\u9633\u7167\u529e\u53f2\u6539\u5386\u8f6c\u753b\u9020\u5634\u6b64\u6cbb\u5317\u5fc5\u670d\u96e8\u7a7f\u5185\u8bc6\u9a8c\u4f20\u4e1a\u83dc\u722c\u7761\u5174\u5f62\u91cf\u54b1\u89c2\u82e6\u4f53\u4f17\u901a\u51b2\u5408\u7834\u53cb\u5ea6\u672f\u996d\u516c\u65c1\u623f\u6781\u5357\u67aa\u8bfb\u6c99\u5c81\u7ebf\u91ce\u575a\u7a7a\u6536\u7b97\u81f3\u653f\u57ce\u52b3\u843d\u94b1\u7279\u56f4\u5f1f\u80dc\u6559\u70ed\u5c55\u5305\u6b4c\u7c7b\u6e10\u5f3a\u6570\u4e61\u547c\u6027\u97f3\u7b54\u54e5\u9645\u65e7\u795e\u5ea7\u7ae0\u5e2e\u5566\u53d7\u7cfb\u4ee4\u8df3\u975e\u4f55\u725b\u53d6\u5165\u5cb8\u6562\u6389\u5ffd\u79cd\u88c5\u9876\u6025\u6797\u505c\u606f\u53e5\u533a\u8863\u822c\u62a5\u53f6\u538b\u6162\u53d4\u80cc\u7ec6";
        String digital="12346567890";
        String letter="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random ran = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
//            String ch = han.charAt(ran.nextInt(han.length())) + "";
//            builder.append(ch);
        	String   ch = digital.charAt(ran.nextInt(digital.length())) + "";
            builder.append(ch);
            ch = letter.charAt(ran.nextInt(letter.length())) + "";
            builder.append(ch);
        }
        return builder.toString();
    }
}
