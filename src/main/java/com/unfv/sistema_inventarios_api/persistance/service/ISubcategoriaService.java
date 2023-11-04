package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Subcategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ISubcategoriaService {
    Page<Subcategoria> findAll(Pageable pageable);
    Optional<Subcategoria> findByNombre(String nombre);
    Subcategoria findByNombreOrThrowException(String nombre);
    Subcategoria create(Subcategoria subcategoria);
    Subcategoria update(Subcategoria subcategoria);
    void deleteById(Long id);
}
