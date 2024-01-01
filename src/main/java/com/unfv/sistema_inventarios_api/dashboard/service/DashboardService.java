package com.unfv.sistema_inventarios_api.dashboard.service;

import com.unfv.sistema_inventarios_api.dashboard.dto.EquiposDashboardDto;
import com.unfv.sistema_inventarios_api.dashboard.dto.EquiposTrabajoDashboardDto;
import com.unfv.sistema_inventarios_api.dashboard.dto.HardwareDashboardDto;
import com.unfv.sistema_inventarios_api.persistance.repository.EquipoRepository;
import com.unfv.sistema_inventarios_api.persistance.repository.EquiposTrabajoRepository;
import com.unfv.sistema_inventarios_api.persistance.repository.HardwareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final HardwareRepository hardwareRepository;
    private final EquiposTrabajoRepository equiposTrabajoRepository;
    private final EquipoRepository equipoRepository;

    public HardwareDashboardDto getHardwareDashboard() {
        return HardwareDashboardDto.builder()
                .estado(hardwareRepository.countHardwareEstado())
                .top5HardwareByModelo(hardwareRepository.findTop5HardwareByModelo())
                .top5HardwareByMarca(hardwareRepository.findTop5HardwareByMarca())
                .build();
    }

    public EquiposTrabajoDashboardDto getEquiposTrabajoDashboard() {
        return EquiposTrabajoDashboardDto.builder()
                .estado(equiposTrabajoRepository.countEquiposTrabajoEstado())
                .top5EquiposTrabajoByModelo(equiposTrabajoRepository.findTop5EquiposTrabajoByModelo())
                .top5EquiposTrabajoByMarca(equiposTrabajoRepository.findTop5EquiposTrabajoByMarca())
                .build();
    }

    public EquiposDashboardDto getEquiposDashboard(){
        return EquiposDashboardDto.builder()
                .estado(equipoRepository.countEquiposEstado())
                .countEquiposBySistemaOperativo(equipoRepository.groupCountEquiposBySistemaOperativo())
                .top5FacultadesByEquiposCount(equipoRepository.top5Facultades())
                .build();
    }
}
