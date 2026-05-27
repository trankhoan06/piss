package com.app.pis.entity;

import jakarta.persistence.*;

import java.util.List;

import java.time.LocalDate;

@Entity
@Table(name = "export_receipt")
public class ExportReceipt {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "issue_reason")
    private String issueReason;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "status", length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany (mappedBy = "exportReceipt")
    private List<ExportReceiptDetail> exportReceiptDetails;

    public ExportReceipt() {}
    public ExportReceipt(Integer id, String issueReason, LocalDate date, String type, String status, User user, List<ExportReceiptDetail> exportReceiptDetails) {
        this.id = id;
        this.issueReason = issueReason;
        this.date = date;
        this.type = type;
        this.status = status;
        this.user = user;
        this.exportReceiptDetails = exportReceiptDetails;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public String getIssueReason() { return this.issueReason; }
    public void setIssueReason(String issueReason) { this.issueReason = issueReason; }
    public LocalDate getDate() { return this.date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getType() { return this.type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }
    public User getUser() { return this.user; }
    public void setUser(User user) { this.user = user; }
    public List<ExportReceiptDetail> getExportReceiptDetails() { return this.exportReceiptDetails; }
    public void setExportReceiptDetails(List<ExportReceiptDetail> exportReceiptDetails) { this.exportReceiptDetails = exportReceiptDetails; }
}