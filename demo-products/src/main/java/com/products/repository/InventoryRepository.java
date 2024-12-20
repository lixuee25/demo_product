package com.products.repository;

import com.products.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	
    Optional<Inventory> findByProduct_Id(int productId);
}
