package com.unfv.sistema_inventarios_api.authentication.mapper;

import com.unfv.sistema_inventarios_api.authentication.dto.UsuarioDto;
import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioDtoMapper extends GenericMapper<UsuarioDto, Usuario> {
}
