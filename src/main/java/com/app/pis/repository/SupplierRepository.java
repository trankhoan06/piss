package com.app.pis.repository;

import com.app.pis.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query("select supplier from Supplier supplier")
    Stream<Supplier> getAll ();
}
