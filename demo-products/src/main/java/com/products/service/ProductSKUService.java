package com.products.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.products.dto.request.AddProductSKURequest;
import com.products.dto.response.APICustomize;
import com.products.dto.response.AddProductSKUResponse;
import com.products.model.Product;
import com.products.model.ProductSKU;
import com.products.repository.ProductRepository;
import com.products.repository.ProductSKURepository;

import jakarta.validation.Valid;


@Service
public class ProductSKUService {
	@Autowired
    private ProductRepository productRepository; 
    @Autowired
    private ProductSKURepository productSKURepository;

   
    public List<ProductSKU> getProductSKUsByBarCode(String barCode) {
        return productSKURepository.findByProduct_BarCode(barCode);
    }
    
    
    

    public AddProductSKUResponse createSKU(@Valid AddProductSKURequest request) {
        Product product = productRepository.findByBarCode(request.getBarCode());
        if (product == null) {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }

        
        ProductSKU productSKU = new ProductSKU();
        productSKU.setSkuCode(request.getSkuCode());
        productSKU.setSkuName(request.getSkuName());
        productSKU.setPrice(request.getPrice());
        productSKU.setProduct(product); 

       
        ProductSKU savedProductSKU = productSKURepository.save(productSKU);
        AddProductSKUResponse response = new AddProductSKUResponse(
                savedProductSKU.getId(),
                savedProductSKU.getSkuCode(),
                savedProductSKU.getSkuName(),
                savedProductSKU.getPrice(),
                savedProductSKU.getProduct().getBarCode(),
                "SKU tạo thành công"
        );

        return response;
    }
}






//@Service
//public class ProductSKUService {
//
//    @Autowired
//    private ProductSKURepository productSKURepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    public AddProductSKUResponse createSKU(AddProductSKURequest request) {
//        // Kiểm tra sản phẩm theo barCode
//        Product product = productRepository.findByBarCode(request.getBarCode());
//        if (product == null) {
//            return new AddProductSKUResponse("Product với barCode " + request.getBarCode() + " not found.");
//        }
//
//        //táokumoi
//        ProductSKU newSKU = new ProductSKU();
//        newSKU.setSkuCode(request.getSkuCode());
//        newSKU.setSkuName(request.getSkuName());
//        newSKU.setPrice(request.getPrice());
//        newSKU.setProduct(product);
//
//        //lúukucs
//        productSKURepository.save(newSKU);
//
//        return new AddProductSKUResponse(newSKU.getId(), newSKU.getSkuCode(), newSKU.getSkuName(),
//                newSKU.getPrice(), newSKU.getProduct().getBarCode(), "SKU tạo thành công.");
//    }
//    
//    @Autowired
//
//    
//    public List<ProductSKU> getProductSKUsByBarCode(String barCode) {
//        return productSKURepository.findByProduct_BarCode(barCode);
//    }
//}	