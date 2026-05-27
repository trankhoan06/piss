package com.app.pis.entity;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Date;

@Entity
@Table(name = "forgot_password_otp")
public class ForgotPasswordOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 6)
    private String otp;

    @Column(name = "expired_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredAt;

    @Column(nullable = false)
    private Boolean used = false;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    public ForgotPasswordOtp() {
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public Boolean getUsed() {
        return used;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}