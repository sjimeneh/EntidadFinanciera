package com.prueba.entidad.financiera.controller;


import com.prueba.entidad.financiera.entity.Transaction;
import com.prueba.entidad.financiera.exception.CustomException;
import com.prueba.entidad.financiera.service.ITransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
