package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.common.reports.ExcelReportHelper;
import com.unfv.sistema_inventarios_api.domain.dto.SubcategoriaDto;
import com.unfv.sistema_inventarios_api.domain.mapper.SubcategoriaDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.ISubcategoriaDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Subcategoria;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.SubcategoriaSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.ISubcategoriaService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.mapper.SubcategoriaRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.SubcategoriaRequest;
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
public class SubcategoriaDtoServiceImpl implements ISubcategoriaDtoService {
    private final ISubcategoriaService subcategoriaService;
    private final SubcategoriaDtoMapper subcategoriaDtoMapper;
    private final SubcategoriaRequestMapper subcategoriaRequestMapper;
    private final ExcelReportHelper excelReportHelper;


    @Override
    public Page<SubcategoriaDto> findAll(SubcategoriaSpecification specification, Pageable pageable) {
        return subcategoriaService.findAll(specification, pageable).map(subcategoriaDtoMapper::toDto);
    }

    @Override
    public SubcategoriaDto findByNombre(String nombre) {
        return subcategoriaDtoMapper.toDto(subcategoriaService.findByNombreOrThrowException(nombre));
    }

    @Override
    public SubcategoriaDto create(SubcategoriaRequest subcategoriaRequest) {
        validarSubcategoriaNombre(subcategoriaRequest.getNombre());
        Subcategoria subcategoriaCreada = subcategoriaService.create(subcategoriaRequestMapper.toEntity(subcategoriaRequest));
        return subcategoriaDtoMapper.toDto(subcategoriaCreada);
    }

    @Override
    public SubcategoriaDto update(String nombre, SubcategoriaRequest subcategoriaRequest) {
        Subcategoria subcategoria = subcategoriaService.findByNombreOrThrowException(nombre);
        if (!subcategoria.getNombre().equals(subcategoriaRequest.getNombre())) {
            validarSubcategoriaNombre(subcategoriaRequest.getNombre());
        }
        Subcategoria subcategoriaActualizada = subcategoriaRequestMapper.toEntity(subcategoriaRequest);
        subcategoriaActualizada.setId(subcategoria.getId());
        return subcategoriaDtoMapper.toDto(subcategoriaService.update(subcategoriaActualizada));
    }

    @Override
    public void deleteByNombre(String nombre) {
        Subcategoria subcategoria = subcategoriaService.findByNombreOrThrowException(nombre);
        subcategoriaService.deleteById(subcategoria.getId());
    }

    @Override
    public void downloadExcel(HttpServletResponse response, SubcategoriaSpecification specification) throws IOException, DecoderException {
        List<Subcategoria> subcategorias = subcategoriaService.findAllNoPage(specification);
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Subcategorias");
        Row row = sheet.createRow(0);
        XSSFCellStyle style = excelReportHelper.tableHeaderColumnStyle(workbook);

        Cell idHeaderCell = row.createCell(0);
        idHeaderCell.setCellValue("ID");
        idHeaderCell.setCellStyle(style);

        Cell nombreHeaderCell = row.createCell(1);
        nombreHeaderCell.setCellValue("NOMBRE");
        nombreHeaderCell.setCellStyle(style);

        Cell categoriaHeaderCell = row.createCell(2);
        categoriaHeaderCell.setCellValue("CATEGORÍA");
        categoriaHeaderCell.setCellStyle(style);

        int dataRowIndex = 1;

        for (Subcategoria subcategoria : subcategorias) {
            Row newRow = sheet.createRow(dataRowIndex);
            newRow.createCell(0).setCellValue(subcategoria.getId());
            newRow.createCell(1).setCellValue(subcategoria.getNombre());
            newRow.createCell(2).setCellValue(subcategoria.getCategoria().getNombre());
            dataRowIndex++;
        }

        excelReportHelper.autoSizeSheetColumns(sheet, 6);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void validarSubcategoriaNombre(String nombre) {
        Optional<Subcategoria> subcategoriaOptional = subcategoriaService.findByNombre(nombre);
        if (subcategoriaOptional.isPresent()) {
            throw new DuplicateKeyException("La subcategoría '" + nombre + "' ya existe");
        }
    }
}
