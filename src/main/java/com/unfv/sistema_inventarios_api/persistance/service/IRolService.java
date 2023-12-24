package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Rol;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.RolSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IRolService {
    Page<Rol> findAll(RolSpecification specification, Pageable pageable);
    Optional<Rol> findByNombre(String nombre);
    Rol findByNombreOrThrowException(String nombre);
    Rol create(Rol rol);
    Rol update(Rol rol);
    void deleteById(Long id);
}
