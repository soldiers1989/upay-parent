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
import com.upay.busi.mer.service.dto.MerInfoQryAndChkDto;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;


/**
 * 商户基本信息查询和状态检查
 * 
 * @author zhanggr
 * 
 */
public class MerInfoQryAndChkService extends AbstractDipperHandler<MerInfoQryAndChkDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public MerInfoQryAndChkDto execute(MerInfoQryAndChkDto merInfoQryAndChkDto, Message message)
            throws Exception {
        String merNo = merInfoQryAndChkDto.getMerNo();
        String secMerNo = merInfoQryAndChkDto.getSecMerNo();
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
        }
        String merState = "";
        String payopenflag = "";
        MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
        merBaseInfoPo.setMerNo(merNo);
        merBaseInfoPo = daoService.selectOne(merBaseInfoPo);
        if (merBaseInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "商户:" + merNo);
        } else {
            merState = merBaseInfoPo.getMerState();
            if (DateBaseConstants_MER.MER_STAT_NORMAL.equals(merState)) {
                payopenflag = merBaseInfoPo.getPayOpenFlag();
                if (StringUtils.isNotBlank(payopenflag) && DateBaseConstants_MER.MER_PAYOPENFLAG_CLOSE.equals(payopenflag)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISMER0011, merNo);
                }
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISMER0010, merNo);
            }
            merInfoQryAndChkDto.setMerName(merBaseInfoPo.getMerName()+"("+merBaseInfoPo.getMerAddr()+")");
            merInfoQryAndChkDto.setMerNameResult(merBaseInfoPo.getMerName());
            merInfoQryAndChkDto.setPromoterDeptNo(merBaseInfoPo.getParentMerNo());
            String subMerId = merBaseInfoPo.getSubMchId();
            if(StringUtils.isBlank(subMerId)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0103, merNo);
            }else{
            	merInfoQryAndChkDto.setSubMchId(merBaseInfoPo.getSubMchId());  
            }
        }
        if(StringUtils.isNotBlank(secMerNo)){//校验二级商户状态
           /* MerBaseInfoPo merBaseInfo = new MerBaseInfoPo();
            merBaseInfo.setMerNo(secMerNo);
            merBaseInfo = daoService.selectOne(merBaseInfo);
            if(merBaseInfo!=null){
                merState = merBaseInfo.getMerState();
                if (DateBaseConstants_MER.MER_STAT_NORMAL.equals(merState)) {
                    payopenflag = merBaseInfoPo.getPayOpenFlag();
                    if (StringUtils.isNotBlank(payopenflag) && DateBaseConstants_MER.MER_PAYOPENFLAG_CLOSE.equals(payopenflag)) {
                        ExInfo.throwDipperEx(AppCodeDict.BISMER0011, merNo);
                    }
                } else {
                    ExInfo.throwDipperEx(AppCodeDict.BISMER0010, merNo);
                }
                String parentMerNo = merBaseInfo.getParentMerNo();
                if(StringUtils.isBlank(parentMerNo)){
                    ExInfo.throwDipperEx(AppCodeDict.BISMER0004);
                }else{
                    if(!parentMerNo.equals(merNo)){
                        ExInfo.throwDipperEx(AppCodeDict.BISMER0025,merNo,secMerNo);  
                    }
                }
            }else{
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "二级商户:" + secMerNo);
            }*/
        }
        return merInfoQryAndChkDto;
    }
}
