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
import com.upay.busi.mer.service.dto.ChkWeiXinMerDelInfoDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;

/**删除微信特约商户识别码
 * @author zhanggr
 *
 */
public class ChkWeiXinMerDelInfoService extends AbstractDipperHandler<ChkWeiXinMerDelInfoDto> {
    @Resource
    private IDaoService daoService;

    
    @Override
    public ChkWeiXinMerDelInfoDto execute(ChkWeiXinMerDelInfoDto chkWeiXinMerDelInfoDto, Message msg) throws Exception {
        String merNo = chkWeiXinMerDelInfoDto.getMerNo();
        String subMchId = chkWeiXinMerDelInfoDto.getSubMchId();
        if(StringUtils.isBlank(merNo)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户号");
        }
        if(StringUtils.isBlank(subMchId)){
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户微信识别码");
        }
        MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
        MerBaseInfoPo merBaseInfo = new MerBaseInfoPo();
        merBaseInfoPo.setMerNo(merNo);
        merBaseInfo.setSubMchId("");
        daoService.update(merBaseInfo, merBaseInfoPo);
        return chkWeiXinMerDelInfoDto;
    }

}
