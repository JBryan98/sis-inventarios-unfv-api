package com.unfv.sistema_inventarios_api.authentication.service;

import com.unfv.sistema_inventarios_api.authentication.controller.request.RolRequest;
import com.unfv.sistema_inventarios_api.authentication.dto.RolDto;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.RolSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRolDtoService {
    Page<RolDto> findAll(RolSpecification specification, Pageable pageable);
    RolDto findByNombre(String nombre);
    RolDto create(RolRequest rolRequest);
    RolDto update(String nombre, RolRequest rolRequest);
    void delete(String nombre);
}
