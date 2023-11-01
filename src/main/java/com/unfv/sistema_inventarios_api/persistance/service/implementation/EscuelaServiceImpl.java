package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.entity.Escuela;
import com.unfv.sistema_inventarios_api.persistance.repository.EscuelaRepository;
import com.unfv.sistema_inventarios_api.persistance.service.IEscuelaService;
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
public class EscuelaServiceImpl implements IEscuelaService {
    private final EscuelaRepository escuelaRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Escuela> findAll(Pageable pageable) {
        return escuelaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Escuela> findByAbreviatura(String abrevitura) {
        return escuelaRepository.findByAbreviatura(abrevitura);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Escuela> findByNombre(String nombre) {
        return escuelaRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Escuela findByAbreviaturaOrThrowException(String abreviatura) {
        return escuelaRepository.findByAbreviatura(abreviatura)
                .orElseThrow(() -> new EntityNotFoundException("La escuela '" + abreviatura + "' no existe"));
    }

    @Override
    public Escuela create(Escuela escuela) {
        return escuelaRepository.save(escuela);
    }

    @Override
    public Escuela update(Escuela escuela) {
        return escuelaRepository.save(escuela);
    }

    @Override
    public void deleteById(Long id) {
        escuelaRepository.deleteById(id);
    }
}