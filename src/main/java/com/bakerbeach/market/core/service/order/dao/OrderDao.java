package com.bakerbeach.market.core.service.order.dao;

import java.util.Map;

import com.bakerbeach.market.order.api.model.Order;
import com.bakerbeach.market.order.api.model.OrderList;
import com.mongodb.DBObject;

public interface OrderDao {

	void saveOrUpdateOrder(Order order) throws OrderDaoException;

	Order findById(String id) throws OrderDaoException;

	@Deprecated
	OrderList findByCustomerId(String customerId, String shopCode, DBObject orderBy, Integer limit, Integer offset)
			throws OrderDaoException;

	OrderList findByCustomerIdAndShop(String customerId, String shopCode, String orderBy, Integer limit, Integer offset)
			throws OrderDaoException;

	Order newInstance() throws InstantiationException, IllegalAccessException;

	OrderList findByStatusAndShop(String status, String shopCode, String orderBy, Integer limit, Integer offset) throws OrderDaoException;
	
	OrderList findByFilters(Map<String,Object> filters, String orderBy, Integer limit, Integer offset) throws OrderDaoException;

}