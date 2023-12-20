package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.domain.dto.SoftwareDto;
import com.unfv.sistema_inventarios_api.domain.service.ISoftwareDtoService;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.SoftwareSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.SoftwareRequest;
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
@RequestMapping("/sistema_inventarios_unfv/api/software")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class SoftwareController {
    private final ISoftwareDtoService softwareDtoService;
    
    @GetMapping
    public ResponseEntity<Page<SoftwareDto>> findAll(SoftwareSpecification specification, Pageable pageable){
        return new ResponseEntity<>(softwareDtoService.findAll(specification, pageable), HttpStatus.OK);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<SoftwareDto> findByNombre(@PathVariable String nombre){
        return new ResponseEntity<>(softwareDtoService.findByNombre(nombre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SoftwareDto> create(@RequestBody SoftwareRequest softwareRequest){
        return new ResponseEntity<>(softwareDtoService.create(softwareRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<SoftwareDto> update(@PathVariable String nombre, @RequestBody SoftwareRequest softwareRequest){
        return new ResponseEntity<>(softwareDtoService.update(nombre, softwareRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> delete(@PathVariable String nombre){
        softwareDtoService.deleteByNombre(nombre);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
