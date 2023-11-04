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
    public ModeloDto findById(Long id) {
        return modeloDtoMapper.toDto(modeloService.findByIdOrThrowException(id));
    }

    @Override
    public ModeloDto create(ModeloRequest modeloRequest) {
        validarNombreYCategoria(modeloRequest.getNombre(), modeloRequest.getCategoria().getNombre());
        Modelo modeloCreado = modeloService.create(modeloRequestMapper.toEntity(modeloRequest));
        return modeloDtoMapper.toDto(modeloCreado);
    }

    @Override
    public ModeloDto update(Long id, ModeloRequest modeloRequest) {
        Modelo modelo = modeloService.findByIdOrThrowException(id);
        if(!modelo.getNombre().equals(modeloRequest.getNombre())){
            validarNombreYCategoria(modeloRequest.getNombre(), modeloRequest.getCategoria().getNombre());
        }
        Modelo modeloActualizado = modeloRequestMapper.toEntity(modeloRequest);
        modeloActualizado.setId(modelo.getId());
        return modeloDtoMapper.toDto(modeloService.update(modeloActualizado));
    }

    @Override
    public void deleteByNombre(Long id) {
        Modelo modelo = modeloService.findByIdOrThrowException(id);
        modeloService.deleteById(modelo.getId());
    }

    public void validarNombreYCategoria(String nombre, String nombreSubcategoria){
        Optional<Modelo> modeloOptional = modeloService.findByNombreAndSubcategoria_Nombre(nombre, nombreSubcategoria);
        if (modeloOptional.isPresent() && (modeloOptional.get().getSubcategoria().getNombre().equals(nombreSubcategoria))) {
            throw new DuplicateKeyException("El modelo '" + nombre + "' ya esta registrado en la subcategoria '" + nombreSubcategoria + "'");
        }
    }
}
