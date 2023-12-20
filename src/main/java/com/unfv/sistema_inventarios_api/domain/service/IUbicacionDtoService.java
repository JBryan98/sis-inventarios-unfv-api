package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.UbicacionConEquiposDto;
import com.unfv.sistema_inventarios_api.domain.dto.UbicacionDto;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.UbicacionSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.UbicacionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUbicacionDtoService {
    Page<UbicacionDto> findAll(UbicacionSpecification specification, Pageable pageable);
    UbicacionConEquiposDto findByNombre(String nombre);
    UbicacionDto create(UbicacionRequest ubicacionRequest);
    UbicacionDto update(String nombre, UbicacionRequest ubicacionRequest);
    UbicacionConEquiposDto administrarUbicacionEquipos(UbicacionRequest ubicacionRequest);
    void deleteByNombre(String nombre);
}
