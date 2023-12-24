package com.unfv.sistema_inventarios_api.authentication.controller;

import com.unfv.sistema_inventarios_api.authentication.controller.request.RolRequest;
import com.unfv.sistema_inventarios_api.authentication.dto.RolDto;
import com.unfv.sistema_inventarios_api.authentication.service.IRolDtoService;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.RolSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sistema_inventarios_unfv/api/roles")
@RequiredArgsConstructor
@Secured("ADMIN")
public class RolController {
    private final IRolDtoService rolDtoService;

    @GetMapping
    public ResponseEntity<Page<RolDto>> findAll(RolSpecification specification, Pageable pageable){
        return new ResponseEntity<>(rolDtoService.findAll(specification, pageable), HttpStatus.OK);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<RolDto> findByNombre(@PathVariable String nombre){
        return new ResponseEntity<>(rolDtoService.findByNombre(nombre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RolDto> create(@RequestBody RolRequest rolRequest){
        return new ResponseEntity<>(rolDtoService.create(rolRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<RolDto> update(@PathVariable String nombre, @RequestBody RolRequest rolRequest){
        return new ResponseEntity<>(rolDtoService.update(nombre, rolRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> delete(@PathVariable String nombre){
        rolDtoService.delete(nombre);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
