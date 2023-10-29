package com.unfv.sistema_inventarios_api.presentation.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacultadRequest {
    private String nombre;
    private String abreviatura;
}
