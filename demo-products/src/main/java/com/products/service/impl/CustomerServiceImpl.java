package com.products.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.products.dto.request.AddCustomerRequest;
import com.products.dto.request.AddProductRequest;
import com.products.dto.response.APICustomize;
import com.products.dto.response.AddCustomerResponse;
import com.products.dto.response.CustomerResponse;
import com.products.dto.response.DeleteCustomerResponse;
import com.products.dto.response.DeleteProductResponse;
import com.products.dto.response.DetailCustomerResponse;
import com.products.dto.response.DetailProductResponse;
import com.products.dto.response.UpdateCustomerResponse;
import com.products.dto.response.UpdateProductResponse;
import com.products.enums.ApiError;
import com.products.model.Customer;
import com.products.model.Product;
import com.products.repository.CustomerRepository;
import com.products.service.CustomerService;



@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public APICustomize<List<CustomerResponse>> customers() {
		List<Customer> customers = customerRepository.findAll();

		List<CustomerResponse> customerResponse = customers.stream()
				.map(customer -> new CustomerResponse(
						customer.getId(), 
						customer.getCustomerCode(), 
						customer.getCustomerName(),
						customer.getEmail(),
						customer.getPhone(),
						customer.getAddress(),
						customer.getCreatedAt()))
				.toList();

		return new APICustomize<>(ApiError.OK.getCode(), ApiError.OK.getMessage(), customerResponse);
	}

	@Override
	public APICustomize<AddCustomerResponse> create(AddCustomerRequest request) {
		
		if (customerRepository.existsByCustomerCode(request.getCustomerCode())) {
			throw new IllegalArgumentException("CustomerCode đã tồn tại, vui lòng nhập lại!");
		}

		Customer newCustomer = new Customer();
		newCustomer.setCustomerCode(request.getCustomerCode());
		newCustomer.setCustomerName(request.getCustomerName());
		newCustomer.setEmail(request.getEmail());
		newCustomer.setPhone(request.getPhone());
		newCustomer.setAddress(request.getAddress());

		customerRepository.save(newCustomer);

		AddCustomerResponse response = new AddCustomerResponse(
				newCustomer.getId(), 
				newCustomer.getCustomerCode(),
				newCustomer.getCustomerName(), 
				newCustomer.getEmail(), 
				newCustomer.getPhone(),
				newCustomer.getAddress());

		return new APICustomize<>(ApiError.CREATED.getCode(), ApiError.CREATED.getMessage(), response);
	}
	
	@Override
	public APICustomize<UpdateCustomerResponse> update(
			Integer id,
			String customerCode,
			String customerName,
			String email,
			String phone,
			String address
			) 
	{

		Customer customer = customerRepository.findById(id).orElse(null); 

		if (customer == null) {

			return new APICustomize<>(ApiError.NOT_FOUND.getCode(), "Không tìm thấy Khách hàng!", null); 
		}
		customer.setCustomerCode(customerCode);
		customer.setCustomerName(customerName);
		customer.setEmail(email);
		customer.setPhone(phone);
		customer.setAddress(address);
		
		customerRepository.save(customer);

		UpdateCustomerResponse response = new UpdateCustomerResponse(
				customer.getId(),
				customer.getCustomerCode(),
				customer.getCustomerName(),
				customer.getEmail(),
				customer.getPhone(),
				customer.getAddress());

		return new APICustomize<>(ApiError.OK.getCode(), "Cập nhật khách hàng thành công!", response);
	}

	
	
	
//	@Override
//	public APICustomize<UpdateCustomerResponse> update(
//			Integer id,
//			String customerCode,
//			String customerName,
//			String email,
//			String phone,
//			String address	
//			) 
//	{
//
//		Customer customer = customerRepository.findById(id).orElse(null); 
//
//		if (customer == null) {
//
//			return new APICustomize<>(ApiError.NOT_FOUND.getCode(), "Không tìm thấy Customer!", null); 
//		}
//		customer.setCustomerCode(customerCode);
//		customer.setCustomerName(customerName);
//		customer.setEmail(email);
//		customer.setPhone(phone);
//		customer.setAddress(address);
//		
//
//		customerRepository.save(customer);
//
//		UpdateCustomerResponse response = new UpdateCustomerResponse(
//				customer.getId(),
//				customer.getCustomerCode(),
//				customer.getCustomerName(),
//				customer.getEmail(),
//				customer.getPhone(),
//				customer.getAddress());
//
//		return new APICustomize<>(ApiError.OK.getCode(), "Cập nhật customer thành công!", response);
//	}

	@Override
	public APICustomize<DeleteCustomerResponse> delete(Integer id) {
		customerRepository.deleteById(id);
		return new APICustomize<>(ApiError.OK.getCode(), "Xóa customer thành công!", null);
	}


	@Override
	public APICustomize<DetailCustomerResponse> detail(Integer id) {

		Customer customer = customerRepository.findById(id).orElse(null);

		if (customer == null) {
			return new APICustomize<>(ApiError.NOT_FOUND.getCode(), "Không tìm thấy customer!", null);
		}

		DetailCustomerResponse response = new DetailCustomerResponse(customer.getId(), customer.getCustomerCode(),
				customer.getCustomerName(), customer.getEmail(), customer.getPhone(), customer.getAddress(), customer.getCreatedAt());

		return new APICustomize<>(ApiError.OK.getCode(), ApiError.OK.getMessage(), response);
	}


	@Override
	public void createCustomer(AddCustomerRequest addCustomerRequest) {
	    if (customerRepository.existsByCustomerCode(addCustomerRequest.getCustomerCode())) {
	        throw new IllegalArgumentException("CustomerCode đã tồn tại, vui lòng nhập lại!");
	    }

	    Customer newCustomer = new Customer();
	    newCustomer.setCustomerCode(addCustomerRequest.getCustomerCode());
	    newCustomer.setCustomerName(addCustomerRequest.getCustomerName());
	    newCustomer.setEmail(addCustomerRequest.getEmail());
	    newCustomer.setPhone(addCustomerRequest.getPhone());
	    newCustomer.setAddress(addCustomerRequest.getAddress());

	   customerRepository.save(newCustomer);
	}


	

	
	
	
	
	
	
	@Override
	public APICustomize<List<CustomerResponse>> customer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public APICustomize<AddCustomerResponse> create(String customerCode, String customerName, String email,
			String phone, String address) {
		// TODO Auto-generated method stub
		return null;
	}

}