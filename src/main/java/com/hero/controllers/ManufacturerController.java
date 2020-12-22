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
    public ResponseEntity<List<ManufacturerGetDto>> getAll() {
        List<ManufacturerGetDto> manufacturerGetDtoList = manufacturerService.getAll();
        return ResponseEntity.ok(manufacturerGetDtoList);
    }

    @PostMapping
    public ResponseEntity<ManufacturerGetDto> addOne(@RequestBody ManufacturerPostDto manufacturerPostDto) {
        return ResponseEntity.ok(manufacturerService.addOne(manufacturerPostDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerGetDto> update(@PathVariable Long id,
                                                     @RequestBody ManufacturerPutDto manufacturerPutDto) {
        ManufacturerGetDto manufacturerGetDto = manufacturerService.update(id, manufacturerPutDto);
        return ResponseEntity.ok(manufacturerGetDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        manufacturerService.delete(id);
        return ResponseEntity.ok().build();
    }

}
