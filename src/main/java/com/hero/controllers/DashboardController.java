package com.hero.controllers;

import com.hero.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<HashMap> getItemCount() {
        Long lowStockItemCount = itemService.getLowStockCount();
        Long itemCount = itemService.getCount();
        HashMap<String, Long> result = new HashMap();
        result.put("itemCount",itemCount);
        result.put("lowStockItemCount",lowStockItemCount);
        return ResponseEntity.ok(result);
    }

//    @GetMapping("/lowStockItemCount")
//    public ResponseEntity<Long> getLowStockItemCount() {
//        Long lowStockItemCount = itemService.getLowStockCount();
//        return ResponseEntity.ok(lowStockItemCount);
//    }
}