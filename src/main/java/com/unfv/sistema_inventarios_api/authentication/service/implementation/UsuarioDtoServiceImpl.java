package com.unfv.sistema_inventarios_api.authentication.service.implementation;

import com.unfv.sistema_inventarios_api.authentication.controller.request.ChangePasswordAdminRequest;
import com.unfv.sistema_inventarios_api.authentication.controller.request.ChangePasswordUserRequest;
import com.unfv.sistema_inventarios_api.authentication.controller.request.UsuarioRequest;
import com.unfv.sistema_inventarios_api.authentication.controller.request.mapper.UsuarioRequestMapper;
import com.unfv.sistema_inventarios_api.authentication.dto.UsuarioDto;
import com.unfv.sistema_inventarios_api.authentication.mapper.UsuarioDtoMapper;
import com.unfv.sistema_inventarios_api.authentication.service.IUsuarioDtoService;
import com.unfv.sistema_inventarios_api.persistance.entity.Usuario;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.UsuarioSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioDtoServiceImpl implements IUsuarioDtoService {
    private final IUsuarioService usuarioService;
    private final UsuarioDtoMapper usuarioDtoMapper;
    private final UsuarioRequestMapper usuarioRequestMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UsuarioDto> findAll(UsuarioSpecification specification, Pageable pageable) {
        return usuarioService.findAll(specification, pageable).map(usuarioDtoMapper::toDto);
    }

    @Override
    public UsuarioDto findByEmail(String email) {
        return usuarioDtoMapper.toDto(usuarioService.findByEmailOrThrowException(email));
    }

    @Override
    public UsuarioDto create(UsuarioRequest usuarioRequest) {
        validarDni(usuarioRequest.getDni());
        validarEmail(usuarioRequest.getEmail());
        Usuario usuario = usuarioRequestMapper.toEntity(usuarioRequest);
        usuario.setPassword(encryptPassword(usuario.getPassword()));
        return usuarioDtoMapper.toDto(usuarioService.create(usuario));
    }

    @Override
    public UsuarioDto update(String email, UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioService.findByEmailOrThrowException(email);
        if(!usuario.getEmail().equals(usuarioRequest.getEmail())){
            validarEmail(usuarioRequest.getEmail());
        }
        if(!usuario.getDni().equals(usuarioRequest.getDni())){
            validarDni(usuarioRequest.getDni());
        }
        Usuario usuarioActualizado = usuarioRequestMapper.toEntity(usuarioRequest);
        usuarioActualizado.setId(usuario.getId());
        usuarioActualizado.setPassword(usuario.getPassword());
        return usuarioDtoMapper.toDto(usuarioService.update(usuarioActualizado));
    }

    @Override
    public void changeMyPassword(ChangePasswordUserRequest changePasswordUserRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByEmailOrThrowException(email);
        if(!passwordEncoder.matches(changePasswordUserRequest.getOldPassword(), usuario.getPassword())){
            throw new BadCredentialsException("La contraseña antigua es incorrecta");
        }
        if(!changePasswordUserRequest.getNewPassword().equals(changePasswordUserRequest.getConfirmPassword())){
            throw new BadCredentialsException("Las contraseñas no coinciden");
        }
        usuario.setPassword(encryptPassword(changePasswordUserRequest.getNewPassword()));
        usuarioService.update(usuario);
    }

    @Override
    public void changePasswordAdmin(String email, ChangePasswordAdminRequest changePasswordAdminRequest) {
        Usuario usuario = usuarioService.findByEmailOrThrowException(email);
        usuario.setPassword(encryptPassword(changePasswordAdminRequest.getNewPassword()));
        usuarioService.update(usuario);
    }

    @Override
    public void delete(String email) {
        Usuario usuario = usuarioService.findByEmailOrThrowException(email);
        usuarioService.deleteById(usuario.getId());
    }

    private String encryptPassword(String password){
        return passwordEncoder.encode(password);
    }

    private void validarEmail(String email){
        Optional<Usuario> usuario = usuarioService.findByEmail(email);
        if(usuario.isPresent()){
            throw new DuplicateKeyException("El usuario " + usuario.get().getNombres() + " " + usuario.get().getApellidos() + " ya cuenta con este email");
        }
    }

    private void validarDni(String dni){
        Optional<Usuario> usuario = usuarioService.findByDni(dni);
        if(usuario.isPresent()){
            throw new DuplicateKeyException("El usuario " + usuario.get().getNombres() + " " + usuario.get().getApellidos() + " ya cuenta con este dni");
        }
    }
}
