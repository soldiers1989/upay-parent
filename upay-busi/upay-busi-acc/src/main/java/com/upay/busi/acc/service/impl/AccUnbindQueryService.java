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
import com.upay.busi.acc.service.dto.AccUnbindQueryDto;
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
public class AccUnbindQueryService extends AbstractDipperHandler<AccUnbindQueryDto> {
    @Resource
    private IDaoService daoService;


    @Override
    public AccUnbindQueryDto execute(AccUnbindQueryDto dto, Message msg) throws Exception {
        // 获取绑定账户解绑所需要字段
        String vAcctNo = dto.getvAcctNo();// 虚拟账户账号
        String vBindAcctNo = dto.getvBindAcctNo();//
        // 绑定卡账户账号,待解决问题！！！
        // 判断所需字段是否为空
        if (StringUtils.isBlank(vAcctNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "虚拟账户账号");
        }
        // 根据用户号及账号判断此用户状态
        AccBindBookPo accBindBookPo = new AccBindBookPo();
        accBindBookPo.setVacctNo(vAcctNo);
        accBindBookPo.setVbindAcctNo(vBindAcctNo);
        accBindBookPo.setBindStat(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_BIND);
        // 创建绑卡用户的list集合
        List<AccBindBookPo> list = new ArrayList<AccBindBookPo>();
        list = daoService.selectList(accBindBookPo);
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                AccBindBookPo accBindBookPoList = list.get(i);
                dto.setOldGateWay_zjpay_sysSeq(accBindBookPoList.getRemark1());
                dto.setVbindBankFlag(accBindBookPoList.getVbindBankFlag());
                dto.setBindAcctType(accBindBookPoList.getBindAcctType());
            }
        } else {
            // 不存在该已经绑定账户
            ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "该绑定账户");
        }
        return dto;
    }
}
