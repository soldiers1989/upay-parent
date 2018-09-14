package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 对账单文件下载DTO
 * @author zhangjianfeng
 * @since 2016/12/03 18:13
 */
public class MerChkFileDownDto extends BaseDto {

    /** 商户号 */
    private String merNo;

    /** 对账日期 */
    private String chkDate;

    /** 二级商户号 */
    private String secMerNo;

    /** 对账明细记录数 */
    private Integer chkListCount;

    /** 生成的对账文件名称 */
    private String chkFileName;

    /** 是否FTP上传到指定服务器 */
    private String isFtpUpload;

    /** 对账单文件存放目录 */
    private String chkFileDirPath;

    /** FTP 主机地址 */
    private String host;

    /** FTP 端口 */
    private String port;

    /** FTP 用户名 */
    private String username;

    /** FTP 密码 */
    private String password;

    /** FTP 编码 */
    private String encoding;

    /** FTP 远程目录 */
    private String remoteDirPath;


    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    public Integer getChkListCount() {
        return chkListCount;
    }

    public void setChkListCount(Integer chkListCount) {
        this.chkListCount = chkListCount;
    }

    public String getChkFileName() {
        return chkFileName;
    }

    public void setChkFileName(String chkFileName) {
        this.chkFileName = chkFileName;
    }

    public String getIsFtpUpload() {
        return isFtpUpload;
    }

    public void setIsFtpUpload(String isFtpUpload) {
        this.isFtpUpload = isFtpUpload;
    }

    public String getChkFileDirPath() {
        return chkFileDirPath;
    }

    public void setChkFileDirPath(String chkFileDirPath) {
        this.chkFileDirPath = chkFileDirPath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getRemoteDirPath() {
        return remoteDirPath;
    }

    public void setRemoteDirPath(String remoteDirPath) {
        this.remoteDirPath = remoteDirPath;
    }
}
