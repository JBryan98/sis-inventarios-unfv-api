package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.entity.Subcategoria;
import com.unfv.sistema_inventarios_api.persistance.repository.SubcategoriaRepository;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.SubcategoriaSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.ISubcategoriaService;
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
public class SubcategoriaServiceImpl implements ISubcategoriaService {
    private final SubcategoriaRepository subcategoriaRepository;
    @Override
    @Transactional(readOnly = true)
    public Page<Subcategoria> findAll(SubcategoriaSpecification specification, Pageable pageable) {
        return subcategoriaRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Subcategoria> findByNombre(String nombre) {
        return subcategoriaRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Subcategoria findByNombreOrThrowException(String nombre) {
        return subcategoriaRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("La subcategoría '" + nombre + "' no existe"));
    }

    @Override
    public Subcategoria create(Subcategoria subcategoria) {
        return subcategoriaRepository.save(subcategoria);
    }

    @Override
    public Subcategoria update(Subcategoria subcategoria) {
        return subcategoriaRepository.save(subcategoria);
    }

    @Override
    public void deleteById(Long id) {
        subcategoriaRepository.deleteById(id);
    }
}
