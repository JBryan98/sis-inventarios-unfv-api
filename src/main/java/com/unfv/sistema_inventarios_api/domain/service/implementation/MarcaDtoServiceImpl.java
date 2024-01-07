package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.common.reports.ExcelReportHelper;
import com.unfv.sistema_inventarios_api.domain.dto.MarcaDto;
import com.unfv.sistema_inventarios_api.domain.mapper.MarcaDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IMarcaDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Marca;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.MarcaSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IMarcaService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.mapper.MarcaRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.MarcaRequest;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
public class MarcaDtoServiceImpl implements IMarcaDtoService {

    private final IMarcaService marcaService;
    private final MarcaDtoMapper marcaDtoMapper;
    private final MarcaRequestMapper marcaRequestMapper;
    private final ExcelReportHelper excelReportHelper;

    @Override
    public Page<MarcaDto> findAll(MarcaSpecification specification, Pageable pageable) {
        return marcaService.findAll(specification, pageable).map(marcaDtoMapper::toDto);
    }

    @Override
    public MarcaDto findByNombre(String nombre) {
        return marcaDtoMapper.toDto(marcaService.findByNombreOrThrowException(nombre));
    }

    @Override
    public MarcaDto create(MarcaRequest marcaRequest) {
        Optional<Marca> marcaOptional = marcaService.findByNombre(marcaRequest.getNombre());
        if (marcaOptional.isPresent()) {
            throw new DuplicateKeyException("La marca '" + marcaRequest.getNombre() + "' ya existe");
        }
        Marca marca = marcaService.create(marcaRequestMapper.toEntity(marcaRequest));
        return marcaDtoMapper.toDto(marca);
    }

    @Override
    public MarcaDto update(String nombre, MarcaRequest marcaRequest) {
        Marca marca = marcaService.findByNombreOrThrowException(nombre);
        if (!marca.getNombre().equals(marcaRequest.getNombre())) {
            Optional<Marca> marcaOptional = marcaService.findByNombre(marcaRequest.getNombre());
            if (marcaOptional.isPresent()) {
                throw new DuplicateKeyException("La marca '" + marcaRequest.getNombre() + "' ya existe");
            }
        }
        Marca marcaActualizada = marcaService.update(marcaRequestMapper.update(marca, marcaRequest));
        return marcaDtoMapper.toDto(marcaActualizada);
    }

    @Override
    public void deleteByNombre(String nombre) {
        Marca marca = marcaService.findByNombreOrThrowException(nombre);
        marcaService.deleteById(marca.getId());
    }

    @Override
    public void downloadExcel(HttpServletResponse response, MarcaSpecification specification) throws IOException, DecoderException {
        List<Marca> marcas = marcaService.findAllNoPage(specification);

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hardware");
        Row row = sheet.createRow(0);
        XSSFCellStyle style = excelReportHelper.tableHeaderColumnStyle(workbook);

        Cell idHeaderCell = row.createCell(0);
        idHeaderCell.setCellValue("ID");
        idHeaderCell.setCellStyle(style);

        Cell nombreHeaderCell = row.createCell(1);
        nombreHeaderCell.setCellValue("NOMBRE");
        nombreHeaderCell.setCellStyle(style);

        int dataRowIndex = 1;

        for (Marca marca : marcas) {
            Row newRow = sheet.createRow(dataRowIndex);
            newRow.createCell(0).setCellValue(marca.getId());
            newRow.createCell(1).setCellValue(marca.getNombre());
            dataRowIndex++;
        }

        excelReportHelper.autoSizeSheetColumns(sheet, 2);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}