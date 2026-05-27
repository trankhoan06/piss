package com.app.pis.repository;

import com.app.pis.entity.CashReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashReceiptRepository extends JpaRepository<CashReceipt, Integer> {
}
