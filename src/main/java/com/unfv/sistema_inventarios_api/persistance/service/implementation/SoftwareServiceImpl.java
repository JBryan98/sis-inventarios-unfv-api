package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.entity.Software;
import com.unfv.sistema_inventarios_api.persistance.repository.SoftwareRepository;
import com.unfv.sistema_inventarios_api.persistance.service.ISoftwareService;
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
public class SoftwareServiceImpl implements ISoftwareService {

    private final SoftwareRepository softwareRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Software> findAll(Pageable pageable) {
        return softwareRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Software> findByNombre(String nombre) {
        return softwareRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Software findByNombreOrThrowException(String nombre) {
        return softwareRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("El software '" + nombre + "' no existe"));
    }

    @Override
    public Software create(Software software) {
        return softwareRepository.save(software);
    }

    @Override
    public Software update(Software software) {
        return softwareRepository.save(software);
    }

    @Override
    public void deleteById(Long id) {
        softwareRepository.deleteById(id);
    }
}
