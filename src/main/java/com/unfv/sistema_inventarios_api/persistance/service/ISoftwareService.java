package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Software;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.SoftwareSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ISoftwareService {
    Page<Software> findAll(SoftwareSpecification specification, Pageable pageable);
    Optional<Software> findByNombre(String nombre);
    Software findByNombreOrThrowException(String nombre);
    Software create(Software software);
    Software update(Software software);
    void deleteById(Long id);
}
