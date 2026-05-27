package com.app.pis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "note")
    private String note;



    public Unit() {}
    public Unit(Integer id, String name, String note) {
        this.id = id;
        this.name = name;
        this.note = note;
    }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getNote() { return this.note; }
    public void setNote(String note) { this.note = note; }
}