package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Ubicacion;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.UbicacionSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUbicacionService {
    Page<Ubicacion> findAll(UbicacionSpecification specification, Pageable pageable);
    Optional<Ubicacion> findByNombre(String nombre);
    Ubicacion findByNombreOrThrowException(String nombre);
    Ubicacion create(Ubicacion ubicacion);
    Ubicacion update(Ubicacion ubicacion);
    void deleteById(Long id);
}
