package com.prueba.entidad.financiera.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prueba.entidad.financiera.entity.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull
    @Min(value = 1,message = "El Monto no puede ser Inferior o igual a cero")
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime dateTransaction;

    @ManyToOne
    @JsonIgnoreProperties(value = "transactions")
    @JoinColumn(name = "origin_product_id")
    private Product originProduct;

    @ManyToOne
    @JsonIgnoreProperties(value = "transactions")
    @JoinColumn(name = "destination_account_id")
    private Product destinationProduct;

}
