package com.prueba.entidad.financiera.service;

import com.prueba.entidad.financiera.entity.Customer;
import com.prueba.entidad.financiera.exception.CustomException;
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
        if (!entity.isAdult()) {
            throw new CustomException("El cliente debe ser mayor de edad.");
        }
       return _iCustomerRepository.save(entity);
    }


    @Override
    public Customer Update(Long id,Customer entity) {
        // Verificar si el cliente existe en la base de datos
        Optional<Customer> existingCustomerOptional = _iCustomerRepository.findById(id);

        // Si el cliente existe, actualiza sus datos
        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();
            existingCustomer.setFirstName(entity.getFirstName());
            existingCustomer.setLastName(entity.getLastName());
            existingCustomer.setDateOfBirth(entity.getDateOfBirth());
            existingCustomer.setEmail(entity.getEmail());
            existingCustomer.setIdentificationNumber(entity.getIdentificationNumber());
            existingCustomer.setIdentificationType(entity.getIdentificationType());

            return _iCustomerRepository.save(existingCustomer);
        } else {
            // Si el cliente no existe, retorna null o maneja el caso según tu lógica de negocio
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

    @Override
    public boolean ExistById(Long id) {
        return _iCustomerRepository.existsById(id);
    }
}
