package com.hero.controllers;

import com.hero.dtos.purchaseOrder.PurchaseOrderGetDto;
import com.hero.dtos.purchaseOrder.PurchaseOrderPostDto;
import com.hero.dtos.purchaseOrder.PurchaseOrderPutDto;
import com.hero.services.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/purchaseorder")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @GetMapping
    public ResponseEntity<List<PurchaseOrderGetDto>> getAllPurchaseOrders() {
        List<PurchaseOrderGetDto> purchaseOrderGetDtoList = purchaseOrderService.getAllPurchaseOrders();
        return ResponseEntity.ok(purchaseOrderGetDtoList);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addPurchaseOrder(@RequestBody PurchaseOrderPostDto purchaseOrderPostDto) {
        Map<String, Object> purchaseOrderMap = purchaseOrderService.addPurchaseOrder(purchaseOrderPostDto);
        return ResponseEntity.ok(purchaseOrderMap);
    }

    @PutMapping("/{purchaseorderId}")
    public ResponseEntity<PurchaseOrderGetDto> update(@PathVariable Long purchaseorderId, @RequestBody PurchaseOrderPutDto purchaseOrderPutDto) {
        return ResponseEntity.ok(purchaseOrderService.modifyPurchaseOrder(purchaseorderId, purchaseOrderPutDto));
    }

    @DeleteMapping("/{purchaseorderId}")
    public ResponseEntity delete(@PathVariable Long purchaseorderId) {
        purchaseOrderService.deletePurchaseOrder(purchaseorderId);
        return ResponseEntity.ok().build();
    }
}
