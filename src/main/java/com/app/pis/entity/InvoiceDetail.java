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





    public InvoiceDetail() {}
    public InvoiceDetail(Integer id, Medicine medicine, BigDecimal totalAmound, Integer quantity, Invoice invoice) {
        this.id = id;
        this.medicine = medicine;
        this.totalAmound = totalAmound;
        this.quantity = quantity;
        this.invoice = invoice;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public Medicine getMedicine() { return this.medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }
    public BigDecimal getTotalAmound() { return this.totalAmound; }
    public void setTotalAmound(BigDecimal totalAmound) { this.totalAmound = totalAmound; }
    public Integer getQuantity() { return this.quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Invoice getInvoice() { return this.invoice; }
    public void setInvoice(Invoice invoice) { this.invoice = invoice; }
}