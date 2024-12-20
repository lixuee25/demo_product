package com.products.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductViewController {

	
	@GetMapping("/product-view")
    public String showProductView() {
        return "product-view";
    }
	
	
}
