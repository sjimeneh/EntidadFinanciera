package com.prueba.entidad.financiera.service;

import com.prueba.entidad.financiera.entity.Customer;
import com.prueba.entidad.financiera.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImplement implements ICustomerService {
@Autowired
ICustomerRepository _iCustomerRepository;

    @Override
    public List<Customer> GetAll() {
        List<Customer> customers = new ArrayList<>();
        _iCustomerRepository.findAll().forEach(customers::add);
        return customers;
    }

    @Override
    public Customer GetById(Long id) {
        Optional<Customer> customer = _iCustomerRepository.findById(id);
        return customer.orElse(null);
    }

    @Override
    public Customer Save(Customer entity) {
        return _iCustomerRepository.save(entity);
    }

    @Override
    public Customer Update(Customer entity) {
        if(GetById(entity.getId()) != null){
            return _iCustomerRepository.save(entity);
        }else{
            return null;
        }
    }

    @Override
    public boolean Delete(Long id) {
        Optional<Customer> customerOptional = _iCustomerRepository.findById(id);
        if (customerOptional.isPresent()) {
            _iCustomerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
