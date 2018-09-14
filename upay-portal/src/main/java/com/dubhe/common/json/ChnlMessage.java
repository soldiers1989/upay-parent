package com.dubhe.common.json;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * @author Hing<xingguang.ren@pactera.com>
 * @since 2014年4月19日
 */
public class ChnlMessage<SH, AH, BD> {
    @JSONField(name = "SYSHEAD")
    private SH syshead;
    @JSONField(name = "APPHEAD")
    private AH apphead;
    @JSONField(name = "BODY")
    private BD body;

    private String md5;

    private String mac;

    private String msg;

    public ChnlMessage() {
    }

    public ChnlMessage(SH syshead, AH apphead, BD body) {
        super();
        this.syshead = syshead;
        this.apphead = apphead;
        this.body = body;
    }

    public ChnlMessage(SH syshead, AH apphead, BD body,String md5) {
        super();
        this.syshead = syshead;
        this.apphead = apphead;
        this.body = body;
        this.md5=md5;
    }


    public SH getSyshead() {
        return syshead;
    }

    public void setSyshead(SH syshead) {
        this.syshead = syshead;
    }

    public AH getApphead() {
        return apphead;
    }

    public void setApphead(AH apphead) {
        this.apphead = apphead;
    }

    public BD getBody() {
        return body;
    }

    public void setBody(BD body) {
        this.body = body;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
