package com.products.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @Column(name = "bar_code", nullable = false, unique = true)
	    private String barCode;

	    @Column(name = "product_name", nullable = false)
	    private String productName;

	    @Column(name = "category_name")
	    private String categoryName;

	    @Column(name = "status", nullable = false)
	    private String status;

	    @Column(name = "price", nullable = false)
	    private BigDecimal price;

	    @Column(name = "unit", nullable = false)
	    private String unit;
	    
	    @OneToMany(mappedBy ="product", cascade = CascadeType.ALL)
	    private List<ProductSKU> productSKUs;

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
		
		

		public List<ProductSKU> getProductSKUs() {
			return productSKUs;
		}

		public void setProductSKUs(List<ProductSKU> productSKUs) {
			this.productSKUs = productSKUs;
		}

		public Product(int id, String barCode, String productName, String categoryName, String status, BigDecimal price,
				String unit) {
			this.id = id;
			this.barCode = barCode;
			this.productName = productName;
			this.categoryName = categoryName;
			this.status = status;
			this.price = price;
			this.unit = unit;
		}
		
		public Product() {

		}

		

		
}
