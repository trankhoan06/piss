package com.app.pis.repository;

import com.app.pis.entity.CustomerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerGroupRepository extends JpaRepository<CustomerGroup, Integer> {
    boolean existsByName(String name);
}
