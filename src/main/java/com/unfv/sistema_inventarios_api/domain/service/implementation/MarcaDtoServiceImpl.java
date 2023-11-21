package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.MarcaDto;
import com.unfv.sistema_inventarios_api.domain.mapper.MarcaDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IMarcaDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Marca;
import com.unfv.sistema_inventarios_api.persistance.service.IMarcaService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.mapper.MarcaRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.MarcaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarcaDtoServiceImpl implements IMarcaDtoService {

    private final IMarcaService marcaService;
    private final MarcaDtoMapper marcaDtoMapper;
    private final MarcaRequestMapper marcaRequestMapper;

    @Override
    public Page<MarcaDto> findAll(Pageable pageable) {
        return marcaService.findAll(pageable).map(marcaDtoMapper::toDto);
    }

    @Override
    public MarcaDto findByNombre(String nombre) {
        return marcaDtoMapper.toDto(marcaService.findByNombreOrThrowException(nombre));
    }

    @Override
    public MarcaDto create(MarcaRequest marcaRequest) {
        Optional<Marca> marcaOptional = marcaService.findByNombre(marcaRequest.getNombre());
        if(marcaOptional.isPresent()){
            throw new DuplicateKeyException("La marca '" + marcaRequest.getNombre() + "' ya existe");
        }
        Marca marca = marcaService.create(marcaRequestMapper.toEntity(marcaRequest));
        return marcaDtoMapper.toDto(marca);
    }

    @Override
    public MarcaDto update(String nombre, MarcaRequest marcaRequest) {
        Marca marca = marcaService.findByNombreOrThrowException(nombre);
        if(!marca.getNombre().equals(marcaRequest.getNombre())){
            Optional<Marca> marcaOptional = marcaService.findByNombre(marcaRequest.getNombre());
            if(marcaOptional.isPresent()){
                throw new DuplicateKeyException("La marca '" + marcaRequest.getNombre() + "' ya existe");
            }
        }
        Marca marcaActualizada = marcaService.update(marcaRequestMapper.update(marca, marcaRequest));
        return marcaDtoMapper.toDto(marcaActualizada);
    }

    @Override
    public void deleteByNombre(String nombre) {
        Marca marca = marcaService.findByNombreOrThrowException(nombre);
        marcaService.deleteById(marca.getId());
    }
}