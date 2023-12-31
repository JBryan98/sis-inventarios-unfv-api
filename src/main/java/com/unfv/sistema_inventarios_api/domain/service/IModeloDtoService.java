package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.ModeloDto;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.ModeloSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.ModeloRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.DecoderException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface IModeloDtoService {
    Page<ModeloDto> findAll(ModeloSpecification specification, Pageable pageable);
    ModeloDto findById(Long id);
    ModeloDto create(ModeloRequest modeloRequest);
    ModeloDto update(Long id, ModeloRequest modeloRequest);
    void deleteByNombre(Long id);
    void downloadExcel(HttpServletResponse response, ModeloSpecification specification) throws IOException, DecoderException;
}