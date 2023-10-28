package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.domain.dto.MarcaDto;
import com.unfv.sistema_inventarios_api.domain.service.IMarcaDtoService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.MarcaRequest;
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
@RequestMapping("/sistema_inventarios_unfv/api/marcas")
@RequiredArgsConstructor
public class MarcaController {
    private final IMarcaDtoService marcaDtoService;

    @GetMapping
    public ResponseEntity<Page<MarcaDto>> findAll(Pageable pageable){
        return new ResponseEntity<>(marcaDtoService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<MarcaDto> findByNombre(@PathVariable String nombre){
        return new ResponseEntity<>(marcaDtoService.findByNombre(nombre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MarcaDto> create(@RequestBody MarcaRequest marcaRequest){
        return new ResponseEntity<>(marcaDtoService.create(marcaRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<MarcaDto> update(@PathVariable String nombre, @RequestBody MarcaRequest marcaRequest){
        return new ResponseEntity<>(marcaDtoService.update(nombre, marcaRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> delete(@PathVariable String nombre){
        marcaDtoService.deleteByNombre(nombre);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
