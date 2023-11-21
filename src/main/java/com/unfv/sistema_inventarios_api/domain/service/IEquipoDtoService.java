package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.EquipoConComponentesDto;
import com.unfv.sistema_inventarios_api.domain.dto.EquipoDto;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EquipoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEquipoDtoService {
    Page<EquipoDto> findAll(Pageable pageable);
    EquipoConComponentesDto findByNombre(String nombre);
    EquipoConComponentesDto create(EquipoRequest equipoRequest);
    EquipoConComponentesDto update(String nombre, EquipoRequest equipoRequest);
    void deleteByNombre(String nombre);
}
