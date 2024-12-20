package com.products.controller;

import com.products.dto.request.AddInventoryRequest;
import com.products.dto.request.UpdateInventoryRequest;
import com.products.dto.response.APICustomize;
import com.products.dto.response.CustomerResponse;
import com.products.dto.response.InventoryResponse;
import com.products.dto.response.UpdateInventoryResponse;
import com.products.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v3/api")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/getListInventory")
    public ResponseEntity<APICustomize<List<InventoryResponse>>> getAllInventories() {
        APICustomize<List<InventoryResponse>> response = inventoryService.getAllInventories();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getInventory/{productId}")
    public ResponseEntity<APICustomize<InventoryResponse>> getInventoryByProductId(@PathVariable int productId) {
        APICustomize<InventoryResponse> response = inventoryService.getInventoryByProductId(productId);
        return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
    }


    @PostMapping("/createInventory")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<APICustomize<InventoryResponse>> addInventory(@RequestBody AddInventoryRequest request) {
        APICustomize<InventoryResponse> response = inventoryService.addInventory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

   
    @PutMapping("/updateInventory/{id}")
    public ResponseEntity<APICustomize<UpdateInventoryResponse>> updateInventory(
            @PathVariable Long id,
            @RequestBody UpdateInventoryRequest request) {
        APICustomize<UpdateInventoryResponse> response = inventoryService.updateInventory(id, request.getQuantity());
        return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
    }

   
    @DeleteMapping("/deleteInventory/{id}")
    public ResponseEntity<APICustomize<Void>> deleteInventory(@PathVariable Long id) {
        APICustomize<Void> response = inventoryService.deleteInventory(id);
        return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
    }
    
   
}


//@GetMapping("/getViewInventory")
//public ResponseEntity<APICustomize<List<InventoryResponse>>> getListInventory() {
//	APICustomize<List<InventoryResponse>> response = inventoryService.getAllInventories();
//	return ResponseEntity.ok(response);
//}
