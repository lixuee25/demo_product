package com.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.dto.request.AddCustomerRequest;
import com.products.dto.request.UpdateCustomerRequest;
import com.products.dto.request.UpdateProductRequest;
import com.products.dto.response.APICustomize;
import com.products.dto.response.CustomerResponse;
import com.products.dto.response.DeleteCustomerResponse;
import com.products.dto.response.DetailCustomerResponse;
import com.products.dto.response.UpdateCustomerResponse;
import com.products.dto.response.UpdateProductResponse;
import com.products.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v2/api")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/getListCustomer")
	public ResponseEntity<?> customers() {
		APICustomize<List<CustomerResponse>> response = customerService.customers();
		return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
	}

	@PostMapping("/createCustomer")
	public ResponseEntity<?> create(@Valid @RequestBody AddCustomerRequest request) {
		return ResponseEntity.ok(customerService.create(request));
	}
	
	@PutMapping("/updateCustomer/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @RequestBody UpdateCustomerRequest request) {
		APICustomize<UpdateCustomerResponse> response = customerService.update(id, request.getCustomerCode(),
				request.getCustomerName(), request.getEmail(), request.getPhone(), request.getAddress());
		return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
	}
	@DeleteMapping("/deleteCustomer/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {

		APICustomize<DeleteCustomerResponse> response = customerService.delete(id);

		return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
	}

	@GetMapping("/detailCustomer/{id}")
	public ResponseEntity<?> detail(@PathVariable int id) {
		APICustomize<DetailCustomerResponse> response = customerService.detail(id);
		return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
	}

	
}




//@GetMapping("/getViewCustomer")
//public ResponseEntity<APICustomize<List<CustomerResponse>>> getListCustomer() {
//	APICustomize<List<CustomerResponse>> response = customerService.customers();
//	return ResponseEntity.ok(response);
//}