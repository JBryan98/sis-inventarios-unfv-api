package com.unfv.sistema_inventarios_api.presentation.controller.request;

import com.unfv.sistema_inventarios_api.domain.dto.CategoriaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoriaRequest {
    private String nombre;
    private CategoriaDto categoria;
}
