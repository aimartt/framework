package com.aimartt.framework.jdbc.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

class QueryCondition {

	final static String QUERY_STRING = " select * from (%s) x where 1 = 1 ";
	final static String COUNT_QUERY_STRING = " select count(*) from (%s) x ";
	final static String COUNT_QUERY_STRING2 = " select count(*) from (%s) x where 1 = 1 ";

	public final String sql;
	public final Object[] params;

	QueryCondition(String sql, Object[] params) {
		this.sql = sql;
		this.params = params;
	}

	/**
	 * <p></p>
	 * @param sql
	 * @param conditions
	 * @return
	 * @author aimartt on 2015年9月21日
	 */
	public static QueryCondition createResultQuery(String sql, Map<String, Object> conditions) {
		return createResultQuery(sql, conditions, null);
	}

	/**
	 * <p></p>
	 * @param sql
	 * @param conditions
	 * @param orderBys
	 * @return
	 * @author aimartt on 2015年9月21日
	 */
	public static QueryCondition createResultQuery(String sql, Map<String, Object> conditions,
			Map<String, Boolean> orderBys) {
		String countQuery = String.format(QUERY_STRING, sql);
		return createQuery(countQuery, conditions, orderBys);
	}

	/**
	 * <p></p>
	 * @param sql
	 * @param objects
	 * @return
	 * @author aimartt on 2015年9月21日
	 */
	public static QueryCondition createCountQuery(String sql, Object... objects) {
		String countQuery = String.format(COUNT_QUERY_STRING, sql);
		return new QueryCondition(countQuery, objects);
	}

	/**
	 * <p></p>
	 * @param sql
	 * @param conditions
	 * @return
	 * @author aimartt on 2015年9月21日
	 */
	public static QueryCondition createCountQuery(String sql, Map<String, Object> conditions) {
		String countQuery = String.format(COUNT_QUERY_STRING2, sql);
		return createQuery(countQuery, conditions);
	}

	/**
	 * <p></p>
	 * @param sql
	 * @param conditions
	 * @return
	 * @author aimartt on 2015年9月21日
	 */
	public static QueryCondition createQuery(String sql, Map<String, Object> conditions) {
		return createQuery(sql, conditions, null);
	}

	private static QueryCondition createQuery(String sql, Map<String, Object> conditions, Map<String, Boolean> orderBys) {
		StringBuilder querysql = new StringBuilder(sql);
		List<SearchFilter> filters = SearchFilter.parse(conditions);
		List<Object> params = Collections.emptyList();
		if (filters != null && !filters.isEmpty()) {
			params = new ArrayList<Object>();
			filterCondions(filters, querysql, params);
		}
		if (orderBys != null && !orderBys.isEmpty()) {
			orderBy(querysql, orderBys);
		}
		return new QueryCondition(querysql.toString(), params.toArray());
	}

	/**
	 * 添加查询过滤条件
	 * @param filters
	 * @param sql
	 * @param params
	 */
	private static void filterCondions(List<SearchFilter> filters, StringBuilder querysql, Collection<Object> params) {
		for (SearchFilter filter : filters) {
			switch (filter.operator) {
				case EQ:
					querysql.append(" AND ").append(filter.originalFieldName).append(" = ? ");
					params.add(filter.value);
					break;
				case NOTEQ:
					querysql.append(" AND ").append(filter.originalFieldName).append(" <> ? ");
					params.add(filter.value);
					break;
				case LIKE:
					querysql.append(" AND ").append(filter.originalFieldName).append(" like ? ");
					params.add("%" + filter.value + "%");
					break;
				case LLIKE:
					querysql.append(" AND ").append(filter.originalFieldName).append(" like ? ");
					params.add("%" + filter.value);
					break;
				case RLIKE:
					querysql.append(" AND ").append(filter.originalFieldName).append(" LIKE ? ");
					params.add(filter.value + "%");
					break;
				case NLIKE:
					querysql.append(" AND ").append(filter.originalFieldName).append(" NOT LIKE ? ");
					params.add(filter.value + "%");
					break;
				case GT:
					querysql.append(" AND ").append(filter.originalFieldName).append(" > ? ");
					params.add(filter.value);
					break;
				case LT:
					querysql.append(" AND ").append(filter.originalFieldName).append(" < ? ");
					params.add(filter.value);
					break;
				case GTE:
					querysql.append(" AND ").append(filter.originalFieldName).append(" >= ? ");
					params.add(filter.value);
					break;
				case LTE:
					querysql.append(" AND ").append(filter.originalFieldName).append(" <= ? ");
					params.add(filter.value);
					break;
				case NULL:
					querysql.append(" AND ").append(filter.originalFieldName).append(" IS NULL ");
					break;
				case NOTNULL:
					querysql.append(" AND ").append(filter.originalFieldName).append(" IS NOT NULL ");
					break;
				case IN:
					if (filter.value instanceof Collection) {
						querysql.append(" AND ").append(filter.originalFieldName).append(" IN ( ");
						Collection<?> cs = (Collection<?>) filter.value;
						for (int i = 0; cs != null && i < cs.size(); i++) {
							querysql.append(" ? ");
							if (i + 1 < cs.size()) {
								querysql.append(",");
							}
						}
						querysql.append(" ) ");
						params.addAll(cs);
					} else if (filter.value instanceof Object[]) {
						querysql.append(" AND ").append(filter.originalFieldName).append(" IN ( ");
						Object[] cs = (Object[]) filter.value;
						for (int i = 0; cs != null && i < cs.length; i++) {
							querysql.append(" ? ");
							if (i + 1 < cs.length) {
								querysql.append(",");
							}
						}
						querysql.append(" ) ");
						params.addAll(Arrays.asList(cs));
					} else {
						querysql.append(" AND ").append(filter.originalFieldName).append(" IN ( ? ) ");
						params.add(filter.value);
					}
					break;
				case NOTIN:
					if (filter.value instanceof Collection) {
						querysql.append(" AND ").append(filter.originalFieldName).append(" NOT IN ( ");
						Collection<?> cs = (Collection<?>) filter.value;
						for (int i = 0; cs != null && i < cs.size(); i++) {
							querysql.append(" ? ");
							if (i + 1 < cs.size()) {
								querysql.append(",");
							}
						}
						querysql.append(" ) ");
						params.addAll(cs);
					} else if (filter.value instanceof Object[]) {
						querysql.append(" AND ").append(filter.originalFieldName).append(" NOT IN ( ");
						Object[] cs = (Object[]) filter.value;
						for (int i = 0; cs != null && i < cs.length; i++) {
							querysql.append(" ? ");
							if (i + 1 < cs.length) {
								querysql.append(",");
							}
						}
						querysql.append(" ) ");
						params.addAll(Arrays.asList(cs));
					} else {
						querysql.append(" AND ").append(filter.originalFieldName).append(" NOT IN ( ? ) ");
						params.add(filter.value);
					}
					break;
				default:
					throw new NullPointerException("查询方式未定义!");
			}
		}
	}

	/**
	 * 排序
	 * @param sql
	 * @param orderBys
	 */
	static void orderBy(StringBuilder querysql, Map<String, Boolean> orderBys) {
		querysql.append(" order by ");
		int i = 1;
		for (String key : orderBys.keySet()) {
			boolean orders = orderBys.get(key);
			key = SearchFilter.coverdColumnName(key);
			querysql.append(key).append(orders ? " asc " : " desc ");
			if (i < orderBys.size()) {
				querysql.append(",");
			}
			i++;
		}
	}

	/**
	 * 去除select 子句，未考虑union的情况
	 */
	static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除orderby 子句
	 */
	static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
}