package com.products.dto.response;

import java.math.BigDecimal;

public class ProductsSKUResponse {

	private String skuCode;
	private String skuName;
	private BigDecimal price;
	
	
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public ProductsSKUResponse(String skuCode, String skuName, BigDecimal price) {
		this.skuCode = skuCode;
		this.skuName = skuName;
		this.price = price;
	}
	
}
