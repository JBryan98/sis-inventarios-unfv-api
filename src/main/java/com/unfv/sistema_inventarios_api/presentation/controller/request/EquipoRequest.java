package com.unfv.sistema_inventarios_api.presentation.controller.request;

import com.unfv.sistema_inventarios_api.domain.dto.HardwareDto;
import com.unfv.sistema_inventarios_api.domain.dto.SoftwareDto;
import com.unfv.sistema_inventarios_api.domain.dto.UbicacionDto;
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
public class EquipoRequest {
    private String nombre;
    private String estado;
    private UbicacionDto ubicacion;
    private Set<HardwareDto> hardware;
    private Set<SoftwareDto> software;
}