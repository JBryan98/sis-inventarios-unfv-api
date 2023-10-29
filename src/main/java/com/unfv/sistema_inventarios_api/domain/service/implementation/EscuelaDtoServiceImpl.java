package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.EscuelaDto;
import com.unfv.sistema_inventarios_api.domain.mapper.EscuelaDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IEscuelaDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Escuela;
import com.unfv.sistema_inventarios_api.persistance.service.IEscuelaService;
import com.unfv.sistema_inventarios_api.presentation.controller.mapper.EscuelaRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EscuelaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EscuelaDtoServiceImpl implements IEscuelaDtoService {
    private final IEscuelaService escuelaService;
    private final EscuelaDtoMapper escuelaDtoMapper;
    private final EscuelaRequestMapper escuelaRequestMapper;

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
        validateEscuela(escuelaRequest.getAbreviatura(), escuelaRequest.getNombre());
        Escuela escuelaCreada = escuelaService.create(escuelaRequestMapper.toEntity(escuelaRequest));
        return escuelaDtoMapper.toDto(escuelaCreada);
    }

    @Override
    public EscuelaDto update(String abreviatura, EscuelaRequest escuelaRequest) {
        Escuela escuela = escuelaService.findByAbreviaturaOrThrowException(abreviatura);
        if(!escuela.getAbreviatura().equals(escuelaRequest.getAbreviatura()) || !escuela.getNombre().equals(escuelaRequest.getNombre())){
            validateEscuela(escuelaRequest.getAbreviatura(), escuelaRequest.getNombre());
        }
        Escuela escuelaActualizada = escuelaService.update(escuelaRequestMapper.update(escuela, escuelaRequest));
        return escuelaDtoMapper.toDto(escuelaActualizada);
    }

    @Override
    public void deleteByAbreviatura(String abrevitura) {
        Escuela escuela = escuelaService.findByAbreviaturaOrThrowException(abrevitura);
        escuelaService.deleteById(escuela.getId());
    }

    private void validateEscuela(String abreviatura, String nombre){
        Optional<Escuela> escuelaOptional = escuelaService.findByAbreviaturaOrNombre(abreviatura, nombre);
        if (escuelaOptional.isPresent()) {
            if (escuelaOptional.get().getNombre().equals(nombre)) {
                throw new DuplicateKeyException("La escuela con nombre '" + nombre + "' ya existe");
            }
            if (escuelaOptional.get().getAbreviatura().equals(abreviatura)) {
                throw new DuplicateKeyException("La escuela con abreviatura '" + abreviatura + "' ya existe");
            }
        }
    }
}
