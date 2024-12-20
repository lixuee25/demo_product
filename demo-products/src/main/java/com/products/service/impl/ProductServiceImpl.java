package com.products.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.products.dto.request.AddProductRequest;
import com.products.dto.request.AddProductSKURequest;
import com.products.dto.response.APICustomize;
import com.products.dto.response.AddProductResponse;
import com.products.dto.response.AddProductSKUResponse;
import com.products.dto.response.DeleteProductResponse;
import com.products.dto.response.DetailProductResponse;
import com.products.dto.response.ProductWithSKUsResponse;
import com.products.dto.response.ProductsResponse;
import com.products.dto.response.ProductsSKUResponse;
import com.products.dto.response.UpdateProductResponse;
import com.products.enums.ApiError;
import com.products.model.Product;
import com.products.model.ProductSKU;
import com.products.repository.ProductRepository;
import com.products.repository.ProductSKURepository;
import com.products.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	 @Autowired
	   private ProductSKURepository productSKURepository;
	@Override
	public APICustomize<List<ProductsResponse>> products() {

		List<Product> products = productRepository.findAll();
		List<ProductsResponse> productsResponse = products.stream()
				.map(product -> new ProductsResponse(
						product.getId(),
						product.getBarCode(),
						product.getProductName(),
						product.getCategoryName(),
						product.getStatus(),
						product.getPrice(),
						product.getUnit(),
						product.getCreatedAt()))
				.toList();

		return new APICustomize<>(ApiError.OK.getCode(), ApiError.OK.getMessage(), productsResponse);
	}
	
	@Override
	public APICustomize<AddProductResponse> create(AddProductRequest request) {
	    if (productRepository.existsByBarCode(request.getBarCode())) {
	        throw new IllegalArgumentException("BarCode đã tồn tại, vui lòng nhập lại!");
	    }

	    Product newProduct = new Product();
	    newProduct.setBarCode(request.getBarCode());
	    newProduct.setProductName(request.getProductName());
	    newProduct.setCategoryName(request.getCategoryName());
	    newProduct.setPrice(request.getPrice());
	    newProduct.setUnit(request.getUnit());

	    productRepository.save(newProduct);

	    AddProductResponse response = new AddProductResponse(
	        newProduct.getId(),
	        newProduct.getBarCode(),
	        newProduct.getProductName(),
	        newProduct.getCategoryName(),
	        newProduct.getStatus(),
	        newProduct.getPrice(),
	        newProduct.getUnit()
	    );

	    return new APICustomize<>(ApiError.CREATED.getCode(), ApiError.CREATED.getMessage(), response);
	}

	@Override
	public APICustomize<ProductWithSKUsResponse> getProductWithSKUs(String barCode) {
	  
	    Product product = productRepository.findByBarCode(barCode);

	    if (product == null) {
	        return new APICustomize<>(ApiError.NOT_FOUND.getCode(),
	                "Không tìm thấy sản phẩm với barCode: " + barCode, null);
	    }

	    List<ProductSKU> productSKUs = productSKURepository.findByProduct_BarCode(barCode);

	    
	    List<ProductsSKUResponse> skuResponses = productSKUs.stream()
	            .map(sku -> new ProductsSKUResponse(
	                    sku.getSkuCode(),
	                    sku.getSkuName(),
	                    sku.getPrice()
	            ))
	            .toList();

	    
	    ProductWithSKUsResponse response = new ProductWithSKUsResponse(
	            product.getBarCode(),
	            product.getProductName(),
	            product.getCategoryName(),
	            product.getStatus(),
	            product.getPrice(),
	            product.getUnit(),
	            skuResponses
	    );

	    return new APICustomize<>(ApiError.OK.getCode(), ApiError.OK.getMessage(), response);
	}

	
//	@Override
//	public APICustomize<AddProductResponse> create(AddProductRequest request) {
//	    Product newProduct = new Product();
//	    newProduct.setBarCode(request.getBarCode());
//	    newProduct.setProductName(request.getProductName());
//	    newProduct.setCategoryName(request.getCategoryName());
//	    newProduct.setStatus(request.getStatus());
//	    newProduct.setPrice(request.getPrice());
//	    newProduct.setUnit(request.getUnit());
//
//	    productRepository.save(newProduct);
//
//	    AddProductResponse response = new AddProductResponse(
//	        newProduct.getId(),
//	        newProduct.getBarCode(),
//	        newProduct.getProductName(),
//	        newProduct.getCategoryName(),
//	        newProduct.getStatus(),
//	        newProduct.getPrice(),
//	        newProduct.getUnit()
//	    );
//
//	    return new APICustomize<>(ApiError.CREATED.getCode(), ApiError.CREATED.getMessage(), response);
//	}


//	@Override
//	public APICustomize<AddProductResponse> create(String barCode, String productName, String categoryName,
//			String status, BigDecimal price, String unit) {
//		Product newProduct = new Product();
//		newProduct.setBarCode(barCode);
//		newProduct.setProductName(productName);
//		newProduct.setCategoryName(categoryName);
//		newProduct.setStatus(status);
//		newProduct.setPrice(price);
//		newProduct.setUnit(unit);
//
//		productRepository.save(newProduct);
//
//		AddProductResponse response = new AddProductResponse(newProduct.getId(), newProduct.getBarCode(),
//				newProduct.getProductName(), newProduct.getCategoryName(), newProduct.getStatus(),
//				newProduct.getPrice(), newProduct.getUnit());
//
//		return new APICustomize<>(ApiError.CREATED.getCode(), ApiError.CREATED.getMessage(), response);
//
//	}
	
	@Override
    public APICustomize<AddProductSKUResponse> createSKU(List<AddProductSKURequest> listProductSku,String barcode) {
        Product product = productRepository.findByBarCode(barcode);
        if (product == null) {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }

        if(listProductSku == null || listProductSku.size() == 0) {
        	  throw new RuntimeException("Sản phẩm không tồn tại");
        }
        ProductSKU productSKU = null;
        for(AddProductSKURequest item: listProductSku) {
        	  productSKU = new ProductSKU();
              productSKU.setSkuCode(item.getSkuCode());
              productSKU.setSkuName(item.getSkuName());
              productSKU.setPrice(item.getPrice());
              productSKU.setProduct(product); 
              productSKURepository.save(productSKU);
        }
      

//        AddProductSKUResponse response = new AddProductSKUResponse(
//                savedProductSKU.getId(),
//                savedProductSKU.getSkuCode(),
//                savedProductSKU.getSkuName(),
//                savedProductSKU.getPrice(),
//                savedProductSKU.getProduct().getBarCode(),
//                "SKU tạo thành công"
//        );

        return new APICustomize<>(ApiError.OK.getCode(), ApiError.OK.getMessage(), null);
    }

    @Override
    public APICustomize<List<ProductSKU>> getProductSKUsByBarCode(String barCode) {
        
        List<ProductSKU> productSKUs = productSKURepository.findByProduct_BarCode(barCode);
        return new APICustomize<>(ApiError.OK.getCode(), ApiError.OK.getMessage(), productSKUs);
    }

	@Override
	public APICustomize<UpdateProductResponse> update(
			Integer id,
			String barCode,
			String productName,
			String categoryName,
			String status,
			BigDecimal price,
			String unit
			) 
	{

		Product product = productRepository.findById(id).orElse(null); 

		if (product == null) {

			return new APICustomize<>(ApiError.NOT_FOUND.getCode(), "Không tìm thấy sản phẩm!", null); 
		}
		product.setBarCode(barCode);
		product.setProductName(productName);
		product.setCategoryName(categoryName);
		product.setStatus(status);
		product.setPrice(price);
		product.setUnit(unit);

		productRepository.save(product);

		UpdateProductResponse response = new UpdateProductResponse(
		product.getId(),
		product.getBarCode(),
		product.getProductName(),
		product.getCategoryName(),
		product.getStatus(),
		product.getPrice(),
		product.getUnit());

		return new APICustomize<>(ApiError.OK.getCode(), "Cập nhật sản phẩm thành công!", response);
	}

	@Override
	public APICustomize<DeleteProductResponse> delete(Integer id) {
		productRepository.deleteById(id);
		return new APICustomize<>(ApiError.OK.getCode(), "Xóa sản phẩm thành công!", null);
	}


	@Override
	public APICustomize<DetailProductResponse> detail(Integer id) {

		Product product = productRepository.findById(id).orElse(null);

		if (product == null) {
			return new APICustomize<>(ApiError.NOT_FOUND.getCode(), "Không tìm thấy sản phẩm!", null);
		}

		DetailProductResponse response = new DetailProductResponse(product.getId(), product.getBarCode(),
				product.getProductName(), product.getCategoryName(), product.getStatus(), product.getPrice(),
				product.getUnit(), product.getCreatedAt());

		return new APICustomize<>(ApiError.OK.getCode(), ApiError.OK.getMessage(), response);
	}


	@Override
	public void createProduct(AddProductRequest addProductRequest) {
	    if (productRepository.existsByBarCode(addProductRequest.getBarCode())) {
	        throw new IllegalArgumentException("BarCode đã tồn tại, vui lòng nhập lại!");
	    }

	    Product newProduct = new Product();
	    newProduct.setBarCode(addProductRequest.getBarCode());
	    newProduct.setProductName(addProductRequest.getProductName());
	    newProduct.setCategoryName(addProductRequest.getCategoryName());
	    newProduct.setPrice(addProductRequest.getPrice());
	    newProduct.setUnit(addProductRequest.getUnit());
	    newProduct.setStatus(addProductRequest.getStatus());

	    productRepository.save(newProduct);
	}


	
}
