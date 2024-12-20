package com.products.dto.request;

import java.math.BigDecimal;

import com.products.validator.ValidBarCode;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddProductRequest {
		@Size(min = 3, max = 20)
	    @NotBlank(message = "BarCode không được để trống")
		@ValidBarCode(message = "BarCode đã tồn tại")
	    private String barCode;

	    @NotBlank(message = "Product Name không được để trống")
	    private String productName;

	    @NotBlank(message = "Category không được để trống")
	    private String categoryName;

	    @NotNull(message = "Price không được để trống")
	    @DecimalMin(value = "0.0", inclusive = false, message = "Price phải lớn hơn 0")
	    private BigDecimal price;

	    @NotBlank(message = "Unit không được để trống")
	    private String unit;

	    @NotNull(message = "Status không được để trống")
	    private String status;

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
    
    
}
