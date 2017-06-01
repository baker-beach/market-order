package com.bakerbeach.market.core.service.order.dao;

import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.bakerbeach.market.core.service.order.model.OrderListImpl;
import com.bakerbeach.market.order.api.model.Order;
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

	public AbstractMorphiaOrderDao(Class<O> orderClass, Datastore datastore, String orderCollectionName) {
		this.datastore = datastore;
		this.orderClass = orderClass;
		this.orderCollectionName = orderCollectionName;
	}

	// @Override
	public void save(Order order) {
		((AdvancedDatastore) datastore).save(orderCollectionName, order);
	}

	@Override
	public void saveOrUpdateOrder(Order order) throws OrderDaoException {
		((AdvancedDatastore) datastore).save(orderCollectionName, order);
	}

	@Override
	public Order findById(String id) throws OrderDaoException {
		Query<O> query = ((AdvancedDatastore) datastore).createQuery(orderCollectionName, orderClass).field("id")
				.equal(id);
		O order = query.get();
		return order;
	}

	@Override
	public OrderList findByCustomerIdAndShop(String customerId, String shopCode, String orderBy, Integer limit,
			Integer offset) throws OrderDaoException {

		Query<O> query = ((AdvancedDatastore) datastore).createQuery(orderCollectionName, orderClass)
				.field("customerId").equal(customerId).field("shopCode").equal(shopCode);

		if (orderBy != null) {
			query.order(orderBy);
		} else {
			query.order("-order_id");
		}

		if (limit != null)
			query.limit(limit);

		if (offset != null)
			query.offset(offset);	
			
		OrderListImpl orderList = new OrderListImpl();
		query.forEach(order -> {
			orderList.add(order);
		});

		orderList.setCount(query.countAll());

		return orderList;
	}
	
	@Override
	public OrderList findByStatusAndShop(String status, String shopCode, String orderBy, Integer limit, Integer offset) throws OrderDaoException {

		Query<O> query = ((AdvancedDatastore) datastore).createQuery(orderCollectionName, orderClass)
				.field("shopCode").equal(shopCode).field("status").equal(status);

		if (orderBy != null) {
			query.order(orderBy);
		} else {
			//query.order("-order_id");
		}

		if (limit != null)
			query.limit(limit);

		if (offset != null)
			query.offset(offset);	
			
		OrderListImpl orderList = new OrderListImpl();
		query.forEach(order -> {
			orderList.add(order);
		});

		orderList.setCount(query.countAll());

		return orderList;
	}
	
	@Deprecated
	@Override
	public OrderList findByCustomerId(String customerId, String shopCode, DBObject orderBy, Integer limit,
			Integer offset) throws OrderDaoException {
		
		if (orderBy != null) {
			StringBuilder sort = new StringBuilder();
			for (String key : orderBy.keySet()) {
				if (sort.length() > 0) {
					sort.append(",");
				}
				
				Object value = orderBy.get(key);
				if (value.equals("1")) {
					sort.append("-");
				}
				sort.append(key);				
			}
			return findByCustomerIdAndShop(customerId, shopCode, sort.toString(), limit, offset);
		} else {
			return findByCustomerIdAndShop(customerId, shopCode, null, limit, offset);			
		}
	}

	@Override
	public Order newInstance() throws InstantiationException, IllegalAccessException {
		return orderClass.newInstance();
	}

}
