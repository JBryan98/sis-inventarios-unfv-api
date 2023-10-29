package com.unfv.sistema_inventarios_api.presentation.controller.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Facultad;
import com.unfv.sistema_inventarios_api.presentation.controller.request.FacultadRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultadRequestMapper extends GenericMapper<FacultadRequest, Facultad> {
}
