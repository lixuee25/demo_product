package com.products.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddCustomerRequest {

	@Size(min = 3, max = 50)
	@NotBlank(message = "customerCode không được để trống")
	private String customerCode;

	@NotBlank(message = "customerName không được để trống")
	private String customerName;

	@NotBlank(message = "Email không được để trống")
	private String email;

	@NotBlank(message = "Phone không được để trống")
	private String phone;

	@NotBlank(message = "address không được để trống")
	private String address;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
}
