package com.unfv.sistema_inventarios_api.domain.service;

import com.unfv.sistema_inventarios_api.domain.dto.HardwareDto;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.HardwareSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.HardwareRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.DecoderException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface IHardwareDtoService {
    Page<HardwareDto> findAll(HardwareSpecification specification, Pageable pageable);

    HardwareDto findBySerie(String serie);

    HardwareDto create(HardwareRequest hardwareRequest);

    HardwareDto update(String serie, HardwareRequest hardwareRequest);

    void deleteBySerie(String serie);

    void downloadExcel(HttpServletResponse response, HardwareSpecification specification) throws IOException, DecoderException;
}
