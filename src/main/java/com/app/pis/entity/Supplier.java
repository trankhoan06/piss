package com.app.pis.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", nullable = false, length = 15, unique = true)
    private String phone;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "status", length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_group_id")
    private SupplierGroup supplierGroup;

    public Supplier() {}
    public Supplier(Integer id, String name, String address, String phone, String email, String status, SupplierGroup supplierGroup) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.supplierGroup = supplierGroup;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return this.phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }
    public SupplierGroup getSupplierGroup() { return this.supplierGroup; }
    public void setSupplierGroup(SupplierGroup supplierGroup) { this.supplierGroup = supplierGroup; }
}