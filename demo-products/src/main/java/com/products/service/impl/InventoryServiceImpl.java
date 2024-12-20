package com.products.service.impl;

import com.products.dto.request.AddInventoryRequest;
import com.products.dto.response.APICustomize;
import com.products.dto.response.InventoryResponse;
import com.products.dto.response.UpdateInventoryResponse;
import com.products.enums.ApiError;
import com.products.model.Inventory;
import com.products.model.Product;
import com.products.repository.InventoryRepository;
import com.products.repository.ProductRepository;
import com.products.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public APICustomize<List<InventoryResponse>> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();

        List<InventoryResponse> responses = inventories.stream()
                .map(inventory -> new InventoryResponse(
                        inventory.getId(),
                        inventory.getProduct().getId(),  // Lấy productId từ Product
                        inventory.getProduct().getProductName(),  // Lấy productName từ Product
                        inventory.getQuantity(),
                        inventory.getLastUpdated()))
                .toList();

        return new APICustomize<>(ApiError.OK.getCode(), ApiError.OK.getMessage(), responses);
    }


    @Override
    public APICustomize<InventoryResponse> getInventoryByProductId(int productId) {
        Inventory inventory = inventoryRepository.findByProduct_Id(productId).orElse(null);

        if (inventory == null) {
            return new APICustomize<>("404", "Không tìm thấy kho với productId: " + productId, null);
        }

        // Sử dụng constructor chính xác
        InventoryResponse response = new InventoryResponse(
                inventory.getId(),
                inventory.getProduct().getId(),  // productId từ Product
                inventory.getProduct().getProductName(),  // productName từ Product
                inventory.getQuantity(),
                inventory.getLastUpdated()
        );

        return new APICustomize<>("200", "OK", response);
    }



    @Override
    public APICustomize<InventoryResponse> addInventory(AddInventoryRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElse(null);

        if (product == null) {
            return new APICustomize<>("404", "Không tìm thấy sản phẩm với ID: " + request.getProductId(), null);
        }

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(request.getQuantity());
        inventory.setLastUpdated(LocalDateTime.now());

        inventoryRepository.save(inventory);

        // Constructor của InventoryResponse cần đúng
        InventoryResponse response = new InventoryResponse(
        		inventory.getId(),
                inventory.getProduct().getId(),  // productId từ Product
                inventory.getProduct().getProductName(),  // productName từ Product
                inventory.getQuantity(),
                inventory.getLastUpdated()
        );

        return new APICustomize<>("201", "Thêm kho thành công!", response);
    }


    @Override
    public APICustomize<UpdateInventoryResponse> updateInventory(Long id, int quantity) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);

        if (inventory == null) {
            return new APICustomize<>("404", "Không tìm thấy kho với ID: " + id, null);
        }

        inventory.setQuantity(quantity);
        inventory.setLastUpdated(LocalDateTime.now());
        inventoryRepository.save(inventory);

        // Sử dụng constructor mới của UpdateInventoryResponse
        UpdateInventoryResponse response = new UpdateInventoryResponse(
                inventory.getId(),
                inventory.getProduct().getId(),  // productId từ Product
                inventory.getProduct().getProductName(),  // productName từ Product
                inventory.getQuantity(),
                inventory.getLastUpdated()
        );

        return new APICustomize<>("200", "Cập nhật kho thành công!", response);
    }


    @Override
    public APICustomize<Void> deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);

        if (inventory == null) {
            return new APICustomize<>("404", "Không tìm thấy kho với ID: " + id, null);
        }

        inventoryRepository.deleteById(id);
        return new APICustomize<>("200", "Xóa kho thành công!", null);
    }
}
