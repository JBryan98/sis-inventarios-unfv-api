package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.domain.dto.EquiposTrabajoDto;
import com.unfv.sistema_inventarios_api.domain.service.IEquiposTrabajoDtoService;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.EquiposTrabajoSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EquiposTrabajoRequest;
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
@RequestMapping("/sistema_inventarios_unfv/api/equiposTrabajo")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class EquiposTrabajoController {
    private final IEquiposTrabajoDtoService equipoDtoService;

    @GetMapping
    public ResponseEntity<Page<EquiposTrabajoDto>> findAll(EquiposTrabajoSpecification specification, Pageable pageable){
        return new ResponseEntity<>(equipoDtoService.findAll(specification, pageable), HttpStatus.OK);
    }

    @GetMapping("/{serie}")
    public ResponseEntity<EquiposTrabajoDto> findByNombre(@PathVariable String serie){
        return new ResponseEntity<>(equipoDtoService.findBySerie(serie), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EquiposTrabajoDto> create(@RequestBody EquiposTrabajoRequest equiposTrabajoRequest){
        return new ResponseEntity<>(equipoDtoService.create(equiposTrabajoRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{serie}")
    public ResponseEntity<EquiposTrabajoDto> update(@PathVariable String serie, @RequestBody EquiposTrabajoRequest equiposTrabajoRequest){
        return new ResponseEntity<>(equipoDtoService.update(serie, equiposTrabajoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{serie}")
    public ResponseEntity<Void> delete(@PathVariable String serie){
        equipoDtoService.deleteByNombre(serie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}