package com.prueba.entidad.financiera.service;

import com.prueba.entidad.financiera.entity.Customer;
import com.prueba.entidad.financiera.entity.Product;
import com.prueba.entidad.financiera.repository.IGenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImplement implements IService<Customer>{
@Autowired
    IGenericRepository<Customer,Long> _iGenericRepository;

    @Override
    public List<Customer> GetAll() {
        List<Customer> customers = new ArrayList<>();
        _iGenericRepository.findAll().forEach(customers::add);
        return customers;
    }

    @Override
    public Customer GetById(Long id) {
        Optional<Customer> customer = _iGenericRepository.findById(id);
        return customer.orElse(null);
    }

    @Override
    public Customer Save(Customer entity) {
        return _iGenericRepository.save(entity);
    }

    @Override
    public Customer Update(Customer entity) {
        if(GetById(entity.getId()) != null){
            return _iGenericRepository.save(entity);
        }else{
            return null;
        }
    }

    @Override
    public boolean Delete(Long id) {
        Optional<Customer> customerOptional = _iGenericRepository.findById(id);
        if (customerOptional.isPresent()) {
            _iGenericRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
