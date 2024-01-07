package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.entity.Modelo;
import com.unfv.sistema_inventarios_api.persistance.repository.ModeloRepository;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.ModeloSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IModeloService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ModeloServiceImpl implements IModeloService {
    private final ModeloRepository modeloRepository;

    @Override
    public List<Modelo> findAllNoPage(ModeloSpecification specification) {
        return modeloRepository.findAll(specification);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Modelo> findAll(ModeloSpecification specification, Pageable pageable) {
        return modeloRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<Modelo> findById(Long id) {
        return modeloRepository.findById(id);
    }

    @Override
    public Optional<Modelo> findByNombreAndSubcategoria_Nombre(String nombre, String subcategoriaNombre) {
        return modeloRepository.findByNombreAndSubcategoria_Nombre(nombre, subcategoriaNombre);
    }

    @Override
    public Modelo findByIdOrThrowException(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El modelo '" + id + "' no existe"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Modelo> findByNombre(String nombre) {
        return modeloRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Modelo findByNombreOrThrowException(String nombre) {
        return modeloRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("El modelo '" + nombre + "' no existe"));
    }

    @Override
    public Modelo create(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    @Override
    public Modelo update(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    @Override
    public void deleteById(Long id) {
        modeloRepository.deleteById(id);
    }
}
