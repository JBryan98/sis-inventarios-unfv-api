package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.entity.Hardware;
import com.unfv.sistema_inventarios_api.persistance.repository.HardwareRepository;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.HardwareSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IHardwareService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class HardServiceImpl implements IHardwareService {
    private final HardwareRepository hardwareRepository;
    @Override
    @Transactional(readOnly = true)
    public Page<Hardware> findAll(HardwareSpecification specification, Pageable pageable) {
        return hardwareRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Hardware> findBySerie(String serie) {
        return hardwareRepository.findBySerie(serie);
    }

    @Override
    @Transactional(readOnly = true)
    public Hardware findBySerieOrThrowException(String serie) {
        return hardwareRepository.findBySerie(serie)
                .orElseThrow(() -> new EntityNotFoundException("El hardware '" + serie + "' no existe"));
    }

    @Override
    public Hardware create(Hardware hardware) {
        return hardwareRepository.save(hardware);
    }

    @Override
    public Hardware update(Hardware hardware) {
        return hardwareRepository.save(hardware);
    }

    @Override
    public void deleteById(Long id) {
        hardwareRepository.deleteById(id);
    }
}
