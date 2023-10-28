package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.CategoriaDto;
import com.unfv.sistema_inventarios_api.presentation.controller.request.CategoriaRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoriaDtoService {
    Page<CategoriaDto> findAll(Pageable pageable);
    CategoriaDto findByNombre(String nombre);
    CategoriaDto create(CategoriaRequest request);
    CategoriaDto update(String nombre, CategoriaRequest request);
    void delete(String nombre);

}
