package com.prueba.entidad.financiera.service;

import com.prueba.entidad.financiera.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ICustomerService{
    List<Customer> GetAll();
    Customer GetById(Long id);
    Customer Save(Customer entity);
    Customer Update(Long id, Customer entity);
    boolean Delete(Long id);

    boolean ExistById(Long id);

}
