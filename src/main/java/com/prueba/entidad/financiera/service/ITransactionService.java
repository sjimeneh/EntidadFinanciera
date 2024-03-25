package com.prueba.entidad.financiera.service;

import com.prueba.entidad.financiera.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ITransactionService {
    List<Transaction> GetAll();
    Transaction GetById(Long id);
    Transaction Save(Transaction transaction);
    Transaction Update(Long id, Transaction transaction);
    boolean Delete(Long id);

    boolean ExistById(Long id);
}
