package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.HardwareDto;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.HardwareSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.HardwareRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IHardwareDtoService {
    Page<HardwareDto> findAll(HardwareSpecification specification, Pageable pageable);
    HardwareDto findBySerie(String serie);
    HardwareDto create(HardwareRequest hardwareRequest);
    HardwareDto update(String serie, HardwareRequest hardwareRequest);
    void deleteBySerie(String serie);
}
