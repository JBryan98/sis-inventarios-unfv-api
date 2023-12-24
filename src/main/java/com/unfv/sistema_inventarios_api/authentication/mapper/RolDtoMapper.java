package com.unfv.sistema_inventarios_api.authentication.mapper;

import com.unfv.sistema_inventarios_api.authentication.dto.RolDto;
import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Rol;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolDtoMapper extends GenericMapper<RolDto, Rol> {
}
