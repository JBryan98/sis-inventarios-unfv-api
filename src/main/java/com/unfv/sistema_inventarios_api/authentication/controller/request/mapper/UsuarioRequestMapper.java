package com.unfv.sistema_inventarios_api.authentication.controller.request.mapper;

import com.unfv.sistema_inventarios_api.authentication.controller.request.UsuarioRequest;
import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioRequestMapper extends GenericMapper<UsuarioRequest, Usuario> {
}