package com.unfv.sistema_inventarios_api.persistance.repository;

import com.unfv.sistema_inventarios_api.persistance.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Optional<Marca> findByNombre(String nombre);
}
