package com.unfv.sistema_inventarios_api.domain.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.domain.dto.SoftwareDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Software;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SoftwareDtoMapper extends GenericMapper<SoftwareDto, Software> {
}
