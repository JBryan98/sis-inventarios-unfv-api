package com.unfv.sistema_inventarios_api.persistance.service.implementation;

import com.unfv.sistema_inventarios_api.persistance.entity.EquiposTrabajo;
import com.unfv.sistema_inventarios_api.persistance.repository.EquiposTrabajoRepository;
import com.unfv.sistema_inventarios_api.persistance.repository.specifications.EquiposTrabajoSpecification;
import com.unfv.sistema_inventarios_api.persistance.service.IEquiposTrabajoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class EquiposTrabajoServiceImpl implements IEquiposTrabajoService {
    private final EquiposTrabajoRepository equiposTrabajoRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<EquiposTrabajo> findAll(EquiposTrabajoSpecification specification, Pageable pageable) {
        return equiposTrabajoRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EquiposTrabajo> findBySerie(String serie) {
        return equiposTrabajoRepository.findBySerie(serie);
    }

    @Override
    @Transactional(readOnly = true)
    public EquiposTrabajo findBySerieOrThrowException(String serie) {
        return equiposTrabajoRepository.findBySerie(serie)
                .orElseThrow(() -> new EntityNotFoundException("El equipo de trabajo con serie '" + serie + "' no existe"));
    }

    @Override
    public List<EquiposTrabajo> saveAll(Set<EquiposTrabajo> equiposTrabajos) {
        return equiposTrabajoRepository.saveAll(equiposTrabajos);
    }

    @Override
    public EquiposTrabajo create(EquiposTrabajo equiposTrabajo) {
        return equiposTrabajoRepository.save(equiposTrabajo);
    }

    @Override
    public EquiposTrabajo update(EquiposTrabajo equiposTrabajo) {
        return equiposTrabajoRepository.save(equiposTrabajo);
    }

    @Override
    public void deleteById(Long id) {
        equiposTrabajoRepository.deleteById(id);
    }
}
