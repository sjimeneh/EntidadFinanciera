package com.prueba.entidad.financiera.repository;

import com.prueba.entidad.financiera.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface ICustomerRepository extends CrudRepository<Customer,Long> {
}
