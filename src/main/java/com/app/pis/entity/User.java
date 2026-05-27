package com.app.pis.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_users_phone", columnNames = "phone"),
                @UniqueConstraint(name = "uk_users_cccd", columnNames = "cccd")
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Email
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Email format is invalid"
    )
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(name = "cccd", nullable = false, length = 12)
    private String cccd;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @Column(name = "first_login")
    @Builder.Default
    private Boolean firstLogin = true;
    public User() {}
    public User(Integer id, String firstName, String lastName, String phone, String email, LocalDate birthDay, String cccd, String status, String address, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.birthDay = birthDay;
        this.cccd = cccd;
        this.status = status;
        this.address = address;
        this.password = password;
        this.role = role;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public String getFirstName() { return this.firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return this.lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return this.phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getBirthDay() { return this.birthDay; }
    public void setBirthDay(LocalDate birthDay) { this.birthDay = birthDay; }
    public String getCccd() { return this.cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }
    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return this.role; }
    public void setRole(String role) { this.role = role; }
}