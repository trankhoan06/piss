package com.app.pis.repository;


import com.app.pis.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, String> {

    Page<Medicine> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
