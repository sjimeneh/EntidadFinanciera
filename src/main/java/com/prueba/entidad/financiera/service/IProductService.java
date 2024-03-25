package com.prueba.entidad.financiera.service;

import com.prueba.entidad.financiera.entity.Customer;
import com.prueba.entidad.financiera.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IProdcutService {
    List<Product> GetAll();
    Product GetById(Long id);
    Product Save(Product entity);
    Product Update(Long id, Product entity);
    boolean Delete(Long id);
    boolean ExistById(Long id);


}
