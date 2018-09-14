package com.upay.busi.pay.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.presys.cp.ftp.client.bean.RequestParam;
import com.pactera.dipper.presys.cp.ftp.client.service.FTPClientService;
import com.pactera.dipper.presys.cp.ftp.client.service.impl.FTPClientServiceImpl;
import com.upay.busi.pay.service.dto.MerChkFileDownDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.LocalHostUtil;
import com.upay.dao.po.chk.ChkMerListPo;
import com.upay.dao.po.mer.MerPlatSettingPo;
import com.upay.dao.po.pay.PayFlowListPo;


/**
 * 生成对账单文件
 * 
 * @author zhangjianfeng
 * @since 2016/12/05 09:09
 */
public class GenMerChkFileService extends AbstractDipperHandler<MerChkFileDownDto> {

    private static final Logger logger = LoggerFactory.getLogger(GenMerChkFileService.class);

    @Resource
    IDaoService daoService;

    private static int MAX_PAGE_SIZE = 500;

    private static char FIELD_SEPARATOR = '|';

    /** 内部交易转外部交易MAP */
    private Map<String, String> inTranCode2OutTransCodeMap;


    @Override
    public MerChkFileDownDto execute(MerChkFileDownDto dto, Message message) throws Exception {

        // 商户号
        String merNo = dto.getMerNo();

        // 对账日期
        String chkDate = dto.getChkDate();

        // 对账单文件名
        String chkFileName = dto.getChkFileName();

        // 对账单文件存放目录非空检查
        String chkFileDirPath = dto.getChkFileDirPath();
        if (StringUtils.isBlank(chkFileDirPath)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "对账单文件存放目录");
        }

        // 对账文件目录检查，如果不存在则生成目录
        File chkFileDir = new File(chkFileDirPath);
        if (!chkFileDir.exists() || !chkFileDir.isDirectory()) {
            chkFileDir.mkdirs();
        }

        // 商户对应的目录检查
        File merDir = new File(chkFileDirPath + merNo);
        if (!merDir.exists() || !merDir.isDirectory()) {
            merDir.mkdir();
        }

        // 创建对账单文件
        File chkFile = new File(chkFileDirPath + merNo + File.separator + chkFileName);
        if (!chkFile.exists()) {
            chkFile.createNewFile();
        }

        // 创建对单文件读写字节流，如果对账单文件已有内容将覆盖
        FileWriter fos = new FileWriter(chkFile, false);
        BufferedWriter bos = new BufferedWriter(fos);

        // 商户对账单查询
        Map<String, Object> where = new HashMap<String, Object>();
        where.put("chkDate", DateUtil.parse(chkDate, "yyyyMMdd"));
        where.put("merNo", merNo);
        // where.put("chkFlag", DateBaseConstants_MER.CHK_FLAG_SUCCESS);
        if (StringUtils.isNotBlank(dto.getSecMerNo())) {
            where.put("secMerNo", dto.getSecMerNo());
        }

        // 明细记录数
        Integer chkListCount = new Integer(0);

        // 为了防止全量查询时数据太大，而使用分页查询
        for (int pageNo = 1; true; pageNo++) {
            // 查询开始记录
            int offset = (pageNo - 1) * MAX_PAGE_SIZE;
            // 分页查询并生成对账文件
            List<ChkMerListPo> chkMerList = daoService
                .selectList(ChkMerListPo.class.getName() + ".selectList", where, offset, MAX_PAGE_SIZE);
            // 对账明细写入文件
            writeChkList2File(chkMerList, bos, offset);
            // 累计明细记录数
            chkListCount += chkMerList.size();

            // 如果查询记录数小于每页最大记录数，说明后续已无数据
            if (chkMerList.size() < MAX_PAGE_SIZE) {
                break;
            }
        }
        bos.close();

        // FTP上传到指定服务器
        // String isFtpUpload = dto.getIsFtpUpload();
        // if(StringUtils.isNotBlank(isFtpUpload) &&
        // CommonConstants_GNR.IS_TRUE.equals(isFtpUpload)) {
        // uploadChkFile(dto);
        // }

        uploadChkFile(dto);

        logger.info("生成商户[{}][{}]日对账文件完成，明细条数：[{}] !", new Object[] { merNo, chkDate, chkListCount });
        return dto;
    }


    /**
     * 将对账明细按格式写入对账文件 明细格式（有些字段无值则填空）：
     * 序号|交易类型|来往账标识|行内外标识|支付系统日期|支付流水号|商户日期|商户流水号|核心日期|核心流水号|付款帐号类型|付款人账号
     * |付款人名称|收款账号类型|收款人账号|收款人名称|交易金额|交易手续费|处理结果|
     * 
     * @param chkMerList
     *            对账明细列表
     * @param chkFileWriter
     *            写入对账文件字节流
     * @param startIdx
     *            记录开始索引
     */
    private void writeChkList2File(List<ChkMerListPo> chkMerList, BufferedWriter chkFileWriter, int startIdx)
            throws Exception {
        for (int i = 0; i < chkMerList.size(); i++) {
            ChkMerListPo chkList = chkMerList.get(i);
            Map<String, Object> payFlowWhere = new HashMap<String, Object>();
            payFlowWhere.put("orderNo", chkList.getOrderNo());
            payFlowWhere.put("transStat", DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
            String transType = chkList.getTransType();
            // 退货、提现用户账户为收款账户，此条件用确认订单的第一笔流水
            if (CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(transType)
                    || CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(transType)) {
                payFlowWhere.put("payeeAcctNo", chkList.getAcctNo());
            } else { // 充值、支付、转账，用户为付款账户
                payFlowWhere.put("payerAcctNo", chkList.getAcctNo());
            }

            PayFlowListPo payFlow =
                    daoService.selectOne(PayFlowListPo.class.getName() + ".selectOne", payFlowWhere);

            StringBuilder chkListStr = new StringBuilder();
            // 序号 8位
            chkListStr.append(StringUtils.leftPad(startIdx + (i + 1) + "", 8, "0")).append(FIELD_SEPARATOR);
            // 交易名称
            chkListStr.append(chkList.getTransType()).append(FIELD_SEPARATOR);
            // 来往账标识 目前只有往账
            chkListStr.append("1").append(FIELD_SEPARATOR);
            // 行内外标识
            String bankFlag = DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(payFlow.getRouteCode())
                    ? DataBaseConstants_PAY.BANK_FLAG_IN : DataBaseConstants_PAY.BANK_FLAG_OUT;
            chkListStr.append(bankFlag).append(FIELD_SEPARATOR);
            // 支付系统日期
            chkListStr.append(DateUtil.format(chkList.getTxnDate(), "yyyyMMdd")).append(FIELD_SEPARATOR);
            // 支付流水号
            chkListStr.append(chkList.getOrderNo()).append(FIELD_SEPARATOR);
            // 商户日期
            chkListStr.append(DateUtil.format(chkList.getMerDate(), "yyyyMMdd")).append(FIELD_SEPARATOR);
            // 商户流水号
            chkListStr.append(chkList.getMerOrder()).append(FIELD_SEPARATOR);
            // 核心日期 核心流水号
            if (DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(payFlow.getRouteCode())) {
                chkListStr.append(DateUtil.format(payFlow.getRouteDate(), "yyyyMMdd"))
                    .append(FIELD_SEPARATOR);
                chkListStr.append(payFlow.getRouteSeq()).append(FIELD_SEPARATOR);
            } else {
                chkListStr.append("").append(FIELD_SEPARATOR);
                chkListStr.append("").append(FIELD_SEPARATOR);
            }

            // 付款帐号类型
            chkListStr
                .append(StringUtils.isNotBlank(payFlow.getPayerAcctType()) ? payFlow.getPayerAcctType() : "")
                .append(FIELD_SEPARATOR);
            // 付款人账号
            chkListStr
                .append(StringUtils.isNotBlank(payFlow.getPayerAcctNo()) ? payFlow.getPayerAcctNo() : "")
                .append(FIELD_SEPARATOR);
            // 付款人名称
            chkListStr.append(StringUtils.isNotBlank(payFlow.getPayerName()) ? payFlow.getPayerName() : "")
                .append(FIELD_SEPARATOR);
            // 收款帐号类型
            chkListStr
                .append(StringUtils.isNotBlank(payFlow.getPayeeAcctType()) ? payFlow.getPayeeAcctType() : "")
                .append(FIELD_SEPARATOR);
            // 收款人账号
            chkListStr
                .append(StringUtils.isNotBlank(payFlow.getPayeeAcctNo()) ? payFlow.getPayeeAcctNo() : "")
                .append(FIELD_SEPARATOR);
            // 收款人名称
            chkListStr.append(StringUtils.isNotBlank(payFlow.getPayeeName()) ? payFlow.getPayeeName() : "")
                .append(FIELD_SEPARATOR);

            // 交易金额
            chkListStr.append(chkList.getTxnAmt().toString()).append(FIELD_SEPARATOR);
            // 交易手续费
            chkListStr.append(chkList.getMerFeeAmt() != null ? chkList.getMerFeeAmt().toString() : "0")
                .append(FIELD_SEPARATOR);
            // 处理结果 1-记账成功；2-记账失败；3-记账中；
            String chkFlag = chkList.getChkFlag();
            if (DateBaseConstants_MER.CHK_FLAG_NOT.equals(chkFlag)) { // 未对账
                chkListStr.append("3").append(FIELD_SEPARATOR);
            } else if (DateBaseConstants_MER.CHK_FLAG_SUCCESS.equals(chkFlag)) { // 对账成功
                chkListStr.append("1").append(FIELD_SEPARATOR);
            } else if (DateBaseConstants_MER.CHK_FLAG_NOT_BALANCED.equals(chkFlag)) { // 对账不平
                chkListStr.append("2").append(FIELD_SEPARATOR);
            }

            // 写入明细文件
            chkFileWriter.write(chkListStr.toString());
            // 使用Windows的回车换行，如果需要根据系统环境来换行可设置为：System.getProperty("line.separator")
            chkFileWriter.write("\r\n");
        }

    }


    /**
     * FTP上传对账文件
     * 
     * @param dto
     */
    private void uploadChkFile(MerChkFileDownDto dto) {

        // //FTP 主机地址
        // String host = dto.getHost();
        // if(StringUtils.isBlank(host)) {
        // ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "FTP主机地址");
        // }
        //
        // //FTP 端口
        // String port = dto.getPort();
        // if(StringUtils.isBlank(port)) {
        // ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "FTP端口");
        // }
        //
        // //FTP 用户名
        // String username = dto.getUsername();
        // if(StringUtils.isBlank(username)) {
        // ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "FTP用户名");
        // }
        //
        // //FTP 密码
        // String password = dto.getPassword();
        // if(StringUtils.isBlank(password)) {
        // ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "FTP密码");
        // }
        //
        // //FTP 编码
        // String encoding = dto.getEncoding();
        // if(StringUtils.isBlank(encoding)) {
        // ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "FTP编码");
        // }
        //
        // //FTP 远程目录
        // String remoteDirPath = dto.getRemoteDirPath();
        // if(StringUtils.isBlank(remoteDirPath)) {
        // ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "FTP远程目录");
        // }

        // 商户号
        String merNo = dto.getMerNo();

        // 对账单文件名
        String chkFileName = dto.getChkFileName();

        // 对账单文件存放目录
        String chkFileDirPath = dto.getChkFileDirPath();

        MerPlatSettingPo merPlatSetting = new MerPlatSettingPo();
        merPlatSetting.setMerPlatNo(merNo);
        merPlatSetting = daoService.selectOne(merPlatSetting);

        if (merPlatSetting == null) {
            logger.error("商户[{}]平台系统参数信息未配置", merNo);
            ExInfo.throwDipperEx(AppCodeDict.BISMER0024, new Object[] { merNo, "平台系统参数" }); // 商户[{}]的[{}]信息不存在
        }

        // 获取FTP的ip地址和文件目录
        String ftpIp = merPlatSetting.getFtpIp();
        String ftpFilePath = merPlatSetting.getFileUploadPath();
        // 获取本机的ip
        String localIp = LocalHostUtil.LOCAL_IP_ADDR;
        // 如果FTP的ip与对账文件ip不同或者FTP的文件路径与对账文件路径不同，则上传对账文件
        String localChkFilePath = chkFileDirPath + merNo + File.separator;
        logger.debug("商户[{}]对账单文件上服务器IP[{}}，远程目录[{}]；本机IP[{}]，本地目录[{}]",
            new Object[] { merNo, ftpIp, ftpFilePath, localIp, localChkFilePath });
        if (!ftpIp.equals(localIp) || !ftpFilePath.equals(localChkFilePath)) {
            RequestParam reqParam = new RequestParam();
            reqParam.setHost(merPlatSetting.getFtpIp());
            reqParam.setPort(Integer.valueOf(merPlatSetting.getFtpPort()));
            reqParam.setUsername(merPlatSetting.getFtpUserName());
            reqParam.setPassword(merPlatSetting.getFtpPwd());
            reqParam.setEncoding("UTF-8");
            reqParam.setRootDir(ftpFilePath);
            reqParam.setRemoteFileName(chkFileName);
            reqParam.setLocalFileName(localChkFilePath + chkFileName);

            FTPClientService service = new FTPClientServiceImpl(reqParam);
            boolean isUploadSuc = service.uploadFile();
            if (isUploadSuc) {
                logger.info("FTP上传商户[{}]对账文件[{}]成功", new Object[] { merNo, chkFileName });
            } else {
                logger.error("FTP上传商户[{}]对账文件[{}]失败", new Object[] { merNo, chkFileName });
                ExInfo.throwDipperEx(AppCodeDict.BISMER0031, new Object[] { merNo, chkFileName }); // FTP上传商户[{}]对账文件[{}]失败
            }
        }

    }


    public Map<String, String> getInTranCode2OutTransCodeMap() {
        return inTranCode2OutTransCodeMap;
    }


    public void setInTranCode2OutTransCodeMap(Map<String, String> inTranCode2OutTransCodeMap) {
        this.inTranCode2OutTransCodeMap = inTranCode2OutTransCodeMap;
    }


    public static void main(String args[]) {
        RequestParam reqParam = new RequestParam();
        reqParam.setHost("179.169.3.79");
        reqParam.setPort(21);
        reqParam.setUsername("appsvr");
        reqParam.setPassword("appsvr");
        reqParam.setEncoding("UTF-8");
        reqParam.setRootDir("/home/appsvr/files/chk/");
        reqParam.setRemoteFileName("temp.xml");
        reqParam.setLocalFileName("/Users/zhangjianfeng/Desktop/temp.xml");

        FTPClientService service = new FTPClientServiceImpl(reqParam);
        boolean isUploadSuc = service.uploadFile();
        if (isUploadSuc) {
            logger.error("FTP上传商户[{}]对账文件[{}]失败");
            logger.info("FTP上传商户[{}]对账文件[{}]成功");
        } else {
        }
    }

}
