package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.MarcaDto;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.MarcaSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.MarcaRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.DecoderException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface IMarcaDtoService {
    Page<MarcaDto> findAll(MarcaSpecification specification, Pageable pageable);
    MarcaDto findByNombre(String nombre);
    MarcaDto create(MarcaRequest marcaRequest);
    MarcaDto update(String nombre, MarcaRequest marcaRequest);
    void deleteByNombre(String nombre);
    void downloadExcel(HttpServletResponse response, MarcaSpecification specification) throws IOException, DecoderException;

}
