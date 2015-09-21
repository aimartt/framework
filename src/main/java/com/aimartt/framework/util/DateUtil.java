/**
 * 
 */
package com.aimartt.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>日期工具类。</p>
 * Copyright (c) aimartt
 * @author aimartt on 2013年5月3日
 */
public class DateUtil {
	
	/** 包含日期的模式 yyyy-MM-dd */
	public static final String PATTEN_OF_DATE_DASH = "yyyy-MM-dd";
	/** 包含日期的模式 yyyy/MM/dd */
	public static final String PATTEN_OF_DATE_SLASH = "yyyy/MM/dd";
	/** 包含日期时间的模式 yyyy-MM-dd HH:mm:ss */
	public static final String PATTEN_OF_DATETIME_DASH = "yyyy-MM-dd HH:mm:ss";
	/** 包含日期时间的模式 yyyy/MM/dd HH:mm:ss */
	public static final String PATTEN_OF_DATETIME_SLASH = "yyyy/MM/dd HH:mm:ss";
	/** 无分隔符的日期时间模式 yyyyMMddHHmmssSSS */
	public static final String PATTEN_WITH_NO_SEPARATOR = "yyyyMMddHHmmssSSS";
	
	/** 全部模式 */
	public static final String[] PARSE_PATTERNS = {
			PATTEN_OF_DATE_DASH,
			PATTEN_OF_DATE_SLASH,
			PATTEN_OF_DATETIME_DASH,
			PATTEN_OF_DATETIME_SLASH,
			PATTEN_WITH_NO_SEPARATOR
	};
	
	/** 日期的正则表达式 yyyy-MM-dd */
	public static final String REGEX_OF_DATE = "[1-2]\\d{3}-\\d{2}-\\d{2}";
	
	/**  
	 * 字符串日期转换成中文格式日期。  
	 * <ul>
	 * <li>若 <tt>date</tt> 不匹配 yyyy-MM-dd 模式，则返回 <tt>date</tt></li>
	 * <li>如 <tt>date = "2013-05-03"</tt>，将返回"二〇一三年五月三日"</li>
	 * </ul>
	 * @param date yyyy-MM-dd 模式的日期字符串
	 * @return 
	 * @category 字符串日期转换成中文格式日期
	 */  
	public static String dateToCnDate(String date) {
		if (StringUtils.isEmpty(date) || !date.matches(REGEX_OF_DATE)) {
			return date;	//若传入的参数不符合 yyyy-MM-dd 模式，则返回传入的参数
		}
	    String result = "";   
	    String[]  cnDate = {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};   
	    String ten = "十";   
	    String[] dateStr = date.split("-");   
	    for (int i = 0; i < dateStr.length; i++) {   
	        for (int j = 0; j < dateStr[i].length(); j++) {   
	            String charStr = dateStr[i];   
	            String str = String.valueOf(charStr.charAt(j));   
	            if (charStr.length() == 2) {   
	                if ("10".equals(charStr)) {   
	                    result += ten;   
	                    break;   
	                } else {   
	                    if (j == 0) {   
	                        if (charStr.charAt(j) == '1') {   
	                            result += ten;   
	                        } else if (charStr.charAt(j) == '0') {   
	                            result += "";   
	                        } else {  
	                            result += cnDate[Integer.parseInt(str)] + ten;
	                        }
	                    }   
	                    if (j == 1) {   
	                        if (charStr.charAt(j) == '0') {   
	                            result += "";   
	                        } else {  
	                            result += cnDate[Integer.parseInt(str)];
	                        }
	                    }   
	                }   
	            } else {   
	                result += cnDate[Integer.parseInt(str)];   
	            }   
	        }   
	        if (i == 0) {   
	            result += "年";   
	            continue;   
	        }   
	        if (i == 1) {   
	            result += "月";   
	            continue;   
	        }   
	        if (i == 2) {   
	            result += "日";   
	            continue;   
	        }   
	    }   
	    return result;   
	}  
	
	/**
	 * 将 <tt>Date</tt> 对象转换为 <tt>Sting</tt> 对象，匹配模式为 yyyy-MM-dd HH:mm:ss。
	 * <ul>
	 * <li>若 <tt>date</tt> 为 <tt>null</tt> 或发生异常，则返回空字符串</li>
	 * </ul>
	 * @param date <tt>Date</tt> 对象
	 * @return
	 * @category Date 对象转换为 String 对象
	 */
	public static String dateToStr(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(PATTEN_OF_DATETIME_DASH);
		try {
			String dateString = format.format(date); 
			return dateString;
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 将 <tt>Date</tt> 对象转换为 <tt>Sting</tt> 对象，匹配模式为 <tt>pattern</tt>。<br />
	 * <ul>
	 * <li>若 <tt>pattern</tt> 为 <tt>null</tt> 或空字符串，则匹配模式为 yyyy-MM-dd HH:mm:ss</li>
	 * <li>若 <tt>date</tt> 为 <tt>null</tt> 或发生异常，则返回空字符串</li>
	 * </ul>
	 * @param date <tt>Date</tt> 对象
	 * @param pattern 匹配模式
	 * @return
	 * @category Date 对象转换为 String 对象
	 */
	public static String dateToStr(Date date,String pattern) {
		if (date == null) {
			return "";
		}
		if (StringUtils.isEmpty(pattern)) {
			pattern = PATTEN_OF_DATETIME_DASH;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			String dateString = format.format(date); 
			return dateString;
		} catch (Exception e) {
			return "";
		}
	}
	
	private DateUtil() {
	}
	
}