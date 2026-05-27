package com.app.pis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory_check_detail" )
public class InventoryCheckDetail {
    @Id
    @Column (name = "id")
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

    @Column(name = "actualQuantity")
    private Integer actualQuantity;

    @Column(name = "inventoryQuantity")
    private Integer inventoryQuantity;

    @ManyToOne
    @JoinColumn(name = "inventory_check_id")
    private InventoryCheck inventoryCheck;

    public InventoryCheckDetail() {}
    public InventoryCheckDetail(Integer id, Inventory inventory, Integer actualQuantity, Integer inventoryQuantity, InventoryCheck inventoryCheck) {
        this.id = id;
        this.inventory = inventory;
        this.actualQuantity = actualQuantity;
        this.inventoryQuantity = inventoryQuantity;
        this.inventoryCheck = inventoryCheck;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public Inventory getInventory() { return this.inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public Integer getActualQuantity() { return this.actualQuantity; }
    public void setActualQuantity(Integer actualQuantity) { this.actualQuantity = actualQuantity; }
    public Integer getInventoryQuantity() { return this.inventoryQuantity; }
    public void setInventoryQuantity(Integer inventoryQuantity) { this.inventoryQuantity = inventoryQuantity; }
    public InventoryCheck getInventoryCheck() { return this.inventoryCheck; }
    public void setInventoryCheck(InventoryCheck inventoryCheck) { this.inventoryCheck = inventoryCheck; }
}