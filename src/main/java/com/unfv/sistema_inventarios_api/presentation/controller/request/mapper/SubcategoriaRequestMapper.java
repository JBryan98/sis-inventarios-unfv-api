package com.unfv.sistema_inventarios_api.presentation.controller.request.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Subcategoria;
import com.unfv.sistema_inventarios_api.presentation.controller.request.SubcategoriaRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubcategoriaRequestMapper extends GenericMapper<SubcategoriaRequest, Subcategoria> {
}
