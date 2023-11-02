package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import com.unfv.sistema_inventarios_api.persistance.repository.EquipoRepository;
import com.unfv.sistema_inventarios_api.persistance.service.IEquipoService;
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
public class EquipoServiceImpl implements IEquipoService {
    private final EquipoRepository equipoRepository;
    @Override
    public Page<Equipo> findAll(Pageable pageable) {
        return equipoRepository.findAll(pageable);
    }

    @Override
    public Optional<Equipo> findByNombre(String nombre) {
        return equipoRepository.findByNombre(nombre);
    }

    @Override
    public Equipo findByNombreOrThrowException(String nombre) {
        return equipoRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("El equipo '" + nombre + "' no existe"));
    }

    @Override
    public Equipo create(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    public Equipo update(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    public void deleteById(Long id) {
        equipoRepository.deleteById(id);
    }
}