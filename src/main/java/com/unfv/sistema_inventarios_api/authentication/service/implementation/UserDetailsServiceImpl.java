package com.unfv.sistema_inventarios_api.authentication.service.implementation;

import com.unfv.sistema_inventarios_api.authentication.dto.UserDetailsDto;
import com.unfv.sistema_inventarios_api.persistance.entity.Usuario;
import com.unfv.sistema_inventarios_api.persistance.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUsuarioService usuarioService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByEmailOrThrowException(username);
        Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();
        return new UserDetailsDto(
                usuario.getUsername(),
                usuario.getPassword(),
                authorities,
                usuario.getNombres() + " " + usuario.getApellidos()
        );
    }
}
