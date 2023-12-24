package com.unfv.sistema_inventarios_api.authentication.controller.request.mapper;

import com.unfv.sistema_inventarios_api.authentication.controller.request.RolRequest;
import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Rol;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolRequestMapper extends GenericMapper<RolRequest, Rol> {
}
