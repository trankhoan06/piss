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

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "expiration_date")
    private java.time.LocalDate expirationDate;






    public ImportReceiptDetail() {}
    public ImportReceiptDetail(Integer id, Medicine medicine, ImportReceipt importReceipt, Integer quantity, BigDecimal purchasePrice, String batchNumber) {
        this.id = id;
        this.medicine = medicine;
        this.importReceipt = importReceipt;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.batchNumber = batchNumber;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public Medicine getMedicine() { return this.medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }
    public ImportReceipt getImportReceipt() { return this.importReceipt; }
    public void setImportReceipt(ImportReceipt importReceipt) { this.importReceipt = importReceipt; }
    public Integer getQuantity() { return this.quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPurchasePrice() { return this.purchasePrice; }
    public void setPurchasePrice(BigDecimal purchasePrice) { this.purchasePrice = purchasePrice; }
    public String getBatchNumber() { return this.batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
}