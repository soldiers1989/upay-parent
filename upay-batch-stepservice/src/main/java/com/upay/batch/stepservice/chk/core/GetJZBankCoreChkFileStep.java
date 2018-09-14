package com.upay.batch.stepservice.chk.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.FileUtil;
import com.upay.dao.po.chk.ChkHostDetailPo;
import com.upay.dao.po.chk.ChkHostFilePo;
import com.upay.dao.po.chk.ChkInfoPo;


/**
 * 
 * 
 * @author Pactera
 *
 */
public class GetJZBankCoreChkFileStep extends AbstractStepExecutor<ChkInfoPo, ChkHostFilePo> {
    private static final Logger log = LoggerFactory.getLogger(GetJZBankCoreChkFileStep.class);

    // 核心文件路径
    private String cnapsChkPath;


    @Override
    public List<ChkInfoPo> getObjectList(BatchParams batchParams) throws BatchException {
        // TODO Auto-generated method stub
        String batchNo = batchParams.getBatchNo();
        ChkInfoPo chkInfoPo = new ChkInfoPo();
        chkInfoPo.setBatchNo(batchNo);
        chkInfoPo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
        List<ChkInfoPo> poList = daoService.selectList(chkInfoPo);

        // 生产放开
//        String chkPath =
//                cnapsChkPath + DateUtil.format(batchParams.getPreDate(), "yyyyMMdd") + File.separator;
        // 测试用
        String localPath = cnapsChkPath +
        DateUtil.format(batchParams.getPreDate(), "yyyyMMdd") + "/";
        batchParams.getParameter().put("chkPath", localPath);

        return poList;
    }


    @Override
    public int getTotalResult(BatchParams batchParams, ChkInfoPo object) throws BatchException {
        // TODO Auto-generated method stub
        // 需要直销提供ChkCnapsTempPo核心对账文件PO
        String chkPath = (String) batchParams.getParameter().get("chkPath");
        log.info("==============chkPath[{}]", chkPath);
        return (int) FileUtil.getFileRowCount(chkPath + object.getChkFile(), ChkHostFilePo.class);
    }


    @Override
    public List<ChkHostFilePo> getDataList(BatchParams batchParams, int offset, int pageSize,
            ChkInfoPo object) throws BatchException {
        // TODO Auto-generated method stub
        log.info("batchNo:[{}],rountCode:[core],fileName[{}]", batchParams.getBatchNo(),
            cnapsChkPath + object.getChkFile());
        String chkPath = (String) batchParams.getParameter().get("chkPath");
        List<ChkHostFilePo> list =
                FileUtil.readFileToList(chkPath + object.getChkFile(), ChkHostFilePo.class, offset, pageSize);
        log.info("文件记录总数[{}]", list != null ? list.size() : 0);
        log.info("文件入库 start...");
        return list;
    }


    @Override
    @SuppressWarnings("all")
    public void execute(BatchParams batchParams, int index, ChkHostFilePo data, ChkInfoPo object)
            throws BatchException {
        // TODO Auto-generated method stub
        String rountCode = object.getOrgCode();
        String checkFile = object.getChkFile();
        //续跑判断
        if(batchParams.isContinue()){
        	ChkHostDetailPo chkHostDetailPo = new ChkHostDetailPo();
        	chkHostDetailPo.setPlatSeq(data.getTransSeq());//TODO 是否加入日期条件
        	ChkHostDetailPo chk = daoService.selectOne(chkHostDetailPo);
        	if(null != chk) {
        		logger.debug("主机明细已经入库，平台流水{}", chk.getPlatSeq());
        		return;
        	}
        }
        // 登记核心对账明细表
        log.info("登记第三方表 [{}]...", index);
        insertHostDetail(data, batchParams, object);

    }



    /**
     * 登记核心对账明细表
     *
     * @param data
     * @param chkDate
     * @param chkSeq
     */
    public void insertHostDetail(ChkHostFilePo data, BatchParams batchParams, ChkInfoPo object) {
        ChkHostDetailPo chkHostDetailPo = new ChkHostDetailPo();
        chkHostDetailPo.setOrgCode(object.getOrgCode());
        chkHostDetailPo.setOrgType(object.getOrgType());
        chkHostDetailPo.setChkActNo(batchParams.getBatchNo());
        chkHostDetailPo.setClearDate(batchParams.getTranDate());

//        chkHostDetailPo.setChnlDate(data.getThirdDate());
        chkHostDetailPo.setChnlSeq(data.getHostTransSeq());
        chkHostDetailPo.setTranDate(data.getTransDate());
        chkHostDetailPo.setTranStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);

        chkHostDetailPo.setPlatDate(data.getTransDate());
        chkHostDetailPo.setPlatSeq(data.getTransSeq());
        chkHostDetailPo.setCurrNo(DataBaseConstants_PAY.T_CORE_CCY_CNY);

        // chkHostDetailPo.setAcctName(data.getPayerAcctName());
        chkHostDetailPo.setAcctNo(data.getPayerAcctNo());
        // chkHostDetailPo.setRelAcctName(data.getPayeeAcctName());
        chkHostDetailPo.setOthAcctNo(data.getPayeeAcctNo());

        chkHostDetailPo.setTransAmt(data.getAmount());
//        chkHostDetailPo.setFeeAmt(new BigDecimal(data.getFeeAmt()));
        chkHostDetailPo.setChkBatchNo(batchParams.getBatchNo());
        chkHostDetailPo.setChkDate(batchParams.getTranDate());

        
        chkHostDetailPo.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);// 未对账 
        
        daoService.insert(chkHostDetailPo);
    }


    public void setCnapsChkPath(String cnapsChkPath) {
        this.cnapsChkPath = cnapsChkPath;
    }

}
