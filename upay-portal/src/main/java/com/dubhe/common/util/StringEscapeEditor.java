package com.dubhe.common.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.propertyeditors.PropertiesEditor;

/**
 * String 对HTML SQL JS的转换
 * @author chen.nie
 *
 */
public class StringEscapeEditor extends PropertiesEditor {

	private boolean escapeHTML;
	private boolean escapeJavaScript;
	private boolean escapeSQL;

	public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript,
			boolean escapeSQL) {
		this.escapeHTML = escapeHTML;
		this.escapeJavaScript = escapeJavaScript;
		this.escapeSQL = escapeSQL;
	}

	@Override
	public void setAsText(String text) {
		if (text == null) {
			setValue(null);
		} else {
			String value = text;
			if (escapeHTML) {
				value = StringEscapeUtils.escapeHtml(value);
			}
			if (escapeJavaScript) {
				value = StringEscapeUtils.escapeJavaScript(value);
			}
			if (escapeSQL) {
				value = StringEscapeUtils.escapeSql(value);
			}
			setValue(value);
		}
	}

}
