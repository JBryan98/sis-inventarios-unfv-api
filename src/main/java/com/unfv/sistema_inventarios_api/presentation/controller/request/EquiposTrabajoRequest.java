package com.unfv.sistema_inventarios_api.presentation.controller.request;

import com.unfv.sistema_inventarios_api.domain.dto.ModeloDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EquiposTrabajoRequest {
    private String serie;
    private String estado;
    private ModeloDto modelo;
}