package com.unfv.sistema_inventarios_api.domain.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.domain.dto.CategoriaDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaDtoMapper extends GenericMapper<CategoriaDto, Categoria> {
}
