package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.domain.dto.HardwareDto;
import com.unfv.sistema_inventarios_api.domain.mapper.HardwareDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IHardwareDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Hardware;
import com.unfv.sistema_inventarios_api.persistance.service.IHardwareService;
import com.unfv.sistema_inventarios_api.presentation.controller.mapper.HardwareRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.HardwareRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HardwareDtoServiceImpl implements IHardwareDtoService {

    private final IHardwareService hardwareService;
    private final HardwareDtoMapper hardwareDtoMapper;
    private final HardwareRequestMapper hardwareRequestMapper;

    @Override
    public Page<HardwareDto> findAll(Pageable pageable) {
        return hardwareService.findAll(pageable).map(hardwareDtoMapper::toDto);
    }

    @Override
    public HardwareDto findBySerie(String serie) {
        return hardwareDtoMapper.toDto(hardwareService.findBySerieOrThrowException(serie));
    }

    @Override
    public HardwareDto create(HardwareRequest hardwareRequest) {
        validateHardware(hardwareRequest.getSerie());
        Hardware hardwareCreado = hardwareService.create(hardwareRequestMapper.toEntity(hardwareRequest));
        return hardwareDtoMapper.toDto(hardwareCreado);
    }

    @Override
    public HardwareDto update(String serie, HardwareRequest hardwareRequest) {
        Hardware hardware = hardwareService.findBySerieOrThrowException(serie);
        if(!hardware.getSerie().equals(hardwareRequest.getSerie())){
            validateHardware(hardwareRequest.getSerie());
        }
        Hardware hardwareActualizado = hardwareService.update(hardwareRequestMapper.toEntity(hardwareRequest));
        return hardwareDtoMapper.toDto(hardwareActualizado);
    }

    @Override
    public void deleteBySerie(String serie) {
        Hardware hardware = hardwareService.findBySerieOrThrowException(serie);
        hardwareService.deleteById(hardware.getId());
    }

    private void validateHardware(String serie){
        Optional<Hardware> hardware = hardwareService.findBySerie(serie);
        if(hardware.isPresent()){
            throw new DuplicateKeyException("El hardware con serie'" + serie + "' ya existe");
        }
    }
}
