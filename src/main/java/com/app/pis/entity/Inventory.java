package com.app.pis.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "import_price", precision = 12, scale = 2)
    private BigDecimal importPrice;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;

    @ManyToOne
    @JoinColumn (name = "supplier", referencedColumnName = "id")
    private Supplier supplier;








    public Inventory() {}
    public Inventory(Long id, BigDecimal importPrice, LocalDate expirationDate, String batchNumber, Integer stockQuantity, Medicine medicine, Supplier supplier) {
        this.id = id;
        this.importPrice = importPrice;
        this.expirationDate = expirationDate;
        this.batchNumber = batchNumber;
        this.stockQuantity = stockQuantity;
        this.medicine = medicine;
        this.supplier = supplier;
    }
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }
    public BigDecimal getImportPrice() { return this.importPrice; }
    public void setImportPrice(BigDecimal importPrice) { this.importPrice = importPrice; }
    public LocalDate getExpirationDate() { return this.expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    public String getBatchNumber() { return this.batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
    public Integer getStockQuantity() { return this.stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    public Medicine getMedicine() { return this.medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }
    public Supplier getSupplier() { return this.supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }
}