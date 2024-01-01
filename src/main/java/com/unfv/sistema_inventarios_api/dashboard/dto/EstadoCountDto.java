package com.unfv.sistema_inventarios_api.dashboard.dto;

public interface EstadoCountDto {
    Integer getStock();
    Integer getMantenimiento();
    Integer getBaja();
    Integer getOperativo();
}
