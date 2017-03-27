package com.bakerbeach.market.core.service.order.service;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.bakerbeach.market.core.api.model.Order;
import com.bakerbeach.market.core.service.order.dao.OrderDao;
import com.bakerbeach.market.core.service.order.dao.OrderDaoException;
import com.bakerbeach.market.core.service.order.model.OrderListImpl;
import com.bakerbeach.market.core.service.order.model.SimpleOrder;
import com.bakerbeach.market.order.api.model.OrderList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

public class OrderDaoMongoImpl implements OrderDao {

	private MongoTemplate mongoShopTemplate;
	
	private String collectionName;
	
	@Override
	public void saveOrUpdateOrder(Order order) throws OrderDaoException {
		QueryBuilder qb = QueryBuilder.start();
		qb.and(OrderMongoConverter.KEY_ORDER_ID).is(order.getId());
		
		getDBCollection().update(qb.get(),OrderMongoConverter.encode(order), true, false);	
	}

	@Override
	public SimpleOrder findById(String id) throws OrderDaoException {
		QueryBuilder qb = QueryBuilder.start();
		qb.and(OrderMongoConverter.KEY_ORDER_ID).is(id);
		DBObject obj = getDBCollection().findOne(qb.get());
		return OrderMongoConverter.decode(obj);

	}

	@Override
	public OrderList findByCustomerId(String customerId, String shopCode, DBObject orderBy, Integer limit, Integer offset) throws OrderDaoException {

		try {
			OrderListImpl orderList = new OrderListImpl();
			QueryBuilder qb = QueryBuilder.start();
			qb.and("customer_id").is(customerId);
			qb.and("shop_code").is(shopCode);
			DBCursor cur = getDBCollection().find(qb.get(), null);
			
			if (orderBy != null) {
				cur.sort(orderBy);
			} else {
				cur.sort(new BasicDBObject("order_id", -1));				
			}

			if (limit != null)
				cur.limit(limit);

			if (offset != null)
				cur.skip(offset);

			while (cur.hasNext()) {
				orderList.add(OrderMongoConverter.decode(cur.next()));
			}

			orderList.setCount(orderList.size());
			return orderList;
		} catch (Exception e) {
			throw new OrderDaoException();
		}

	}

	public MongoTemplate getMongoShopTemplate() {
		return mongoShopTemplate;
	}

	public void setMongoShopTemplate(MongoTemplate mongoShopTemplate) {
		this.mongoShopTemplate = mongoShopTemplate;
	}

	protected DBCollection getDBCollection() {
		return mongoShopTemplate.getCollection(collectionName);
	}
	
	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	@Override
	public Order newInstance() throws InstantiationException, IllegalAccessException {
		throw new RuntimeException("not implemented");
	}

}
