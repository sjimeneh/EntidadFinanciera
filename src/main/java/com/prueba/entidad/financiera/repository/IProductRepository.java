package com.prueba.entidad.financiera.repository;

import com.prueba.entidad.financiera.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface IProductRepository extends CrudRepository<Product,Long> {

    Optional<Product> findByProductNumber(String productNumber);
}
