package com.unfv.sistema_inventarios_api.domain.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.domain.dto.EquipoConComponentesDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipoConComponentesMapper extends GenericMapper<EquipoConComponentesDto, Equipo> {
}
