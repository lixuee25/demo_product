package com.products.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.products.model.Product;

//@Repository
//public interface ProductRepository extends JpaRepository<Product, Integer> {
//
//}
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByBarCode(String barCode); 
    
    Product findByBarCode(String barCode);
//    	Optional<Product> findByBarCode(String barCode);

}
