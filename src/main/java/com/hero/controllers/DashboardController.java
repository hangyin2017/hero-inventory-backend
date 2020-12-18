package com.hero.controllers;

import com.hero.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<Long> getCount() {

        Long itemCount = itemService.getCount();
        return ResponseEntity.ok(itemCount);
    }
}