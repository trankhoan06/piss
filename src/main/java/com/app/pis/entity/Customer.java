package com.app.pis.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "year")
    private String year;

    @Column(name = "phone", nullable = false, length = 15, unique = true)
    private String phone;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "status", length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_group_id")
    private CustomerGroup customerGroup;

    public Customer() {}
    public Customer(Integer id, String fullName, String year, String phone, String email, String status, CustomerGroup customerGroup) {
        this.id = id;
        this.fullName = fullName;
        this.year = year;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.customerGroup = customerGroup;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public String getFullName() { return this.fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getYear() { return this.year; }
    public void setYear(String year) { this.year = year; }
    public String getPhone() { return this.phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }
    public CustomerGroup getCustomerGroup() { return this.customerGroup; }
    public void setCustomerGroup(CustomerGroup customerGroup) { this.customerGroup = customerGroup; }
}