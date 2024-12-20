package com.products.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerViewController {

	@GetMapping("/customer-view")
    public String showCustomerView() {
        return "customer-view";
    }
	
}
