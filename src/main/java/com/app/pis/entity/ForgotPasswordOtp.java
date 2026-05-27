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


        return id;

        return email;

        return otp;

        return expiredAt;

        return used;

        return createdAt;

        this.id = id;

        this.email = email;

        this.otp = otp;

        this.expiredAt = expiredAt;

        this.used = used;

        this.createdAt = createdAt;
    public ForgotPasswordOtp() {}
    public ForgotPasswordOtp(Integer id, String email, String otp, Date expiredAt) {
        this.id = id;
        this.email = email;
        this.otp = otp;
        this.expiredAt = expiredAt;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }
    public String getOtp() { return this.otp; }
    public void setOtp(String otp) { this.otp = otp; }
    public Date getExpiredAt() { return this.expiredAt; }
    public void setExpiredAt(Date expiredAt) { this.expiredAt = expiredAt; }
}