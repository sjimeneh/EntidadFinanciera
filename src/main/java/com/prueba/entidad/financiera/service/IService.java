package com.prueba.entidad.financiera.service;

import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
@Service
public interface IService <T>{
    List<T> GetAll();
    T GetById(Long id);
    T Save(T entity);
    T Update(T entity);
    boolean Delete(Long id);

}
