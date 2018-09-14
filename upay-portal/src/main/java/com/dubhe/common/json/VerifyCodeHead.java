package com.dubhe.common.json;

import com.alibaba.fastjson.annotation.JSONField;
import com.dubhe.common.annotation.NotNull;

/**
 * 图形验证码头
 * @author freeplato
 *
 */
public class VerifyCodeHead {

	/**
	 * 图形验证码KEY
	 */
    @NotNull
    @JSONField(name = "VERIFY_KEY")
    private String verifyKey;
    
    /**
     * 图形验证码
     */
    @JSONField(name = "VERIFY_CODE")
    private String verifyCode;

	public String getVerifyKey() {
		return verifyKey;
	}

	public void setVerifyKey(String verifyKey) {
		this.verifyKey = verifyKey;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

}
