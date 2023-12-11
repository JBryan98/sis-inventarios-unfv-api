package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.EquiposTrabajoDto;
import com.unfv.sistema_inventarios_api.domain.mapper.EquiposTrabajoDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IEquiposTrabajoDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.EquiposTrabajo;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.EquiposTrabajoSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IEquiposTrabajoService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EquiposTrabajoRequest;
import com.unfv.sistema_inventarios_api.presentation.controller.request.mapper.EquiposTrabajoRequestMapper;
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
public class EquiposTrabajoDtoService implements IEquiposTrabajoDtoService {
    private final IEquiposTrabajoService equiposTrabajoService;
    private final EquiposTrabajoRequestMapper equiposTrabajoRequestMapper;
    private final EquiposTrabajoDtoMapper equiposTrabajoDtoMapper;

    @Override
    public Page<EquiposTrabajoDto> findAll(EquiposTrabajoSpecification specification, Pageable pageable) {
        return equiposTrabajoService.findAll(specification, pageable).map(equiposTrabajoDtoMapper::toDto);
    }

    @Override
    public EquiposTrabajoDto findBySerie(String serie) {
        return equiposTrabajoDtoMapper.toDto(equiposTrabajoService.findBySerieOrThrowException(serie));
    }

    @Override
    public EquiposTrabajoDto create(EquiposTrabajoRequest equiposTrabajoRequest) {
        validarSerie(equiposTrabajoRequest.getSerie());
        EquiposTrabajo equiposTrabajoCreado = equiposTrabajoService.create(equiposTrabajoRequestMapper.toEntity(equiposTrabajoRequest));
        return equiposTrabajoDtoMapper.toDto(equiposTrabajoCreado);
    }

    @Override
    public EquiposTrabajoDto update(String serie, EquiposTrabajoRequest equiposTrabajoRequest) {
        EquiposTrabajo equiposTrabajo = equiposTrabajoService.findBySerieOrThrowException(serie);
        if(!equiposTrabajo.getSerie().equals(equiposTrabajoRequest.getSerie())){
            validarSerie(equiposTrabajoRequest.getSerie());
        }
        EquiposTrabajo equiposTrabajoActualizado = equiposTrabajoRequestMapper.toEntity(equiposTrabajoRequest);
        equiposTrabajoActualizado.setId(equiposTrabajo.getId());
        log.info(equiposTrabajoActualizado.toString());
        return equiposTrabajoDtoMapper.toDto(equiposTrabajoService.update(equiposTrabajoActualizado));
    }

    @Override
    public void deleteByNombre(String serie) {
        EquiposTrabajo equiposTrabajo = equiposTrabajoService.findBySerieOrThrowException(serie);
        equiposTrabajoService.deleteById(equiposTrabajo.getId());
    }

    /*
    * Método para validar que la serie del equipo de trabajo no sea duplicada
    * @param número de serie del equipo de trabajo request
    * */

    private void validarSerie(String serie){
        Optional<EquiposTrabajo> equipo = equiposTrabajoService.findBySerie(serie);
        if(equipo.isPresent()){
            throw new DuplicateKeyException("El equipo de trabajo con serie '" + serie + "' ya existe");
        }
    }
}
