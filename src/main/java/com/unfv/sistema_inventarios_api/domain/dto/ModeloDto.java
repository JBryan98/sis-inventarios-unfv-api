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
public class ModeloDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private SubcategoriaDto subcategoria;
    private MarcaDto marca;
}
