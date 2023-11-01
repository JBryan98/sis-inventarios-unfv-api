package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.domain.dto.CategoriaDto;
import com.unfv.sistema_inventarios_api.domain.service.ICategoriaDtoService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.CategoriaRequest;
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
@RequestMapping("/sistema_inventarios_unfv/api/categorias")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class CategoriaController {
    private final ICategoriaDtoService categoriaDtoService;

    @GetMapping
    public ResponseEntity<Page<CategoriaDto>> findAll(Pageable pageable){
        return new ResponseEntity<>(categoriaDtoService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<CategoriaDto> findByNombre(@PathVariable String nombre){
        return new ResponseEntity<>(categoriaDtoService.findByNombre(nombre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> create(@RequestBody CategoriaRequest categoriaRequest){
        return new ResponseEntity<>(categoriaDtoService.create(categoriaRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<CategoriaDto> update(@PathVariable String nombre, @RequestBody CategoriaRequest categoriaRequest){
        return new ResponseEntity<>(categoriaDtoService.update(nombre, categoriaRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> delete(@PathVariable String nombre){
        categoriaDtoService.delete(nombre);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
