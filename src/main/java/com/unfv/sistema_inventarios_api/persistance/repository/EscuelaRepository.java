package com.unfv.sistema_inventarios_api.persistance.repository;

import com.unfv.sistema_inventarios_api.persistance.entity.Escuela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EscuelaRepository extends JpaRepository<Escuela, Long> {
    Optional<Escuela> findByAbreviaturaOrNombre(String abreviatura, String nombre);
    Optional<Escuela> findByAbreviatura(String abreviatura);
}
