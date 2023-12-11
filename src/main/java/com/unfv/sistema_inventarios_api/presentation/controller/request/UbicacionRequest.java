package com.unfv.sistema_inventarios_api.presentation.controller.request;

import com.unfv.sistema_inventarios_api.domain.dto.EquipoDto;
import com.unfv.sistema_inventarios_api.domain.dto.EquiposTrabajoDto;
import com.unfv.sistema_inventarios_api.domain.dto.FacultadDto;
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
public class UbicacionRequest {
    private String nombre;
    private FacultadDto facultad;
    private Set<EquiposTrabajoDto> equiposTrabajo;
    private Set<EquipoDto> equipos;
}
