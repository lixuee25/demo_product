package com.products.service;

import com.products.dto.request.AddInventoryRequest;
import com.products.dto.response.InventoryResponse;
import com.products.dto.response.APICustomize;
import com.products.dto.response.UpdateInventoryResponse;

import java.util.List;

public interface InventoryService {

    APICustomize<List<InventoryResponse>> getAllInventories();
    
    APICustomize<InventoryResponse> getInventoryByProductId(int productId);

    APICustomize<InventoryResponse> addInventory(AddInventoryRequest request);

    APICustomize<UpdateInventoryResponse> updateInventory(Long id, int quantity);

    APICustomize<Void> deleteInventory(Long id);
}
