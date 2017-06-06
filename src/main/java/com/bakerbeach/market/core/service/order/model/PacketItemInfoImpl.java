package com.bakerbeach.market.core.service.order.model;

import java.math.BigDecimal;

import com.bakerbeach.market.order.api.model.PacketItemInfo;

public class PacketItemInfoImpl implements PacketItemInfo {
	
	private BigDecimal quantity;
	private String paketReference;

	public String getPaketReference() {
		return paketReference;
	}

	public void setPaketReference(String paketReference) {
		this.paketReference = paketReference;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
}
