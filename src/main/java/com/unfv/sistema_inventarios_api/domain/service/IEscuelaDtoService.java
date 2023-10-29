package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.EscuelaDto;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EscuelaRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEscuelaDtoService {
    Page<EscuelaDto> findAll(Pageable pageable);
    EscuelaDto findByAbreviatura(String abreviatura);
    EscuelaDto create(EscuelaRequest escuelaRequest);
    EscuelaDto update(String abreviatura, EscuelaRequest escuelaRequest);
    void deleteByAbreviatura(String abrevitura);
}
