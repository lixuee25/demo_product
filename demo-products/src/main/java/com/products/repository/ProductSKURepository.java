package com.products.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.products.model.ProductSKU;

@Repository
public interface ProductSKURepository extends JpaRepository<ProductSKU, Integer>{
	List<ProductSKU> findByProduct_BarCode(String barCode);
	
}
	