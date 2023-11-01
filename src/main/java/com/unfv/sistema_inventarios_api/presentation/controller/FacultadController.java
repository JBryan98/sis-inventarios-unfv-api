package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.domain.dto.FacultadDto;
import com.unfv.sistema_inventarios_api.domain.service.IFacultadDtoService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.FacultadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sistema_inventarios_unfv/api/facultades")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class FacultadController {
    private final IFacultadDtoService facultadDtoService;

    @GetMapping
    public ResponseEntity<Page<FacultadDto>> findAll(Pageable pageable){
        return new ResponseEntity<>(facultadDtoService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{abreviatura}")
    public ResponseEntity<FacultadDto> findByAbreviatura(@PathVariable String abreviatura){
        return new ResponseEntity<>(facultadDtoService.findByAbreviatura(abreviatura), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FacultadDto> create(@RequestBody FacultadRequest facultadDto){
        return new ResponseEntity<>(facultadDtoService.create(facultadDto), HttpStatus.CREATED);
    }

    @PutMapping("/{abreviatura}")
    public ResponseEntity<FacultadDto> update(@PathVariable String abreviatura, @RequestBody FacultadRequest facultadRequest){
        return new ResponseEntity<>(facultadDtoService.update(abreviatura, facultadRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{abreviatura}")
    public ResponseEntity<Void> delete(@PathVariable String abreviatura){
        facultadDtoService.deleteByAbreviatura(abreviatura);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
