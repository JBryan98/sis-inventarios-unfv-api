package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.MarcaDto;
import com.unfv.sistema_inventarios_api.presentation.controller.request.MarcaRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMarcaDtoService {
    Page<MarcaDto> findAll(Pageable pageable);
    MarcaDto findByNombre(String nombre);
    MarcaDto create(MarcaRequest marcaRequest);
    MarcaDto update(String nombre, MarcaRequest marcaRequest);
    void deleteByNombre(String nombre);
}
