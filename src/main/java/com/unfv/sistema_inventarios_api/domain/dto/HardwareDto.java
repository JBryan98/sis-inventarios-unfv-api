package com.unfv.sistema_inventarios_api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class HardwareDto {
    private Long id;
    private String serie;
    private String estado;
    private ModeloDto modelo;
    private EquipoDto equipo;
}