package com.unfv.sistema_inventarios_api.domain.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.domain.dto.EquiposTrabajoDto;
import com.unfv.sistema_inventarios_api.persistance.entity.EquiposTrabajo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquiposTrabajoDtoMapper extends GenericMapper<EquiposTrabajoDto, EquiposTrabajo> {
}
