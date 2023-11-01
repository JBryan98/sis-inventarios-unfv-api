package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.EquipoDto;
import com.unfv.sistema_inventarios_api.domain.mapper.EquipoDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IEquipoDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import com.unfv.sistema_inventarios_api.persistance.service.IEquipoService;
import com.unfv.sistema_inventarios_api.presentation.controller.mapper.EquipoRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EquipoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipoDtoServiceImpl implements IEquipoDtoService {
    private final IEquipoService equipoService;
    private final EquipoDtoMapper equiDtoMapper;
    private final EquipoRequestMapper equipoRequestMapper;

    @Override
    public Page<EquipoDto> findAll(Pageable pageable) {
        return equipoService.findAll(pageable).map(equiDtoMapper::toDto);
    }

    @Override
    public EquipoDto findByNombre(String nombre) {
        return equiDtoMapper.toDto(equipoService.findByNombreOrThrowException(nombre));
    }

    @Override
    public EquipoDto create(EquipoRequest equipoRequest) {
        validateEquipo(equipoRequest.getNombre());
        Equipo equipoCreado = equipoService.create(equipoRequestMapper.toEntity(equipoRequest));
        return equiDtoMapper.toDto(equipoCreado);
    }

    @Override
    public EquipoDto update(String nombre, EquipoRequest equipoRequest) {
        Equipo equipo = equipoService.findByNombreOrThrowException(nombre);
        if(!equipo.getNombre().equals(equipoRequest.getNombre())){
            validateEquipo(equipoRequest.getNombre());
        }
        Equipo equipoActualizado = equipoService.update(equipoRequestMapper.update(equipo, equipoRequest));
        return equiDtoMapper.toDto(equipoActualizado);
    }

    @Override
    public void deleteByNombre(String nombre) {
        Equipo equipo = equipoService.findByNombreOrThrowException(nombre);
        equipoService.deleteById(equipo.getId());
    }

    private void validateEquipo(String nombre){
        Optional<Equipo> equipoOptional = equipoService.findByNombre(nombre);
        if(equipoOptional.isPresent()){
            throw new DuplicateKeyException("El equipo '" + nombre + "' ya existe");
        }
    }
}
