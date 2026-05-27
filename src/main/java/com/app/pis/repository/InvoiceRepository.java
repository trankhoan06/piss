package com.app.pis.repository;

import com.app.pis.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Page<Invoice> findAll(Pageable pageable);

    @Query("SELECT SUM(i.totalAmound) FROM Invoice i WHERE i.status = 'ACCEPTED' AND i.saleDate >= :startDate AND i.saleDate < :endDate")
    BigDecimal sumRevenueByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(i) FROM Invoice i WHERE i.status = 'ACCEPTED' AND i.saleDate >= :startDate AND i.saleDate < :endDate")
    long countByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
