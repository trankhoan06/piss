package com.app.pis.repository;


import com.app.pis.entity.InvoiceDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {
    @Query("SELECT d.medicine.id, d.medicine.name, SUM(d.quantity) as totalQty FROM InvoiceDetail d JOIN d.invoice i WHERE i.status = 'ACCEPTED' GROUP BY d.medicine.id, d.medicine.name ORDER BY totalQty DESC")
    List<Object[]> findTopSellingMedicines(Pageable pageable);
}
