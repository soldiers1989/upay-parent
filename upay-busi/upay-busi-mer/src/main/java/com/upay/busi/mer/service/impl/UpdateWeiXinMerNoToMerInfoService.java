package com.upay.busi.mer.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.UpdateWeiXinMerNoToMerInfoDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerApplyBookPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
/**微信支付开通之后更新微信特约商户识别码到商户信息表和商户审批表
 * @author zhanggr
 *
 */
public class UpdateWeiXinMerNoToMerInfoService extends AbstractDipperHandler<UpdateWeiXinMerNoToMerInfoDto> {

    @Resource
    IDaoService daoService;
    
    @Override
    public UpdateWeiXinMerNoToMerInfoDto execute(UpdateWeiXinMerNoToMerInfoDto updateWeiXinMerNoToMerInfoDto, Message msg)
            throws Exception {
                String merNo = updateWeiXinMerNoToMerInfoDto.getMerNo();
                String subMchId = updateWeiXinMerNoToMerInfoDto.getSubMchId();
                if(StringUtils.isBlank(merNo)){
                    ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户号");
                }
                if(StringUtils.isBlank(subMchId)){
                    ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户微信识别码");
                }
                MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
                MerBaseInfoPo merBaseInfo = new MerBaseInfoPo();
                merBaseInfoPo.setMerNo(merNo);
                merBaseInfo.setSubMchId(subMchId);
                daoService.update(merBaseInfo, merBaseInfoPo);
        return updateWeiXinMerNoToMerInfoDto;
    }
}
