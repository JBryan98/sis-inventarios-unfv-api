package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Modelo;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.ModeloSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IModeloService {
    List<Modelo> findAllNoPage(ModeloSpecification specification);
    Page<Modelo> findAll(ModeloSpecification specification, Pageable pageable);
    Optional<Modelo> findById(Long id);
    Optional<Modelo> findByNombreAndSubcategoria_Nombre(String nombre, String subcategoriaNombre);
    Modelo findByIdOrThrowException(Long id);
    Optional<Modelo> findByNombre(String nombre);
    Modelo findByNombreOrThrowException(String nombre);
    Modelo create(Modelo modelo);
    Modelo update(Modelo modelo);
    void deleteById(Long id);
}
