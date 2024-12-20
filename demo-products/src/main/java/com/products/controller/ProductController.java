package com.products.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model; // Sửa import từ ch.qos.logback.core.model.Model thành org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.dto.request.AddProductRequest;
import com.products.dto.request.AddProductSKURequest;
import com.products.dto.request.UpdateProductRequest;
import com.products.dto.response.APICustomize;
import com.products.dto.response.AddProductResponse;
import com.products.dto.response.AddProductSKUResponse;
import com.products.dto.response.DeleteProductResponse;
import com.products.dto.response.DetailProductResponse;
import com.products.dto.response.ProductWithSKUsResponse;
import com.products.dto.response.ProductsResponse;
import com.products.dto.response.UpdateProductResponse;
import com.products.model.ProductSKU;
import com.products.service.ProductSKUService;
import com.products.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/api")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductSKUService productSKUService;
	

	@GetMapping("/getListProduct")
	public ResponseEntity<?> products() {
		APICustomize<List<ProductsResponse>> response = productService.products();
		return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response); 
																									
	}

	@PostMapping("/createProduct")
	public ResponseEntity<?> createProduct(@Valid @RequestBody AddProductRequest addProductRequest) {
		productService.createProduct(addProductRequest);
		return ResponseEntity.ok("Sản phẩm đã được tạo thành công");
	}


	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @RequestBody UpdateProductRequest request) {
		APICustomize<UpdateProductResponse> response = productService.update(id, request.getBarCode(),
				request.getProductName(), request.getCategoryName(), request.getStatus(), request.getPrice(),
				request.getUnit());
		return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
	}

	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {

		APICustomize<DeleteProductResponse> response = productService.delete(id);

		return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
	}

	@GetMapping("/detailProduct/{id}")
	public ResponseEntity<?> detail(@PathVariable int id) {
		APICustomize<DetailProductResponse> response = productService.detail(id);
		return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
	}

	@GetMapping("/getViewProduct")
	public ResponseEntity<APICustomize<List<ProductsResponse>>> getListProduct() {
		APICustomize<List<ProductsResponse>> response = productService.products();
		return ResponseEntity.ok(response);
	}

//---------------------------------SKU------------------------------

	@GetMapping("/{barCode}/skus")
	public ResponseEntity<?> getProductWithSKUs(@PathVariable String barCode) {
		APICustomize<ProductWithSKUsResponse> response = productService.getProductWithSKUs(barCode);
		return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
	}

	
	@PostMapping("/{barCode}/skus")
	public ResponseEntity<APICustomize<AddProductSKUResponse>> createProductSKU(@PathVariable String barCode,
			@RequestBody List<AddProductSKURequest> listProductSku) {
		APICustomize<AddProductSKUResponse> response = productService.createSKU(listProductSku,barCode);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	 @GetMapping("/getProductList")
	    public ResponseEntity<APICustomize<List<ProductsResponse>>> getProductList() {
	        APICustomize<List<ProductsResponse>> response = productService.products();
	        return ResponseEntity.ok(response);
	    }
}










//@GetMapping("/{barCode}/skus")
//public ResponseEntity<APICustomize<List<ProductSKU>>> getProductSKUsByBarCode(@PathVariable String barCode) {
//  APICustomize<List<ProductSKU>> response = productService.getProductSKUsByBarCode(barCode);
//  return ResponseEntity.ok(response);
//}

//    @PostMapping("/createProduct")
//    public ResponseEntity<?> createProduct(@RequestBody AddProductRequest addProductRequest) {
//        if (addProductRequest.getStatus() == null) {
//            return ResponseEntity.badRequest().body("Status không được để trống");
//        }
//       
//        productService.createProduct(addProductRequest);
//        return ResponseEntity.ok("Sản phẩm đã được tạo thành công");
//    }

//    @PostMapping("/createProduct")
//    public ResponseEntity<APICustomize<?>> create(@Valid @RequestBody AddProductRequest request) {
//        APICustomize<?> response = productService.create(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

//    @PostMapping("/createProduct")
//    public ResponseEntity<?> create(@Valid @RequestBody AddProductRequest request) {
//        return ResponseEntity.ok(productService.create(request));
//    }

//@GetMapping("/getSKUsByBarCode/{barCode}")
//public ResponseEntity<?> getProductSKUsByBarCode(@PathVariable String barCode) {
//  List<ProductSKU> productSKUs = productSKUService.getProductSKUsByBarCode(barCode);
//  if (productSKUs.isEmpty()) {
//      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy SKU cho barcode: " + barCode);
//  }
//  return ResponseEntity.ok(productSKUs);
//}
//
//
//@PostMapping("/createSKU")
//public ResponseEntity<?> createSKU(@Valid @RequestBody AddProductSKURequest request) {
//  AddProductSKUResponse response = productSKUService.createSKU(request);
//  return ResponseEntity.status(HttpStatus.CREATED).body(response);
//}

//@PostMapping("/createProduct")
//public ResponseEntity<?> create(@Valid @RequestBody AddProductRequest request) {
//  // Nếu validation thất bại, sẽ trả về lỗi tự động
//  AddProductResponse response = productService.create(request);
//  return ResponseEntity.ok(response);
//}

//@PostMapping("/createProduct")
//public ResponseEntity<?> create(@RequestBody AddProductRequest request) {
//  APICustomize<AddProductResponse> response = productService.create(request.getBarCode(),
//          request.getProductName(), request.getCategoryName(), request.getStatus(), request.getPrice(),
//          request.getUnit());
//  return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
//}