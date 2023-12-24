package com.unfv.sistema_inventarios_api.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UsuarioDto {
    private Long id;
    private String nombres;
    private String apellidos;
    private String dni;
    private String email;
    private Set<RolDto> roles;
}
