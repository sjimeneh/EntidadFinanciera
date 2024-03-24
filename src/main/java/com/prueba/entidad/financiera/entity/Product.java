package com.prueba.entidad.financiera.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prueba.entidad.financiera.entity.enums.AccountType;
import com.prueba.entidad.financiera.entity.enums.StatusAccount;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[0-9]+$")
    private String accountNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusAccount status;

    @NotNull
    @Min(0)
    private BigDecimal balance;

    @NotNull
    private Boolean exemptFromGmf; // Assuming Gmf refers to a fee or tax

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modificationDate;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties(value="products")
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = "product")
    private List<Transaction> transactions;

}
