package com.unfv.sistema_inventarios_api.presentation.controller.request.mapper;

import com.unfv.sistema_inventarios_api.common.util.mapper.GenericMapper;
import com.unfv.sistema_inventarios_api.persistance.entity.Hardware;
import com.unfv.sistema_inventarios_api.presentation.controller.request.HardwareRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HardwareRequestMapper extends GenericMapper<HardwareRequest, Hardware> {
}
