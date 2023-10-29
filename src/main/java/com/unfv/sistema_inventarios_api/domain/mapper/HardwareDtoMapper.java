package com.unfv.sistema_inventarios_api.domain.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.domain.dto.HardwareDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Hardware;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HardwareDtoMapper extends GenericMapper<HardwareDto, Hardware> {
}
