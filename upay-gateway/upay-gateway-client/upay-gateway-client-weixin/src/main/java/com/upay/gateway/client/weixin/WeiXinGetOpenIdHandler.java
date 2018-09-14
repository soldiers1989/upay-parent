/**
 * 
 */
package com.upay.gateway.client.weixin;

import static com.pactera.dipper.core.utils.Constants.Channel._IS_CLIENT;
import static com.pactera.dipper.core.utils.Constants.Channel._TRAN_CODE_NAME;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.presys.cp.client.AbstractClientDipperHandler;
import com.upay.commons.dict.AppCodeDict;
import com.upay.gateway.client.weixin.util.WeixinUtil;

/**
 * @author hing
 * 
 */
public class WeiXinGetOpenIdHandler implements IDipperHandler<Message> {
	private final static Logger LOG = LoggerFactory
			.getLogger(AbstractClientDipperHandler.class);
	private static final String PRESYS = "PRESYS";

	@Override
	public Message handle(Message m) throws Exception {
		Map<String, Object> targetHeaderMap = new HashMap<String, Object>(3);
		Map<String, Object> targetMap = (Map<String, Object>) m.getTarget()
				.getBodys();
		targetHeaderMap.put(_IS_CLIENT, true);
		targetHeaderMap.put(PRESYS, PRESYS);
		if (null != m.getOther()) {
			String headTranCode = (String) m.getOther().get(_TRAN_CODE_NAME);
			if (m.getOther().containsKey(_TRAN_CODE_NAME)
					&& StringUtils.isNotBlank(headTranCode)) {
				targetHeaderMap.put(_TRAN_CODE_NAME, headTranCode);
				m.getOther().remove(_TRAN_CODE_NAME);
			}
			LOG.debug("param-ref[{}]", m.getOther());
			targetMap.putAll(m.getOther());
		}
		Map<String, Object> InitMap = (Map<String, Object>) m.getTarget()
				.getBodys();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String flag = InitMap.get("searchFlag").toString();
		if (StringUtils.isNotBlank(flag) && flag.equals("1")) {
			returnMap = WeixinUtil.getOpenId((String) InitMap.get("code"),
					(String) InitMap.get("appId"),
					(String) InitMap.get("appSecret"));
		} else if (StringUtils.isNotBlank(flag) && flag.equals("2")) {
			returnMap = WeixinUtil.checkAccessToken(
					(String) InitMap.get("accessToken"),
					(String) InitMap.get("openid"));
		} else if (StringUtils.isNotBlank(flag) && flag.equals("3")) {
			returnMap = WeixinUtil.refreshAccessToken(
					(String) InitMap.get("refreshToken"),
					(String) InitMap.get("appId"));
		} else {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "查询标识");
		}

		targetMap.putAll(returnMap);

		return m;
	}
}
