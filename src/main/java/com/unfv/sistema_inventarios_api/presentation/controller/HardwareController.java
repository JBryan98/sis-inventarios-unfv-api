package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.common.reports.ExcelReportHelper;
import com.unfv.sistema_inventarios_api.domain.dto.HardwareDto;
import com.unfv.sistema_inventarios_api.domain.service.IHardwareDtoService;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.HardwareSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.HardwareRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/sistema_inventarios_unfv/api/hardware")
@CrossOrigin(origins = {"http://localhost:3000"})
@Slf4j
@RequiredArgsConstructor
public class HardwareController {
    private final IHardwareDtoService hardwareDtoService;
    private final ExcelReportHelper excelReportHelper;
    @GetMapping
    public ResponseEntity<Page<HardwareDto>> findAll(HardwareSpecification specification, Pageable pageable){
        return new ResponseEntity<>(hardwareDtoService.findAll(specification, pageable), HttpStatus.OK);
    }

    @GetMapping("/{serie}")
    public ResponseEntity<HardwareDto> findByserie(@PathVariable String serie){
        return new ResponseEntity<>(hardwareDtoService.findBySerie(serie), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/descargar-excel")
    public void generateExcelReport(HttpServletResponse response, HardwareSpecification hardwareSpecification) throws IOException, DecoderException {
        HttpServletResponse httpServletResponse = excelReportHelper.getExcelReportResponse(response, "Reporte_hardware");
        hardwareDtoService.downloadExcel(httpServletResponse, hardwareSpecification);
    }

    @PostMapping
    public ResponseEntity<HardwareDto> create(@RequestBody HardwareRequest hardwareRequest){
        return new ResponseEntity<>(hardwareDtoService.create(hardwareRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{serie}")
    public ResponseEntity<HardwareDto> update(@PathVariable String serie, @RequestBody HardwareRequest hardwareRequest){
        return new ResponseEntity<>(hardwareDtoService.update(serie, hardwareRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{serie}")
    public ResponseEntity<Void> delete(@PathVariable String serie){
        hardwareDtoService.deleteBySerie(serie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
