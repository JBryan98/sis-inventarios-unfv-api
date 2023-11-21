package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.entity.Ubicacion;
import com.unfv.sistema_inventarios_api.persistance.repository.UbicacionRepository;
import com.unfv.sistema_inventarios_api.persistance.service.IUbicacionService;
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
public class UbicacionServiceImpl implements IUbicacionService {
    private final UbicacionRepository ubicacionRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Ubicacion> findAll(Pageable pageable) {
        return ubicacionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ubicacion> findByNombre(String nombre) {
        return ubicacionRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Ubicacion findByNombreOrThrowException(String nombre) {
        return ubicacionRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("La ubicación '" + nombre + "' no existe"));
    }

    @Override
    public Ubicacion create(Ubicacion ubicacion) {
        return ubicacionRepository.save(ubicacion);
    }

    @Override
    public Ubicacion update(Ubicacion ubicacion) {
        return ubicacionRepository.save(ubicacion);
    }

    @Override
    public void deleteById(Long id) {
        ubicacionRepository.deleteById(id);
    }
}
