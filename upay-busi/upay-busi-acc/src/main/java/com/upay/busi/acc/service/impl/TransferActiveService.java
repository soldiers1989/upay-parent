package com.upay.busi.acc.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.TransferActiveDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccBindBookPo;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.acc.AccVopenBookPo;


/**
 * 激活电子账户（打款验证）
 * 
 * @author: liubing
 * @CreateDate:2015年4月13日
 * 
 */
public class TransferActiveService extends AbstractDipperHandler<TransferActiveDto> {
    @Resource
    private IDaoService daoService;
    private final static Logger log = LoggerFactory.getLogger(TransferActiveService.class);


    @Override
    public TransferActiveDto execute(TransferActiveDto transferActiveDto, Message msg) throws Exception {

        // 获取激活电子账户（打款验证）需要的值
        String userId = transferActiveDto.getUserId();// 获取userId
        String eBindBankCode = transferActiveDto.geteBindBankCode();// 待绑定卡行号（相当于机构）
        String eBindAcctNo = transferActiveDto.geteBindAcctNo();// 待绑定卡号
        String transCode = transferActiveDto.getTransCode();// 绑定账户行名
        Date sysTime = transferActiveDto.getSysTime();
        String cnapsBankNo = transferActiveDto.getCnapsBankNo();// 绑定卡的行号
        String cardBinType = transferActiveDto.getCardBinType();//卡类型
        
        log.info("交易码============" + transCode + "====激活电子账户==" + eBindBankCode + "==" + eBindAcctNo + "==");
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "userId");
        }
        if (StringUtils.isBlank(eBindBankCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "eBindBankCode");
        }
        if (StringUtils.isBlank(eBindAcctNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "eBindAcctNo");
        }
        if (StringUtils.isBlank(transCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "transCode");
        }
        
        if (StringUtils.isBlank(cardBinType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "绑定卡类型");
        }

        // 电子账户信息表中的账户状态更新为正常
        AccVbookPo accEbookPo = new AccVbookPo();
        accEbookPo.setUserId(userId);
        // accEbookPo.setVacctStat(DataBaseConstans_ACC.ACCT_STAT_NOACTIVE);
        accEbookPo = daoService.selectOne(accEbookPo);
        if (accEbookPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "待激活电子账户");
        }
        String vAcctNo = accEbookPo.getVacctNo();

        // if (transCode.equals(DataBaseConstans_ACC.TRANS_CODE_OPEN_CARD)) {
        // openAcctFlag = true;
        // } else if
        // (transCode.equals(DataBaseConstans_ACC.TRANS_CODE_CHANG_CARD) ||
        // transCode.equals(DataBaseConstans_ACC.TRANS_CODE_AMT_CHANG_CARD) ||
        // transCode.equals(DataBaseConstans_ACC.TRANS_CODE_PMT_RECHARG)) {
        // AccVopenBookPo accEopenBookPo = new AccVopenBookPo();
        // accEopenBookPo.setVacctNo(accEbookPo.getVacctNo());
        // accEopenBookPo = daoService.selectOne(accEopenBookPo);
        // if (accEopenBookPo == null) {
        // openAcctFlag = true;
        // }
        // }

        // 将绑定卡登记簿状态更新为已绑定
        AccBindBookPo accBindBookPo = new AccBindBookPo();
        accBindBookPo.setVacctNo(vAcctNo);
        accBindBookPo.setVbindAcctNo(eBindAcctNo);// 绑定卡号
        accBindBookPo.setVbindBankCode(eBindBankCode);
        accBindBookPo.setVbindOpenCode(cnapsBankNo);
        accBindBookPo.setBindStat(DataBaseConstans_ACC.BIND_STAT_BINDED_TOACTIVATED);
        accBindBookPo = daoService.selectOne(accBindBookPo);
        if (accBindBookPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "待激活电子账户绑定账户记录");
        } else {
            if (!DataBaseConstans_ACC.BIND_STAT_BINDED_ACTIVATED.equals(accBindBookPo.getBindStat())) {
                accBindBookPo.setActiveTime(sysTime);
                accBindBookPo.setBindStat(DataBaseConstans_ACC.BIND_STAT_BINDED_ACTIVATED);
                AccBindBookPo whereAccBindBookPo = new AccBindBookPo();
                whereAccBindBookPo.setId(accBindBookPo.getId());
                daoService.update(accBindBookPo,whereAccBindBookPo);
            }
        }

        boolean openAcctFlag = false;

        // 3.如果电子账户的状态为待激活，就更改电子账户的状态为正常
        if (accEbookPo.getVacctStat().equals(DataBaseConstans_ACC.ACCT_STAT_NOACTIVE)) {
            // ExInfo.throwDipperEx(AppCodeDict.BISACC0020, "待激活电子账户");
            AccVbookPo whAccEbookPo = new AccVbookPo();
            whAccEbookPo.setUserId(userId);
            AccVbookPo upAccEbookPo = new AccVbookPo();
            upAccEbookPo.setVacctStat(DataBaseConstans_ACC.ACCT_STAT_NORMAL);
            upAccEbookPo.setLastChgTime(sysTime);
            daoService.update(upAccEbookPo, whAccEbookPo);
            openAcctFlag = true;
        }

        log.info("登记开户登记簿===================true:登记 fasle:不登记=========================" + openAcctFlag);
        // 登记开户登记簿
        if (openAcctFlag) {
            String eBindFlag = transferActiveDto.geteBindFlag();// 绑卡方式----------第三方渠道传值1
            String thirdAuthChnl = transferActiveDto.getThirdAuthChnl();// 第三方鉴权渠道
            BigDecimal transAmt = transferActiveDto.getTransAmt();// 打款验证金额
            String eOpenFlag = transferActiveDto.geteOpenFlag();// 开户原因
            String eBindBankFlag = transferActiveDto.geteBindBankFlag();// 本行他行标志
            // if (StringUtils.isBlank(eOpenFlag)) {
            // ExInfo.throwDipperEx(AppCodeDict.BISACC0012, "开户标志");
            // }
            if (StringUtils.isBlank(eBindFlag)) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0012, "绑卡方式");
            }
            if (eBindFlag.equals(DataBaseConstans_ACC.BIND_FLAG_CHNL)) {
                if (StringUtils.isBlank(thirdAuthChnl)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0012, "第三方鉴权渠道");
                }
            }

            AccVopenBookPo accEopenBookPo = new AccVopenBookPo();
            String eAccBrancode = (String) DipperParm.getParmByKey("RCB_BRANCODE");
            accEopenBookPo.setVacctNo(accEbookPo.getVacctNo());
            accEopenBookPo.setOpenTime(sysTime);// 开户时间
            accEopenBookPo.setOpenChnl(transferActiveDto.getChnlId());// 开户渠道
            accEopenBookPo.setOpenOrg(eAccBrancode);// 开户机构
            accEopenBookPo.setVbindOpenCode(transferActiveDto.geteBindBankCode());
            // accEopenBookPo.setVopenFlag(eOpenFlag);// 开户原因（方式）
            accEopenBookPo.setVbindFlag(eBindFlag);// 绑卡方式
            if (eBindFlag.equals(DataBaseConstans_ACC.BIND_FLAG_CHNL)) {
                // 第三方鉴权渠道
                accEopenBookPo.setThirdAuthChnl(thirdAuthChnl);
            } else if (eBindFlag.equals(DataBaseConstans_ACC.BIND_FLAG_VERIFYAMT)) {
                // 打款验证金额
                accEopenBookPo.setOtherVerifyAmt(transAmt);// 打款验证金额--------------------第三方渠道传值
            }
            if (eBindBankFlag.equals(DataBaseConstans_ACC.THIRD_AUTH_CHNL_CORE)) {
                // 银行标志:1：核心系统 ，即为本行卡
                accEopenBookPo.setVbindBankFlag(DataBaseConstans_ACC.BIND_BANK_FLAG_THIS);// 本行绑定账户类型
            } else {
                accEopenBookPo.setVbindBankFlag(DataBaseConstans_ACC.BIND_BANK_FLAG_OTHER);// 他行
            }
            accEopenBookPo.setVbindBankCode(accBindBookPo.getVbindBankCode());// 绑定卡行号
            accEopenBookPo.setVbindBankName(accBindBookPo.getVbindBankName());// 电子账户绑定账户行名--------------------第三方渠道传值
            accEopenBookPo.setVbindOpenCode(cnapsBankNo);// 电子账户绑定账户开户行名
            accEopenBookPo.setVbindAcctNo(eBindAcctNo);// 绑定卡行号
            
            
            accEopenBookPo.setAcctKind(cardBinType);
            
            daoService.insert(accEopenBookPo);
            log.info("插入开户登记簿数据============================================成功！！！！！");
        }
        return transferActiveDto;
    }
}
