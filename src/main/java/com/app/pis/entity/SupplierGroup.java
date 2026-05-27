package com.app.pis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "supplier_group")
public class SupplierGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public SupplierGroup() {}
    public SupplierGroup(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }
}