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
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.mer.service.dto.GenerateQrCodeUrlDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;


/**
 * 生成聚合码链接
 * 
 * @author 
 * 
 */
public class GenerateQrCodeUrlService extends AbstractDipperHandler<GenerateQrCodeUrlDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public GenerateQrCodeUrlDto execute(GenerateQrCodeUrlDto dto, Message message)
            throws Exception {
        String merNo = dto.getMerNo();
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
        }
        MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
        merBaseInfoPo.setMerNo(merNo);
        merBaseInfoPo = daoService.selectOne(merBaseInfoPo);
        if (merBaseInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "商户:" + merNo);
        }
        GnrParmPo gnrParmPo = new GnrParmPo();
        gnrParmPo.setParmId("ALI_WE_QRURL");
        gnrParmPo = daoService.selectOne(gnrParmPo);
        if(gnrParmPo == null){
        	 ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "微信、支付宝聚合码链接配置");
        }
        String aliWeQrurl = gnrParmPo.getParmValue();
		if(merBaseInfoPo.getParentMerNo()!=null){
			aliWeQrurl = aliWeQrurl+merBaseInfoPo.getParentMerNo()+"&secMerNo="+merNo;
		}else{
			aliWeQrurl = aliWeQrurl+merNo;
		}
        dto.setMerName(merBaseInfoPo.getMerName());
        dto.setAlipayWeChatUrl(aliWeQrurl);
        dto.setUnionQrUrl(merBaseInfoPo.getQrCode());
        
        return dto;
    }
}
