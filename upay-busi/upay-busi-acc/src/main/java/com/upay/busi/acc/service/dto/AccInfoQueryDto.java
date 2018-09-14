package com.upay.busi.acc.service.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.upay.commons.dto.BaseDto;


/**
 * 虚拟账户信息查询
 * 
 * @author liyulong
 * 
 */
public class AccInfoQueryDto extends BaseDto {
    private String vAcctNo;// 账号
    private String vAcctStat;// 账户状态
    private String stopStat;// 止付状态
    private String frzStat;// 冻结状态
    private String setFlag;// 账户设置标志
    private String acctName;// 账户户名
    private String acctOtherName;// 账户别名
    private String CCY;// 币种
    private BigDecimal frzBal;// 冻结余额
    private BigDecimal acctBal;// 账户余额
    private BigDecimal lastBal;// 昨日账户余额
    private BigDecimal cutBal;// 日切点余额
    private Date extTime;// 注销时间
    private Date lastChgTime;// 最新修改日期
    private Date lastTime;// 最后修改时间
    private String DAC;// DAC验证码
    private Date openTime;// 开户时间
    private BigDecimal useBal;// 账户余额


    public String getvAcctNo() {
        return vAcctNo;
    }


    public void setvAcctNo(String vAcctNo) {
        this.vAcctNo = vAcctNo;
    }


    public String getvAcctStat() {
        return vAcctStat;
    }


    public void setvAcctStat(String vAcctStat) {
        this.vAcctStat = vAcctStat;
    }


    public String getStopStat() {
        return stopStat;
    }


    public void setStopStat(String stopStat) {
        this.stopStat = stopStat;
    }


    public String getFrzStat() {
        return frzStat;
    }


    public void setFrzStat(String frzStat) {
        this.frzStat = frzStat;
    }


    public String getSetFlag() {
        return setFlag;
    }


    public void setSetFlag(String setFlag) {
        this.setFlag = setFlag;
    }


    public String getAcctName() {
        return acctName;
    }


    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }


    public String getAcctOtherName() {
        return acctOtherName;
    }


    public void setAcctOtherName(String acctOtherName) {
        this.acctOtherName = acctOtherName;
    }


    public String getCCY() {
        return CCY;
    }


    public void setCCY(String cCY) {
        CCY = cCY;
    }


    public BigDecimal getFrzBal() {
        return frzBal;
    }


    public void setFrzBal(BigDecimal frzBal) {
        this.frzBal = frzBal;
    }


    public BigDecimal getAcctBal() {
        return acctBal;
    }


    public void setAcctBal(BigDecimal acctBal) {
        this.acctBal = acctBal;
    }


    public BigDecimal getLastBal() {
        return lastBal;
    }


    public void setLastBal(BigDecimal lastBal) {
        this.lastBal = lastBal;
    }


    public BigDecimal getCutBal() {
        return cutBal;
    }


    public void setCutBal(BigDecimal cutBal) {
        this.cutBal = cutBal;
    }


    public Date getLastChgTime() {
        return lastChgTime;
    }


    public void setLastChgTime(Date lastChgTime) {
        this.lastChgTime = lastChgTime;
    }


    public String getDAC() {
        return DAC;
    }


    public void setDAC(String dAC) {
        DAC = dAC;
    }


    public Date getExtTime() {
        return extTime;
    }


    public void setExtTime(Date extTime) {
        this.extTime = extTime;
    }


    public Date getLastTime() {
        return lastTime;
    }


    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }


    public Date getOpenTime() {
        return openTime;
    }


    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }


    public BigDecimal getUseBal() {
        return useBal;
    }


    public void setUseBal(BigDecimal useBal) {
        this.useBal = useBal;
    }

}
