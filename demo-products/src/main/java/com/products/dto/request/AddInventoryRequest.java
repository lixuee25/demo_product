package com.products.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddInventoryRequest {

	@NotNull(message = "productId không được để trống")
	private int productId;
	
	@Min(value = 0, message = "Quantity phải >= 0")
	private int quantity;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
