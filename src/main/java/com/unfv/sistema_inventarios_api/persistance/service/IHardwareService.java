package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Hardware;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.HardwareSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IHardwareService {
    Page<Hardware> findAll(HardwareSpecification specification, Pageable pageable);
    Optional<Hardware> findBySerie(String serie);
    Hardware findBySerieOrThrowException(String serie);
    List<Hardware> saveAll(Set<Hardware> hardware);
    Hardware create(Hardware hardware);
    Hardware update(Hardware hardware);
    void deleteById(Long id);
}
