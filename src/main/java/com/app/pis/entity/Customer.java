package com.app.pis.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "year")
    private String year;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;


}
