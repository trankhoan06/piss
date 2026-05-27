package com.app.pis.repository;

import com.app.pis.entity.SupplierGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierGroupRepository extends JpaRepository<SupplierGroup, Integer> {
    boolean existsByName(String name);
}
