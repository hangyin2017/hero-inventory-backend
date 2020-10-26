package com.hero.controllers;

import com.hero.dto.items.ItemGetDto;
import com.hero.services.ItemService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemGetDto>> find() {
        return ResponseEntity.ok(itemService.getAllItems());
    }
}
