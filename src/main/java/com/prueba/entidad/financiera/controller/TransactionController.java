package com.prueba.entidad.financiera.controller;


import com.prueba.entidad.financiera.entity.Product;
import com.prueba.entidad.financiera.entity.Transaction;
import com.prueba.entidad.financiera.exception.CustomException;
import com.prueba.entidad.financiera.service.ITransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")

public class TransactionController {
    @Autowired
    ITransactionService _iTransactionService;
    @PostMapping("/create")
    public ResponseEntity<?> CreateTransaction(@Valid @RequestBody Transaction transaction){
        try{
            Transaction responseTransaction =  _iTransactionService.Save(transaction);
            return new ResponseEntity<>(responseTransaction, HttpStatus.CREATED);
        }catch (CustomException ce){
            return new ResponseEntity<>(ce.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Transaction> FindTransactionByID(@PathVariable Long id){

        Transaction ResponseTransaction =  _iTransactionService.GetById(id);

        if(ResponseTransaction == null){
            return new ResponseEntity<Transaction>(ResponseTransaction, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Transaction>(ResponseTransaction, HttpStatus.OK);
    }

    @GetMapping("/find/all")
    public List<Transaction> FindAllTransactions(){

        return _iTransactionService.GetAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteTransaction(@PathVariable Long id) {
        Transaction transaction = _iTransactionService.GetById(id);

        if(transaction==null){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        if (_iTransactionService.Delete(id)) {
            return ResponseEntity.ok("Producto eliminado con éxito");
        }else {
            return new ResponseEntity<String>("No se pudo eliminar la transacción", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
