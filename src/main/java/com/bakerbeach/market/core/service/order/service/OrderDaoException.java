package com.bakerbeach.market.core.service.order.service;

public class OrderDaoException extends Exception {
	private static final long serialVersionUID = 1L;

	private String key;
	private Object[] objects;

	public OrderDaoException() {
		super();
	}

	public OrderDaoException(String key, Object... objects) {
		super();
		this.key = key;
		this.objects = objects;
	}

	public String getKey() {
		return key;
	}

	public Object[] getObjects() {
		return objects;
	}

	@Override
	public String getMessage() {
		return new StringBuilder(getClass().getSimpleName()).append(" - ")
				.append(getKey()).toString();
	}

}
