package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.EquipoConComponentesDto;
import com.unfv.sistema_inventarios_api.domain.dto.EquipoDto;
import com.unfv.sistema_inventarios_api.domain.dto.HardwareDto;
import com.unfv.sistema_inventarios_api.domain.mapper.EquipoConComponentesMapper;
import com.unfv.sistema_inventarios_api.domain.mapper.EquipoDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IEquipoDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import com.unfv.sistema_inventarios_api.persistance.entity.Hardware;
import com.unfv.sistema_inventarios_api.persistance.service.IEquipoService;
import com.unfv.sistema_inventarios_api.persistance.service.IHardwareService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.mapper.EquipoRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EquipoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipoDtoServiceImpl implements IEquipoDtoService {
    private final IEquipoService equipoService;
    private final IHardwareService hardwareService;
    private final EquipoDtoMapper equiDtoMapper;
    private final EquipoRequestMapper equipoRequestMapper;
    private final EquipoConComponentesMapper equipoConComponentesMapper;

    @Override
    public Page<EquipoDto> findAll(Pageable pageable) {
        return equipoService.findAll(pageable).map(equiDtoMapper::toDto);
    }

    @Override
    public EquipoConComponentesDto findByNombre(String nombre) {
        return equipoConComponentesMapper.toDto(equipoService.findByNombreOrThrowException(nombre));
    }

    @Override
    public EquipoConComponentesDto create(EquipoRequest equipoRequest) {
        validateEquipo(equipoRequest.getNombre());
        Equipo equipoMapeado = equipoRequestMapper.toEntity(equipoRequest);
        Equipo nuevoEquipo = Equipo.builder()
                .nombre(equipoMapeado.getNombre())
                .ubicacion(equipoMapeado.getUbicacion())
                .software(equipoMapeado.getSoftware())
                .build();
        Equipo equipoCreado = equipoService.create(nuevoEquipo);
        updateEquipoHardwareBeforeCreate(equipoCreado, equipoMapeado);
        return equipoConComponentesMapper.toDto(equipoCreado);
    }

    @Override
    public EquipoConComponentesDto update(String nombre, EquipoRequest equipoRequest) {
        Equipo equipo = equipoService.findByNombreOrThrowException(nombre);
        if(!equipo.getNombre().equals(equipoRequest.getNombre())){
            validateEquipo(equipoRequest.getNombre());
        }
        Equipo equipoMapeado = equipoRequestMapper.update(equipo, equipoRequest);
        updateEquipoHardwareBeforeUpdate(equipoMapeado.getHardware());
        Equipo equipoActualizado = equipoService.update(equipoMapeado);
        return equipoConComponentesMapper.toDto(equipoActualizado);
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

    /*
    * Método para actualizar el hardware una vez creado el equipo, aquí se actualizará su estado a "Operativo" y
    * se le asignará como equipo, el equipo persistido en la BD.
    * Los hardwares asignados a un equipo deben actualizar su estado a "Operativo"
    * @param equipoCreado Este objeto contiene los datos del nuevo equipo, pero que aún no tiene su hardware asignado
    * @param equipoMapeado Este objeto contiene el arreglo de hardware enviado desde el request, pero
    * estos equipos aún no tienen un equipo asignado
    * */

    public void updateEquipoHardwareBeforeCreate(Equipo equipoCreado, Equipo equipoMapeado){
        for(Hardware hardware: equipoMapeado.getHardware()){
            hardware.setEstado("Operativo");
            hardware.setEquipo(equipoCreado);
        }
        hardwareService.saveAll(equipoMapeado.getHardware());
    }


    /*
    * Método para actualizar el estado del hardware a "Stock" u "Operativo"
    * Dependiendo de si el hardware fue añadido o removido del equipo
    * Si el hardware fue removido, este llegará con equipo = null
    * Y deberá pasar a "Stock"
    * @param hardwareArray Arreglo de hardware con los cambios
    * */

    public void updateEquipoHardwareBeforeUpdate(Set<Hardware> hardwareArray){
        for(Hardware hardware: hardwareArray){
            if(hardware.getEquipo() == null){
                hardware.setEstado("Stock");
            }else {
                hardware.setEstado("Operativo");
            }
        }
    }
}
