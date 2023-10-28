package com.unfv.sistema_inventarios_api.presentation.controller.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Marca;
import com.unfv.sistema_inventarios_api.presentation.controller.request.MarcaRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarcaRequestMapper extends GenericMapper<MarcaRequest, Marca> {
}
