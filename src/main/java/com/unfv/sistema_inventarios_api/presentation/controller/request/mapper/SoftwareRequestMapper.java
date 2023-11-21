package com.unfv.sistema_inventarios_api.presentation.controller.request.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Software;
import com.unfv.sistema_inventarios_api.presentation.controller.request.SoftwareRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SoftwareRequestMapper extends GenericMapper<SoftwareRequest, Software> {
}
