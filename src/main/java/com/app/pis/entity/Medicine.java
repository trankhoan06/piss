package com.app.pis.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "medicines")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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


}