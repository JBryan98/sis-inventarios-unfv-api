package com.unfv.sistema_inventarios_api.persistance.repository;

import com.unfv.sistema_inventarios_api.persistance.entity.EquiposTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquiposTrabajoRepository extends JpaRepository<EquiposTrabajo, Long> {
    Optional<EquiposTrabajo> findBySerie(String serie);
}