package com.products.service;

import java.util.List;

import com.products.dto.request.AddCustomerRequest;
import com.products.dto.response.APICustomize;
import com.products.dto.response.AddCustomerResponse;
import com.products.dto.response.CustomerResponse;
import com.products.dto.response.DeleteCustomerResponse;
import com.products.dto.response.DetailCustomerResponse;
import com.products.dto.response.UpdateCustomerResponse;

import jakarta.validation.Valid;

public interface CustomerService {

	public APICustomize<List<CustomerResponse>> customer();
	public APICustomize<AddCustomerResponse> create(String customerCode, String customerName, String email, String phone, String address);
	public APICustomize<AddCustomerResponse> create(@Valid AddCustomerRequest request);
	public APICustomize<List<CustomerResponse>> customers();
	public APICustomize<UpdateCustomerResponse> update(Integer id, String customerCode, String customerName, String email,
			String phone, String address);
//	public APICustomize<DeleteCustomerResponse> delete(int id);
//	public APICustomize<DetailCustomerResponse> detail(int id);
	APICustomize<DeleteCustomerResponse> delete(Integer id);
	APICustomize<DetailCustomerResponse> detail(Integer id);
	void createCustomer(AddCustomerRequest addCustomerRequest);
}
