package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.EquiposTrabajo;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.EquiposTrabajoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IEquiposTrabajoService {
    Page<EquiposTrabajo> findAll(EquiposTrabajoSpecification specification, Pageable pageable);
    Optional<EquiposTrabajo> findBySerie(String serie);
    EquiposTrabajo findBySerieOrThrowException(String serie);
    List<EquiposTrabajo> saveAll(Set<EquiposTrabajo> equiposTrabajos);
    EquiposTrabajo create(EquiposTrabajo equiposTrabajo);
    EquiposTrabajo update(EquiposTrabajo equiposTrabajo);
    void deleteById(Long id);
}
