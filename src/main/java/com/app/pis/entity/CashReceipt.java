package com.app.pis.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cash_receipt")
public class CashReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "receipt_date")
    private LocalDateTime receiptDate;

    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;

    // e.g. "INCOME", "EXPENSE"
    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Reference to other entities like Invoice, ImportReceipt, etc.
    @Column(name = "reference_type", length = 50)
    private String referenceType;

    @Column(name = "reference_id")
    private Integer referenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public CashReceipt() {}
    public CashReceipt(Integer id, LocalDateTime receiptDate, BigDecimal amount, String type, String description, String referenceType, Integer referenceId, User user) {
        this.id = id;
        this.receiptDate = receiptDate;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.user = user;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDateTime getReceiptDate() { return this.receiptDate; }
    public void setReceiptDate(LocalDateTime receiptDate) { this.receiptDate = receiptDate; }
    public BigDecimal getAmount() { return this.amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getType() { return this.type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }
    public String getReferenceType() { return this.referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }
    public Integer getReferenceId() { return this.referenceId; }
    public void setReferenceId(Integer referenceId) { this.referenceId = referenceId; }
    public User getUser() { return this.user; }
    public void setUser(User user) { this.user = user; }
}