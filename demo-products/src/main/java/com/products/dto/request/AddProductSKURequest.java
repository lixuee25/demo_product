package com.products.dto.request;



import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddProductSKURequest {

	@Size(min = 3, max = 20 )
	@NotBlank(message = "skuCode không được để trống ")
	private String skuCode;
	
	@NotBlank(message = "skuName không được đê trống")
	private String skuName;
	
	@NotNull(message = "Price không được đê trống")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price phải lớn hơn 0")
	private BigDecimal price;
	
	@NotBlank(message = "Mã barCode không được để trống")
	private String barCode;

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

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	
	
	
}
