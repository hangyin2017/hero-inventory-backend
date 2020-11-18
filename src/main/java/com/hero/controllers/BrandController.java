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
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandGetDto>> getAllBrands() {
        List<BrandGetDto> brandGetDtoList = brandService.getAllBrands();
        return ResponseEntity.ok(brandGetDtoList);
    }

    @PostMapping
    public ResponseEntity<BrandGetDto> addBrand(@RequestBody BrandPostDto brandPostDto) {
        BrandGetDto brandGetDto = brandService.addBrand(brandPostDto);
        return ResponseEntity.ok(brandGetDto);
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<BrandGetDto> update(@PathVariable Long brandId, @RequestBody BrandPutDto brandPutDto) {
        return ResponseEntity.ok(brandService.modify(brandId,brandPutDto));
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity delete(@PathVariable Long brandId) {
        brandService.delete(brandId);
        return ResponseEntity.ok().build();
    }
}
