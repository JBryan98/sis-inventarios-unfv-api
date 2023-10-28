package com.unfv.sistema_inventarios_api.domain.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.domain.dto.ModeloDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Modelo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModeloDtoMapper extends GenericMapper<ModeloDto, Modelo> {
}