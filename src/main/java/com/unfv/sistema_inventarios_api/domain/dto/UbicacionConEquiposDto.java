package com.unfv.sistema_inventarios_api.domain.dto;

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
public class UbicacionConEquiposDto {
    private Long id;
    private String nombre;
    private FacultadDto facultad;
    private Set<EquipoDto> equipos;
    private Set<EquiposTrabajoDto> equiposTrabajo;
}
