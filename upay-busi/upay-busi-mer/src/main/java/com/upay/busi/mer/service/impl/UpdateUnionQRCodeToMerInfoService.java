package com.upay.busi.mer.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.UpdateUnionQRCodeToMerInfoDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.mer.MerBaseInfoPo;
/**商户申请线下主扫银联标码，更新商户线下二维码
 * @author hry
 *
 */
public class UpdateUnionQRCodeToMerInfoService extends AbstractDipperHandler<UpdateUnionQRCodeToMerInfoDto> {

    @Resource
    IDaoService daoService;
    
    @Override
    public UpdateUnionQRCodeToMerInfoDto execute(UpdateUnionQRCodeToMerInfoDto updateUnionQRCodeToMerInfoDto, Message msg)
            throws Exception {
                String merNo = updateUnionQRCodeToMerInfoDto.getMerNo();
                String qrCode = updateUnionQRCodeToMerInfoDto.getQrCode();
                String limitCount = updateUnionQRCodeToMerInfoDto.getLimitCount();
                String orderTime = updateUnionQRCodeToMerInfoDto.getOrderTime();
                String qrValidTime = updateUnionQRCodeToMerInfoDto.getQrValidTime();
                
                Date tmpDate=DateUtil.addSecond(DateUtil.parse(orderTime, "yyyyMMddHHmmss"),Integer.valueOf(qrValidTime));
                
                if(StringUtils.isBlank(merNo)){
                    ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户号");
                }
                if(StringUtils.isBlank(qrCode)){
                    ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户二维码");
                }
                MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
                MerBaseInfoPo merBaseInfo = new MerBaseInfoPo();
                merBaseInfoPo.setMerNo(merNo);
                merBaseInfo.setQrCode(qrCode);
                merBaseInfo.setQrLimitCount(limitCount);
                merBaseInfo.setQrValidTime(tmpDate);
                merBaseInfo.setQrOrderNo(updateUnionQRCodeToMerInfoDto.getOrderNo());
                daoService.update(merBaseInfo, merBaseInfoPo);
        return updateUnionQRCodeToMerInfoDto;
    }
}
