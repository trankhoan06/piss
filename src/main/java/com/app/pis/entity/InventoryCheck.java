package com.app.pis.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "inventory_check")
public class InventoryCheck {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "note")
    private String note;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "inventoryCheck")
    private List<InventoryCheckDetail> details;




}
