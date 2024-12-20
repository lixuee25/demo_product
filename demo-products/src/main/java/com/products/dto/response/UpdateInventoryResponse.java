package com.products.dto.response;

import java.time.LocalDateTime;

public class UpdateInventoryResponse {

	private Long id;
	private int productId;
	private String productName;
	private int quantity;
	private LocalDateTime lastUpdated;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public UpdateInventoryResponse(Long id, int productId, String productName, int quantity, LocalDateTime lastUpdated) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
    }
}
