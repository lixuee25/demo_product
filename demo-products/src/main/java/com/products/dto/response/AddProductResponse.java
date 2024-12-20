package com.products.dto.response;

import java.math.BigDecimal;

public class AddProductResponse {

	
	private int id;
    private String barCode;
    private String productName;
    private String categoryName;
    private String status;
    private BigDecimal price;
    private String unit;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public AddProductResponse(int id, String barCode, String productName, String categoryName, String status,
			BigDecimal price, String unit) {
		this.id = id;
		this.barCode = barCode;
		this.productName = productName;
		this.categoryName = categoryName;
		this.status = status;
		this.price = price;
		this.unit = unit;
	}
	public AddProductResponse() {
	}
    
}
