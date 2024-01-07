package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.common.reports.ExcelReportHelper;
import com.unfv.sistema_inventarios_api.domain.dto.EquiposTrabajoDto;
import com.unfv.sistema_inventarios_api.domain.mapper.EquiposTrabajoDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IEquiposTrabajoDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.EquiposTrabajo;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.EquiposTrabajoSpecification;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.HardwareSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IEquiposTrabajoService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EquiposTrabajoRequest;
import com.unfv.sistema_inventarios_api.presentation.controller.request.mapper.EquiposTrabajoRequestMapper;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquiposTrabajoDtoService implements IEquiposTrabajoDtoService {
    private final IEquiposTrabajoService equiposTrabajoService;
    private final EquiposTrabajoRequestMapper equiposTrabajoRequestMapper;
    private final EquiposTrabajoDtoMapper equiposTrabajoDtoMapper;
    private final ExcelReportHelper excelReportHelper;

    @Override
    public Page<EquiposTrabajoDto> findAll(EquiposTrabajoSpecification specification, Pageable pageable) {
        return equiposTrabajoService.findAll(specification, pageable).map(equiposTrabajoDtoMapper::toDto);
    }

    @Override
    public EquiposTrabajoDto findBySerie(String serie) {
        return equiposTrabajoDtoMapper.toDto(equiposTrabajoService.findBySerieOrThrowException(serie));
    }

    @Override
    public EquiposTrabajoDto create(EquiposTrabajoRequest equiposTrabajoRequest) {
        validarSerie(equiposTrabajoRequest.getSerie());
        EquiposTrabajo equiposTrabajoCreado = equiposTrabajoService.create(equiposTrabajoRequestMapper.toEntity(equiposTrabajoRequest));
        return equiposTrabajoDtoMapper.toDto(equiposTrabajoCreado);
    }

    @Override
    public EquiposTrabajoDto update(String serie, EquiposTrabajoRequest equiposTrabajoRequest) {
        EquiposTrabajo equiposTrabajo = equiposTrabajoService.findBySerieOrThrowException(serie);
        if(!equiposTrabajo.getSerie().equals(equiposTrabajoRequest.getSerie())){
            validarSerie(equiposTrabajoRequest.getSerie());
        }
        EquiposTrabajo equiposTrabajoActualizado = equiposTrabajoRequestMapper.toEntity(equiposTrabajoRequest);
        equiposTrabajoActualizado.setId(equiposTrabajo.getId());
        log.info(equiposTrabajoActualizado.toString());
        return equiposTrabajoDtoMapper.toDto(equiposTrabajoService.update(equiposTrabajoActualizado));
    }

    @Override
    public void deleteByNombre(String serie) {
        EquiposTrabajo equiposTrabajo = equiposTrabajoService.findBySerieOrThrowException(serie);
        equiposTrabajoService.deleteById(equiposTrabajo.getId());
    }

    @Override
    public void downloadExcel(HttpServletResponse response, EquiposTrabajoSpecification specification) throws IOException, DecoderException {
        List<EquiposTrabajo> equiposTrabajos = equiposTrabajoService.findAllNoPage(specification);
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Equipos de trabajo");
        Row row = sheet.createRow(0);
        XSSFCellStyle style = excelReportHelper.tableHeaderColumnStyle(workbook);

        Cell idHeaderCell = row.createCell(0);
        idHeaderCell.setCellValue("ID");
        idHeaderCell.setCellStyle(style);

        Cell serieHeaderCell = row.createCell(1);
        serieHeaderCell.setCellValue("SERIE");
        serieHeaderCell.setCellStyle(style);

        Cell estadoHeaderCell = row.createCell(2);
        estadoHeaderCell.setCellValue("ESTADO");
        estadoHeaderCell.setCellStyle(style);

        Cell ubicacionHeaderCell = row.createCell(3);
        ubicacionHeaderCell.setCellValue("UBICACIÓN");
        ubicacionHeaderCell.setCellStyle(style);


        Cell modeloHeaderCell = row.createCell(4);
        modeloHeaderCell.setCellValue("MODELO");
        modeloHeaderCell.setCellStyle(style);

        Cell subcategoriaHeaderCell = row.createCell(5);
        subcategoriaHeaderCell.setCellValue("SUBCATEGORÍA");
        subcategoriaHeaderCell.setCellStyle(style);

        Cell marcaHeaderCell = row.createCell(6);
        marcaHeaderCell.setCellValue("MARCA");
        marcaHeaderCell.setCellStyle(style);

        int dataRowIndex = 1;

        for(EquiposTrabajo et: equiposTrabajos){
            Row newRow = sheet.createRow(dataRowIndex);
            newRow.createCell(0).setCellValue(et.getId());
            newRow.createCell(1).setCellValue(et.getSerie());
            newRow.createCell(2).setCellValue(et.getEstado());
            newRow.createCell(3).setCellValue(et.getUbicacion() == null ? "No asignado" : et.getUbicacion().getNombre());
            newRow.createCell(4).setCellValue(et.getModelo().getNombre());
            newRow.createCell(5).setCellValue(et.getModelo().getSubcategoria().getNombre());
            newRow.createCell(6).setCellValue(et.getModelo().getMarca().getNombre());
            dataRowIndex++;
        }

        excelReportHelper.autoSizeSheetColumns(sheet, 6);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
    /*
    * Método para validar que la serie del equipo de trabajo no sea duplicada
    * @param número de serie del equipo de trabajo request
    * */

    private void validarSerie(String serie){
        Optional<EquiposTrabajo> equipo = equiposTrabajoService.findBySerie(serie);
        if(equipo.isPresent()){
            throw new DuplicateKeyException("El equipo de trabajo con serie '" + serie + "' ya existe");
        }
    }
}
