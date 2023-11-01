package com.unfv.sistema_inventarios_api.presentation.controller.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EquipoRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipoRequestMapper extends GenericMapper<EquipoRequest, Equipo> {
}
