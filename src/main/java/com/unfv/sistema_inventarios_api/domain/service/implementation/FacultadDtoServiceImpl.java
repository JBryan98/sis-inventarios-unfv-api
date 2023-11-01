package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.FacultadDto;
import com.unfv.sistema_inventarios_api.domain.mapper.FacultadDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IFacultadDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Facultad;
import com.unfv.sistema_inventarios_api.persistance.service.IFacultadService;
import com.unfv.sistema_inventarios_api.presentation.controller.mapper.FacultadRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.FacultadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacultadDtoServiceImpl implements IFacultadDtoService {
    private final IFacultadService facultadService;
    private final FacultadDtoMapper facultadDtoMapper;
    private final FacultadRequestMapper facultadRequestMapper;

    @Override
    public Page<FacultadDto> findAll(Pageable pageable) {
        return facultadService.findAll(pageable).map(facultadDtoMapper::toDto);
    }

    @Override
    public FacultadDto findByAbreviatura(String abreviatura) {
        return facultadDtoMapper.toDto(facultadService.findByAbreviaturaOrThrowException(abreviatura));
    }

    @Override
    public FacultadDto create(FacultadRequest facultadRequest) {
        validarNombreUnico(facultadRequest.getNombre());
        validarAbreviaturaUnica(facultadRequest.getAbreviatura());
        Facultad facultadCreada = facultadService.create(facultadRequestMapper.toEntity(facultadRequest));
        return facultadDtoMapper.toDto(facultadCreada);
    }

    @Override
    public FacultadDto update(String abreviatura, FacultadRequest facultadRequest) {
        Facultad facultad = facultadService.findByAbreviaturaOrThrowException(abreviatura);
        if(!facultad.getAbreviatura().equals(facultadRequest.getAbreviatura())){
            validarAbreviaturaUnica(facultadRequest.getAbreviatura());
        }
        if(!facultad.getNombre().equals(facultadRequest.getNombre())){
            validarNombreUnico(facultadRequest.getNombre());
        }
        Facultad facultadActualizada = facultadService.update(facultadRequestMapper.update(facultad, facultadRequest));
        return facultadDtoMapper.toDto(facultadActualizada);
    }

    @Override
    public void deleteByAbreviatura(String abreviatura) {
        Facultad facultad = facultadService.findByAbreviaturaOrThrowException(abreviatura);
        facultadService.deleteById(facultad.getId());
    }

    private void validarAbreviaturaUnica(String abreviatura) {
        Optional<Facultad> facultadOptional = facultadService.findByAbreviatura(abreviatura);
        if (facultadOptional.isPresent()) {
            throw new DuplicateKeyException("La facultad con abreviatura '" + abreviatura + "' ya existe");
        }
    }

    private void validarNombreUnico(String nombre) {
        Optional<Facultad> facultadOptional = facultadService.findByNombre(nombre);
        if (facultadOptional.isPresent()) {
            throw new DuplicateKeyException("La facultad con abreviatura '" + nombre + "' ya existe");
        }
    }
}
