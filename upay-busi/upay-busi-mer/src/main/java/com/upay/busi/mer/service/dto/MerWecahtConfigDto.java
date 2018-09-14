/**
 * 
 */
package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**新增微信特约商户，校验商户信息
 * @author zhanggr
 *
 */
public class MerWecahtConfigDto extends BaseDto {
	private String operateFlag;//操作标志  1 添加支付授权目录  2 绑定APPID  3 推荐关注APPID
    private String merNo;//商户号
    private String subMchId;//商户的商户号
    
    private String jsapiPath;//授权目录
    private String subAppid;//绑定特约商户或渠道公众号、小程序、APP支付等对应的APPID
    private String subscribeAppid;//推荐关注APPID
    private String tranCode;//
    
    private String updateFlag;//1:微信商户  2：微信回调地址配置  3：关注appid 4:配置appid   5:支付宝    6：银联
    
    
    
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getOperateFlag() {
		return operateFlag;
	}
	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getSubMchId() {
		return subMchId;
	}
	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}
	public String getJsapiPath() {
		return jsapiPath;
	}
	public void setJsapiPath(String jsapiPath) {
		this.jsapiPath = jsapiPath;
	}
	public String getSubAppid() {
		return subAppid;
	}
	public void setSubAppid(String subAppid) {
		this.subAppid = subAppid;
	}
	public String getSubscribeAppid() {
		return subscribeAppid;
	}
	public void setSubscribeAppid(String subscribeAppid) {
		this.subscribeAppid = subscribeAppid;
	}
    
    
}
