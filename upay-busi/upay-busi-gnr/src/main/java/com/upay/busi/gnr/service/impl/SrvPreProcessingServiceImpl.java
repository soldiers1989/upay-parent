package com.upay.busi.gnr.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.gnr.service.dto.SrvPreProcessingDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.gnr.GnrOperateListPo;
import com.upay.dao.po.gnr.GnrTransConfPo;
import com.upay.dao.po.mer.MerBaseInfoPo;


/**
 * 接口服务前处理 步骤： 1.根据交易代码查询交易属性配置表，判断交易是否开通 2.预计操作流水 3.查询后处理的事件处理标志（短信、通知、积分返还）
 * 
 * @author freeplato
 * 
 */
public class SrvPreProcessingServiceImpl extends AbstractDipperHandler<SrvPreProcessingDto> {

    @Resource
    private IDaoService daoService;
    @Resource
    ISequenceService sequenceService;


    @Override
    public SrvPreProcessingDto execute(SrvPreProcessingDto srvPreProcessingDTO, Message message)
            throws Exception {

        String userId = srvPreProcessingDTO.getUserId();
        String chnlId = srvPreProcessingDTO.getChnlId();
        String serviceCode = srvPreProcessingDTO.getTransCode();
        String operSeq = sequenceService.generateOperSeq();
        Date sysTime = new Date();

        GnrTransConfPo gnrServiceConf = transStatCheck(serviceCode, srvPreProcessingDTO);
        
        savePreOperateList(userId, chnlId, serviceCode, gnrServiceConf.getTransName(), operSeq, sysTime);

        /*
         * String eventNo = gnrServiceConf.getEventNo(); String pointsReturnFlag
         * = gnrServiceConf.getPointsReturnFlag();
         */
        String amtFlag = gnrServiceConf.getAmtFlag();
        /*
         * srvPreProcessingDTO = postEventDeal(srvPreProcessingDTO, eventNo,
         * pointsReturnFlag);
         */

        srvPreProcessingDTO.setSysSeq(operSeq);
        srvPreProcessingDTO.setSysDate(SysInfoContext.getSysDate());

        srvPreProcessingDTO.setSysTime(sysTime);
        srvPreProcessingDTO.setSysStat(SysInfoContext.getSysStat());
        srvPreProcessingDTO.setAmtFlag(amtFlag);
        if (CommonConstants_GNR.AMT_FLAG_YES.equals(amtFlag)) {
            srvPreProcessingDTO.setDcFlag(gnrServiceConf.getDcFlag());
        }

        return srvPreProcessingDTO;
    }


    /**
     * 交易属性检查
     * 
     * @return
     */
    private GnrTransConfPo transStatCheck(String serviceCode, SrvPreProcessingDto dto) {
        GnrTransConfPo gnrServiceConf = new GnrTransConfPo();
        gnrServiceConf.setTransCode(serviceCode);
        gnrServiceConf = daoService.selectOne(gnrServiceConf);
        if (gnrServiceConf == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0001, serviceCode);
        }
        // String smsNo=gnrServiceConf.getEventNo();
        // if(StringUtils.isNotBlank(smsNo)){
        //
        // GnrSmsConfPo gnrSmsConfPo = new GnrSmsConfPo();
        // gnrSmsConfPo.setSmsNo(smsNo);
        // gnrSmsConfPo.setSmsStat(CommonConstants_GNR.SMS_STAT_ENABLE);
        // gnrSmsConfPo.addOrder(Order.asc("smsSendObj"));
        // gnrSmsConfPo.addOrder(Order.asc("smsSeq"));
        // gnrSmsConfPo = daoService.selectOne(gnrSmsConfPo);
        //
        // //设置短信模版和短信编号
        // if(null!=gnrSmsConfPo){
        // dto.setSmsNo(smsNo);
        // dto.setSmsMsg(gnrSmsConfPo.getSmsMsg());
        // }
        // }

        String serviceStat = gnrServiceConf.getTransStat();
        if (!CommonConstants_GNR.SERVICE_STAT_NORMAL.equals(serviceStat)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0002, serviceCode);
        }
        return gnrServiceConf;
    }


    /**
     * 预计操作流水记录
     */
    private void savePreOperateList(String userId, String chnlId, String serviceCode, String transName,
            String operSeq, Date sysTime) {
        GnrOperateListPo gnrOperateList = new GnrOperateListPo();
        gnrOperateList.setUserId(userId);
        gnrOperateList.setChnlId(chnlId);
        gnrOperateList.setOperTime(sysTime);
        gnrOperateList.setOperSeq(operSeq);
        gnrOperateList.setTransCode(serviceCode);
        gnrOperateList.setTransName(transName);
        gnrOperateList.setOperStat(CommonConstants_GNR.OPERATE_STAT_FAIL);
        gnrOperateList.setRspCode(CommonConstants_GNR.RSP_CODE_FAIL);
        gnrOperateList.setRspMsg(CommonConstants_GNR.RSP_CODE_FAIL_DESC);
        daoService.insert(gnrOperateList);
    }

    /**
     * 查询后处理的事件处理标志
     * 
     * @param srvPreProcessingDTO
     * @param eventNo
     * @param pointsReturnFlag
     * @return
     */
    // private SrvPreProcessingDto postEventDeal(
    // SrvPreProcessingDto srvPreProcessingDTO, String eventNo,
    // String pointsReturnFlag) {
    // if (StringUtils.isNotBlank(eventNo)) {
    //
    // GnrEventConfPo gnrEventConfPo = new GnrEventConfPo();
    // gnrEventConfPo.setEventNo(eventNo);
    // gnrEventConfPo = daoService.selectOne(gnrEventConfPo);
    // if (gnrEventConfPo != null) {
    // // 短信前处理
    // GnrSmsConfPo gnrSmsConfPo = new GnrSmsConfPo();
    // gnrSmsConfPo.setEventNo(eventNo);
    // gnrSmsConfPo.setSmsStat(CommonConstants_GNR.SMS_STAT_ENABLE);
    // gnrSmsConfPo.addOrder(Order.asc("smsSendObj"));
    // gnrSmsConfPo.addOrder(Order.asc("smsSeq"));
    // List<GnrSmsConfPo> gnrSmsConfPolist = daoService
    // .selectList(gnrSmsConfPo);
    // Map<String, List<String>> smsNos = new HashMap<String, List<String>>();
    // for (GnrSmsConfPo gnrSmsConfPoEach : gnrSmsConfPolist) {
    // String smsSendObj = gnrSmsConfPoEach.getSmsSendObj();
    // List<String> list = new ArrayList<String>();
    // if (smsNos.containsKey(smsSendObj)) {
    // list = smsNos.get(smsSendObj);
    // list.add(gnrSmsConfPoEach.getSmsMsg());
    // } else {
    // list.add(gnrSmsConfPoEach.getSmsMsg());
    // }
    // smsNos.put(smsSendObj, list);
    // }
    // if (smsNos.size() > 0) {
    // srvPreProcessingDTO.setSmsNos(smsNos);
    // }
    // }
    // }

    // 积分返还处理
    // if (StringUtils.isNotBlank(pointsReturnFlag)) {
    // srvPreProcessingDTO.setPointsReturnFlag(pointsReturnFlag);
    // }
    //
    // return srvPreProcessingDTO;
    // }
}
