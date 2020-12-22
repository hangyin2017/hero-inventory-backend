package com.hero.controllers;

import com.hero.services.ItemService;
import com.hero.services.PurchaseOrderService;
import com.hero.services.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.BufferPoolMXBean;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ItemService itemService;
    private final SalesOrderService salesOrderService;
    private final PurchaseOrderService purchaseOrderService;


    @GetMapping
    public ResponseEntity<HashMap> getItemCount() {
        Long lowStockItemCount = itemService.getLowStockCount();
        Long itemCount = itemService.getCount();
        Long salesOrderCount = salesOrderService.getSalesOrderCount();
        Long purchaseOrderCount = purchaseOrderService.getPurchaseOrderCount();
        HashMap<String, Long> result = new HashMap();
        result.put("itemCount",itemCount);
        result.put("lowStockItemCount",lowStockItemCount);
        result.put("salesOrderCount",salesOrderCount);
        result.put("purchaseOrderCount",purchaseOrderCount);
        return ResponseEntity.ok(result);
    }

//    @GetMapping("/lowStockItemCount")
//    public ResponseEntity<Long> getLowStockItemCount() {
//        Long lowStockItemCount = itemService.getLowStockCount();
//        return ResponseEntity.ok(lowStockItemCount);
//    }
}