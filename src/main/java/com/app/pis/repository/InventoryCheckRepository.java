package com.app.pis.repository;

import com.app.pis.entity.Inventory;
import com.app.pis.entity.InventoryCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryCheckRepository extends JpaRepository<InventoryCheck, Integer> {
}
