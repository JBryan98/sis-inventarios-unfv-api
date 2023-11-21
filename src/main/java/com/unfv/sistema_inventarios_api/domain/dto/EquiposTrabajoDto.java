package com.unfv.sistema_inventarios_api.domain.dto;

import com.unfv.sistema_inventarios_api.persistance.entity.Ubicacion;
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
public class EquiposTrabajoDto {
    private Long id;
    private String serie;
    private ModeloDto modelo;
    private Ubicacion ubicacion;
}
