/**
 * 
 */
package com.upay.busi.acc.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccVBookOpenDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.acc.AccVbookPo;


/**
 * 虚拟银行开户
 * 
 */
public class AccVBookOpenService extends AbstractDipperHandler<AccVBookOpenDto> {
    @Resource
    private IDaoService daoService;
    @Resource
    private ISequenceService sequenceService;


    @Override
    public AccVBookOpenDto execute(AccVBookOpenDto accOpenDto, Message msg) throws Exception {
        String certName = accOpenDto.getCertName();
        // 获取电子账户开户需要的值
        String userId = accOpenDto.getUserId();// 用户号
        String eAcctCertLevel = accOpenDto.geteAcctCertLevel();// 用户实名认证等级
        // 绑定账户类型，软需中写的没有用到
        String eBindBankName = accOpenDto.geteBindBankName();// 绑定账户行名----------第三方渠道传值
        String eOpenFlag = accOpenDto.geteOpenFlag();// 开户原因
        String eBindFlag = accOpenDto.geteBindFlag();// 绑卡方式----------第三方渠道传值1
        String thirdAuthChnl = accOpenDto.getThirdAuthChnl();// 第三方鉴权渠道

        // 对应的param-value值，电子账户归属机构
        String eAccBrancode = (String) DipperParm.getParmByKey("RCB_BRANCODE");

        // 判断所需字段是否为空
        if (StringUtils.isBlank(eAcctCertLevel)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "用户实名认证等级");
        }
        if (StringUtils.isBlank(eBindBankName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "电子账户绑定账户行名");
        }
        if (StringUtils.isBlank(eOpenFlag)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "开户原因");
        }
        if (eOpenFlag.equals(DataBaseConstans_ACC.OPEN_FLAG_NORMAL)) {
            if (StringUtils.isBlank(eBindFlag)) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "绑卡方式");
            }
        }
        if (eBindFlag.equals(DataBaseConstans_ACC.BIND_FLAG_CHNL)) {
            if (StringUtils.isBlank(thirdAuthChnl)) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "第三方鉴权渠道");
            }
        }

        boolean daoFlag = false;// 数据库操作标志

        // 从电子账户信息表检查用户登录名是否存在
        AccVbookPo accEbookPo = new AccVbookPo();
        accEbookPo.setUserId(userId);
        accEbookPo = daoService.selectOne(accEbookPo);
        if (accEbookPo != null) {
            // 输入电子账户账号
            accOpenDto.setvAcctNo(accEbookPo.getVacctNo());
            accOpenDto.setCcy(DataBaseConstans_ACC.TRANS_CCY);
            String eAcctStat = accEbookPo.getVacctStat();
            // 如果电子账户的状态为正常或都是待激活，则说明已经开通电子账户，不需要再开户
            if (eAcctStat.equals(DataBaseConstans_ACC.ACCT_STAT_NORMAL)
                    || eAcctStat.equals(DataBaseConstans_ACC.ACCT_STAT_NOACTIVE)) {
                return accOpenDto;
            } else if (eAcctStat.equals(DataBaseConstans_ACC.ACCT_STAT_CANCEL)) {
                // 如果电子账户状态为销户状态，则将原电子账户信息账户状态更改为待激活（不生成电子账号）
                accEbookPo.setVacctStat(DataBaseConstans_ACC.ACCT_STAT_NOACTIVE);
                accEbookPo.setLastChgTime(accOpenDto.getSysTime());
                // 输入eCardNo电子账户账号
                daoFlag = true;
                // } else if
                // (eAcctStat.equals(DataBaseConstans_ACC.ACCT_STAT_NORMAL) ||
                // eAcctStat.equals(DataBaseConstans_ACC.ACCT_STAT_SLEEP)) {
                // ExInfo.throwDipperEx(AppCodeDict.BISACC0016,
                // "处于正常或者睡眠状态，不能重复开户");
                // }
            } else if (eAcctStat.equals(DataBaseConstans_ACC.ACCT_STAT_SLEEP)) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "处于睡眠状态，不能重复开户");
            }
        }

        // 账户类别
        String vAcc = (String) DipperParm.getParmByKey(DataBaseConstans_ACC.ACC_V_BOOK_TYPE);

        // 账户类别(3)+机构(8）+币种(2位)+核算代码（8）+顺序号（10）+校验位（1）
        String eAcctNoSeq = sequenceService.generateeAcctNoSeq();
        StringBuffer sb = new StringBuffer();
        sb.append(vAcc).append(eAccBrancode).append("01").append(eAcctNoSeq);
        String vilidatorAcctNo = sequenceService.vilidatorAcctNo(sb.toString());
        sb.append(vilidatorAcctNo);

        // 创建AccEbookPo实例
        if (!daoFlag) {
            accEbookPo = new AccVbookPo();
            accEbookPo.setVacctNo(sb.toString());// 对应的核心活期存款账号
            accEbookPo.setUserId(userId);
            accOpenDto.setvAcctNo(sb.toString());
            // accEbookPo.setEcardNo(eCardNo);
        }
        accEbookPo.setVacctStat(DataBaseConstans_ACC.ACCT_STAT_NOACTIVE);
        accEbookPo.setStopStat(DataBaseConstans_ACC.STP_TYPE_NOMAL);// 0正常,1不收不付,2只收不付,3部分止付
        accEbookPo.setFrzStat(DataBaseConstans_ACC.FRZ_TYPE_FROZEN_NORMAL);//
        accEbookPo.setSetFlag(DataBaseConstans_ACC.ACC_V_BOOK_SET_FLAG_DEFAULT);
        accEbookPo.setAcctName(certName);
        // accEbookPo.setAcctOtherName("");
        accEbookPo.setCcy(DataBaseConstans_ACC.TRANS_CCY);
        accEbookPo.setAcctBal(BigDecimal.ZERO);
        accEbookPo.setFrzBal(BigDecimal.ZERO);
        accEbookPo.setLastBal(BigDecimal.ZERO);
        accEbookPo.setCutBal(BigDecimal.ZERO);
        accEbookPo.setExtTime(null);
        accEbookPo.setLastChgTime(accOpenDto.getSysDate());// 最新修改日期
        accOpenDto.setCcy(DataBaseConstans_ACC.TRANS_CCY);

        // 输入eCardNo电子账户账号
        accOpenDto.seteCardNo(accEbookPo.getVacctNo());
        // 当数据库操作标志为真时，更新到电子账户信息表；否则插入数据库
        if (daoFlag) {
        	AccVbookPo whereAccBookPo = new AccVbookPo();
        	whereAccBookPo.setId(accEbookPo.getId());
            daoService.update(accEbookPo,whereAccBookPo);
        } else {
            daoService.insert(accEbookPo);
        }
        return accOpenDto;
    }
}
