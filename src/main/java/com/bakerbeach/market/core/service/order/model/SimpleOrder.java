package com.bakerbeach.market.core.service.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bakerbeach.market.core.api.model.Address;
import com.bakerbeach.market.core.api.model.Order;
import com.bakerbeach.market.core.api.model.OrderItem;

public class SimpleOrder extends HashMap<String, Object> implements Order {

	private static final long serialVersionUID = 1L;

	private static final String ORDER_ID = "order_id";
	private static final String SHOP_CODE = "shop_code";
	private static final String CURRENCY = "currency";
	private static final String TOTAL = "total";
	private static final String STATUS = "status";
	private static final String CUSTOMER_ID = "customer_id";
	private static final String CUSTOMER_EMAIL = "customer_email";
	private static final String CREATED_AT = "created_at";
	private static final String UPDATED_AT = "updated_at";
	private static final String PAYMENT_CODE = "payment_code";
	private static final String PAYMENT_TRANSACTION_ID = "payment_transaction_id";
	private static final String ADDITIONAL_INFORMATIONS = "additional_informations";
	private Address shippingAddress;
	private Address billingAddress;
	private List<OrderItem> items;

	public SimpleOrder() {
		setItems(new ArrayList<OrderItem>());
	}

	@Override
	public String getId() {
		return (String) get(ORDER_ID);
	}

	public void setId(String id) {
		put(ORDER_ID, id);
	}

	@Override
	public String getShopCode() {
		return (String) get(SHOP_CODE);
	}

	public void setShopCode(String shopCode) {
		put(SHOP_CODE, shopCode);
	}

	@Override
	public String getCurrency() {
		return (String) get(CURRENCY);
	}

	public void setCurrency(String currency) {
		put(CURRENCY, currency);
	}

	@Override
	public BigDecimal getTotal() {
		return (BigDecimal) get(TOTAL);
	}

	public void setTotal(BigDecimal total) {
		put(TOTAL, total);
	}

	@Override
	public String getCustomerId() {
		return (String) get(CUSTOMER_ID);
	}

	public void setCustomerId(String customerId) {
		put(CUSTOMER_ID, customerId);
	}

	@Override
	public String getCustomerEmail() {
		return (String) get(CUSTOMER_EMAIL);
	}

	public void setCustomerEmail(String customerEmail) {
		put(CUSTOMER_EMAIL, customerEmail);
	}

	@Override
	public String getOrderStatus() {
		return (String) get(STATUS);
	}

	public void setStatus(String status) {
		put(STATUS, status);
	}

	@Override
	public HashMap<String, Object> getAttributes() {
		return this;
	}

	/**
	 * @return the shippingAddress
	 */
	public Address getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress
	 *            the shippingAddress to set
	 */
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the billingAddress
	 */
	public Address getBillingAddress() {
		return billingAddress;
	}

	/**
	 * @param billingAddress
	 *            the billingAddress to set
	 */
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	/**
	 * @return the items
	 */
	public List<OrderItem> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getAdditionalInformations() {
		return (HashMap<String, Object>) get(ADDITIONAL_INFORMATIONS);
	}

	public void setAdditionalInformations(HashMap<String, Object> additionalInformations) {
		put(ADDITIONAL_INFORMATIONS, additionalInformations);
	}

	@Override
	public Date getUpdatedAt() {
		return (Date) get(UPDATED_AT);
	}

	@Override
	public Date getCreatedAt() {
		return (Date) get(CREATED_AT);
	}

	@Override
	public void setUpdatedAt(Date date) {
		put(UPDATED_AT, date);
	}

	public void setCreatedAt(Date date) {
		put(CREATED_AT, date);
	}

	@Override
	public String getPaymentCode() {
		return (String) get(PAYMENT_CODE);
	}

	public void setPaymentCode(String paymentCode) {
		put(PAYMENT_CODE, paymentCode);
	}

	@Override
	public String getPaymentTransactionId() {
		return (String) get(PAYMENT_TRANSACTION_ID);
	}

	public void setPaymentTransactionId(String paymentTransactionId) {
		put(PAYMENT_TRANSACTION_ID, paymentTransactionId);
	}

	@Override
	public String getCurrencyCode() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Map<String, OrderItem> getAllItems() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public OrderItem getItem(String key) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Map<String, Object> getAllAttributes() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public String getStatus() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setCurrencyCode(String currencyCode) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void addItem(Object newOrderItem) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void addAttributes(Map<String, Object> map) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void addItem(OrderItem item) {
		throw new RuntimeException("not implemented");
	}

}
