package com.unfv.sistema_inventarios_api.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EquiposDashboardDto {
    private EstadoCountDto estado;
    private Set<Top5FacultadesByEquipoCountDto> top5FacultadesByEquiposCount = new HashSet<>();
    private Set<EquiposCountBySistemaOperativoDto> countEquiposBySistemaOperativo = new HashSet<>();
}
