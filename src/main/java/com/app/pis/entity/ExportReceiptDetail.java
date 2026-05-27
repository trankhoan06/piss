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


    public ExportReceiptDetail() {}
    public ExportReceiptDetail(Integer id, Inventory inventory, Integer quantity, String note, ExportReceipt exportReceipt) {
        this.id = id;
        this.inventory = inventory;
        this.quantity = quantity;
        this.note = note;
        this.exportReceipt = exportReceipt;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public Inventory getInventory() { return this.inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public Integer getQuantity() { return this.quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getNote() { return this.note; }
    public void setNote(String note) { this.note = note; }
    public ExportReceipt getExportReceipt() { return this.exportReceipt; }
    public void setExportReceipt(ExportReceipt exportReceipt) { this.exportReceipt = exportReceipt; }
}