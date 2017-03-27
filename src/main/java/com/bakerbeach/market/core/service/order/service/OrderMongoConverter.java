package com.bakerbeach.market.core.service.order.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bakerbeach.market.core.api.model.Address;
import com.bakerbeach.market.core.api.model.Order;
import com.bakerbeach.market.core.api.model.OrderItem;
import com.bakerbeach.market.core.api.model.OrderItem.OrderItemComponent;
import com.bakerbeach.market.core.api.model.OrderItem.OrderItemOption;
import com.bakerbeach.market.core.api.model.TaxCode;
import com.bakerbeach.market.core.api.model.Text;
import com.bakerbeach.market.core.service.order.model.OrderAddress;
import com.bakerbeach.market.core.service.order.model.SimpleOrder;
import com.bakerbeach.market.core.service.order.model.SimpleOrderItem;
import com.bakerbeach.market.core.service.order.model.SimpleOrderItem.SimpleOrderItemComponent;
import com.bakerbeach.market.core.service.order.model.SimpleOrderItem.SimpleOrderItemOption;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class OrderMongoConverter {

	public static final String KEY_ORDER_ID = "order_id";

	protected static final Logger log = Logger.getLogger(OrderMongoConverter.class.getName());

	public static SimpleOrder decode(DBObject source) {

		SimpleOrder order = new SimpleOrder();

		for (String key : source.keySet()) {
			if (key.equals("shipping_address")) {
				order.setShippingAddress(decodeAddress((DBObject) source.get(key)));
			} else if (key.equals("billing_address")) {
				order.setBillingAddress(decodeAddress((DBObject) source.get(key)));
			} else if (key.equals("order_item_list")) {
				for (Object item : ((BasicDBList) source.get(key))) {
					DBObject tmpItem = (DBObject) item;
					SimpleOrderItem orderItem = new SimpleOrderItem();
					for (String itemKey : tmpItem.keySet()) {
						Object value = tmpItem.get(itemKey);
						if (value instanceof Double) {
							orderItem.put(itemKey, new BigDecimal((Double) value));
						} 
						else if (itemKey.equals("options")) {
							DBObject dbObject = (DBObject) value;
							
						}
						
						
						else if (itemKey.equals("components")) {
							DBObject dbObject = (DBObject) value;
							for (String componentKey : dbObject.keySet()) {
								DBObject componentObject = (DBObject) dbObject.get(componentKey);
								SimpleOrderItemComponent component = new SimpleOrderItemComponent(componentKey);
								for (String optionKey : componentObject.keySet()) {
									DBObject optionObject = (DBObject) componentObject.get(optionKey);
									SimpleOrderItemOption option = new SimpleOrderItemOption((String) optionObject.get("gtin"));
									option.setQuantity((Integer) optionObject.get("quantity"));
									option.setTitle1((String) optionObject.get("title1"));
									option.setTitle2((String) optionObject.get("title2"));
									option.setTitle3((String) optionObject.get("title3"));
									option.setTotalPrice(new BigDecimal((Double) optionObject.get("total_price")));
									option.setUnitPrice(new BigDecimal((Double) optionObject.get("unit_price")));
									component.getOptions().put(optionKey, option);
								}
								orderItem.getComponents().put(componentKey, component);
							}
						} else if (itemKey.equals("tax_code")) {
							orderItem.put(itemKey, TaxCode.valueOf((String) value));
						} else {
							orderItem.put(itemKey, value);
						}
					}

					order.getItems().add(orderItem);
				}
			} else {
				Object value = source.get(key);
				if (value instanceof Double) {
					order.put(key, new BigDecimal((Double) value));
				} else {
					order.put(key, value);
				}
			}
		}

		return order;
	}

	public static DBObject encode(Order order) {
		DBObject dbo = new BasicDBObject();
		Map<String, Object> attributeMap = order.getAttributes();
		for (String key : attributeMap.keySet()) {
			Object value = attributeMap.get(key);

			if (value instanceof BigDecimal) {
				dbo.put(key, ((BigDecimal) value).doubleValue());
			} else {
				dbo.put(key, value);
			}

		}

		dbo.put("shipping_address", encodeAddress(order.getShippingAddress()));
		dbo.put("billing_address", encodeAddress(order.getBillingAddress()));

		List<DBObject> orderItemList = new LinkedList<DBObject>();

		for (OrderItem item : order.getItems()) {
			DBObject dboItem = new BasicDBObject();
			attributeMap = item.getAttributes();

			for (String key : attributeMap.keySet()) {
				Object value = attributeMap.get(key);

				if (value instanceof BigDecimal) {
					dboItem.put(key, ((BigDecimal) value).doubleValue());
				} else if (value instanceof Text) {

				} else if (value instanceof TaxCode) {
					dboItem.put(key, value.toString());
				} else if (key.equals("updated_at")) {
					dboItem.put(key, new Date());
				} else {
					dboItem.put(key, value);
				}

			}

			DBObject components = new BasicDBObject();

			for (String componentKey : item.getComponents().keySet()) {
				DBObject component = new BasicDBObject();
				OrderItemComponent oic = item.getComponents().get(componentKey);
				for (String optionKey : oic.getOptions().keySet()) {
					OrderItemOption oio = oic.getOptions().get(optionKey);
					DBObject option = new BasicDBObject();
					option.put("gtin", oio.getGtin());
					option.put("quantity", oio.getQuantity());
					option.put("title1", oio.getTitle1());
					option.put("title2", oio.getTitle2());
					option.put("title3", oio.getTitle3());
					option.put("total_price", ((BigDecimal) oio.getTotalPrice()).doubleValue());
					option.put("unit_price", ((BigDecimal) oio.getUnitPrice()).doubleValue());
					component.put(optionKey, option);
				}
				components.put(componentKey, component);
			}
			dboItem.put("components", components);

			orderItemList.add(dboItem);
		}

		dbo.put("order_item_list", orderItemList);

		return dbo;
	}

	public static Address decodeAddress(DBObject source) {
		OrderAddress address = new OrderAddress();

		address.setFirstName((String) source.get("first_name"));
		address.setLastName((String) source.get("last_name"));
		address.setMiddleName((String) source.get("middle_name"));
		address.setCompany((String) source.get("company"));
		address.setSuffix((String) source.get("suffix"));
		address.setPrefix((String) source.get("prefix"));
		address.setStreet1((String) source.get("street1"));
		address.setStreet2((String) source.get("street2"));
		address.setPostcode((String) source.get("postcode"));
		address.setCity((String) source.get("city"));
		address.setCountryCode((String) source.get("country"));
		address.setFax((String) source.get("fax"));
		address.setEmail((String) source.get("email"));
		address.setTelephone((String) source.get("telephone"));
		address.setRegion((String) source.get("region"));

		return address;
	}

	public static DBObject encodeAddress(Address address) {

		DBObject addressObject = new BasicDBObject();

		addressObject.put("first_name", address.getFirstName());
		addressObject.put("last_name", address.getLastName());
		addressObject.put("middle_name", address.getMiddleName());
		addressObject.put("company", address.getCompany());
		addressObject.put("suffix", address.getSuffix());
		addressObject.put("prefix", address.getPrefix());
		addressObject.put("street1", address.getStreet1());
		addressObject.put("street2", address.getStreet2());
		addressObject.put("postcode", address.getPostcode());
		addressObject.put("city", address.getCity());
		addressObject.put("country", address.getCountryCode());
		addressObject.put("telephone", address.getTelephone());
		addressObject.put("fax", address.getFax());
		addressObject.put("email", address.getEmail());
		addressObject.put("region", address.getRegion());

		return addressObject;
	}

}