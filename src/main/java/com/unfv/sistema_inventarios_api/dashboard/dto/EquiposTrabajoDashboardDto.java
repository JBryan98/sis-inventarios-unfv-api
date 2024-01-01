package com.unfv.sistema_inventarios_api.dashboard.dto;

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
public class EquiposTrabajoDashboardDto {
    private EstadoCountDto estado;
    private Set<Top5ByModeloDto> top5EquiposTrabajoByModelo;
    private Set<Top5ByMarcaDto> top5EquiposTrabajoByMarca;
}
