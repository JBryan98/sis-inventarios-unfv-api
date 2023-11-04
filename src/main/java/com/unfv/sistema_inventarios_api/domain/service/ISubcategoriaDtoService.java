package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.SubcategoriaDto;
import com.unfv.sistema_inventarios_api.presentation.controller.request.SubcategoriaRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISubcategoriaDtoService {
    Page<SubcategoriaDto> findAll(Pageable pageable);
    SubcategoriaDto findByNombre(String nombre);
    SubcategoriaDto create(SubcategoriaRequest subcategoriaDto);
    SubcategoriaDto update(String nombre, SubcategoriaRequest subcategoriaDto);
    void deleteByNombre(String nombre);
}
