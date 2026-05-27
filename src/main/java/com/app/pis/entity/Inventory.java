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





}
