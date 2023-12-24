package com.unfv.sistema_inventarios_api.authentication.controller.request;

import com.unfv.sistema_inventarios_api.authentication.dto.RolDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {
    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String dni;
    private Set<RolDto> roles;
    private String password;
}
