package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.EquipoDto;
import com.unfv.sistema_inventarios_api.domain.dto.EquiposTrabajoDto;
import com.unfv.sistema_inventarios_api.domain.dto.UbicacionConEquiposDto;
import com.unfv.sistema_inventarios_api.domain.dto.UbicacionDto;
import com.unfv.sistema_inventarios_api.domain.mapper.EquipoDtoMapper;
import com.unfv.sistema_inventarios_api.domain.mapper.EquiposTrabajoDtoMapper;
import com.unfv.sistema_inventarios_api.domain.mapper.UbicacionConEquiposDtoMapper;
import com.unfv.sistema_inventarios_api.domain.mapper.UbicacionDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IUbicacionDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import com.unfv.sistema_inventarios_api.persistance.entity.EquiposTrabajo;
import com.unfv.sistema_inventarios_api.persistance.entity.Ubicacion;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.UbicacionSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IEquipoService;
import com.unfv.sistema_inventarios_api.persistance.service.IEquiposTrabajoService;
import com.unfv.sistema_inventarios_api.persistance.service.IUbicacionService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.UbicacionRequest;
import com.unfv.sistema_inventarios_api.presentation.controller.request.mapper.UbicacionRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UbicacionDtoServiceImpl implements IUbicacionDtoService {
    private final IUbicacionService ubicacionService;
    private final IEquipoService equipoService;
    private final IEquiposTrabajoService equiposTrabajoService;
    private final EquipoDtoMapper equipoDtoMapper;
    private final EquiposTrabajoDtoMapper equiposTrabajoDtoMapper;
    private final UbicacionDtoMapper ubicacionDtoMapper;
    private final UbicacionConEquiposDtoMapper ubicacionConEquiposDtoMapper;
    private final UbicacionRequestMapper ubicacionRequestMapper;

    @Override
    public Page<UbicacionDto> findAll(UbicacionSpecification specification, Pageable pageable) {
        return ubicacionService.findAll(specification, pageable).map(ubicacionDtoMapper::toDto);
    }

    @Override
    public UbicacionConEquiposDto findByNombre(String nombre) {
        return ubicacionConEquiposDtoMapper.toDto(ubicacionService.findByNombreOrThrowException(nombre));
    }

    /**
     * Servicio para crear una ubicación y sus equipos
     * @param ubicacionRequest Request con la nueva ubicación con sus equipos y equipos de trabajo correspondientes
     * Primero se persiste la ubicación para luego asignar esta ubicación a los equipos y equipos de trabjao
     * */
    @Override
    public UbicacionDto create(UbicacionRequest ubicacionRequest) {
        validarUbicacionUnica(ubicacionRequest.getNombre());
        Ubicacion ubicacionMapeada = ubicacionRequestMapper.toEntity(ubicacionRequest);
        Ubicacion ubicacionCreada = ubicacionService.create(ubicacionMapeada);
        setUbicacionEquipoAfterPersist(ubicacionCreada, ubicacionRequest.getEquipos());
        setUbicacionEquiposTrabajoAfterPersist(ubicacionCreada, ubicacionRequest.getEquiposTrabajo());
        return ubicacionDtoMapper.toDto(ubicacionCreada);
    }

    /**
     * Servicio para actualizar solo la ubicación y no los equipos ni equipos de trabajo en cascada
     * */
    @Override
    public UbicacionDto update(String nombre, UbicacionRequest ubicacionRequest) {
        Ubicacion ubicacion = ubicacionService.findByNombreOrThrowException(nombre);
        if(!ubicacion.getNombre().equals(ubicacionRequest.getNombre())){
            validarUbicacionUnica(ubicacionRequest.getNombre());
        }
        Ubicacion ubicacionMapeada = ubicacionRequestMapper.toEntity(ubicacionRequest);
        ubicacionMapeada.setId(ubicacion.getId());
        Ubicacion ubicacionActualizada = ubicacionService.update(ubicacionMapeada);
        return ubicacionDtoMapper.toDto(ubicacionActualizada);
    }

    /**
     * Servicio para gestionar los equipos dentro de una ubicación.
     * @param ubicacionRequest Request que contiene el arreglo de equipos  y equipos de trabajo que han sido añadidos
     * o removidos de la ubicación
     * @return La ubicación con los equipos y equipos de trabajo actualizados
     * */
    @Override
    public UbicacionConEquiposDto administrarUbicacionEquipos(UbicacionRequest ubicacionRequest) {
        actualizarEquiposAdministrados(ubicacionRequest.getEquipos());
        actualizarEquiposTrabajoAdministrados(ubicacionRequest.getEquiposTrabajo());
        return this.findByNombre(ubicacionRequest.getNombre());
    }

    @Override
    public void deleteByNombre(String nombre) {
        Ubicacion ubicacion = ubicacionService.findByNombreOrThrowException(nombre);
        removerEquipoUbicacion(ubicacion.getEquipos());
        removerEquiposTrabajoUbicacion(ubicacion.getEquiposTrabajo());
        ubicacionService.deleteById(ubicacion.getId());
    }

    /**
     * Método para actualizar los equipos con la nueva ubicación creada.
     * @param ubicacionCreada Es la ubicación que fue persistida.
     * @param equiposDto Arreglo de equipos que aún no cuentan con la ubicación.
     * El equipo pasará de Stock a Operativo una vez asignada su ubicación
     * */
    private void setUbicacionEquipoAfterPersist(Ubicacion ubicacionCreada, Set<EquipoDto> equiposDto){
        Set<Equipo> equiposConUbicacion = equiposDto.stream().map(equipoDto -> {
            Equipo equipo = equipoService.findByNombreOrThrowException(equipoDto.getNombre());
            equipo.setEstado("Operativo");
            equipo.setUbicacion(ubicacionCreada);
            return equipo;
        }).collect(Collectors.toSet());
        equipoService.saveAll(equiposConUbicacion);
    }

    /**
     * Método para actualizar los equipos de trabajo con la nueva ubicación creada.
     * @param ubicacionCreada Es la ubicación que fue persistida.
     * @param equiposTrabajoDtos Arreglo de equipos de trabajo que aún no cuentan con la ubicación.
     * El equipo de trabajo pasará de Stock a Operativo una vez asignada su ubicación
     * */
    private void setUbicacionEquiposTrabajoAfterPersist(Ubicacion ubicacionCreada, Set<EquiposTrabajoDto> equiposTrabajoDtos){
        Set<EquiposTrabajo> equiposTrabajoConUbicacion = equiposTrabajoDtos.stream().map(equiposTrabajoDto -> {
            EquiposTrabajo equiposTrabajo = equiposTrabajoService.findBySerieOrThrowException(equiposTrabajoDto.getSerie());
            equiposTrabajo.setEstado("Operativo");
            equiposTrabajo.setUbicacion(ubicacionCreada);
            return equiposTrabajo;
        }).collect(Collectors.toSet());
        equiposTrabajoService.saveAll(equiposTrabajoConUbicacion);
    }

    /**
     * Método para validar que la ubicación a persistir no exista.
     * @param nombre Nombre de la ubicación que se desea persistir
     * */
    private void validarUbicacionUnica(String nombre){
        Optional<Ubicacion> ubicacion = ubicacionService.findByNombre(nombre);
        if(ubicacion.isPresent()){
            throw new DuplicateKeyException("La ubicación '" + nombre + "' ya existe");
        }
    }

    /**
     * Método para actualizar los equipos administrados que pueden haber sido removidos como añadidos a la ubicación.
     * Si fue añadido, el equipo debe pasar de Stock a operativo.
     * Si fue romovido, vendrá con ubicacion null, y deberá pasar a Stock.
     * Por último debe persistir todos los equipos con los cambios.
     * */
    private void actualizarEquiposAdministrados(Set<EquipoDto> equiposDto){
        Set<Equipo> equipos = equiposDto.stream().map(equipoDto -> {
            Equipo equipo = equipoService.findByNombreOrThrowException(equipoDto.getNombre());
            if(equipoDto.getUbicacion() == null){
                equipoDto.setEstado("Stock");
            }else {
                equipoDto.setEstado("Operativo");
            }
            return equipoDtoMapper.update(equipo, equipoDto);
        }).collect(Collectors.toSet());
        equipoService.saveAll(equipos);
    }

    /**
     * Método para actualizar los equipos de trabajo administrados que pueden haber sido removidos como añadidos a la ubicación.
     * Si fue añadido, el equipo de trabajo debe pasar de Stock a operativo.
     * Si fue romovido, vendrá con ubicacion null, y deberá pasar a Stock.
     * Por último debe persistir todos los equipos de trabajo con los cambios.
     * */
    private void actualizarEquiposTrabajoAdministrados(Set<EquiposTrabajoDto> equiposTrabajoDto){
        Set<EquiposTrabajo> equiposTrabajos = equiposTrabajoDto.stream().map(equipoTrabajoDto -> {
            if(equipoTrabajoDto.getUbicacion() == null){
                equipoTrabajoDto.setEstado("Stock");
            }else {
                equipoTrabajoDto.setEstado("Operativo");
            }
            return equiposTrabajoDtoMapper.toEntity(equipoTrabajoDto);
        }).collect(Collectors.toSet());
        equiposTrabajoService.saveAll(equiposTrabajos);
    }

    /**
     * Método para remover los equipos asociados a una ubicación luego de su eliminación.
     * Los equipos no se eliminan en cascada con la ubicación, sino que pasan a Stock.
     * Por último se persisten todos los equipos con sus respectivos cambios.
     * */
    private void removerEquipoUbicacion(Set<Equipo> equipos){
        for(Equipo equipo: equipos){
            equipo.setEstado("Stock");
            equipo.setUbicacion(null);
        }
        equipoService.saveAll(equipos);
    }

    /**
     * Método para remover los equipos de trabajo asociados a una ubicación luego de su eliminación.
     * Los equipos de trabajo no se eliminan en cascada con la ubicación, sino que pasan a Stock.
     * Por último se persisten todos los equipos de trabajo con sus respectivos cambios.
     * */
    private void removerEquiposTrabajoUbicacion(Set<EquiposTrabajo> equiposTrabajo){
        for(EquiposTrabajo equipoTrabajo: equiposTrabajo){
            equipoTrabajo.setEstado("Stock");
            equipoTrabajo.setUbicacion(null);
        }
        equiposTrabajoService.saveAll(equiposTrabajo);
    }
}
