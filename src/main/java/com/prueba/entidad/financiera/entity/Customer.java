package com.prueba.entidad.financiera.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Auditable {
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

        @OneToMany(mappedBy = "customer")
        @JsonIgnoreProperties(value="customer")
        private List<Product> products;

        // Método para verificar si el cliente es mayor de edad
        @JsonIgnore
        public boolean isAdult() {
                LocalDate today = LocalDate.now();
                LocalDate birthDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int age = Period.between(birthDate, today).getYears();
                return age >= 18;
        }

}
