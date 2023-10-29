package com.unfv.sistema_inventarios_api.presentation.controller;

import com.unfv.sistema_inventarios_api.domain.dto.HardwareDto;
import com.unfv.sistema_inventarios_api.domain.service.IHardwareDtoService;
import com.unfv.sistema_inventarios_api.presentation.controller.request.HardwareRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sistema_inventarios_unfv/api/hardware")
@RequiredArgsConstructor
public class HardwareController {
    private final IHardwareDtoService hardwareDtoService;
    @GetMapping
    public ResponseEntity<Page<HardwareDto>> findAll(Pageable pageable){
        return new ResponseEntity<>(hardwareDtoService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{serie}")
    public ResponseEntity<HardwareDto> findByserie(@PathVariable String serie){
        return new ResponseEntity<>(hardwareDtoService.findBySerie(serie), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HardwareDto> create(@RequestBody HardwareRequest hardwareRequest){
        return new ResponseEntity<>(hardwareDtoService.create(hardwareRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{serie}")
    public ResponseEntity<HardwareDto> update(@PathVariable String serie, @RequestBody HardwareRequest hardwareRequest){
        return new ResponseEntity<>(hardwareDtoService.update(serie, hardwareRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{serie}")
    public ResponseEntity<Void> delete(@PathVariable String serie){
        hardwareDtoService.deleteBySerie(serie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
