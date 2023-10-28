package com.unfv.sistema_inventarios_api.presentation.controller.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Modelo;
import com.unfv.sistema_inventarios_api.presentation.controller.request.ModeloRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModeloRequestMapper extends GenericMapper<ModeloRequest, Modelo> {
}
