package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.ModeloDto;
import com.unfv.sistema_inventarios_api.presentation.controller.request.ModeloRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IModeloDtoService {
    Page<ModeloDto> findAll(Pageable pageable);
    ModeloDto findByNombre(String nombre);
    ModeloDto create(ModeloRequest modeloRequest);
    ModeloDto update(String nombre, ModeloRequest modeloRequest);
    void deleteByNombre(String nombre);
}