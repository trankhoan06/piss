package com.app.pis.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "export_receipt")
public class ExportReceipt {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "issue_reason")
    private String issueReason;

    @OneToMany (mappedBy = "exportReceipt")
    private List<ExportReceiptDetail> exportReceiptDetails;
}
