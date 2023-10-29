package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Facultad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IFacultadService {
    Page<Facultad> findAll(Pageable pageable);
    Optional<Facultad> findByAbreviaturaOrNombre(String abreviatura, String nombre);
    Optional<Facultad> findByAbreviatura(String abreviatura);
    Facultad findByAbreviaturaOrThrowException(String abreviatura);
    Facultad create(Facultad facultad);
    Facultad update(Facultad facultad);
    void deleteById(Long id);
}
