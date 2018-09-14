package com.upay.busi.pay.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.RegTransferFileDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.gnr.FileInfoPo;

/**
 * 登转批量需要转账的文件名
 * @author Administrator
 *
 */
public class RegTransferFileService extends AbstractDipperHandler<RegTransferFileDto>{
	 private static final Logger LOG = LoggerFactory.getLogger(RegTransferFileService.class);
	@Resource
    private IDaoService daoService;
	 /** 平台序列 */
    @Resource
    private ISequenceService sequenceService;
	@Override
	public RegTransferFileDto execute(RegTransferFileDto dto, Message arg1)
			throws Exception {
		// 文件唯一序列号
        Date nowDate = new Date();
        
		String fileNames = dto.getFileNames();
		LOG.debug("转账文件      "+fileNames);
		if(StringUtils.isBlank(fileNames)){
			 ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "批量转账文件不能为空");
		}
		String[] split = fileNames.split(",");
		if(null!=split&&split.length>0){
			for(String fileName:split){
				FileInfoPo fileInfo=new FileInfoPo();
				fileInfo.setFileName(fileName);
				fileInfo = daoService.selectOne(fileInfo);
				if(fileInfo==null){
					fileInfo = new FileInfoPo();
                	fileInfo.setFileSerino(sequenceService.generateSysSeq());
                	fileInfo.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_INIT);//初始化
                	fileInfo.setBatchNo(sequenceService.generateSysSeq());
                	fileInfo.setUpdateTime(nowDate);
                	fileInfo.setRealTransDate(DateUtil.parse(DateUtil.format(nowDate, DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
                	fileInfo.setTaskCode(CommonConstants_GNR.T_FILE_INFO_TASK_CODE_TRANSFER);
                	fileInfo.setFileName(fileName);
                	fileInfo.setFileType(CommonConstants_GNR.T_FILE_INFO_FILE_TYPE_DOWNLOAD);
                	fileInfo.setCreateTime(nowDate);
                    daoService.insert(fileInfo);
				}else{
					LOG.debug("文件名："+fileName+"   己存在。");
				}
			}
		}
		
		return dto;
	}
}
