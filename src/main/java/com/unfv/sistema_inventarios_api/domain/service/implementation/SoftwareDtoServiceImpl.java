package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.SoftwareDto;
import com.unfv.sistema_inventarios_api.domain.mapper.SoftwareDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.ISoftwareDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Software;
import com.unfv.sistema_inventarios_api.persistance.service.ISoftwareService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.mapper.SoftwareRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.SoftwareRequest;
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
public class SoftwareDtoServiceImpl implements ISoftwareDtoService {
    private final ISoftwareService softwareService;
    private final SoftwareDtoMapper softwareDtoMapper;
    private final SoftwareRequestMapper softwareRequestMapper;

    @Override
    public Page<SoftwareDto> findAll(Pageable pageable) {
        return softwareService.findAll(pageable).map(softwareDtoMapper::toDto);
    }

    @Override
    public SoftwareDto findByNombre(String nombre) {
        return softwareDtoMapper.toDto(softwareService.findByNombreOrThrowException(nombre));
    }

    @Override
    public SoftwareDto create(SoftwareRequest softwareRequest) {
        validarSoftwareNombre(softwareRequest.getNombre());
        Software softwareCreado = softwareService.create(softwareRequestMapper.toEntity(softwareRequest));
        return softwareDtoMapper.toDto(softwareCreado);
    }

    @Override
    public SoftwareDto update(String nombre, SoftwareRequest softwareRequest) {
        Software software = softwareService.findByNombreOrThrowException(nombre);
        if(!software.getNombre().equals(softwareRequest.getNombre())){
            validarSoftwareNombre(softwareRequest.getNombre());
        }
        Software softwareActualizado = softwareRequestMapper.toEntity(softwareRequest);
        softwareActualizado.setId(software.getId());
        return softwareDtoMapper.toDto(softwareService.update(softwareActualizado));
    }

    @Override
    public void deleteByNombre(String nombre) {
        Software software = softwareService.findByNombreOrThrowException(nombre);
        softwareService.deleteById(software.getId());
    }

    private void validarSoftwareNombre(String nombre){
        Optional<Software> softwareOptional = softwareService.findByNombre(nombre);
        if(softwareOptional.isPresent()){
            throw new DuplicateKeyException("El software '" + nombre + "' ya existe");
        }
    }
}
