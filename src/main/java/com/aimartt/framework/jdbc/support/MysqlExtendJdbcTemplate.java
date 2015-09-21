package com.aimartt.framework.jdbc.support;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aimartt.framework.jdbc.ExtendJdbcTemplate;

public class MysqlExtendJdbcTemplate extends JdbcTemplate implements ExtendJdbcTemplate {

	static final Logger logger = Logger.getLogger(MysqlExtendJdbcTemplate.class);

	static final String PAGE_QUERY_STRING = " %s limit %d, %d ";

	@Override
	public long queryForCount(String sql, Object... object) {
		Number number = queryForObject(sql, Long.class, object);
		return (number != null ? number.longValue() : 0);
	}

	@Override
	public long queryForCount(String sql, Map<String, Object> conditions) {
		QueryCondition countQuery = QueryCondition.createCountQuery(sql, conditions);
		return queryForCount(countQuery.sql, countQuery.params);
	}

	@Override
	public <T> List<T> queryForList(Class<T> transClass, String sql, Object... object) {
		return super.query(sql, object, BeanPropertyRowMapper.newInstance(transClass));
	}

	@Override
	public <T> List<T> queryForList(Class<T> transClass, String sql, Map<String, Object> conditions) {
		return queryForList(transClass, sql, conditions, null);
	}

	@Override
	public <T> List<T> queryForList(Class<T> transClass, String sql, Map<String, Object> conditions,
			Map<String, Boolean> orders) {
		QueryCondition result = QueryCondition.createResultQuery(sql, conditions, orders);
		return queryForList(transClass, result.sql, result.params);
	}

	@Override
	public <T> Page<T> queryForPage(Class<T> transClass, String sql, Pageable pageable, Object... object) {
		return queryForPage(transClass, sql, pageable, null, object);
	}

	@Override
	public <T> Page<T> queryForPage(Class<T> transClass, String sql, Pageable pageable, Map<String, Boolean> orderBys,
			Object... object) {
		QueryCondition count = QueryCondition.createCountQuery(sql, object);
		long total = queryForCount(count.sql, count.params);
		if (total < 1) {
			return new PageImpl<T>(Collections.<T>emptyList());
		}

		if (orderBys != null && !orderBys.isEmpty()) {
			StringBuilder sqlbuilder = new StringBuilder(sql);
			QueryCondition.orderBy(sqlbuilder, orderBys);
			sql = sqlbuilder.toString();
		}
		sql = pageableQuery(sql, pageable);
		List<T> content = queryForList(transClass, sql, object);
		return new PageImpl<T>(content, pageable, total);
	}

	@Override
	public <T> Page<T> queryForPage(Class<T> transClass, String sql, Map<String, Object> conditions, Pageable pageable) {
		return queryForPage(transClass, sql, conditions, pageable, null);
	}

	@Override
	public <T> Page<T> queryForPage(Class<T> transClass, String sql, Map<String, Object> conditions, Pageable pageable,
			Map<String, Boolean> orders) {
		QueryCondition count = QueryCondition.createCountQuery(sql, conditions);
		long total = queryForCount(count.sql, count.params);
		if (total < 1) {
			return new PageImpl<T>(Collections.<T> emptyList());
		}

		QueryCondition result = QueryCondition.createResultQuery(sql, conditions, orders);
		sql = pageableQuery(result.sql, pageable);
		List<T> content = queryForList(transClass, sql, result.params);
		return new PageImpl<T>(content, pageable, total);
	}

	protected String pageableQuery(String sql, Pageable pageable) {
		int page = pageable.getPageNumber();
		int size = pageable.getPageSize();
		return String.format(PAGE_QUERY_STRING, sql, (page * size), size);
	}

}