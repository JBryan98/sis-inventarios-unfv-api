package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.EquiposTrabajoDto;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.EquiposTrabajoSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EquiposTrabajoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEquiposTrabajoDtoService {
    Page<EquiposTrabajoDto> findAll(EquiposTrabajoSpecification specification, Pageable pageable);
    EquiposTrabajoDto findBySerie(String serie);
    EquiposTrabajoDto create(EquiposTrabajoRequest equiposTrabajoRequest);
    EquiposTrabajoDto update(String serie, EquiposTrabajoRequest equiposTrabajoRequest);
    void deleteByNombre(String serie);
}
