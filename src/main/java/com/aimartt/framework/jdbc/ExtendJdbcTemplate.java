package com.aimartt.framework.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcOperations;

public interface ExtendJdbcTemplate extends JdbcOperations {

	/**
	 * <p>按可变参数获取总数据量。</p>
	 * <pre>SELECT COUNT(*) FROM 表名 WHERE 字段1 = ? AND ... 字段n = ?</pre>
	 * @param sql sql语句
	 * @param object 参数数组
	 * @return
	 */
	long queryForCount(String sql, Object... object);

	/**
	 * <p>按动态查询条件获取总数据量。</p>
	 * <p>注意：动态条件key必须在查询SQL语句中存在</p>
	 * <pre>SELECT COUNT(*) FROM 表名</pre>
	 * @param sql sql语句
	 * @param conditions 动态条件参数（按键值动态添加条件）
	 * @return
	 */
	long queryForCount(String sql, Map<String, Object> conditions);

	/**
	 * <p>按可变参数获取数据集合。</p>
	 * <ul>
	 * <li>SELECT 字段1,...,字段n FROM 表名 WHERE 字段1 = ? AND ... 字段n = ?</li>
	 * <li>SELECT * FROM 表名 WHERE 字段1 = ? AND ... 字段n = ?</li>
	 * </ul>
	 * @param transClass 返回对象类型
	 * @param sql sql语句
	 * @param object 参数数组（不存在条件，可为空）
	 * @return
	 */
	public <T> List<T> queryForList(Class<T> transClass, String sql, Object... object);

	/**
	 * <p>按动态查询条件获取数据集合。</p>
	 * <p> 注意：动态条件key必须在查询SQL语句中存在 <p>
	 * <pre>SELECT 字段1,...字段n FROM 表名</pre>
	 * @param transClass 返回对象类型
	 * @param sql sql语句
	 * @param conditions 动态条件参数（按键值动态添加条件）
	 * @return
	 */
	public <T> List<T> queryForList(Class<T> transClass, String sql, Map<String, Object> conditions);

	/**
	 * <p>按动态查询条件获取数据集合。</p>
	 * <p>注意：动态条件key必须在查询SQL语句中存在</p>
	 * <pre>SELECT 字段1,...字段n FROM 表名</pre>
	 * @param transClass 返回对象类型
	 * @param sql sql语句
	 * @param conditions 动态条件（按键值动态添加条件）
	 * @param orders 动态排序（true: 正序, false: 倒序）
	 * @return
	 */
	public <T> List<T> queryForList(Class<T> transClass, String sql, Map<String, Object> conditions,
			Map<String, Boolean> orders);

	/**
	 * <p>按可变参数条件获取数据分页数据。</p>
	 * <ul>
	 * <li>SELECT 字段1,...,字段n FROM 表名 WHERE 字段1 = ? AND ... 字段n = ?</li>
	 * <li>SELECT * FROM 表名 WHERE 字段1 = ? AND ... 字段n = ?</li>
	 * </ul>
	 * @param transClass 返回对象类型
	 * @param sql sql语句
	 * @param object 参数数组（不存在条件，可为空）
	 * @param pageable 分页对象
	 * @param object 参数
	 * @return
	 */
	public <T> Page<T> queryForPage(Class<T> transClass, String sql, Pageable pageable, Object... object);

	/**
	 * <p>按可变参数条件获取数据分页数据。</p>
	 * <ul>
	 * <li>SELECT 字段1,...,字段n FROM 表名 WHERE 字段1 = ? AND ... 字段n = ?</li>
	 * <li>SELECT * FROM 表名 WHERE 字段1 = ? AND ... 字段n = ?</li>
	 * </ul>
	 * @param transClass 返回对象类型
	 * @param sql sql语句
	 * @param pageable 分页对象
	 * @param orderBys 动态排序(排序字段格式如：user_id或userId 两种方式)
	 * @param object 参数数组（不存在条件，可为空）
	 * @return
	 */
	public <T> Page<T> queryForPage(Class<T> transClass, String sql, Pageable pageable, Map<String, Boolean> orderBys,
			Object... object);

	/**
	 * <p>按动态查询条件获取数据分页数据。</p>
	 * <p>注意：动态条件key必须在查询SQL语句中存在</p>
	 * <pre>SELECT 字段1,...字段n FROM 表名</pre>
	 * @param transClass 返回对象类型
	 * @param sql sql语句
	 * @param conditions 动态条件（按键值动态添加条件）
	 * @param pageable 分页对象
	 * @return
	 */
	public <T> Page<T> queryForPage(Class<T> transClass, String sql, Map<String, Object> conditions, Pageable pageable);

	/**
	 * <p>按动态查询条件获取数据分页数据。</p>
	 * <p>注意：动态条件key必须在查询SQL语句中存在</p>
	 * <pre>SELECT 字段1,...字段n FROM 表名</pre>
	 * @param transClass 返回对象类型
	 * @param sql sql语句
	 * @param conditions 动态条件（按键值动态添加条件）
	 * @param pageable 分页对象
	 * @param orders 动态排序（true: 正序, false: 倒序）
	 * @return
	 */
	public <T> Page<T> queryForPage(Class<T> transClass, String sql, Map<String, Object> conditions, Pageable pageable,
			Map<String, Boolean> orders);

}