package com.unfv.sistema_inventarios_api.domain.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.domain.dto.FacultadDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Facultad;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultadDtoMapper extends GenericMapper<FacultadDto, Facultad> {
}
