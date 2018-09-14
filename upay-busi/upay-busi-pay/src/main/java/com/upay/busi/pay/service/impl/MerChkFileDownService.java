package com.upay.busi.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.MerChkFileDownDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkMerListPo;


/**
 * 商户对账单下载
 * 
 * @author zhangjianfeng
 * @since 2016/12/03 18:13
 */
public class MerChkFileDownService extends AbstractDipperHandler<MerChkFileDownDto> {

    private static final Logger logger = LoggerFactory.getLogger(MerChkFileDownService.class);

    @Resource
    IDaoService daoService;


    @Override
    public MerChkFileDownDto execute(MerChkFileDownDto dto, Message message) throws Exception {

        // 商户号非空检查
        String merNo = dto.getMerNo();
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
        }

        // 对账日期非空检查
        String chkDate = dto.getChkDate();
        if (StringUtils.isBlank(chkDate)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "对账日期");
        }

        // 商户对账单查询
        Map<String, Object> where = new HashMap<String, Object>();
        where.put("chkDate", DateUtil.parse(chkDate, "yyyyMMdd"));
        where.put("merNo", merNo);
        // where.put("chkFlag", DataBaseConstants_MER.CHK_FLAG_SUCCESS);
        if (StringUtils.isNotBlank(dto.getSecMerNo())) {
            where.put("secMerNo", dto.getSecMerNo());
        }

        // 对账文件名
        String chkFileName = merNo + "_CHKFILE_" + chkDate;

        Integer chkListCount =
                daoService.selectOne(ChkMerListPo.class.getName() + ".chkMerListDownCount", where);

        if (chkListCount == null) {
            chkListCount = Integer.valueOf(0);
        }

        dto.setChkFileName(chkFileName);
        dto.setChkListCount(chkListCount);
        return dto;
    }

}
