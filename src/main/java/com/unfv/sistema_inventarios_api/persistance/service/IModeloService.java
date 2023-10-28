package com.unfv.sistema_inventarios_api.persistance.service;

import com.unfv.sistema_inventarios_api.persistance.entity.Modelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IModeloService {
    Page<Modelo> findAll(Pageable pageable);

}
