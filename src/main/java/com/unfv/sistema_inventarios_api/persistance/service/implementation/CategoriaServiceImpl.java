package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.service.ICategoriaService;
import com.unfv.sistema_inventarios_api.persistance.entity.Categoria;
import com.unfv.sistema_inventarios_api.persistance.repository.CategoriaRepository;
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
public class CategoriaServiceImpl implements ICategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Categoria> findAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Categoria> findByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }

    @Override
    public Categoria findByNombreOrThrowException(String nombre) {
        return categoriaRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("La categoría '" + nombre + "' no existe"));
    }

    @Override
    public Categoria create(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria update(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }
}
