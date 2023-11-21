package com.unfv.sistema_inventarios_api.presentation.controller.request.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Categoria;
import com.unfv.sistema_inventarios_api.presentation.controller.request.CategoriaRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaRequestMapper extends GenericMapper<CategoriaRequest, Categoria> {
}
