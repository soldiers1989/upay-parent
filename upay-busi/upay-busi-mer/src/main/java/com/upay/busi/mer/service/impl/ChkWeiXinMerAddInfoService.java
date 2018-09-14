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
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;

/**新增微信特约商户，校验商户信息
 * @author zhanggr
 *
 */
public class ChkWeiXinMerAddInfoService extends AbstractDipperHandler<ChkWeiXinMerAddInfoDto> {


    @Resource
    private IDaoService daoService;

    
    
    @Override
    public ChkWeiXinMerAddInfoDto execute(ChkWeiXinMerAddInfoDto chkWeiXinMerAddInfoDto, Message msg) throws Exception {
        String merNo = chkWeiXinMerAddInfoDto.getMerNo();
        String merchantName = chkWeiXinMerAddInfoDto.getMerchantName();
        String merchantShortname = chkWeiXinMerAddInfoDto.getMerchantShortname();
        String servicePhone = chkWeiXinMerAddInfoDto.getServicePhone();
        String contact = chkWeiXinMerAddInfoDto.getContact();//可选
        String contactPhone = chkWeiXinMerAddInfoDto.getContactPhone();//可选
        String contactEmail = chkWeiXinMerAddInfoDto.getContactEmail();//可选
        String business = chkWeiXinMerAddInfoDto.getBusiness();
        String merchantRemark = chkWeiXinMerAddInfoDto.getMerchantRemark();
        
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
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
            if(StringUtils.isNotBlank(subMerId)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0105, merNo);
            }
        }
        if(StringUtils.isBlank(merchantName)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户名称");
        }
        if(StringUtils.isBlank(merchantShortname)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户备注");
        }
        if(StringUtils.isBlank(servicePhone)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "客服电话");
        }
        if(StringUtils.isBlank(business)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "经营类目");
        }
        if(StringUtils.isBlank(merchantRemark)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户备注");
        }
        
        return chkWeiXinMerAddInfoDto;
    }

}
