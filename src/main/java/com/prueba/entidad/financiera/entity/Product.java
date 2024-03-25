package com.prueba.entidad.financiera.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prueba.entidad.financiera.entity.enums.ProductType;
import com.prueba.entidad.financiera.entity.enums.StatusProduct;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(nullable = false,length = 10,unique = true,updatable = false)
    private String productNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusProduct status;

    @NotNull
    @Min(0)
    private BigDecimal balance;

    @NotNull
    private Boolean exemptFromGmf;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties(value="products")
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "originProduct")
    @JsonIgnoreProperties(value = "originProduct")
    private List<Transaction> transactions;


}
