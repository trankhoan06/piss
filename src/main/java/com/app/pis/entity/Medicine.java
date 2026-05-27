package com.app.pis.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "medicines")
public class Medicine {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "active_ingredient", length = 255)
    private String activeIngredient;

    @Column(name = "manufacturer_name", length = 255)
    private String manufacturerName;

    @Column(name = "selling_price", precision = 12, scale = 2)
    private BigDecimal sellingPrice;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne ()
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn (name = "base_unit", nullable = false)
    private Unit baseUnit;

    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL)
    private List<MedicineUnit> medicineUnits;

    @Column(name = "status", length = 20)
    private String status;


    public Medicine() {}
    public Medicine(String id, String name, String activeIngredient, String manufacturerName, BigDecimal sellingPrice, String description, Category category, Unit baseUnit, List<MedicineUnit> medicineUnits, String status) {
        this.id = id;
        this.name = name;
        this.activeIngredient = activeIngredient;
        this.manufacturerName = manufacturerName;
        this.sellingPrice = sellingPrice;
        this.description = description;
        this.category = category;
        this.baseUnit = baseUnit;
        this.medicineUnits = medicineUnits;
        this.status = status;
    }
    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getActiveIngredient() { return this.activeIngredient; }
    public void setActiveIngredient(String activeIngredient) { this.activeIngredient = activeIngredient; }
    public String getManufacturerName() { return this.manufacturerName; }
    public void setManufacturerName(String manufacturerName) { this.manufacturerName = manufacturerName; }
    public BigDecimal getSellingPrice() { return this.sellingPrice; }
    public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }
    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }
    public Category getCategory() { return this.category; }
    public void setCategory(Category category) { this.category = category; }
    public Unit getBaseUnit() { return this.baseUnit; }
    public void setBaseUnit(Unit baseUnit) { this.baseUnit = baseUnit; }
    public List<MedicineUnit> getMedicineUnits() { return this.medicineUnits; }
    public void setMedicineUnits(List<MedicineUnit> medicineUnits) { this.medicineUnits = medicineUnits; }
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }
}