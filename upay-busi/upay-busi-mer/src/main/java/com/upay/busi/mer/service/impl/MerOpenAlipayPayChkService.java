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
import com.upay.busi.mer.service.dto.ChkWeiXinMerAddInfoDto;
import com.upay.busi.mer.service.dto.MerOpenAlipayPayChkDto;
import com.upay.busi.mer.service.dto.MerOpenWechatPayChkDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**新增支付宝商户，校验商户信息
 * 
 *
 */
public class MerOpenAlipayPayChkService extends AbstractDipperHandler<MerOpenAlipayPayChkDto> {


    @Resource
    private IDaoService daoService;

    
    
    @Override
    public MerOpenAlipayPayChkDto execute(MerOpenAlipayPayChkDto dto, Message msg) throws Exception {
        String merNo = dto.getMerNo();
        String categoryId = dto.getCategoryId();//经营类目
        
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
        }
        if(StringUtils.isBlank(categoryId)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "经营类目");
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
                dto.setServicePhone(merBaseInfoPo.getContactMobile());
                dto.setMerName(merBaseInfoPo.getMerName());
                dto.setAliasName(merBaseInfoPo.getMerName());
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0010, merNo);
            }
            String subMerId = merBaseInfoPo.getAlipayMerchantId();
            if(StringUtils.isNotBlank(subMerId)){
            	ExInfo.throwDipperEx(AppCodeDict.BISPAY0120, merNo);
            }
            dto.setExternalId(merNo);
        }
        
        if(StringUtils.isBlank(dto.getMerName())){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户名称");
        }
        if(StringUtils.isBlank(dto.getAliasName())){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户缩写名");
        }
        
        if(StringUtils.isBlank(dto.getAlipaySource())){
        	PayRouteInfoPo payRouteInfo = new PayRouteInfoPo();
            payRouteInfo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY);
            payRouteInfo = daoService.selectOne(payRouteInfo);
            dto.setAlipaySource(payRouteInfo.getOrgNo());
        }
        
        return dto;
    }

}
