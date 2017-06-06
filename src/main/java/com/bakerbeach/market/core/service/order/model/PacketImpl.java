package com.bakerbeach.market.core.service.order.model;

import com.bakerbeach.market.core.api.model.Address;
import com.bakerbeach.market.order.api.model.Packet;

public class PacketImpl implements Packet {

	private String reference;
	private String invoiceId;
	private String trackingId;
	private Address shippingAddress;

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

}
