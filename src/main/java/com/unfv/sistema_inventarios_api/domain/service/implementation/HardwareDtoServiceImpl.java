package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.common.reports.ExcelReportHelper;
import com.unfv.sistema_inventarios_api.domain.dto.HardwareDto;
import com.unfv.sistema_inventarios_api.domain.mapper.HardwareDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IHardwareDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Hardware;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.HardwareSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IHardwareService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.mapper.HardwareRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.HardwareRequest;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
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
public class HardwareDtoServiceImpl implements IHardwareDtoService {

    private final IHardwareService hardwareService;
    private final HardwareDtoMapper hardwareDtoMapper;
    private final HardwareRequestMapper hardwareRequestMapper;
    private final ExcelReportHelper excelReportHelper;

    @Override
    public Page<HardwareDto> findAll(HardwareSpecification specification, Pageable pageable) {
        return hardwareService.findAll(specification, pageable).map(hardwareDtoMapper::toDto);
    }

    @Override
    public HardwareDto findBySerie(String serie) {
        return hardwareDtoMapper.toDto(hardwareService.findBySerieOrThrowException(serie));
    }

    @Override
    public HardwareDto create(HardwareRequest hardwareRequest) {
        validateHardware(hardwareRequest.getSerie());
        Hardware hardwareCreado = hardwareService.create(hardwareRequestMapper.toEntity(hardwareRequest));
        return hardwareDtoMapper.toDto(hardwareCreado);
    }

    @Override
    public HardwareDto update(String serie, HardwareRequest hardwareRequest) {
        Hardware hardware = hardwareService.findBySerieOrThrowException(serie);
        if(!hardware.getSerie().equals(hardwareRequest.getSerie())){
            validateHardware(hardwareRequest.getSerie());
        }
        Hardware hardwareActualizado = hardwareService.update(hardwareRequestMapper.update(hardware, hardwareRequest));
        return hardwareDtoMapper.toDto(hardwareActualizado);
    }

    @Override
    public void deleteBySerie(String serie) {
        Hardware hardware = hardwareService.findBySerieOrThrowException(serie);
        hardwareService.deleteById(hardware.getId());
    }

    @Override
    public void downloadExcel(HttpServletResponse response, HardwareSpecification specification) throws IOException, DecoderException {
        List<Hardware> hardware = hardwareService.findAllNoPage(specification);
        //XSS para archivos excel .xlsx
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hardware");
        Row row = sheet.createRow(0);
        XSSFCellStyle style = excelReportHelper.tableHeaderColumnStyle(workbook);

        Cell idHeaderCell = row.createCell(0);
        idHeaderCell.setCellValue("ID");
        idHeaderCell.setCellStyle(style);

        Cell serieHeaderCell= row.createCell(1);
        serieHeaderCell.setCellValue("SERIE");
        serieHeaderCell.setCellStyle(style);

        Cell estadoHeaderCell= row.createCell(2);
        estadoHeaderCell.setCellValue("ESTADO");
        estadoHeaderCell.setCellStyle(style);

        Cell equipoHeaderCell = row.createCell(3);
        equipoHeaderCell.setCellValue("EQUIPO");
        equipoHeaderCell.setCellStyle(style);

        Cell modeloHeaderCell = row.createCell(4);
        modeloHeaderCell.setCellValue("MODELO");
        modeloHeaderCell.setCellStyle(style);

        Cell subcategoriaHeaderCell = row.createCell(5);
        subcategoriaHeaderCell.setCellValue("SUBCATEGORIA");
        subcategoriaHeaderCell.setCellStyle(style);

        Cell marcaHeaderCell = row.createCell(6);
        marcaHeaderCell.setCellValue("MARCA");
        marcaHeaderCell.setCellStyle(style);


        int dataRowIndex = 1;

        for(Hardware hw: hardware){
            Row newRow = sheet.createRow(dataRowIndex);
            newRow.createCell(0).setCellValue(hw.getId());
            newRow.createCell(1).setCellValue(hw.getSerie());
            newRow.createCell(2).setCellValue(hw.getEstado());
            newRow.createCell(3).setCellValue(hw.getEquipo() == null ? "No asignado" : hw.getEquipo().getNombre());
            newRow.createCell(4).setCellValue(hw.getModelo().getNombre());
            newRow.createCell(5).setCellValue(hw.getModelo().getSubcategoria().getNombre());
            newRow.createCell(6).setCellValue(hw.getModelo().getMarca().getNombre());
            dataRowIndex++;
        }

        excelReportHelper.autoSizeSheetColumns(sheet, 6);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void validateHardware(String serie){
        Optional<Hardware> hardware = hardwareService.findBySerie(serie);
        if(hardware.isPresent()){
            throw new DuplicateKeyException("El hardware con serie'" + serie + "' ya existe");
        }
    }
}
