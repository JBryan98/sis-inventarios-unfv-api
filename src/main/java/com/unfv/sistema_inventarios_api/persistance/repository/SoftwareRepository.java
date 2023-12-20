package com.unfv.sistema_inventarios_api.persistance.repository;

import com.unfv.sistema_inventarios_api.persistance.entity.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long>, JpaSpecificationExecutor<Software> {
    Optional<Software> findByNombre(String nombre);
}
