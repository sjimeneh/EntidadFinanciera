package com.prueba.entidad.financiera.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String identificationType;

        @Column(nullable = false, unique = true)
        private String identificationNumber;

        @Column(nullable = false)
        @Size(min = 2)
        private String firstName;

        @Column(nullable = false)
        @Size(min = 2)
        private String lastName;

        @Email
        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        @Temporal(TemporalType.DATE)
        private Date dateOfBirth;

        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private Date creationDate;

        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private Date modificationDate;

        @OneToMany(mappedBy = "customer")
        @JsonIgnoreProperties(value="customer")
        private List<Product> products;

}
