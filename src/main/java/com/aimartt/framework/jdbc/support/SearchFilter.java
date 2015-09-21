package com.aimartt.framework.jdbc.support;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;

import com.aimartt.framework.exception.BusinessException;
import com.google.common.collect.Lists;

public class SearchFilter {

	public final String fieldName;
	public final String originalFieldName;
	public Object value;
	public final Object originalValue;
	public final Operator operator;
	
	/**
	 * <p>覆盖字段名称，将驼峰式的字符串替换为下划线分隔的字符串。</p>
	 * <p>如：userName 将返回 user_name</p>
	 * @param columnName 字段名称
	 * @return
	 */
	public static String coverdColumnName(String columnName) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < columnName.length(); i++) {
			char c = columnName.charAt(i);
			if (!Character.isLowerCase(c) && c != 46 && c != 95) {
				str.append((char) 95).append((char) (c + 32));
			} else {
				str.append(c);
			}
		}
		return str.toString();
	}

	/**
	 * <p>解析查询条件</p>
	 * @param searchParams
	 * @return
	 */
	public static List<SearchFilter> parse(Map<String, Object> searchParams) {
		List<SearchFilter> filters = Lists.newArrayList();
		if (searchParams == null) {
			return Collections.emptyList();
		}
		for (Entry<String, Object> entry : searchParams.entrySet()) {
			String key = entry.getKey();
			if (!StringUtils.contains(key, '_')) {
				throw new BusinessException("查询条件不符合规范!");
			}
			int index = StringUtils.indexOfAny(key, "_");
			String[] names = StringUtils.split(key, "_", (index + 1));
			if (names.length < 2) {
				throw new BusinessException("查询条件截取出错!");
			}
			Operator op = Operator.getOpeartor(names[0]);
			if (op == null) {
				throw new BusinessException("查询方式未定义!");
			}
			String filedName = names[1];
			Object value = entry.getValue();
			if (value == null || StringUtils.isBlank(String.valueOf(value))) {
				continue;
			}
			filters.add(new SearchFilter(filedName, coverdColumnName(filedName), op, value));
		}
		return filters;
	}

	public SearchFilter(String fieldName, String originalFieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.originalFieldName = originalFieldName;
		this.value = value;
		this.originalValue = value;
		this.operator = operator;
	}

	/**
	 * <p>条件操作符枚举</p>
	 */
	public enum Operator {
		/** Equal */
		EQ,
		/** Not equal */
		NOTEQ,
		/** Like */
		LIKE,
		/** Left like */
		LLIKE,
		/** Right like */
		RLIKE,
		/** Not like */
		NLIKE,
		/** Great than */
		GT,
		/** Less than */
		LT,
		/** Great than or equal */
		GTE,
		/** Less than or equal */
		LTE,
		/** In */
		IN,
		/** Not in */
		NOTIN,
		/** Is null */
		NULL,
		/** Is not null */
		NOTNULL;
	
		public static Operator getOpeartor(String name) {
			for (Operator op : Operator.values()) {
				if (op.toString().equals(name)) {
					return op;
				}
			}
			return null;
		}
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}