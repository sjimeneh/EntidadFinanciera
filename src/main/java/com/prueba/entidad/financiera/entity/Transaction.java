package com.prueba.entidad.financiera.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prueba.entidad.financiera.entity.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTransaction;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties(value = "transactions")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties(value = "transactions")
    @JoinColumn(name = "destination_account_id")
    private Product destinationProduct;

}
