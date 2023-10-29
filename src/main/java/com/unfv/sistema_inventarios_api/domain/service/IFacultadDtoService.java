package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.FacultadDto;
import com.unfv.sistema_inventarios_api.presentation.controller.request.FacultadRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFacultadDtoService {
    Page<FacultadDto> findAll(Pageable pageable);
    FacultadDto findByAbreviatura(String abreviatura);
    FacultadDto create(FacultadRequest facultadRequest);
    FacultadDto update(String abreviatura, FacultadRequest facultadRequest);
    void deleteByAbreviatura(String abreviatura);
}
