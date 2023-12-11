package com.unfv.sistema_inventarios_api.domain.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.domain.dto.UbicacionConEquiposDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Ubicacion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UbicacionConEquiposDtoMapper extends GenericMapper<UbicacionConEquiposDto, Ubicacion> {
}
