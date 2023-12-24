package com.unfv.sistema_inventarios_api.authentication.service.implementation;

import com.unfv.sistema_inventarios_api.authentication.controller.request.RolRequest;
import com.unfv.sistema_inventarios_api.authentication.controller.request.mapper.RolRequestMapper;
import com.unfv.sistema_inventarios_api.authentication.dto.RolDto;
import com.unfv.sistema_inventarios_api.authentication.mapper.RolDtoMapper;
import com.unfv.sistema_inventarios_api.authentication.service.IRolDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Rol;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.RolSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IRolService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolDtoServiceImpl implements IRolDtoService {
    private final IRolService rolService;
    private final RolDtoMapper rolDtoMapper;
    private final RolRequestMapper rolRequestMapper;


    @Override
    public Page<RolDto> findAll(RolSpecification specification, Pageable pageable) {
        return rolService.findAll(specification, pageable).map(rolDtoMapper::toDto);
    }

    @Override
    public RolDto findByNombre(String nombre) {
        return rolDtoMapper.toDto(rolService.findByNombreOrThrowException(nombre));
    }

    @Override
    public RolDto create(RolRequest rolRequest) {
        validarNombre(rolRequest.getNombre());
        Rol nuevoRol = rolRequestMapper.toEntity(rolRequest);
        return rolDtoMapper.toDto(rolService.create(nuevoRol));
    }

    @Override
    public RolDto update(String nombre, RolRequest rolRequest) {
        Rol rol = rolService.findByNombreOrThrowException(nombre);
        if(!rol.getNombre().equals(rolRequest.getNombre())){
            validarNombre(rolRequest.getNombre());
        }
        Rol rolActualizado = rolRequestMapper.toEntity(rolRequest);
        rolActualizado.setId(rol.getId());
        return rolDtoMapper.toDto(rolService.update(rolActualizado));
    }

    @Override
    public void delete(String nombre) {
        Rol rol = rolService.findByNombreOrThrowException(nombre);
        rolService.deleteById(rol.getId());
    }

    private void validarNombre(String nombre){
        Optional<Rol> rol = rolService.findByNombre(nombre);
        if(rol.isPresent()){
            throw new DuplicateKeyException("El rol '" + nombre + "' ya existe");
        }
    }
}
