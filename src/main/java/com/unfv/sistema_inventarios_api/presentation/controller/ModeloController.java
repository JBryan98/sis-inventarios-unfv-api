package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.domain.dto.ModeloDto;
import com.unfv.sistema_inventarios_api.domain.service.IModeloDtoService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.ModeloRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sistema_inventarios_unfv/api/modelos")
@RequiredArgsConstructor
public class ModeloController {
    private final IModeloDtoService modeloDtoService;

    @GetMapping
    public ResponseEntity<Page<ModeloDto>> findAll(Pageable pageable){
        return new ResponseEntity<>(modeloDtoService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<ModeloDto> findByNombre(@PathVariable String nombre){
        return new ResponseEntity<>(modeloDtoService.findByNombre(nombre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ModeloDto> create(@RequestBody ModeloRequest modeloRequest){
        return new ResponseEntity<>(modeloDtoService.create(modeloRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<ModeloDto> update(@PathVariable String nombre, @RequestBody ModeloRequest modeloRequest){
        return new ResponseEntity<>(modeloDtoService.update(nombre, modeloRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> deleteByNombre(@PathVariable String nombre){
        modeloDtoService.deleteByNombre(nombre);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
