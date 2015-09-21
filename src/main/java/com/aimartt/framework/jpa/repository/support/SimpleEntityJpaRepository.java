package com.aimartt.framework.jpa.repository.support;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.aimartt.framework.jpa.repository.EntityJpaRepository;
import com.aimartt.framework.jpa.repository.RepositoryHelper;

/**
 * @author zhangpu
 * @param <T>
 * @param <ID>
 */
@Transactional(readOnly = true)
public class SimpleEntityJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements
		EntityJpaRepository<T, ID> {

	private Class<T> domainClass;
	private EntityManager entityManager;

	public SimpleEntityJpaRepository(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.domainClass = domainClass;
		this.entityManager = entityManager;
	}

	@Override
	public T get(ID id) throws DataAccessException {
		return findOne(id);
	}

	@Override
	@Transactional
	public void remove(T o) throws DataAccessException {
		delete(o);
	}

	@Override
	@Transactional
	public void removeById(ID id) throws DataAccessException {
		delete(id);
	}

	@Override
	@Transactional
	public void removeByIds(Iterable<ID> ids) {
		Assert.notNull(ids, "The given Iterable of ids not be null!");
		if (!ids.iterator().hasNext()) {
			return;
		}
		String queryString = QueryUtils.getQueryString(QueryUtils.DELETE_ALL_QUERY_STRING, getEntityName());
		Query q = RepositoryHelper.applyAndBind(queryString, ids, getEntityManager());
		q.executeUpdate();
	}

	@Override
	@Transactional
	public void removeByIds(ID[] ids) {
		Assert.notNull(ids, "The given Array of ids not be null!");
		if (ids.length < 1) {
			return;
		}
		removeByIds(Arrays.asList(ids));
	}

	@Override
	public List<T> getAll() throws DataAccessException {
		return findAll();
	}

	@Override
	public List<T> query(Map<String, Object> conditions, Map<String, Boolean> orderMap) {
		Specification<T> spec = RepositoryHelper.buildSpecification(conditions);
		Sort sort = RepositoryHelper.buildSort(orderMap);
		List<T> t = findAll(spec, sort);
		return t;
	}

	@Override
	public Page<T> query(Pageable pageable, Map<String, Object> conditions, Map<String, Boolean> orderMap)
			throws DataAccessException {
		Specification<T> spec = RepositoryHelper.buildSpecification(conditions);
		Sort sort = RepositoryHelper.buildSort(orderMap);
		Pageable newPageable = null;
		if (sort == null) {
			newPageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize());
		} else {
			newPageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
		Page<T> page = findAll(spec, newPageable);
		return page;
	}

	@Override
	protected Class<T> getDomainClass() {
		return domainClass;
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	protected String getEntityName() {
		return domainClass.getSimpleName();
	}

}