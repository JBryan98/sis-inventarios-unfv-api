package com.unfv.sistema_inventarios_api.persistance.repository;

import com.unfv.sistema_inventarios_api.dashboard.dto.EquiposCountBySistemaOperativoDto;
import com.unfv.sistema_inventarios_api.dashboard.dto.EstadoCountDto;
import com.unfv.sistema_inventarios_api.dashboard.dto.Top5FacultadesByEquipoCountDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface EquipoRepository extends JpaRepository<Equipo, Long>, JpaSpecificationExecutor<Equipo> {
    Optional<Equipo> findByNombre(String nombre);

    @Query(value = "SELECT TOP 5 facultades.nombre, COUNT(equipos.id) as cantidad " +
            "FROM ubicaciones " +
            "INNER JOIN facultades ON ubicaciones.id_facultad = facultades.id " +
            "INNER JOIN equipos ON equipos.id_ubicacion = ubicaciones.id " +
            "GROUP BY facultades.nombre", nativeQuery = true)
    Set<Top5FacultadesByEquipoCountDto> top5Facultades();

    @Query(value = "SELECT software.nombre, COUNT(equipo_software.id_software) as cantidad " +
            "FROM equipo_software " +
            "INNER JOIN equipos ON equipos.id = equipo_software.id_equipo " +
            "INNER JOIN software ON software.id = equipo_software.id_software " +
            "INNER JOIN subcategorias ON software.id_subcategoria = subcategorias.id " +
            "WHERE UPPER(subcategorias.nombre) LIKE UPPER('Sistema operativo') " +
            "GROUP BY software.nombre", nativeQuery = true)
    Set<EquiposCountBySistemaOperativoDto> groupCountEquiposBySistemaOperativo();

    @Query(value = "SELECT " +
            "COUNT(CASE WHEN Estado = 'Operativo' THEN 1 END) as Operativo, " +
            "COUNT(CASE WHEN Estado = 'Mantenimiento' THEN 1 END) as Mantenimiento, " +
            "COUNT(CASE WHEN Estado = 'Stock' THEN 1 END) as Stock, " +
            "COUNT(CASE WHEN Estado = 'Baja' THEN 1 END) as Baja " +
            "FROM equipos " +
            "WHERE Estado IN ('Operativo', 'Stock', 'Mantenimiento', 'Baja')", nativeQuery = true)
    EstadoCountDto countEquiposEstado();

}
