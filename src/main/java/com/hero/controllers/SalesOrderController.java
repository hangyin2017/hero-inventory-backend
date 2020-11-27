package com.hero.controllers;

import com.hero.dtos.salesOrder.SalesOrderGetDto;
import com.hero.dtos.salesOrder.SalesOrderPostDto;
import com.hero.dtos.salesOrder.SalesOrderPutDto;
import com.hero.services.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> addSalesOrder(@RequestBody SalesOrderPostDto salesOrderPostDto) {
        Map<String, Object> salesOrderMap= salesOrderService.addSalesOrder(salesOrderPostDto);
        return ResponseEntity.ok(salesOrderMap);
    }

    @PutMapping("/{salesorderId}")
    public ResponseEntity<SalesOrderGetDto> update(@PathVariable Long salesorderId, @RequestBody SalesOrderPutDto salesOrderPutDto) {
        return ResponseEntity.ok(salesOrderService.modifySalesOrder(salesorderId,salesOrderPutDto));
    }

    @DeleteMapping("/{salesorderId}")
    public ResponseEntity delete(@PathVariable Long salesorderId) {
        salesOrderService.deleteSalesOrder(salesorderId);
        return ResponseEntity.ok().build();
    }
}
