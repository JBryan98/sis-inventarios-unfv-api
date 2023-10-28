package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.entity.Marca;
import com.unfv.sistema_inventarios_api.persistance.repository.MarcaRepository;
import com.unfv.sistema_inventarios_api.persistance.service.IMarcaService;
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
public class MarcaServiceImpl implements IMarcaService {

    private final MarcaRepository marcaRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Marca> findAll(Pageable pageable) {
        return marcaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Marca> findByNombre(String nombre) {
        return marcaRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Marca findByNombreOrThrowException(String nombre) {
        return marcaRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("La marca '" + nombre + "' no existe"));
    }

    @Override
    public Marca create(Marca categoria) {
        return marcaRepository.save(categoria);
    }

    @Override
    public Marca update(Marca categoria) {
        return marcaRepository.save(categoria);
    }

    @Override
    public void deleteById(Long id) {
        marcaRepository.findById(id);
    }
}
