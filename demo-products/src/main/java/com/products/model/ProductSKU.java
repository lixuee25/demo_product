package com.products.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products_sku")
public class ProductSKU extends BaseEntity {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "sku_code", nullable = false)
	private String skuCode;
	
	@Column(name = "sku_name", nullable = false)
	private String skuName;
	
	@Column(name = "price", nullable = false)
	private BigDecimal price;
	
	
	@ManyToOne
	@JoinColumn(name = "bar_code", referencedColumnName = "bar_code", nullable = false)
	private Product product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	public ProductSKU(int id, String skuCode, String skuName, BigDecimal price, Product product ) {
		this.id = id;
		this.skuCode = skuCode;
		this.skuName = skuName;
		this.price = price;
		this.product = product;
	}
	
	public ProductSKU() {
		
	}
	
}

