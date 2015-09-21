package com.aimartt.framework.jpa.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aimartt.framework.exception.BusinessException;
import com.aimartt.framework.exception.GeneralException;
import com.aimartt.framework.jdbc.support.SearchFilter;
import com.aimartt.framework.jpa.domain.AbstractEntity;

/**
 * 针对单个Entity对象的操作定义. 不依赖于具体ORM实现方案.
 * @author zhangpu
 */
public interface EntityJpaService<T extends AbstractEntity<ID>, ID extends Serializable> {

	/**
	 * 根据ID获得单个对象
	 * @param id
	 * @return
	 * @throws GeneralException
	 */
	T get(ID id) throws GeneralException;

	/**
	 * 获得对象所有集合
	 * @return
	 * @throws GeneralException
	 */
	List<T> getAll() throws GeneralException;

	/**
	 * 保存对象
	 * @param o
	 * @throws BusinessException
	 */
	T save(T t) throws BusinessException;

	/**
	 * 批量保存对象
	 * @param o
	 * @throws BusinessException
	 */
	List<T> save(Iterable<T> ts) throws BusinessException;

	/**
	 * 更新对象
	 * @param t
	 * @throws BusinessException
	 */
	T update(T t) throws BusinessException;

	/**
	 * 删除对象
	 * @param t
	 * @throws Exception
	 */
	void remove(T t) throws BusinessException;

	/**
	 * 根据ID删除对象
	 * @param id
	 * @throws Exception
	 */
	void removeById(ID id) throws BusinessException;

	/**
	 * 按ID集合批量删除对象
	 * @param ids
	 * @throws BusinessException
	 */
	void removeByIds(Iterable<ID> ids) throws BusinessException;

	/**
	 * 按ID集合批量删除对象
	 * @param ids
	 * @throws BusinessException
	 */
	void removeByIds(ID[] ids) throws BusinessException;

	/**
	 * 多条件、多排序规则的分页查询
	 * @param pageable
	 * @param condions propertyName([EQ/LIKE...]_propertyName) --> value
	 * @param orderMap propertyName --> true:asc,false:desc
	 * @return
	 * @throws GeneralException
	 * @see SearchFilter.Operator 条件关键字
	 */
	Page<T> query(Pageable pageable, Map<String, Object> condions, Map<String, Boolean> orderMap)
			throws GeneralException;

	/**
	 * 多条件规则的分页查询
	 * @param pageable 
	 * @param conditions 条件Map，Key--> LIKE/EQ/GT/GTE/LT/LTE_propertyName; value-->查询条件右值
	 * @return
	 * @throws GeneralException
	 * @see SearchFilter.Operator 条件关键字
	 */
	Page<T> query(Pageable pageable, Map<String, Object> conditions) throws GeneralException;

	/**
	 * <p>多条件、多排序规则的查询。</p>
	 * @param conditions 条件Map，Key--> LIKE/EQ/GT/GTE/LT/LTE_propertyName; value-->查询条件右值
	 * @param orderMap 排序Map, Key --> propertyName; value=true(ASC)/false(DESC)
	 * @return
	 * @throws GeneralException
	 * @see SearchFilter.Operator 条件关键字
	 */
	List<T> query(Map<String, Object> conditions, Map<String, Boolean> orderMap) throws GeneralException;

	/**
	 * <p>多条件规则的查询。</p>
	 * @param conditions 条件Map，Key--> LIKE/EQ/GT/GTE/LT/LTE_propertyName; value-->查询条件右值
	 * @return
	 * @throws GeneralException
	 * @see SearchFilter.Operator 条件关键字
	 */
	List<T> query(Map<String, Object> conditions) throws GeneralException;

}