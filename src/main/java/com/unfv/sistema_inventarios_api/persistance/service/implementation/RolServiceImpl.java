package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.entity.Rol;
import com.unfv.sistema_inventarios_api.persistance.repository.RolRepository;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.RolSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IRolService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RolServiceImpl implements IRolService {
    private final RolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Rol> findAll(RolSpecification specification, Pageable pageable) {
        return rolRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Rol findByNombreOrThrowException(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("El rol '" + nombre + "' no existe"));
    }

    @Override
    public Rol create(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public Rol update(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void deleteById(Long id) {
        rolRepository.deleteById(id);
    }
}
