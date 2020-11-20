package com.hero.controllers;

import com.hero.dtos.brand.BrandGetDto;
import com.hero.dtos.salesOrder.SalesOrderGetDto;
import com.hero.services.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salesorder")
@RequiredArgsConstructor
public class SalesOrderController {
    private final SalesOrderService salesOrderService;
    @GetMapping
    public ResponseEntity<List<SalesOrderGetDto>> getAllSalesOrders() {
        List<SalesOrderGetDto> salesOrderGetDtoList = salesOrderService.getAllSalesOrders();
        return ResponseEntity.ok(salesOrderGetDtoList);
    }
}
