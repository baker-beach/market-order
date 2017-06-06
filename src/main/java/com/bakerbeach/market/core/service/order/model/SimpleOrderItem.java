package com.bakerbeach.market.core.service.order.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.bakerbeach.market.core.api.model.TaxCode;
import com.bakerbeach.market.order.api.model.OrderItem;
import com.bakerbeach.market.order.api.model.PacketItemInfo;

public class SimpleOrderItem extends HashMap<String, Object> implements OrderItem {
	private static final long serialVersionUID = 1L;

	private Map<String, OrderItemComponent> components = new TreeMap<String, OrderItemComponent>();
	protected Map<String, OrderItemOption> options = new LinkedHashMap<>();

	@Override
	public HashMap<String, Object> getAttributes() {
		return this;
	}

	@Override
	public String getQualifier() {
		return (String) this.get("qualifier");
	}

	public void setQualifier(String qualifier) {
		put("qualifier", qualifier);
	}

	public void setGtin(String gtin) {
		put("gtin", gtin);
	}

	@Override
	public String getGtin() {
		return (String) this.get("gtin");
	}

	@Override
	public String getBrand() {
		return (String) this.get("brand");
	}

	public void setBrand(String brand) {
		this.put("brand", brand);
	}

	public String getTitle1() {
		return (String) this.get("title_1");
	}

	public void setTitle1(String title1) {
		put("title_1", title1);

	}

	@Override
	public String getTitle2() {
		return (String) this.get("title_2");
	}

	public void setTitle2(String title2) {
		put("title_2", title2);
	}

	@Override
	public String getTitle3() {
		return (String) this.get("title_3");
	}

	public void setTitle3(String title3) {
		put("title_3", title3);
	}

	@Override
	public String getSupplier() {
		return (String) this.get("supplier");
	}

	public void setSupplier(String supplier) {
		put("supplier", supplier);
	}

	@Override
	public String getImageUrl1() {
		return (String) this.get("image_url_1");
	}

	public void setImageUrl1(String imageUrl1) {
		put("image_url_1", imageUrl1);
	}

	@Override
	public String getImageUrl2() {
		return (String) this.get("image_url_2");
	}

	public void setImageUrl2(String imageUrl2) {
		put("image_url_2", imageUrl2);
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
		return (BigDecimal) get("unit_price");
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		put("unit_price", unitPrice);
	}

	@Override
	public BigDecimal getQuantity() {
		return (BigDecimal) get("quantity");
	}

	public void setQuantity(BigDecimal quantity) {
		put("quantity", quantity);
	}

	@Override
	public BigDecimal getDiscount() {
		return (BigDecimal) get("discount");
	}

	public void setDiscount(BigDecimal discount) {
		put("discount", discount);
	}

	@Override
	public BigDecimal getTotalPrice() {
		return (BigDecimal) get("total_price");
	}

	public void setTotalPrice(BigDecimal total) {
		put("total_price", total);
	}

	public TaxCode getTaxCode() {
		return (TaxCode) get("tax_code");
	}

	public void setTaxCode(TaxCode taxCode) {
		put("tax_code", taxCode);
	}

	@Override
	public BigDecimal getTaxPercent() {
		return (BigDecimal) get("tax_percent");
	}

	public void setTaxPercent(BigDecimal taxPercent) {
		put("tax_percent", taxPercent);
	}

	@Override
	public Boolean isVisible() {
		return (Boolean) get("visible");
	}

	@Override
	public Boolean isVolatile() {
		return (Boolean) get("volatile");
	}

	@Override
	public Boolean isImmutable() {
		return (Boolean) get("immutable");
	}

	public void setVisible(Boolean visible) {
		put("visible", visible);
	}

	public void setVolatile(Boolean _volatile) {
		put("volatile", _volatile);
	}

	public void setImmutable(Boolean immutable) {
		put("immutable", immutable);
	}

	@Override
	@Deprecated
	public Map<String, OrderItemComponent> getComponents() {
		return components;
	}

	@Override
	public Map<String, OrderItemOption> getOptions() {
		return options;
	}

	public static class SimpleOrderItemComponent implements OrderItemComponent {
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

	public static class SimpleOrderItemOption implements OrderItemOption {
		private String gtin;
		private Integer quantity = 0;
		private BigDecimal unitPrice = BigDecimal.ZERO;
		protected Map<String, BigDecimal> unitPrices = new HashMap<>();
		private BigDecimal totalPrice = BigDecimal.ZERO;
		protected Map<String, String> title = new HashMap<>();
		@Deprecated
		private String title1;
		@Deprecated
		private String title2;
		@Deprecated
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
		@Deprecated
		public BigDecimal getUnitPrice() {
			return unitPrice;
		}

		@Override
		@Deprecated
		public void setUnitPrice(BigDecimal unitPrice) {
			this.unitPrice = unitPrice;
		}

		@Override
		public Map<String, BigDecimal> getUnitPrices() {
			return unitPrices;
		}

		@Override
		public void setUnitPrices(Map<String, BigDecimal> unitPrices) {
			this.unitPrices = unitPrices;
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
		public Map<String, String> getTitle() {
			return title;
		}

		@Override
		public void setTitle(Map<String, String> title) {
			this.title = title;
		}

		@Override
		@Deprecated
		public String getTitle1() {
			return title1;
		}

		@Override
		@Deprecated
		public void setTitle1(String title1) {
			this.title1 = title1;
		}

		@Override
		@Deprecated
		public String getTitle2() {
			return title2;
		}

		@Override
		@Deprecated
		public void setTitle2(String title2) {
			this.title2 = title2;
		}

		@Override
		@Deprecated
		public String getTitle3() {
			return title3;
		}

		@Override
		@Deprecated
		public void setTitle3(String title3) {
			this.title3 = title3;
		}
	}

	@Override
	public String getCode() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setCode(String code) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public String getName() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setName(String name) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Map<String, String> getTitle() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public String getTitle(String key) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Map<String, String> getImages() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public String getImage(String key) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Map<String, BigDecimal> getUnitPrices() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public BigDecimal getUnitPrice(String key) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setUnitPrice(String string, BigDecimal value) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void addUnitPrices(Map<String, BigDecimal> unitPrices) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Map<String, BigDecimal> getTotalPrices() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public BigDecimal getTotalPrice(String key) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Map<String, Object> getAllAttributes() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Map<String, Option> getAllOptions() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setTitle(Map<String, String> title) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setImages(Map<String, String> images) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setUnitPrices(Map<String, BigDecimal> unitPrices) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setTotalPrices(Map<String, BigDecimal> totalPrices) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setIsVisible(Boolean isVisible) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setIsVolatile(Boolean isVolatile) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setIsImmutable(Boolean isImmutable) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setOptions(Map<String, Option> options) {
		throw new RuntimeException("not implemented");
		
	}

	@Override
	public Option newOption(String code) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void putOption(String key, Option option) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<PacketItemInfo> getPacketItemInfos() {
		throw new RuntimeException("not implemented");
	}

}