package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.ModeloDto;
import com.unfv.sistema_inventarios_api.domain.mapper.ModeloDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IModeloDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Modelo;
import com.unfv.sistema_inventarios_api.persistance.service.IModeloService;
import com.unfv.sistema_inventarios_api.presentation.controller.mapper.ModeloRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.ModeloRequest;
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
public class ModeloDtoServiceImpl implements IModeloDtoService {
    private final IModeloService modeloService;
    private final ModeloDtoMapper modeloDtoMapper;
    private final ModeloRequestMapper modeloRequestMapper;

    @Override
    public Page<ModeloDto> findAll(Pageable pageable) {
        return modeloService.findAll(pageable).map(modeloDtoMapper::toDto);
    }

    @Override
    public ModeloDto findByNombre(String nombre) {
        return modeloDtoMapper.toDto(modeloService.findByNombreOrThrowException(nombre));
    }

    @Override
    public ModeloDto create(ModeloRequest modeloRequest) {
        Optional<Modelo> modeloOptional = modeloService.findByNombre(modeloRequest.getNombre());
        if(modeloOptional.isPresent()){
            throw new DuplicateKeyException("El modelo '" + modeloRequest.getNombre() + "' ya existe.");
        }
        Modelo modeloCreado = modeloService.create(modeloRequestMapper.toEntity(modeloRequest));
        return modeloDtoMapper.toDto(modeloCreado);
    }

    @Override
    public ModeloDto update(String nombre, ModeloRequest modeloRequest) {
        Modelo modelo = modeloService.findByNombreOrThrowException(nombre);
        if(!modelo.getNombre().equals(modeloRequest.getNombre())){
            Optional<Modelo> modeloOptional = modeloService.findByNombre(modeloRequest.getNombre());
            if(modeloOptional.isPresent()){
                throw new DuplicateKeyException("El modelo '" + modeloRequest.getNombre() + "' ya existe");
            }
        }
        Modelo modeloActualizado = modeloRequestMapper.toEntity(modeloRequest);
        modeloActualizado.setId(modelo.getId());
        return modeloDtoMapper.toDto(modeloService.update(modeloActualizado));
    }

    @Override
    public void deleteByNombre(String nombre) {
        Modelo modelo = modeloService.findByNombreOrThrowException(nombre);
        modeloService.deleteById(modelo.getId());
    }
}
