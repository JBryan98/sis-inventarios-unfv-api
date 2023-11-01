package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Escuela;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IEscuelaService {
    Page<Escuela> findAll(Pageable pageable);
    Optional<Escuela> findByAbreviatura(String abrevitura);
    Optional<Escuela> findByNombre(String nombre);
    Escuela findByAbreviaturaOrThrowException(String abreviatura);
    Escuela create(Escuela escuela);
    Escuela update(Escuela escuela);
    void deleteById(Long id);

}
