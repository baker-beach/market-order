package com.bakerbeach.market.core.service.order.dao;

import com.bakerbeach.market.core.api.model.Order;
import com.bakerbeach.market.core.service.order.model.SimpleOrder;
import com.bakerbeach.market.order.api.model.OrderList;
import com.mongodb.DBObject;

public interface OrderDao {

	void saveOrUpdateOrder(Order order) throws OrderDaoException;

	SimpleOrder findById(String id) throws OrderDaoException;

	OrderList findByCustomerId(String customerId, String shopCode, DBObject orderBy, Integer limit, Integer offset) throws OrderDaoException;

	Order newInstance() throws InstantiationException, IllegalAccessException;

}