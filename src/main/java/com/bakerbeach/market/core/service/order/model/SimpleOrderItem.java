package com.bakerbeach.market.core.service.order.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.bakerbeach.market.core.api.model.OrderItem;
import com.bakerbeach.market.core.api.model.TaxCode;

public class SimpleOrderItem extends HashMap<String,Object> implements OrderItem {
	private static final long serialVersionUID = 1L;
	
	private Map<String, OrderItemComponent> components = new TreeMap<String, OrderItemComponent>();
	
	@Override
	public HashMap<String,Object> getAttributes() {
		return this;
	}

	@Override
	public String getQualifier() {
		return (String)this.get("qualifier");
	}
	
	public void setQualifier(String  qualifier) {
		put("qualifier",qualifier);
	}
	
	public void setGtin(String  gtin) {
		put("gtin",gtin);
	}

	@Override
	public String getGtin() {
		return (String)this.get("gtin");
	}

	@Override
	public String getBrand() {
		return (String)this.get("brand");
	}

	public void setBrand(String brand) {
		this.put("brand",brand);
	}

	public String getTitle1() {
		return (String)this.get("title_1");
	}

	public void setTitle1(String title1) {
		put("title_1",title1);

	}

	@Override
	public String getTitle2() {
		return (String)this.get("title_2");
	}

	public void setTitle2(String title2) {
		put("title_2",title2);
	}

	@Override
	public String getTitle3() {
		return (String)this.get("title_3");
	}

	public void setTitle3(String title3) {
		put("title_3",title3);
	}
	
	@Override
	public String getSupplier() {
		return (String)this.get("supplier");
	}

	public void setSupplier(String supplier) {
		put("supplier",supplier);
	}

	@Override
	public String getImageUrl1() {
		return (String)this.get("image_url_1");
	}

	public void setImageUrl1(String imageUrl1) {
		put("image_url_1",imageUrl1);
	}

	@Override
	public String getImageUrl2() {
		return (String)this.get("image_url_2");
	}

	public void setImageUrl2(String imageUrl2) {
		put("image_url_2",imageUrl2);
	}

	@Override
	public String getSize() {
		return (String) this.get("size");
	}

	public void setSize(String size) {
		put("size", size);
	}

	@Override
	public String getColor() {
		return (String) this.get("color");
	}
	
	public void setColor(String color) {
		put("color", color);
	}
	
	@Override
	public BigDecimal getUnitPrice() {
		return (BigDecimal)get("unit_price");
	}
	
	public void setUnitPrice(BigDecimal unitPrice) {
		put("unit_price",unitPrice);
	}

	@Override
	public BigDecimal getQuantity() {
		return (BigDecimal)get("quantity");
	}
	
	public void setQuantity(BigDecimal quantity){
		put("quantity",quantity);
	}

	@Override
	public BigDecimal getDiscount() {
		return (BigDecimal)get("discount");
	}
	
	public void setDiscount(BigDecimal discount){
		put("discount",discount);
	}

	@Override
	public BigDecimal getTotalPrice() {
		return (BigDecimal)get("total_price");
	}
	
	public void setTotalPrice(BigDecimal total) {
		put("total_price",total);
	}

	public TaxCode getTaxCode() {
		return (TaxCode)get("tax_code");
	}
	
	public void setTaxCode(TaxCode taxCode) {
		put("tax_code",taxCode);
	}

	@Override
	public BigDecimal getTaxPercent() {
		return (BigDecimal)get("tax_percent");
	}
	
	public void setTaxPercent(BigDecimal taxPercent) {
		put("tax_percent",taxPercent);
	}

	@Override
	public boolean isVisible() {
		return (Boolean)get("visible");
	}

	@Override
	public boolean isVolatile() {
		return (Boolean)get("volatile");
	}

	@Override
	public boolean isImmutable() {
		return (Boolean)get("immutable");
	}
	
	public void setVisible(Boolean visible) {
		put("visible",visible);
	}
	
	public void setVolatile(Boolean _volatile) {
		put("volatile",_volatile);
	}
	
	public void setImmutable(Boolean immutable) {
		put("immutable",immutable);
	}

	@Override
	public Map<String, OrderItemComponent> getComponents() {
		return components;
	}
	
	public static class SimpleOrderItemComponent implements OrderItemComponent{
		private String name;
		private Map<String, OrderItemOption> options = new TreeMap<String, OrderItemOption>();

		public SimpleOrderItemComponent(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Map<String, OrderItemOption> getOptions() {
			return options;
		}

	}

	public static class SimpleOrderItemOption implements OrderItemOption{
		private String gtin;
		private Integer quantity = 0;
		private BigDecimal unitPrice = BigDecimal.ZERO;
		private BigDecimal totalPrice = BigDecimal.ZERO;
		private String title1;
		private String title2;
		private String title3;

		public SimpleOrderItemOption(String gtin) {
			this.gtin = gtin;
		}

		@Override
		public String getGtin() {
			return gtin;
		}

		public void setGtin(String gtin) {
			this.gtin = gtin;
		}

		@Override
		public Integer getQuantity() {
			return quantity;
		}

		@Override
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		@Override
		public BigDecimal getUnitPrice() {
			return unitPrice;
		}
		
		@Override
		public void setUnitPrice(BigDecimal unitPrice) {
			this.unitPrice = unitPrice;
		}

		@Override
		public BigDecimal getTotalPrice() {
			return totalPrice;
		}
		
		@Override
		public void setTotalPrice(BigDecimal totalPrice) {
			this.totalPrice = totalPrice;
		}
		
		@Override
		public String getTitle1() {
			return title1;
		}
		
		@Override
		public void setTitle1(String title1) {
			this.title1 = title1;
		}
		
		@Override
		public String getTitle2() {
			return title2;
		}
		
		@Override
		public void setTitle2(String title2) {
			this.title2 = title2;
		}
		
		@Override
		public String getTitle3() {
			return title3;
		}
		
		@Override
		public void setTitle3(String title3) {
			this.title3 = title3;
		}
	}



}