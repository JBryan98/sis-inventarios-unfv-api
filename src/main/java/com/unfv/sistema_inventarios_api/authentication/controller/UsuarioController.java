package com.unfv.sistema_inventarios_api.authentication.controller;

import com.unfv.sistema_inventarios_api.authentication.controller.request.ChangePasswordAdminRequest;
import com.unfv.sistema_inventarios_api.authentication.controller.request.ChangePasswordUserRequest;
import com.unfv.sistema_inventarios_api.authentication.controller.request.UsuarioRequest;
import com.unfv.sistema_inventarios_api.authentication.dto.UsuarioDto;
import com.unfv.sistema_inventarios_api.authentication.service.IUsuarioDtoService;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.UsuarioSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sistema_inventarios_unfv/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final IUsuarioDtoService usuarioDtoService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<UsuarioDto>> findAll(UsuarioSpecification specification, Pageable pageable){
        return new ResponseEntity<>(usuarioDtoService.findAll(specification, pageable), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioDto> findByEmail(@PathVariable String email){
        return new ResponseEntity<>(usuarioDtoService.findByEmail(email), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioDto> create(@RequestBody UsuarioRequest usuarioRequest){
        return new ResponseEntity<>(usuarioDtoService.create(usuarioRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioDto> update(@PathVariable String email, @RequestBody UsuarioRequest usuarioRequest){
        return new ResponseEntity<>(usuarioDtoService.update(email, usuarioRequest), HttpStatus.OK);
    }

    @PutMapping("/change-my-password")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Void> changeMyPassword(@RequestBody ChangePasswordUserRequest changePasswordUserRequest){
        usuarioDtoService.changeMyPassword(changePasswordUserRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{username}/change-password")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> changePasswordAdmin(@PathVariable String username, @RequestBody ChangePasswordAdminRequest newPassword){
        usuarioDtoService.changePasswordAdmin(username, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String email){
        usuarioDtoService.delete(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
