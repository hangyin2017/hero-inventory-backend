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
    public ResponseEntity<List<SalesOrderGetDto>> getAll() {
        List<SalesOrderGetDto> salesOrderGetDtoList = salesOrderService.getAll();
        return ResponseEntity.ok(salesOrderGetDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrderGetDto> getOne(@PathVariable Long id) {
        SalesOrderGetDto salesOrderGetDto = salesOrderService.getOne(id);
        return ResponseEntity.ok(salesOrderGetDto);
    }

    @GetMapping("filter")
    public ResponseEntity<List<SalesOrderGetDto>> filterSalesOrders(@RequestParam String searchInput) {
        return ResponseEntity.ok(salesOrderService.findByNumberLike(searchInput));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addOne(@RequestBody SalesOrderPostDto salesOrderPostDto) {
        Map<String, Object> salesOrderMap= salesOrderService.addOne(salesOrderPostDto);
        return ResponseEntity.ok(salesOrderMap);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesOrderGetDto> update(@PathVariable Long id, @RequestBody SalesOrderPutDto salesOrderPutDto) {
        return ResponseEntity.ok(salesOrderService.update(id,salesOrderPutDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        salesOrderService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Map<String, Object>> confirm(@PathVariable Long id) {
        return ResponseEntity.ok(salesOrderService.confirm(id));
    }

    @GetMapping("/{id}/send")
    public ResponseEntity<Map<String, Object>> send(@PathVariable Long id) {
        return ResponseEntity.ok(salesOrderService.send(id));
    }
}
