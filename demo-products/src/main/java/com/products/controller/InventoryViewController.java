package com.products.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class InventoryViewController {

	 @GetMapping("/inventory-view")
	    public String showInventory() {
	        return "inventory-view"; 
	    }
	}