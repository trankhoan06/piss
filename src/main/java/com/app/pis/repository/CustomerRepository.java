package com.app.pis.repository;

import com.app.pis.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    Page<Customer> findByFullNameContainingIgnoreCaseOrPhoneNumberContaining(String name, String phone, Pageable pageable);
}
