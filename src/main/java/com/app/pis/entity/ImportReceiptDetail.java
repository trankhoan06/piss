package com.app.pis.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "import_receipt_detail")
public class ImportReceiptDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "importr_eceipt_id", referencedColumnName = "id")
    private ImportReceipt importReceipt;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "purchase_price", precision = 12, scale = 2)
    private BigDecimal purchasePrice;



}
