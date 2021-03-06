package com.bakerbeach.market.core.service.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.mongodb.morphia.annotations.Id;

import com.bakerbeach.market.core.api.model.Address;
import com.bakerbeach.market.core.api.model.Total;
import com.bakerbeach.market.core.api.model.Total.Line;
import com.bakerbeach.market.core.service.order.model.XOrderTotalImpl.LineImpl;
import com.bakerbeach.market.order.api.model.Invoice;
import com.bakerbeach.market.order.api.model.Order;
import com.bakerbeach.market.order.api.model.OrderItem;
import com.bakerbeach.market.order.api.model.Packet;

//@Entity(value = "order", noClassnameStored = false)
public class XOrderImpl implements Order {
	@Id
	protected String id;
	protected String shopCode;
	protected String currencyCode;
	protected Total total;
	protected String customerId;
	protected Address shippingAddress;
	protected Address billingAddress;
	protected Map<String, OrderItem> items = new LinkedHashMap<>();
	protected Map<String, Object> attributes = new HashMap<>();
	protected List<Invoice> invoices = new ArrayList<>();
	protected List<Packet> packets = new ArrayList<>();
	protected String status;
	protected String customerEmail;
	protected Date updatedAt;
	protected Date createdAt;
	protected String paymentCode;
	protected String paymentTransactionId;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getShopCode() {
		return shopCode;
	}

	@Override
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	@Override
	@Deprecated
	public String getCurrency() {
		return currencyCode;
	}

	@Override
	public String getCurrencyCode() {
		return currencyCode;
	}

	@Override
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	@Deprecated
	public BigDecimal getTotal() {
		return total.getGross();
	}

	@Override
	@Deprecated
	public void setTotal(BigDecimal total) {
		throw new RuntimeException("not supported anymore");
	}

	public Total getTotal(Boolean asObject) {
		return total;
	}

	@Override
	public void setTotal(Total total) {
		this.total = total;
	}

	@Override
	public String getCustomerId() {
		return customerId;
	}

	@Override
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Override
	public Address getShippingAddress() {
		return shippingAddress;
	}

	@Override
	public void setShippingAddress(Address source) {
		this.shippingAddress = source;
	}

	@Override
	public Address newAddress(Address source) {
		OrderAddressImpl target = new OrderAddressImpl();

		target.setPrefix(source.getPrefix());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setMiddleName(source.getMiddleName());
		target.setSuffix(source.getSuffix());
		target.setStreet1(source.getStreet1());
		target.setStreet2(source.getStreet2());
		target.setPostcode(source.getPostcode());
		target.setCity(source.getCity());
		target.setRegion(source.getRegion());
		target.setCountryCode(source.getCountryCode());
		target.setEmail(source.getEmail());
		target.setTelephone(source.getTelephone());
		target.setFax(source.getFax());
		target.setCompany(source.getCompany());
		target.setCreatedAt(source.getCreatedAt());
		target.setUpdatedAt(source.getUpdatedAt());

		return target;
	}

	@Override
	public Total newTotal(Total source) {
		XOrderTotalImpl target = new XOrderTotalImpl();

		target.setQualifier(source.getQualifier());
		target.setQuantity(source.getQuantity());
		target.setGross(source.getGross());
		target.setNet(source.getNet());

		source.getLines().forEach((k, v) -> {
			target.putLine(k, newTotalLine(v));
		});

		return target;
	}

	private Line newTotalLine(Line source) {
		XOrderTotalImpl.LineImpl target = new LineImpl(source.getTaxCode(), source.getTaxPercent());

		target.setQuantity(source.getQuantity());
		target.setGross(source.getGross());
		target.setNet(source.getNet());
		target.setTax(source.getTax());

		return target;
	}

	@Override
	public Address getBillingAddress() {
		return billingAddress;
	}

	@Override
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	@Override
	@Deprecated
	public List<OrderItem> getItems() {
		return new ArrayList<>(items.values());
	}

	@Override
	public Map<String, OrderItem> getItems(Boolean asObject) {
		return items;
	}

	@Override
	@Deprecated
	public void addItem(Object item) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void addItem(OrderItem item) {
		items.put(item.getCode(), item);
	}

	@Override
	public OrderItem getItem(String key) {
		return items.get(key);
	}

	@Override
	public Map<String, Object> getAllAttributes() {
		return attributes;
	}

	@Override
	public void addAttributes(Map<String, Object> map) {
		if (MapUtils.isNotEmpty(map)) {
			attributes.putAll(map);
		}
	}

	@Override
	@Deprecated
	public HashMap<String, Object> getAttributes() {
		throw new RuntimeException("not implemented");
	}

	@Override
	@Deprecated
	public HashMap<String, Object> getAdditionalInformations() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	@Deprecated
	public String getOrderStatus() {
		return status;
	}

	@Override
	public String getCustomerEmail() {
		return customerEmail;
	}

	@Override
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	@Override
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public void setUpdatedAt(Date date) {
		this.updatedAt = date;
	}

	@Override
	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String getPaymentCode() {
		return paymentCode;
	}

	@Override
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	@Override
	public String getPaymentTransactionId() {
		return paymentTransactionId;
	}

	@Override
	public OrderItem newItem() {
		return new XOrderItemImpl();
	}

	@Override
	public List<Invoice> getInvoices() {
		return invoices;
	}

	@Override
	public List<Packet> getPackets() {
		return packets;
	}

}
