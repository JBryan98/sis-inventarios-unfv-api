package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.domain.dto.UbicacionConEquiposDto;
import com.unfv.sistema_inventarios_api.domain.dto.UbicacionDto;
import com.unfv.sistema_inventarios_api.domain.service.IUbicacionDtoService;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.UbicacionSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.UbicacionRequest;
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
@RequestMapping("/sistema_inventarios_unfv/api/ubicaciones")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class UbicacionController {
    private final IUbicacionDtoService ubicacionDtoService;
    @GetMapping
    public ResponseEntity<Page<UbicacionDto>> findAll(UbicacionSpecification specification, Pageable pageable){
        return new ResponseEntity<>(ubicacionDtoService.findAll(specification, pageable), HttpStatus.OK);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<UbicacionConEquiposDto> findByNombre(@PathVariable String nombre){
        return new ResponseEntity<>(ubicacionDtoService.findByNombre(nombre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UbicacionDto> create(@RequestBody UbicacionRequest ubicacionRequest){
        return new ResponseEntity<>(ubicacionDtoService.create(ubicacionRequest), HttpStatus.CREATED);
    }

    @PostMapping("/administrar-ubicacion")
    public ResponseEntity<UbicacionConEquiposDto> administrarUbicacionEquipos(@RequestBody UbicacionRequest ubicacionRequest){
        return new ResponseEntity<>(ubicacionDtoService.administrarUbicacionEquipos(ubicacionRequest), HttpStatus.OK);
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<UbicacionDto> update(@PathVariable String nombre, @RequestBody UbicacionRequest ubicacionRequest){
        return new ResponseEntity<>(ubicacionDtoService.update(nombre, ubicacionRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> delete(@PathVariable String nombre){
        ubicacionDtoService.deleteByNombre(nombre);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
