package com.app.pis.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "export_receipt_detail")
public class ExportReceiptDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;


    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "export_receipt_id", referencedColumnName = "id", nullable = false)
    private ExportReceipt exportReceipt;

}
