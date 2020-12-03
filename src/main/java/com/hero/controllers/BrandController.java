package com.hero.controllers;

import com.hero.dtos.brand.BrandGetDto;
import com.hero.dtos.brand.BrandPostDto;
import com.hero.dtos.brand.BrandPutDto;
import com.hero.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@CrossOrigin
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandGetDto>> getAll() {
        List<BrandGetDto> brandGetDtoList = brandService.getAll();
        return ResponseEntity.ok(brandGetDtoList);
    }

    @PostMapping
    public ResponseEntity<BrandGetDto> addOne(@RequestBody BrandPostDto brandPostDto) {
        BrandGetDto brandGetDto = brandService.addOne(brandPostDto);
        return ResponseEntity.ok(brandGetDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandGetDto> update(@PathVariable Long id, @RequestBody BrandPutDto brandPutDto) {
        return ResponseEntity.ok(brandService.modify(id,brandPutDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.ok().build();
    }
}
