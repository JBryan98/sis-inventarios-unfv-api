package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.EquipoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IEquipoService {
    List<Equipo> findAllNoPage(EquipoSpecification specification);
    Page<Equipo> findAll(EquipoSpecification specification, Pageable pageable);
    Optional<Equipo> findByNombre(String nombre);
    Equipo findByNombreOrThrowException(String nombre);
    List<Equipo> saveAll(Set<Equipo> equipos);
    Equipo create(Equipo equipo);
    Equipo update(Equipo equipo);
    void deleteById(Long id);
}
