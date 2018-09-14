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
import com.upay.busi.mer.service.dto.MerOpenWechatPayChkDto;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;

/**新增微信特约商户，校验商户信息
 * @author zhanggr
 *
 */
public class MerOpenWechatPayChkService extends AbstractDipperHandler<MerOpenWechatPayChkDto> {


    @Resource
    private IDaoService daoService;

    
    
    @Override
    public MerOpenWechatPayChkDto execute(MerOpenWechatPayChkDto chkWeiXinMerAddInfoDto, Message msg) throws Exception {
        String merNo = chkWeiXinMerAddInfoDto.getMerNo();
//        String servicePhone = chkWeiXinMerAddInfoDto.getServicePhone();
        String contact = chkWeiXinMerAddInfoDto.getContact();//可选
        String contactPhone = chkWeiXinMerAddInfoDto.getContactPhone();//可选
        String contactEmail = chkWeiXinMerAddInfoDto.getContactEmail();//可选
        String business = chkWeiXinMerAddInfoDto.getBusiness();
        String merchantRemark = chkWeiXinMerAddInfoDto.getMerchantRemark();
        String channelId = chkWeiXinMerAddInfoDto.getChannelId();
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
        }
        if (StringUtils.isBlank(channelId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "微信渠道号");
        }
//        if(StringUtils.isBlank(servicePhone)){
//            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "客服电话");
//        }
        if(StringUtils.isBlank(business)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "经营类目");
        }
        if(StringUtils.isBlank(merchantRemark)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户备注");
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
                chkWeiXinMerAddInfoDto.setServicePhone(merBaseInfoPo.getContactMobile());
                chkWeiXinMerAddInfoDto.setMerchantName(merBaseInfoPo.getMerName());
                chkWeiXinMerAddInfoDto.setMerchantShortname(merBaseInfoPo.getAliasName());
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0010, merNo);
            }
            String subMerId = merBaseInfoPo.getSubMchId();
            if(StringUtils.isNotBlank(subMerId)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0105, merNo);
            }
        }
        if(StringUtils.isBlank(chkWeiXinMerAddInfoDto.getMerchantName())){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户名称");
        }
        if(StringUtils.isBlank(chkWeiXinMerAddInfoDto.getMerchantShortname())){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户缩写名");
        }
        return chkWeiXinMerAddInfoDto;
    }

}
