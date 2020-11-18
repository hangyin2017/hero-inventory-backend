package com.hero.controllers;


import com.hero.dtos.manufacturer.ManufacturerGetDto;
import com.hero.dtos.manufacturer.ManufacturerPostDto;
import com.hero.dtos.manufacturer.ManufacturerPutDto;
import com.hero.services.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

    public final ManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<List<ManufacturerGetDto>> getAllManufacturers() {
        List<ManufacturerGetDto> manufacturerGetDtoList = manufacturerService.getAllManufacturers();
        return ResponseEntity.ok(manufacturerGetDtoList);
    }

    @PostMapping
    public ResponseEntity<ManufacturerGetDto> postManufacturers(@RequestBody ManufacturerPostDto manufacturerPostDto) {
        return ResponseEntity.ok(manufacturerService.postManufacturer(manufacturerPostDto));
    }

    @PutMapping("/{manufacturerId}")
    public ResponseEntity<ManufacturerGetDto> modifyManufacturers(@PathVariable Long manufacturerId, @RequestBody ManufacturerPutDto manufacturerPutDto) {
        ManufacturerGetDto manufacturerGetDto = manufacturerService.modifyManufacturer(manufacturerId, manufacturerPutDto);
        return ResponseEntity.ok(manufacturerGetDto);
    }

    @DeleteMapping("/{manufacturerId}")
    public ResponseEntity deleteManufacturer(@PathVariable Long manufacturerId) {
        manufacturerService.deleteManufacturer(manufacturerId);
        return ResponseEntity.ok().build();
    }

}
