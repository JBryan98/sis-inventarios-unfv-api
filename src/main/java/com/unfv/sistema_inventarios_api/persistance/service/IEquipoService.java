package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IEquipoService {
    Page<Equipo> findAll(Pageable pageable);
    Optional<Equipo> findByNombre(String nombre);
    Equipo findByNombreOrThrowException(String nombre);
    Equipo create(Equipo equipo);
    Equipo update(Equipo equipo);
    void deleteById(Long id);
}
