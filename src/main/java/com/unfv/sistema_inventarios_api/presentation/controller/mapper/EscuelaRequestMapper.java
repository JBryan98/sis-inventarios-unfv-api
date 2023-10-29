package com.unfv.sistema_inventarios_api.presentation.controller.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Escuela;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EscuelaRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EscuelaRequestMapper extends GenericMapper<EscuelaRequest, Escuela> {
}
