package com.bakerbeach.market.core.service.order.model;

import java.util.ArrayList;
import java.util.List;

import com.bakerbeach.market.order.api.model.Order;
import com.bakerbeach.market.order.api.model.OrderList;

@SuppressWarnings("serial")
public class OrderListImpl extends ArrayList<Order> implements OrderList {
    private Long count;

    @Override
    public Long getCount() {
        return count;
    }

    @Override
    public void setCount(Long count) {
        this.count = count;
    }
    
    public OrderListImpl(){
    	super();
    }
    
    public OrderListImpl(List<Order> list){
    	super(list);
    }

	@Override
	public List<Order> getOrders() {
		return this;
	}

}