package com.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.products.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	boolean existsByCustomerCode(String customerCode);
}
