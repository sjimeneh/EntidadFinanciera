package com.prueba.entidad.financiera.controller;

import com.prueba.entidad.financiera.entity.Customer;
import com.prueba.entidad.financiera.exception.CustomException;
import com.prueba.entidad.financiera.service.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    ICustomerService _iCustomerService;

    @PostMapping("/create")
    public ResponseEntity<?> CreateCustomer(@Valid @RequestBody Customer customer){

        try {
            _iCustomerService.Save(customer);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Customer> FindCustomerByID(@PathVariable Long id){

        Customer ResponseCustomer =  _iCustomerService.GetById(id);

        if(ResponseCustomer == null){
            return new ResponseEntity<Customer>(ResponseCustomer, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(ResponseCustomer, HttpStatus.OK);
    }

    @GetMapping("/find/all")
    public List<Customer> FindAllCustomer(){
        return _iCustomerService.GetAll();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> UpdateCustomer(@PathVariable Long id,@Valid @RequestBody Customer customer){

        if(!_iCustomerService.ExistById(id)){
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }

        Customer ResponseCustomer =  _iCustomerService.Update(id,customer);
        return new ResponseEntity<Customer>(ResponseCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteCustomer(@PathVariable Long id) {
        Customer customer = _iCustomerService.GetById(id);

        if(customer==null){
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }

        if (_iCustomerService.Delete(id)) {
            return ResponseEntity.ok("Cliente eliminado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede eliminar el Cliente: Tiene productos asociados");
        }
    }
}
