package com.unfv.sistema_inventarios_api.domain.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.domain.dto.SubcategoriaDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Subcategoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubcategoriaDtoMapper extends GenericMapper<SubcategoriaDto, Subcategoria> {
}
