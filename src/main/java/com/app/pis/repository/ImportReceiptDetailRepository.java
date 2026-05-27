package com.app.pis.repository;

import com.app.pis.entity.ImportReceipt;
import com.app.pis.entity.ImportReceiptDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportReceiptDetailRepository extends JpaRepository<ImportReceiptDetail, Integer> {
}
