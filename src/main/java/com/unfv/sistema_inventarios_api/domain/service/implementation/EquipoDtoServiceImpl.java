package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.common.reports.ExcelReportHelper;
import com.unfv.sistema_inventarios_api.domain.dto.EquipoConComponentesDto;
import com.unfv.sistema_inventarios_api.domain.dto.EquipoDto;
import com.unfv.sistema_inventarios_api.domain.mapper.EquipoConComponentesMapper;
import com.unfv.sistema_inventarios_api.domain.mapper.EquipoDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IEquipoDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import com.unfv.sistema_inventarios_api.persistance.entity.Hardware;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.EquipoSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IEquipoService;
import com.unfv.sistema_inventarios_api.persistance.service.IHardwareService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.mapper.EquipoRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EquipoRequest;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipoDtoServiceImpl implements IEquipoDtoService {
    private final IEquipoService equipoService;
    private final IHardwareService hardwareService;
    private final EquipoDtoMapper equiDtoMapper;
    private final EquipoRequestMapper equipoRequestMapper;
    private final EquipoConComponentesMapper equipoConComponentesMapper;
    private final ExcelReportHelper excelReportHelper;

    @Override
    public Page<EquipoDto> findAll(EquipoSpecification specification, Pageable pageable) {
        return equipoService.findAll(specification, pageable).map(equiDtoMapper::toDto);
    }

    @Override
    public EquipoConComponentesDto findByNombre(String nombre) {
        return equipoConComponentesMapper.toDto(equipoService.findByNombreOrThrowException(nombre));
    }

    /**
     * Servicio para crear un nuevo equipo con su hardware y software
     */
    @Override
    public EquipoConComponentesDto create(EquipoRequest equipoRequest) {
        validarNombreUnico(equipoRequest.getNombre());
        Equipo equipoPersistido = persistirNuevoEquipo(equipoRequest);
        return equipoConComponentesMapper.toDto(equipoPersistido);
    }

    /**
     * Servicio para actualizar el equipo, más no sus componentes
     */
    @Override
    public EquipoConComponentesDto update(String nombre, EquipoRequest equipoRequest) {
        Equipo equipo = equipoService.findByNombreOrThrowException(nombre);
        if (!equipo.getNombre().equals(equipoRequest.getNombre())) {
            validarNombreUnico(equipoRequest.getNombre());
        }
        Equipo equipoMapeado = equipoRequestMapper.update(equipo, equipoRequest);
        equipoMapeado.setId(equipo.getId());
        equipoMapeado.setSoftware(equipo.getSoftware());
        Equipo equipoActualizado = equipoService.update(equipoMapeado);
        return equipoConComponentesMapper.toDto(equipoActualizado);
    }

    /**
     * Servicio para administrar el hardware y software del equipo
     * El software se persiste en cascada junto al equipo, pero el hardware no
     *
     * @param equipoRequest Request con el hardware y sofware
     *                      Si el software fue removido, no llegará en el arreglo y gracias a cascade y orphan removal será removido
     *                      Si el hardware fue removido, llegará con su equipo en null
     */
    @Override
    public EquipoConComponentesDto administrarEquipoHardwareYSoftware(EquipoRequest equipoRequest) {
        Equipo equipo = equipoService.findByNombreOrThrowException(equipoRequest.getNombre());
        Equipo equipoMapeado = equipoRequestMapper.toEntity(equipoRequest);
        equipo.setSoftware(equipoMapeado.getSoftware());
        actualizarEquipoHardware(equipoMapeado.getHardware());
        Equipo equipoActualizado = equipoService.update(equipo);
        return equipoConComponentesMapper.toDto(equipoActualizado);
    }

    @Override
    public void deleteByNombre(String nombre) {
        Equipo equipo = equipoService.findByNombreOrThrowException(nombre);
        equipoService.deleteById(equipo.getId());
    }

    @Override
    public void downloadExcel(HttpServletResponse response, EquipoSpecification specification) throws IOException, DecoderException {
        List<Equipo> equipos = equipoService.findAllNoPage(specification);
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Equipos");
        Row row = sheet.createRow(0);
        XSSFCellStyle style = excelReportHelper.tableHeaderColumnStyle(workbook);

        Cell idHeaderCell = row.createCell(0);
        idHeaderCell.setCellValue("ID");
        idHeaderCell.setCellStyle(style);

        Cell nombreHeaderCell = row.createCell(1);
        nombreHeaderCell.setCellValue("NOMBRE");
        nombreHeaderCell.setCellStyle(style);

        Cell estadoHeaderCell = row.createCell(2);
        estadoHeaderCell.setCellValue("ESTADO");
        estadoHeaderCell.setCellStyle(style);

        Cell ubicacionHeaderCell = row.createCell(3);
        ubicacionHeaderCell.setCellValue("UBICACIÓN");
        ubicacionHeaderCell.setCellStyle(style);

        int dataRowIndex = 1;

        for (Equipo equipo : equipos) {
            Row newRow = sheet.createRow(dataRowIndex);
            newRow.createCell(0).setCellValue(equipo.getId());
            newRow.createCell(1).setCellValue(equipo.getNombre());
            newRow.createCell(2).setCellValue(equipo.getEstado());
            newRow.createCell(3).setCellValue(equipo.getUbicacion() == null ? "No asignado" : equipo.getUbicacion().getNombre());
            dataRowIndex++;
        }

        excelReportHelper.autoSizeSheetColumns(sheet, 3);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void validarNombreUnico(String nombre) {
        Optional<Equipo> equipoOptional = equipoService.findByNombre(nombre);
        if (equipoOptional.isPresent()) {
            throw new DuplicateKeyException("El equipo '" + nombre + "' ya existe");
        }
    }

    public Equipo persistirNuevoEquipo(EquipoRequest equipoRequest) {
        Equipo nuevoEquipo = equipoRequestMapper.toEntity(equipoRequest);
        nuevoEquipo.setEstado("Stock");
        Equipo equipoPersistido = equipoService.create(nuevoEquipo);
        if (!nuevoEquipo.getHardware().isEmpty()) {
            setEquipoToHardware(equipoPersistido, nuevoEquipo.getHardware());
        }
        return equipoPersistido;
    }


    /**
     * Método para actualizar el hardware una vez creado el equipo, aquí se actualizará su estado a "Operativo" y
     * se le asignará como equipo, el equipo persistido en la BD.
     * Los hardwares asignados a un equipo deben actualizar su estado a "Operativo"
     *
     * @param equipoCreado Este objeto contiene los datos del nuevo equipo, pero que aún no tiene su hardware asignado
     * @param hardware     Este objeto contiene el arreglo de hardware enviado desde el request, pero
     *                     estos equipos aún no tienen un equipo asignado
     */
    public void setEquipoToHardware(Equipo equipoCreado, Set<Hardware> hardware) {
        Set<Hardware> hardwareConEquipo = hardware.stream().map(hw -> {
            hw.setEstado("Operativo");
            hw.setEquipo(equipoCreado);
            return hw;
        }).collect(Collectors.toSet());
        hardwareService.saveAll(hardwareConEquipo);
    }

    /**
     * Método para actualizar el estado del hardware a "Stock" u "Operativo"
     * Dependiendo de si el hardware fue añadido o removido del equipo
     * Si el hardware fue removido, este llegará con equipo = null
     * Y deberá pasar a "Stock"
     *
     * @param hardware Arreglo de hardware con los cambios
     */
    public void actualizarEquipoHardware(Set<Hardware> hardware) {
        for (Hardware hw : hardware) {
            if (hw.getEquipo() == null) {
                hw.setEstado("Stock");
            } else {
                hw.setEstado("Operativo");
            }
        }
        hardwareService.saveAll(hardware);
    }
}
