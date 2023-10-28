package com.unfv.sistema_inventarios_api.domain.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.domain.dto.MarcaDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Marca;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarcaDtoMapper extends GenericMapper<MarcaDto, Marca> {
}
