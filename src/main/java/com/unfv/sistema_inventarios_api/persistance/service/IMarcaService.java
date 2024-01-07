package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Marca;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.MarcaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IMarcaService {
    List<Marca> findAllNoPage(MarcaSpecification specification);
    Page<Marca> findAll(MarcaSpecification specification, Pageable pageable);
    Optional<Marca> findByNombre(String nombre);
    Marca findByNombreOrThrowException(String nombre);
    Marca create(Marca categoria);
    Marca update(Marca categoria);
    void deleteById(Long id);
}
