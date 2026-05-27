package com.app.pis.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "invoice_detail")
public class InvoiceDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;

    @Column(name = "total_amound", precision = 12, scale = 2)
    private BigDecimal totalAmound;

    @Column(name = "quantity")
    private Integer quantity;


    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;




}
