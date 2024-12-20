package com.products.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.products.repository.ProductRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BarCodeValidator implements ConstraintValidator<ValidBarCode, String> {


	
	 @Autowired
	    private ProductRepository productRepository;

	    @Override
	    public boolean isValid(String barCode, ConstraintValidatorContext context) {
	        
	        return !productRepository.existsByBarCode(barCode);
	    }
	}
