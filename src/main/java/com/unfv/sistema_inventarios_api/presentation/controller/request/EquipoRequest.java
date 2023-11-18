package com.unfv.sistema_inventarios_api.presentation.controller.request;

import com.unfv.sistema_inventarios_api.domain.dto.HardwareDto;
import com.unfv.sistema_inventarios_api.domain.dto.SoftwareDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipoRequest {
    private String nombre;
    private Set<HardwareDto> hardware;
    private Set<SoftwareDto> software;
}
