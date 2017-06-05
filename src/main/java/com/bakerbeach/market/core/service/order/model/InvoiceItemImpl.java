package com.bakerbeach.market.core.service.order.model;

import java.math.BigDecimal;

import com.bakerbeach.market.core.api.model.TaxCode;
import com.bakerbeach.market.order.api.model.InvoiceItem;

public class InvoiceItemImpl implements InvoiceItem {
	
	private String gtin;
	private String name;
	private BigDecimal quantity;
	protected TaxCode taxCode;
	protected BigDecimal taxPercent;
	
	
	

}
