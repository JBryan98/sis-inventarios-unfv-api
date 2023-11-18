package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.SoftwareDto;
import com.unfv.sistema_inventarios_api.presentation.controller.request.SoftwareRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISoftwareDtoService {
    Page<SoftwareDto> findAll(Pageable pageable);
    SoftwareDto findByNombre(String nombre);
    SoftwareDto create(SoftwareRequest softwareRequest);
    SoftwareDto update(String nombre, SoftwareRequest softwareRequest);
    void deleteByNombre(String nombre);
}
