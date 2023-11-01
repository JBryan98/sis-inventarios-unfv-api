package com.unfv.sistema_inventarios_api.persistance.repository;

import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    Optional<Equipo> findByNombre(String nombre);
}
