package com.prueba.entidad.financiera.service;

import com.prueba.entidad.financiera.entity.Product;
import com.prueba.entidad.financiera.entity.enums.ProductType;
import com.prueba.entidad.financiera.entity.enums.StatusProduct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public interface IProductService {
    List<Product> GetAll();
    Product GetById(Long id);
    Product Save(Product entity);
    Product UpdateBalance(Long id, BigDecimal newBalance);
    Product UpdateStatus(Long id, StatusProduct status);

    boolean Delete(Long id);
    boolean ExistById(Long id);


}
