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
}
