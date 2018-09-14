/**
 * 
 */
package com.upay.busi.acc.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.UpdateAccBindingDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccBindBookPo;

/**
 * @author shang
 * 2016年12月26日
 */
public class UpdateAccBindingService extends AbstractDipperHandler<UpdateAccBindingDto> {

    @Resource
    private IDaoService daoService;
    
    @Override
    public UpdateAccBindingDto execute(UpdateAccBindingDto dto, Message message) throws Exception {
        
        if(StringUtils.isBlank(dto.getAccNo())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "账号");
        }
        if(StringUtils.isBlank(dto.getBankAccNo())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "卡号");
        }
        if(StringUtils.isBlank(dto.getTxSNBinding())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "绑定流水号");
        }
        if(dto.isTxSNBindingNew()){
            AccBindBookPo where=new AccBindBookPo();
            where.setVacctNo(dto.getAccNo());
            where.setVbindAcctNo(dto.getBankAccNo());
            where.setBindStat(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_BIND);
            AccBindBookPo param=new AccBindBookPo();
            param.setRemark1(dto.getTxSNBinding());
            int num=daoService.update(param, where);
            if(num!=1){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "更新绑定流水");
            }
        }
        return dto;
    }

}
