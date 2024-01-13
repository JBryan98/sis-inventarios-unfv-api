package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.EquipoConComponentesDto;
import com.unfv.sistema_inventarios_api.domain.dto.EquipoDto;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.EquipoSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EquipoRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.DecoderException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface IEquipoDtoService {
    Page<EquipoDto> findAll(EquipoSpecification specification, Pageable pageable);
    EquipoConComponentesDto findByNombre(String nombre);
    EquipoConComponentesDto create(EquipoRequest equipoRequest);
    EquipoConComponentesDto update(String nombre, EquipoRequest equipoRequest);
    EquipoConComponentesDto administrarEquipoHardwareYSoftware(EquipoRequest equipoRequest);
    void deleteByNombre(String nombre);
    void downloadExcel(HttpServletResponse response, EquipoSpecification specification) throws IOException, DecoderException;

}
