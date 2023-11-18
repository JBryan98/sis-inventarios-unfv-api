package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.domain.dto.SubcategoriaDto;
import com.unfv.sistema_inventarios_api.domain.service.ISubcategoriaDtoService;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.SubcategoriaSpecification;
import com.unfv.sistema_inventarios_api.presentation.controller.request.SubcategoriaRequest;
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
@RequestMapping("/sistema_inventarios_unfv/api/subcategorias")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class SubcategoriaController {
    private final ISubcategoriaDtoService subcategoriaDtoService;

    @GetMapping
    public ResponseEntity<Page<SubcategoriaDto>> findAll(SubcategoriaSpecification specification, Pageable pageable){
        return new ResponseEntity<>(subcategoriaDtoService.findAll(specification, pageable), HttpStatus.OK);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<SubcategoriaDto> findByNombre(@PathVariable String nombre){
        return new ResponseEntity<>(subcategoriaDtoService.findByNombre(nombre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubcategoriaDto> create(@RequestBody SubcategoriaRequest subcategoriaRequest){
        return new ResponseEntity<>(subcategoriaDtoService.create(subcategoriaRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<SubcategoriaDto> update(@PathVariable String nombre, @RequestBody SubcategoriaRequest categoriaRequest){
        return new ResponseEntity<>(subcategoriaDtoService.update(nombre, categoriaRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> delete(@PathVariable String nombre){
        subcategoriaDtoService.deleteByNombre(nombre);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
