package com.prueba.entidad.financiera.controller;

import com.prueba.entidad.financiera.entity.Customer;
import com.prueba.entidad.financiera.service.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    ICustomerService _iCustomerService;
@PostMapping("/create")
    public Customer CreateCustomer(@Valid @RequestBody Customer customer){
        return _iCustomerService.Save(customer);
    }

    @GetMapping("/find/{id}")
    public Customer CreateCustomer(@PathVariable Long id){
        return _iCustomerService.GetById(id);
    }
}
