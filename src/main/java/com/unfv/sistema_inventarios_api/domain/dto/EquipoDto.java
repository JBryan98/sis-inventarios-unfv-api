package com.unfv.sistema_inventarios_api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipoDto {
    private Long id;
    private String nombre;
    private Set<HardwareDto> hardware;
    private Set<SoftwareDto> software;
}
