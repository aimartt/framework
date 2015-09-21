package com.aimartt.framework.jpa.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.aimartt.framework.jdbc.support.SearchFilter;

public interface EntityJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	/**
	 * 获取单个实体。
	 * @param id 
	 * @return 实体对象
	 */
	T get(ID id);

	/**
	 * 获取全部数据。
	 * @return 实体集合
	 */
	List<T> getAll();

	/**
	 * 删除实体。
	 * @param obj 实体对象
	 */
	void remove(T obj);

	/**
	 * 根据实体 id 删除实体。
	 * @param id 实体ID
	 */
	void removeById(ID id);

	/**
	 * 根据实体 id 集合删除实体。
	 * @param id 实体 id 集合
	 */
	void removeByIds(Iterable<ID> id);

	/**
	 * 批量删除。
	 * @param ids 实体 id 数组
	 */
	void removeByIds(ID[] ids);

	/**
	 * 多条件、多排序规则的分页查询。
	 * @param conditions 条件Map，Key--> LIKE/EQ/GT/GTE/LT/LTE_propertyName; value-->查询条件右值
	 * @param orderMap 排序Map, Key --> propertyName; value=true(ASC)/false(DESC)
	 * @return
	 * @see SearchFilter.Operator 条件关键字
	 */
	Page<T> query(Pageable pageable, Map<String, Object> conditions, Map<String, Boolean> orderMap);

	/**
	 * 多条件、多排序规则的查询。
	 * @param conditions 条件Map，Key--> LIKE/EQ/GT/GTE/LT/LTE_propertyName; value-->查询条件右值
	 * @param sortMap 排序Map, Key --> propertyName; value=true(ASC)/false(DESC)
	 * @return List<T> 查询结果：满足条件并按直接排序的实体集合
	 * @see SearchFilter.Operator 条件关键字
	 */
	List<T> query(Map<String, Object> conditions, Map<String, Boolean> orderMap);
	
}