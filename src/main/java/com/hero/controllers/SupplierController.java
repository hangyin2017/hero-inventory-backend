package com.hero.controllers;

import com.hero.dtos.supplier.SupplierGetDto;
import com.hero.dtos.supplier.SupplierPostDto;
import com.hero.dtos.supplier.SupplierPutDto;
import com.hero.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    public final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<SupplierGetDto>> getAllSuppliers() {
        List<SupplierGetDto> supplierGetDtoList = supplierService.getAllSuppliers();
        return ResponseEntity.ok(supplierGetDtoList);
    }

    @PostMapping
    public ResponseEntity<SupplierGetDto> postSuppliers(@RequestBody SupplierPostDto supplierPostDto) {
        return ResponseEntity.ok(supplierService.postSupplier(supplierPostDto));
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<SupplierGetDto> modifySuppliers(@PathVariable Long supplierId, @RequestBody SupplierPutDto supplierPutDto) {
        SupplierGetDto supplierGetDto = supplierService.modify(supplierId, supplierPutDto);
        return ResponseEntity.ok(supplierGetDto);
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity deleteSuppliers(@PathVariable Long supplierId) {
        supplierService.delete(supplierId);
        return ResponseEntity.ok().build();
    }
}
