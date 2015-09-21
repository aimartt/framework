package com.aimartt.framework.jpa.service.support;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aimartt.framework.exception.BusinessException;
import com.aimartt.framework.exception.GeneralException;
import com.aimartt.framework.jpa.domain.AbstractEntity;
import com.aimartt.framework.jpa.repository.EntityJpaRepository;
import com.aimartt.framework.jpa.service.EntityJpaService;
import com.aimartt.framework.util.BeanUtil;
import com.aimartt.framework.util.GenericsUtil;

/**
 * 基础EntityService的抽象实现。
 * @author zhangpu
 * @param <T> 被管理的实体类
 * @param <M> 实体类的DAO
 */
public abstract class EntityJpaServiceImpl<M extends EntityJpaRepository<T, ID>, T extends AbstractEntity<ID>, ID extends Serializable>
		implements ApplicationContextAware, EntityJpaService<T, ID> {

	private M entityDao;
	private ApplicationContext context;

	@SuppressWarnings("unchecked")
	protected M getEntityDao() throws BusinessException {
		if (entityDao != null) {
			return entityDao;
		}
		// 获取定义的第一个实例变量类型
		Class<M> daoType = GenericsUtil.getSuperClassGenricType(getClass(), 0);
		List<Field> fields = BeanUtil.getFieldsByType(this, daoType);
		try {
			if (fields != null && fields.size() > 0) {
				entityDao = (M) BeanUtil.getDeclaredProperty(this, fields.get(0).getName());
			} else {
				entityDao = (M) context.getBean(daoType);
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		return entityDao;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public T get(ID id) throws BusinessException {
		try {
			return getEntityDao().get(id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}

	}

	@Override
	public List<T> getAll() throws BusinessException {
		try {
			return getEntityDao().getAll();
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void remove(T o) throws BusinessException {
		try {
			getEntityDao().remove(o);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void removeById(ID id) throws BusinessException {
		try {
			getEntityDao().removeById(id);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}

	@Override
	public void removeByIds(Iterable<ID> ids) throws BusinessException {
		try {
			getEntityDao().removeByIds(ids);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void removeByIds(ID[] ids) throws BusinessException {
		try {
			getEntityDao().removeByIds(ids);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public T save(T o) throws BusinessException {
		try {
			return getEntityDao().save(o);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<T> save(Iterable<T> ts) throws BusinessException {
		try {
			return getEntityDao().save(ts);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public T update(T o) throws BusinessException {
		try {
			return getEntityDao().save(o);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<T> query(Pageable pageable, Map<String, Object> condions, Map<String, Boolean> orderMap)
			throws GeneralException {
		try {
			return getEntityDao().query(pageable, condions, orderMap);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<T> query(Pageable pageable, Map<String, Object> condions) throws GeneralException {
		return query(pageable, condions, null);
	}

	@Override
	public List<T> query(Map<String, Object> map, Map<String, Boolean> sortMap) {
		try {
			return getEntityDao().query(map, sortMap);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<T> query(Map<String, Object> condions) throws GeneralException {
		return query(condions, null);
	}
	
}