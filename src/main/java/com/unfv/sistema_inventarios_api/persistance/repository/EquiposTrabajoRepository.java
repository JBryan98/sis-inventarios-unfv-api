package com.unfv.sistema_inventarios_api.persistance.repository;

import com.unfv.sistema_inventarios_api.dashboard.dto.EstadoCountDto;
import com.unfv.sistema_inventarios_api.dashboard.dto.Top5ByMarcaDto;
import com.unfv.sistema_inventarios_api.dashboard.dto.Top5ByModeloDto;
import com.unfv.sistema_inventarios_api.persistance.entity.EquiposTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EquiposTrabajoRepository extends JpaRepository<EquiposTrabajo, Long>, JpaSpecificationExecutor<EquiposTrabajo> {
    Optional<EquiposTrabajo> findBySerie(String serie);

    @Query(value = "SELECT TOP 5 modelo.nombre AS modelo, subcategorias.nombre AS subcategoria, marcas.nombre AS marca, COUNT(modelo.id) AS cantidad " +
            "FROM modelo " +
            "INNER JOIN equipos_trabajo ON equipos_trabajo.id_modelo = modelo.id " +
            "INNER JOIN subcategorias ON subcategorias.id = modelo.id_subcategoria " +
            "INNER JOIN marcas ON marcas.id = modelo.id_marca " +
            "GROUP BY modelo.nombre, subcategorias.nombre, marcas.nombre " +
            "ORDER BY cantidad DESC", nativeQuery = true)
    Set<Top5ByModeloDto> findTop5EquiposTrabajoByModelo();

    @Query(value = "SELECT TOP 5 marcas.nombre AS marca, COUNT(modelo.id) AS cantidad " +
            "FROM modelo " +
            "INNER JOIN equipos_trabajo ON equipos_trabajo.id_modelo = modelo.id " +
            "INNER JOIN subcategorias ON subcategorias.id = modelo.id_subcategoria " +
            "INNER JOIN marcas ON marcas.id = modelo.id_marca " +
            "GROUP BY marcas.nombre " +
            "ORDER BY cantidad DESC", nativeQuery = true)
    Set<Top5ByMarcaDto> findTop5EquiposTrabajoByMarca();

    @Query(value = "SELECT " +
            "COUNT(CASE WHEN Estado = 'Operativo' THEN 1 END) as Operativo, " +
            "COUNT(CASE WHEN Estado = 'Mantenimiento' THEN 1 END) as Mantenimiento, " +
            "COUNT(CASE WHEN Estado = 'Stock' THEN 1 END) as Stock, " +
            "COUNT(CASE WHEN Estado = 'Baja' THEN 1 END) as Baja " +
            "FROM equipos_trabajo " +
            "WHERE Estado IN ('Operativo', 'Stock', 'Mantenimiento', 'Baja')", nativeQuery = true)
    EstadoCountDto countEquiposTrabajoEstado();
}