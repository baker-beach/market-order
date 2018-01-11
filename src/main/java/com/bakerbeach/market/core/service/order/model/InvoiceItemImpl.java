package com.bakerbeach.market.core.service.order.model;

import java.math.BigDecimal;

import com.bakerbeach.market.core.api.model.TaxCode;
import com.bakerbeach.market.order.api.model.InvoiceItem;

public class InvoiceItemImpl implements InvoiceItem {

	private String gtin;
	private String name;
	private BigDecimal quantity;
	private TaxCode taxCode;
	private BigDecimal taxPercent;
	private BigDecimal total;
	private BigDecimal unitPrice;

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public TaxCode getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(TaxCode taxCode) {
		this.taxCode = taxCode;
	}

	public BigDecimal getTaxPercent() {
		return taxPercent;
	}

	public void setTaxPercent(BigDecimal taxPercent) {
		this.taxPercent = taxPercent;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

}
