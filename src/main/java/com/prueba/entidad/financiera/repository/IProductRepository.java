package com.prueba.entidad.financiera.repository;

import com.prueba.entidad.financiera.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface IProductRepository extends CrudRepository<Product,Long> {
}
