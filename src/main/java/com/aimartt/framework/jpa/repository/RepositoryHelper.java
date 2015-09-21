package com.aimartt.framework.jpa.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.Assert;

import com.aimartt.framework.jdbc.support.SearchFilter;
import com.aimartt.framework.jpa.DynamicSpecifications;

public abstract class RepositoryHelper {
	
	private RepositoryHelper() {
	}

	public static Sort buildSort(Map<String, Boolean> sorts) {
		if (sorts == null || sorts.isEmpty()) {
			return null;
		}
		List<Order> orders = new ArrayList<Order>();
		for (Map.Entry<String, Boolean> entry : sorts.entrySet()) {
			orders.add(new Order(entry.getValue() ? Direction.ASC : Direction.DESC, entry.getKey()));
		}
		return new Sort(orders);
	}

	/**
	 * 生成查询条件
	 * @param userName
	 * @return
	 */
	public static <T> Specification<T> buildSpecification(Map<String, Object> conditions) {
		List<SearchFilter> searchFilters = SearchFilter.parse(conditions);
		return DynamicSpecifications.bySearchFilter(searchFilters, null);
	}

	public static <ID extends Serializable> Query applyAndBind(String queryString, Iterable<ID> ids,
			EntityManager entityManager) {
		Assert.notNull(queryString);
		Assert.notNull(ids);
		Assert.notNull(entityManager);
		Iterator<ID> iterator = ids.iterator();
		if (!iterator.hasNext()) {
			return entityManager.createQuery(queryString);
		}
		String alias = QueryUtils.detectAlias(queryString);
		StringBuilder builder = new StringBuilder(queryString);
		builder.append(" where");
		int i = 0;
		while (iterator.hasNext()) {
			iterator.next();
			builder.append(String.format(" %s.id = ?%d", alias, ++i));
			if (iterator.hasNext()) {
				builder.append(" or");
			}
		}
		Query query = entityManager.createQuery(builder.toString());
		iterator = ids.iterator();
		i = 0;
		while (iterator.hasNext()) {
			query.setParameter(++i, iterator.next());
		}
		return query;
	}
	
}