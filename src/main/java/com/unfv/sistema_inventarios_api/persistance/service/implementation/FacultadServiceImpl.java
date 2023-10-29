package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.entity.Facultad;
import com.unfv.sistema_inventarios_api.persistance.repository.FacultadRepository;
import com.unfv.sistema_inventarios_api.persistance.service.IFacultadService;
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
public class FacultadServiceImpl implements IFacultadService {
    private final FacultadRepository facultadRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Facultad> findAll(Pageable pageable) {
        return facultadRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Facultad> findByAbreviaturaOrNombre(String abreviatura, String nombre) {
        return facultadRepository.findByAbreviaturaOrNombre(abreviatura, nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Facultad> findByAbreviatura(String abreviatura) {
        return facultadRepository.findByAbreviatura(abreviatura);
    }

    @Override
    @Transactional(readOnly = true)
    public Facultad findByAbreviaturaOrThrowException(String abreviatura) {
        return facultadRepository.findByAbreviatura(abreviatura)
                .orElseThrow(() -> new EntityNotFoundException("La facultad '" + abreviatura + "' no existe"));
    }

    @Override
    public Facultad create(Facultad facultad) {
        return facultadRepository.save(facultad);
    }

    @Override
    public Facultad update(Facultad facultad) {
        return facultadRepository.save(facultad);
    }

    @Override
    public void deleteById(Long id) {
        facultadRepository.deleteById(id);
    }
}
