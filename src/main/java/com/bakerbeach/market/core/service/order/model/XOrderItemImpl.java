package com.bakerbeach.market.core.service.order.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.bakerbeach.market.core.api.model.OrderItem;
import com.bakerbeach.market.core.api.model.TaxCode;

public class XOrderItemImpl implements OrderItem {
	protected String id;
	protected String qualifier;
	protected String code;
	protected String gtin;
	protected String name;
	protected String brand;
	protected String supplier;
	protected BigDecimal quantity = BigDecimal.ZERO;
	protected BigDecimal discount = BigDecimal.ZERO;
	protected Boolean isVisible = false;
	protected Boolean isVolatile = false;
	protected Boolean isImmutable = false;
	protected TaxCode taxCode;
	protected BigDecimal taxPercent;
	protected String size;
	protected String color;
	protected Map<String, Option> options = new LinkedHashMap<>();
	protected Map<String, Object> attributes = new HashMap<String, Object>();	
	protected Map<String, String> title = new HashMap<>();
	protected Map<String, String> images = new HashMap<>();
	protected Map<String, BigDecimal> unitPrices = new HashMap<>();
	protected Map<String, BigDecimal> totalPrices = new HashMap<>();

	@Override
	public Map<String, Object> getAllAttributes() {
		return attributes;
	}
	
	@Override
	public HashMap<String, Object> getAttributes() {
		throw new RuntimeException("not implemented");
	}

	public String getId() {
		if (id != null) {
			return id;			
		}
		
		id = createId();
		return id;
	}
	
	private String createId() {
		StringBuilder id = new StringBuilder(code);
		for (Option o : options.values()) {
			id.append(":").append(o.getCode()).append(":").append(o.getQuantity());
		}
		return id.toString();
	}

	@Override
	public String getQualifier() {
		return qualifier;
	}

	@Override
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getGtin() {
		return gtin;
	}

	@Override
	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getBrand() {
		return brand;
	}

	@Override
	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String getSupplier() {
		return supplier;
	}

	@Override
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Override
	public BigDecimal getQuantity() {
		return quantity;
	}

	@Override
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Override
	public BigDecimal getDiscount() {
		return discount;
	}

	@Override
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getMonthlyUnitPrice() {
		throw new RuntimeException("not implemented anymore");
	}

	public void setMonthlyUnitPrice(BigDecimal monthlyUnitPrice) {
		throw new RuntimeException("not implemented anymore");
	}

	public BigDecimal getMonthlyTotalPrice() {
		throw new RuntimeException("not implemented anymore");
	}

	public void setMonthlyTotalPrice(BigDecimal monthlyTotalPrice) {
		throw new RuntimeException("not implemented anymore");
	}

	@Override
	public TaxCode getTaxCode() {
		return taxCode;
	}

	@Override
	public void setTaxCode(TaxCode taxCode) {
		this.taxCode = taxCode;
	}

	@Override
	public BigDecimal getTaxPercent() {
		return taxPercent;
	}

	@Override
	public void setTaxPercent(BigDecimal taxPercent) {
		this.taxPercent = taxPercent;
	}

	@Override
	public Map<String, String> getTitle() {
		return title;
	}

	@Override
	public String getTitle(String key) {
		return title.get(key);
	}
	
	@Override
	public void setTitle(Map<String, String> title) {
		this.title = title;
	}
	
	@Override
	@Deprecated
	public String getTitle1() {
		return title.get("title1");
	}

	@Override
	@Deprecated
	public String getTitle2() {
		return title.get("title2");
	}

	@Override
	@Deprecated
	public String getTitle3() {
		return title.get("title3");
	}

	@Override
	public Map<String, String> getImages() {
		return images;
	}
	
	@Override
	public String getImage(String key) {
		return images.get(key);
	}	
	
	@Override
	public void setImages(Map<String, String> images) {
		this.images = images;
	}
	
	@Deprecated
	public String getImageUrl1() {
		return images.get("image1");
	}

	@Deprecated
	public void setImageUrl1(String imageUrl1) {
		this.images.put("image1", imageUrl1);
	}

	@Deprecated
	public String getImageUrl2() {
		return images.get("image2");
	}

	@Deprecated
	public void setImageUrl2(String imageUrl2) {
		this.images.put("image2", imageUrl2);
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public Option newOption(String code) {
		return new OptionImpl(code);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("CartItem {");
		builder.append("id: ").append(id).append(",");
		builder.append("code: ").append(code).append(",");
		builder.append("brand: ").append(brand).append(",");
		builder.append("quantity: ").append(quantity).append(",");
		builder.append("}");

		return builder.toString();
	}

	@Deprecated
	@Override
	public BigDecimal getUnitPrice() {
		return getUnitPrice("std");
	}

	@Override
	public Map<String, BigDecimal> getUnitPrices() {
		return unitPrices;
	}

	@Override
	public BigDecimal getUnitPrice(String key) {
		return unitPrices.get(key);
	}
	
	@Override
	public void setUnitPrice(String key, BigDecimal value) {
		unitPrices.put(key, value);
	}
	
	@Override
	public void addUnitPrices(Map<String, BigDecimal> unitPrices) {
		unitPrices.forEach((k,v) -> {
			if (!this.unitPrices.containsKey(k)) {
				this.unitPrices.put(k, v);
			} else {
				this.unitPrices.put(k, this.unitPrices.get(k).add(v));
			}
		});
	}
	
	@Override
	public void setUnitPrices(Map<String, BigDecimal> unitPrices) {
		this.unitPrices = unitPrices;
	}

	@Deprecated
	@Override
	public BigDecimal getTotalPrice() {
		return getTotalPrice("std");
	}
	
	@Override
	public Map<String, BigDecimal> getTotalPrices() {
		return totalPrices;
	}
	
	@Override
	public BigDecimal getTotalPrice(String key) {
		return totalPrices.get(key);
	}
	
	@Override
	public void setTotalPrices(Map<String, BigDecimal> totalPrices) {
		this.totalPrices = totalPrices;
	}
	
	@Override
	public Boolean isVolatile() {
		return isVolatile;
	}
	
	@Override
	public void setIsVolatile(Boolean isVolatile) {
		this.isVolatile = isVolatile;
	}

	@Override
	public Boolean isVisible() {
		return isVisible;
	}

	@Override
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	@Override
	public Boolean isImmutable() {
		return isImmutable;
	}
	
	@Override
	public void setIsImmutable(Boolean isImmutable) {
		this.isImmutable = isImmutable;
	}

	@Override
	public Map<String, Option> getAllOptions() {
		return options;
	}
	
	@Override
	public void setOptions(Map<String, Option> options) {
		this.options = options;
	}
	
	@Override
	@Deprecated
	public Map<String, OrderItemOption> getOptions() {
		throw new RuntimeException("not implemented");
	}

	@Override
	@Deprecated
	public Map<String, OrderItemComponent> getComponents() {
		throw new RuntimeException("not implemented");
	}
	
	public static class OptionImpl implements Option {
		private String code;
		private String gtin;
		private BigDecimal quantity;
		protected Map<String, BigDecimal> unitPrices = new HashMap<>();
		protected Map<String, BigDecimal> totalPrices = new HashMap<>();
		private String tag;
		protected Map<String, String> title = new HashMap<>();

		public OptionImpl() {
		}
		
		public OptionImpl(String code) {
			this.code = code;
		}
		
		public OptionImpl(String code, String tag) {
			this.code = code;
			this.tag = tag;
		}

		@Override
		public String getCode() {
			return code;
		}

		@Override
		public void setCode(String code) {
			this.code = code;
		}
		
		@Override
		public String getGtin() {
			return gtin;
		}

		@Override
		public void setGtin(String gtin) {
			this.gtin = gtin;
		}

		@Override
		public BigDecimal getQuantity() {
			return quantity;
		}

		@Override
		public void setQuantity(BigDecimal quantity) {
			this.quantity = quantity;
		}


		@Override
		public Map<String, BigDecimal> getUnitPrices() {
			return unitPrices;
		}
		
		@Override
		public BigDecimal getUnitPrice(String key) {
			return unitPrices.get(key);
		}

		@Override
		public void setUnitPrices(Map<String, BigDecimal> unitPrices) {
			this.unitPrices = unitPrices;
		}
		
		@Override
		public void setUnitPrice(String key, BigDecimal value) {
			unitPrices.put(key, value);
		}

		@Override
		public void addUnitPrices(Map<String, BigDecimal> unitPrices) {
			unitPrices.forEach((k,v) -> {
				if (!this.unitPrices.containsKey(k)) {
					this.unitPrices.put(k, v);
				} else {
					this.unitPrices.put(k, this.unitPrices.get(k).add(v));
				}
			});
		}
		
		@Override
		public void multiplyUnitPrices(BigDecimal multiplicand) {
			unitPrices.forEach((k,v) -> {
				unitPrices.put(k, unitPrices.get(k).multiply(multiplicand));
			});
		}
		
		@Override
		public String getTag() {
			return tag;
		}

		@Override
		public void setTag(String tag) {
			this.tag = tag;
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
		public String getTitle(String key) {
			return title.get(key);
		}

	}
	
}
