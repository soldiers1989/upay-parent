package com.dubhe.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateConvertUtils {
	public static Date parse(String dateString, String dateFormat) {
		return parse(dateString, dateFormat, Date.class);
	}

	public static <T extends Date> T parse(String dateString,
			String dateFormat, Class<T> targetResultType) {
		if (StringUtils.isEmpty(dateString))
			return null;
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			long time = df.parse(dateString).getTime();
			Date t = (Date) targetResultType.getConstructor(
					new Class[] { Long.TYPE }).newInstance(
					new Object[] { Long.valueOf(time) });
			return (T) t;
		} catch (ParseException e) {
			String errorInfo = "cannot use dateformat:" + dateFormat
					+ " parse datestring:" + dateString;
			throw new IllegalArgumentException(errorInfo, e);
		} catch (Exception e) {
			throw new IllegalArgumentException("error targetResultType:"
					+ targetResultType.getName(), e);
		}
	}

	public static String format(Date date, String dateFormat) {
		if (date == null)
			return null;
		return new SimpleDateFormat(dateFormat).format(date);
	}

	/**
	 * 返回当前日期的下一天
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 * @throws java.text.ParseException
	 */
	public static String nextDay(String value, String dateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(value));
		calendar.roll(Calendar.DATE, 1);
		return sdf.format(calendar.getTime());
	}
}