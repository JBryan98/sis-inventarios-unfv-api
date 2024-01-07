package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.common.reports.ExcelReportHelper;
import com.unfv.sistema_inventarios_api.domain.dto.ModeloDto;
import com.unfv.sistema_inventarios_api.domain.service.IModeloDtoService;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.HardwareSpecification;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.ModeloSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.ModeloRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.DecoderException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/sistema_inventarios_unfv/api/modelos")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class ModeloController {
    private final IModeloDtoService modeloDtoService;
    private final ExcelReportHelper excelReportHelper;

    @GetMapping
    public ResponseEntity<Page<ModeloDto>> findAll(ModeloSpecification specification, Pageable pageable){
        return new ResponseEntity<>(modeloDtoService.findAll(specification, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloDto> findByNombre(@PathVariable Long id){
        return new ResponseEntity<>(modeloDtoService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/descargar-excel")
    public void generateExcelReport(HttpServletResponse response, ModeloSpecification modeloSpecification) throws IOException, DecoderException {
        HttpServletResponse httpServletResponse = excelReportHelper.getExcelReportResponse(response, "Reporte_modelos");
        modeloDtoService.downloadExcel(httpServletResponse, modeloSpecification);
    }

    @PostMapping
    public ResponseEntity<ModeloDto> create(@RequestBody ModeloRequest modeloRequest){
        return new ResponseEntity<>(modeloDtoService.create(modeloRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloDto> update(@PathVariable Long id, @RequestBody ModeloRequest modeloRequest){
        return new ResponseEntity<>(modeloDtoService.update(id, modeloRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByNombre(@PathVariable Long id){
        modeloDtoService.deleteByNombre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
