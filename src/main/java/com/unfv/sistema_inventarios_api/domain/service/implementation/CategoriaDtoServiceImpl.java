package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.CategoriaDto;
import com.unfv.sistema_inventarios_api.domain.mapper.CategoriaDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.ICategoriaDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Categoria;
import com.unfv.sistema_inventarios_api.persistance.service.ICategoriaService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.mapper.CategoriaRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.CategoriaRequest;
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
public class CategoriaDtoServiceImpl implements ICategoriaDtoService {
    private final ICategoriaService categoriaService;
    private final CategoriaDtoMapper categoriaDtoMapper;
    private final CategoriaRequestMapper categoriaRequestMapper;
    @Override
    public Page<CategoriaDto> findAll(Pageable pageable) {
        return categoriaService.findAll(pageable).map(categoriaDtoMapper::toDto);
    }
    @Override
    public CategoriaDto findByNombre(String nombre) {
        return categoriaDtoMapper.toDto(categoriaService.findByNombreOrThrowException(nombre));
    }

    @Override
    public CategoriaDto create(CategoriaRequest request) {
        Optional<Categoria> categoriaOptional = categoriaService.findByNombre(request.getNombre());
        if(categoriaOptional.isPresent()){
            throw new DuplicateKeyException("La categoria '" + request.getNombre() + "' ya existe");
        }
        Categoria categoria = categoriaService.create(categoriaRequestMapper.toEntity(request));
        return categoriaDtoMapper.toDto(categoria);
    }

    @Override
    public CategoriaDto update(String nombre, CategoriaRequest request) {
        Categoria categoria = categoriaService.findByNombreOrThrowException(nombre);
        if(!categoria.getNombre().equals(request.getNombre())){
            Optional<Categoria> categoriaOptional = categoriaService.findByNombre(request.getNombre());
            if(categoriaOptional.isPresent()){
                throw new DuplicateKeyException("La categoria '" + request.getNombre() + "' ya existe");
            }
        }
        Categoria categoriaActualizada = categoriaService.update(categoriaRequestMapper.update(categoria, request));
        return categoriaDtoMapper.toDto(categoriaActualizada);
    }

    @Override
    public void delete(String nombre) {
        Categoria categoria = categoriaService.findByNombreOrThrowException(nombre);
        categoriaService.deleteById(categoria.getId());
    }
}
