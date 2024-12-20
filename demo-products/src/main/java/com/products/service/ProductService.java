package com.products.service;

import java.math.BigDecimal;
import java.util.List;

import com.products.dto.request.AddProductRequest;
import com.products.dto.request.AddProductSKURequest;
import com.products.dto.request.DetailProductRequest;
import com.products.dto.response.APICustomize;
import com.products.dto.response.AddProductResponse;
import com.products.dto.response.AddProductSKUResponse;
import com.products.dto.response.DeleteProductResponse;
import com.products.dto.response.DetailProductResponse;
import com.products.dto.response.ProductWithSKUsResponse;
import com.products.dto.response.ProductsResponse;
import com.products.dto.response.UpdateProductResponse;
import com.products.model.ProductSKU;

import jakarta.validation.Valid;

public interface ProductService {
//	public APICustomize<List<ProductsResponse>> products();
//	public APICustomize<AddProductResponse> create(String barCode, String productName, String categoryName, String status, BigDecimal price, String unit);

	
	    APICustomize<List<ProductsResponse>> products();
	    APICustomize<AddProductResponse> create(@Valid AddProductRequest request);
	    APICustomize<UpdateProductResponse> update(Integer id, String barCode, String productName, 
	            String categoryName, String status, BigDecimal price, String unit);
	    APICustomize<DeleteProductResponse> delete(Integer id);
	    APICustomize<DetailProductResponse> detail(Integer id);
//	    APICustomize<AddProductSKUResponse> createSKU(AddProductSKURequest request);
	    APICustomize<List<ProductSKU>> getProductSKUsByBarCode(String barCode);
	    APICustomize<ProductWithSKUsResponse> getProductWithSKUs(String barCode);

		public void createProduct(AddProductRequest addProductRequest);
		APICustomize<AddProductSKUResponse> createSKU(List<AddProductSKURequest> request, String barcode);
	}



//	public APICustomize<List<ProductsResponse>> products();
//	public APICustomize<AddProductResponse> create(String barCode, 
//			String productName, String categoryName, String status, BigDecimal price, String unit);
//	public APICustomize<UpdateProductResponse> update(Integer id, String barCode, String productName,
//			String categoryName, String status, BigDecimal price, String unit);
//	public APICustomize<DeleteProductResponse> delete(Integer id);
//	public APICustomize<DetailProductResponse> detail(Integer id);
//	public APICustomize<AddProductResponse> create(@Valid AddProductRequest request);
//
//
//	public APICustomize<AddProductSKUResponse> createSKU(AddProductSKURequest request);
//
//	public APICustomize<List<ProductSKU>> getProductSKUsByBarCode(String barCode);
//	public void createProduct(AddProductRequest addProductRequest);
//}
