package com.prueba.entidad.financiera.repository;

import org.springframework.data.repository.CrudRepository;

public interface IGenericRepository <T,ID> extends CrudRepository<T,ID> {
}
