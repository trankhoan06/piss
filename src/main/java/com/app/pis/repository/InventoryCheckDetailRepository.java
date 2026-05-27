package com.app.pis.repository;

import com.app.pis.entity.Inventory;
import com.app.pis.entity.InventoryCheckDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryCheckDetailRepository extends JpaRepository<InventoryCheckDetail, Integer> {
}
