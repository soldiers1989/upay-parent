package com.upay.busi.acc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.UniqueBindAccCheckDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccBindBookPo;
import com.upay.dao.po.acc.AccVbookPo;


/**
 * 绑定账户唯一性检查
 * 
 * @author liubing
 * 
 */
public class UniqueBindAccCheckService extends AbstractDipperHandler<UniqueBindAccCheckDto> {
    @Resource
    private IDaoService daoService;


    @Override
    public UniqueBindAccCheckDto execute(UniqueBindAccCheckDto uniqueBindAccCheckDto, Message message)
            throws Exception {
        // 获取输入项
        String userId = uniqueBindAccCheckDto.getUserId();
        String eBindBankCode = uniqueBindAccCheckDto.geteBindBankCode();
        String eBindAcctNo = uniqueBindAccCheckDto.geteBindAcctNo();
        if (StringUtils.isBlank(eBindAcctNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "绑定卡卡号");
        }
        if (StringUtils.isBlank(eBindBankCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "绑定卡行号");
        }

        // // 微信加判断用户是否已经绑定卡了，已经绑定卡就不能再进行再次绑卡
        // if
        // (uniqueBindAccCheckDto.getChnlId().equals(DataBaseConstants.CHNL_ID_WEIXIN))
        // {
        // AccEbookPo accEopenBookPo = new AccEbookPo();
        // accEopenBookPo.setUserId(userId);
        // AccEbookPo accEBPo = daoService.selectOne(accEopenBookPo);
        // if (null != accEBPo && null != accEBPo.getEcardNo() &&
        // accEBPo.getEcardNo().length() > 0) {
        // ExInfo.throwDipperEx(AppCodeDict.BISACC0041);
        // }
        // }
        AccVbookPo vbookPo=new AccVbookPo();
        vbookPo.setUserId(userId);
        AccVbookPo accVbookPo = daoService.selectOne(vbookPo);
        if(null==accVbookPo){
        	return uniqueBindAccCheckDto;
        }
        
        AccBindBookPo accBindBookPo = new AccBindBookPo();
        accBindBookPo.setVbindBankCode(eBindBankCode);
        accBindBookPo.setVbindAcctNo(eBindAcctNo);
        accBindBookPo.setVacctNo(accVbookPo.getVacctNo());
        
        // 用户有可能出现反复绑定解绑的情况
        List<AccBindBookPo> list = daoService.selectList(accBindBookPo);
        // 判断卡是否已被绑定
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                AccBindBookPo accBindBookPoList = list.get(i);
                String bindStat = accBindBookPoList.getBindStat();

                if (bindStat.equals(DataBaseConstans_ACC.BIND_STAT_BINDED_TOACTIVATED)) {
                    // 如果绑定卡状态为待激活状态,从电子账户信息表检查用户登录名是否存在
                    if (accVbookPo != null) {
                        String eAcctStat = accVbookPo.getVacctStat();

                        // 判断用户状态
                        if (eAcctStat.equals(DataBaseConstans_ACC.ACCT_STAT_NOACTIVE)) {
                            return uniqueBindAccCheckDto;
                        } else if (eAcctStat.equals(DataBaseConstans_ACC.ACCT_STAT_NORMAL)) {
                            ExInfo.throwDipperEx(AppCodeDict.BISACC0013, "正常");
                        } else if (eAcctStat.equals(DataBaseConstans_ACC.ACCT_STAT_SLEEP)) {
                            ExInfo.throwDipperEx(AppCodeDict.BISACC0013, "睡眠");
                        } else if (eAcctStat.equals(DataBaseConstans_ACC.ACCT_STAT_CANCEL)) {
                            ExInfo.throwDipperEx(AppCodeDict.BISACC0013, "注销");
                        }
                    } else {
                        // 该绑定卡处于待激活状态，请重新激活或进行变更绑定卡
                        ExInfo.throwDipperEx(AppCodeDict.BISACC0015);
                    }
                }else if (bindStat.equals(DataBaseConstans_ACC.BIND_STAT_BINDED_ACTIVATED)) {
                    // 如果绑定卡状态为已绑定状态
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0014, eBindAcctNo);
                }
            }
        }
        return uniqueBindAccCheckDto;
    }
}
