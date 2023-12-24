package com.unfv.sistema_inventarios_api.authentication.dto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDetailsDto extends User {
    private String nombreCompleto;
    private Set<String> roles;

    public UserDetailsDto(String username, String password, Collection<? extends GrantedAuthority> authorities, String nombreCompleto) {
        super(username, password, authorities);
        this.nombreCompleto = nombreCompleto;
        this.roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
}