package com.app.pis.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "import_receipt")
public class ImportReceipt {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @OneToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "importReceipt")
    private List<ImportReceiptDetail> importReceiptDetails;

    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "status", length = 20)
    private String status;



    public ImportReceipt() {}
    public ImportReceipt(Integer id, LocalDate date, BigDecimal totalAmount, User user, Supplier supplier, List<ImportReceiptDetail> importReceiptDetails, String type, String status) {
        this.id = id;
        this.date = date;
        this.totalAmount = totalAmount;
        this.user = user;
        this.supplier = supplier;
        this.importReceiptDetails = importReceiptDetails;
        this.type = type;
        this.status = status;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDate getDate() { return this.date; }
    public void setDate(LocalDate date) { this.date = date; }
    public BigDecimal getTotalAmount() { return this.totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public User getUser() { return this.user; }
    public void setUser(User user) { this.user = user; }
    public Supplier getSupplier() { return this.supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }
    public List<ImportReceiptDetail> getImportReceiptDetails() { return this.importReceiptDetails; }
    public void setImportReceiptDetails(List<ImportReceiptDetail> importReceiptDetails) { this.importReceiptDetails = importReceiptDetails; }
    public String getType() { return this.type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }
}