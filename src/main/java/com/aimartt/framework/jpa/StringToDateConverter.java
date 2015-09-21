package com.aimartt.framework.jpa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.aimartt.framework.util.DateUtil;

/**
 * String to Date simple converter implement.
 * @author zhangpu
 */
public class StringToDateConverter implements Converter<String, Date> {

	public static final SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTEN_OF_DATETIME_DASH);

	private List<String> dateFormats = new ArrayList<String>();

	public StringToDateConverter() {
//		super();
		addDefaultFormats();
	}
	
	@Override
	public Date convert(String source) {
		for (String dateFormat : dateFormats) {
			sdf.applyPattern(dateFormat);
			try {
				return sdf.parse(source);
			} catch (Exception e) {
				// ignore
			}
		}
		throw new IllegalArgumentException(String.format("类型转换失败。支持格式：%s,但输入格式是[%s]", dateFormats.toString(), source));
	}

	private void addDefaultFormats() {
		dateFormats.add(DateUtil.PATTEN_OF_DATETIME_DASH);
		dateFormats.add(DateUtil.PATTEN_OF_DATE_DASH);
		dateFormats.add(DateUtil.PATTEN_OF_DATETIME_SLASH);
		dateFormats.add(DateUtil.PATTEN_OF_DATE_SLASH);
	}

}