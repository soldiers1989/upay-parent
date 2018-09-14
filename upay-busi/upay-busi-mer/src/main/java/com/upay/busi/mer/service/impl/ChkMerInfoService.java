/**
 * 
 */
package com.upay.busi.mer.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.ChkMerInfoDto;
import com.upay.busi.mer.service.dto.ChkWeiXinMerAddInfoDto;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;

/**校验商户信息
 * @author zhanggr
 *
 */
public class ChkMerInfoService extends AbstractDipperHandler<ChkMerInfoDto> {


    @Resource
    private IDaoService daoService;

    
    
    @Override
    public ChkMerInfoDto execute(ChkMerInfoDto dto, Message msg) throws Exception {
        String merNo = dto.getMerNo();
        String authCode = dto.getAuthCode();
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
        }
        if (StringUtils.isBlank(authCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "授权码");
        }
        MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
        merBaseInfoPo.setMerNo(merNo);
        merBaseInfoPo = daoService.selectOne(merBaseInfoPo);
        if (merBaseInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "商户:" + merNo);
        } else {
            String merState = merBaseInfoPo.getMerState();
            if (DateBaseConstants_MER.MER_STAT_NORMAL.equals(merState)) {
                String payopenflag = merBaseInfoPo.getPayOpenFlag();
                if (StringUtils.isNotBlank(payopenflag) && DateBaseConstants_MER.MER_PAYOPENFLAG_CLOSE.equals(payopenflag)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISMER0011, merNo);
                }
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0010, merNo);
            }
            String subMerId = merBaseInfoPo.getSubMchId();
            if(StringUtils.isBlank(subMerId)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0103, merNo);
            }
            dto.setSubMchId(subMerId);
        }
        return dto;
    }

}
