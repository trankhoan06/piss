package com.app.pis.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sale_date")
    private LocalDateTime saleDate;

    @Column(name = "total_amound", precision = 12, scale = 2)
    private BigDecimal totalAmound;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "invoice")
    private List<InvoiceDetail> invoiceDetails;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(name = "status", length = 20)
    private String status;




    public Invoice() {}
    public Invoice(Integer id, LocalDateTime saleDate, BigDecimal totalAmound, User user, List<InvoiceDetail> invoiceDetails, Customer customer, String status) {
        this.id = id;
        this.saleDate = saleDate;
        this.totalAmound = totalAmound;
        this.user = user;
        this.invoiceDetails = invoiceDetails;
        this.customer = customer;
        this.status = status;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDateTime getSaleDate() { return this.saleDate; }
    public void setSaleDate(LocalDateTime saleDate) { this.saleDate = saleDate; }
    public BigDecimal getTotalAmound() { return this.totalAmound; }
    public void setTotalAmound(BigDecimal totalAmound) { this.totalAmound = totalAmound; }
    public User getUser() { return this.user; }
    public void setUser(User user) { this.user = user; }
    public List<InvoiceDetail> getInvoiceDetails() { return this.invoiceDetails; }
    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) { this.invoiceDetails = invoiceDetails; }
    public Customer getCustomer() { return this.customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }
}