package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.entity.Usuario;
import com.unfv.sistema_inventarios_api.persistance.repository.UsuarioRepository;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.UsuarioSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IUsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(UsuarioSpecification specification, Pageable pageable) {
        return usuarioRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByEmailOrThrowException(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con email '" + email + "' no existe"));
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByDniOrThrowException(String dni) {
        return usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con dni '" + dni + "' no existe"));
    }

    @Override
    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }
}
