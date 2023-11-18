package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.SubcategoriaDto;
import com.unfv.sistema_inventarios_api.domain.mapper.SubcategoriaDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.ISubcategoriaDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Subcategoria;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.SubcategoriaSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.ISubcategoriaService;
import com.unfv.sistema_inventarios_api.presentation.controller.mapper.SubcategoriaRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.SubcategoriaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubcategoriaDtoServiceImpl implements ISubcategoriaDtoService {
    private final ISubcategoriaService subcategoriaService;
    private final SubcategoriaDtoMapper subcategoriaDtoMapper;
    private final SubcategoriaRequestMapper subcategoriaRequestMapper;


    @Override
    public Page<SubcategoriaDto> findAll(SubcategoriaSpecification specification, Pageable pageable) {
        return subcategoriaService.findAll(specification, pageable).map(subcategoriaDtoMapper::toDto);
    }

    @Override
    public SubcategoriaDto findByNombre(String nombre) {
        return subcategoriaDtoMapper.toDto(subcategoriaService.findByNombreOrThrowException(nombre));
    }

    @Override
    public SubcategoriaDto create(SubcategoriaRequest subcategoriaRequest) {
        validarSubcategoriaNombre(subcategoriaRequest.getNombre());
        Subcategoria subcategoriaCreada = subcategoriaService.create(subcategoriaRequestMapper.toEntity(subcategoriaRequest));
        return subcategoriaDtoMapper.toDto(subcategoriaCreada);
    }

    @Override
    public SubcategoriaDto update(String nombre, SubcategoriaRequest subcategoriaRequest) {
        Subcategoria subcategoria = subcategoriaService.findByNombreOrThrowException(nombre);
        if(!subcategoria.getNombre().equals(subcategoriaRequest.getNombre())){
            validarSubcategoriaNombre(subcategoriaRequest.getNombre());
        }
        Subcategoria subcategoriaActualizada = subcategoriaRequestMapper.toEntity(subcategoriaRequest);
        subcategoriaActualizada.setId(subcategoria.getId());
        return subcategoriaDtoMapper.toDto(subcategoriaService.update(subcategoriaActualizada));
    }

    @Override
    public void deleteByNombre(String nombre) {
        Subcategoria subcategoria = subcategoriaService.findByNombreOrThrowException(nombre);
        subcategoriaService.deleteById(subcategoria.getId());
    }

    private void validarSubcategoriaNombre(String nombre){
        Optional<Subcategoria> subcategoriaOptional = subcategoriaService.findByNombre(nombre);
        if(subcategoriaOptional.isPresent()){
            throw new DuplicateKeyException("La subcategoría '" + nombre + "' ya existe");
        }
    }
}
