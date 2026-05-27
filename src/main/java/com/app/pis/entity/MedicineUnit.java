package com.app.pis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "medicine_unit")
public class MedicineUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @Column(name = "conversion_rate", nullable = false)
    private Integer conversionRate;

    @Column(name = "note")
    private String note;
    public MedicineUnit() {}
    public MedicineUnit(Long id, Medicine medicine, Unit unit, Integer conversionRate, String note) {
        this.id = id;
        this.medicine = medicine;
        this.unit = unit;
        this.conversionRate = conversionRate;
        this.note = note;
    }
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }
    public Medicine getMedicine() { return this.medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }
    public Unit getUnit() { return this.unit; }
    public void setUnit(Unit unit) { this.unit = unit; }
    public Integer getConversionRate() { return this.conversionRate; }
    public void setConversionRate(Integer conversionRate) { this.conversionRate = conversionRate; }
    public String getNote() { return this.note; }
    public void setNote(String note) { this.note = note; }
}