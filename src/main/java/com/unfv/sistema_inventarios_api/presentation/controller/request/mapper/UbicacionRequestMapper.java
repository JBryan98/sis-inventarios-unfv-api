package com.unfv.sistema_inventarios_api.presentation.controller.request.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Ubicacion;
import com.unfv.sistema_inventarios_api.presentation.controller.request.UbicacionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UbicacionRequestMapper extends GenericMapper<UbicacionRequest, Ubicacion> {
}
