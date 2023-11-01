package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.EscuelaDto;
import com.unfv.sistema_inventarios_api.domain.mapper.EscuelaDtoMapper;
import com.unfv.sistema_inventarios_api.domain.mapper.FacultadDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IEscuelaDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Escuela;
import com.unfv.sistema_inventarios_api.persistance.service.IEscuelaService;
import com.unfv.sistema_inventarios_api.presentation.controller.mapper.EscuelaRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EscuelaRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EscuelaDtoServiceImpl implements IEscuelaDtoService {
    private final IEscuelaService escuelaService;
    private final EscuelaDtoMapper escuelaDtoMapper;
    private final EscuelaRequestMapper escuelaRequestMapper;
    private final FacultadDtoMapper facultadDtoMapper;

    @Override
    public Page<EscuelaDto> findAll(Pageable pageable) {
        return escuelaService.findAll(pageable).map(escuelaDtoMapper::toDto);
    }

    @Override
    public EscuelaDto findByAbreviatura(String abreviatura) {
        return escuelaDtoMapper.toDto(escuelaService.findByAbreviaturaOrThrowException(abreviatura));
    }

    @Override
    public EscuelaDto create(EscuelaRequest escuelaRequest) {
        validarNombreUnico(escuelaRequest.getNombre());
        validarAbreviaturaUnica(escuelaRequest.getAbreviatura());
        Escuela escuelaCreada = escuelaService.create(escuelaRequestMapper.toEntity(escuelaRequest));
        return escuelaDtoMapper.toDto(escuelaCreada);
    }

    @Override
    public EscuelaDto update(String abreviatura, EscuelaRequest escuelaRequest) {
        Escuela escuela = escuelaService.findByAbreviaturaOrThrowException(abreviatura);
        if(!escuela.getAbreviatura().equals(escuelaRequest.getAbreviatura())){
            validarAbreviaturaUnica(escuelaRequest.getAbreviatura());
        }

        Escuela escuelaActualizada = escuelaRequestMapper.toEntity(escuelaRequest);
        escuelaActualizada.setId(escuela.getId());
        return escuelaDtoMapper.toDto(escuelaService.update(escuelaActualizada));
    }

    @Override
    public void deleteByAbreviatura(String abrevitura) {
        Escuela escuela = escuelaService.findByAbreviaturaOrThrowException(abrevitura);
        escuelaService.deleteById(escuela.getId());
    }

    private void validarNombreUnico(String nombre){
        Optional<Escuela> escuelaOptionalByNombre = escuelaService.findByNombre(nombre);
        if (escuelaOptionalByNombre.isPresent() && (escuelaOptionalByNombre.get().getNombre().equals(nombre))) {
            throw new DuplicateKeyException("La escuela con nombre '" + nombre + "' ya existe");
        }
    }

    private void validarAbreviaturaUnica(String abreviatura){
        Optional<Escuela> escuelaOptionalByAbreviatura = escuelaService.findByAbreviatura(abreviatura);
        if (escuelaOptionalByAbreviatura.isPresent() && (escuelaOptionalByAbreviatura.get().getAbreviatura().equals(abreviatura))) {
            throw new DuplicateKeyException("La escuela con abreviatura '" + abreviatura + "' ya existe");
        }
    }
}
