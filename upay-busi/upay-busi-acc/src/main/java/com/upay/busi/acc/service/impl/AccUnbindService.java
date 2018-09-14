package com.upay.busi.acc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccUnbindDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccBindBookPo;


/**
 * 绑定账户解绑
 * 
 * @author: liyulong
 * @CreateDate:2015年4月10日
 * 
 */
public class AccUnbindService extends AbstractDipperHandler<AccUnbindDto> {
    @Resource
    private IDaoService daoService;


    @Override
    public AccUnbindDto execute(AccUnbindDto AccUnbindDto, Message msg) throws Exception {
        // 获取绑定账户解绑所需要字段
        String vAcctNo = AccUnbindDto.getvAcctNo();// 虚拟账户账号
        // String bindAcctType = AccUnbindDto.getBindAcctType();// 绑定账户类型
        // String vBindBankCode = AccUnbindDto.getBindBankCode();// 绑定卡行号
        String vBindAcctNo = AccUnbindDto.getvBindAcctNo();//
        // 绑定卡账户账号,待解决问题！！！
        // 判断所需字段是否为空
        if (StringUtils.isBlank(vAcctNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "虚拟账户账号");
        }

        // if (StringUtils.isBlank(bindAcctType)) {
        // ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "绑定账户类型");
        // }
        // 根据用户号及账号判断此用户状态
        AccBindBookPo accBindBookPo = new AccBindBookPo();
        accBindBookPo.setVacctNo(vAcctNo);
        // accBindBookPo.setBindAcctType(bindAcctType);
        // accBindBookPo.setVbindBankCode(vBindBankCode);
        accBindBookPo.setVbindAcctNo(vBindAcctNo);
        accBindBookPo.setBindStat(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_BIND);
        // 创建绑卡用户的list集合
        List<AccBindBookPo> list = new ArrayList<AccBindBookPo>();
        list = daoService.selectList(accBindBookPo);
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                // 当上送绑定卡行号，绑定卡账户账号时，只会查询出一条list并解绑一条；
                // 当不上送绑定卡行号，绑定卡账户账号时，查询出的多条list并全部解绑；
                AccBindBookPo accBindBookPoList = list.get(i);
                accBindBookPoList.setBindStat(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_UNBIND);// 绑定状态,解绑-3
                accBindBookPoList.setUnbindTime(new Date());
                accBindBookPoList.setUnbindReasonFlag(DataBaseConstans_ACC.UNBIND_ACCT_CHANG);

                AccUnbindDto.setOldGateWay_zjpay_sysSeq(accBindBookPoList.getRemark1());
                AccUnbindDto.setVbindBankFlag(accBindBookPoList.getVbindBankFlag());

                AccBindBookPo accBindBookWherePo = new AccBindBookPo();
                accBindBookWherePo.setVacctNo(accBindBookPoList.getVacctNo());
                accBindBookWherePo.setVbindAcctNo(accBindBookPoList.getVbindAcctNo());

                daoService.update(accBindBookPoList, accBindBookWherePo);
            }
        } else {
            // 不存在该已经绑定账户
            ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "该绑定账户");
        }

        // // 更新电子账户信息状态
        // AccVbookPo accVbookPo = new AccVbookPo();
        // accVbookPo.setVacctNo(vAcctNo);
        // accVbookPo = daoService.selectOne(accVbookPo);
        // if (accVbookPo == null) {
        // ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "该虚拟账户");
        // }
        // // 将电子账户信息表中的账户状态更新为待激活
        // accVbookPo.setVacctStat(DataBaseConstans_ACC.ACCT_STAT_NOACTIVE);
        // daoService.update(accVbookPo);

        return AccUnbindDto;
    }
}
