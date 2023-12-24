package com.unfv.sistema_inventarios_api.authentication.service;

import com.unfv.sistema_inventarios_api.authentication.controller.request.ChangePasswordAdminRequest;
import com.unfv.sistema_inventarios_api.authentication.controller.request.ChangePasswordUserRequest;
import com.unfv.sistema_inventarios_api.authentication.controller.request.UsuarioRequest;
import com.unfv.sistema_inventarios_api.authentication.dto.UsuarioDto;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.UsuarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioDtoService {
    Page<UsuarioDto> findAll(UsuarioSpecification specification, Pageable pageable);
    UsuarioDto findByEmail(String email);
    UsuarioDto create(UsuarioRequest usuarioRequest);
    UsuarioDto update(String email, UsuarioRequest usuarioRequest);
    void changeMyPassword(ChangePasswordUserRequest changePasswordUserRequest);
    void changePasswordAdmin(String email, ChangePasswordAdminRequest changePasswordAdminRequest);
    void delete(String email);
}
