package com.bakerbeach.market.core.service.order.dao;

import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.bakerbeach.market.core.api.model.Order;
import com.bakerbeach.market.core.service.order.model.SimpleOrder;
import com.bakerbeach.market.order.api.model.OrderList;
import com.mongodb.DBObject;

public abstract class AbstractMorphiaOrderDao<O extends Order> implements OrderDao {
	protected Morphia morphia = new Morphia();
	protected Datastore datastore;

	protected String uri;
	protected String dbName;
	protected String orderCollectionName;
	protected String packages;

	protected Class<O> orderClass;

	public AbstractMorphiaOrderDao(Class<O> orderClass, Datastore datastore,
			String orderCollectionName) {
		this.datastore = datastore;
		this.orderClass = orderClass;
		this.orderCollectionName = orderCollectionName;
	}

//	@Override
	public void save(Order order) {
		((AdvancedDatastore) datastore).save(orderCollectionName, order);
	}
	
	@Override
	public void saveOrUpdateOrder(Order order) throws OrderDaoException {
		((AdvancedDatastore) datastore).save(orderCollectionName, order);
	}
	
	@Override
	public SimpleOrder findById(String id) throws OrderDaoException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public OrderList findByCustomerId(String customerId, String shopCode, DBObject orderBy, Integer limit,
			Integer offset) throws OrderDaoException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Order newInstance() throws InstantiationException, IllegalAccessException {
		return orderClass.newInstance();
	}

}
