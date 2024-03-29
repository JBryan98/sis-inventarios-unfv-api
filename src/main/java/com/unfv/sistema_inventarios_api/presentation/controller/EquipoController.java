package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.common.reports.ExcelReportHelper;
import com.unfv.sistema_inventarios_api.domain.dto.EquipoConComponentesDto;
import com.unfv.sistema_inventarios_api.domain.dto.EquipoDto;
import com.unfv.sistema_inventarios_api.domain.service.IEquipoDtoService;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.EquipoSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EquipoRequest;
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
@RequestMapping("/sistema_inventarios_unfv/api/equipos")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class EquipoController {
    private final IEquipoDtoService equipoDtoService;
    private final ExcelReportHelper excelReportHelper;

    @GetMapping
    public ResponseEntity<Page<EquipoDto>> findAll(EquipoSpecification specification, Pageable pageable){
        return new ResponseEntity<>(equipoDtoService.findAll(specification, pageable), HttpStatus.OK);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<EquipoConComponentesDto> findByNombre(@PathVariable String nombre){
        return new ResponseEntity<>(equipoDtoService.findByNombre(nombre), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/descargar-excel")
    public void generateExcelReport(HttpServletResponse response, EquipoSpecification equipoSpecification) throws IOException, DecoderException {
        HttpServletResponse httpServletResponse = excelReportHelper.getExcelReportResponse(response, "Reporte_equipos");
        equipoDtoService.downloadExcel(httpServletResponse, equipoSpecification);
    }

    @PostMapping
    public ResponseEntity<EquipoConComponentesDto> create(@RequestBody EquipoRequest equipoRequest){
        return new ResponseEntity<>(equipoDtoService.create(equipoRequest), HttpStatus.CREATED);
    }

    @PostMapping("/administrar-equipo")
    public ResponseEntity<EquipoConComponentesDto> administrarEquipo(@RequestBody EquipoRequest equipoRequest){
        return new ResponseEntity<>(equipoDtoService.administrarEquipoHardwareYSoftware(equipoRequest), HttpStatus.OK);
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<EquipoConComponentesDto> update(@PathVariable String nombre, @RequestBody EquipoRequest equipoRequest){
        return new ResponseEntity<>(equipoDtoService.update(nombre, equipoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> delete(@PathVariable String nombre){
        equipoDtoService.deleteByNombre(nombre);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
