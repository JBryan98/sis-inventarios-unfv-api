package com.unfv.sistema_inventarios_api.persistance.repository;

import com.unfv.sistema_inventarios_api.dashboard.dto.EstadoCountDto;
import com.unfv.sistema_inventarios_api.dashboard.dto.Top5ByMarcaDto;
import com.unfv.sistema_inventarios_api.dashboard.dto.Top5ByModeloDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface HardwareRepository extends JpaRepository<Hardware, Long>, JpaSpecificationExecutor<Hardware> {
    Optional<Hardware> findBySerie(String serie);



    @Query(value = "SELECT TOP 5 modelo.nombre AS modelo, subcategorias.nombre AS subcategoria, marcas.nombre AS marca, COUNT(modelo.id) AS cantidad " +
            "FROM modelo " +
            "INNER JOIN hardware ON hardware.id_modelo = modelo.id " +
            "INNER JOIN subcategorias ON subcategorias.id = modelo.id_subcategoria " +
            "INNER JOIN marcas ON marcas.id = modelo.id_marca " +
            "GROUP BY modelo.nombre, subcategorias.nombre, marcas.nombre " +
            "ORDER BY cantidad DESC", nativeQuery = true)
    Set<Top5ByModeloDto> findTop5HardwareByModelo();

    @Query(value = "SELECT TOP 5 marcas.nombre AS marca, COUNT(modelo.id) AS cantidad " +
            "FROM modelo " +
            "INNER JOIN hardware ON hardware.id_modelo = modelo.id " +
            "INNER JOIN subcategorias ON subcategorias.id = modelo.id_subcategoria " +
            "INNER JOIN marcas ON marcas.id = modelo.id_marca " +
            "GROUP BY marcas.nombre " +
            "ORDER BY cantidad DESC", nativeQuery = true)
    Set<Top5ByMarcaDto> findTop5HardwareByMarca();

    @Query(value = "SELECT " +
            "COUNT(CASE WHEN Estado = 'Operativo' THEN 1 END) as Operativo, " +
            "COUNT(CASE WHEN Estado = 'Mantenimiento' THEN 1 END) as Mantenimiento, " +
            "COUNT(CASE WHEN Estado = 'Stock' THEN 1 END) as Stock, " +
            "COUNT(CASE WHEN Estado = 'Baja' THEN 1 END) as Baja " +
            "FROM hardware " +
            "WHERE Estado IN ('Operativo', 'Stock', 'Mantenimiento', 'Baja')", nativeQuery = true)
    EstadoCountDto countHardwareEstado();
}
