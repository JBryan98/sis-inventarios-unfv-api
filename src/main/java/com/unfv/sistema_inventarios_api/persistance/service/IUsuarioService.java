package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Usuario;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.UsuarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUsuarioService {
    Page<Usuario> findAll(UsuarioSpecification specification, Pageable pageable);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByDni(String dni);

    Usuario findByEmailOrThrowException(String email);
    Usuario findByDniOrThrowException(String dni);
    Usuario create(Usuario usuario);
    Usuario update(Usuario usuario);
    void deleteById(Long id);
}
