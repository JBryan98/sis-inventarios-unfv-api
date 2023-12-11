package com.unfv.sistema_inventarios_api.persistance.repository;

import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EquipoRepository extends JpaRepository<Equipo, Long>, JpaSpecificationExecutor<Equipo> {
    Optional<Equipo> findByNombre(String nombre);
}
