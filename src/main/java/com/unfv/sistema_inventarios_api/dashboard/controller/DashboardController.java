package com.unfv.sistema_inventarios_api.dashboard.controller;

import com.unfv.sistema_inventarios_api.dashboard.dto.EquiposDashboardDto;
import com.unfv.sistema_inventarios_api.dashboard.dto.EquiposTrabajoDashboardDto;
import com.unfv.sistema_inventarios_api.dashboard.dto.HardwareDashboardDto;
import com.unfv.sistema_inventarios_api.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sistema_inventarios_unfv/api/dashboard")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class DashboardController {
    private  final DashboardService dashboardService;
    @GetMapping("/hardware")
    public ResponseEntity<HardwareDashboardDto> hardwareDashboard(){
        return new ResponseEntity<>(dashboardService.getHardwareDashboard(), HttpStatus.OK);
    }

    @GetMapping("/equipos-de-trabajo")
    public ResponseEntity<EquiposTrabajoDashboardDto> equiposTrabajoDashboard(){
        return new ResponseEntity<>(dashboardService.getEquiposTrabajoDashboard(), HttpStatus.OK);
    }
    @GetMapping("/equipos")
    public ResponseEntity<EquiposDashboardDto> equiposDashboard(){
        return new ResponseEntity<>(dashboardService.getEquiposDashboard(), HttpStatus.OK);
    }
}
