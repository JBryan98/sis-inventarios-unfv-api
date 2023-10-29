package com.unfv.sistema_inventarios_api.domain.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.domain.dto.EscuelaDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Escuela;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EscuelaDtoMapper extends GenericMapper<EscuelaDto, Escuela> {
}
