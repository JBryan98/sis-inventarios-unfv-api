package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Modelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IModeloService {
    Page<Modelo> findAll(Pageable pageable);
    Optional<Modelo> findById(Long id);
    Modelo findByIdOrThrowException(Long id);
    Optional<Modelo> findByNombre(String nombre);
    Modelo findByNombreOrThrowException(String nombre);
    Modelo create(Modelo modelo);
    Modelo update(Modelo modelo);
    void deleteById(Long id);
}
