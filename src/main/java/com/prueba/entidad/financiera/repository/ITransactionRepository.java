package com.prueba.entidad.financiera.repository;

import com.prueba.entidad.financiera.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface ITransactionRepository extends CrudRepository<Transaction,Long> {
}
