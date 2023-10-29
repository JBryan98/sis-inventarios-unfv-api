package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Hardware;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.HardwareSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IHardwareService {
    Page<Hardware> findAll(HardwareSpecification specification, Pageable pageable);
    Optional<Hardware> findBySerie(String serie);
    Hardware findBySerieOrThrowException(String serie);
    Hardware create(Hardware hardware);
    Hardware update(Hardware hardware);
    void deleteById(Long id);
}
