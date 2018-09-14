/**
 * 
 */
package com.upay.busi.mer.service.dto;

import java.util.Date;

import com.upay.commons.dto.BaseDto;


/**
 * 操作商户参数配置
 * 
 * @author zhanggr
 *
 */
public class ControlMerPlatSettingDto extends BaseDto {
    private String contralFlag;// 操作标识 增删改  0：增加 1：删除 2：改 3：查
    private String merPlatNo;// 商户号
    private String fileUrl;// 文件URL地址
    private String referUrl;// 来源url
    private String signType;// 签名类型
    private String fileEncryType;// 文件加密类型
    private String key3des;// 密钥3DES
    private String checkFileFormType;// 对账文件格式类型
    private String signCheckFlag;// 清算对账标志
    private String fileTransferMode;// 文件传输模式
    private String ftpIp;// FTPIP地址
    private String ftpPort;// FTP端口
    private String ftpUserName;// FTP用户名
    private String ftpPwd;// FTP密码
    private String fileUploadPath;// 文件上传路径
    private String modifyUser;// 修改人
    private Date dateLastMaint;// 最后更新日期
    private String merRepUrlB;// 通知商户平台地址(后台)
    private String merRepUrlF;// 通知商户平台地址(前台)
    private String merKey;// 商户密钥
    private String clearCheckFlag;// 清算对账标志
    private String controlChnlId;// 操作渠道    不为空  传04【内管】


    public String getContralFlag() {
        return contralFlag;
    }


    public void setContralFlag(String contralFlag) {
        this.contralFlag = contralFlag;
    }


    public String getMerPlatNo() {
        return merPlatNo;
    }


    public void setMerPlatNo(String merPlatNo) {
        this.merPlatNo = merPlatNo;
    }


    public String getFileUrl() {
        return fileUrl;
    }


    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }


    public String getReferUrl() {
        return referUrl;
    }


    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl;
    }


    public String getSignType() {
        return signType;
    }


    public void setSignType(String signType) {
        this.signType = signType;
    }


    public String getFileEncryType() {
        return fileEncryType;
    }


    public void setFileEncryType(String fileEncryType) {
        this.fileEncryType = fileEncryType;
    }


    public String getKey3des() {
        return key3des;
    }


    public void setKey3des(String key3des) {
        this.key3des = key3des;
    }


    public String getCheckFileFormType() {
        return checkFileFormType;
    }


    public void setCheckFileFormType(String checkFileFormType) {
        this.checkFileFormType = checkFileFormType;
    }


    public String getSignCheckFlag() {
        return signCheckFlag;
    }


    public void setSignCheckFlag(String signCheckFlag) {
        this.signCheckFlag = signCheckFlag;
    }


    public String getFileTransferMode() {
        return fileTransferMode;
    }


    public void setFileTransferMode(String fileTransferMode) {
        this.fileTransferMode = fileTransferMode;
    }


    public String getFtpIp() {
        return ftpIp;
    }


    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }


    public String getFtpPort() {
        return ftpPort;
    }


    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }


    public String getFtpUserName() {
        return ftpUserName;
    }


    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }


    public String getFtpPwd() {
        return ftpPwd;
    }


    public void setFtpPwd(String ftpPwd) {
        this.ftpPwd = ftpPwd;
    }


    public String getFileUploadPath() {
        return fileUploadPath;
    }


    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }


    public String getModifyUser() {
        return modifyUser;
    }


    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }


    public Date getDateLastMaint() {
        return dateLastMaint;
    }


    public void setDateLastMaint(Date dateLastMaint) {
        this.dateLastMaint = dateLastMaint;
    }


    public String getMerRepUrlB() {
        return merRepUrlB;
    }


    public void setMerRepUrlB(String merRepUrlB) {
        this.merRepUrlB = merRepUrlB;
    }


    public String getMerRepUrlF() {
        return merRepUrlF;
    }


    public void setMerRepUrlF(String merRepUrlF) {
        this.merRepUrlF = merRepUrlF;
    }


    public String getMerKey() {
        return merKey;
    }


    public void setMerKey(String merKey) {
        this.merKey = merKey;
    }


    public String getClearCheckFlag() {
        return clearCheckFlag;
    }


    public void setClearCheckFlag(String clearCheckFlag) {
        this.clearCheckFlag = clearCheckFlag;
    }


    public String getControlChnlId() {
        return controlChnlId;
    }


    public void setControlChnlId(String controlChnlId) {
        this.controlChnlId = controlChnlId;
    }

}
