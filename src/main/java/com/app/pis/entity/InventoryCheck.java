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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "note")
    private String note;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "inventoryCheck")
    private List<InventoryCheckDetail> details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public InventoryCheck() {}
    public InventoryCheck(Integer id, String status, LocalDate date, String note, List<InventoryCheckDetail> details, User user) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.note = note;
        this.details = details;
        this.user = user;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getDate() { return this.date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getNote() { return this.note; }
    public void setNote(String note) { this.note = note; }
    public List<InventoryCheckDetail> getDetails() { return this.details; }
    public void setDetails(List<InventoryCheckDetail> details) { this.details = details; }
    public User getUser() { return this.user; }
    public void setUser(User user) { this.user = user; }
}