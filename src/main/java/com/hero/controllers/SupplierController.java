package com.hero.controllers;

import com.hero.dtos.customer.CustomerGetDto;
import com.hero.dtos.supplier.SupplierGetDto;
import com.hero.dtos.supplier.SupplierPostDto;
import com.hero.dtos.supplier.SupplierPutDto;
import com.hero.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    public final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<SupplierGetDto>> getAll() {
        List<SupplierGetDto> supplierGetDtoList = supplierService.getAll();
        return ResponseEntity.ok(supplierGetDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierGetDto> getOne(@PathVariable Long id) {
        SupplierGetDto supplierGetDto = supplierService.getOne(id);
        return ResponseEntity.ok(supplierGetDto);
    }

    @PostMapping
    public ResponseEntity<SupplierGetDto> addOne(@RequestBody SupplierPostDto supplierPostDto) {
        return ResponseEntity.ok(supplierService.addOne(supplierPostDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierGetDto> update(@PathVariable Long id, @RequestBody SupplierPutDto supplierPutDto) {
        SupplierGetDto supplierGetDto = supplierService.update(id, supplierPutDto);
        return ResponseEntity.ok(supplierGetDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseEntity.ok().build();
    }
}
