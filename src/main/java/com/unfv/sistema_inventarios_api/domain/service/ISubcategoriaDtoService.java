package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.SubcategoriaDto;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.SubcategoriaSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.SubcategoriaRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.DecoderException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ISubcategoriaDtoService {
    Page<SubcategoriaDto> findAll(SubcategoriaSpecification specification, Pageable pageable);
    SubcategoriaDto findByNombre(String nombre);
    SubcategoriaDto create(SubcategoriaRequest subcategoriaDto);
    SubcategoriaDto update(String nombre, SubcategoriaRequest subcategoriaDto);
    void deleteByNombre(String nombre);
    void downloadExcel(HttpServletResponse response, SubcategoriaSpecification specification) throws IOException, DecoderException;
}
