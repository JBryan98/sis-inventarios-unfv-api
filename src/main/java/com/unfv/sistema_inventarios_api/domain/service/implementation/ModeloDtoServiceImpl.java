package com.unfv.sistema_inventarios_api.domain.service.implementation;

import com.unfv.sistema_inventarios_api.common.reports.ExcelReportHelper;
import com.unfv.sistema_inventarios_api.domain.dto.ModeloDto;
import com.unfv.sistema_inventarios_api.domain.mapper.ModeloDtoMapper;
import com.unfv.sistema_inventarios_api.domain.service.IModeloDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Modelo;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.ModeloSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IModeloService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.mapper.ModeloRequestMapper;
import com.unfv.sistema_inventarios_api.presentation.controller.request.ModeloRequest;
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
public class ModeloDtoServiceImpl implements IModeloDtoService {
    private final IModeloService modeloService;
    private final ModeloDtoMapper modeloDtoMapper;
    private final ModeloRequestMapper modeloRequestMapper;
    private final ExcelReportHelper excelReportHelper;

    @Override
    public Page<ModeloDto> findAll(ModeloSpecification specification, Pageable pageable) {
        return modeloService.findAll(specification, pageable).map(modeloDtoMapper::toDto);
    }

    @Override
    public ModeloDto findById(Long id) {
        return modeloDtoMapper.toDto(modeloService.findByIdOrThrowException(id));
    }

    @Override
    public ModeloDto create(ModeloRequest modeloRequest) {
        validarNombreYCategoria(modeloRequest.getNombre(), modeloRequest.getSubcategoria().getNombre());
        Modelo modeloCreado = modeloService.create(modeloRequestMapper.toEntity(modeloRequest));
        return modeloDtoMapper.toDto(modeloCreado);
    }

    @Override
    public ModeloDto update(Long id, ModeloRequest modeloRequest) {
        Modelo modelo = modeloService.findByIdOrThrowException(id);
        if(!modelo.getNombre().equals(modeloRequest.getNombre())){
            validarNombreYCategoria(modeloRequest.getNombre(), modeloRequest.getSubcategoria().getNombre());
        }
        Modelo modeloActualizado = modeloRequestMapper.toEntity(modeloRequest);
        modeloActualizado.setId(modelo.getId());
        return modeloDtoMapper.toDto(modeloService.update(modeloActualizado));
    }

    @Override
    public void deleteByNombre(Long id) {
        Modelo modelo = modeloService.findByIdOrThrowException(id);
        modeloService.deleteById(modelo.getId());
    }

    @Override
    public void downloadExcel(HttpServletResponse response, ModeloSpecification specification) throws IOException, DecoderException {
        List<Modelo> modelos = modeloService.findAllNoPage(specification);
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Modelos");
        Row row = sheet.createRow(0);
        XSSFCellStyle style = excelReportHelper.tableHeaderColumnStyle(workbook);

        Cell idHeaderCell = row.createCell(0);
        idHeaderCell.setCellValue("ID");
        idHeaderCell.setCellStyle(style);

        Cell modeloHeaderCell = row.createCell(1);
        modeloHeaderCell.setCellValue("MODELO");
        modeloHeaderCell.setCellStyle(style);

        Cell descripcionHeaderCell = row.createCell(2);
        descripcionHeaderCell.setCellValue("DESCRIPCIÓN");
        descripcionHeaderCell.setCellStyle(style);

        Cell subcategoriaHeaderCell = row.createCell(3);
        subcategoriaHeaderCell.setCellValue("SUBCATEGORÍA");
        subcategoriaHeaderCell.setCellStyle(style);

        Cell categoriaHeaderCell = row.createCell(4);
        categoriaHeaderCell.setCellValue("CATEGORÍA");
        categoriaHeaderCell.setCellStyle(style);

        Cell marcaHeaderCell = row.createCell(5);
        marcaHeaderCell.setCellValue("MARCA");
        marcaHeaderCell.setCellStyle(style);

        int dataRowIndex = 1;

        for(Modelo modelo: modelos){
            Row newRow = sheet.createRow(dataRowIndex);
            newRow.createCell(0).setCellValue(modelo.getId());
            newRow.createCell(1).setCellValue(modelo.getNombre());
            newRow.createCell(2).setCellValue(modelo.getDescripcion());
            newRow.createCell(3).setCellValue(modelo.getSubcategoria().getNombre());
            newRow.createCell(4).setCellValue(modelo.getSubcategoria().getCategoria().getNombre());
            newRow.createCell(5).setCellValue(modelo.getMarca().getNombre());
            dataRowIndex++;
        }

        excelReportHelper.autoSizeSheetColumns(sheet, 6);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void validarNombreYCategoria(String nombre, String nombreSubcategoria){
        Optional<Modelo> modeloOptional = modeloService.findByNombreAndSubcategoria_Nombre(nombre, nombreSubcategoria);
        if (modeloOptional.isPresent() && (modeloOptional.get().getSubcategoria().getNombre().equals(nombreSubcategoria))) {
            throw new DuplicateKeyException("El modelo '" + nombre + "' ya esta registrado en la subcategoria '" + nombreSubcategoria + "'");
        }
    }
}
