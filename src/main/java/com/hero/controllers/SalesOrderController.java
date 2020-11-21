package com.hero.controllers;

import com.hero.dtos.brand.BrandGetDto;
import com.hero.dtos.brand.BrandPostDto;
import com.hero.dtos.salesOrder.SalesOrderGetDto;
import com.hero.dtos.salesOrder.SalesOrderPostDto;
import com.hero.services.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<SalesOrderGetDto> addSalesOrder(@RequestBody SalesOrderPostDto salesOrderPostDto) {
        SalesOrderGetDto salesOrderGetDto= salesOrderService.addSalesOrder(salesOrderPostDto);
        return ResponseEntity.ok(salesOrderGetDto);
    }
}
