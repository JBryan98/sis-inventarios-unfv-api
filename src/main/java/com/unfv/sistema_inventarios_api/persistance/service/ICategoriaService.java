package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICategoriaService {
    Page<Categoria> findAll(Pageable pageable);
    Optional<Categoria> findByNombre(String nombre);
    Categoria findByNombreOrThrowException(String nombre);
    Categoria create(Categoria categoria);
    Categoria update(Categoria categoria);
    void deleteById(Long id);
}
