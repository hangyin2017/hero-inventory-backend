package com.hero.controllers;

import com.hero.dtos.brand.BrandGetDto;
import com.hero.dtos.item.ItemGetDto;
import com.hero.entities.Brand;
import com.hero.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
