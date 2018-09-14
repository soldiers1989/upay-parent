package com.upay.batch.stepservice.chk.union;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdk.SecureUtil;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.dao.po.chk.ChkInfoPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * 下载银联对账文件
 *
 * @author Pactera
 */
public class DownloadUnionPayChkFile extends AbstractStepExecutor<Map<String, String>, Object> {

    private static final Logger log = LoggerFactory.getLogger(DownloadUnionPayChkFile.class);

    //对账文件的目录
    private String outPutDirectory;


    @Resource(name = "UnionPayFileTransferDipperHandler")
    private IDipperHandler<Message> unionPayFileTransferDipperHandler;


    @Override
    public void execute(BatchParams batchParams, int index, Object data, Map<String, String> object)
            throws BatchException {
        //1.下载银联报文对账文件
        Message message = this.downFile();
        Map<String, String> body = (Map<String, String>) message.getTarget().getBodys();
        //2.获取zip包路径
        String zipFilePath = this.deCodeFileContent(body, outPutDirectory, CmparmConstants.ENCODING);
        //成功获取zip包路径
        if (StringUtils.isNotBlank(zipFilePath)) {
            // 4.进行解压
            List<String> fileList = this.unzip(zipFilePath, outPutDirectory);
            if (!fileList.isEmpty()) {
                //5.获取解压文件的目录
                for (String fileName : fileList) {
                    if (StringUtils.isNotBlank(fileName)) {
                        //获取银联文件的的解压目录   每天对应的银联对账文件对应一个解压目录
                        String outPutZipDirectory = fileName.substring(0, fileName.lastIndexOf(File.separator));
                        //获取银联对账文件的文件名
                        fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.length());
                        log.info("银联文件对账文件解压目录-----------[{}]", outPutZipDirectory);
                        //7.设置银联对账文件的解压目录
                        batchParams.getParameter().put("outPutZipDirectory", outPutZipDirectory);
                        //8.获取到银联对账文件名  银联包括两个对账文件  其中INN 开头的才是需要的文件
                        if (fileName.startsWith("INN")) {
                            //9.插入银联对账文件名
                            log.info("银联文件对账文件名-----------[{}]", fileName);
                            this.insertChkInfo(batchParams, fileName);
                            break;
                        }
                    }

                }
            }
        }
        //下载对账文件失败的情况
        else {
            throw new BatchException("银联对账文件下载失败，请检查商户号是否有下载权限");
        }


    }


    /**
     * Description: 从服务器下载文件
     *
     * @return
     */
    public Message downFile() {

        Map<String, Object> contentData = new HashMap<String, Object>();
        //组装报文
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessageInstance(),
                        MessageFactory.createSimpleMessage(null, contentData),
                        null, null);
        try {
            log.debug("-------------DownloadUnionPayChkFileStep download start");
            //获取返回的报文
            message = unionPayFileTransferDipperHandler.handle(message);
            Map<String, Object> body = (Map<String, Object>) message.getTarget().getBodys();
            //zip文件完整路径
            String filePath = null;
            if (body != null && !body.isEmpty()) {
                log.info("-----fileName:[{}]", body.get("fileName"));
                filePath = outPutDirectory + body.get("fileName");
                log.info("-----filePath:[{}]", filePath);
            }
            log.info("-------------DownloadUnionPayChkFileStep download end");
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 功能：解析交易返回的fileContent字符串并落地 （ 解base64，解DEFLATE压缩并落地）<br>
     * 适用到的交易：对账文件下载，批量交易状态查询<br>
     *
     * @param resData       返回报文map<br>
     * @param fileDirectory 落地的文件目录（绝对路径）
     * @param encoding      上送请求报文域encoding字段的值<br>
     */
    public String deCodeFileContent(Map<String, String> resData, String fileDirectory, String encoding) {
        // 解析返回文件
        String filePath = null;
        String fileContent = resData.get(SDKConstants.param_fileContent);
        if (null != fileContent && !"".equals(fileContent)) {
            FileOutputStream out = null;
            try {
                byte[] fileArray = SecureUtil.inflater(SecureUtil
                        .base64Decode(fileContent.getBytes(encoding)));
                if (SDKUtil.isEmpty(resData.get("fileName"))) {
                    filePath = fileDirectory + File.separator + resData.get("merId")
                            + "_" + resData.get("batchNo") + "_"
                            + resData.get("txnTime") + ".txt";
                } else {
                    filePath = fileDirectory + File.separator + resData.get("fileName");
                }
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                out = new FileOutputStream(file);
                out.write(fileArray, 0, fileArray.length);
                out.flush();
            } catch (UnsupportedEncodingException e) {
                log.info(e.getMessage());
            } catch (IOException e) {
                log.info(e.getMessage());
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }


    /**
     * 文件解压缩并解析
     *
     * @param zipFilePath     输入源zip路径
     * @param outPutDirectory 需要解析的zip文件位置
     * @return 返回解压的目录
     */
    public List<String> unzip(String zipFilePath, String outPutDirectory) {
        List<String> fileList = new ArrayList<String>();
        try {
            outPutDirectory = outPutDirectory + File.separator + zipFilePath.substring(zipFilePath.lastIndexOf(File.separator), zipFilePath.lastIndexOf("."));
            log.info("-----[{}]银联对账文件解压开始，输出到[{}]文件夹", zipFilePath, outPutDirectory);
            //输入源zip路径
            ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFilePath));
            BufferedInputStream bin = new BufferedInputStream(zin);
            BufferedOutputStream bout = null;
            File file = null;
            ZipEntry entry;
            try {
                while ((entry = zin.getNextEntry()) != null && !entry.isDirectory()) {
                    file = new File(outPutDirectory, entry.getName());
                    if (!file.exists()) {
                        (new File(file.getParent())).mkdirs();
                    }
                    bout = new BufferedOutputStream(new FileOutputStream(file));
                    int b;
                    while ((b = bin.read()) != -1) {
                        bout.write(b);
                    }
                    bout.flush();
                    fileList.add(file.getAbsolutePath());
                    log.info("-----[{}]解压成功", file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bin.close();
                    zin.close();
                    if (bout != null) {
                        bout.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    /**
     * 插入对账文件的文件名
     *
     * @param batchParams
     * @param fileName
     */
    public void insertChkInfo(BatchParams batchParams, String fileName) {
        ChkInfoPo chkInfo = new ChkInfoPo();
        chkInfo.setBatchNo(batchParams.getBatchNo());
        chkInfo.setChkFile(fileName);
        chkInfo = this.daoService.selectOne(chkInfo);
        if (chkInfo == null) {
            chkInfo = new ChkInfoPo();
            chkInfo.setBatchNo(batchParams.getBatchNo());
            chkInfo.setChkFile(fileName);
            chkInfo.setChkDate(batchParams.getTranDate());
            chkInfo.setBenchmarkFlag("03");
            chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
            chkInfo.setOrgType("002");// 资金通道
            chkInfo.setChkTime(new Date());
            chkInfo.setChkEntryClsCd(batchParams.getBatchNo());
            chkInfo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_NO);
            chkInfo.setFileType("0");// 下载
            chkInfo.setFileDownStat("3");// 下载成功
            daoService.insert(chkInfo);
        }
        log.info("-------------DownloadUnionPayChkFileStep insert ChkInfo fileName[{}]", fileName);

    }

    public String getOutPutDirectory() {
        return outPutDirectory;
    }

    public void setOutPutDirectory(String outPutDirectory) {
        this.outPutDirectory = outPutDirectory;
    }

}

