package com.unfv.sistema_inventarios_api.presentation.controller.request.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.EquiposTrabajo;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EquiposTrabajoRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquiposTrabajoRequestMapper extends GenericMapper<EquiposTrabajoRequest, EquiposTrabajo> {
}
