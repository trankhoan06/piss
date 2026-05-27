package com.app.pis.repository;

import com.app.pis.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByMedicineIdAndSupplierIdAndBatchNumber(String medicineId, Integer supplierId, String batchNumber);
    List<Inventory> findByMedicineIdAndStockQuantityGreaterThanOrderByExpirationDateAsc(String medicineId, Integer quantity);
}
