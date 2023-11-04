package com.unfv.sistema_inventarios_api.persistance.repository;

import com.unfv.sistema_inventarios_api.persistance.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    Optional<Modelo> findByNombre(String nombre);
    Optional<Modelo> findByNombreAndSubcategoria_Nombre(String nombre, String subcategoriaNombre);
}
