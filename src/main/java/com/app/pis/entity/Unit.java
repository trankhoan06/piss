package com.app.pis.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "unit")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Unit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;


}
