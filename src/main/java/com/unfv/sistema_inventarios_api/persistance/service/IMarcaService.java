package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IMarcaService {
    Page<Marca> findAll(Pageable pageable);
    Optional<Marca> findByNombre(String nombre);
    Marca findByNombreOrThrowException(String nombre);
    Marca create(Marca categoria);
    Marca update(Marca categoria);
    void deleteById(Long id);
}
