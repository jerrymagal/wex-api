package br.com.wex.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "purchase_transaction")
@Data
public class PurchaseTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "UUID")
    private UUID uuid;

    @Column(length = 50, nullable = false)
    private String description;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "purchase_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal purchaseAmount;

    @PrePersist
    public void initializeUUID() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
