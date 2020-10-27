package com.hero.controllers;

import com.hero.dtos.item.ItemGetDto;
import com.hero.dtos.item.ItemPostDto;
import com.hero.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemGetDto>> find() {
        List<ItemGetDto> itemGetDtoList = itemService.getAllItems();
        return ResponseEntity.ok(itemGetDtoList);
    }

    @PostMapping
    public ResponseEntity<ItemGetDto> postItem(@RequestBody ItemPostDto itemPostDto) {
        return ResponseEntity.ok(itemService.postItem(itemPostDto));
    }
}
