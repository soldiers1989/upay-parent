package com.dubhe.common.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 签名验签初始准备工具类 Created by Guo on 16/1/12.
 */
public class Dom4jUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(Dom4jUtil.class);
	private static final String SIGN_TYPE = "signType";
	private static final String SIGN = "Sign";

	/**
	 * 格式化xml,去除空格换行
	 * 
	 * @param message
	 * @param charset
	 * @param logSeq
	 * @return
	 */
	public static String formatXML(String message, String charset, String logSeq) {
		StringWriter w = new StringWriter();
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding(charset);
		format.setExpandEmptyElements(true);
		XMLWriter xw = new XMLWriter(w, format);
		try {
			Document doc = DocumentHelper.parseText(message);
			xw.write(doc);
		} catch (Exception e) {
			logger.error("{}", logSeq, e);
		} finally {
			try {
				if (null != xw) {
					xw.close();
				}
			} catch (IOException e) {
				logger.error("{}", logSeq, e);
			}
			try {
				if (null != w) {
					w.close();
				}
			} catch (IOException e) {
				logger.error("{}", logSeq, e);
			}
		}

		return w.toString();
	}

	/**
	 * 添加签名标签
	 * 
	 * @param message
	 * @param charset
	 * @param signText
	 * @return
	 */
	public static String addSign2XML(String message, String charset,
			String signText) {
		StringWriter w = new StringWriter();
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding(charset);
		format.setExpandEmptyElements(true);
		XMLWriter xw = new XMLWriter(w, format);
		try {
			Document doc = DocumentHelper.parseText(message);
			Element root = doc.getRootElement();
			root.addElement(SIGN).addText(signText);
			xw.write(doc);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				if (null != xw) {
					xw.close();
				}
			} catch (IOException e) {
				logger.error("", e);
			}
			try {
				if (null != w) {
					w.close();
				}
			} catch (IOException e) {
				logger.error("", e);
			}
		}

		return w.toString();
	}

	/**
	 * 获取签名原始串,不含空格,换行符
	 * 
	 * @param message
	 * @return
	 */
	public static StringBuilder getPlainText(String message, String dataSignName)
			throws Exception {
		List<String> list = new ArrayList<>();
		Document document = DocumentHelper.parseText(message);
		Element el = document.getRootElement();
		Iterator it = el.elementIterator();
		while (it.hasNext()) {
			Element next = (Element) it.next();
			// 空值、sign、signType不参与签名
			if (StringUtils.isNotBlank(next.getText())
					&& !dataSignName.equals(next.getName())
					&& !SIGN_TYPE.equals(next.getName())) {
				StringBuilder keyVal = new StringBuilder();
				keyVal.append(next.getName()).append("=")
						.append(next.getText());// 拼接key=value
				list.add(keyVal.toString());
				logger.debug(keyVal.toString());
			}
		}

		// 排序
		String[] keyVals = new String[list.size()];
		list.toArray(keyVals);
		Arrays.sort(keyVals);

		StringBuilder signBuf = new StringBuilder();
		for (String keyVal : keyVals) {
			signBuf.append(keyVal).append("&");
		}
		return signBuf;
	}

}
