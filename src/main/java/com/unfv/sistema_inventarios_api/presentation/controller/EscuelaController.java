package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.domain.dto.EscuelaDto;
import com.unfv.sistema_inventarios_api.domain.service.IEscuelaDtoService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.EscuelaRequest;
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
@RequestMapping("/sistema_inventarios_unfv/api/escuelas")
@RequiredArgsConstructor
public class EscuelaController {
    private final IEscuelaDtoService escuelaDtoService;

    @GetMapping
    public ResponseEntity<Page<EscuelaDto>> findAll(Pageable pageable){
        return new ResponseEntity<>(escuelaDtoService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{abreviatura}")
    public ResponseEntity<EscuelaDto> findByAbreviatura(@PathVariable String abreviatura){
        return new ResponseEntity<>(escuelaDtoService.findByAbreviatura(abreviatura), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EscuelaDto> create(@RequestBody EscuelaRequest escuelaRequest){
        return new ResponseEntity<>(escuelaDtoService.create(escuelaRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{abreviatura}")
    public ResponseEntity<EscuelaDto> update(@PathVariable String abreviatura, @RequestBody EscuelaRequest escuelaRequest){
        return new ResponseEntity<>(escuelaDtoService.update(abreviatura, escuelaRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{abreviatura}")
    public ResponseEntity<Void> delete(@PathVariable String abreviatura){
        escuelaDtoService.deleteByAbreviatura(abreviatura);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
