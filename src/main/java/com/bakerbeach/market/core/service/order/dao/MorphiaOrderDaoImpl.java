package com.bakerbeach.market.core.service.order.dao;

import org.mongodb.morphia.Datastore;

import com.bakerbeach.market.core.service.order.model.XOrderImpl;

public class MorphiaOrderDaoImpl extends AbstractMorphiaOrderDao<XOrderImpl> {

	protected MorphiaOrderDaoImpl(Datastore datastore, String orderCollection)
			throws Exception {
		super(XOrderImpl.class, datastore, orderCollection);
	}

}
