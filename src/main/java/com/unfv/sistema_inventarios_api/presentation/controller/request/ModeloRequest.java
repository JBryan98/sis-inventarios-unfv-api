package com.unfv.sistema_inventarios_api.presentation.controller.request;

import com.unfv.sistema_inventarios_api.domain.dto.CategoriaDto;
import com.unfv.sistema_inventarios_api.domain.dto.MarcaDto;
import com.unfv.sistema_inventarios_api.domain.dto.SubcategoriaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ModeloRequest {
    private String nombre;
    private String descripcion;
    private SubcategoriaDto subcategoria;
    private MarcaDto marca;
}