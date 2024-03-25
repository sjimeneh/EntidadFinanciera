package com.prueba.entidad.financiera.service;

import com.prueba.entidad.financiera.entity.enums.ProductType;
import com.prueba.entidad.financiera.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProducServiceImplementTest {

    @Test
    void generateAccountNumber() {
        ProductType type = ProductType.CURRENT_ACCOUNT;
        String prefix = switch (type) {
            case CURRENT_ACCOUNT -> "33";
            case SAVINGS_ACCOUNT -> "53";
            default -> throw new CustomException("Tipo de producto no válido");
        };
        int randomNumber = (int) (Math.random() * (99999999 - 10000000 + 1)) + 10000000;
        String ProductNumber = prefix + randomNumber;

        System.out.println("Tamaño Product Number"+ProductNumber);
    }
}