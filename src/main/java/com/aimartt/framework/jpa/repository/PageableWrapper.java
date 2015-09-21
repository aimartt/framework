package com.aimartt.framework.jpa.repository;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableWrapper implements Pageable, Serializable {

	private static final long serialVersionUID = -7050346166988152126L;

	private final Pageable pageable;

	public PageableWrapper(Pageable pageable) {
		this(pageable, pageable.getSort());
	}

	public PageableWrapper(Pageable pageable, Sort sort) {
		System.out.println(String.format("%d,%d", pageable.getPageNumber(), pageable.getPageSize()));
		this.pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
	}

	@Override
	public int getPageNumber() {
		return pageable.getPageNumber();
	}

	@Override
	public int getPageSize() {
		return pageable.getPageSize();
	}

	@Override
	public int getOffset() {
		return pageable.getOffset();
	}

	@Override
	public Sort getSort() {
		return pageable.getSort();
	}

	@Override
	public Pageable next() {
		return pageable.next();
	}

	@Override
	public Pageable previousOrFirst() {
		return pageable.previousOrFirst();
	}

	@Override
	public Pageable first() {
		return pageable.first();
	}

	@Override
	public boolean hasPrevious() {
		return pageable.hasPrevious();
	}

}