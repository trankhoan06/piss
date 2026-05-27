package com.app.pis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cash_receipt")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CashReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "receipt_date")
    private LocalDateTime receiptDate;

    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;

    // e.g. "INCOME", "EXPENSE"
    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Reference to other entities like Invoice, ImportReceipt, etc.
    @Column(name = "reference_type", length = 50)
    private String referenceType;

    @Column(name = "reference_id")
    private Integer referenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
